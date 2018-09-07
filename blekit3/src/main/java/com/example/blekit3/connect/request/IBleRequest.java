package com.example.blekit3.connect.request;


import com.example.blekit3.connect.IBleConnectDispatcher;

/**
 * Created by dingjikerbo on 16/8/25.
 */
public interface IBleRequest {

    void process(IBleConnectDispatcher dispatcher);



    void cancel();
}
