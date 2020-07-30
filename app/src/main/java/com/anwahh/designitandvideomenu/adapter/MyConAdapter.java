package com.anwahh.designitandvideomenu.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public   class MyConAdapter extends PagerAdapter {
    /**
     * view的集合
     */
    private ArrayList<View> viewLists;

    /**
     * ViewPager的构造函数
     * @param viewLists 传入的View的集合，用于展示这些视图
     */
    public MyConAdapter(ArrayList<View> viewLists) {
        super();
        this.viewLists = viewLists;
    }

    /**
     * 获取要滑动的控件的数量
     */
    @Override
    public int getCount() {
        return viewLists.size();
    }

    /**
     * 判断显示的是否是同个控件，一般将两个参数相比较返回即可
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * PagerAdapter只缓存三个页面，如果滑动的页面超出了缓存的范围，
     * 就会调用这个方法，将页面销毁
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewLists.get(position));

        Log.i("mojin","=========================第:"+position);


        return viewLists.get(position);
    }

    /**
     * 当要显示的页面可以进行缓存的时候，会调用这个方法进行显示页面的初始化，
     * 我们将要显示的控件(viewLists.get(position)从集合中获取View)加入到ViewGroup中，然后作为返回值返回即可
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewLists.get(position));
    }
}
