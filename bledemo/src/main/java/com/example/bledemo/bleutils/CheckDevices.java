package com.example.bledemo.bleutils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.bledemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : dell
 *     time   : 2018/07/02
 *     desc   : major in check bluetooth deviece and permisson
 *     version: 1.0
 * </pre>
 */

public class CheckDevices {

    private  Context  mContext;
    private static final int REQUEST_CODE_OPEN_GPS = 1;
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 2;
    private BluetoothAdapter bluetoothAdapter   = BluetoothAdapter.getDefaultAdapter();



    public  void   checkdevices() {

        //检查设备是否有蓝牙设备：
        if (bluetoothAdapter  != null){


            if ( !bluetoothAdapter.isEnabled()){
                setDialog("提示：","蓝牙未打开，请打开蓝牙！");
                return;
            }else {

                checkPermissions();
            }


        }else {
            setDialog("提示","该设备不支持蓝牙！");
        }
    }


    public   CheckDevices(Context  context){
        mContext  = context;
    }

    private void checkPermissions() {
//        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        List<String> permissionDeniedList = new ArrayList<>();
        for (String permission : permissions) {
            int permissionCheck = ContextCompat.checkSelfPermission(mContext, permission);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted(permission);
            } else {
                permissionDeniedList.add(permission);
            }
        }
        if (!permissionDeniedList.isEmpty()) {
            String[] deniedPermissions = permissionDeniedList.toArray(new String[permissionDeniedList.size()]);
            ActivityCompat.requestPermissions((Activity) mContext, deniedPermissions, REQUEST_CODE_PERMISSION_LOCATION);
        }
    }

    private void onPermissionGranted(String permission) {
        switch (permission) {
            case Manifest.permission.ACCESS_FINE_LOCATION:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkGPSIsOpen()) {
                    new AlertDialog.Builder(mContext)
                            .setTitle(R.string.notifytitle)
                            .setMessage(R.string.GPSNotigyMessage)
                            .setNegativeButton(R.string.negative,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //取消的时候结束这个activity

                                        }
                                    })
                            .setPositiveButton(R.string.setting,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //确定的时候，跳转到设置页面；
                                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);

                                        }
                                    })

                            .setCancelable(false)
                            .show();
                } else {

                    //开始寻找设备发现设备；

                }
                break;
        }
    }

    private boolean checkGPSIsOpen() {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null)
            return false;
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public  void  setDialog (String titles, String  msg){

        new AlertDialog.Builder(mContext)
                .setTitle(titles)
                .setMessage(msg)
                .setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .show();
    }
}
