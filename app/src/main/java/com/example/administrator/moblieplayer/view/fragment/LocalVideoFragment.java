package com.example.administrator.moblieplayer.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
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
import java.io.File;
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
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(mContext,"Sd卡不存在",Toast.LENGTH_SHORT).show();
        }else {
            File sdCard_filedir = Environment.getExternalStorageDirectory();
            long totalSp = sdCard_filedir.getTotalSpace();
            String total = android.text.format.Formatter.formatFileSize(mContext,totalSp);
            Toast.makeText(mContext,"sdcadr======" +total,Toast.LENGTH_SHORT).show();
            final FileManager fileManager = new FileManager(mContext);
            videoBaenList = fileManager.getVideo(mContext);
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
}
