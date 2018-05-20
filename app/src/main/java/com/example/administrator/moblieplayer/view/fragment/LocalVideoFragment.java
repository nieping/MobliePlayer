package com.example.administrator.moblieplayer.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.moblieplayer.R;
import com.example.administrator.moblieplayer.adapter.VideoAdapter;
import com.example.administrator.moblieplayer.baen.VideoBaen;
import com.example.administrator.moblieplayer.utli.FileManager;

import java.util.List;

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

    private Context mContext;
    private VideoAdapter adapter = null;
    private String TAG = LocalVideoFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e(TAG, "====================onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_localvideo, container, false);
        mContext = getActivity();
        ButterKnife.bind(this, view);
        initView();
        return view;

    }

    private void initView() {
        List<VideoBaen> videoBaenList = null;
        FileManager fileManager = FileManager.getInstance(mContext);
        videoBaenList = fileManager.getVideo();
        Log.e(TAG, "initView: video ====" + videoBaenList);
        if (videoBaenList.isEmpty()){
            nullVideo.setVisibility(View.VISIBLE);
        }
        if (adapter == null) {
            adapter = new VideoAdapter(mContext, videoBaenList);
        }
        videoListView.setAdapter(adapter);
    }
}
