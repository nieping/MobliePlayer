package com.example.administrator.moblieplayer.view.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/5/13.
 */

public class BaseActivity<T> extends Activity {
    private String TAG = BaseActivity.class.getSimpleName();
    private Context mContext;
    private static List<Activity> activityList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mContext =  getApplicationContext();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        addActivity(this);
    }

    /**
     * 添加一个activity到集合中
     * @param activity
     */
    public  static void addActivity(Activity activity){
        activityList.add(activity);
    }

    /**
     * 删除指定的activity
     * @param activity
     */
    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    /**
     * 获取activity栈中顶不得activity
     * @return
     */
    public static Activity getTopActivity(){
        if (activityList.isEmpty()){
            return null;
        }else {
            return activityList.get(activityList.size() -1);
        }
    }
    public void startAct(Class<T> cla){
        Log.e(TAG, "startAct: " + mContext);
        Intent intent = new Intent();
        intent.setClass(this, cla);
        startActivity(intent);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
