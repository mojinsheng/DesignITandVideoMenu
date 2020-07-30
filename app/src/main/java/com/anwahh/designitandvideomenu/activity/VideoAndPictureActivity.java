package com.anwahh.designitandvideomenu.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import androidx.viewpager.widget.ViewPager;

import com.anwahh.designitandvideomenu.R;
import com.anwahh.designitandvideomenu.adapter.MyAdapter;
import com.anwahh.designitandvideomenu.adapter.MyConAdapter;
import com.anwahh.designitandvideomenu.commonUtils.FileUtils;
import com.anwahh.designitandvideomenu.commonUtils.ShowToastUtils;
import com.anwahh.designitandvideomenu.view.ChildViewPager;
import com.anwahh.designitandvideomenu.view.MyViewPager;
import com.anwahh.designitandvideomenu.viewModel.CustomerVideoView;
import java.io.File;
import java.util.ArrayList;


/**
 * @describe 产品介绍类（视频&图片）
 * @author Anwahh
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

public class VideoAndPictureActivity extends MySwipeBackActivity implements ViewPager.OnPageChangeListener {

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
    private MyViewPager mViewPager;
    /**
     * 本地图片
     */
    ArrayList<String> photos;
    /**
     * 是否正在轮播
     */
    boolean isRecycling;
    /**
     * 是否正在运行
     * 默认：false
     */
    boolean isRunning = false;
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
     * 视频轮转
     */
    private ChildViewPager oneViewPager;

    /**
     * 手势识别器
     */
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_and_picture);

        // 获取屏幕宽度和高度的像素大小
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHieght = displayMetrics.heightPixels;



        // 初始化View
        //initView();
        // 初始化数据
        //initData();

        /*timer.start();*/













    }
    /**
     * 初始化View控件
     */
    private void initView() {
        mViewPager = (MyViewPager) findViewById(R.id.viewPager);

        ArrayList<View> viewLists = new ArrayList<View>();
        viewLists.add(getLayoutInflater().inflate(R.layout.view_one, null, false));
        viewLists.add(getLayoutInflater().inflate(R.layout.view_two, null, false));
        viewLists.add(getLayoutInflater().inflate(R.layout.view_three, null, false));
        viewLists.add(getLayoutInflater().inflate(R.layout.view_four, null, false));

        mViewPager.setAdapter(new MyConAdapter(viewLists));

        View view=viewLists.get(0);





//对子view实现setOnTouchListener(new ...)监听，在onTouch()方法里，进行拦截。
// 调用当前子view的onInterceptTouchEvent()方法。
        oneViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        (v.getParent()).requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        (v.getParent()).requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

    }

    /////////////////////////////////////////////////////////////////////////////////
    //
    //                      初始化相关
    //
    /////////////////////////////////////////////////////////////////////////////////

    /**
     * 初始化View
     */
   // private void initView() {
        // 视频
