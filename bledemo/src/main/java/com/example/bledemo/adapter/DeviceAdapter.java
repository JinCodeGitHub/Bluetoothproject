package com.example.bledemo.adapter;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.bledemo.R;
import com.example.mylibrary.ble.BleManager;
import com.example.mylibrary.ble.data.BleDevice;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DeviceAdapter extends BaseAdapter {

    private Context context;
    private List<BleDevice> bleDeviceList;

    public DeviceAdapter(Context context) {
        this.context = context;
        bleDeviceList = new ArrayList<>();
    }

    public void addDevice(BleDevice bleDevice) {
        removeDevice(bleDevice);
        bleDeviceList.add(bleDevice);
//        Log.i(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!+++++++++"+String.valueOf(bleDeviceList));
    }

    public void removeDevice(BleDevice bleDevice) {
        for (int i = 0; i < bleDeviceList.size(); i++) {
            BleDevice device = bleDeviceList.get(i);
            if (bleDevice.getKey().equals(device.getKey())) {
                bleDeviceList.remove(i);
            }
        }
    }

    public  void  removeallDevice(){

        for (int i = 0; i < bleDeviceList.size(); i++){
            bleDeviceList.remove(i);
        }
        bleDeviceList.clear();
    }


    public void clearConnectedDevice() {
        for (int i = 0; i < bleDeviceList.size(); i++) {
            BleDevice device = bleDeviceList.get(i);
            if (BleManager.getInstance().isConnected(device)) {
                bleDeviceList.remove(i);
            }
        }
    }

    public void clearScanDevice() {
        for (int i = 0; i < bleDeviceList.size(); i++) {
            BleDevice device = bleDeviceList.get(i);
            if (!BleManager.getInstance().isConnected(device)) {
                bleDeviceList.remove(i);
            }
        }
    }

    public void clear() {
        clearConnectedDevice();
        clearScanDevice();
    }

    @Override
    public int getCount() {
//        Log.i(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!-------------------------------------------"+String.valueOf(bleDeviceList.size()));
        return bleDeviceList.size();
    }

    @Override
    public BleDevice getItem(int position) {
        if (position > bleDeviceList.size())
            return null;
        return bleDeviceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = View.inflate(context, R.layout.adapter_device, null);
            holder = new ViewHolder();
            convertView.setTag(holder);
          /*  holder.img_blue = (ImageView) convertView.findViewById(R.id.img_blue);
            holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txt_mac = (TextView) convertView.findViewById(R.id.txt_mac);
            holder.txt_rssi = (TextView) convertView.findViewById(R.id.txt_rssi);
            holder.layout_idle = (LinearLayout) convertView.findViewById(R.id.layout_idle);
            holder.layout_connected = (LinearLayout) convertView.findViewById(R.id.layout_connected);
//            holder.btn_disconnect = (Button) convertView.findViewById(R.id.btn_disconnect);
            holder.btn_connect = (Button) convertView.findViewById(R.id.btn_connect);
//            holder.btn_detail = (Button) convertView.findViewById(R.id.btn_detail);*/
            holder.rl_deviceadapter = convertView.findViewById(R.id.rl_deviceadapter);
            holder.tv_device_name   = convertView.findViewById(R.id.tv_device_name);
            holder.bt_connect       = convertView.findViewById(R.id.bt_connect);
        }

        final BleDevice bleDevice = getItem(position);
        if (bleDevice != null) {
            boolean isConnected = BleManager.getInstance().isConnected(bleDevice);

            String name = bleDevice.getName();
//            String mac = bleDevice.getMac();
//            int rssi = bleDevice.getRssi();
            holder.tv_device_name.setText(name);
//            holder.txt_mac.setText(mac);
//            holder.txt_rssi.setText(String.valueOf(rssi));
            if (isConnected) {
//                holder.img_blue.setImageResource(R.mipmap.ic_blue_connected);
//                holder.txt_name.setTextColor(0xFF1DE9B6);
//                holder.txt_mac.setTextColor(0xFF1DE9B6);
//                holder.layout_idle.setVisibility(View.GONE);
//                holder.layout_connected.setVisibility(View.VISIBLE);
                holder.bt_connect.setText(R.string.unconnect);
            } else {
//                holder.img_blue.setImageResource(R.mipmap.ic_blue_remote);
//                holder.txt_name.setTextColor(0xFF000000);
//                holder.txt_mac.setTextColor(0xFF000000);
//                holder.layout_idle.setVisibility(View.VISIBLE);
//                holder.layout_connected.setVisibility(View.GONE);
                if (holder.bt_connect.getText().equals("断开")){
                    holder.bt_connect.setText(R.string.connect);
                }
            }
        }

        holder.bt_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {

                if(holder.bt_connect.getText().toString().trim().equals("连接")){
                    mListener.onConnect(bleDevice);


                }else if (holder.bt_connect.getText().toString().trim().equals("断开")){
                    mListener.onDisConnect(bleDevice);

                }





                }
            }
        });

     /*   holder.btn_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onDisConnect(bleDevice);
                }
            }
        });
*/     /*   holder.btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onDetail(bleDevice);
                }
            }
        });
*/


        return convertView;
    }

    class ViewHolder {
     /*   ImageView img_blue;
        TextView txt_name;
        TextView txt_mac;
        TextView txt_rssi;
        LinearLayout layout_idle;
        LinearLayout layout_connected;
        Button btn_disconnect;
        Button btn_connect;
        Button btn_detail;*/
        RelativeLayout  rl_deviceadapter;
        TextView  tv_device_name;
        Button    bt_connect;
    }

    public interface OnDeviceClickListener {
        void onConnect(BleDevice bleDevice);

        void onDisConnect(BleDevice bleDevice);

//        void onDetail(BleDevice bleDevice);
    }

    private OnDeviceClickListener mListener;

    public void setOnDeviceClickListener(OnDeviceClickListener listener) {
        this.mListener = listener;
    }
/*
    //设置设备列表监听
    public  interface  OnDeviceItemOnClickListener{
        void  ItemOnClickListener(BleDevice bleDevice);
    }
    public  OnDeviceItemOnClickListener  onDeviceItemOnClickListener;
    public  void  SetOnDeviceItemOnClickListener(OnDeviceItemOnClickListener deviceItemOnClickListener){
        this.onDeviceItemOnClickListener  = deviceItemOnClickListener;
    }*/
}


