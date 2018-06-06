package com.example.administrator.moblieplayer.view.ui;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2018/6/6.
 */

public class VideoView extends android.widget.VideoView {
    public VideoView(Context context) {
        super(context);
    }

    public VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置VideoViewSize 方法
     * @param w
     * @param h
     */
    public void setVidoViewSize(int w,int h){

        setMeasuredDimension(w,h);

    }
}
