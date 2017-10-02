package com.example.timmy.splashactivity.Activity.Activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.example.timmy.splashactivity.Activity.Activity.utils.Code;
import com.example.commonlibrary.utils.ToastUtils;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

import static android.content.ContentValues.TAG;

/**
 * Created by Timmy on 2017/9/23.
 */

public class NetRequest {

    private Context context;
    private String mBaseUrl;
    private Map<String, String>params;
    private int Requestcode=1;//1代表上传基本信息，2代表上传图片信息
    private Message message;
    public HandlerResult handlerResult;
    public static final int NET_OK=110;
    public static final int NET_ERROR=120;
    private String mUrl;
    private int filenum;
    private String ID;
    private int type=1;//网络请求类型 1代表验证型 2代表获取实体数据型。
    public String Content="";

    public NetRequest(Map<String, String> params, String mBaseUrl,Context context,int type) {
        this.params = params;
        this.mBaseUrl = mBaseUrl;
        this.context=context;
        this.type=type;
    }

    public NetRequest(Context context, String mBaseUrl, Map<String, String> params, int requestcode, String mUrl, int filenum, String ID,int type) {
        this.context = context;
        this.mBaseUrl = mBaseUrl;
        this.params = params;
        Requestcode = requestcode;
        this.mUrl = mUrl;
        this.filenum = filenum;
        this.ID = ID;
        this.type=type;
    }

    private Handler mhandler=new Handler()
    {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what)
            {
                case NET_OK:
                    if(type==1) {

                        Code code = (Code) msg.obj;
                        if (code.getCode() == 2) {

                            handlerResult.success();

                        } else {

                            handlerResult.failed();

                        }
                    }else{
                       Content= (String) msg.obj;

                        if(Content!=null){
                           // ToastUtils.show(context,Content,1);
                            handlerResult.success();

                        }else {
                            handlerResult.failed();

                        }
                    }
                    break;
                case  NET_ERROR:
                    ToastUtils.show(context,"网络出现异常",2);
                    break;
                default:
                    break;
            }
        }
    };

    private void donet(Map<String, String> params)
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("APP-Key", "APP-Secret222");
        headers.put("APP-Secret", "APP-Secret111");


        String url = mBaseUrl;

        com.zhy.http.okhttp.OkHttpUtils.post()//
                //.addFile()//
                .url(url)//
                .params(params)//
                .headers(headers)//
                .build()//
                .execute(new NetRequest.MyStringCallback());
    }


    public void multiFileUpload(Map<String, String>params)
    {
        File file = new File(mUrl);
        if (!file.exists())
        {
            ToastUtils.show(context,"文件不存在，请修改文件路径",2);
            return;
        }


        String url = mBaseUrl;
        com.zhy.http.okhttp.OkHttpUtils.post()//
                .addFile("upload", ID+"_0"+filenum+".png", file)
                .url(url)
                .params(params)//
                .build()//
                .execute(new MyStringCallback());
    }



    public class MyStringCallback extends com.zhy.http.okhttp.callback.StringCallback
    {


        @Override
        public void onError(Call call, Exception e, int id)
        {
            e.printStackTrace();
          //  textView.setText("onError:" + e.getMessage());
            message.what=NET_ERROR;
            message.obj=e.getMessage();
            mhandler.sendMessage(message);
        }

        @Override
        public void onResponse(String response, int id)
        {
            Log.e(TAG, "onResponse：complete");
            if(type==1) {


                Gson gson = new Gson();
                Code code = gson.fromJson(response, Code.class);
                if (code != null) {
                    message.what = NET_OK;
                    message.obj = code;
                }
            }else{
                if(response!=null){
                    message.what=NET_OK;
                    message.obj=response;
                  //  Content=response;
                }

            }
            mhandler.sendMessage(message);



        }

        @Override
        public void inProgress(float progress, long total, int id)
        {
            Log.e(TAG, "inProgress:" + progress);
            //  mProgressBar.setProgress((int) (100 * progress));
        }
    }

    public void execute()
    {
        message=mhandler.obtainMessage();

        new Thread(new Runnable() {
            @Override
            public void run() {
                if(Requestcode==1) {
                    donet(params);
                }else{
                    multiFileUpload(params);
                }


            }
        }).start();





    }
}
