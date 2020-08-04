package com.anwahh.designitandvideomenu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.anwahh.designitandvideomenu.R;
import com.anwahh.designitandvideomenu.commonUtils.ShowToastUtils;

public class ThreeActivity extends Activity {


    /**
     * 手势识别器
     */
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_three);


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

                    return true;
                }

                if ((e2.getRawX() - e1.getRawX()) > 200) {
                    ShowToastUtils.ShowToast(getApplicationContext(), "右滑", Toast.LENGTH_SHORT);

                    return true;
                }
                return true;
            }
        });

    }
}
