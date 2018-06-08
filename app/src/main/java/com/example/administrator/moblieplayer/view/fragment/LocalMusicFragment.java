package com.example.administrator.moblieplayer.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.moblieplayer.R;
import com.example.administrator.moblieplayer.adapter.MusicAdapter;
import com.example.administrator.moblieplayer.baen.MediaBaen;
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

public class LocalMusicFragment extends BaseFragment {
    @BindView(R.id.lv_music)
    ListView lvMusic;
    @BindView(R.id.tv_nullmusic)
    TextView tvNullMusic;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;
    private String TAG = LocalMusicFragment.class.getSimpleName();
    private ArrayList<MediaBaen> musicBaenList;
    private Context mContext;
    private MusicAdapter adapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    if (musicBaenList != null && musicBaenList.size() > 0){
                        tvNullMusic.setVisibility(View.GONE);
                        if (adapter ==  null){
                            adapter = new MusicAdapter(mContext,musicBaenList);
                            lvMusic.setAdapter(adapter);
                        }else {
                            adapter.notifyDataSetChanged(musicBaenList);
                        }
                    }else {
                        tvNullMusic.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    };


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
                getMusic();
            }
        });
        getMusic();

    }

    private void getMusic() {
        musicBaenList = new ArrayList<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                isGrantExternalRW(getActivity());
                Cursor cursor = null;
                try {
                    ContentResolver mContentResolver = mContext.getContentResolver();
                    cursor = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
                    while (cursor.moveToNext()) {
                        String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                        String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                        String size = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                        int data = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                        String artst = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                        MediaBaen videoBaen = new MediaBaen(name, path, size, data, artst);
                        musicBaenList.add(videoBaen);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (cursor != null) {
                        cursor.close();
                        handler.sendEmptyMessage(1);
                    }
                }
            }
        }).start();

    }

    public  boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);

            return false;
        }

        return true;
    }
}
