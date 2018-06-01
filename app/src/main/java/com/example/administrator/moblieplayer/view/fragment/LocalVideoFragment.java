package com.example.administrator.moblieplayer.view.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.moblieplayer.R;
import com.example.administrator.moblieplayer.adapter.VideoAdapter;
import com.example.administrator.moblieplayer.baen.MediaBaen;
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

public class LocalVideoFragment extends Fragment {
    @BindView(R.id.lv_video)
    ListView videoListView;
    @BindView(R.id.tv_nullvideo)
    TextView nullVideo;
    @BindView(R.id.pb)
    ProgressBar pd;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;

    private Context mContext;
    private VideoAdapter adapter = null;
    private String TAG = LocalVideoFragment.class.getSimpleName();
    private ArrayList<MediaBaen> videoBaenList;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    pd.setVisibility(View.GONE);
                    if (videoBaenList != null && videoBaenList.size() > 0){
                        nullVideo.setVisibility(View.GONE);

                        if (adapter == null){
                            adapter = new VideoAdapter(mContext,videoBaenList);
                            videoListView.setAdapter(adapter);
                        }else {
                            adapter.notifyDataSetChanged(videoBaenList);
                        }
                    }else {
                        nullVideo.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_localvideo, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {

        pd.setVisibility(View.VISIBLE);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                getVideo();
            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
                getVideo();
            }
        });

        getVideo();


    }



    public void getVideo() {
        videoBaenList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                isGrantExternalRW((Activity)mContext);
                Cursor cursor = null;
                try {
                    ContentResolver mContentResolver = mContext.getContentResolver();
                    cursor = mContentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
                    while (cursor.moveToNext()) {
                        String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                        String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                        String size = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
                        int data = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                        String artst = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ARTIST));
                        MediaBaen videoBaen = new MediaBaen(name, path, size, data, artst);
                        videoBaenList.add(videoBaen);
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
    public static boolean isGrantExternalRW(Activity activity) {
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(this);
    }
}
