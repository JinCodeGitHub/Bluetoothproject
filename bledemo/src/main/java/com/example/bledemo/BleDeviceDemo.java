package com.example.bledemo;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bledemo.adapter.DeviceAdapter;
import com.example.bledemo.comm.ObserverManager;
import com.example.mylibrary.ble.BleManager;
import com.example.mylibrary.ble.callback.BleGattCallback;
import com.example.mylibrary.ble.callback.BleMtuChangedCallback;
import com.example.mylibrary.ble.callback.BleRssiCallback;
import com.example.mylibrary.ble.callback.BleScanCallback;
import com.example.mylibrary.ble.data.BleDevice;
import com.example.mylibrary.ble.exception.BleException;
import java.util.ArrayList;
import java.util.List;


public class BleDeviceDemo extends AppCompatActivity  {


    private static final String TAG = BleDeviceDemo.class.getSimpleName();
    private static final int    RQUESTCODE   =  101;
    private static final int REQUEST_CODE_OPEN_GPS = 1;
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 2;
    private static final  String   KEY_DEVICE = "Key_device";

    private LinearLayout ll1;
    private Toolbar  toolbar;
    private ImageButton ib_back;
    private TextView tv_seekble;
    private RelativeLayout rl1;
    private TextView tv_ble;
    private Button   bt_openble;
    private Button   bt_seekble;
    private TextView tv_connected_ble;
    private ListView lv_connected_ble;
    private TextView tv_unconnect_ble;
    private ListView lv_unconnect_ble;

    private Animation operatingAnim;
    private DeviceAdapter mDeviceAdapter;
    private ProgressDialog progressDialog;

    private BleDevice bleDevice;
    private DeviceAdapter mConDeviceAdapter;

    private BluetoothAdapter bluetoothAdapter   = BluetoothAdapter.getDefaultAdapter();

    private BleDevice bleDevices; //已连接设备

//    private BleDisConBrocastReceiver bleDisConBrocastReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        checkBle();
        initView();

        BleManager.getInstance().init(getApplication());
        BleManager.getInstance()
                .enableLog(true)
                .setMaxConnectCount(1)
                .setOperateTimeout(5000);

     /*   bleDisConBrocastReceiver = new BleDisConBrocastReceiver();
        IntentFilter intentFilter    = new IntentFilter();
        intentFilter.addAction("com.example.bledemos");
        registerReceiver(bleDisConBrocastReceiver,intentFilter);*/

    }

   /* @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.bt_openble:
                if (bt_openble.getText().equals(getString(R.string.openble))){
                    bt_openble.setText(getString(R.string.closeble));
                    //打开蓝牙
                    checkBle();


                }else  if (bt_openble.getText().equals(getString(R.string.closeble))){
                    bt_openble.setText(getString(R.string.openble));
                    //关闭蓝牙
                    bluetoothAdapter.disable();

                }
                break;
            case R.id.bt_seekble:
                if (bt_seekble.getText().equals(getString(R.string.seekble))){
                    bt_seekble.setText(getString(R.string.stopseekble));
                    checkPermissions();
                }else  if (bt_seekble.getText().equals(getString(R.string.stopseekble))){
                    bt_seekble.setText(getString(R.string.seekble));
                    BleManager.getInstance().cancelScan();
                }
                break;
        }
    }*/


    @Override
    protected void onResume() {
        super.onResume();
//        showConnectedDevice();
//        mDeviceAdapter.removeDevice(bleDevice);
       /* if (!BleManager.getInstance().isConnected(bleDevice)){
            mConDeviceAdapter.removeDevice(bleDevice);

            new AlertDialog.Builder(BleDeviceDemo.this).setMessage(R.string.dialogtips).setPositiveButton(R.string.dialogpositive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                }
            }).setCancelable(false).show();
        }*/
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        BleManager.getInstance().disconnectAllDevice();
        BleManager.getInstance().destroy();
//        unregisterReceiver(bleDisConBrocastReceiver);
    }


