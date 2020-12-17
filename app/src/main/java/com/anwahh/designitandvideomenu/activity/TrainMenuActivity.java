package com.anwahh.designitandvideomenu.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anwahh.designitandvideomenu.R;
import com.anwahh.designitandvideomenu.ScannerAnsyTask;
import com.anwahh.designitandvideomenu.adapter.TrainGridAdapter;
import com.anwahh.designitandvideomenu.bean.MediaBean;
import com.anwahh.designitandvideomenu.commonUtils.BitmapUtils;
import com.anwahh.designitandvideomenu.commonUtils.FileUtils;
import com.anwahh.designitandvideomenu.view.GridViewWithHeaderAndFooter;

import java.io.File;
import java.util.List;

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
    private ImageView img_top,img_botton;

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

        File[] projectSet=getFileList(FileUtils.trainSet());
        img_top=v_top.findViewById(R.id.img_top);
        img_botton=v_bottom.findViewById(R.id.img_top);
        Bitmap bitmap = BitmapUtils.getSmallBitmap(projectSet[0].getPath(),
                this.getResources().getDisplayMetrics().heightPixels,
                this.getResources().getDisplayMetrics().widthPixels);
        img_top.setImageBitmap(bitmap);


        Bitmap bitmap1 = BitmapUtils.getSmallBitmap(projectSet[1].getPath(),
                this.getResources().getDisplayMetrics().heightPixels,
                this.getResources().getDisplayMetrics().widthPixels);
        img_botton.setImageBitmap(bitmap1);




        mGridView.addHeaderView(v_top);
        mGridView.addFooterView(v_bottom);

        mGridView.setVerticalSpacing(20);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(TrainMenuActivity.this, TrainVideoAndPictureActivity.class);

                intent.putExtra("position", position+"");
                startActivity(intent);
            }
        });
    }

    /**
     * 新开一个线程用于执行后台的扫描任务
     */
    private void startScanTack() {

        final File[] projectPicFiles = getFileList(FileUtils.DirPathForTrainPic());


        mTitle.setText("产品介绍");
        mTitle.setVisibility(View.GONE);
        // 列表控件可见
        mGridView.setVisibility(View.VISIBLE);
        ll_pro.setVisibility(View.VISIBLE);
        // 填充适配器
        mGridAdapter = new TrainGridAdapter(getBaseContext(), projectPicFiles);
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
}
