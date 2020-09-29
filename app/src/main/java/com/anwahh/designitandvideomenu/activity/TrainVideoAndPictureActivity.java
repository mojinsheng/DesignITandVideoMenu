package com.anwahh.designitandvideomenu.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.anwahh.designitandvideomenu.R;
import com.anwahh.designitandvideomenu.commonUtils.FileUtils;
import com.anwahh.designitandvideomenu.commonUtils.ShowToastUtils;
import com.anwahh.designitandvideomenu.view.MyViewPager;
import com.anwahh.designitandvideomenu.viewModel.CustomerVideoView;

import java.io.File;
import java.util.ArrayList;


/**
 * @author Anwahh
 * @describe 产品介绍类（视频&图片）
 * @date 2020-04-10
 */


//  ┏┓　　　┏┓
//┏┛┻━━━┛┻┓
//┃　　　　　　　┃ 　
//┃　　　━　　　┃
//┃　┳┛　┗┳　┃
//┃　　　　　　　┃
//┃　　　┻　　　┃
//┃　　　　　　　┃
//┗━┓　　　┏━┛
//    ┃　　　┃  神兽保佑　　　　　　　　
//    ┃　　　┃  代码无BUG！
//    ┃　　　┗━━━┓
//    ┃　　　　　　　┣┓
//    ┃　　　　　　　┏┛
//    ┗┓┓┏━┳┓┏┛
//      ┃┫┫　┃┫┫
//      ┗┻┛　┗┻┛

public class TrainVideoAndPictureActivity extends BaseActivity {

    /**
     * 视频播放器
     */
    private CustomerVideoView mCustomerVideoView;
    /**
     * 视频数量
     */
    private int index;
    /**
     * 图片分页
     */
    private MyViewPager myViewPager;
    /**
     * 本地图片
     */
    ArrayList<String> photos;
    /**
     * 是否正在轮播
     */
    boolean isRecycling;
    boolean isthreeRecycling;
    boolean isfourRecycling;
    boolean isfiveRecycling;

    /**
     * 是否正在运行
     * 默认：false
     */
    boolean isRunning = false;
    boolean isthreeRunning = false;
    boolean isfourRunning = false;
    boolean isfiveRunning = false;

    /**
     * 长图展示区域
     */
    private ImageView mImageView;
    /**
     * 屏幕宽度
     */
    private int screenWidth;
    /**
     * 屏幕高度
     */
    private int screenHieght;
    /**
     * 计时轮播
     */
    /*CountDownTimer timer;*/


    /**
     * 手势识别器
     */
    private GestureDetector mGestureDetector;

    /**
     * 下拉刷新界面
     */
    private SwipeRefreshLayout mSwipeRefreshLayout;


    private String pro;
    private File[] trainCount;
    private File[] baseCount;

    /**
     * 视频播放器
     */
    private CustomerVideoView customerVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_deali);

        // 获取屏幕宽度和高度的像素大小
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHieght = displayMetrics.heightPixels;


        String position = getIntent().getStringExtra("position");
        pro = FileUtils.DirPathForTrainVideo();


         trainCount = FileUtils.getFileList(FileUtils.DirPathForTrainVideo());


         if((Integer.parseInt(position)+1)>trainCount.length){
             Toast.makeText(this,"请复制相关的图片和视频",Toast.LENGTH_LONG).show();
             return;
         }


        baseCount = FileUtils.getFileList(trainCount[Integer.parseInt(position)].getPath());

        pro=baseCount[0].getPath();
        // 初始化View
        initView();
        // 初始化数据


    }

    /**
     * 初始化View控件
     */
    private void initView() {


        mSwipeRefreshLayout = findViewById(R.id.mSwipeRefreshLayout);
        customerVideoView = findViewById(R.id.mainVideoView);


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
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                    return true;
                }

                if ((e2.getRawX() - e1.getRawX()) > 200) {
                    ShowToastUtils.ShowToast(getApplicationContext(), "右滑", Toast.LENGTH_SHORT);

                    return true;
                }
                return true;
            }
        });

        initData();

    }

    /**
     * 初始化数据
     */
    @SuppressLint("ResourceAsColor")
    private void initData() {
        // 接收上个页面传递的数据信息


        // 下拉刷新控件样式相关
        mSwipeRefreshLayout.setColorSchemeColors(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light);  // 设置下拉圆圈上的颜色：蓝色、绿色、橙色
        mSwipeRefreshLayout.setDistanceToTriggerSync(100);  // 设置手指在屏幕下拉多少距离会触发下拉刷新
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(R.color.colorPrimaryDark);  // 下拉控件背景颜色
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);  // 刷新图标大小


        // 下拉刷新
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ShowToastUtils.ShowToast(TrainVideoAndPictureActivity.this, "开始刷新！", Toast.LENGTH_SHORT);
                PlayVideo(pro);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        /////////////////////////////////////////////////////////////////////////////////
        //
        //                      视频相关
        //
        /////////////////////////////////////////////////////////////////////////////////

        // 滑动切换视频
        final File[] videoFiles = FileUtils.getFileList(pro);
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

        // 播放视频
        PlayVideo(pro);

    }

    /**
     * 播放视频
     *
     * @param path 视频路径
     */
    private void PlayVideo(String path) {
        final File[] videoFiles = FileUtils.getFileList(path);
        if (videoFiles != null && videoFiles.length != 0) {
            index = 0;
            File file = videoFiles[index];
            String externName = file.getName();
            Log.d("file", externName);
            resetVideoView(file.getPath());
            customerVideoView.setVideoURI(Uri.parse(file.getPath()));
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

        customerVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
    }


    /**
     * 根据视频的宽度和高度重设VideoView的大小
     *
     * @param path
     */
    private void resetVideoView(String path) {
        // 获取视频的宽度高度
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(path);
        final String height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        final String width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
        Log.d("Video height & width", "height = " + height + " width =" + width);
        // 重设VideoView的大小
        customerVideoView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                customerVideoView.getViewTreeObserver().removeOnPreDrawListener(this);
                ViewGroup.LayoutParams videoLayoutParams = customerVideoView.getLayoutParams();
                videoLayoutParams.width = Integer.parseInt(width);
                videoLayoutParams.height = Integer.parseInt(height);
                customerVideoView.setLayoutParams(videoLayoutParams);
                return true;
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRecycling = false;
        isRunning = false;

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
}

