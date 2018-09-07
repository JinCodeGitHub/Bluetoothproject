package com.example.dell.bluetoothproject.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 读，写，通知信息
 * */
public class ConnectService  extends Service{

    public static final String KEY_DATA = "key_data";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }
    //需要手动的开启跟关闭
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

        //实现读、写的操作；读写完成以后数据需要返回到主页面；在页面消失的时候结束服务；
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