/*
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan:
                if (btn_scan.getText().equals(getString(R.string.start_scan))) {
                    checkPermissions();

                } else if (btn_scan.getText().equals(getString(R.string.stop_scan))) {
                    BleManager.getInstance().cancelScan();
                }
                break;

            case R.id.txt_setting:
                if (layout_setting.getVisibility() == View.VISIBLE) {
                    layout_setting.setVisibility(View.GONE);
                    txt_setting.setText(getString(R.string.expand_search_settings));
                } else {
                    layout_setting.setVisibility(View.VISIBLE);
                    txt_setting.setText(getString(R.string.retrieve_search_settings));
                }
                break;
        }
    }*/
    //检查是否有蓝牙设备；
    private  void  checkBle (){

        if (bluetoothAdapter == null){
            Toast.makeText(BleDeviceDemo.this,"该设备不支持蓝牙！",Toast.LENGTH_SHORT).show();
            return;
        }else {
            if (bluetoothAdapter.isEnabled()){

                }else {

                Intent intent1   = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

                try {
                    startActivityForResult(intent1,RQUESTCODE);
                }catch (ActivityNotFoundException e){
                    e.printStackTrace();
                }


            }
        }
    }

    public void initView() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        btn_scan = (Button) findViewById(R.id.btn_scan);
//        btn_scan.setText(getString(R.string.start_scan));
//        btn_scan.setOnClickListener(this);

//        et_name = (EditText) findViewById(R.id.et_name);
//        et_mac = (EditText) findViewById(R.id.et_mac);
//        et_uuid = (EditText) findViewById(R.id.et_uuid);
//        sw_auto = (Switch) findViewById(R.id.sw_auto);

//        layout_setting = (LinearLayout) findViewById(R.id.layout_setting);
//        txt_setting = (TextView) findViewById(R.id.txt_setting);
//        txt_setting.setOnClickListener(this);
//        layout_setting.setVisibility(View.GONE);
//        txt_setting.setText(getString(R.string.expand_search_settings));

//        img_loading = (ImageView) findViewById(R.id.img_loading);
//        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
//        operatingAnim.setInterpolator(new LinearInterpolator());
//        progressDialog = new ProgressDialog(this);
          ll1   = findViewById(R.id.ll1);
