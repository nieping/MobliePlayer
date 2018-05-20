package com.example.administrator.moblieplayer.utli;

import java.io.File;

/**
 * Created by Administrator on 2018/5/16.
 */

public class FileUtils {
    public static boolean isExists(String path){
        File file = new File(path);
        return file.exists() ;
    }
}
