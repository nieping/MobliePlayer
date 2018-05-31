package com.example.administrator.moblieplayer.view.activity;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.moblieplayer.R;
import com.example.administrator.moblieplayer.baen.MediaBaen;
import com.example.administrator.moblieplayer.service.service.MusicPlayService;
import com.example.administrator.moblieplayer.view.base.BaseActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocalMusicPlayActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.tv_curr_time)
    TextView tv_CurrTime;
    @BindView(R.id.tv_time)
    TextView tvTimes;
    @BindView(R.id.tv_music_name)
    TextView tvMusicNames;
    @BindView(R.id.sb_progress)
    SeekBar seekBar;
    @BindView(R.id.bt_previous)
    Button btPrevious;
    @BindView(R.id.bt_play)
    Button btPlay;
    @BindView(R.id.bt_next)
    Button btNext;


    private String url;
    private Context mContext;
    private Intent intent;
    private MediaPlayer mPlayer;

    private MusicPlayService musicPlayService;
    private MediaBaen baen;
    private ObjectAnimator animator;
    private SimpleDateFormat time = new SimpleDateFormat("mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_music_play);
        ButterKnife.bind(this);
        mContext = this;
        Intent intent = getIntent();
        baen = (MediaBaen) intent.getSerializableExtra("music");
        initView();
    }

    private void initView() {
        binServiceConnection();
        if (baen != null){
            mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource(baen.getPath());
                mPlayer.prepare();
                mPlayer.setLooping(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        final ObjectAnimator animator = ObjectAnimator.ofFloat(image, "rotation", 0f, 360f);
        animator.setDuration(1000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);


        btPlay.setOnClickListener(this);
        btPrevious.setOnClickListener(this);
        btNext.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser == true) {
                    musicPlayService.mPlayer.seekTo(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    /**
     * 判断播放器的状态
     */
    public void playOrPause(){
        if (mPlayer.isPlaying()){
            mPlayer.pause();
        }else {
            mPlayer.start();
        }
    }
    public void stop(){
        if (mPlayer != null){
            mPlayer.stop();
            try {
                mPlayer.reset();
                mPlayer.setDataSource(baen.getPath());
                mPlayer.seekTo(0);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
    private void binServiceConnection() {
        Intent intent = new Intent(this, MusicPlayService.class);
        startService(intent);
        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicPlayService = ((MusicPlayService.MusicBinder) (service)).getService();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicPlayService = null;
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_play:
                playOrPause();
//                animator.start();

                break;
            case R.id.bt_next:

                break;
            case R.id.bt_previous:

                break;
            default:
                break;
        }
    }
    public Handler handler = new Handler();
    public Runnable runnable =new Runnable() {
        @Override
        public void run() {
            tvTimes.setText(time.format(musicPlayService.mPlayer.getCurrentPosition()));
            seekBar.setProgress(musicPlayService.mPlayer.getCurrentPosition());
            seekBar.setMax(musicPlayService.mPlayer.getDuration());
            tv_CurrTime.setText(time.format(musicPlayService.mPlayer.getDuration()));
            handler.postDelayed(runnable,200);
        }
    };
}
