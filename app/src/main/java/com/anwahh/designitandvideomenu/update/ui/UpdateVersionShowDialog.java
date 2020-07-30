package com.anwahh.designitandvideomenu.update.ui;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;


import com.anwahh.designitandvideomenu.R;
import com.anwahh.designitandvideomenu.commonUtils.ShowToastUtils;
import com.anwahh.designitandvideomenu.update.AppUpdater;
import com.anwahh.designitandvideomenu.update.bean.DownloadBean;
import com.anwahh.designitandvideomenu.update.net.INetDownloadCallBack;
import com.anwahh.designitandvideomenu.update.utils.AppUtils;

import java.io.File;

/**
 * @describe 版本更新弹窗类
 * @author Anwahh
 * @date 2020-04-10
 */
public class UpdateVersionShowDialog extends DialogFragment {

    private static final String KEY_DOWNLOAD_BEAN = "download_bean";

    private DownloadBean mDownloadBean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mDownloadBean = (DownloadBean) arguments.getSerializable(KEY_DOWNLOAD_BEAN);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_updater, container, false);
        bindEvents(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void bindEvents(View view) {
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvContent = view.findViewById(R.id.tv_content);
        final TextView tvUpdate = view.findViewById(R.id.tv_update);

        tvTitle.setText(mDownloadBean.title);
        tvContent.setText(mDownloadBean.content);

        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setEnabled(false);

                final File targetFile = new File(getActivity().getCacheDir(), "target.apk");

                AppUpdater.getInstance().getNetManager().download(mDownloadBean.url, targetFile, new INetDownloadCallBack() {
                    @Override
                    public void success(File apkFile) {
                        v.setEnabled(true);
                        // 安装的代码
                        Log.d("Anwahh", "success = " + apkFile.getAbsolutePath());

                        dismiss();

                        // TODO check MD5
                        String fileMD5 = AppUtils.getFileMd5(targetFile);
                        Log.d("Anwahh", "md5= " + fileMD5);

                        if(fileMD5 != null && fileMD5.equals(mDownloadBean.md5)) {
                            AppUtils.installApk(getActivity(), apkFile);
                        } else {
                            ShowToastUtils.ShowToast(getActivity(), "md5检查失败", Toast.LENGTH_SHORT);
                        }
                    }

                    @Override
                    public void progress(int progress) {
                        Log.d("Anwahh", "progress = " + progress);
                        tvUpdate.setText(progress + "%");
                    }

                    @Override
                    public void failed(Throwable throwable) {
                        v.setEnabled(true);
                        ShowToastUtils.ShowToast(getActivity(), "文件下载失败", Toast.LENGTH_SHORT);
                    }
                }, UpdateVersionShowDialog.this);
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        Log.d("Anwahh", "onDismiss");
        AppUpdater.getInstance().getNetManager().cancel(UpdateVersionShowDialog.this);
    }

    public static void show(FragmentActivity activity, DownloadBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_DOWNLOAD_BEAN, bean);
        UpdateVersionShowDialog dialog = new UpdateVersionShowDialog();
        dialog.setArguments(bundle);

        /*try {
            dialog.show(activity.getSupportFragmentManager(), "updateVersionShowDialog");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        dialog.show(activity.getSupportFragmentManager(), "updateVersionShowDialog");
    }
}
