package com.example.dell.bluetoothproject.operation;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.TextView;

import com.example.dell.bluetoothproject.R;
import com.example.dell.bluetoothproject.comm.Observer;
import com.example.dell.bluetoothproject.comm.ObserverManager;
import com.example.dell.bluetoothproject.fragment.BaseFragment;
import com.example.dell.bluetoothproject.fragment.DataDealFragment;
import com.example.dell.bluetoothproject.fragment.IOFragment;
import com.example.dell.bluetoothproject.fragment.OTAFragment;
import com.example.dell.bluetoothproject.fragment.StateFragment;
import com.example.dell.bluetoothproject.service.ConnectService;
import com.example.mylibrary.ble.BleManager;
import com.example.mylibrary.ble.callback.BleNotifyCallback;
import com.example.mylibrary.ble.callback.BleReadCallback;
import com.example.mylibrary.ble.callback.BleWriteCallback;
import com.example.mylibrary.ble.data.BleDevice;
import com.example.mylibrary.ble.exception.BleException;

import java.util.ArrayList;
import java.util.List;


public class ConnectActivity extends AppCompatActivity implements Observer {
    public static final String KEY_DATA = "key_data";
    private TextView  tv_connect_data;
    private BleDevice bleDevices;
    private String    uuid_service  = null;
    private List<String> listUUIDs  = new ArrayList<>();
    private List<BluetoothGattCharacteristic> bluetoothGattCharacteristicList = new ArrayList<>();
    private String   uuid_read_write_notify = null;
    private byte[] s  ;
    private List<Fragment> fragmentList;
    private Intent serviceIntent;

    private  int   currentPage   = 0;

    private String[] titles = new String[5];

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_connect);
        initView();
        initData();

    }



    //获取选择的设备的服务，通知服务，读，写的服务，打开通知接收蓝牙设备发来的消息，展示；

    //可以放在fragment中来进行执行
    private void initData() {
        //获取到选择到的设备
        bleDevices  = getIntent().getParcelableExtra("KEY_DATA");
        //如果获取到的服务是null，就关闭页面；
        if(bleDevices == null){
            finish();
        }

        //初始化数据时，启动服务，进行读写通知操作；

        serviceIntent  = new Intent(ConnectActivity.this, ConnectService.class);
        serviceIntent.putExtra(ConnectService.KEY_DATA,serviceIntent);
        startService(serviceIntent);
        //拿到设备，获取设备的服务列表;(1)
        BluetoothGatt  bluetoothGatt   = BleManager.getInstance().getBluetoothGatt(bleDevices);


        //拿到响应的服务，获取到所有服务(2)
        for (BluetoothGattService bluetoothGattService  : bluetoothGatt.getServices()){
            //拿到服务列表 bluetoothGattService ，拿到服务列表，每一组的服务里又对应着一组的特征值，判断是否可以读写，就可以判断是否可以读写；
            //获取当前的服务,当前服务的uuid：（相当于一个服务）
            uuid_service   = bluetoothGattService.getUuid().toString();
//            listUUIDs.add(uuid);
            //根据服务获取里面的一系列的特征值，获取特定的特征值
             for(BluetoothGattCharacteristic bluetoothGattCharacteristic  : bluetoothGattService.getCharacteristics()){


                 //将服务中的特征值装到集合中；
                 bluetoothGattCharacteristicList.add(bluetoothGattCharacteristic);

             }
        }

         //判断读写

        if((getCharaProp() & BluetoothGattCharacteristic.PROPERTY_READ) > 0){

           BleManager.getInstance().read(bleDevices, uuid_service, uuid_read_write_notify, new BleReadCallback() {
               @Override
               public void onReadSuccess(byte[] data) {
                   runOnUIThered(new Runnable() {
                       @Override
                       public void run() {

                        //添加要读的内容；
                       }
                   });

               }

               @Override
               public void onReadFailure(BleException exception) {
                   runOnUIThered(new Runnable() {
                       @Override
                       public void run() {
                           //添加要读的内容；
                       }
                   });

               }
           });
        }

        //读
        if ((getCharaProp() & BluetoothGattCharacteristic.PROPERTY_WRITE) > 0){

            BleManager.getInstance().write(bleDevices, uuid_service, uuid_read_write_notify, s, new BleWriteCallback() {
                @Override
                public void onWriteSuccess(int current, int total, byte[] justWrite) {
                    runOnUIThered(new Runnable() {
                        @Override
                        public void run() {
                            //添加要写的内容；
                        }
                    });
                }

                @Override
                public void onWriteFailure(BleException exception) {
                        runOnUIThered(new Runnable() {
                            @Override
                            public void run() {
                                //
                            }
                        });
                }
            });
        }


        //通知

        if ((getCharaProp() & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0 ){
            BleManager.getInstance().notify(bleDevices, uuid_service, uuid_read_write_notify, new BleNotifyCallback() {
                @Override
                public void onNotifySuccess() {
                    runOnUIThered(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }

                @Override
                public void onNotifyFailure(BleException exception) {
                    runOnUIThered(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }

                @Override
                public void onCharacteristicChanged(byte[] data) {
                    runOnUIThered(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }
            });
        }

    }

    //获取每个服务特征中读写操作，只要有读或者写或者通知的操作，是来使用该操作；

    int  chpro;
     public  int  getCharaProp(){
         //获取每个服务中的特征值对应的操作；
         for (int  i = 0; i < bluetoothGattCharacteristicList.size();i++){

             uuid_read_write_notify  = bluetoothGattCharacteristicList.get(i).getUuid().toString();
              chpro  = bluetoothGattCharacteristicList.get(i).getProperties();

         }
        return   chpro;
     }


    //初始化界面；
    private void initView() {

        tv_connect_data =  findViewById(R.id.tv_connect_data);
    }



    @Override
    public void disConnected(BleDevice bleDevice) {
     if (bleDevice != null && bleDevices != null && bleDevice.getKey().equals(bleDevices.getKey()) ){
         finish();
     }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        BleManager.getInstance().clearCharacterCallback(bleDevices);
        ObserverManager.getInstance().deleteObserver(this);
        //关闭服务;
        stopService(serviceIntent);
    }

    //在UI线程中进行；
    private  void runOnUIThered(Runnable runnable){

    }

    private void addText(TextView textView, String content) {
        textView.append(content);
        textView.append("\n");
        int offset = textView.getLineCount() * textView.getLineHeight();
        if (offset > textView.getHeight()) {
            textView.scrollTo(0, offset - textView.getHeight());
        }
    }

    //创建页面 1.状态页面；2.基本设置；3.I/O设置；4.网络设置；5.数据处理；6.固件升级;
    //需要结合UI，具体实现

    public  void  initFragment() {
         fragmentList.add(new StateFragment());
         fragmentList.add(new BaseFragment());
         fragmentList.add(new IOFragment());
         fragmentList.add(new DataDealFragment());
         fragmentList.add(new OTAFragment());

         for (Fragment fragment  : fragmentList){
             getSupportFragmentManager().beginTransaction().add(R.id.fragment,fragment).hide(fragment).commit();
         }
    }

    public  void changepage(int page){

         currentPage   = page;
         if (currentPage ==1 ){

         }else if (currentPage == 2){

         }else  if (currentPage == 3){

         }


    }

    //在按返回键，以后页面要进行切换
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    //动态加载页面;（fragment最多可以有几层）





}
