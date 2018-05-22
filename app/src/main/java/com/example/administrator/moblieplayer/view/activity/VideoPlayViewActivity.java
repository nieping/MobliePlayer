package com.example.administrator.moblieplayer.view.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.moblieplayer.R;

import butterknife.BindView;
import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class VideoPlayViewActivity extends Activity implements SurfaceHolder.Callback {
    @BindView(R.id.vitamio)
    VideoView videoView;
    @BindView(R.id.buffer_percent)
    TextView tvBufferPerent;
    @BindView(R.id.net_speed)
    TextView tvNetSpeed;

    private SurfaceHolder holder;
    private Bundle extras;

    private int mVideoLayout = 0;
    private String url1 = "http://112.253.22.157/17/z/z/y/u/zzyuasjwufnqerzvyxgkuigrkcatxr/hc.yinyuetai.com/D046015255134077DDB3ACA0D7E68D45.flv";
    private String url2 = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";
    private String url3 = "rtsp://184.72.239.149/vod/mp4:BigBuckBunny_115k.mov";
    private String url4 = "http://42.96.249.166/live/388.m3u8";
    private String url5 = "http://live.3gv.ifeng.com/zixun.m3u8";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!LibsChecker.checkVitamioLibs(this)){
            return;
        }
        setContentView(R.layout.activity_video_play_view);
     /*   holder = videoView.getHolder();
        holder.addCallback(this);
        holder.setFormat(PixelFormat.RGBA_8888);
        extras = getIntent().getExtras();*/
        if (Vitamio.isInitialized(this)){
            videoView.setVideoURI(Uri.parse(url2));
            videoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
            MediaController controller = new MediaController(this);
            videoView.setMediaController(controller);
            videoView.setBufferSize(1024);
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setPlaybackSpeed(1.0f);
                }
            });
            videoView.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    tvBufferPerent.setText("已缓冲" + percent + "%");
                }
            });
            videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    switch (what){
                        case MediaPlayer.MEDIA_INFO_BUFFERING_START :
                            tvBufferPerent.setVisibility(View.VISIBLE);
                            tvNetSpeed.setVisibility(View.VISIBLE);
                            mp.pause();
                            break;
                        case MediaPlayer.MEDIA_INFO_BUFFERING_END :
                            tvNetSpeed.setVisibility(View.GONE);
                            tvBufferPerent.setVisibility(View.GONE);
                            mp.start();
                             break;
                        case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED :
                            tvBufferPerent.setText("当前网速" + extra + "kb/s");
                            break;
                    }
                    return true;
                }
            });
        }

    }
    public void changLayout(View view){
        mVideoLayout ++;
        if (mVideoLayout == 4){
            mVideoLayout = 0;

        }
        switch (mVideoLayout){
            case 0:
                mVideoLayout = VideoView.VIDEO_LAYOUT_ORIGIN;
                view.setBackgroundResource(R.mipmap.base_bg);
                break;
            case 1:
                mVideoLayout = VideoView.VIDEO_LAYOUT_SCALE;
                view.setBackgroundResource(R.mipmap.base_bg);
                break;
            case 2:
                mVideoLayout = VideoView.VIDEO_LAYOUT_STRETCH;
                view.setBackgroundResource(R.mipmap.base_bg);
                break;
            case 3:
                mVideoLayout = VideoView.VIDEO_LAYOUT_ZOOM;
                view.setBackgroundResource(R.mipmap.base_bg);

                break;
        }
        videoView.setVideoLayout(mVideoLayout,0);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
