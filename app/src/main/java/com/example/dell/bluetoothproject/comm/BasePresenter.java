package com.example.dell.bluetoothproject.comm;

public class BasePresenter <V extends  BaseView>  {

    private  V  BaseView;

    public   BasePresenter(){

    }
      //绑定view
    public  void  attachView ( V  baseView ){
        this.BaseView   = baseView;
    }
      //解除绑定
    public  void  detachView (){
        this.BaseView   = null;
    }
      //判断是否进行了绑定
    public  boolean  isViewAttach(){
        return  BaseView != null;
    }
      // 获取到数据
    public  void  getdata (String parm ){

        BaseModel.getData(parm, new CallBack<String>() {
            @Override
            public void OnSuccess( String data) {


                if (isViewAttach()){
                    BaseView.ShowData(data);
                }

            }

            @Override
            public void OnFailure(String msg) {

                if (isViewAttach()){
                    BaseView.EndProgress();
                }
            }

            @Override
            public void OnComplete() {

            }

            @Override
            public void OnError() {

            }
        });
    }
}
