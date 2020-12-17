package com.anwahh.designitandvideomenu.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.anwahh.designitandvideomenu.R;
import com.anwahh.designitandvideomenu.commonUtils.FileUtils;
import com.anwahh.designitandvideomenu.commonUtils.ShowToastUtils;
import com.anwahh.designitandvideomenu.view.ChildViewPager;
import com.anwahh.designitandvideomenu.viewModel.CustomerVideoView;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


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

public class ProVideoAndPictureActivity extends BaseActivity {

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
    private Banner mViewPager,mViewPager1,mViewPager2,mViewPager3;

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
    boolean isRunning= false;
    private List<String> list;

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
    private String position,proStr;

    /**
     * 手势识别器
     */
    private GestureDetector mGestureDetector;


    /**
     * 图片分页
     */
    private ChildViewPager twoViewPager,threeViewPager,fourViewPager,fiveViewPager;


    /**
     * 本地图片
     */
    ArrayList<String> photos,twophotos,threephotos,fourphotos,fivephotos;
    File[] videoFilesConunt,baseFile;

    /**
     * 本地图片
     */
    ArrayList<String> title,twotitle,threetitle,fourtitle;

    private LinearLayout ll_pro;
    private String projectPath,P1,P2,P3,P4,P5;

    /**
     * 视频播放器
     */
    private CustomerVideoView customerVideoView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_deali);

        // 获取屏幕宽度和高度的像素大小
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHieght = displayMetrics.heightPixels;

        ((ActivityManager)getSystemService(Context.ACTIVITY_SERVICE)).getLargeMemoryClass();
        ll_pro=findViewById(R.id.ll_pro);
        Intent intent = getIntent();
        position=(Integer.parseInt(intent.getStringExtra("position")))+"";

        projectPath = FileUtils.DirPathForProjectPicVideo();
        videoFilesConunt= FileUtils.getFileList(projectPath);
        if((Integer.parseInt(position)+1)>videoFilesConunt.length){
            Toast.makeText(this,"请复制相关的图片和视频",Toast.LENGTH_LONG).show();
            return;
        }
        baseFile = FileUtils.getFileList(videoFilesConunt[Integer.parseInt(position)].getPath());






        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 初始化View
                initView();
                // 初始化数据

            }
        });



    }
    /**
     * 初始化View控件
     */
    private void initView() {
        mViewPager = findViewById(R.id.viewPager);
        mViewPager1= findViewById(R.id.viewPager1);
        mViewPager2= findViewById(R.id.viewPager2);
        mViewPager3= findViewById(R.id.viewPager3);

        if(baseFile.length>=1){
            customerVideoView = findViewById(R.id.mainVideoView);
            PlayVideo(baseFile[0].getPath());
            P1=baseFile[0].getPath();
        }
//        customerVideoView.setOnTouchListener(this);
//        customerVideoView.setFocusable(true);
//        customerVideoView.setClickable(true);
//        customerVideoView.setLongClickable(true);

        customerVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override//可以捕获到触摸屏幕发生的Event事件
            public boolean onTouch(View vew, MotionEvent motionEvent) {
                mGestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });
        photos = new ArrayList<String>();

        if(baseFile.length>=2){
            resetViewPager(mViewPager);
            title=new ArrayList<String>();
            selectPicture(baseFile[1].getPath(),1);
            mViewPager.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
            mViewPager.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
            mViewPager.setImageLoader(new MyImageLoader());
            mViewPager.setDelayTime(5000);//设置轮播时间
            mViewPager.setImages(photos);//设置图片源PageTransformer
            mViewPager.setBannerTitles(title);//设置标题源
            mViewPager.start();
        }
        photos = new ArrayList<String>();
        twophotos = new ArrayList<String>();
        twotitle=new ArrayList<String>();

        threephotos = new ArrayList<String>();
        threetitle=new ArrayList<String>();

        fourphotos = new ArrayList<String>();

        if(baseFile.length>=3){
            //第二个view Banner
            resetViewPager(mViewPager1);

            selectPicture(baseFile[2].getPath(),2);
            mViewPager1.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
            mViewPager1.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位
            mViewPager1.setImageLoader(new MyImageLoader());
            mViewPager1.setDelayTime(5000);//设置轮播时间
            mViewPager1.setImages(twophotos);//设置图片源
            mViewPager1.setBannerTitles(twotitle);//设置标题源
            mViewPager1.start();
        }





        if(baseFile.length>=4){
            //第三个view Banner
            resetViewPager(mViewPager2);

            selectPicture(baseFile[3].getPath(),3);
            mViewPager2.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
            mViewPager2.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位
            mViewPager2.setImageLoader(new MyImageLoader());
            mViewPager2.setDelayTime(5000);//设置轮播时间
            mViewPager2.setImages(threephotos);//设置图片源
            mViewPager2.setBannerTitles(threetitle);//设置标题源
            mViewPager2.start();
        }



        if(baseFile.length>=5){            //第四个view Banner
            resetViewPager(mViewPager3);


            selectPicture(baseFile[4].getPath(),4);
            mViewPager3.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
            mViewPager3.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位
            mViewPager3.setImageLoader(new MyImageLoader());
            mViewPager3.setDelayTime(5000);//设置轮播时间
            mViewPager3.setImages(fourphotos);//设置图片源
            mViewPager3.setBannerTitles(fourtitle);//设置标题源
            mViewPager3.start();
        }
        if(photos.size()==0){
            mViewPager.setVisibility(View.GONE);
        }
        if(twophotos.size()==0){
            mViewPager1.setVisibility(View.GONE);
        }
        if(threephotos.size()==0){
            mViewPager2.setVisibility(View.GONE);
        }
        if(fourphotos.size()==0){
            mViewPager3.setVisibility(View.GONE);
        }
        initData();

    }

    public class MyImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);    //代码中
            Glide.with(ProVideoAndPictureActivity.this).load(path).into(imageView);
        }
    }










    /**
     * 初始化数据
     */
    @SuppressLint("ResourceAsColor")
    private void initData() {

        /////////////////////////////////////////////////////////////////////////////////
        //
        //                      视频相关
        //
        /////////////////////////////////////////////////////////////////////////////////

        // 滑动切换视频
        final File[] videoFiles = FileUtils.getFileList(P1);


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
                    //ShowToastUtils.ShowToast(getApplicationContext(), "动作不合法,请左右滑动", Toast.LENGTH_SHORT);
                    return true;
                }

                if (Math.abs(velocityX) < 150) {
                    ShowToastUtils.ShowToast(getApplicationContext(), "滑动的太慢", Toast.LENGTH_SHORT);
                    return true;
                }
                Log.i("mojin","================z左滑:"+(e1.getRawX() - e2.getRawX()));
                if ((e1.getRawX() - e2.getRawX()) > 100) {
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
                Log.i("mojin","================右滑:"+(e2.getRawX() - e1.getRawX()));

                if ((e2.getRawX() - e1.getRawX()) > 100) {
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


    }



    /**
     * 播放视频
     * @param path 视频路径
     */
    private void PlayVideo(String path) {
        try {

        final File[] videoFiles = FileUtils.getFileList(path);
        if (videoFiles != null && videoFiles.length !=0) {
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
            //ShowToastUtils.ShowToast(this, "当前文件夹下没有视频文件", Toast.LENGTH_SHORT);
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
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 查询图片
     * @param photoPath 图片存放路径
     */
    private void selectPicture(String photoPath,int pos) {
        if(pos==1){
            File[] files = FileUtils.getFileList(photoPath);
            if (files != null && files.length != 0) {
                for (int i = 0; i < files.length; i++) {
                    photos.add(files[i].getAbsolutePath());
                    title.add(" ");
                }
            } else {
               // ShowToastUtils.ShowToast(ProVideoAndPictureActivity.this, "当前文件夹下没有图片文件", Toast.LENGTH_SHORT);
            }
        }

        if(pos==2){
            File[] files = FileUtils.getFileList(photoPath);
            if (files != null && files.length != 0) {
                for (int i = 0; i < files.length; i++) {
                    twophotos.add(files[i].getAbsolutePath());
                    twotitle.add(" ");
                }
            } else {
               // ShowToastUtils.ShowToast(ProVideoAndPictureActivity.this, "当前文件夹下没有图片文件", Toast.LENGTH_SHORT);
            }
        }
        if(pos==3){
            File[] files = FileUtils.getFileList(photoPath);
            if (files != null && files.length != 0) {
                for (int i = 0; i < files.length; i++) {
                    threephotos.add(files[i].getAbsolutePath());
                    threetitle.add(" ");
                }
            } else {
               // ShowToastUtils.ShowToast(ProVideoAndPictureActivity.this, "当前文件夹下没有图片文件", Toast.LENGTH_SHORT);
            }
        }

        if(pos==4){
            File[] files = FileUtils.getFileList(photoPath);
            if (files != null && files.length != 0) {
                for (int i = 0; i < files.length; i++) {
                    fourphotos.add(files[i].getAbsolutePath());
                    fourtitle.add(" ");
                }
            } else {
               // ShowToastUtils.ShowToast(ProVideoAndPictureActivity.this, "当前文件夹下没有图片文件", Toast.LENGTH_SHORT);
            }
        }


    }




    /**
     * 重设ViewPager的大小
     */
    public void resetViewPager(Banner bviewPager) {
        bviewPager.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                bviewPager.getViewTreeObserver().removeOnPreDrawListener(this);
                ViewGroup.LayoutParams videoLayoutParams = bviewPager.getLayoutParams();
                videoLayoutParams.width = screenWidth;
                videoLayoutParams.height = screenHieght;
                bviewPager.setLayoutParams(videoLayoutParams);
                Log.d("VideoView", "123321");
                return true;
            }
        });
    }

    /**
     * 根据视频的宽度和高度重设VideoView的大小
     * @param path
     */
//    private void resetVideoView(String path) {
//        // 获取视频的宽度高度
//        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//        retriever.setDataSource(path);
//        final String height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
//        final String width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
//        Log.d("Video height & width" , "height = " + height + " width =" + width);
//        // 重设VideoView的大小
//        customerVideoView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                customerVideoView.getViewTreeObserver().removeOnPreDrawListener(this);
//                ViewGroup.LayoutParams videoLayoutParams = customerVideoView.getLayoutParams();
//                videoLayoutParams.width = screenWidth;
//                videoLayoutParams.height = screenHieght;
//                customerVideoView.setLayoutParams(videoLayoutParams);
//                return true;
//            }
//        });
//    }
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
                videoLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                videoLayoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
                customerVideoView.setLayoutParams(videoLayoutParams);
                return true;
            }
        });
    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
     //   banner.destroy();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
      //  mGestureDetector.onTouchEvent(ev);

        return super.dispatchTouchEvent(ev);
    }
}

