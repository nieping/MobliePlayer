package com.example.administrator.moblieplayer.view.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

import com.example.administrator.moblieplayer.R;
import com.example.administrator.moblieplayer.view.base.BaseActivity;

public class WelcomeActivity extends BaseActivity {
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startAct(NavigationActivity.class);
                finish();
            }
        }, 2000);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
                startAct(NavigationActivity.class);
                finish();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 在生命周期结束的时候讲handler 移除
     */
    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
