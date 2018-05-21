package com.example.administrator.moblieplayer.utli;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2018/5/10.
 */

public class Utli {
    public static void ToastUtil(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();

    }
    public static String formatter(Context context,long size){
        String fileSize;
        fileSize = android.text.format.Formatter.formatFileSize(context,size);
        return fileSize;
    }


}
