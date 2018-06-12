package com.example.administrator.moblieplayer.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.moblieplayer.R;
import com.example.administrator.moblieplayer.baen.MediaBaen;
import com.example.administrator.moblieplayer.utli.FileUtils;
import com.example.administrator.moblieplayer.utli.Utli;
import com.example.administrator.moblieplayer.view.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VitamioVideoPlayViewActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.video)
    com.example.administrator.moblieplayer.view.ui.VideoView videoView;
    @BindView(R.id.tv_video_name)
    TextView tvVideoName;
    @BindView(R.id.tv_system_time)
    TextView tvSystemTime;
    @BindView(R.id.iv_battery)
    ImageView ivBattery;
    @BindView(R.id.bt_viceo)
    Button btViceo;
    @BindView(R.id.sk_viceo)
    SeekBar skViceo;
    @BindView(R.id.bt_switch)
    Button btSwitch;
    @BindView(R.id.tv_current_time)
    TextView tvCurrentTime;
    @BindView(R.id.sk_video)
    SeekBar skVideo;
    @BindView(R.id.tv_duration)
    TextView tvDuration;
    @BindView(R.id.bt_video_exit)
    Button btVideoExit;
    @BindView(R.id.bt_video_previous)
    Button btVideoPrevious;
    @BindView(R.id.bt_video_play)
    Button btVideoPlay;
    @BindView(R.id.bt_video_next)
    Button btVideoNext;
    @BindView(R.id.bt_full_screen)
    Button btFullScreen;
    @BindView(R.id.video_controller)
    RelativeLayout videoController;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.tv_netspeed)
    TextView tvNetSpeed;
    @BindView(R.id.iv_video_play)
    ImageView ivVideoPlay;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;
    @BindView(R.id.tv_loading_speed)
    TextView tvLoadingSpeed;

    private MediaBaen mediaBaen;
    private int mark;
    private Intent intent;
    private Context mContext;
    private ArrayList<MediaBaen> videoList;
    private AudioManager audioManager;
    private BatteryBroadcastReceiver batteryBroadcastReceiver;
    private GestureDetector mGestureDetector;
    private boolean isMediaControllerVisibility = false; //视频控制面板是否显示
    private final int MEDIACONTROLLER_VISIBILITY = 20;
    private SimpleDateFormat systemTime = new SimpleDateFormat("hh:mm:ss");
    private SimpleDateFormat time = new SimpleDateFormat("mm:ss");
    private final int PLAY_STATUS = 10;
    private int currentVolume;//当前音量
    private boolean isVolume = false;//是否静音
    private boolean isFullScreen = false;//是否全屏
    private  int currentPosition = 0;
    private int fullScreenW = 0;//全屏宽
    private int fullScreenH = 0;//全屏高
    private int defaultW = 0;//视频实际宽
    private int defaultH = 0;//视频实际高
    private float startY;
    private float startX;
    private int maxVolume;//最大音量
    private float mVol;//按下的时候的当前音量
    private float touchRang;//拖动过程中当前音量
    private boolean isUseSystem = false;//是否使用系统卡顿
    private boolean isNetUri = false;//是否为网络视频
    private int preCurrentPositon;//上一次播放进度
    private final int UPDATA_SPEED = 30;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play_view);
        ButterKnife.bind(this);
        intent = getIntent();
        mContext = this;
        initView();
        initData();


    }

    private void initData() {
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        getFullScreenWidthHeight();
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        skViceo.setMax(maxVolume);
        skViceo.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        batteryBroadcastReceiver = new BatteryBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryBroadcastReceiver, intentFilter);
        mGestureDetector = new GestureDetector(mContext, new OnMyGestureListener());
        if (intent != null) {
            videoList = (ArrayList<MediaBaen>) intent.getSerializableExtra("videoList");
            mark = intent.getIntExtra("mark", 0);
            getMediaBaen(mark);
            setButtonState();
        }
    }



    private void initView() {
        handler.sendEmptyMessage(UPDATA_SPEED);
        videoController.setVisibility(View.GONE);
        tvSystemTime.setText(systemTime.format(new Date(System.currentTimeMillis())));
        btViceo.setOnClickListener(this);
        skViceo.setOnSeekBarChangeListener(new ViceoSeekBarChangeListener());
        btSwitch.setOnClickListener(this);
        skVideo.setOnSeekBarChangeListener(new VideoSeekBarChangeListener());
        btVideoExit.setOnClickListener(this);
        btVideoPrevious.setOnClickListener(this);
        btVideoPlay.setOnClickListener(this);
        btVideoNext.setOnClickListener(this);
        btFullScreen.setOnClickListener(this);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mark++;
                if (mark >= 0 && mark <= videoList.size() - 1) {
                    playNextVideo();
                } else {
                    finish();
                }

            }
        });
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                defaultH = mp.getVideoHeight();
                defaultW = mp.getVideoWidth();
                btVideoPlay.setBackgroundResource(R.drawable.bg_bt_video_pause_selcet);
                tvDuration.setText(time.format(videoView.getDuration()));
                tvVideoName.setText(mediaBaen.getName());
                skVideo.setMax(videoView.getDuration());
                handler.sendEmptyMessage(PLAY_STATUS);
                llLoading.setVisibility(View.GONE);
                mp.start();
                setDefaultScreen();

            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            //监听视频卡顿
            videoView.setOnInfoListener(new VideoViewOnInfoListener());
        }

    }

    /**
     * 获取屏幕的高和宽
     */
    private void getFullScreenWidthHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        fullScreenW = displayMetrics.widthPixels;
        fullScreenH = displayMetrics.heightPixels;
    }

    /**
     * 设置视频播放地址
     * @param stringUrl
     */
    public void setVideoUrl(Uri stringUrl) {
        if (videoList.size() < 0 && videoList == null) {
           isNetUri = FileUtils.isNetUri(String.valueOf(stringUrl));
            videoView.setVideoURI(stringUrl);
        } else if (stringUrl != null) {
            isNetUri = FileUtils.isNetUri(String.valueOf(stringUrl));
            videoView.setVideoURI(stringUrl);
        }
        setButtonState();
    }

    /**
     * 获取当前视频
     * @param mark
     */
    public void getMediaBaen(int mark) {
        Uri uri = getIntent().getData();
        if (uri == null) {
            mediaBaen = videoList.get(mark);
            uri = Uri.parse(mediaBaen.getPath());
        }
        setVideoUrl(uri);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_video_play :
                playVideo();
                break;
            case R.id.bt_viceo:
                setVideoViceo();
                break;
            case R.id.bt_switch:
                Utli.ToastUtil(mContext, "没有此功能");
                break;
            case R.id.bt_video_exit:
                finish();
                break;
            case R.id.bt_video_previous:
                playPreviousVideo();
                setButtonState();
                break;
            case R.id.bt_video_play:
                playVideo();

                break;
            case R.id.bt_video_next:
                playNextVideo();
                setButtonState();
                break;
            case R.id.bt_full_screen:
                switchFullScreen();
                break;
        }
        handler.removeMessages(MEDIACONTROLLER_VISIBILITY);
        handler.sendEmptyMessageDelayed(MEDIACONTROLLER_VISIBILITY, 4000);

    }

    /**
     * 设置全屏播放
     */
    private void switchFullScreen() {
        if (isFullScreen) {
            //显示默认
            setDefaultScreen();

        } else {
            //显示全屏
            setFullScreen();
        }
    }

    /**
     * 设置默认屏幕
     */
    private void setDefaultScreen() {
        int width = fullScreenW;
        int height = fullScreenH;
        // for compatibility, we adjust size based on aspect ratio
        if (defaultW * height < width * defaultH) {
            //Log.i("@@@", "image too wide, correcting");
            width = height * defaultW / defaultH;
        } else if (defaultW * height > width * defaultH) {
            //Log.i("@@@", "image too tall, correcting");
            height = width * defaultH / defaultW;
        }
        videoView.setVidoViewSize(width, height);
        btFullScreen.setBackgroundResource(R.drawable.bg_bt_full_screen_selcet);
        isFullScreen = false;
    }

    /**
     * 设置全屏
     */
    private void setFullScreen() {
        videoView.setVidoViewSize(fullScreenW, fullScreenH);
        btFullScreen.setBackgroundResource(R.drawable.bg_bt_default_screen_selcet);
        Log.e("================", "setFullScreen: " + "fullScreenW = " + fullScreenW + "fullScreenH=" + fullScreenH);
        isFullScreen = true;
    }

    /**
     * 设置按钮状态
     */
    public void setButtonState() {
        if (videoList != null && videoList.size() > 0) {
            if (videoList.size() == 1) {
                btVideoNext.setBackgroundResource(R.mipmap.btn_next_gray);
                btVideoPrevious.setBackgroundResource(R.mipmap.btn_pre_gray);
                btVideoNext.setEnabled(false);
                btVideoPrevious.setEnabled(false);
            } else if (videoList.size() == 2) {
                if (mark == 0) {
                    btVideoPrevious.setBackgroundResource(R.mipmap.btn_pre_gray);
                    btVideoPrevious.setEnabled(false);
                    btVideoNext.setBackgroundResource(R.drawable.bg_bt_next_select);
                    btVideoNext.setEnabled(true);
                } else if (mark == videoList.size() - 1) {
                    btVideoPrevious.setBackgroundResource(R.drawable.bg_bt_previous_select);
                    btVideoPrevious.setEnabled(true);
                    btVideoNext.setBackgroundResource(R.mipmap.btn_next_gray);
                    btVideoNext.setEnabled(false);
                }
            } else {
                if (mark == 0) {
                    btVideoPrevious.setBackgroundResource(R.mipmap.btn_pre_gray);
                    btVideoPrevious.setEnabled(false);
                    btVideoNext.setBackgroundResource(R.drawable.bg_bt_next_select);
                    btVideoNext.setEnabled(true);
                } else if (mark == videoList.size() - 1) {
                    btVideoPrevious.setBackgroundResource(R.drawable.bg_bt_previous_select);
                    btVideoPrevious.setEnabled(true);
                    btVideoNext.setBackgroundResource(R.mipmap.btn_next_gray);
                    btVideoNext.setEnabled(false);
                } else {
                    btVideoPrevious.setBackgroundResource(R.drawable.bg_bt_previous_select);
                    btVideoPrevious.setEnabled(true);
                    btVideoNext.setBackgroundResource(R.drawable.bg_bt_next_select);
                    btVideoNext.setEnabled(true);
                }
            }
        }
    }

    /**
     * 设置视频播放音量
     */
    public void setVideoViceo() {
        if (isVolume) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
            skViceo.setProgress(currentVolume);
            isVolume = false;
        } else {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            skViceo.setProgress(0);
            isVolume = true;
        }
    }

    /**
     * 播放下一个视频
     */
    public void playNextVideo() {
        if (mark >= 0 && mark <= videoList.size() - 1) {
            mark++;
            getMediaBaen(mark);
            llLoading.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "亲，没有了", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 播放上一个视频
     */
    public void playPreviousVideo() {
        if (mark >= 0 && mark <= videoList.size() - 1) {
            mark--;
            getMediaBaen(mark);
            llLoading.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "亲，没有了", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 播放暂停视频
     */
    public void playVideo() {
        if (videoView.isPlaying()) {
            videoView.pause();
            ivVideoPlay.setVisibility(View.VISIBLE);
            btVideoPlay.setBackgroundResource(R.drawable.bg_bt_video_play_selcet);
        } else {
            videoView.start();
            ivVideoPlay.setVisibility(View.GONE);
            btVideoPlay.setBackgroundResource(R.drawable.bg_bt_video_pause_selcet);
        }
    }

    /**
     * 设置电量图片
     *
     * @param batteryLevel
     */
    public void setIvBattery(int batteryLevel) {
        if (batteryLevel <= 0) {
            ivBattery.setImageResource(R.mipmap.ic_battery_0);
        } else if (batteryLevel <= 20) {
            ivBattery.setImageResource(R.mipmap.ic_battery_20);
        } else if (batteryLevel <= 40) {
            ivBattery.setImageResource(R.mipmap.ic_battery_40);
        } else if (batteryLevel <= 60) {
            ivBattery.setImageResource(R.mipmap.ic_battery_60);
        } else if (batteryLevel <= 80) {
            ivBattery.setImageResource(R.mipmap.ic_battery_80);
        } else if (batteryLevel <= 100) {
            ivBattery.setImageResource(R.mipmap.ic_battery_100);
        } else {
            ivBattery.setImageResource(R.mipmap.ic_battery_100);
        }

    }



    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case UPDATA_SPEED:
                        tvLoadingSpeed.setText(FileUtils.getNetSpeed(mContext));
                        tvNetSpeed.setText(FileUtils.getNetSpeed(mContext));
                        handler.removeMessages(UPDATA_SPEED);
                        handler.sendEmptyMessageDelayed(UPDATA_SPEED,2000);
                        break;
                    case PLAY_STATUS:
                        /**
                         * 更新视频播放进度
                         */
                        currentPosition = videoView.getCurrentPosition();
                        tvSystemTime.setText(systemTime.format(System.currentTimeMillis()));
                        tvCurrentTime.setText(time.format(currentPosition));
                        skVideo.setProgress(currentPosition);
                        break;
                    case MEDIACONTROLLER_VISIBILITY:
                        /**
                         * 隐藏控制面板
                         */
                        hideMediaController();
                        break;

                }
            }
            if (isNetUri){
                /**
                 * 计算缓冲进度
                 */
                int buffer = videoView.getBufferPercentage();
                int totalBuffer =  buffer * skVideo.getMax();
                int secondaryProgress = totalBuffer / 100;
                skVideo.setSecondaryProgress(secondaryProgress);
            }else {
                skVideo.setSecondaryProgress(0);
            }
            /**
             * 判断是否使用系统的卡顿
             * 如果为true的话，是否下面的方法
             *
             * 当前进度-下一次播放进度的值 如果 小于 500毫秒 ，则卡顿
             *
             * 本方法适用于直播格式，拿不到当前播放进度
             */
            if (isUseSystem && videoView.isPlaying()){
                int buffer = currentPosition - preCurrentPositon;
                if (buffer < 500){
                    llInfo.setVisibility(View.VISIBLE);
                    tvNetSpeed.setText(FileUtils.getNetSpeed(mContext));//显示当前网速
                }else {
                    llInfo.setVisibility(View.GONE);
                }
            }else {
                llInfo.setVisibility(View.GONE);
            }
            preCurrentPositon = currentPosition;
            handler.removeMessages(PLAY_STATUS);
            handler.sendEmptyMessageDelayed(PLAY_STATUS, 1000);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getY();
                startX = event.getX();
                touchRang = Math.min(fullScreenH, fullScreenW);
                mVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                handler.removeMessages(MEDIACONTROLLER_VISIBILITY);
                break;
            case MotionEvent.ACTION_MOVE:
                float endY = event.getY();
                float endX = event.getX();
                float distanceY = startY - endY;

                if (endX > fullScreenW / 2) {
                    float dalta = (distanceY / touchRang) * maxVolume;
                    int voice = (int) Math.min(Math.max(mVol + dalta, 0), maxVolume);
                    if (dalta != 0) {
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, voice, 0);
                        currentVolume = voice;
                        skViceo.setProgress(voice);
                        if (voice == 0) {
                            isVolume = true;
                        } else {
                            isVolume = false;
                        }
                    }
                } else {
                    final double FLING_MIN_DISTANCE = 0.5;
                    final double FLING_MIN_VELOCITY = 0.5;
                    if (distanceY > FLING_MIN_VELOCITY && Math.abs(distanceY) > FLING_MIN_VELOCITY) {
                        setBrightness(20);
                    }
                    if (distanceY < FLING_MIN_DISTANCE && Math.abs(distanceY) > FLING_MIN_VELOCITY) {
                        setBrightness(-20);
                    }

                }

                break;

            case MotionEvent.ACTION_UP:
                handler.sendEmptyMessageDelayed(MEDIACONTROLLER_VISIBILITY, 4000);
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 设置屏幕亮度
     *
     * @param i
     */
    private void setBrightness(int i) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.screenBrightness = lp.screenBrightness + i / 255.0f;
        if (lp.screenBrightness > 1) {
            lp.screenBrightness = 1;
        } else if (lp.screenBrightness < 0.2) {
            lp.screenBrightness = (float) 0.2;
        }
        getWindow().setAttributes(lp);
    }

    /**
     * 获取当前屏幕亮度
     *
     * @return systemBrighness 当前屏幕亮度
     */
    public int getSystemBrightness() {
        int systemBrighness = 0;
        try {
            systemBrighness = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return systemBrighness;

    }

    @Override
    protected void onDestroy() {
        if (batteryBroadcastReceiver != null) {
            unregisterReceiver(batteryBroadcastReceiver);
        }
        super.onDestroy();
    }

    /**
     * 音量调节器实现类
     */
    private class ViceoSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                if (progress > 0) {
                    isVolume = false;
                } else {
                    isVolume = true;
                }
                currentVolume = progress;
                seekBar.setProgress(currentVolume);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
            } else {
                seekBar.setProgress(currentVolume);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            handler.removeMessages(MEDIACONTROLLER_VISIBILITY);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            handler.sendEmptyMessageDelayed(MEDIACONTROLLER_VISIBILITY, 4000);
        }
    }

    /**
     * 隐藏控制面板
     */
    private void hideMediaController() {
        videoController.setVisibility(View.GONE);
        isMediaControllerVisibility = false;
        handler.removeMessages(MEDIACONTROLLER_VISIBILITY);

    }

    /**
     * 显示控制面板
     */
    private void showMediaController() {
        videoController.setVisibility(View.VISIBLE);
        isMediaControllerVisibility = true;
        handler.sendEmptyMessageDelayed(MEDIACONTROLLER_VISIBILITY, 4000);
    }

    /**
     * 视频进度条监听
     */
    private class VideoSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                seekBar.setProgress(progress);
                videoView.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            handler.removeMessages(MEDIACONTROLLER_VISIBILITY);
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            handler.sendEmptyMessageDelayed(MEDIACONTROLLER_VISIBILITY, 4000);
        }
    }

    /**
     * 电量广播
     */
    public class BatteryBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                int BatterLevel = intent.getIntExtra("level", 0);
                setIvBattery(BatterLevel);
            }
        }
    }

    /**
     * 手势监听实现类
     */
    public class OnMyGestureListener implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            playVideo();
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDown(MotionEvent e) {


            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (isMediaControllerVisibility) {
                hideMediaController();
            } else {
                showMediaController();
            }


            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {


            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            if (isFullScreen) {
                setDefaultScreen();
            } else {
                setFullScreen();
            }
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    /**
     * 视频卡顿监听
     */
    public class VideoViewOnInfoListener implements MediaPlayer.OnInfoListener {

        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            switch (what){
                case MediaPlayer.MEDIA_INFO_BUFFERING_START :
                    //视频出现卡顿
                    llInfo.setVisibility(View.VISIBLE);
                    tvNetSpeed.setText(FileUtils.getNetSpeed(mContext));//显示当前网速
                    break;
                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    //卡顿结束
                    llInfo.setVisibility(View.GONE);
                    break;
            }
            return true;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            currentVolume++;
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);

            skViceo.setProgress(currentVolume);
            if (currentVolume == 0) {
                isVolume = true;
            } else {
                isVolume = false;
            }
            handler.removeMessages(MEDIACONTROLLER_VISIBILITY);
            handler.sendEmptyMessageDelayed(MEDIACONTROLLER_VISIBILITY, 4000);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            currentVolume--;
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
            skViceo.setProgress(currentVolume);
            if (currentVolume == 0) {
                isVolume = true;
            } else {
                isVolume = false;
            }
            handler.removeMessages(MEDIACONTROLLER_VISIBILITY);
            handler.sendEmptyMessageDelayed(MEDIACONTROLLER_VISIBILITY, 4000);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
