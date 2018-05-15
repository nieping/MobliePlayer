package com.example.administrator.moblieplayer.view.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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

    public PermissionListener getPermissionListener() {
        return permissionListener;
    }

    public void setPermissionListener(PermissionListener permissionListener) {
        this.permissionListener = permissionListener;
    }

    private PermissionListener permissionListener;
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

    /**
     * 权限申请
     * @param permissions 带申请的权限集合
     * @param listener 申请结果监听事件
     */
    public void requestRunTimePermission(String[] permissions,PermissionListener listener){
        this.permissionListener = listener;
        List<String> permissionList=  new ArrayList<>();//存放未授权的权限
        //遍历传递过来的权限
        for (String permission : permissionList){
            //判断是否已经授权
            if (ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED) {
               //未授权，则加入带授权的集合中
                permissionList.add(permission);
            }
            //判断集合是否为空
            if (!permissionList.isEmpty()){
                //不未空，去授权
                ActivityCompat.requestPermissions(this,permissionList.toArray(new String[permissionList.size()]),1);

            }else {
                //为空则全部授权成功
                listener.onGranted();
            }
        }
    }

    /**
     *  权限申请结果
     * @param requestCode 请求码
     * @param permissions 所有的权限集合
     * @param grantResults  授权结果集合
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length >0){
                    List<String> deniedPermissions =  new ArrayList<>();
                    List<String> grantedPermissions =  new ArrayList<>();
                    for (int i = 0; i < grantResults[i];i++){
                        int grantResult = grantResults[i];
                        if (grantResult != PackageManager.PERMISSION_GRANTED){
                            String permission = permissions[i];
                            deniedPermissions.add(permission);
                        }else {
                            String permission = permissions[i];
                            grantedPermissions.add(permission);
                        }
                    }
                    if (deniedPermissions.isEmpty()){
                        permissionListener.onGranted();
                    }else {
                        permissionListener.onGranted(grantedPermissions);
                        permissionListener.onDenied(deniedPermissions);
                    }
                }
                break;
        }
    }

    public interface PermissionListener{
        void onGranted();//授权成功
        void onGranted(List<String> grantedPermission);//部分授权
        void onDenied(List<String> grantedPermissin);//拒绝授权
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }
}
