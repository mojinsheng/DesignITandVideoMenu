package com.anwahh.designitandvideomenu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.anwahh.designitandvideomenu.utils.ScreenUtils;

public class ThumbListLayout extends LinearLayout {
    private Context mContext;
    private ImageView imageView;
    private int mHeight;
    LayoutParams params;

    public ThumbListLayout(Context context) {
        super(context);
        init(context);
    }

    public ThumbListLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ThumbListLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        mHeight= ScreenUtils.getHeightPixels(context);
        imageView=new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        params=new LayoutParams(LayoutParams.MATCH_PARENT,(int)(mHeight*0.3666666666666666));

        this.addView(imageView,params);


    }

    public ImageView getImageView() {
        return imageView;
    }


}
