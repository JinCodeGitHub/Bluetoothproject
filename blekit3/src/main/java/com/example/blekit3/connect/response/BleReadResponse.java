package com.example.blekit3.connect.response;



/**
 * Created by dingjikerbo on 2016/8/28.
 */
public interface BleReadResponse extends BleTResponse<byte[]> {
    void onResponse(int code, byte[] data);
}