//         toolbar    = findViewById(R.id.tb_ble);
//        setSupportActionBar(toolbar);
        ib_back  = findViewById(R.id.ib_back);
        tv_seekble = findViewById(R.id.tv_seekble);
        rl1 = findViewById(R.id.rl1);
        tv_ble = findViewById(R.id.tv_ble);
        bt_openble = findViewById(R.id.bt_openble);
        bt_seekble = findViewById(R.id.bt_seekble);
        tv_connected_ble  = findViewById(R.id.tv_connected_ble);
        lv_connected_ble = findViewById(R.id.lv_connected_ble);
        tv_unconnect_ble = findViewById(R.id.tv_unconnect_ble);
        lv_unconnect_ble = findViewById(R.id.lv_unconnect_ble);

        if (bluetoothAdapter.isEnabled()){
            bt_openble.setText(getString(R.string.closeble));
        }else {
            bt_openble.setText(getString(R.string.openble));
        }


        bt_openble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bt_openble.getText().equals(getString(R.string.openble))){
                    bt_openble.setText(getString(R.string.closeble));
                    //打开蓝牙
                    checkBle();


                }else  if (bt_openble.getText().equals(getString(R.string.closeble))){
                    bt_openble.setText(getString(R.string.openble));
                    //关闭蓝牙
                    bluetoothAdapter.disable();
                    mDeviceAdapter.removeallDevice();
                    mDeviceAdapter.clear();
                    if ( mConDeviceAdapter != null){
                        mConDeviceAdapter.clear();
                    }


                }
            }
        });


            bt_seekble.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(bt_openble.getText().toString().trim().equals("关闭")){

                        if (bt_seekble.getText().equals(getString(R.string.seekble))){
                            bt_seekble.setText(getString(R.string.stopseekble));
                            checkPermissions();
                        }else  if (bt_seekble.getText().equals(getString(R.string.stopseekble))){
                            bt_seekble.setText(getString(R.string.seekble));
                            BleManager.getInstance().cancelScan();
                        }
                    }else {
                        Toast.makeText(BleDeviceDemo.this,"请打开蓝牙设备！",Toast.LENGTH_SHORT).show();

                    }

                }
            });






        mDeviceAdapter = new DeviceAdapter(BleDeviceDemo.this);



        mDeviceAdapter.setOnDeviceClickListener(new DeviceAdapter.OnDeviceClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onConnect(BleDevice bleDevice) {
                if (!BleManager.getInstance().isConnected(bleDevice)) {
                    BleManager.getInstance().cancelScan();
                    connect(bleDevice);

                    if (BleManager.getInstance().isConnected(bleDevice)){
                        BleManager.getInstance().cancelScan();
                    }



//                    Log.i("BLEDEVICE","bleDevice"+String.valueOf(bleDevice)+String.valueOf(bleDevice.getName()));
//                    Toast.makeText(BleDeviceDemo.this,"jianlilianjie",Toast.LENGTH_SHORT).show();
                }


//                lv_connected_ble.setAdapter(mConDeviceAdapter);
//                 if(BleManager.getInstance().isConnected(bleDevice)){
//                     Log.i("sssssssssssssss_bleyi",String.valueOf(BleManager.getInstance().isConnected(bleDevice)));

//                     Log.i("sssssssssssssss_bleyi", String.valueOf(bleDevice));
//                 }
            }

            @Override
            public void onDisConnect(BleDevice bleDevice) {
                if (BleManager.getInstance().isConnected(bleDevice)) {
                    BleManager.getInstance().disconnect(bleDevice);
                }

            }
        /*    //进入操作页面
            @Override
            public void onDetail(BleDevice bleDevice) {
                if (BleManager.getInstance().isConnected(bleDevice)) {
                    Intent intent = new Intent(BleDeviceDemo.this, MainActivity.class);
                    intent.putExtra(MainActivity.KEY_DATA, bleDevice);
                    startActivity(intent);
                    //选取设备后进去到后台服务去执行读写通知等操作；

                    Intent  intent1    = new Intent(MainActivity.this, ConnectService.class);
                    intent.putExtra(ConnectService.KEY_DATA,intent1);
                    startService(intent1);
                }
            }*/
        });
        //在设置之前需要判断一下是不是蓝牙打开
        lv_unconnect_ble.setAdapter(mDeviceAdapter);


    }


    private void showConnectedDevice() {
        List<BleDevice> deviceList = BleManager.getInstance().getAllConnectedDevice();
//        mDeviceAdapter.clearConnectedDevice();
        for (BleDevice bleDevice : deviceList) {
            mDeviceAdapter.addDevice(bleDevice);
        }
        mDeviceAdapter.notifyDataSetChanged();
    }

    //

