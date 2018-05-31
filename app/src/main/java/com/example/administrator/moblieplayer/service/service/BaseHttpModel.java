package com.example.administrator.moblieplayer.service.service;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2018/5/29.
 */

public class BaseHttpModel {
    /**
     * 打印返回数据
     * @return
     */
    @Override
    public String toString() {
        return new Gson().toString();
    }
}
