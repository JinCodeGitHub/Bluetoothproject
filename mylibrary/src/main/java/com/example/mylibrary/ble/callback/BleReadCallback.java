package com.example.mylibrary.ble.callback;


import com.example.mylibrary.ble.exception.BleException;


public abstract class BleReadCallback extends BleBaseCallback {

    public abstract void onReadSuccess(byte[] data);

    public abstract void onReadFailure(BleException exception);

}
