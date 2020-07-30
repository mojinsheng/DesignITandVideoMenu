package com.anwahh.designitandvideomenu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.anwahh.designitandvideomenu.commonUtils.BitmapUtils;

import java.util.ArrayList;

/**
 * @describe 图片轮播适配器类
 * @author Anwahh
 * @date 2020-04-10
 */
public class MyProjectAdapter extends PagerAdapter {

    private ArrayList<String> image;
    private Context context;

    public MyProjectAdapter(ArrayList<String> image, Context context) {
        this.image = image;
        this.context = context;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int newPosition = position % image.size();
        Bitmap bitmap = BitmapUtils.getSmallBitmap(image.get(newPosition),
                context.getResources().getDisplayMetrics().heightPixels,
                context.getResources().getDisplayMetrics().widthPixels);
        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(bitmap);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
