package com.example.bledemo;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.bledemo.bean.NavinfoBean;
import com.example.bledemo.bleutils.JsonTools;
import com.example.mylibrary.ble.BleManager;
import com.example.mylibrary.ble.callback.BleWriteCallback;
import com.example.mylibrary.ble.data.BleDevice;
import com.example.mylibrary.ble.exception.BleException;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     author : dell
 *     time   : 2018/07/09
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class SettingActivity extends AppCompatActivity  implements View.OnClickListener{

    private LinearLayout ll_setting_1,ll_setting_2,ll_setting_3,
            ll_setting_4,ll_setting_5,ll_setting_6,ll_setting_7;
    private Toolbar tl_setting;
    private ImageButton ib_back_setting;
    private TextView tv_setting_title;
    private Spinner  sp_stamode;
    private TextView  tv_netdata;
    private TextView  tv_setting_ntrip;
    private TextView  tv_setting_ntripdata;
    private TextView  tv_setting_hostaddr;
    private TextView  tv_setting_hostaddrdata;
    private TextView  tv_setting_port;
    private TextView  tv_setting_portdata;
    private TextView  tv_setting_mount;
    private TextView  tv_setting_mountdata;
    private TextView  tv_setting_user;
    private TextView  tv_setting_userdata;
    private TextView  tv_setting_pwd;
    private TextView  tv_setting_pwddata;
    private Button    bt_syn;
    private ArrayAdapter arrayAdapter;
    private static final  String   KEY_DEVICE = "Key_device";
    private BleDevice bleDevice;
    private ArrayList<NavinfoBean> navinfoBeans = new ArrayList<>();

    private String uuid_service;
    private String uuid_write;

    private String  ntripdata;
    private String  hostaddrdata;
    private String  protdata;
    private String  mountdata;
    private String  userdata;
    private String  pwddata;
    private String  stn_mode;

    private List<String> arrayList   = new ArrayList<>();

    private  String  stn_mode_sts ="";

    private  String  ssss;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_setting);

        bleDevice  = getIntent().getParcelableExtra(KEY_DEVICE);
        Log.i("sssssssssssssss_bledev", String.valueOf(bleDevice));
        uuid_service   = getIntent().getStringExtra("UUID_SERVICE");
        Log.i("sssssssssssssss_service",uuid_service);
        uuid_write     = getIntent().getStringExtra("UUID_WRITE");
        Log.i("sssssssssssssss_write",uuid_write);

        initView();
        initData();

       /* bt_syn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                 SetDataThread setDataThread  = new SetDataThread();
//                 setDataThread.start();

//                for (int i = 0 ;i < arrayList.size() ; i++){
//                    String  s   = setATData();

//                    Log.i("sssssssssssssss",s);

                    final String  ss    = "#DBCMD,SET,NTRIP,SERVER,DSTPORT,"+80000+",*\r\n";
                    Log.i("sssssssssssssss",ss);

                    BleManager.getInstance().write(bleDevice, uuid_service, uuid_write, ss.getBytes(),new BleWriteCallback() {
                        @Override
                        public void onWriteSuccess(int current, int total, byte[] justWrite) {
                                Log.i("sssssssssssssss","同步成功");
                        }

                        @Override
                        public void onWriteFailure(BleException exception) {
                            Log.i("sssssssssssssss","同步失败"+ ss);
                        }
                    });

                 *//*   try {
//                        setDataThread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*//*

//                }

                Toast.makeText(SettingActivity.this,"同步成功！",Toast.LENGTH_SHORT).show();

            }
        });
*/

    }


    // 初始化数据；
    private void initData() {
       //获取数据；

         tv_setting_hostaddrdata.setText("station.navinfo.com");
         tv_setting_ntripdata.setText("server2");
         tv_setting_mountdata.setText("mpstn03");
         tv_setting_portdata.setText("80003");
         tv_setting_userdata.setText("stn02");
         tv_setting_pwddata.setText("12345612");


        ntripdata  = tv_setting_ntripdata.getText().toString().trim();
        Log.i("sssssssssssssss",ntripdata);


        hostaddrdata =   tv_setting_hostaddrdata.getText().toString().trim();
        Log.i("sssssssssssssss",hostaddrdata);

        protdata  =  tv_setting_portdata.getText().toString().trim();
        Log.i("sssssssssssssss",protdata);

        mountdata  = tv_setting_mountdata.getText().toString().trim();
        Log.i("sssssssssssssss",mountdata);

        userdata  = tv_setting_userdata.getText().toString().trim();
        Log.i("sssssssssssssss",userdata);

        pwddata   = tv_setting_pwddata.getText().toString().trim();
        Log.i("sssssssssssssss",pwddata);

//         setATData();



        arrayList.add("#DBCMD,SET,NTRIP,SERVER,"+ntripdata+"*\r\n");
        arrayList.add("#DBCMD,SET,NTRIP,SERVER,DSTHOST,"+hostaddrdata+",*\r\n");
        arrayList.add("#DBCMD,SET,NTRIP,SERVER,DSTPORT,"+protdata+",*\r\n");
        arrayList.add("#DBCMD,SET,NTRIP,SERVER,DSTMOUNT,"+mountdata+",*\r\n");
        arrayList.add("#DBCMD,SET,NTRIP,SERVER,DSTUSER,"+userdata+",*\r\n");
        arrayList.add("#DBCMD,SET,NTRIP,SERVER,DSTPWD,"+pwddata+",*\r\n");

        //设置网络模式

        arrayList.add("#DBCMD,SET,NET,MODE,wired,*\r\n");

    }

    private void initView() {
//        tl_setting = findViewById(R.id.tl_setting);
//        ib_back_setting = findViewById(R.id.ib_back_setting);
//        tv_setting_title = findViewById(R.id.tv_setting_title);
        ll_setting_1 = findViewById(R.id.ll_setting_1);
        ll_setting_2 = findViewById(R.id.ll_setting_2);
        ll_setting_3 = findViewById(R.id.ll_setting_3);
        ll_setting_4 = findViewById(R.id.ll_setting_4);
        ll_setting_5 = findViewById(R.id.ll_setting_5);
        ll_setting_6 = findViewById(R.id.ll_setting_6);
        ll_setting_7 = findViewById(R.id.ll_setting_7);
        sp_stamode = findViewById(R.id.sp_stamode);
        tv_netdata = findViewById(R.id.tv_netdata);
        tv_setting_ntrip = findViewById(R.id.tv_setting_ntrip);
        tv_setting_ntripdata = findViewById(R.id.tv_setting_ntripdata);
        tv_setting_hostaddr = findViewById(R.id.tv_setting_hostaddr);
        tv_setting_hostaddrdata = findViewById(R.id.tv_setting_hostaddrdata);
        tv_setting_port = findViewById(R.id.tv_setting_port);
        tv_setting_portdata = findViewById(R.id.tv_setting_portdata);
        tv_setting_mount = findViewById(R.id.tv_setting_mount);
        tv_setting_mountdata = findViewById(R.id.tv_setting_mountdata);
        tv_setting_user = findViewById(R.id.tv_setting_user);
        tv_setting_userdata = findViewById(R.id.tv_setting_userdata);
        tv_setting_pwd = findViewById(R.id.tv_setting_pwd);
        tv_setting_pwddata = findViewById(R.id.tv_setting_pwddata);
        bt_syn             = findViewById(R.id.bt_syn);

        bt_syn.setOnClickListener(SettingActivity.this);
        //获取下来条目中的数据；
        arrayAdapter  = ArrayAdapter.createFromResource(SettingActivity.this,R.array.stn_type,R.layout.support_simple_spinner_dropdown_item);
        sp_stamode.setAdapter(arrayAdapter);

        sp_stamode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                stn_mode  = sp_stamode.getSelectedItem().toString();

                if (stn_mode.equals("辅接收机")){
                    stn_mode_sts = "minor";
                    arrayList.add("#DBCMD,SET,DEVICE,TYPE,"+stn_mode_sts +",*\r\n");
                }else if (stn_mode.equals("主接收机")){
                    stn_mode_sts = "major";
                    arrayList.add("#DBCMD,SET,DEVICE,TYPE,"+stn_mode_sts +",*\r\n");
                }
                Log.i("sssssssssssssss",stn_mode);
                Log.i("sssssssssssssss",stn_mode_sts);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    //拼装指令,设置发送的时间间隔；

    public   String   setATData(){

        for (int i = 0 ; i < arrayList.size(); i++){

            Log.i("sssssssssssssss", String.valueOf(i));
                ssss  = arrayList.get(i);

                return ssss;
        }
        /*if (stn_mode_sts.equals("major")){

            arrayList.add("#DBCMD,SET,DEVICE,TYPE,"+stn_mode_sts +",*\r\n");
            Log.i("sssssssssssssss","#DBCMD,SET,DEVICE,TYPE,"+stn_mode_sts +",*\r\n");
            arrayList.add("#DBCMD,SET,NTRIP,SERVER,*\r\n");
            arrayList.add("#DBCMD,SET,NTRIP,SERVER,DSTHOST,"+hostaddrdata+",*\r\n");
            arrayList.add("#DBCMD,SET,NTRIP,SERVER,DSTPORT,"+protdata+",*\r\n");
            arrayList.add("#DBCMD,SET,NTRIP,SERVER,DSTMOUNT,"+mountdata+",*\r\n");
            arrayList.add("#DBCMD,SET,NTRIP,SERVER,DSTUSER,"+userdata+",*\r\n");
            arrayList.add("#DBCMD,SET,NTRIP,SERVER,DSTPWD,"+pwddata+",*\r\n");

            for (int i = 0 ; i < arrayList.size(); i++){

               return  arrayList.get(i);

            }

            }*//*else  if (stn_mode_sts.equals("minor")){

            arrayList.add("#DBCMD,SET,DEVICE,TYPE,"+stn_mode_sts +",*\r\n");
            arrayList.add("#DBCMD,SET,NTRIP,SERVER,*\r\n");
            arrayList.add("#DBCMD,SET,NTRIP,SERVER,DSTHOST,"+hostaddrdata+",*\r\n");
            arrayList.add("#DBCMD,SET,NTRIP,SERVER,DSTPORT,"+protdata+",*\r\n");
            arrayList.add("#DBCMD,SET,NTRIP,SERVER,DSTMOUNT,"+mountdata+",*\r\n");
            arrayList.add("#DBCMD,SET,NTRIP,SERVER,DSTUSER,"+userdata+",*\r\n");
            arrayList.add("#DBCMD,SET,NTRIP,SERVER,DSTPWD,"+pwddata+",*\r\n");

            for (int i = 0 ; i < arrayList.size(); i++){


                return  arrayList.get(i);

            }

        }*/

        return  "";

    }

    @Override
    public void onClick(View v) {
          Log.i("sssssssssssssss", String.valueOf(arrayList.size()));
          SetDataThread setDataThread   = new SetDataThread();
          setDataThread.start();
    }

    class   SetDataThread extends Thread {

        public void run() {

            for (int i = 0 ;i < arrayList.size() ; i++){
                 String  s   = arrayList.get(i);

                 Log.i("sssssssssssssss",s);

                 BleManager.getInstance().write(bleDevice, uuid_service, uuid_write, s.getBytes(), true, new BleWriteCallback() {
                     @Override
                     public void onWriteSuccess(int current, int total, byte[] justWrite) {

                     }

                     @Override
                     public void onWriteFailure(BleException exception) {

                     }
                 });

                 try {
                     sleep(100);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }

             }
         }
     }

}
