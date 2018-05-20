package com.example.administrator.moblieplayer.utli;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

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

    public static FileManager getInstance(Context context){
        if (mInstance == null){
            synchronized (mLock){
                if (mInstance == null){
                    mInstance = new FileManager();
                    mContext = context;
                    mContentResolver = context.getContentResolver();
                }
            }

        }
        return mInstance;
    }

    public static List<VideoBaen> getVideo(){
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
                int date = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                VideoBaen videoBaen =  new VideoBaen(id,name,size,path,duration,date);
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
}
