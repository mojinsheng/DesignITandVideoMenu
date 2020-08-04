package com.anwahh.designitandvideomenu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.anwahh.designitandvideomenu.R;
import com.anwahh.designitandvideomenu.ScannerAnsyTask;
import com.anwahh.designitandvideomenu.adapter.GridAdapter;
import com.anwahh.designitandvideomenu.adapter.TrainGridAdapter;
import com.anwahh.designitandvideomenu.bean.MediaBean;
import com.anwahh.designitandvideomenu.view.GridViewWithHeaderAndFooter;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @describe 视频缩略图类
 * @author Anwahh
 * @date 2020-04-10
 */
public class TrainMenuActivity extends BaseActivity {

    /**
     * 待加载的ProgressBar
     */
    private ProgressBar mProgressBar;
    /**
     * 媒体文件的网格布局
     */
    private GridViewWithHeaderAndFooter mGridView;
    /**
     * 数据集合
     */
    private List<MediaBean> mChildList;
    /**
     * 列表适配器
     */
    private TrainGridAdapter mGridAdapter;
    /**
     * 媒体列表标题
     */
    private TextView mTitle;
    /**
     * 扫描文件的异步任务
     */
    private ScannerAnsyTask mAnsyTask;
    /**
     * 打印Log所需的TAG
     */

    /**
     * 产品布局
     */
    private LinearLayout ll_pro;
    private static final String TAG = "VideoMenuActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_menu);

        // 初始化View
        initView();
        // 启动扫描任务
        startScanTack();

    }

    /////////////////////////////////////////////////////////////////////////////////
    //
    //                      初始化相关
    //
    /////////////////////////////////////////////////////////////////////////////////
    /**
     * 初始化View
     */
    private void initView() {
        mProgressBar = findViewById(R.id.mProgressBar);

        mTitle = findViewById(R.id.titleTV);
        mTitle.setText("正在扫描设备，请稍后...");

        ll_pro=findViewById(R.id.ll_pro);

        mGridView =(GridViewWithHeaderAndFooter) findViewById(R.id.mediaGridView);
        View v_top = this.getLayoutInflater().inflate(R.layout.activity_video_menu_top,null);
        View v_bottom = this.getLayoutInflater().inflate(R.layout.activity_video_menu_bottom,null);

        mGridView.addHeaderView(v_top);
        mGridView.addFooterView(v_bottom);

        mGridView.setVerticalSpacing(20);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mGridAdapter.getItem(position).getPath();
                mGridAdapter.getItem(position).getPhoto();
                mGridAdapter.getItem(position).getLongphoto();
                Log.d(TAG, "path: " + mGridAdapter.getItem(position).getPath());
                Log.d(TAG, "photo: " + mGridAdapter.getItem(position).getPhoto());
                Log.d(TAG, "longphoto: " + mGridAdapter.getItem(position).getLongphoto());

                Intent intent = new Intent(TrainMenuActivity.this, TrainVideoAndPictureActivity.class);
                intent.putExtra("video", mGridAdapter.getItem(position).getPath());
                intent.putExtra("photo", mGridAdapter.getItem(position).getPhoto());
                intent.putExtra("longphoto", mGridAdapter.getItem(position).getLongphoto());
                intent.putExtra("pone", mGridAdapter.getItem(position).getPone());
                intent.putExtra("ptwo", mGridAdapter.getItem(position).getPtwo());
                intent.putExtra("pthree", mGridAdapter.getItem(position).getPthree());
                intent.putExtra("pfour", mGridAdapter.getItem(position).getPfour());
                intent.putExtra("pfive", mGridAdapter.getItem(position).getPfive());
                intent.putExtra("tone", mGridAdapter.getItem(position).getTone());

                //intent.putExtra("picture", mGridAdapter.getItem(position).)
                startActivity(intent);
            }
        });
    }

    /**
     * 新开一个线程用于执行后台的扫描任务
     */
    private void startScanTack() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mAnsyTask = new ScannerAnsyTask(getParent(),mProgressBar);
                    mAnsyTask.execute();
                    mChildList = mAnsyTask.get();
                    Log.d(TAG, "---mAnsyTask.getStatue()---" + mAnsyTask.getStatus());
                    if(mChildList != null && mChildList.size() > 0) {
                        mHandler.sendEmptyMessage(0x101);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 0x101) {
                mTitle.setText("产品介绍");
                mTitle.setVisibility(View.GONE);
                // 列表控件可见
                mGridView.setVisibility(View.VISIBLE);
                ll_pro.setVisibility(View.VISIBLE);
                // 填充适配器
                mGridAdapter = new TrainGridAdapter(getBaseContext(), mChildList);
                // 设置适配器
                mGridView.setAdapter(mGridAdapter);
            }
        }
    };
}
