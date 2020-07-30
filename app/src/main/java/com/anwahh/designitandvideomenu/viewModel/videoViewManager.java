package com.anwahh.designitandvideomenu.viewModel;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import java.io.File;

/**
 * @describe 视频播放单例
 * @author Anwahh
 * @date 2020-04-10
 */
public class videoViewManager {

    private static videoViewManager instance;
    private Context mContext;

    private videoViewManager() {

    }

    public static videoViewManager GetInstance() {
        if (instance == null) {
            instance = new videoViewManager();
        }
        return instance;
    }

    public int index;
    private CustomerVideoView mCustomerVideoView;

    public void playVideo(final VideoView view, final File[] videoFiles) {
        if (videoFiles != null && videoFiles.length != 0) {
            index = 0;
            File file = videoFiles[index];
            String externName = file.getName();
            Log.d("file", externName);
            view.setVideoURI(Uri.parse(file.getPath()));
            if (view.getVisibility() == View.GONE) {
                view.setVisibility(View.VISIBLE);
            }
            view.start();
        } else {
            return;
        }

        /**
         * 提前加载，防止软件启动视频黑屏
         */
        view.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });

        /**
         * 播放问当前视频自动播放下一个
         */
        view.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                index++;
                if (index >= videoFiles.length) {
                    index = 0;
                    File file = videoFiles[index];
                    String externName = file.getName();
                    Log.d("file", externName);
                    view.setVideoURI(Uri.parse(file.getPath()));
                    view.start();
                } else {
                    File file = videoFiles[index];
                    String externName = file.getName();
                    Log.d("file", externName);
                    view.setVideoURI(Uri.parse(file.getPath()));
                    view.start();
                }
            }
        });
    }
}
