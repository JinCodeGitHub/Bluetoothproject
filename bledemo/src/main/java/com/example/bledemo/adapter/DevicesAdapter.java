package com.example.bledemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.mylibrary.ble.data.BleDevice;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : dell
 *     time   : 2018/07/04
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class DevicesAdapter extends BaseAdapter {

    private Context mContext;
    private List<BleDevice> bleDeviceList;

    public  DevicesAdapter(Context context){
          mContext  = context;
          bleDeviceList   = new ArrayList<>();
    }


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
