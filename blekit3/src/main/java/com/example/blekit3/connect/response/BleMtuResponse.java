package com.example.blekit3.connect.response;



/**
 * Created by fuhao on 2017/8/24.
 */

public interface BleMtuResponse extends BleTResponse<Integer> {
    void onResponse(int code, Integer data);
}
