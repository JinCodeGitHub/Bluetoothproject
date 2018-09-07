package com.example.bledemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bledemo.bean.NavinfoBean;
import com.example.bledemo.bleutils.JsonTools;
import com.example.mylibrary.ble.BleManager;
import com.example.mylibrary.ble.callback.BleNotifyCallback;
import com.example.mylibrary.ble.callback.BleReadCallback;
import com.example.mylibrary.ble.callback.BleWriteCallback;
import com.example.mylibrary.ble.data.BleDevice;
import com.example.mylibrary.ble.exception.BleException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


public class MainActivity extends AppCompatActivity {

    private Context context;
    public static final String KEY_DATA = "key_data";
    private  static  int RQUESTCODE  = 100;
    private BluetoothAdapter bluetoothAdapter  = BluetoothAdapter.getDefaultAdapter();

    private LinearLayout ll_1,ll_2,ll_3,ll_4,ll_5,ll_6,ll_7,ll_8,ll_9,ll_10,ll_11,
                         ll_12,ll_13,ll_14,ll_15,ll_16,ll_17,ll_18,ll_19,ll_20,
                         ll_21,ll_22,ll_23;

    private Button  bt_setting;
    private Button  bt_senderr;
    private TextView  tv_productID;
    private TextView  tv_productIDs;
    private TextView  tv_workstatus;
    private TextView  tv_workmode;
    private TextView  tv_worktype;
    private TextView  tv_loactioninfo;
    private TextView  tv_latitude;
    private TextView  tv_setlatitude;
    private TextView  tv_longitude;
    private TextView  tv_setlongitude;
    private TextView  tv_altitude;
    private TextView  tv_setaltitude;
    private TextView  tv_fixmode;
    private TextView  tv_setfixmode;
    private TextView  tv_satnum;
    private TextView  tv_setsatnum;
    private TextView  tv_HDOP;
    private TextView  tv_setHDOP;
    private TextView  tv_net;
    private TextView  tv_netmode;
    private TextView  tv_setnetmode;
    private TextView  tv_rssi;
//    private TextView  tv_setrssi;
    private TextView  tv_setvendor;
    private TextView  tv_netLink;
    private TextView  tv_setnetlink;
    private TextView  tv_ipaddr;
    private TextView  tv_setipaddr;
    private TextView  tv_ntrip;
    private TextView  tv_ntripmode;
    private TextView  tv_setntripmode;
    private TextView  tv_Host;
    private TextView  tv_sethost;
    private TextView  tv_port;
    private TextView  tv_setport;
    private TextView  tv_mount;
    private TextView  tv_setmount;
    private TextView  tv_user;
    private TextView  tv_setuser;
    private TextView  tv_pwd;
    private TextView  tv_setpwd;
    private Button    bt_setcomplete;
    private LinearLayout ll_25;
    private TextView   tv_ntripLink;
    private TextView   tv_setntriplink;

    private String    uuid_service  = null;
    private List<String> listUUIDs  = new ArrayList<>();
    private List<BluetoothGattCharacteristic> bluetoothGattCharacteristicList = new ArrayList<>();
    private String   uuid_read_write_notify = null;
    private byte[] s  = {'d','d','d','d','d','d','d','d','d','d','d','d','d','d','d','d','d','d','d','d','d','d','d','d','d','d','d','d','d','d','d','d','d','d'} ;

    private  BleDevice bleDevice;
    private static final  String   KEY_DEVICE = "Key_device";
//    private  static  final  String  UUID  = "0000180F-0000-1000-8000-00805F9B34FB";

    //给定的uuid
    private  String   service_UUID  = "0003cdd0-0000-1000-8000-00805f9b0131";
    private  List<Integer> choplist  = new ArrayList<>();

    private  ArrayList<NavinfoBean> beanArrayList;

    private  String receivedata;

    private  String  CONNECT  = ">BT Connected!\r\n";
    private  String  DISCONNECT = ">BT Disconnected!\r\n";

    private  BluetoothGattCharacteristic bluetoothGattCharacteristic;

