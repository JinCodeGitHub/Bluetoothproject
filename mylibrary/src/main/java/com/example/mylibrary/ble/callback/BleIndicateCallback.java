package com.example.mylibrary.ble.callback;


import com.example.mylibrary.ble.exception.BleException;


public abstract class BleIndicateCallback extends BleBaseCallback{

    public abstract void onIndicateSuccess();

    public abstract void onIndicateFailure(BleException exception);

    public abstract void onCharacteristicChanged(byte[] data);
}
