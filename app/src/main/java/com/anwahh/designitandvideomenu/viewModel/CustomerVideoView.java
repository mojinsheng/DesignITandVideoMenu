package com.anwahh.designitandvideomenu.viewModel;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * @describe 自定义视频控件
 * @author Anwahh
 * @date 2020-04-10
 */
public class CustomerVideoView extends VideoView {

    public CustomerVideoView(Context context) {
        super(context);
    }

    public CustomerVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomerVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getDefaultSize(0,widthMeasureSpec);
        int height = getDefaultSize(0,heightMeasureSpec);

        setMeasuredDimension(width, heightMeasureSpec);
    }
}