/*    private void setScanRule() {
        String[] uuids;
        String str_uuid = et_uuid.getText().toString();
        if (TextUtils.isEmpty(str_uuid)) {
            uuids = null;
        } else {
            uuids = str_uuid.split(",");
        }
        UUID[] serviceUuids = null;
        if (uuids != null && uuids.length > 0) {
            serviceUuids = new UUID[uuids.length];
            for (int i = 0; i < uuids.length; i++) {
                String name = uuids[i];
                String[] components = name.split("-");
                if (components.length != 5){
                    serviceUuids[i] = null;
                }else {
                    serviceUuids[i] = UUID.fromString(uuids[i]);
                }
            }
        }

        String[] names;
        String str_name = et_name.getText().toString();
        if (TextUtils.isEmpty(str_name)) {
            names = null;
        } else {
            names = str_name.split(",");
        }

        String mac = et_mac.getText().toString();

        boolean isAutoConnect = sw_auto.isChecked();

        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                .setServiceUuids(serviceUuids)      // 只扫描指定的服务的设备，可选
                .setDeviceName(true, names)   // 只扫描指定广播名的设备，可选
                .setDeviceMac(mac)                  // 只扫描指定mac的设备，可选
                .setAutoConnect(isAutoConnect)      // 连接时的autoConnect参数，可选，默认false
                .setScanTimeOut(10000)              // 扫描超时时间，可选，默认10秒
                .build();
        BleManager.getInstance().initScanRule(scanRuleConfig);
    }*/

    private void startScan() {
        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
//                Log.i(TAG, "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+String.valueOf(success));
                mDeviceAdapter.clearScanDevice();
                mDeviceAdapter.notifyDataSetChanged();
//                img_loading.startAnimation(operatingAnim);
//                img_loading.setVisibility(View.VISIBLE);
//                btn_scan.setText(getString(R.string.stop_scan));
                bt_seekble.setText(getString(R.string.stopseekble));
            }

            @Override
            public void onLeScan(BleDevice bleDevice) {
                super.onLeScan(bleDevice);
            }

            @Override
            public void onScanning(BleDevice bleDevice) {
                Log.i("BLEDEVICE","bleDevice"+String.valueOf(bleDevice)+String.valueOf(bleDevice.getName()));
                mDeviceAdapter.addDevice(bleDevice);
                mDeviceAdapter.notifyDataSetChanged();


            }

            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {
//                img_loading.clearAnimation();
//                img_loading.setVisibility(View.INVISIBLE);
//                btn_scan.setText(getString(R.string.start_scan));
                bt_seekble.setText(getString(R.string.seekble));
            }
        });
    }

    private void connect(final BleDevice bleDevice) {
        BleManager.getInstance().connect(bleDevice, new BleGattCallback() {
            @Override
            public void onStartConnect() {
//                progressDialog.show();
//                Log.i(TAG,"connectdevice+++"+bleDevice);
            }

            @Override
            public void onConnectFail(BleException exception) {
//                img_loading.clearAnimation();
//                img_loading.setVisibility(View.INVISIBLE);
//                btn_scan.setText(getString(R.string.start_scan));
//                progressDialog.dismiss();
//                Toast.makeText(, getString(R.string.connect_fail), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
//                progressDialog.dismiss();
//                mDeviceAdapter.addDevice(bleDevice);
                //连接成功以后跳转页面；
                Intent intent   = new Intent(BleDeviceDemo.this,MainActivity.class);
                intent.putExtra(KEY_DEVICE,bleDevice);
//                startActivity(intent);
                startActivityForResult(intent,101);
                //连接成功以后从未连接连接移除已连接设备；
                mDeviceAdapter.removeDevice(bleDevice);
                mDeviceAdapter.clearConnectedDevice();
                mDeviceAdapter.notifyDataSetChanged();
                //把连接成功的设备显示到已连接设备列表
                mConDeviceAdapter  = new DeviceAdapter(BleDeviceDemo.this);
                mConDeviceAdapter.addDevice(bleDevice);

                mConDeviceAdapter.setOnDeviceClickListener(new DeviceAdapter.OnDeviceClickListener() {
                    @Override
                    public void onConnect(BleDevice bleDevice) {
                        if (!BleManager.getInstance().isConnected(bleDevice)) {
                            BleManager.getInstance().cancelScan();
                            connect(bleDevice);
                        }


                    }


                    @Override
                    public void onDisConnect(BleDevice bleDevice) {
                        if (BleManager.getInstance().isConnected(bleDevice)) {
                            BleManager.getInstance().disconnect(bleDevice);
                        }
                        mConDeviceAdapter.clear();
                    }
                });
                lv_connected_ble.setAdapter(mConDeviceAdapter);
//                readRssi(bleDevice);
                setMtu(bleDevice, 512);

                // 连接成功以后开始获取，读写的服务;
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
//                progressDialog.dismiss();

                mDeviceAdapter.removeDevice(bleDevice);
                mDeviceAdapter.notifyDataSetChanged();

                if (isActiveDisConnected) {
//                    Toast.makeText(BleDeviceDemo.this, getString(R.string.active_disconnected), Toast.LENGTH_LONG).show();
                } else {
//                    Toast.makeText(BleDeviceDemo.this, getString(R.string.disconnected), Toast.LENGTH_LONG).show();
                    ObserverManager.getInstance().notifyObserver(bleDevice);
                }

            }
        });
    }

    private void readRssi(BleDevice bleDevice) {
        BleManager.getInstance().readRssi(bleDevice, new BleRssiCallback() {
            @Override
            public void onRssiFailure(BleException exception) {
                Log.i(TAG, "onRssiFailure" + exception.toString());
            }

            @Override
            public void onRssiSuccess(int rssi) {
                Log.i(TAG, "onRssiSuccess: " + rssi);
            }
        });
    }

    private void setMtu(BleDevice bleDevice, int mtu) {
        BleManager.getInstance().setMtu(bleDevice, mtu, new BleMtuChangedCallback() {
            @Override
            public void onSetMTUFailure(BleException exception) {
                Log.i(TAG, "onsetMTUFailure" + exception.toString());
            }

            @Override
            public void onMtuChanged(int mtu) {
                Log.i(TAG, "onMtuChanged: " + mtu);
            }
        });
    }

    @Override
    public final void onRequestPermissionsResult(int requestCode,
                                                 @NonNull String[] permissions,
                                                 @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_LOCATION:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            onPermissionGranted(permissions[i]);
                        }
                    }
                }
                break;
        }
    }

    private void checkPermissions() {
//        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
       /* if (!bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, getString(R.string.please_open_blue), Toast.LENGTH_LONG).show();
            return;
        }*/

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        List<String> permissionDeniedList = new ArrayList<>();
        for (String permission : permissions) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, permission);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted(permission);
            } else {
                permissionDeniedList.add(permission);
                startScan();
            }
        }
        if (!permissionDeniedList.isEmpty()) {
            String[] deniedPermissions = permissionDeniedList.toArray(new String[permissionDeniedList.size()]);
            ActivityCompat.requestPermissions(this, deniedPermissions, REQUEST_CODE_PERMISSION_LOCATION);
        }
    }

    private void onPermissionGranted(String permission) {
        switch (permission) {
            case Manifest.permission.ACCESS_FINE_LOCATION:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkGPSIsOpen()) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.notifytitle)
                            .setMessage(R.string.GPSNotigyMessage)
                            .setNegativeButton(R.string.positive,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                            .setPositiveButton(R.string.setting,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                            startActivityForResult(intent, REQUEST_CODE_OPEN_GPS);
                                        }
                                    })

                            .setCancelable(false)
                            .show();
                } else {
//                    setScanRule();
                    startScan();
                }
                break;
        }
    }

    private boolean checkGPSIsOpen() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null)
            return false;
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OPEN_GPS) {
            if (checkGPSIsOpen()) {
//                setScanRule();
                startScan();
            }
        }

        if (resultCode == 102 ){

//            mConDeviceAdapter.removeDevice(bleDevices);
            mConDeviceAdapter.clear();

            new AlertDialog.Builder(BleDeviceDemo.this).setMessage(R.string.dialogtips).setPositiveButton(R.string.dialogpositive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setCancelable(false).show();
        }
    }

    /*  class  BleDisConBrocastReceiver extends BroadcastReceiver{
          @Override
          public void onReceive(Context context, Intent intent) {
              if (intent.getStringExtra("broadcastdata").equals("com.example.bledemos")){
                  if (!BleManager.getInstance().isConnected(bleDevice)){
                      mConDeviceAdapter.removeDevice(bleDevice);

                      new AlertDialog.Builder(BleDeviceDemo.this).setMessage(R.string.dialogtips).setPositiveButton(R.string.dialogpositive, new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              dialog.dismiss();
                          }
                      }).setCancelable(false).show();
                  }
              }
          }
      }*/

}



