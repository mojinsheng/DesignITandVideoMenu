package com.anwahh.designitandvideomenu.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.anwahh.designitandvideomenu.R;
import com.anwahh.designitandvideomenu.ScannerAnsyTask;
import com.anwahh.designitandvideomenu.adapter.GridAdapter;
import com.anwahh.designitandvideomenu.bean.MediaBean;
import com.anwahh.designitandvideomenu.commonUtils.FileUtils;
import com.anwahh.designitandvideomenu.view.GridViewWithHeaderAndFooter;

import java.io.File;
import java.util.List;

/**
 * @describe 视频缩略图类
 * @author Anwahh
 * @date 2020-04-10
 */
public class VideoMenuActivity extends BaseActivity {

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
    private GridAdapter mGridAdapter;
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
        setContentView(R.layout.activity_video_menu);
        ((ActivityManager)getSystemService(Context.ACTIVITY_SERVICE)).getLargeMemoryClass();

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

       // mGridView.setVerticalSpacing(20);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(VideoMenuActivity.this,"你点击的是"+position,Toast.LENGTH_LONG).show();
               Intent intent = new Intent(VideoMenuActivity.this, ProVideoAndPictureActivity.class);
                intent.putExtra("position", position+"");
//                intent.putExtra("ptwo", mGridAdapter.getItem(position).getPtwo());
//                intent.putExtra("pthree", mGridAdapter.getItem(position).getPthree());
//                intent.putExtra("pfour", mGridAdapter.getItem(position).getPfour());
//                intent.putExtra("pfive", mGridAdapter.getItem(position).getPfive());
                startActivity(intent);
            }
        });
    }

    /**
     * 新开一个线程用于执行后台的扫描任务
     */
    private void startScanTack() {


        final File[] projectPicFiles = getFileList(FileUtils.DirPathForProjectPic());

        mTitle.setText("产品介绍");
        mTitle.setVisibility(View.GONE);
        // 列表控件可见
        mGridView.setVisibility(View.VISIBLE);
        ll_pro.setVisibility(View.VISIBLE);
        // 填充适配器
        mGridAdapter = new GridAdapter(getBaseContext(), projectPicFiles);
        // 设置适配器
        mGridView.setAdapter(mGridAdapter);


    }
    /**
     * 获取某个路径下的文件列表
     * @param path 文件路径
     * @return 文件列表 File[] files
     */
    public static File[] getFileList(String path) {
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for(int i=0;i<files.length;i++){
                String fileName= files[i].getName();
                if(fileName.equals("Thumbs.db")){
                    files[i].delete();
                }
            }
            File[] filess = file.listFiles();
            if (filess != null) {
                return filess;
            } else {
                return null;
            }
        } else {
            return null;
        }
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
//                mGridAdapter = new GridAdapter(getBaseContext(), mChildList);
                // 设置适配器
                mGridView.setAdapter(mGridAdapter);
            }
        }
    };
}
