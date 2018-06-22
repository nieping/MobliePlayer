package com.example.administrator.moblieplayer.service.listener;

import com.android.volley.Request;

/**
 * Created by Administrator on 2018/6/22.
 */

public abstract class DefaultRequestListener<T> implements BaseRequestListener<T> {
    @Override
    public void onRequestSuccess(String operationCode, T objdect) {

    }

    @Override
    public void onRequestFailure(String operationCode, Request object) {

    }

    @Override
    public void onPreRequest(String operationCode) {

    }
}
