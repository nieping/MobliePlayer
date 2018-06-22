package com.example.administrator.moblieplayer.service.listener;

import com.android.volley.Request;

/**
 * Created by Administrator on 2018/6/22.
 */

public interface BaseRequestListener<T> {
    /**
     * 请求预处理
     * @param operationCode
     */
    void onPreRequest(String operationCode);

    /**
     * 请求成功
     * @param operationCode
     * @param objdect
     */
    void onRequestSuccess(String operationCode,T objdect);

    /**
     * 请求失败
     * @param operationCode
     * @param object
     */
    void onRequestFailure(String operationCode, Request object);
}
