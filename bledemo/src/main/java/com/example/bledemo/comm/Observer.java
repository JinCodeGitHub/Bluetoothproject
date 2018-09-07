package com.example.bledemo.comm;

import com.example.mylibrary.ble.data.BleDevice;

public interface Observer {
    void disConnected(BleDevice bleDevice);
}
