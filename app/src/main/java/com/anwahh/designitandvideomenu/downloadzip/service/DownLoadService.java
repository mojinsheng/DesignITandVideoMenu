package com.anwahh.designitandvideomenu.downloadzip.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.anwahh.designitandvideomenu.downloadzip.entities.FileInfo;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownLoadService extends Service {
    private static final String TAG = "DownLoadService";
    //开始下载
    public static final String ACTION_START = "ACTION_START";
    //暂停下载
    public static final String ACTION_STOP = "ACTION_STOP";
    //更新进度
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wangtuo";
    private static final int MSG_INIT = 0;
    private DownLoadTask mTask;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            return super.onStartCommand(intent, flags, startId);
        }
        //获取activity传递过来的参数
        if (ACTION_START.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            //开启线程
            InitThread thread = new InitThread(fileInfo);
            thread.start();
        } else if (ACTION_STOP.equals(intent.getAction())) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            if (mTask != null) {
                //暂停下载
                mTask.isPause = true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    //启动下载任务
                    mTask = new DownLoadTask(DownLoadService.this, fileInfo);
                    mTask.downLoad();
                    break;
            }
        }
    };

    class InitThread extends Thread {
        private FileInfo mFileInfo;

        public InitThread(FileInfo fileInfo) {
            this.mFileInfo = fileInfo;
        }

        @Override
        public void run() {
            super.run();
            HttpURLConnection conn = null;
            RandomAccessFile faf = null;
            int length = -1;
            try {
                //连接网络文件，
                URL url = new URL(mFileInfo.url);
                conn = (HttpURLConnection) url.openConnection();
                //设置链接超时
                conn.setConnectTimeout(6000*5);
                //设置请求方式
                conn.setRequestMethod("GET");
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    //获得文件的大小
                    length = conn.getContentLength();
                }
                if (length < 0) {
                    return;
                }
                File dir = new File(DOWNLOAD_PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                //在本地创建文件
                File file = new File(dir, mFileInfo.fileName);
                faf = new RandomAccessFile(file, "rwd");
                //设置文件的长度
                faf.setLength(length);
                mFileInfo.length = length;
                //通过handler发送消息进行文件的下载
                Message message = mHandler.obtainMessage(MSG_INIT, mFileInfo);
                mHandler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.disconnect();
                    }
                    if (faf != null) {
                        faf.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
