package com.example.mylibrary.ble.callback;


import com.example.mylibrary.ble.exception.BleException;


public abstract class BleNotifyCallback extends BleBaseCallback {

    public abstract void onNotifySuccess();

    public abstract void onNotifyFailure(BleException exception);

    public abstract void onCharacteristicChanged(byte[] data);

}
