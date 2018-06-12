package com.example.administrator.moblieplayer.utli;

import android.content.Context;
import android.net.TrafficStats;

import java.io.File;

/**
 * Created by Administrator on 2018/5/16.
 */

public class FileUtils {
    private static long lastTotalRxBytes;
    private static long lastTimeStamp;

    public static boolean isExists(String path){
        File file = new File(path);
        return file.exists() ;
    }

    /**
     * 判断是否是网络的资源
     * @param uri
     * @return
     */
    public static boolean isNetUri(String uri){
        boolean reault = false;
        if (uri != null){
            if (uri.toLowerCase().startsWith("http") || uri.toLowerCase().startsWith("rtsp") || uri.toLowerCase().startsWith("mma")){
                reault = true;
            }

        }
        return reault;
    }

    /**
     * 得到网速
     * @param context
     * @return
     */
    public static String getNetSpeed(Context context){
        String netSpeed = "0 kb/s";
        long newTotaRxBytes = TrafficStats.getUidRxBytes(context.getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 :(TrafficStats.getTotalRxBytes() / 1024);
        long newTimeStamp = System.currentTimeMillis();

        long speed = ((newTotaRxBytes - lastTotalRxBytes) * 1000 / (newTimeStamp - lastTimeStamp));
        lastTotalRxBytes = newTotaRxBytes;
        lastTimeStamp = newTimeStamp;
        netSpeed = String.valueOf(speed) + "kb/s";
        return  netSpeed;
    }
}
