package com.example.administrator.moblieplayer.view.ui;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.administrator.moblieplayer.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/6/5.
 */

public class MediaController extends LinearLayout implements View.OnClickListener {
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


    private Context mContext;
    private SimpleDateFormat systemTime = new SimpleDateFormat("hh:mm:ss");
    private SimpleDateFormat time = new SimpleDateFormat("mm:ss");
    private MediaControllerInterface mediaControllerInterface;

    public MediaController(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.video_controller,this,true);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

        btViceo.setOnClickListener(this);
        skViceo.setOnSeekBarChangeListener(new ViceoSeekBarChangeListener());
        btSwitch.setOnClickListener(this);
        skVideo.setOnSeekBarChangeListener(new VideoSeekBarChangeListener());
        btVideoExit.setOnClickListener(this);
        btVideoPrevious.setOnClickListener(this);
        btVideoPlay.setOnClickListener(this);
        btVideoNext.setOnClickListener(this);
        btFullScreen.setOnClickListener(this);

    }

    /**
     * 设置音量进度条的最大值
     * @param max
     */
    public void setSkViceoMax (int max){
        skViceo.setMax(max);
    }

    /**
     * 设置音量进度条的值
     * @param Progress
     */
    public void setSkViceoProgress(int Progress){
        skViceo.setProgress(Progress);

    }
    /**
     * 设置视频进度条的最大值
     * @param max
     */
    public void setSkVideoMax (int max){
        skVideo.setMax(max);
    }

    /**
     * 设置视频进度条的值
     * @param Progress
     */
    public void setSkVideoProgress(int Progress){
        skVideo.setProgress(Progress);

    }
    /**
     *设置视频名称
     * @param name
     */
    public void setTvVideoName (String name){
        tvVideoName.setText(name);
    }
    /**
     *设置btVideo背景
     */
    public void setBtVideoPlayBackgroundResource(@DrawableRes int resid){
        btVideoPlay.setBackgroundResource(resid);
    }

    /**
     * 设置btFullScreen背景
     * @param resid
     */
    public void setBtFullScreenBackgroudResource(@DrawableRes int resid){
        btFullScreen.setBackgroundResource(resid);
    }
    /**
     * 设置系统时间
     * @param date
     */
    public void setTvSystemTime(Date date){
        tvSystemTime.setText(systemTime.format(date));
    }

    /**
     * 设置当前播放时间
     * @param date
     */
    public void setTvCurrentTime(int date){
        tvCurrentTime.setText(time.format(date));
    }

    /**
     * 设置当前视频总时长
     * @param date
     */
    public void setTvDuration(int date){
        tvDuration.setText(time.format(date));
    }

    /**
     * 设置电量图片
     * @param batteryLevel
     */
    public void setIvBattery(int batteryLevel){
        if (batteryLevel <= 0 ){
            ivBattery.setImageResource(R.mipmap.ic_battery_0);
        }else if(batteryLevel <= 20){
            ivBattery.setImageResource(R.mipmap.ic_battery_20);
        }else if(batteryLevel <= 40){
            ivBattery.setImageResource(R.mipmap.ic_battery_40);
        }else if(batteryLevel <= 60){
            ivBattery.setImageResource(R.mipmap.ic_battery_60);
        }else if(batteryLevel <= 80){
            ivBattery.setImageResource(R.mipmap.ic_battery_80);
        }else if(batteryLevel <= 100){
            ivBattery.setImageResource(R.mipmap.ic_battery_100);
        }else {
            ivBattery.setImageResource(R.mipmap.ic_battery_100);
        }

    }

    @Override
    public void onClick(View v) {
        if (mediaControllerInterface != null){
            switch (v.getId()){
                case R.id.bt_viceo :
                    mediaControllerInterface.btViceo();
                    break;
                case R.id.bt_switch :
                    mediaControllerInterface.btSwitch();


                    break;
                case R.id.bt_video_exit :
                    mediaControllerInterface.btVideoExit();

                    break;
                case R.id.bt_video_previous :
                    mediaControllerInterface.btVideoPrevious();

                    break;
                case R.id.bt_video_play :
                    mediaControllerInterface.btVideoPlay();

                    break;
                case R.id.bt_video_next :
                    mediaControllerInterface.btVideoNext();

                    break;
                case R.id.bt_full_screen :
                    mediaControllerInterface.btFullScreen();

                    break;
            }
        }


    }

    /**
     * 音量进度调监听
     */
    private class ViceoSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (mediaControllerInterface != null){
                mediaControllerInterface.ViceoSeekBarProgreessChanged(seekBar,progress,fromUser);
            }

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    /**
     * 视频进度条监听
     */
    private class VideoSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (mediaControllerInterface != null){
                mediaControllerInterface.VideoSeekBarProgreessChanged(seekBar,progress,fromUser);

            }

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    /**
     * 控制器接口
     */
    public interface MediaControllerInterface{
        /**
         * 一键静音
         */
        void btViceo();
        /**
         * 切换播放器
         */
        void btSwitch();
        /**
         * 退出
         */
        void btVideoExit();
        /**
         * 上一个
         */
        void btVideoPrevious();
        /**
         * 播放/暂停
         */
        void btVideoPlay();
        /**
         * 下一个
         */
        void btVideoNext();
        /**
         * 全屏
         */
        void btFullScreen();

        /**
         * 音量监听变化的时候
         */
        void ViceoSeekBarProgreessChanged(SeekBar seekBar, int progress, boolean fromUser);
        /**
         * 视频监听变化的时候
         */
        void VideoSeekBarProgreessChanged(SeekBar seekBar, int progress, boolean fromUser);
    }

    public MediaControllerInterface getMediaControllerInterface() {
        return mediaControllerInterface;
    }

    public void setMediaControllerInterface(MediaControllerInterface mediaControllerInterface) {
        this.mediaControllerInterface = mediaControllerInterface;
    }
}
