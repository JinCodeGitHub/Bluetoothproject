package com.example.unlock;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GnssNavigationMessage;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

//    private  Testreceiver testreceiver;

    private LocationManager locationManager;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     /*   testreceiver   = new Testreceiver();
        IntentFilter intentFilter   = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        this.registerReceiver(testreceiver,intentFilter);*/

       /* findViewById(R.id.bt_sendbroadcast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent   = new Intent();
                intent.setAction("com.example.unlock");
                sendBroadcast(intent);
            }
        });*/

      /* String  s  = "ser";


       String testdata  = "{\"dev_id\":\"00000000-0000-0000-0000-000000009876\",\"stn_mode\":\"base\",\"stn_type\":\"major\",\"latitude\":111343,\"longitude\":4004.302208,\"altitude\":102.126600,\"fixmode\":1,\"sat_num\":17,\"hdop\":0.600000,\"net_mode\":\"wired\",\"net_link\":\"disconnected\",\"eth_ipaddr\":\"192.168.8.201\",\"ntrip_mode\":\"server\",\"ntrip_link\":\"disconnected\",\"server_dsthost\":\"station.navinfo.com\",\"server_dstport\":\"80003\",\"server_dstmount\":\"mpstn03\",\"server_dstuser\":\"stn02\",\"server_dstpassword\":\"12345612\"}";
       if (s.contains("ser") && !s.contains("sser")){
           Toast.makeText(MainActivity.this,"zhadao",Toast.LENGTH_SHORT).show();
       }

      Map<Integer, String> reString  =  StringUtils(testdata);
       Log.i("inputdata", reString.get(18));*/


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            Log.i("MainActivity", "true");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            locationManager.registerGnssNavigationMessageCallback(callback);



     } else {
         Intent GPSIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
         startActivityForResult(GPSIntent,100);

     }






    }


    GnssNavigationMessage.Callback callback  = new GnssNavigationMessage.Callback() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onGnssNavigationMessageReceived(GnssNavigationMessage event) {
             super.onGnssNavigationMessageReceived(event);

            Log.i("Gnss", String.valueOf(event.getData()));
//                 event.getData();
        }

        @Override
        public void onStatusChanged(int status) {
            super.onStatusChanged(status);
        }
    };



  /*  GnssMeasurementsEvent.Callback  callback1   = new GnssMeasurementsEvent.Callback() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onGnssMeasurementsReceived(GnssMeasurementsEvent eventArgs) {
//                 super.onGnssMeasurementsReceived(eventArgs);
            eventArgs.getMeasurements();
            Log.i("GnssEvent", String.valueOf(eventArgs.getMeasurements()));
        }

        @Override
        public void onStatusChanged(int status) {
            super.onStatusChanged(status);
        }
    };
*/
    /**
     * 主要是通过该方法来返回一个接收到的字符串的一个key值，但是要返回的key不可以确定
     * （默认给所有的key值，都排上序号，方便后续拿到数据的时候直接从序号上拿）
     * 如果数据不全，不是完整的解析数据，怎么处理；
     * 需要考虑网络信号质量，以及运营商，也就是带来的字段的增加；
     * @param inputdata
     * @return
     */

    public Map<Integer,String> StringUtils(String inputdata){

        ArrayList <String> enddata  =  new ArrayList<>();
        Map<Integer ,String > map  = new HashMap<>();
        //需要进行判空处理；
        String  firstdata = inputdata.substring(1,inputdata.length()-1);
        Log.i("inputdata",firstdata);
        String [] str  = firstdata.split(",");
        for (int i = 0; i < str.length; i ++){
            String  seconddata = str[i];
//            Log.i("inputdata",seconddata);
            String [] keydata = seconddata.split(":");
            for (int j = 0 ; j < keydata.length;j ++){

//                Log.i("inputdata",keydata[j]);
                if (j ==0 ){

                    enddata.add(keydata[0]);
                    map.put(i,keydata[0]);
                }
            }
        }
        //返回一个值还是一个列表，应该是返回一个值
     /*   for (int n = 0 ; n < enddata.size();n++){
//            Log.i("inputdata",enddata.get(n));

        }*/
         return map;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        unregisterReceiver(new Testreceiver());

    }

  /*  public  class  Testreceiver  extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BluetoothAdapter.ACTION_STATE_CHANGED)){

                Toast.makeText(MainActivity.this,"com.example.unlock",Toast.LENGTH_SHORT).show();
                Log.i("com.example.unlock","link");
            }
        }
    }*/



}
