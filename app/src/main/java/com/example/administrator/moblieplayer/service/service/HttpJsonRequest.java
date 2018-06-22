package com.example.administrator.moblieplayer.service.service;

import android.content.Context;

import com.example.administrator.moblieplayer.service.listener.BaseRequestListener;

/**
 * Created by Administrator on 2018/6/22.
 */

public class HttpJsonRequest<T> {
    public BaseRequestListener<T> mlistener;
    public Context mContext;

    public HttpJsonRequest(Context activity , BaseRequestListener<T> mListener){
        this.mContext = activity;
        this.mlistener = mListener;
    }

}
