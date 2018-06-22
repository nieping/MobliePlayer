package com.example.administrator.moblieplayer.service.service;

/**
 * Created by Administrator on 2018/6/22.
 */

public class ResponseInfo<T> {
   private T data ;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
