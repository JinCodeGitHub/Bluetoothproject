package com.example.blekit3.connect.listener;

import com.example.blekit3.connect.listener.GattResponseListener;

/**
 * Created by dingjikerbo on 2016/8/25.
 */
public interface ReadRssiListener extends GattResponseListener {
    void onReadRemoteRssi(int rssi, int status);
}
