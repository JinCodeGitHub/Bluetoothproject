package com.example.dell.bluetoothproject.comm;

import android.content.Context;

public interface ComBaseView  {
    //显示加载框
    public  void  ShowProgress();

    //加载结束
    public  void  EndProgress();

    //显示数据
    public  void  ShowData(String data);

    //显示toast
    public  void  ShowToast(String Msg);

    //显示错误/数据加载异常
    public  void   ShowError();
    //获取上下文
    Context  getContext();

}
