package com.example.administrator.moblieplayer.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.moblieplayer.R;
import com.example.administrator.moblieplayer.view.adapter.VideoAdapter;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/5/13.
 */

public class LocalVideoFragment extends Fragment {
    @BindView(R.id.lv_video_list)
    ListView lvVideoList;
    private Context mContext;
    private VideoAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_localvideo,container,false);
        initView();
        return view;


    }

    private void initView() {
        lvVideoList.setAdapter(adapter);
    }
}
