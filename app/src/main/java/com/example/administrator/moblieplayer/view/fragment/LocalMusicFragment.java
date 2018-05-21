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
    private String TAG = LocalMusicFragment.class.getSimpleName();
    private List<MusicBaen> musicBaenList;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_localmusic,container,false);
        mContext = getActivity();
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView() {
        final  FileManager fileManager = new FileManager(mContext);
        musicBaenList =fileManager.getMusic(mContext);
        if (musicBaenList.isEmpty()){
            tvNullMusic.setVisibility(View.VISIBLE);
        }else {
            tvNullMusic.setVisibility(View.GONE);
            MusicAdapter adapter =  null;
            if (adapter == null){
                adapter = new MusicAdapter(mContext,musicBaenList);
            }
            lvMusic.setAdapter(adapter);
        }

    }
}
