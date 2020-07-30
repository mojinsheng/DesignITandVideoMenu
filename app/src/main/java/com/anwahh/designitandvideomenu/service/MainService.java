package com.anwahh.designitandvideomenu.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;

import com.anwahh.designitandvideomenu.R;
import com.anwahh.designitandvideomenu.activity.CustomerShowActivity;
import com.anwahh.designitandvideomenu.activity.MainActivity;
import com.anwahh.designitandvideomenu.activity.ManoeuvreActivity;
import com.anwahh.designitandvideomenu.activity.SetUpActivity;
import com.anwahh.designitandvideomenu.activity.TrainMenuActivity;
import com.anwahh.designitandvideomenu.activity.VideoMenuActivity;
import com.anwahh.designitandvideomenu.commonUtils.ShowToastUtils;

/**
 * @describe 服务类
 * @author Anwahh
 * @date 2020-04-10
 */
public class MainService extends Service {

    /**
     *  点击次数
     */
    final static int COUNTS = 5;
    long[] mHits = new long[COUNTS];
    /**
     *  有效时间
     */
    final long DURATION = 1000;
    /**
     * 打印Log使用的TAG
     */
    private static final String TAG = "MainService";
    /**
     * 屏幕宽度
     */
    private int ScreenWidth;
    /**
     * 屏幕高度
     */
    private int ScreenHeight;
    /**
     * 实例化的WindowManager
     */
    private WindowManager windowManager;
    /**
     * 布局参数
     */
    private WindowManager.LayoutParams layoutParams;
    /**
     * 需要引用的布局文件
     */
    private View floatView;
    /**
     * 主按钮
     */
    private ImageView iv_Relati;
    /**
     * 子按钮
     */
    private ImageView iv_advert;
    private ImageView iv_product;
    private ImageView iv_activity;
    private ImageView iv_customershow;
    private ImageView iv_train;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "MainService Created");

        // 获取屏幕大小
        DisplayMetrics displayMetrics = new DisplayMetrics();
        displayMetrics = getResources().getDisplayMetrics();
        ScreenWidth = displayMetrics.widthPixels;
        ScreenHeight = displayMetrics.heightPixels;

        // OnCreated中生成悬浮窗
        createFloatTouchWindow();

        // 生成悬浮窗后动作
        /*createFloatTouchWindowThenToDo();*/

        /* iconBeanList = getFileFromJson(iconBeanList, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/DataSource.json");*/

       // iv_Relati.setImageDrawable(Drawable.createFromPath(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/logo.jpg"));

        //new Thread(runnable).start();

    }

//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            List<ShowStatus> list = DBService.getDbService().getShowStatusData();
//            StringBuilder s = new StringBuilder();
//            for (int i=0; i< list.size(); i++) {
//                s.append(list.get(i).getStatus());
//            }
//            Log.d("1111",s.toString());
//
//            if (list.get(0).getStatus().equals("1")) {
//                handler.sendEmptyMessage(11);
//            } else {
//                handler.sendEmptyMessage(01);
//            }
//
//            if (list.get(1).getStatus().equals("1")) {
//                handler.sendEmptyMessage(12);
//            } else {
//                handler.sendEmptyMessage(02);
//            }
//
//            if (list.get(2).getStatus().equals("1")) {
//                handler.sendEmptyMessage(13);
//            } else {
//                handler.sendEmptyMessage(03);
//            }
//
//            if (list.get(3).getStatus().equals("1")) {
//                handler.sendEmptyMessage(14);
//            } else {
//                handler.sendEmptyMessage(04);
//            }
//
//            if (list.get(4).getStatus().equals("1")) {
//                handler.sendEmptyMessage(15);
//            } else {
//                handler.sendEmptyMessage(05);
//            }
//        }
//    };
//    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 11) {
//                iv_advert.setVisibility(View.VISIBLE);
//            } else if (msg.what ==01) {
//                iv_advert.setVisibility(View.GONE);
//            }
//
//            if (msg.what == 12) {
//                iv_product.setVisibility(View.VISIBLE);
//            } else if (msg.what ==02) {
//                iv_product.setVisibility(View.GONE);
//            }
//
//            if (msg.what == 13) {
//                iv_activity.setVisibility(View.VISIBLE);
//            } else if (msg.what ==03) {
//                iv_advert.setVisibility(View.GONE);
//            }
//
//            if (msg.what == 14) {
//                iv_customershow.setVisibility(View.VISIBLE);
//            } else if (msg.what ==04) {
//                iv_customershow.setVisibility(View.GONE);
//            }
//
//            if (msg.what == 15) {
//                iv_train.setVisibility(View.VISIBLE);
//            } else if (msg.what ==05) {
//                iv_train.setVisibility(View.GONE);
//            }
//
//        }
//    };
    /**
     * 创建悬浮窗
     */
    private void createFloatTouchWindow() {
        // 赋值WindowManager & LayoutParams
        windowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        // 设置type:系统提示型窗口，一般都在应用程序窗口上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        // 设置效果为背景透明
        layoutParams.format = PixelFormat.RGB_888;
        // 设置flags.不可聚焦及不可使用按钮对悬浮窗进行操控
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 设置窗口初始停靠的位置
        layoutParams.gravity = Gravity.LEFT;
        // 设置窗口的width & height,都是wrap_content包裹自身
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        // 引用布局文件
        floatView = new View(getApplication());
        floatView = View.inflate(getApplicationContext(), R.layout.float_touch_ls, null);
        // 获取主按钮控件
        iv_Relati = floatView.findViewById(R.id.relati_icon);
        // 获取子按钮
        iv_advert = floatView.findViewById(R.id.relati_advert);
        iv_product = floatView.findViewById(R.id.relati_product);
        iv_activity  =floatView.findViewById(R.id.relati_activity);
        iv_customershow = floatView.findViewById(R.id.relati_customershow);
        iv_train  =floatView.findViewById(R.id.relati_train);

        iv_advert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                iv_advert.setVisibility(View.GONE);
                iv_product.setVisibility(View.GONE);
                iv_activity.setVisibility(View.GONE);
                iv_customershow.setVisibility(View.GONE);
                iv_train.setVisibility(View.GONE);
            }
        });

        /**
         * 产品
         */
        iv_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), VideoMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                iv_advert.setVisibility(View.GONE);
                iv_product.setVisibility(View.GONE);
                iv_activity.setVisibility(View.GONE);
                iv_customershow.setVisibility(View.GONE);
                iv_train.setVisibility(View.GONE);
            }
        });

        // 活动
        iv_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ManoeuvreActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                iv_advert.setVisibility(View.GONE);
                iv_product.setVisibility(View.GONE);
                iv_activity.setVisibility(View.GONE);
                iv_customershow.setVisibility(View.GONE);
                iv_train.setVisibility(View.GONE);
            }
        });

        // 买家秀
        iv_customershow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CustomerShowActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                iv_advert.setVisibility(View.GONE);
                iv_product.setVisibility(View.GONE);
                iv_activity.setVisibility(View.GONE);
                iv_customershow.setVisibility(View.GONE);
                iv_train.setVisibility(View.GONE);
            }
        });

        // 培训
        iv_train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), TrainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                iv_advert.setVisibility(View.GONE);
