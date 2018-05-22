package com.example.administrator.moblieplayer.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.moblieplayer.R;
import com.example.administrator.moblieplayer.view.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/13.
 */

public class NetMusicFragment extends BaseFragment {
    @BindView(R.id.lv_netmusic)
    ListView lvNetMusic;
    @BindView(R.id.tv_nullnetmusic)
    TextView tvNullNetMusic;

    private Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_netmusic,container,false);
        ButterKnife.bind(this,view);
        mContext = getActivity();
        initView();
        return view;
    }

    private void initView() {

    }
}
