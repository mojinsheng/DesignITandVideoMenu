package com.anwahh.designitandvideomenu.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.anwahh.designitandvideomenu.R;
import com.anwahh.designitandvideomenu.bean.MediaBean;
import com.anwahh.designitandvideomenu.viewHolder.ViewHolder;

import java.util.List;

/**
 * @describe 媒体文件适配器类
 * @author Anwahh
 * @date 2020-04-10
 */
public class TrainGridAdapter extends BaseAdapter {


    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 数据集合
     */
    private List<MediaBean> mChildList;

    public TrainGridAdapter(Context mContext, List<MediaBean> mChildList) {
        super();
        this.mContext = mContext;
        this.mChildList = mChildList;


        MediaBean m8=new MediaBean();
        m8.setTrainId("/storage/emulated/0/Pictures/Training/img8.jpg");
        m8.setTone("/storage/emulated/0/Pictures/trainVideo/pro8/P1");
        this.mChildList.add(m8);


        MediaBean m9=new MediaBean();
        m9.setTrainId("/storage/emulated/0/Pictures/Training/img9.jpg");
        m9.setTone("/storage/emulated/0/Pictures/trainVideo/pro9/P1");
        this.mChildList.add(m9);


        MediaBean m10=new MediaBean();
        m10.setTrainId("/storage/emulated/0/Pictures/Training/img10.jpg");
        m10.setTone("/storage/emulated/0/Pictures/trainVideo/pro10/P1");
        this.mChildList.add(m10);


        MediaBean m11=new MediaBean();
        m11.setTrainId("/storage/emulated/0/Pictures/Training/img11.jpg");
        m11.setTone("/storage/emulated/0/Pictures/trainVideo/pro11/P1");
        this.mChildList.add(m11);

        MediaBean m12=new MediaBean();
        m12.setTrainId("/storage/emulated/0/Pictures/Training/img12.jpg");
        m12.setTone("/storage/emulated/0/Pictures/trainVideo/pro12/P1");
        this.mChildList.add(m12);






    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mChildList.size();
    }

    @Override
    public MediaBean getItem(int position) {
        // TODO Auto-generated method stub
        return mChildList.get(position);
    }

    @Override
    public long getItemId(int id) {
        // TODO Auto-generated method stub
        return id;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        view  = LayoutInflater.from(mContext).inflate(R.layout.thumb_item_list, null);
        ImageView img = ViewHolder.get(view, R.id.iv_icon);
       /* TextView tv = ViewHolder.get(view,R.id.tv_icon);*/
        img.setImageDrawable(Drawable.createFromPath(getItem(position).getTrainId()));
      /*  tv.setText(getItem(position).getName());*/
        return view;
    }
}
