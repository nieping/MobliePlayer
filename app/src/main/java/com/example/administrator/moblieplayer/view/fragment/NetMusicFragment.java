package com.example.administrator.moblieplayer.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.moblieplayer.R;
import com.example.administrator.moblieplayer.view.base.BaseFragment;

/**
 * Created by Administrator on 2018/5/13.
 */

public class NetMusicFragment extends BaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_netmusic,container,false);
        return view;
    }
}
