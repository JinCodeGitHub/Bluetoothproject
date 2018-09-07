package com.example.dell.bluetoothproject.internet.okhttp;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Looper;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


import okhttp3.Call;
import okhttp3.Response;


//做网络请求的回调
public  abstract  class CallBackUtils<T> {
    public  static  android.os.Handler   handler    = new android.os.Handler(Looper.getMainLooper());

    public  void onProgress(float progress,long tital){}

    public  void onError (final  Call call,final Exception e){
        handler.post(new Runnable() {
            @Override
            public void run() {
              onFailure(call,e);
            }
        });
    }
    public void  onSuccess(Call call , Response response){
        final  T obj   = onPaseRespone(call,response);
        handler.post(new Runnable() {
            @Override
            public void run() {
              onResponse(obj);
            }
        });
    }

    public  abstract  T  onPaseRespone(Call call ,Response response);

    public  abstract  void onFailure(Call call, Exception e);

    public  abstract  void onResponse(T response);


    public  static  abstract  class  CallBackDefault extends  CallBackUtils<Response>{

        @Override
        public Response onPaseRespone(Call call, Response response) {
            return response;
        }
    }

    public static  abstract  class CallBackString  extends  CallBackUtils<String>{
        @Override
        public String onPaseRespone(Call call, Response response) {
            try {
                return  response.body().string();
            } catch (IOException e) {
                new RuntimeException("failure");
                return "";
            }
        }
    }

    public static  abstract  class  CallBackBitmap extends  CallBackUtils<Bitmap> {

        private int mTargetWith;
        private int mTargetHeight;

        public CallBackBitmap() {
        }

        public CallBackBitmap(int targetWith, int targetHeight) {
            this.mTargetHeight = targetHeight;
            this.mTargetHeight = targetHeight;
        }

        public CallBackBitmap(ImageView imageView) {
            int width = imageView.getWidth();
            int height = imageView.getHeight();
            if (width <= 0 || height <= 0) {
                throw new RuntimeException("无法获取ImageView的高或者宽");

            }
            mTargetHeight = width;
            mTargetHeight = height;
        }

        @Override
        public Bitmap onPaseRespone(Call call, Response response) {
            if (mTargetWith == 0 || mTargetHeight == 0) {
                return BitmapFactory.decodeStream(response.body().byteStream());
            } else {

                 return  getZoomBitmap(response) ;
            }

        }

        //压缩获取到的图片，防止图片过大造成oom；
        private  Bitmap getZoomBitmap(Response response){

            byte[]  data  = null;
            try {
                data  =  response.body().bytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BitmapFactory.Options   options   = new BitmapFactory.Options();
            options.inJustDecodeBounds  = true;
            BitmapFactory.decodeByteArray(data,0,data.length,options);

            int  picWidth  = options.outWidth;
            int  picHeight = options.outHeight;
            int  sampleSize   = 1;

            int  heightRatio  = (int) Math.floor(picWidth/mTargetWith);
            int  widthRatio   = (int) Math.floor(picHeight/mTargetHeight);

            if (heightRatio > 1 || widthRatio  > 1){
                sampleSize   =  Math.max(heightRatio,widthRatio);
            }

            options.inSampleSize  = sampleSize;
            options.inJustDecodeBounds  = false;

            Bitmap  bitmap   = BitmapFactory.decodeByteArray(data,0,data.length,options);
            if (bitmap == null){
                throw  new RuntimeException(" Failed  to  decode stream");
            }
             return  bitmap;
        }

    }

    public  static  abstract  class  CallBackFile extends  CallBackUtils<File>{

         private  final  String  mDestFileDir;
         private  final  String  mDestFielname;

        protected CallBackFile(String mdestfiledir,String mdestfilename) {
            this.mDestFielname  = mdestfilename;
            this.mDestFileDir   = mdestfiledir;
        }

        @Override
        public File onPaseRespone(Call call, Response response) {

            InputStream  is   = null;
            byte [] buf  = new byte[1024 * 8];
            int len  = 0;
            FileOutputStream  os     = null;
            is  = response.body().byteStream();
            final   long total   =  response.body().contentLength();
            long  sum  = 0;

            File  dir   = new File(mDestFileDir);
            if (!dir.exists()){
                dir.mkdirs();
            }
            File  file    = new File(dir,mDestFielname);
            try {
                os    = new FileOutputStream(file);

                while ((len = is.read(buf)) != -1){
                    sum += len;
                    os.write(buf, 0, len);
                    final long finalSum = sum;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onProgress(finalSum * 100.0f / total,total);
                        }
                    });
                }
                os.flush();

                return file;
        }catch ( Exception e){
                e.printStackTrace();
            }finally {
                try{
                    response.body().close();
                    if (is != null) is.close();
                } catch (IOException e){
                }
                try{
                    if (os != null) os.close();
                } catch (IOException e){
                }
            }

            return null;
        }
    }
}
