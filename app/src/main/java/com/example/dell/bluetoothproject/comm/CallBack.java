package com.example.dell.bluetoothproject.comm;

public interface CallBack<T> {

    void OnSuccess(T date);
    void OnFailure(String msg);
    void OnComplete();
    void OnError();
}
