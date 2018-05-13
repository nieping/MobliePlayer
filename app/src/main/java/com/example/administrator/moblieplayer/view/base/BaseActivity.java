package com.example.administrator.moblieplayer.view.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Window;

/**
 * Created by Administrator on 2018/5/13.
 */

public class BaseActivity<T> extends Activity {
    private String TAG = BaseActivity.class.getSimpleName();
    private Context mContext;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mContext =  getApplicationContext();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void startAct(Class<T> cla){
        Log.e(TAG, "startAct: " + mContext);
        Intent intent = new Intent();
        intent.setClass(this, cla);
        startActivity(intent);
    }
}