//        mCustomerVideoView = findViewById(R.id.videoView);
//        // 图片轮播
//        mViewPager = findViewById(R.id.viewPager);
//        // 长图
//        mImageView = findViewById(R.id.imageView);

   // }

    /**
     * 初始化数据
     */
    private void initData() {
        // 接收上个页面传递的数据信息
        Intent intent = getIntent();
        String path = intent.getStringExtra("video");
        String photo = intent.getStringExtra("photo");
        String longphoto = intent.getStringExtra("longphoto");


        // 图片轮播
        resetViewPager();
        mViewPager.setOnPageChangeListener(this);
        photos = new ArrayList<String>();
        selectPicture(photo, mViewPager);
        beginRecycle(mViewPager);
        // 播放视频
        playVideo(path);

        /*timer = new CountDownTimer(10000 * 12,5000) {
            File[] files = FileUtils.getFileList(photo);
            int j = 0;
            @Override
            public void onTick(long millisUntilFinished) {
                if (files != null && files.length != 0) {
                    for (int i = 0; i < files.length; i++) {
                        photos.add(files[i].getAbsolutePath());
                    }
                } else {
                    ShowToastUtils.ShowToast(VideoAndPictureActivity.this, "当前文件夹下没有图片文件", Toast.LENGTH_SHORT);
                }
                mViewPager.setImageDrawable(Drawable.createFromPath(files[j].getAbsolutePath()));
                mViewPager.refreshDrawableState();
                j++;
                if (j >= files.length) {
                    j = 0;
                }
                Log.d("j" ,Integer.toString(j));
                Log.d("time", millisUntilFinished + "");
            }

            @Override
            public void onFinish() {
                mViewPager.setImageDrawable(Drawable.createFromPath(files[j].getAbsolutePath()));
                mViewPager.refreshDrawableState();
                timer.start();
            }
        };*/

        mImageView.setImageDrawable(Drawable.createFromPath(longphoto));
    }

    /**
     * 图片轮播
     * @param viewPager 图片轮播控件
     */
    private void beginRecycle(ViewPager viewPager) {
        if (!isRecycling && photos.size() > 0) {
            isRecycling = true;
            mViewPager.setAdapter(new MyAdapter(photos, VideoAndPictureActivity.this));
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    isRunning = true;
                    while (isRunning) {
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                            }
                        });
                    }
                }
            }.start();
        } else if(photos.size() == 0) {
            ShowToastUtils.ShowToast(VideoAndPictureActivity.this, "当前文件夹下没有图片文件", Toast.LENGTH_SHORT);
            isRecycling = false;
        }
    }

    /**
     * 查询图片
     * @param photoPath 图片存放路径
     * @param viewPager 轮播容器
     */
    private void selectPicture(String photoPath, ViewPager viewPager) {
        File[] files = FileUtils.getFileList(photoPath);
        if (files != null && files.length != 0) {
            for (int i = 0; i < files.length; i++) {
                photos.add(files[i].getAbsolutePath());
            }
        } else {
            ShowToastUtils.ShowToast(VideoAndPictureActivity.this, "当前文件夹下没有图片文件", Toast.LENGTH_SHORT);
        }
    }

    /**
     * 播放视频
     * @param videoPath 视频路径
     */
    public void playVideo (String videoPath) {
        final File[] videoFiles = FileUtils.getFileList(videoPath);
        if (videoFiles != null && videoFiles.length != 0) {
            index = 0;
            File file = videoFiles[index];
            String externName = file.getName();
            Log.d("file", externName);
            resetVideoView(file.getPath());
            mCustomerVideoView.setVideoURI(Uri.parse(file.getPath()));
            if (mCustomerVideoView.getVisibility() == View.GONE) {
                mCustomerVideoView.setVisibility(View.VISIBLE);
            }
            mCustomerVideoView.start();
        } else {
            ShowToastUtils.ShowToast(this, "当前文件夹下没有视频文件", Toast.LENGTH_SHORT);
        }

        mCustomerVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                index++;
                if (index >= videoFiles.length) {
                    index = 0;
                    File file = videoFiles[index];
                    String externName = file.getName();
                    Log.d("file", externName);
                    resetVideoView(file.getPath());
                    mCustomerVideoView.setVideoURI(Uri.parse(file.getPath()));
                    mCustomerVideoView.start();
                } else {
                    File file = videoFiles[index];
                    String externName = file.getName();
                    Log.d("file", externName);
                    resetVideoView(file.getPath());
                    mCustomerVideoView.setVideoURI(Uri.parse(file.getPath()));
                    mCustomerVideoView.start();
                }
            }
        });

        mCustomerVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
    }

    /**
     * 根据视频的宽度和高度重设VideoView的大小
     * @param videoPath
     */
    public void resetVideoView (String videoPath) {
        // 获取视频的高度和宽度的像素
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(videoPath);
        String videoHeight = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
        final String videoWidth = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
        // 打印视频宽度高度的Log
        Log.d("Video height & width" , "videoHeight = " + videoHeight + " videoWidth =" + videoWidth);
        // 重设视频控件的宽度和高度与视频的宽度和高度一致
        mCustomerVideoView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mCustomerVideoView.getViewTreeObserver().removeOnPreDrawListener(this);
                ViewGroup.LayoutParams videoLayoutParams = mCustomerVideoView.getLayoutParams();
                videoLayoutParams.width = Integer.parseInt(videoWidth);
                videoLayoutParams.height = screenHieght;
                mCustomerVideoView.setLayoutParams(videoLayoutParams);
                return true;
            }
        });
    }

    /**
     * 重设ViewPager的大小
     */
    public void resetViewPager() {
        mViewPager.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mViewPager.getViewTreeObserver().removeOnPreDrawListener(this);
                ViewGroup.LayoutParams videoLayoutParams = mViewPager.getLayoutParams();
                videoLayoutParams.width = screenWidth;
                videoLayoutParams.height = screenHieght;
                mViewPager.setLayoutParams(videoLayoutParams);
                Log.d("VideoView", "123321");
                return true;
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRecycling = false;
        isRunning = false;
        /*timer.cancel();*/

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
}

