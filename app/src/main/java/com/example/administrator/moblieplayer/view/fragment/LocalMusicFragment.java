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
import com.example.administrator.moblieplayer.adapter.MusicAdapter;
import com.example.administrator.moblieplayer.baen.MusicBaen;
import com.example.administrator.moblieplayer.utli.FileManager;
import com.example.administrator.moblieplayer.view.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/5/13.
 */

public class LocalMusicFragment extends BaseFragment {
    @BindView(R.id.lv_music)
    ListView lvMusic;
    @BindView(R.id.tv_nullmusic)
    TextView tvNullMusic;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    private String TAG = LocalMusicFragment.class.getSimpleName();
    private List<MusicBaen> musicBaenList;
    private Context mContext;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_localmusic,container,false);
        mContext = getActivity();
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                getMusic();
            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
            }
        });
        getMusic();
        if (musicBaenList.isEmpty()){
            tvNullMusic.setVisibility(View.VISIBLE);
        }else {
            tvNullMusic.setVisibility(View.GONE);
            MusicAdapter adapter =  null;
            if (adapter == null){
                adapter = new MusicAdapter(mContext,musicBaenList);
            }else {

            }
            lvMusic.setAdapter(adapter);
        }

    }

    private void getMusic() {
        final FileManager fileManager = new FileManager(mContext);
        musicBaenList =fileManager.getMusic(mContext);
    }
}
