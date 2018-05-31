package com.example.administrator.moblieplayer.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.administrator.moblieplayer.R;
import com.example.administrator.moblieplayer.view.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TesActivity extends BaseActivity {
    @BindView(R.id.video)
    VideoView videoView;

    private String url2 = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tes);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Uri uri = Uri.parse(url2);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(uri);
        videoView.requestFocus();

    }
}
