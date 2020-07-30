package com.anwahh.designitandvideomenu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.anwahh.designitandvideomenu.R;
import com.anwahh.designitandvideomenu.db.DBOpenHelper;
import com.anwahh.designitandvideomenu.db.DBService;
import com.anwahh.designitandvideomenu.db.ShowStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class SetUpActivity extends AppCompatActivity {

    /**
     * 复选框按钮
     */
    private CheckBox cb_advert,cb_product,cb_manoeuvre,cb_live,cb_train;
    /**
     * 按钮
     */
    private Button btn_back,btn_finish;
    /**
     * 数据库相关
     */
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        // 初始化View
        initView();
        // 初始化数据
        initDate();

        new Thread(runnable).start();

        BottomBar bottomBar = findViewById(R.id.bottom_bar);
        bottomBar.setContainer(R.id.bottom_bar)
                .setTitleBeforeAndAfterColor("#999999","#ff5d5e")
                .addItem(SetUpActivity.class,
                        "设置",
                        R.drawable.ic_launcher_background,
                        R.drawable.ic_launcher_background)
                .addItem(AddActivity.class,
                        "添加",
                        R.drawable.ic_launcher_background,
                        R.drawable.ic_launcher_background);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            List<ShowStatus> list = DBService.getDbService().getShowStatusData();
            StringBuilder s = new StringBuilder();
            for (int i=0; i< list.size(); i++) {
                s.append(list.get(i).getStatus());
            }
            Log.d("1111",s.toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (list.get(0).getStatus().equals("1")) {
                        cb_advert.setChecked(true);
                    } else {
                        cb_advert.setChecked(false);
                    }

                    if (list.get(1).getStatus().equals("1")) {
                        cb_product.setChecked(true);
                    } else {
                        cb_product.setChecked(false);
                    }

                    if (list.get(2).getStatus().equals("1")) {
                        cb_manoeuvre.setChecked(true);
                    } else {
                        cb_manoeuvre.setChecked(false);
                    }

                    if (list.get(3).getStatus().equals("1")) {
                        cb_live.setChecked(true);
                    } else {
                        cb_live.setChecked(false);
                    }

                    if (list.get(4).getStatus().equals("1")) {
                        cb_train.setChecked(true);
                    } else {
                        cb_train.setChecked(false);
                    }
                }
            });
        }
    };


    /**
     * 初始化View
     */
    private void initView() {
        // 返回按钮
        btn_back = findViewById(R.id.back_btn);
        // 完成按钮
        btn_finish = findViewById(R.id.finish_btn);
        // 复选框按钮
        cb_advert = findViewById(R.id.advert);
        cb_product = findViewById(R.id.product);
        cb_manoeuvre = findViewById(R.id.manoeuvre);
        cb_live = findViewById(R.id.live);
        cb_train = findViewById(R.id.train);
    }

    /**
     * 初始化数据
     */
    private void initDate() {

        //connMySql();

        cb_advert.setOnCheckedChangeListener(checkBox_listener);
        cb_product.setOnCheckedChangeListener(checkBox_listener);
        cb_manoeuvre.setOnCheckedChangeListener(checkBox_listener);
        cb_live.setOnCheckedChangeListener(checkBox_listener);
        cb_train.setOnCheckedChangeListener(checkBox_listener);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                DBOpenHelper.clossAll(conn,ps,rs);
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String str = "";
                        if (cb_advert.isChecked()){ // 选中形象
                            str += cb_advert.getText().toString();
                            DBService.getDbService().updateUserData("1",cb_advert.getTag().toString());
                        }else {
                            DBService.getDbService().updateUserData("0",cb_advert.getTag().toString());
                        }

                        if (cb_product.isChecked()){ // 选中产品
                            str += cb_product.getText().toString();
                            DBService.getDbService().updateUserData("1",cb_product.getTag().toString());
                        } else {
                            DBService.getDbService().updateUserData("0",cb_product.getTag().toString());
                        }

                        if (cb_manoeuvre.isChecked()){ // 选中活动
                            str += cb_manoeuvre.getText().toString();
                            DBService.getDbService().updateUserData("1",cb_manoeuvre.getTag().toString());
                        } else {
                            DBService.getDbService().updateUserData("0",cb_manoeuvre.getTag().toString());
                        }

                        if (cb_live.isChecked()) { // 选中直播
                            str += cb_live.getText().toString();
                            DBService.getDbService().updateUserData("1",cb_live.getTag().toString());
                        } else {
                            DBService.getDbService().updateUserData("0",cb_live.getTag().toString());
                        }

                        if (cb_train.isChecked()){ // 选中培训
                            str += cb_train.getText().toString();
                            DBService.getDbService().updateUserData("1",cb_train.getTag().toString());
                        }  else {
                            DBService.getDbService().updateUserData("0",cb_train.getTag().toString());
                        }
                    }
                }).start();


                Intent intent = new Intent(SetUpActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
    }

    private CompoundButton.OnCheckedChangeListener checkBox_listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if (isChecked) { //判断复选按钮是否被选中
                Log.d("复选按钮", "选中了[" + compoundButton.getText().toString() + "]");
            }
        }
    };


}
