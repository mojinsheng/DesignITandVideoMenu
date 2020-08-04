package com.anwahh.designitandvideomenu.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.anwahh.designitandvideomenu.R;
import com.anwahh.designitandvideomenu.commonUtils.ShowToastUtils;

import java.io.File;

public class TwoActivity extends Activity {

    private int FLIP_DISTANCE=100;
    /**
     * 手势识别器
     */
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_two);


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
                if (e1.getX() - e2.getX() > FLIP_DISTANCE) {
                   // Log.i(TAG, "向左滑...");
                    ShowToastUtils.ShowToast(TwoActivity.this, "向左滑", Toast.LENGTH_SHORT);

                    return true;
                }
                if (e2.getX() - e1.getX() > FLIP_DISTANCE) {
                    //Log.i(TAG, "向右滑...");
                    ShowToastUtils.ShowToast(TwoActivity.this, "向右滑..", Toast.LENGTH_SHORT);

                    return true;
                }
                if (e1.getY() - e2.getY() > FLIP_DISTANCE) {
                   // Log.i(TAG, "向上滑...");
                    ShowToastUtils.ShowToast(TwoActivity.this, "向上滑...", Toast.LENGTH_SHORT);

                    return true;
                }
                if (e2.getY() - e1.getY() > FLIP_DISTANCE) {
                   // Log.i(TAG, "向下滑...");
                    ShowToastUtils.ShowToast(TwoActivity.this, "向下滑...", Toast.LENGTH_SHORT);
                    Intent intent1=new Intent(TwoActivity.this,ProVideoAndPictureActivity.class);

                    startActivity(intent1);
                    overridePendingTransition(R.anim.activity_open,0);
                    return true;
                }

                Log.d("TAG", e2.getX() + " " + e2.getY());

                return false;
//                if (Math.abs(e1.getRawY() - e2.getRawY()) > 100) {
//                  ShowToastUtils.ShowToast(TwoActivity.this, "动作不合法,请左右滑动", Toast.LENGTH_SHORT);
////                    Intent intent1=new Intent(TwoActivity.this,ProVideoAndPictureActivity.class);
////
////                    startActivity(intent1);
////                    overridePendingTransition(R.anim.in_from_up, android.R.anim.fade_out);
////                    finish();
//                    return true;
//                }
//                 if (Math.abs(e2.getRawY() - e1.getRawY()) > 100) {
//                     ShowToastUtils.ShowToast(TwoActivity.this, "滑动向上", Toast.LENGTH_SHORT);
//                     Intent intent1=new Intent(TwoActivity.this,ProVideoAndPictureActivity.class);
//
//                     startActivity(intent1);
//                     //overridePendingTransition(R.anim.activity_open,0);
//                     return true;
//                }
//                if (Math.abs(velocityX) < 150) {
//                    ShowToastUtils.ShowToast(TwoActivity.this, "滑动的太慢", Toast.LENGTH_SHORT);
//                    return true;
//                }
//
//                if ((e1.getRawX() - e2.getRawX()) > 200) {
//                    ShowToastUtils.ShowToast(TwoActivity.this, "左滑", Toast.LENGTH_SHORT);
//
//                    return true;
//                }
//
//                if ((e2.getRawX() - e1.getRawX()) > 200) {
//                    ShowToastUtils.ShowToast(TwoActivity.this, "右滑", Toast.LENGTH_SHORT);
//
//                    return true;
//                }
//                return false;
            }
        });

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }
}
