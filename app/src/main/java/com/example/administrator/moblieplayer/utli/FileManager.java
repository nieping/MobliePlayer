package com.example.administrator.moblieplayer.utli;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.example.administrator.moblieplayer.baen.MusicBaen;
import com.example.administrator.moblieplayer.baen.VideoBaen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/16.
 */

public class FileManager {
    private static FileManager mInstance;
    private static Context mContext;
    private static ContentResolver mContentResolver;
    private static Object mLock = new Object();
    private String TAG = FileManager.class.getSimpleName();
    public FileManager(Context context){
        this.mContext = context;
            mContext = context;
            mContentResolver = context.getContentResolver();

    }

    public static List<VideoBaen> getVideo(Context context){
        Log.e("FileManager", "================getVideo: " );
         ContentResolver mContentResolver = context.getContentResolver();
        List<VideoBaen> videoBaens = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = mContentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,null,null,null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
            while (cursor.moveToNext()){
                String path =  cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                if (FileUtils.isExists(path)){
                    continue;
                }
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME));
                long size = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE);
                int duration = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION);
                int id = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID);
                int data = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                VideoBaen videoBaen =  new VideoBaen(id,name,size,path,duration,data);
                Log.e("FileManager", "=========video=======getVideo: " );
                videoBaens.add(videoBaen);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null){
                cursor.close();
            }
        }
        return videoBaens;

    }

    public static List<MusicBaen> getMusic(Context context){
        Log.e("FileManager", "================getMusic: " );
        ContentResolver mContentResolver = context.getContentResolver();
        List<MusicBaen> musicBaenList = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = mContentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            while (cursor.moveToNext()){
                String path =  cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                if (FileUtils.isExists(path)){
                    continue;
                }
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                long size = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE);
                int duration = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION);
                int id = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID);
                int data = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
                MusicBaen musicBaen =  new MusicBaen(id,name,path,duration,size,data);
                Log.e("FileManager", "=========video=======getVideo: " );
                musicBaenList .add(musicBaen);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null){
                cursor.close();
            }
        }
        return musicBaenList;

    }
}
