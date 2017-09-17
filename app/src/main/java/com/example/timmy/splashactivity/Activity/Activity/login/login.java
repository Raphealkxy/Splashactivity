package com.example.timmy.splashactivity.Activity.Activity.login;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timmy.splashactivity.Activity.Activity.register.register;
import com.example.timmy.splashactivity.Activity.Activity.utils.LoginUser;
import com.example.timmy.splashactivity.R;
import com.google.gson.Gson;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

import static android.content.ContentValues.TAG;


@ContentView(R.layout.activity_login_modified)
public class login extends Activity{




    private TextView username;
    private TextView password;
    private TextView textView;
    private String BaseUrl="";
    private String mBaseUrl = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  x.view().inject(this);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        x.view().inject(this);

        Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.fade);
        getWindow().setExitTransition(slide);
        getWindow().setEnterTransition(slide);
        getWindow().setReenterTransition(slide);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        BaseUrl=this.getString(R.string.app_name);
        mBaseUrl = BaseUrl+"CheckLogin";
        init();
    }

    private void init() {

        username= (TextView) findViewById(R.id.username);
        password= (TextView) findViewById(R.id.password);
        textView= (TextView) findViewById(R.id.login_showmesg);
    }


    @Event(value = R.id.register_user)
    private void direct(View view) {
        Intent intent=new Intent(this,register.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());


      //  finish();
    }
@Event(value = R.id.login)
    private void submit(View view) {
        Toast.makeText(login.this, "hahah", Toast.LENGTH_SHORT).show();
       new Thread(new Runnable() {
           @Override
           public void run() {
               postString(username.getText()+"",password.getText()+"");

           }
       }).start();


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
