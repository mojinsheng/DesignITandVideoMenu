package com.anwahh.designitandvideomenu.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;

import com.anwahh.designitandvideomenu.R;
import com.anwahh.designitandvideomenu.bean.MediaBean;

import com.anwahh.designitandvideomenu.commonUtils.ActivityUtils;
import com.anwahh.designitandvideomenu.commonUtils.FileUtils;
import com.anwahh.designitandvideomenu.commonUtils.ShowToastUtils;
import com.anwahh.designitandvideomenu.service.MainService;
import com.anwahh.designitandvideomenu.utils.PermissionUtil;
import com.anwahh.designitandvideomenu.viewModel.CustomerVideoView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @describe MainActivity类
 * @author Anwahh
 * @date 2020-04-10
 */
public class MainActivity extends BaseActivity{

    /**
     * 权限集合
     */
    private static String[] PERMISSION_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    /**
     * 权限代号
     */
    private static int REQUEST_PERMISSION_CODE = 1;
    /**
     * 屏幕宽度
     */
    private int screenWidth;
    /**
     * 屏幕高度
     */
    private int screenHieght;
    /**
     * 视频播放器
     */
    private CustomerVideoView customerVideoView;
    /**
     * 文件夹数
     */
    private List dirList = new ArrayList();
    /**
     * 跳转机制Intent
     */
    private Intent intent;
    /**
     * 视频数
     */
    private int index;
    /**
     * 手势识别器
     */
    private GestureDetector mGestureDetector;
    /**
     * 下拉刷新界面
     */
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //设置全屏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取屏幕的宽度和高度
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHieght = displayMetrics.heightPixels;

        initView();
        Log.i("mojin","======================="+Environment.getExternalStorageDirectory()
                .getAbsolutePath());


        if(!PermissionUtil.requestPermissions_STORAGE(this,1000)){
            return;
        }
       // dirList = getFileFromJson(dirList, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/DataSource.json");


