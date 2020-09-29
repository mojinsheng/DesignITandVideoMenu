package com.anwahh.designitandvideomenu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.anwahh.designitandvideomenu.R;
import com.anwahh.designitandvideomenu.bean.AllDataBean;
import com.anwahh.designitandvideomenu.utils.PermissionUtil;
import com.xx.download.XXDownLoader;

public class DownLoadActivity extends Activity {


    ProgressBar bottom_progress;
    TextView txtView;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        bottom_progress=findViewById(R.id.bottom_progress);
        txtView=findViewById(R.id.txtView);



        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            XXDownLoader.get().init(this);

            XXDownLoader.get().setXxDownLoaderListener(new XXDownLoader.XXDownLoaderListener() {
                @Override
                public void progress(int count, int mNumber) {
                    txtView.setText(mNumber+"/"+count);

                    float pressent = (float) mNumber / count * 100;//i 增加数量，mBNumber 总数
                    bottom_progress.setProgress((int) pressent);

                }

                @Override
                public void finish() {

                    DownLoadActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                              txtView.setText("下载完成");
                              Toast.makeText(DownLoadActivity.this,"已下载完成",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(DownLoadActivity.this,MainActivity.class);
                            startActivity(intent);
                            DownLoadActivity.this.finish();
                        }
                    });

                }
            });
            if(PermissionUtil.requestPermissions_STORAGE(this,1000)){
                XXDownLoader.get().startDownTask(DownLoadActivity.this,"http://rlt.mtaction.com/tp/rlt.php/Home/Index/index");
            }
        } else {
            Intent intent=new Intent(DownLoadActivity.this,MainActivity.class);
            startActivity(intent);
        }





    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 1000) {
//
//        }
//    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1000) {
            XXDownLoader.get().startDownTask(DownLoadActivity.this,"http://rlt.mtaction.com/tp/rlt.php/Home/Index/index");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销广播
        if (networkInfo != null && networkInfo.isAvailable()) {
            XXDownLoader.get().unRegister();

        }

    }



}
