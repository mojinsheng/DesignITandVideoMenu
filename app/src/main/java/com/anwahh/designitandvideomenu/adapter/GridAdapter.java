package com.anwahh.designitandvideomenu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.anwahh.designitandvideomenu.R;
import com.anwahh.designitandvideomenu.commonUtils.BitmapUtils;
import com.anwahh.designitandvideomenu.view.ThumbListLayout;
import com.anwahh.designitandvideomenu.viewHolder.ViewHolder;

import java.io.File;

/**
 * @describe 媒体文件适配器类
 * @author Anwahh
 * @date 2020-04-10
 */
public class GridAdapter extends BaseAdapter {


    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 数据集合
     */
    private File[] mChildList;

    public GridAdapter(Context mContext, File[] mChildList) {
        super();
        this.mContext = mContext;
        this.mChildList = mChildList;



    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mChildList.length;
    }

    @Override
    public String getItem(int position) {
        // TODO Auto-generated method stub
        return mChildList[position].getPath();
    }

    @Override
    public long getItemId(int id) {
        // TODO Auto-generated method stub
        return id;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ThumbListLayout thumbListLayout =new ThumbListLayout(mContext);

        View view;
        if (convertView == null)
        {
            view  = thumbListLayout;
        } else
        {
            view = convertView;
        }

        Bitmap bitmap = BitmapUtils.getSmallBitmap(getItem(position),
                mContext.getResources().getDisplayMetrics().heightPixels,
                mContext.getResources().getDisplayMetrics().widthPixels);
        ImageView img = thumbListLayout.getImageView();
       /* TextView tv = ViewHolder.get(view,R.id.tv_icon);*/
        img.setImageBitmap(bitmap);
      /*  tv.setText(getItem(position).getName());*/
        return view;
    }
}