        //检查权限
        //getPermissionThenDo();
        // 初始化View
        //初始化数据
        initData();
    }

    /////////////////////////////////////////////////////////////////////////////////
    //
    //                      权限相关回调接口
    //
    /////////////////////////////////////////////////////////////////////////////////
    /**
     * 获取权限后创建文件
     */
    private void getPermissionThenDo() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSION_STORAGE, REQUEST_PERMISSION_CODE);
            } else {
                FileUtils.creatFolder(FileUtils.DirPathForMainVideo());
                FileUtils.creatFolder(FileUtils.DirPathForThumb());
                FileUtils.creatFolder(FileUtils.DirPathForLongPhoto());
                FileUtils.creatFolder(FileUtils.DirPathForManoeuvre());
                FileUtils.creatFolder(FileUtils.DirPathForCustomerShow());
                FileUtils.creatFolder(FileUtils.DirPathForTrain());
                FileUtils.createFolderAutoForPhoto(dirList.size());
                FileUtils.createFolderAutoForVideo(dirList.size());
            }
        } else {
            FileUtils.creatFolder(FileUtils.DirPathForMainVideo());
            FileUtils.creatFolder(FileUtils.DirPathForThumb());
            FileUtils.creatFolder(FileUtils.DirPathForLongPhoto());
            FileUtils.creatFolder(FileUtils.DirPathForManoeuvre());
            FileUtils.creatFolder(FileUtils.DirPathForCustomerShow());
            FileUtils.creatFolder(FileUtils.DirPathForTrain());
            FileUtils.createFolderAutoForPhoto(dirList.size());
            FileUtils.createFolderAutoForVideo(dirList.size());
        }
    }

    /**
     * 请求权限返回结果
     * 获得权限后重新创建文件夹
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1000) {
            //dirList = getFileFromJson(dirList, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/DataSource.json");


            //检查权限
            //getPermissionThenDo();
            // 初始化View
            //初始化数据
            initData();
        }
    }



    /////////////////////////////////////////////////////////////////////////////////
    //
    //                      初始化相关
    //
    /////////////////////////////////////////////////////////////////////////////////

    /**
     * 初始化界面
     */
    private void initView() {
        // 初始化自定义视频控件
        customerVideoView = findViewById(R.id.mainVideoView);
        // 初始化下拉刷新控件
        mSwipeRefreshLayout = findViewById(R.id.mSwipeRefreshLayout);
    }

    /**
     * 初始化数据
     */
    @SuppressLint("ResourceAsColor")
    private void initData() {

        /////////////////////////////////////////////////////////////////////////////////
        //
        //                      悬浮窗相关
        //
        /////////////////////////////////////////////////////////////////////////////////

        // 开启悬浮窗
        intent = new Intent(MainActivity.this, MainService.class);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            startService(intent);
        } else {
            startMainService();
        }

        /////////////////////////////////////////////////////////////////////////////////
        //
        //                      下拉刷新相关
        //
        /////////////////////////////////////////////////////////////////////////////////



        // 下拉刷新控件样式相关
        mSwipeRefreshLayout.setColorSchemeColors(android.R.color.holo_blue_bright,android.R.color.holo_green_light,android.R.color.holo_orange_light);  // 设置下拉圆圈上的颜色：蓝色、绿色、橙色
        mSwipeRefreshLayout.setDistanceToTriggerSync(100);  // 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(R.color.colorPrimaryDark);  // 下拉控件背景颜色
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);  // 刷新图标大小

        // 下拉刷新
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ShowToastUtils.ShowToast(MainActivity.this, "开始刷新！", Toast.LENGTH_SHORT);
                PlayVideo(FileUtils.DirPathForMainVideo());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        /////////////////////////////////////////////////////////////////////////////////
        //
        //                      视频相关
        //
        /////////////////////////////////////////////////////////////////////////////////

        // 滑动切换视频
        final File[] videoFiles = FileUtils.getFileList(FileUtils.DirPathForMainVideo());
        String  aa=FileUtils.DirPathForMainVideo();
        mGestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                if (Math.abs(e1.getRawY() - e2.getRawY()) > 100) {
                    ShowToastUtils.ShowToast(getApplicationContext(), "动作不合法,请左右滑动", Toast.LENGTH_SHORT);
                    return true;
                }

                if (Math.abs(velocityX) < 150) {
                    ShowToastUtils.ShowToast(getApplicationContext(), "滑动的太慢", Toast.LENGTH_SHORT);
                    return true;
                }

                if ((e1.getRawX() - e2.getRawX()) > 200) {
                    ShowToastUtils.ShowToast(getApplicationContext(), "左滑", Toast.LENGTH_SHORT);
                    index--;
                    if (index < 0) {
                        index = videoFiles.length - 1;
                        Log.d("index", String.valueOf(index));
                        File file = videoFiles[index];
                        String externName = file.getName();
                        Log.d("file", externName);
                        resetVideoView(file.getPath());
                        customerVideoView.setVideoURI(Uri.parse(file.getPath()));
                        customerVideoView.start();

                        customerVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();
                            }
                        });

                    } else {
                        File file = videoFiles[index];
                        String exterName = file.getName();
                        Log.d("file", exterName);
                        resetVideoView(file.getPath());
                        customerVideoView.setVideoURI(Uri.parse(file.getPath()));
                        customerVideoView.start();

                        customerVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();
                            }
                        });
                    }
                    return true;
                }

                if ((e2.getRawX() - e1.getRawX()) > 200) {
                    ShowToastUtils.ShowToast(getApplicationContext(), "右滑", Toast.LENGTH_SHORT);
                    index++;
                    if (index >= videoFiles.length) {
                        index = 0;
                        File file = videoFiles[index];
                        String externName = file.getName();
                        Log.d("file", externName);
                        resetVideoView(file.getPath());
                        customerVideoView.setVideoURI(Uri.parse(file.getPath()));
                        customerVideoView.start();

                        customerVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();
                            }
                        });

                    } else {
                        File file = videoFiles[index];
                        String externName = file.getName();
                        Log.d("file", externName);
                        resetVideoView(file.getPath());
                        customerVideoView.setVideoURI(Uri.parse(file.getPath()));
                        customerVideoView.start();

                        customerVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                mp.start();
                            }
                        });

                    }
                    return true;
                }
                return true;
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 播放视频
                PlayVideo(FileUtils.DirPathForMainVideo());
            }
        });




    }


    /**
     * 开启悬浮窗服务
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startMainService() {
        if(ActivityUtils.isServiceWork(this,"com.anwahh.designitandvideomenu.service.MainService")) {
            ShowToastUtils.ShowToast(this, "服务已启动！", Toast.LENGTH_SHORT);
        }

        if(!Settings.canDrawOverlays(this)) {
            ShowToastUtils.ShowToast(this, "当前无授权，请授权！", Toast.LENGTH_SHORT);
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
        } else {
            startService(intent);
        }
    }

    /**
     * 悬浮窗请求权限结果
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    ShowToastUtils.ShowToast(this, "授权成功！", Toast.LENGTH_SHORT );
                } else {
                    //ShowToastUtils.ShowToast(this, "授权失败！", Toast.LENGTH_SHORT);
                    startService(intent);
                }
            }
        }
    }



    /**
     * 播放视频
     * @param path 视频路径
     */
    private void PlayVideo(String path) {
        final File[] videoFiles = FileUtils.getFileList(path);
        if (videoFiles != null && videoFiles.length !=0) {
            index = 0;
            File file = videoFiles[index];
            String externName = file.getName();
            Log.d("file", "========11111111111111111111111========="+Uri.parse(file.getPath()));
            resetVideoView(file.getPath());
            customerVideoView.setVideoPath(file.getPath());
            if (customerVideoView.getVisibility() == View.GONE) {
                customerVideoView.setVisibility(View.VISIBLE);
            }
            customerVideoView.start();
        } else {
            ShowToastUtils.ShowToast(this, "当前文件夹下没有视频文件", Toast.LENGTH_SHORT);
        }

        customerVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                index++;
                if (index >= videoFiles.length) {
                    index = 0;
                    File file = videoFiles[index];
                    String externName = file.getName();
                    Log.d("file", externName);
                    resetVideoView(file.getPath());
                    customerVideoView.setVideoURI(Uri.parse(file.getPath()));
                    customerVideoView.start();
                } else {
                    File file = videoFiles[index];
                    String externName = file.getName();
                    Log.d("file", externName);
                    resetVideoView(file.getPath());
                    customerVideoView.setVideoURI(Uri.parse(file.getPath()));
                    customerVideoView.start();
                }
            }
        });
        customerVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                 Log.d("MainAActivity", "==================================OnError - Error code: " + what + " Extra code: " + extra);

                customerVideoView.stopPlayback(); //播放异常，则停止播放，防止弹窗使界面阻塞

                return true;
            }
        });
        customerVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
    }

    /**
     * 根据视频的宽度和高度重设VideoView的大小
     * @param path
     */
    private void resetVideoView(String path) {
        // 获取视频的宽度高度
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        final String height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        final String width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
        Log.d("Video height & width" , "height = " + height + " width =" + width);
        // 重设VideoView的大小
        customerVideoView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                customerVideoView.getViewTreeObserver().removeOnPreDrawListener(this);
                ViewGroup.LayoutParams videoLayoutParams = customerVideoView.getLayoutParams();
                videoLayoutParams.width = Integer.parseInt(width)/2;
                videoLayoutParams.height = Integer.parseInt(height)/2;
                customerVideoView.setLayoutParams(videoLayoutParams);
                return true;
            }
        });
    }

    /**
     * 读取JSON文件
     * @param list
     * @param path
     * @return
     */
    @SuppressLint("LongLogTag")
    private List getFileFromJson(List list, String path) {
        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            JSONObject jsonObject = new JSONObject(builder.toString());

            JSONArray array = jsonObject.getJSONArray("thumb");

            for (int i = 0; i < array.length(); i++) {
                JSONObject thumb = array.getJSONObject(i);
                StringBuilder s = new StringBuilder();
                s.append(thumb.getString("thumbId") + "   ");
                s.append(thumb.getString("name") + "   ");
                s.append(thumb.getString("poster") + "   ");
                s.append(thumb.getString("path") + "   ");
                s.append(thumb.getString("photo") + "   ");
                s.append(thumb.getString("longphoto"));
                Log.d(TAG, "result" + s.toString());
                MediaBean file = new MediaBean();
                file.setName(thumb.getString("name"));
                file.setPoster(thumb.getString("poster"));
                file.setPath(thumb.getString("path"));
                file.setPhoto(thumb.getString("photo"));
                file.setLongphoto(thumb.getString("longphoto"));

                list.add(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取本机Mac地址
     * @return mac
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
