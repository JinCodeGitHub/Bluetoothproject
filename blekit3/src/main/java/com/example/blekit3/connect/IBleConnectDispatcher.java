package com.example.blekit3.connect;


import com.example.blekit3.connect.request.BleRequest;

public interface IBleConnectDispatcher {

    void onRequestCompleted(BleRequest request);
}
