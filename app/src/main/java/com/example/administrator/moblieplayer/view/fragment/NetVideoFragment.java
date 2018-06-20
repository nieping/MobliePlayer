package com.example.administrator.moblieplayer.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.moblieplayer.R;
import com.example.administrator.moblieplayer.baen.NetVideoBean;
import com.example.administrator.moblieplayer.view.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/13.
 */

public class NetVideoFragment extends BaseFragment {
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.lv_netvideo)
    ListView lvNetVideo;
    @BindView(R.id.tv_nullnetvideo)
    TextView tvNullNetVideo;
    @BindView(R.id.pb_bar)
    ProgressBar pbBar;

    private Context mContext;
    private ArrayList<NetVideoBean> videoItem = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_netvideo,container,false);
        ButterKnife.bind(this,view);
        mContext = getActivity().getApplicationContext();
        return view;
    }

    private void initView() {
        pbBar.setVisibility(View.VISIBLE);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
                initData();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
                initData();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
        initData();
    }

    private void initData() {

    }
}