//                iv_product.setVisibility(View.GONE);
//                iv_activity.setVisibility(View.GONE);
//                iv_customershow.setVisibility(View.GONE);
//                iv_train.setVisibility(View.GONE);
                Intent intent = new Intent(getApplicationContext(), TrainMenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                iv_advert.setVisibility(View.GONE);
                iv_product.setVisibility(View.GONE);
                iv_activity.setVisibility(View.GONE);
                iv_customershow.setVisibility(View.GONE);
                iv_train.setVisibility(View.GONE);

            }
        });

        // 设置主按钮点击事件
        floatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iv_advert.getVisibility() == View.GONE) {
                    iv_advert.setVisibility(View.VISIBLE);
                } else {
                    iv_advert.setVisibility(View.GONE);
                }

                if (iv_product.getVisibility() == View.GONE) {
                    iv_product.setVisibility(View.VISIBLE);
                } else {
                    iv_product.setVisibility(View.GONE);
                }

                if (iv_activity.getVisibility() == View.GONE) {
                    iv_activity.setVisibility(View.VISIBLE);
                } else {
                    iv_activity.setVisibility(View.GONE);
                }

                if (iv_customershow.getVisibility() == View.GONE) {
                    iv_customershow.setVisibility(View.VISIBLE);
                } else {
                    iv_customershow.setVisibility(View.GONE);
                }

                if (iv_train.getVisibility() == View.GONE) {
                    iv_train.setVisibility(View.VISIBLE);
                } else {
                    iv_train.setVisibility(View.GONE);
                }

                continousClick(COUNTS, DURATION);
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showMainServiceWindow();
        return super.onStartCommand(intent, flags, startId);

    }

    private void showMainServiceWindow() {
        try {
            windowManager.addView(floatView, layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }
        floatView.setOnTouchListener(new FloatingOnTouchListener());
    }

    private class FloatingOnTouchListener implements View.OnTouchListener{

        private int x;
        private int y;

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + movedX;
                    layoutParams.y = layoutParams.y + movedY;
                    Log.i(TAG, "onTouch: " + layoutParams.x + " " + layoutParams.y);
                    windowManager.updateViewLayout(view, layoutParams);
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (iv_Relati != null) {
            windowManager.removeViewImmediate(floatView);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void continousClick(int counts, long duration) {
        // 每次点击时，数组向前移动一位
        System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
        // 为数组最后一位赋值
        mHits[mHits.length - 1] = SystemClock.uptimeMillis();
        if (mHits[0] >= (SystemClock.uptimeMillis() - DURATION)) {
            //连续点击主按钮5次，进入设置界面
            ShowToastUtils.ShowToast(this, "连续点击了5次", Toast.LENGTH_SHORT);
            Intent intent = new Intent(MainService.this, SetUpActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private MyBinder binder = new MyBinder();

    public class MyBinder extends Binder {
        public MainService getService() {
            MainService mainService = MainService.this;
            return mainService;
        }
    }
}


