package com.example.timmy.splashactivity.Activity.Activity.register;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timmy.splashactivity.Activity.Activity.getDatafromDb.requestUserData;
import com.example.timmy.splashactivity.R;
import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

import static android.content.ContentValues.TAG;

@ContentView(R.layout.activity_register_modified)
public class register extends Activity {

    private TextView id;
    private TextView textView;
    private TextView username;
    private TextView password;
    private TextView telephone;
    private TextView email;
    private TextView sex;
   private String BaseUrl="";
    private String mBaseUrl = "";

    private String origin=register.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        int mycolor = getResources().getColor(R.color.mystatuscolor);
        getWindow().setStatusBarColor(mycolor);
         BaseUrl=this.getResources().getString(R.string.BaseUrl);
        mBaseUrl = BaseUrl+"action_getdata";
        initUI();

    }

    private void initUI() {
        id= (TextView) findViewById(R.id.registerId);
        username= (TextView) findViewById(R.id.registerUsername);
        password= (TextView) findViewById(R.id.registerPassword);
        sex= (TextView) findViewById(R.id.registerSex);
        email= (TextView) findViewById(R.id.registerEmail);
        telephone= (TextView) findViewById(R.id.registerTelephone);
        textView= (TextView) findViewById(R.id.showmesg);


    }


@Event(value=R.id.register_submit)
    private void submit(View view) {

    new Thread(new Runnable() {
        @Override
        public void run() {
            getdatares();

        }
    }).start();
    }



    public void getdatares()
    {

//    File file = new File(uri);
//    if (!file.exists())
//    {
//        Toast.makeText(registerFace.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
//        return;
//    }
        Map<String, String> params = new HashMap<>();
        // params.put("password", password);
        params.put("username",username.getText()+"");
        params.put("password",password.getText()+"");
        params.put("id",id.getText()+"");
        params.put("telphone",telephone.getText()+"");
        params.put("email",email.getText()+"");
        params.put("sex",sex.getText()+"");

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
                .execute(new register.MyStringCallback());
    }
    public class MyStringCallback extends com.zhy.http.okhttp.callback.StringCallback
    {
        @Override
        public void onBefore(Request request, int id)
        {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id)
        {
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id)
        {
            e.printStackTrace();
            textView.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id)
        {
            Log.e(TAG, "onResponse：complete");
//            textView.setText("onResponse:" + response);
//
//            switch (id)
//            {
//                case 100:
//                    Toast.makeText(register.this, "http", Toast.LENGTH_SHORT).show();
//                    break;
//                case 101:
//                    Toast.makeText(register.this, "https", Toast.LENGTH_SHORT).show();
//                    break;
//            }
            direct();
        }

        @Override
        public void inProgress(float progress, long total, int id)
        {
            Log.e(TAG, "inProgress:" + progress);
          //  mProgressBar.setProgress((int) (100 * progress));
        }
    }

    private void direct() {
        Intent intent=new Intent(this,registerFace.class);
        intent.putExtra("id",id.getText()+"");
        intent.putExtra("username",username.getText()+"");
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

        finish();
    }

    @Event(value = R.id.registerReset)
    private void reset(View view) {
        username.setText("");
        password.setText("");
        sex.setText("");
        email.setText("");
        telephone.setText("");
    }
}
