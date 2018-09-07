package com.example.blekit3.connect.response;


import com.example.blekit3.model.BleGattProfile;

/**
 * Created by dingjikerbo on 2016/8/28.
 */
public interface BleConnectResponse extends BleTResponse<BleGattProfile> {
    void onResponse(int code, BleGattProfile profile);
}