    private ByteBuf byteBuf   = Unpooled.buffer(1024*4);
    private byte[] bytes    = new byte[1024*2];

    private   StringBuffer stringBuffer = new StringBuffer();

    //记录次数；
//    private  int  Count = 0  ;

    private BleStateChangeReceiver bleStateChangeReceiver;

    private  int  DisConnectCode ;
    private  String  COMP  = "";
    int sum = 0;



    //需要考虑蓝牙不稳定的情况

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context   = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_status);

        bleDevice  = getIntent().getParcelableExtra(KEY_DEVICE);
        Log.i(KEY_DEVICE, String.valueOf(bleDevice));
        Log.i("sssssssssssssss_ble1", String.valueOf(bleDevice));

        initView();

        if (BleManager.getInstance().isConnected(bleDevice)){
            getReadorWrite(bleDevice);
            getNotify(bleDevice);
        }

        Log.i("sssssssssssssss_ble2", String.valueOf(bleDevice));

        timer.schedule(timerTask,5000,5000);

        //订阅一个广播
        bleStateChangeReceiver  = new BleStateChangeReceiver();
        IntentFilter intentFilter    = new IntentFilter();
        intentFilter.addAction("com.example.bledemos");
        registerReceiver(bleStateChangeReceiver,intentFilter);


        Log.i("isConnected! = " , String.valueOf(BleManager.getInstance().isConnected(bleDevice)));
    }

    private void initView() {
        ll_1 = findViewById(R.id.ll_1);
        ll_2 = findViewById(R.id.ll_2);
        ll_3 = findViewById(R.id.ll_3);
        ll_4 = findViewById(R.id.ll_4);
        ll_5 = findViewById(R.id.ll_5);
//        ll_6 = findViewById(R.id.ll_6);
        ll_7 = findViewById(R.id.ll_7);
        ll_8 = findViewById(R.id.ll_8);
        ll_9 = findViewById(R.id.ll_9);
        ll_10 = findViewById(R.id.ll_10);
        ll_11 = findViewById(R.id.ll_11);
        ll_12 = findViewById(R.id.ll_12);
        ll_13 = findViewById(R.id.ll_13);
        ll_14 = findViewById(R.id.ll_14);
        ll_15 = findViewById(R.id.ll_15);
        ll_16 = findViewById(R.id.ll_16);
        ll_17 = findViewById(R.id.ll_17);
        ll_18 = findViewById(R.id.ll_18);
        ll_19 = findViewById(R.id.ll_19);
        ll_20 = findViewById(R.id.ll_20);
        ll_21 = findViewById(R.id.ll_21);
        ll_22 = findViewById(R.id.ll_22);
        ll_23 = findViewById(R.id.ll_23);
        bt_setting = findViewById(R.id.bt_setting);
        bt_senderr = findViewById(R.id.bt_senderr);
        tv_productID = findViewById(R.id.tv_productID);
        tv_productIDs = findViewById(R.id.tv_productIDs);
        tv_workstatus = findViewById(R.id.tv_workstatus);
        tv_workmode = findViewById(R.id.tv_workmode);
        tv_worktype = findViewById(R.id.tv_worktype);
//        tv_loactioninfo = findViewById(R.id.tv_loactioninfo);
        tv_latitude = findViewById(R.id.tv_latitude);
        tv_setlatitude = findViewById(R.id.tv_setlatitude);
        tv_longitude = findViewById(R.id.tv_longitude);
        tv_setlongitude = findViewById(R.id.tv_setlongitude);
        tv_altitude = findViewById(R.id.tv_altitude);
        tv_setaltitude = findViewById(R.id.tv_setaltitude);
        tv_fixmode = findViewById(R.id.tv_fixmode);
        tv_setfixmode = findViewById(R.id.tv_setfixmode);
        tv_satnum = findViewById(R.id.tv_satnum);
        tv_setsatnum = findViewById(R.id.tv_setsatnum);
        tv_HDOP = findViewById(R.id.tv_HDOP);
        tv_setHDOP = findViewById(R.id.tv_setHDOP);
        tv_net = findViewById(R.id.tv_net);
        tv_netmode = findViewById(R.id.tv_netmode);
        tv_setnetmode = findViewById(R.id.tv_setnetmode);
        tv_rssi = findViewById(R.id.tv_rssi);
//        tv_setrssi = findViewById(R.id.tv_setrssi);
        tv_setvendor = findViewById(R.id.tv_setvendor);//运营商
        tv_netLink = findViewById(R.id.tv_netLink);
        tv_setnetlink = findViewById(R.id.tv_setnetlink);
        tv_ipaddr = findViewById(R.id.tv_ipaddr);
        tv_setipaddr = findViewById(R.id.tv_setipaddr);
        tv_ntrip = findViewById(R.id.tv_ntrip);
        tv_ntripmode = findViewById(R.id.tv_ntripmode);
        tv_setntripmode = findViewById(R.id.tv_setntripmode);
        tv_Host = findViewById(R.id.tv_Host);
        tv_sethost = findViewById(R.id.tv_sethost);
        tv_port = findViewById(R.id.tv_port);
        tv_setport = findViewById(R.id.tv_setport);
        tv_mount = findViewById(R.id.tv_mount);
        tv_setmount = findViewById(R.id.tv_setmount);
        tv_user = findViewById(R.id.tv_user);
        tv_setuser = findViewById(R.id.tv_setuser);
        tv_pwd = findViewById(R.id.tv_pwd);
        tv_setpwd = findViewById(R.id.tv_setpwd);
        bt_setcomplete = findViewById(R.id.bt_setcomplete);
        ll_25    =   findViewById(R.id.ll_25);
        tv_ntripLink  = findViewById(R.id.tv_ntripLink);
        tv_setntriplink = findViewById(R.id.tv_setntriplink);

        bt_setting.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent   = new Intent(MainActivity.this, SettingActivity.class);
             intent.putExtra(KEY_DEVICE,bleDevice);
             intent.putExtra("UUID_WRITE",uuid_read_write_notify);
             intent.putExtra("UUID_SERVICE",uuid_service);
             startActivity(intent);
         }
     });
        }

    //在获取了设备以后，进行连接
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)

    public  void getReadorWrite(BleDevice bleDevices){

        //如果获取到的服务是null，就关闭页面；
        if(bleDevices == null){
            finish();
        }

        //拿到设备，获取设备的服务列表;(1)
        BluetoothGatt bluetoothGatt   = BleManager.getInstance().getBluetoothGatt(bleDevices);

        if (bluetoothGatt != null){
            //拿到响应的服务，获取到所有服务(2)
            for (BluetoothGattService bluetoothGattService  : bluetoothGatt.getServices()){
                //拿到服务列表 bluetoothGattService ，拿到服务列表，每一组的服务里又对应着一组的特征值，判断是否可以读写，就可以判断是否可以读写；
                //获取当前的服务,当前服务的uuid：（相当于一个服务）
                uuid_service   = bluetoothGattService.getUuid().toString();
//            listUUIDs.add(uuid_service);
                //通过制定的服务获取相对应的特征值
                if (uuid_service.equals(service_UUID)){
                    //根据服务获取里面的一系列的特征值，获取特定的特征值  0003cdd0-0000-1000-8000-00805f9b0131

                    Log.i("ddddddddddddd",String.valueOf(uuid_service.equals(service_UUID)));
                    for (BluetoothGattCharacteristic bluetoothGattCharacteristic  : bluetoothGattService.getCharacteristics()){
                        //将服务中的特征值装到集合中；
                        bluetoothGattCharacteristicList.add(bluetoothGattCharacteristic);
                    }

                    break;
                }

            }
        }

        getCharaProp(bleDevices);

        }

    //获取每个服务特征中读写操作，只要有读或者写或者通知的操作，是来使用该操作；

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public  void  getCharaProp(final BleDevice bleDevices) {

        //获取每个服务中的特征值对应的操作；
        for (int i = 0; i < bluetoothGattCharacteristicList.size(); i++) {

            uuid_read_write_notify = bluetoothGattCharacteristicList.get(i).getUuid().toString();
          int   chpro = bluetoothGattCharacteristicList.get(i).getProperties();
            bluetoothGattCharacteristic = bluetoothGattCharacteristicList.get(i);

            //判断读
            if ((chpro & BluetoothGattCharacteristic.PROPERTY_READ) > 0) {

                BleManager.getInstance().read(bleDevices, uuid_service, uuid_read_write_notify, new BleReadCallback() {
                    @Override
                    public void onReadSuccess(byte[] data) {

                    }

                    @Override
                    public void onReadFailure(BleException exception) {

                    }
                });
            }

      /*      //写
            if ((chpro & BluetoothGattCharacteristic.PROPERTY_WRITE) > 0) {


                BleManager.getInstance().write(bleDevices, uuid_service, uuid_read_write_notify, CONNECT.getBytes(), new BleWriteCallback() {
                    @Override
                    public void onWriteSuccess(int current, int total, final byte[] justWrite) {
                        Log.i("PROPERTY_WRITE","onWriteSuccess");
                    }
                    @Override
                    public void onWriteFailure(BleException exception) {
                    }
                });
            }*/

            //写没有反应
            if ((chpro & BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE) > 0) {

                BleManager.getInstance().write(bleDevices, uuid_service, uuid_read_write_notify, CONNECT.getBytes(), new BleWriteCallback() {
                    @Override
                    public void onWriteSuccess(int current, int total, byte[] justWrite) {
                        Log.i("WRITE_NO_RESPONSE","onWriteSuccess");
                        }
                    @Override
                    public void onWriteFailure(BleException exception) {

                    }
                });
            }
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public  void getNotify(BleDevice bleDevice){
        for (int  i = 0; i < bluetoothGattCharacteristicList.size();i++) {

        final String     uuid_write_notify = bluetoothGattCharacteristicList.get(i).getUuid().toString();
         int    chpro = bluetoothGattCharacteristicList.get(i).getProperties();
            bluetoothGattCharacteristic = bluetoothGattCharacteristicList.get(i);


            if ((chpro & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0 ) {

                Log.i("getNotify-bledevice = ", String.valueOf(bleDevice));
                Log.i("getNotity-uuid_service=",uuid_service);
                Log.i("getNotify-uwritenotify=",uuid_write_notify);

                BleManager.getInstance().notify(bleDevice, uuid_service, uuid_write_notify, new BleNotifyCallback() {
                    @Override
                    public void onNotifySuccess() {
                         Log.i("uuid_write_notify",uuid_write_notify);
                    }

                    @Override
                    public void onNotifyFailure(BleException exception) {

                    }

                    @Override
                    public void onCharacteristicChanged(final byte[] data) {

//                        Toast.makeText(MainActivity.this,new String(data),Toast.LENGTH_SHORT).show();
                        Log.i("dadadadadadada",new String(data));
                        receivedata = new String(data);
                        int len  = receivedata.length();
                        sum  = sum +len;

                        Log.i("sum", String.valueOf(sum));

//                        Count++;
                        //判断第一个大括号
                        if (receivedata.substring(0,1).equals("{")) {
                            stringBuffer.delete(0,stringBuffer.length());
                            stringBuffer.append(receivedata);
                        }
                        //获取大括号中间的；
                        if (!receivedata.substring(0,1).equals("{")  && !receivedata.substring(receivedata.length()-1, receivedata.length()).equals("}")){
                            stringBuffer.append(receivedata);
                        }
                        //获取最后一个括号
                        if(receivedata.substring(receivedata.length()-1, receivedata.length()).equals("}")){
                            stringBuffer.append(receivedata);
                            }
                         //判断是否拿到了完整的大括号
                        if (stringBuffer.substring(0,1).equals("{") && stringBuffer.substring(stringBuffer.length()-1,stringBuffer.length()).equals("}")) {
//                            Count++;

                            Message message = new Message();
                            message.obj = stringBuffer;
                            handler.sendMessage(message);
//                                    }

/*                            JsonTools jsonTools   = new JsonTools();
                            String  listdata  = new String(stringBuffer);

//                                if (stringBuffer.substring(0,1).equals("{") && stringBuffer.substring(stringBuffer.length()-1,stringBuffer.length()).equals("}")){

                            try {
                                beanArrayList = jsonTools.ResolveJson(listdata);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

//                                }
//
                            if (beanArrayList != null){

                                NavinfoBean navinfoBean   = beanArrayList.get(0);

                                tv_productIDs.setText(navinfoBean.getDev_id());

                                if (navinfoBean.getStn_mode().equals("base")){
                                    tv_workmode.setText("基准站");
                                }else if (navinfoBean.getStn_mode().equals("")){
                                    tv_workmode.setText("流动站");
                                }
                                tv_setlatitude.setText(navinfoBean.getLatitude());
                                tv_setaltitude.setText(navinfoBean.getAltitude());
                                tv_setlongitude.setText(navinfoBean.getLongitude());
                                tv_setfixmode.setText(navinfoBean.getFixmode());
                                tv_setsatnum.setText(navinfoBean.getSat_num());
                                tv_setHDOP.setText(navinfoBean.getHdop());
                                Log.i("getHdop = ",navinfoBean.getHdop());
//                                    tv_setnetmode.setText(navinfoBean.getNet_mode());
                                if (navinfoBean.getNet_mode().equals("wired")){
                                    ll_14.setVisibility(View.GONE);
                                    tv_setnetmode.setText("有线网络");
                                }else if(navinfoBean.getNet_mode().equals("mobile")){
                                    ll_14.setVisibility(View.VISIBLE);
                                    tv_setnetmode.setText("无线网络");
                                    tv_setrssi.setText(navinfoBean.getRssi());
                                    tv_setvendor.setText(navinfoBean.getVendor());
                                }

//                                    tv_setnetlink.setText(navinfoBean.getNet_link());
                                if (navinfoBean.getNet_link().equals("disconnected")){
                                    tv_setnetlink.setText("未连接");
                                }else if(navinfoBean.getNet_link().equals("connected")){
                                    tv_setnetlink.setText("已连接");
                                }

                                tv_setipaddr.setText(navinfoBean.getEth_ipaddr());
//                                    tv_setntriplink.setText(navinfoBean.getNtrip_link());
                                if (navinfoBean.getNtrip_link().equals("disconnected")){
                                    tv_setntriplink.setText("未连接");
                                }else if (navinfoBean.getNtrip_link().equals("connected")){
                                    tv_setntriplink.setText("已连接");
                                }

                                tv_setntripmode.setText(navinfoBean.getNtrip_mode());


                                tv_sethost.setText(navinfoBean.getServer_dsthost());
                                tv_setport.setText(navinfoBean.getServer_dstport());
                                tv_setmount.setText(navinfoBean.getServer_dstmount());
                                tv_setuser.setText(navinfoBean.getServer_dstuser());
                                tv_setpwd.setText(navinfoBean.getServer_dstpassword());

                                stringBuffer.delete(0,stringBuffer.length());
                            }else {
                                tv_productIDs.setText("");
                                tv_workmode.setText("");
                                tv_setlatitude.setText("");
                                tv_setaltitude.setText("");
                                tv_setlongitude.setText("");
                                tv_setfixmode.setText("");
                                tv_setsatnum.setText("");
                                tv_setHDOP.setText("");
                                tv_setnetmode.setText("");
                                tv_setrssi.setText("");
                                tv_setnetlink.setText("");
                                tv_setvendor.setText("");
                                tv_setntriplink.setText("");
                                tv_setipaddr.setText("");
                                tv_setntripmode.setText("");
                                tv_setnetlink.setText("");
                                tv_sethost.setText("");
                                tv_setport.setText("");
                                tv_setmount.setText("");
                                tv_setuser.setText("");
                                tv_setpwd.setText("");
                            }*/
                        }else {
//                                stringBuffer.delete(0,stringBuffer.length());
                        }




                           /* if (receivedata.substring(0).equals("{") || !receivedata.substring(0).equals("{")  && !receivedata.substring(receivedata.length() - 1, receivedata.length()).equals("}") || receivedata.substring(receivedata.length() - 1, receivedata.length()).equals("}")){
                                stringBuffer.append(receivedata);
                            }*/


                        Log.i("ddddddddddddddddddd", receivedata);

/*
                        if (new String(data) == null ){
                            new AlertDialog.Builder(MainActivity.this)
                                    .setTitle("提示")
                                    .setMessage("蓝牙已断开，请保证蓝牙在连接状态！")
                                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .create().show();

                        }*/


//                               bluetoothGattCharacteristic.getValue();
//                        Toast.makeText(MainActivity.this,new String(data),Toast.LENGTH_LONG).show();
                      /*  if (data != null && data.length > 0) {
                            byteBuf.writeBytes(data);
//                            Toast.makeText(MainActivity.this, (CharSequence) byteBuf,Toast.LENGTH_LONG).show();
                            Log.i("byteBuf", String.valueOf(byteBuf));
                          *//*  while (byteBuf.readableBytes() == 1024*2){
                                ByteBuf byteBuf1  = byteBuf.readBytes(1024*2);
                                byteBuf1.readBytes(bytes);
//                                Toast.makeText(MainActivity.this,new String(bytes),Toast.LENGTH_LONG).show();
                                Log.i("dddddddddd",new String(bytes));
                                receivedata   = new String (bytes);
                                Log.i("",receivedata);
                            }*//*
                        }*/
//                        receivedata   = data.toString();
//                        Log.i("Mainlog = " ,String.valueOf(Count));
                    }
                });
            }
        }



        }

    @Override
    protected void onPause() {
        super.onPause();

       /* BleManager.getInstance().write(bleDevice, uuid_service, uuid_read_write_notify, DISCONNECT.getBytes(), new BleWriteCallback() {
            @Override
            public void onWriteSuccess(int current, int total, byte[] justWrite) {

            }

            @Override
            public void onWriteFailure(BleException exception) {

            }
        });*/
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_BACK){
             BleManager.getInstance().write(bleDevice, uuid_service, uuid_read_write_notify, DISCONNECT.getBytes(), new BleWriteCallback() {
            @Override
            public void onWriteSuccess(int current, int total, byte[] justWrite) {

            }

            @Override
            public void onWriteFailure(BleException exception) {

            }
        });

             MainActivity.this.setResult(DisConnectCode);
        }

        return super.onKeyDown(keyCode, event);
    }



    Handler handler   = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null){

                StringBuffer stringBuffer  = (StringBuffer) msg.obj;

                JsonTools jsonTools   = new JsonTools();
                String  listdata  = new String(stringBuffer);
                Log.i("listdata = " ,listdata);
//                                if (stringBuffer.substring(0,1).equals("{") && stringBuffer.substring(stringBuffer.length()-1,stringBuffer.length()).equals("}")){

                try {
                    beanArrayList = jsonTools.ResolveJson(listdata);

                    if (beanArrayList != null){

                        NavinfoBean navinfoBean   = beanArrayList.get(0);
                        if (listdata.contains("dev_id")){

                            if (!navinfoBean.getDev_id().equals(COMP)){

                                tv_productIDs.setText(navinfoBean.getDev_id());
                            }
                        }

                        if (listdata.contains("stn_mode")){

                            //设名字为汉字
                            if (!navinfoBean.getStn_mode().equals(COMP)&&navinfoBean.getStn_mode().equals("base")){
                                tv_workmode.setText("基准站");
                            }else if (navinfoBean.getStn_mode().equals(COMP)){
                                tv_workmode.setText("流动站");
                            }
                        }

                        if (listdata.contains("latitude")){

                            if(!navinfoBean.getLatitude().equals(COMP)){

                                tv_setlatitude.setText(navinfoBean.getLatitude());
                            }
                        }
                        if (listdata.contains("altitude")){

                            if (!navinfoBean.getAltitude().equals(COMP)){

                                tv_setaltitude.setText(navinfoBean.getAltitude());
                            }
                        }
                        if (listdata.contains("longitude")){

                            if (!navinfoBean.getLongitude().equals(COMP)){

                                tv_setlongitude.setText(navinfoBean.getLongitude());
                            }
                        }
                        if (listdata.contains("fixmode")){

                            if (!navinfoBean.getFixmode().equals(COMP)){

                                tv_setfixmode.setText(navinfoBean.getFixmode());
                            }
                        }
                        if (listdata.contains("sat_num")){

                            if (!navinfoBean.getSat_num().equals(COMP)){

                                tv_setsatnum.setText(navinfoBean.getSat_num());
                            }
                        }
                        if (listdata.contains("hdop")){

                            if (!navinfoBean.getHdop().equals(COMP)){

                                tv_setHDOP.setText(navinfoBean.getHdop());
                            }
                        }
                        Log.i("getHdop = ",navinfoBean.getHdop());
//                                    tv_setnetmode.setText(navinfoBean.getNet_mode());
                        //不能作为唯一性来判断：
                        if (listdata.contains("net_mode")){

                            if (!navinfoBean.getNet_mode().equals(COMP)&&navinfoBean.getNet_mode().equals("wired")){
                                ll_14.setVisibility(View.GONE);
                                tv_setnetmode.setText("有线网络");
                            }else if(!navinfoBean.getNet_mode().equals(COMP)&&navinfoBean.getNet_mode().equals("mobile")){
                                ll_14.setVisibility(View.VISIBLE);
                                tv_setnetmode.setText("无线网络");
                                //设置信号强度跟运营商
//                                tv_setrssi.setText("("+navinfoBean.getRssi()+")");
                                if (!navinfoBean.getVendor().equals(COMP) && navinfoBean.getVendor().equals("ChinaUnicom")){
                                    tv_setvendor.setText("中国联通"+"("+navinfoBean.getRssi()+")");
                                }else if (!navinfoBean.getVendor().equals(COMP) && navinfoBean.getVendor().equals("ChinaMobile")){
                                    tv_setvendor.setText("中国移动"+"("+navinfoBean.getRssi()+")");
                                }else if (!navinfoBean.getVendor().equals(COMP) && navinfoBean.getVendor().equals("ChinaTelecom")){
                                    tv_setvendor.setText("中国电信"+"("+navinfoBean.getRssi()+")");
                                }
//                                tv_setvendor.setText(navinfoBean.getVendor()+"("+navinfoBean.getRssi()+")");
                            }
                        }

//
//                 tv_setnetlink.setText(navinfoBean.getNet_link());
                        if (listdata.contains("net_link")){

                            if (!navinfoBean.getNet_link().equals(COMP) && navinfoBean.getNet_link().equals("disconnected")){
                                tv_setnetlink.setText("未连接");
                            }else if(!navinfoBean.getNet_link().equals(COMP)&&navinfoBean.getNet_link().equals("connected")){
                                tv_setnetlink.setText("已连接");
                            }
                        }
                        if (listdata.contains("eth_ipaddr")){

                            if (!navinfoBean.getEth_ipaddr().equals(COMP)){

                                tv_setipaddr.setText(navinfoBean.getEth_ipaddr());
                            }
                        }
//                                    tv_setntriplink.setText(navinfoBean.getNtrip_link());

                        if (listdata.contains("ntrip_mode")){

                            if (!navinfoBean.getNtrip_mode().equals(COMP)){

                                tv_setntripmode.setText(navinfoBean.getNtrip_mode());
                            }
                        }

                        if (listdata.contains("ntrip_link")){

                            if (!navinfoBean.getNtrip_link().equals(COMP) && navinfoBean.getNtrip_link().equals("disconnected")){
                                tv_setntriplink.setText("未连接");
                            }else if (!navinfoBean.getNtrip_link().equals(COMP)&&navinfoBean.getNtrip_link().equals("connected")){
                                tv_setntriplink.setText("已连接");
                            }
                        }

                        if (listdata.contains("server_dsthost")){

                            if (!navinfoBean.getServer_dsthost().equals("")){

                                tv_sethost.setText(navinfoBean.getServer_dsthost());
                            }
                        }
                        if (listdata.contains("server_dstport")){

                            if (!navinfoBean.getServer_dstport().equals(COMP)){

                                tv_setport.setText(navinfoBean.getServer_dstport());
                            }
                        }
                        if (listdata.contains("server_dstmount") && !listdata.contains("sserver_dstmount")){

                            if (!navinfoBean.getServer_dstmount().equals(COMP)){

                                tv_setmount.setText(navinfoBean.getServer_dstmount());
                            }
                        }
                        if (listdata.contains("server_dstuser") && !listdata.contains("seuser")){

                            if (!navinfoBean.getServer_dstuser().equals(COMP)){

                                tv_setuser.setText(navinfoBean.getServer_dstuser());
                            }
                        }
                        if (listdata.contains("server_dstpassword")){

                            if (!navinfoBean.getServer_dstpassword().equals(COMP)){

                                tv_setpwd.setText(navinfoBean.getServer_dstpassword());
                            }
                        }

                        stringBuffer.delete(0,stringBuffer.length());
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

//                                }
//


            }

        }
    };


    //注册接收蓝牙状态改变的广播
   public  class  BleStateChangeReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getStringExtra("broadcastdata").equals("com.example.bledemos")){

                Log.i("brocastdata","first brocastreceiver");

                if (!BleManager.getInstance().isConnected(bleDevice)){
                    Log.i("brocastdata","second brocastreceiver");
                    //五秒钟以后还会广播，新建dialog；
                    new AlertDialog.Builder(MainActivity.this).setMessage(R.string.dialogtips).setNegativeButton(R.string.dialogpositive, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    }).setCancelable(false)
                            .show();

                    tv_productIDs.setText("");
                    tv_workmode.setText("");
                    tv_setlatitude.setText("");
                    tv_setaltitude.setText("");
                    tv_setlongitude.setText("");
                    tv_setfixmode.setText("");
                    tv_setsatnum.setText("");
                    tv_setHDOP.setText("");
                    tv_setnetmode.setText("");
//                    tv_setrssi.setText("");
                    tv_setnetlink.setText("");
                    tv_setvendor.setText("");
                    tv_setntriplink.setText("");
                    tv_setipaddr.setText("");
                    tv_setntripmode.setText("");
                    tv_setnetlink.setText("");
                    tv_sethost.setText("");
                    tv_setport.setText("");
                    tv_setmount.setText("");
                    tv_setuser.setText("");
                    tv_setpwd.setText("");


                     //向第一个页面返回code
                     DisConnectCode  = 102;
                     //取消广播订阅
                    unregisterReceiver(bleStateChangeReceiver);
                     //取消时间订阅
                     timer.cancel();
                }



//                 Toast.makeText(MainActivity.this,"com.example.bledemos", Toast.LENGTH_SHORT).show();
               /*  Toast t     = Toast.makeText(MainActivity.this,"com.example.bledemos",Toast.LENGTH_SHORT);
                 t.setGravity(Gravity.TOP,50,50);
                 t.show();*/



            }
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (BleManager.getInstance().isConnected(bleDevice)){

            unregisterReceiver(bleStateChangeReceiver);
            timer.cancel();
        }
    }

    //创建定时任务来发送广播；
    Timer  timer   = new Timer();
    TimerTask  timerTask   = new TimerTask() {
            @Override
            public void run() {

            //设置定时任务，定时的发送广播；

            Intent intent   = new Intent();
            intent.putExtra("broadcastdata","com.example.bledemos");
            intent.setAction("com.example.bledemos");
            sendBroadcast(intent);

        }
    };
}
