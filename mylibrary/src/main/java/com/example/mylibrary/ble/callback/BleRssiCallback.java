package com.example.mylibrary.ble.callback;


import com.example.mylibrary.ble.exception.BleException;


public abstract class BleRssiCallback extends BleBaseCallback{

    public abstract void onRssiFailure(BleException exception);

    public abstract void onRssiSuccess(int rssi);

}