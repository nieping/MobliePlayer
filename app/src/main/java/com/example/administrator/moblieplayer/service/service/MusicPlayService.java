package com.example.administrator.moblieplayer.service.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;


public class MusicPlayService extends Service {
    public MediaPlayer mPlayer;


    public MusicPlayService() {

    }
    public MusicBinder binder = new MusicBinder();//通过Binder来保持Activity和service的通信


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
     public  class MusicBinder extends Binder{
      public MusicPlayService getService(){
            return MusicPlayService.this;
        }

    }
}
