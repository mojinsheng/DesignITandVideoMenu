package com.anwahh.designitandvideomenu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.anwahh.designitandvideomenu.R;
import com.anwahh.designitandvideomenu.bean.MediaBean;
import com.anwahh.designitandvideomenu.commonUtils.BitmapUtils;
import com.anwahh.designitandvideomenu.viewHolder.ViewHolder;

import java.util.List;

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
    private List<MediaBean> mChildList;

    public GridAdapter(Context mContext, List<MediaBean> mChildList) {
        super();
        this.mContext = mContext;
        this.mChildList = mChildList;


        /**
         * 特殊处理文件
         */
        MediaBean m8=new MediaBean();
        m8.setPoster("/storage/emulated/0/Pictures/Thumb/img8.jpg");
        m8.setPone("/storage/emulated/0/Pictures/projectVideo/pro8/P1");
        m8.setPtwo("/storage/emulated/0/Pictures/projectVideo/pro8/P2");
        m8.setPthree("/storage/emulated/0/Pictures/projectVideo/pro8/P3");
        m8.setPfour("/storage/emulated/0/Pictures/projectVideo/pro8/P4");
        m8.setPfive("/storage/emulated/0/Pictures/projectVideo/pro8/P5");
        this.mChildList.add(m8);


        MediaBean m9=new MediaBean();
        m9.setPoster("/storage/emulated/0/Pictures/Thumb/img9.jpg");
        m9.setPone("/storage/emulated/0/Pictures/projectVideo/pro9/P1");
        m9.setPtwo("/storage/emulated/0/Pictures/projectVideo/pro9/P2");
        m9.setPthree("/storage/emulated/0/Pictures/projectVideo/pro9/P3");
        m9.setPfour("/storage/emulated/0/Pictures/projectVideo/pro9/P4");
        m9.setPfive("/storage/emulated/0/Pictures/projectVideo/pro9/P5");
        this.mChildList.add(m9);

        MediaBean m10=new MediaBean();
        m10.setPoster("/storage/emulated/0/Pictures/Thumb/img10.jpg");
        m10.setPone("/storage/emulated/0/Pictures/projectVideo/pro10/P1");
        m10.setPtwo("/storage/emulated/0/Pictures/projectVideo/pro10/P2");
        m10.setPthree("/storage/emulated/0/Pictures/projectVideo/pro10/P3");
        m10.setPfour("/storage/emulated/0/Pictures/projectVideo/pro10/P4");
        m10.setPfive("/storage/emulated/0/Pictures/projectVideo/pro10/P5");
        this.mChildList.add(m10);

        MediaBean m11=new MediaBean();
        m11.setPoster("/storage/emulated/0/Pictures/Thumb/img11.jpg");
        m11.setPone("/storage/emulated/0/Pictures/projectVideo/pro11/P1");
        m11.setPtwo("/storage/emulated/0/Pictures/projectVideo/pro11/P2");
        m11.setPthree("/storage/emulated/0/Pictures/projectVideo/pro11/P3");
        m11.setPfour("/storage/emulated/0/Pictures/projectVideo/pro11/P4");
        m11.setPfive("/storage/emulated/0/Pictures/projectVideo/pro11/P5");
        this.mChildList.add(m11);


        MediaBean m12=new MediaBean();
        m12.setPoster("/storage/emulated/0/Pictures/Thumb/img12.jpg");
        m12.setPone("/storage/emulated/0/Pictures/projectVideo/pro12/P1");
        m12.setPtwo("/storage/emulated/0/Pictures/projectVideo/pro12/P2");
        m12.setPthree("/storage/emulated/0/Pictures/projectVideo/pro12/P3");
        m12.setPfour("/storage/emulated/0/Pictures/projectVideo/pro12/P4");
        m12.setPfive("/storage/emulated/0/Pictures/projectVideo/pro12/P5");
        this.mChildList.add(m12);

        MediaBean m13=new MediaBean();
        m13.setPoster("/storage/emulated/0/Pictures/Thumb/img13.jpg");
        m13.setPone("/storage/emulated/0/Pictures/projectVideo/pro13/P1");
        m13.setPtwo("/storage/emulated/0/Pictures/projectVideo/pro13/P2");
        m13.setPthree("/storage/emulated/0/Pictures/projectVideo/pro13/P3");
        m13.setPfour("/storage/emulated/0/Pictures/projectVideo/pro13/P4");
        m13.setPfive("/storage/emulated/0/Pictures/projectVideo/pro13/P5");
        this.mChildList.add(m13);


        MediaBean m14=new MediaBean();
        m14.setPoster("/storage/emulated/0/Pictures/Thumb/img14.jpg");
        m14.setPone("/storage/emulated/0/Pictures/projectVideo/pro14/P1");
        m14.setPtwo("/storage/emulated/0/Pictures/projectVideo/pro14/P2");
        m14.setPthree("/storage/emulated/0/Pictures/projectVideo/pro14/P3");
        m14.setPfour("/storage/emulated/0/Pictures/projectVideo/pro14/P4");
        m14.setPfive("/storage/emulated/0/Pictures/projectVideo/pro14/P5");
        this.mChildList.add(m14);

        MediaBean m15=new MediaBean();
        m15.setPoster("/storage/emulated/0/Pictures/Thumb/img15.jpg");
        m15.setPone("/storage/emulated/0/Pictures/projectVideo/pro15/P1");
        m15.setPtwo("/storage/emulated/0/Pictures/projectVideo/pro15/P2");
        m15.setPthree("/storage/emulated/0/Pictures/projectVideo/pro15/P3");
        m15.setPfour("/storage/emulated/0/Pictures/projectVideo/pro15/P4");
        m15.setPfive("/storage/emulated/0/Pictures/projectVideo/pro15/P5");
        this.mChildList.add(m15);

        MediaBean m16=new MediaBean();
        m16.setPoster("/storage/emulated/0/Pictures/Thumb/img16.jpg");
        m16.setPone("/storage/emulated/0/Pictures/projectVideo/pro16/P1");
        m16.setPtwo("/storage/emulated/0/Pictures/projectVideo/pro16/P2");
        m16.setPthree("/storage/emulated/0/Pictures/projectVideo/pro16/P3");
        m16.setPfour("/storage/emulated/0/Pictures/projectVideo/pro16/P4");
        m16.setPfive("/storage/emulated/0/Pictures/projectVideo/pro16/P5");
        this.mChildList.add(m16);

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
    public View getView(final int position, View convertView, ViewGroup parent) {


        View view;
        if (convertView == null)
        {
            view  = LayoutInflater.from(mContext).inflate(R.layout.thumb_item_list, null);
        } else
        {
            view = convertView;
        }

        Bitmap bitmap = BitmapUtils.getSmallBitmap(getItem(position).getPoster(),
                mContext.getResources().getDisplayMetrics().heightPixels,
                mContext.getResources().getDisplayMetrics().widthPixels);
        ImageView img = ViewHolder.get(view, R.id.iv_icon);
       /* TextView tv = ViewHolder.get(view,R.id.tv_icon);*/
        img.setImageBitmap(bitmap);
      /*  tv.setText(getItem(position).getName());*/
        return view;
    }
}
