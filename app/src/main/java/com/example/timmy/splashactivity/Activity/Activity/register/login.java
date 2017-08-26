package com.example.timmy.splashactivity.Activity.Activity.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timmy.splashactivity.R;
import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;


import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

import static android.content.ContentValues.TAG;

public class login extends Activity  implements View.OnClickListener{



    private Button resbtn;
    private Button login;
    private TextView username;
    private TextView password;
    private TextView textView;
    private String mBaseUrl = "http://192.168.1.109:8080/AMS/CheckLogin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        resbtn= (Button) findViewById(R.id.register_user);
        login= (Button) findViewById(R.id.login);
        username= (TextView) findViewById(R.id.username);
        password= (TextView) findViewById(R.id.password);
       resbtn.setOnClickListener(this);
       login.setOnClickListener(this);
        textView= (TextView) findViewById(R.id.login_showmesg);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.register_user:
             direct();
                break;
            case R.id.login:
                submit();
                break;
            default:
                break;






        }
    }

    private void direct() {
        Intent intent=new Intent(this,register.class);
        startActivity(intent);


        finish();
    }

    private void submit() {
        Toast.makeText(login.this, "hahah", Toast.LENGTH_SHORT).show();

        postString(username.getText()+"",password.getText()+"");

    }

    public void postString(String username,String password)
    {
        String url = mBaseUrl;
        com.zhy.http.okhttp.OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content(new Gson().toJson(new LoginUser(username, password)))
                .build()
                .execute(new login.MyStringCallback());

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
            textView.setText("onResponse:" + response);

            switch (id)
            {
                case 100:
                    Toast.makeText(login.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(login.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id)
        {
            Log.e(TAG, "inProgress:" + progress);
            //  mProgressBar.setProgress((int) (100 * progress));
        }
    }
}
