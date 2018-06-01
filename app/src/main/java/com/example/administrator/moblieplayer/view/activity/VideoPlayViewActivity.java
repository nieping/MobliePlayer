package com.example.administrator.moblieplayer.view.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.administrator.moblieplayer.R;
import com.example.administrator.moblieplayer.baen.MediaBaen;
import com.example.administrator.moblieplayer.view.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VideoPlayViewActivity extends BaseActivity {
    @BindView(R.id.video)
    VideoView videoView;


    private MediaBaen mediaBaen;
    private int mark;
    private Intent intent;
    private  ArrayList<MediaBaen>videoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play_view);
        ButterKnife.bind(this);
        intent  = getIntent();
        if (intent != null) {
           videoList = (ArrayList<MediaBaen>) intent.getSerializableExtra("videoList");
            mark = intent.getIntExtra("mark", 0);
            getMediaBaen(mark);
        }





        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (mark >= 0 && mark <= videoList.size() -1  ){
                    mark ++;
                    getMediaBaen(mark);
                }else {
                    finish();
                }

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
        Log.e("==============", "setVideoPath: "  +    mark );
        videoView.setMediaController(new MediaController(this));
    }
    public void getMediaBaen(int mark){
        mediaBaen = videoList.get(mark);
        initPlay(mediaBaen.getPath());
    }
}
