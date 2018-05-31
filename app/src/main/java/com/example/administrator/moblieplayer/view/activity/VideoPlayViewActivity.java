package com.example.administrator.moblieplayer.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.administrator.moblieplayer.R;
import com.example.administrator.moblieplayer.baen.MediaBaen;

import butterknife.BindView;
import butterknife.ButterKnife;



public class VideoPlayViewActivity extends Activity  {
    @BindView(R.id.video)
    VideoView videoView;


    private MediaBaen mediaBaen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play_view);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mediaBaen = (MediaBaen) intent.getSerializableExtra("video");
        initPlay(mediaBaen.getPath());
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                finish();
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
        }


    public void initPlay(String stringUrl) {
        videoView.setVideoPath(stringUrl);
        videoView.setMediaController(new MediaController(this));
    }

}
