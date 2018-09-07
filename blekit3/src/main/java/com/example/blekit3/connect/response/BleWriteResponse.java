package com.example.blekit3.connect.response;



/**
 * Created by dingjikerbo on 2016/8/28.
 */
public interface BleWriteResponse extends BleResponse {
    void onResponse(int code);
}
