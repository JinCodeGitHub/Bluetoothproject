package com.example.mylibrary.ble.callback;


import android.os.Handler;

import com.example.mylibrary.ble.exception.OtherException;

public abstract class BleBaseCallback {

    private String key;
    private Handler handler;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void onNotifyFailure(OtherException e) {
    }
}
