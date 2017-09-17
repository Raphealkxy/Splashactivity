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

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.example.timmy.splashactivity.Activity.Activity.MainActivity;
import com.example.timmy.splashactivity.Activity.Activity.utils.Code;
import com.example.timmy.splashactivity.Activity.Activity.register.register;
import com.example.timmy.splashactivity.R;
import com.google.gson.Gson;
import com.zhy.http.okhttp.callback.StringCallback;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

import static android.content.ContentValues.TAG;


@ContentView(R.layout.activity_login_modified)
@Route("result")
public class loginActivity extends Activity {



    private TextView username;
    private TextView password;
    private TextView textView;
   // private String mBaseUrl = "http://192.168.253.1:8080/AMS/CheckLogin";
    private String BaseUrl="";
    private String mBaseUrl="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        x.view().inject(this);
        Router.initialize(this);

        Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);
        getWindow().setExitTransition(slide);
        getWindow().setEnterTransition(slide);
        getWindow().setReenterTransition(slide);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
         BaseUrl=this.getString(R.string.BaseUrl);
        mBaseUrl = BaseUrl+"action_CheckLogin";
        init();
    }

    private void init() {

        username= (TextView) findViewById(R.id.username);
        password= (TextView) findViewById(R.id.password);

        textView= (TextView) findViewById(R.id.login_showmesg);
    }


@Event(value=R.id.register_user)
    private void direct(View view) {
        Intent intent=new Intent(this,register.class);
        startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle());


      //  finish();
    }
@Event(value=R.id.login)
    private void submit(View view) {
        Toast.makeText(loginActivity.this, "hahah", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                loginCheck(username.getText()+"",password.getText()+"");

            }
        }).start();

    }


public void loginCheck(String username,String password)
{


    Map<String, String> params = new HashMap<>();
    params.put("password", password);
    params.put("username",username);

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
            .execute(new loginActivity.MyStringCallback());
}

    public class MyStringCallback extends StringCallback
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
                    Toast.makeText(loginActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(loginActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }

            Gson gson=new Gson();
            Code code=gson.fromJson(response,Code.class);
            if(code.getCode()==1)
            {
                Toast.makeText(loginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                loginsuccess();

            }else
            {
                Toast.makeText(loginActivity.this, "密码或用户名错误", Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        public void inProgress(float progress, long total, int id)
        {
            Log.e(TAG, "inProgress:" + progress);
            //  mProgressBar.setProgress((int) (100 * progress));
        }
    }

    private void loginsuccess() {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
      //  overridePendingTransition(R.anim.enter_anim,R.anim.exit_anim);
     //   finish();
    }

}
