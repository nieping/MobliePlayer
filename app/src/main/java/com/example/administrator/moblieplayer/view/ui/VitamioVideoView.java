package com.example.administrator.moblieplayer.view.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/6/6.
 */

public class VitamioVideoView extends io.vov.vitamio.widget.VideoView {
    public VitamioVideoView(Context context) {
        super(context);
    }

    public VitamioVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VitamioVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置VideoViewSize 方法
     * @param width
     * @param height
     */
    public void setVidoViewSize(int width,int height){
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = height;
        params.width = width;
        setLayoutParams(params);
        Log.e("VideoView========", "setFullScreen: " + "fullScreenW = " + width + "fullScreenH=" + height);


    }
}
