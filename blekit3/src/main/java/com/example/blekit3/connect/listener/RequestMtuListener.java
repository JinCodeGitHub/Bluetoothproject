package com.example.blekit3.connect.listener;



/**
 * Created by fuhao on 2017/8/24.
 */

public interface RequestMtuListener extends GattResponseListener {
    void onMtuChanged(int mtu, int status);
}