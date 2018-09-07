package com.example.dell.bluetoothproject.comm;

import com.example.mylibrary.ble.data.BleDevice;

public interface Observable {

    void addObserver(Observer obj);

    void deleteObserver(Observer obj);

    void notifyObserver(BleDevice bleDevice);
}
