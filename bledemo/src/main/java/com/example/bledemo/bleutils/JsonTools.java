package com.example.bledemo.bleutils;



import android.util.Log;

import com.example.bledemo.bean.NavinfoBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * <pre>
 *     author : dell
 *     time   : 2018/07/13
 *     desc   : translate json to string
 *     version: 1.0
 * </pre>
 */

public class JsonTools {

      ArrayList<NavinfoBean> navinfoBeanList  = new ArrayList<>();
      public   static  int  count  = 0;

    public  JsonTools(){

    }

    public ArrayList<NavinfoBean> ResolveJson (String data) throws JSONException {

        NavinfoBean navinfoBean   = new NavinfoBean();
        JSONObject jsonObject   = new JSONObject(data);
//        Map<Integer,String> reString  = StringUtils(data);
        if (data.contains("dev_id")){
//              String dugstr   = reString.get(0);
//            if(reString.get(0).equals("\"dev_id\"")){
                String  dev_id   = jsonObject.getString("dev_id");
                navinfoBean.setDev_id(dev_id);
//            }
        }else {
            count ++;
        }
//        navinfoBean.dev_id  = jsonObject.getString("dev_id");

        if (data.contains("stn_mode")){

//            if (reString.get(1).equals("\"stn_mode\"")){
                String  stn_mode  = jsonObject .getString("stn_mode");
                navinfoBean.setStn_mode(stn_mode);
//            }
        }else {
            count ++;
        }

        if (data.contains("stn_type")){

//            if (reString.get(2).equals("\"stn_type\"")){
                String  stn_type   = jsonObject.getString("stn_type");
                navinfoBean.setStn_type(stn_type);
//            }
        }else {
            count ++;
        }


        if (data.contains("latitude")){

//            if (reString.get(3).equals("\"latitude\"")){
                String  latitude    = jsonObject.getString("latitude");
                navinfoBean.setLatitude(latitude);
//            }
        }else {
            count ++;
        }

        if (data.contains("longitude")){

//            if (reString.get(4).equals("\"longitude\"")){
                String   longitude   = jsonObject.getString("longitude");
                navinfoBean.setLongitude(longitude);
//            }
        }else {
            count ++;
        }
        if (data.contains("altitude")){

//            if (reString.get(5).equals("\"altitude\"")){
                String   altitude  = jsonObject.getString("altitude");
                navinfoBean.setAltitude(altitude);
//            }
        }else {
            count ++;
        }
        if (data.contains("fixmode")){

//            if (reString.get(6).equals("\"fixmode\"")){
                String   fixmode    = jsonObject.getString("fixmode");
                navinfoBean.setFixmode(fixmode);
//            }
        }
        if (data.contains("sat_num")){

//            if (reString.get(7).equals("\"sat_num\"")){
                String   sat_num   = jsonObject.getString("sat_num");
                navinfoBean.setSat_num(sat_num);
//            }
        }else {
            count ++;
        }

        if (data.contains("hdop")){

//            if (reString.get(8).equals("\"hdop\"")){
                String   hdop       = jsonObject.getString("hdop");
                navinfoBean.setHdop(hdop);
//            }
        }else {
            count ++;
        }



/*
          if (data.contains("rssi")){

            if (reString.get().equals("rssi")){
                  String  rssi        = jsonObject.getString("rssi");
                  navinfoBean.setRssi(rssi);
              }
          }else {
              count ++;
          }

      *//*  String  mobile_ipaddr = jsonObject.getString("mobile_ipaddr");
        navinfoBean.setMobile_ipaddr(mobile_ipaddr);*//*

        if (data.contains("vendor")){

            if (reString.get().equals("vendor")){
                String  vendor        = jsonObject.getString("vendor");
                navinfoBean.setVendor(vendor);
            }
        }else {
            count ++;
        }*/


        if (data.contains("net_mode")){

//            if (reString.get(9).equals("\"net_mode\"")){
                String  net_mode  = jsonObject.getString("net_mode");
                navinfoBean.setNet_mode(net_mode);
                  //有线网模式****************************************
                if (net_mode.equals("wired")){

                    if (data.contains("net_link")){

//                        if (reString.get(10).equals("\"net_link\"")){
                            String  net_link  = jsonObject.getString("net_link");
                            navinfoBean.setNet_link(net_link);
//                        }
                    }else {
                        count ++;
                    }

                    if(data.contains("eth_ipaddr")){
//                        if (reString.get(11).equals("\"eth_ipaddr\"")){

                            String  eth_ipaddr  = jsonObject.getString("eth_ipaddr");
                            navinfoBean.setEth_ipaddr(eth_ipaddr);
//                        }
                    }else {
                        count++;
                    }


                    if (data.contains("ntrip_mode")){

//                        if (reString.get(12).equals("\"ntrip_mode\"")){
                            String  ntrip_mode = jsonObject.getString("ntrip_mode");
                            navinfoBean.setNtrip_mode(ntrip_mode);
//                        }

                    }else {
                        count ++;
                    }
                    if (data.contains("ntrip_link")){

//                        if (reString.get(13).equals("\"ntrip_link\"")){
                            String   ntrip_link = jsonObject.getString("ntrip_link");
                            navinfoBean.setNtrip_link(ntrip_link);
//                        }
                    }else {
                        count ++;
                    }


                    if (data.contains("server_dsthost")){

//                        if (reString.get(14).equals("\"server_dsthost\"")){
                            String  server_dsthost   = jsonObject.getString("server_dsthost");
                            navinfoBean.setServer_dsthost(server_dsthost);
//                        }

                    }else {
                        count ++;
                    }

                    if (data.contains("server_dstport")){

//                        if (reString.get(15).equals("\"server_dstport\"")){
                            String  server_dstport   = jsonObject.getString("server_dstport");
                            navinfoBean.setServer_dstport(server_dstport);
//                        }

                    }else {
                        count ++;
                    }

                    if (data.contains("server_dstmount")){

//                        if (reString.get(16).equals("\"server_dstmount\"")){
                            String  server_dstmount  = jsonObject.getString("server_dstmount");
                            navinfoBean.setServer_dstmount(server_dstmount);
//                        }

                    }else {
                        count ++;
                    }

                    if (data.contains("server_dstuser")){

//                        if (reString.get(17).equals("\"server_dstuser\"")){
                            String  server_dstuser   = jsonObject.getString("server_dstuser");
                            navinfoBean.setServer_dstuser(server_dstuser);
//                        }

                    }else {
                        count ++;
                    }

                    if (data.contains("server_dstpassword")){

//                        if (reString.get(18).equals("\"server_dstpassword\"")){
                            String  server_dstpassword  = jsonObject.getString("server_dstpassword");
                            navinfoBean.setServer_dstpassword(server_dstpassword);
//                        }

                    }else {
                        count ++;
                    }

                    //无线网模式****************************************
                }else if (net_mode.equals("mobile")){


                    if (data.contains("vendor")){

//                        if (reString.get(10).equals("\"vendor\"")){
                            String  vendor        = jsonObject.getString("vendor");
                            navinfoBean.setVendor(vendor);
//                        }
                    }else {
                        count ++;
                    }

                    if (data.contains("rssi")){

//                        if (reString.get(11).equals("\"rssi\"")){
                            String  rssi        = jsonObject.getString("rssi");
                            navinfoBean.setRssi(rssi);
//                        }
                    }else {
                        count ++;
                    }

                    if (data.contains("mobile_ipaddr")){

//                        if (reString.get(12).equals("\"mobile_ipaddr\"")){
                            String mobile_ipaddr   = jsonObject.getString("mobile_ipaddr");
                            navinfoBean.setMobile_ipaddr(mobile_ipaddr);
//                        }
                    }


                    if (data.contains("net_link")){

//                        if (reString.get(13).equals("\"net_link\"")){
                            String  net_link  = jsonObject.getString("net_link");
                            navinfoBean.setNet_link(net_link);
//                        }
                    }else {
                        count ++;
                    }



                    if(data.contains("eth_ipaddr")){
//                        if (reString.get(14).equals("\"eth_ipaddr\"")){

                            String  eth_ipaddr  = jsonObject.getString("eth_ipaddr");
                            navinfoBean.setEth_ipaddr(eth_ipaddr);
//                        }
                    }else {
                        count++;
                    }


                    if (data.contains("ntrip_mode")){

//                        if (reString.get(15).equals("\"ntrip_mode\"")){
                            String  ntrip_mode = jsonObject.getString("ntrip_mode");
                            navinfoBean.setNtrip_mode(ntrip_mode);
//                        }

                    }else {
                        count ++;
                    }
                    if (data.contains("ntrip_link")){

//                        if (reString.get(16).equals("\"ntrip_link\"")){
                            String   ntrip_link = jsonObject.getString("ntrip_link");
                            navinfoBean.setNtrip_link(ntrip_link);
//                        }
                    }else {
                        count ++;
                    }


                    if (data.contains("server_dsthost")){

//                        if (reString.get(17).equals("\"server_dsthost\"")){
                            String  server_dsthost   = jsonObject.getString("server_dsthost");
                            navinfoBean.setServer_dsthost(server_dsthost);
//                        }

                    }else {
                        count ++;
                    }

                    if (data.contains("server_dstport")){

//                        if (reString.get(18).equals("\"server_dstport\"")){
                            String  server_dstport   = jsonObject.getString("server_dstport");
                            navinfoBean.setServer_dstport(server_dstport);
//                        }

                    }else {
                        count ++;
                    }

                    if (data.contains("server_dstmount")){

//                        if (reString.get(19).equals("\"server_dstmount\"")){
                            String  server_dstmount  = jsonObject.getString("server_dstmount");
                            navinfoBean.setServer_dstmount(server_dstmount);
//                        }

                    }else {
                        count ++;
                    }

                    if (data.contains("server_dstuser")){

//                        if (reString.get(20).equals("\"server_dstuser\"")){
                            String  server_dstuser   = jsonObject.getString("server_dstuser");
                            navinfoBean.setServer_dstuser(server_dstuser);
//                        }

                    }else {
                        count ++;
                    }

                    if (data.contains("server_dstpassword")){

//                        if (reString.get(21).equals("\"server_dstpassword\"")){
                            String  server_dstpassword  = jsonObject.getString("server_dstpassword");
                            navinfoBean.setServer_dstpassword(server_dstpassword);
//                        }
                    }else {
                        count ++;
                    }

//                }
            }
        }else {
            count ++;
        }


        navinfoBeanList.add(navinfoBean);


        System.out.println("count = "+count);

        return  navinfoBeanList;
    }

    public Map<Integer,String> StringUtils(String inputdata){

        ArrayList <String> enddata  =  new ArrayList<>();
        Map<Integer ,String > map  = new HashMap<>();
        //需要进行判空处理；
        String  firstdata = inputdata.substring(1,inputdata.length()-1);
//        Log.i("inputdata",firstdata);
        String [] str  = firstdata.split(",");
        for (int i = 0; i < str.length; i ++){
            String  seconddata = str[i];
            String [] keydata = seconddata.split(":");
            for (int j = 0 ; j < keydata.length;j ++){

                if (j ==0 ){

                    enddata.add(keydata[0]);
                    map.put(i,keydata[0]);
                }
            }
        }

        return map;
    }

}
