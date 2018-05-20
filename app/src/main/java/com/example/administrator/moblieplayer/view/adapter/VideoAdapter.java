package com.example.administrator.moblieplayer.view.adapter;

import android.content.Context;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/16.
 */

public class VideoAdapter extends BaseAdapter{
    private Context context;
    private List videoList =  new ArrayList();

    public VideoAdapter(Context context, List list){
        this.context = context;
        this.videoList = list;
    }
    @Override
    public int getCount() {
        return videoList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MediaStore.Video video;
        return null;
    }

}
