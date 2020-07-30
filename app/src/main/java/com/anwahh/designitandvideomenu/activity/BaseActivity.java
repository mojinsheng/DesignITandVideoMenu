package com.anwahh.designitandvideomenu.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.anwahh.designitandvideomenu.commonUtils.ShowToastUtils;
import com.anwahh.designitandvideomenu.update.AppUpdater;
import com.anwahh.designitandvideomenu.update.bean.DownloadBean;
import com.anwahh.designitandvideomenu.update.net.INetCallBack;
import com.anwahh.designitandvideomenu.update.ui.UpdateVersionShowDialog;
import com.anwahh.designitandvideomenu.update.utils.AppUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


/**
 * @describe Activity基类
 * @author Anwahh
 * @date 2020-04-10
 */
public class BaseActivity extends AppCompatActivity {

    /**
     * 打印Log时需要的TAG
     */
    public static final String TAG = "Anwahh_AppUpdate_BaseActivity";
    /**
     * 是否在前台显示
     */
    private boolean isCurrentRunningForeground = true;



    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*UpdateApplication();

        getEthernetMacAddress();
        ShowToastUtils.ShowToast(getApplicationContext(),getEthernetMacAddress(),Toast.LENGTH_SHORT) ;
        Log.d("Anwahh","MacAddress:" + getEthernetMacAddress());*/
    }

    /**
     * 获取本机Mac地址
     */
    private String getEthernetMacAddress() {
        String mac = null;
        BufferedReader bufferedReader = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("busybox ifconfig eth0");
            bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            int index = -1;
            while ((line = bufferedReader.readLine()) != null) {
                index = line.toLowerCase().indexOf("hwaddr");// find string [hwaddr]
                if (index >= 0) {
                    mac = line.substring(index +"hwaddr".length()+ 1).trim(); //extract mac address which trimed spaces in head and tail
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            bufferedReader = null;
            process = null;
        }
        return mac;
    }

    /**
     * 去除标点符号
     * @param s
     * @return
     */
    public static String format(String s){
        String str=s.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
        return str;
    }

    /**
     * 版本 升级 & 更新 模块
     */
    private void UpdateApplication() {

        AppUpdater.getInstance().getNetManager().get("http://39.98.212.0:8080/" + format(getEthernetMacAddress()) + "/app_updater_version.json", new INetCallBack() {
            @SuppressLint("LongLogTag")
            @Override
            public void success(String response) {
                Log.d(TAG, "response = " + response);
                // 1、解析json
                DownloadBean bean = DownloadBean.parse(response);
                if (bean == null) {
                    ShowToastUtils.ShowToast(BaseActivity.this, "版本检查接口返回数据异常", Toast.LENGTH_SHORT);
                    return;
                }

                // 2、做版本匹配，检查，是否需要弹窗
                try {
                    long versionCode = Long.parseLong(bean.versionCode);
                    if (versionCode <= AppUtils.getVersionCode(BaseActivity.this)) {
                        ShowToastUtils.ShowToast(BaseActivity.this, "已经是最新版本，无需更新", Toast.LENGTH_SHORT);
                        return;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    ShowToastUtils.ShowToast(BaseActivity.this, "版本接口返回版本号异常", Toast.LENGTH_SHORT);
                    return;
                }

                // 3、弹窗
                UpdateVersionShowDialog.show(BaseActivity.this, bean);
            }

            @Override
            public void failed(Throwable throwable) {
                throwable.printStackTrace();
                ShowToastUtils.ShowToast(BaseActivity.this, "版本更新接口请求失败", Toast.LENGTH_SHORT);
            }
        },BaseActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppUpdater.getInstance().getNetManager().cancel(BaseActivity.this);
    }

    /**
     * 判断本应用是否已经位于最前端
     * @return 本应用已经位于最前端时，返回true，否则返回false
     */
    @SuppressLint("LongLogTag")
    public boolean isRunningForeground() {
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfoList = activityManager.getRunningAppProcesses();
        // 枚举进程
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfoList) {
            if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (appProcessInfo.processName.equals(this.getApplicationInfo().processName)) {
                    Log.d(TAG ,"MainActivity isRunningForeGround");
                    return true;
                }
            }
        }
        Log.d(TAG, "MainActivity isRunningBackGround");
        return false;
    }

    /*@Override
    protected void onStart() {
        super.onStart();
        if(!isCurrentRunningForeground) {
            Log.d(TAG, "---------->切换到前台 activity process");
        }
    }*/

    /*@Override
    protected void onStop() {
        super.onStop();
        isCurrentRunningForeground = isRunningForeground();
        if (!isCurrentRunningForeground) {
            Log.d(TAG, "---------->切换到后台 activity process");
            Intent intent = getPackageManager().getLaunchIntentForPackage("com.anwahh.designitandvideomenu");
            startActivity(intent);
        }
    }*/

}
