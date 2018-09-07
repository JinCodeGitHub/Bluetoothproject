package com.example.dell.bluetoothproject.comm;

import com.example.mylibrary.ble.data.BleDevice;

public interface Observer {
    void disConnected(BleDevice bleDevice);
}
