package com.anwahh.designitandvideomenu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.anwahh.designitandvideomenu.R;
import com.anwahh.designitandvideomenu.adapter.MyAdapter;
import com.anwahh.designitandvideomenu.commonUtils.FileUtils;
import com.anwahh.designitandvideomenu.commonUtils.ShowToastUtils;

import java.io.File;
import java.util.ArrayList;

public class CustomerShowActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    /**
     * 图片分页
     */
    private ViewPager mViewPager;
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
     * 本地图片
     */
    ArrayList<String> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_show);
        initView();
        initData();

    }


    /**
     * 初始化界面
     */
    private void initView() {
        mViewPager = findViewById(R.id.iv_customershow);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        photos = new ArrayList<String>();
        mViewPager.setOnPageChangeListener(this);
        selectPicture(mViewPager);
        beginRecycle(mViewPager);
    }

    /**
     * 图片轮播
     * @param view
     */
    private void beginRecycle(View view) {
        if (!isRecycling && photos.size() > 0) {
            isRecycling = true;
            mViewPager.setAdapter(new MyAdapter(photos, CustomerShowActivity.this));
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
        } else if (photos.size() == 0) {
            ShowToastUtils.ShowToast(CustomerShowActivity.this,"当前文件夹下没有图片文件！", Toast.LENGTH_SHORT);
            isRecycling = false;
        }
    }

    /**
     * 查询图片
     * @param view
     */
    private void selectPicture(View view) {
        File[] files = FileUtils.getFileList(FileUtils.DirPathForCustomerShow());
        if (files != null && files.length != 0) {
            for (int i = 0; i < files.length; i++) {
                photos.add(files[i].getAbsolutePath());
            }
        } else {
            ShowToastUtils.ShowToast(CustomerShowActivity.this, "当前文件夹下没有图片文件！", Toast.LENGTH_SHORT);
        }
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
    protected void onDestroy() {
        super.onDestroy();
        isRecycling = false;
        isRunning = false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
