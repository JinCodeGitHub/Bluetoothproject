package com.example.dell.bluetoothproject.comm;



public class BaseModel  {

    public  static  void getData(final String  parm , final CallBack<String> callBack){

        new android.os.Handler().post(new Runnable() {
            @Override
            public void run() {

               switch (parm){
                   case  "1":
                       callBack.OnSuccess("");
                       break;
                   case "2":
                       callBack.OnFailure("数据加载失败");
                       break;
                   case "3":
                       callBack.OnError();
                       break;

               }

               callBack.OnComplete();
            }
        });
    }
}
