package com.example.administrator.moblieplayer.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.moblieplayer.R;
import com.example.administrator.moblieplayer.adapter.VideoAdapter;
import com.example.administrator.moblieplayer.baen.VideoBaen;
import com.example.administrator.moblieplayer.utli.FileManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2018/5/13.
 */

public class LocalVideoFragment extends Fragment {
    @BindView(R.id.lv_video)
    ListView videoListView;
    @BindView(R.id.tv_nullvideo)
    TextView nullVideo;

    @BindView(R.id.refreshlayout)
    SmartRefreshLayout smartRefreshLayout;

    private Context mContext;
    private VideoAdapter adapter = null;
    private String TAG = LocalVideoFragment.class.getSimpleName();
    private List<VideoBaen> videoBaenList = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "====================onCreateView: ");
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
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                getVideo();
                Log.e(TAG, "=============onRefresh:============== " );
            }
        });
        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
                Log.e(TAG, "==============onLoadmore: " );
            }
        });

        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(mContext,"Sd卡不存在",Toast.LENGTH_SHORT).show();
        }else {
            getVideo();
            Log.e(TAG, "initView: video ====" + videoBaenList);
            if (videoBaenList.isEmpty()){
                nullVideo.setVisibility(View.VISIBLE);
            }else {
                nullVideo.setVisibility(View.GONE);
                if (adapter == null) {
                    adapter = new VideoAdapter(mContext, videoBaenList);
                }
                videoListView.setAdapter(adapter);
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,true);
    }

    private void getVideo() {

        File sdCard_filedir = Environment.getExternalStorageDirectory();
        long totalSp = sdCard_filedir.getTotalSpace();
        String total = android.text.format.Formatter.formatFileSize(mContext,totalSp);
        Toast.makeText(mContext,"sdcadr======" +total,Toast.LENGTH_SHORT).show();
        final FileManager fileManager = new FileManager(mContext);
        videoBaenList = fileManager.getVideo(mContext);
    }
}
