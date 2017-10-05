package com.example.timmy.splashactivity.Activity.Activity.login;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chenenyu.router.Router;
import com.chenenyu.router.annotation.Route;
import com.entity.User;
import com.example.commonlibrary.utils.SPUtils;
import com.example.timmy.splashactivity.Activity.Activity.HandlerResult;
import com.example.timmy.splashactivity.Activity.Activity.MainActivity;
import com.example.timmy.splashactivity.Activity.Activity.NetRequest;
import com.example.timmy.splashactivity.Activity.Activity.register.register;
import com.example.commonlibrary.utils.ToastUtils;
import com.example.timmy.splashactivity.R;
import com.google.gson.Gson;
import com.timmy.data.UrlUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;



@ContentView(R.layout.activity_login_modified)
@Route("result")
public class loginActivity extends Activity {



    private TextView username;
    private TextView password;
    private TextView textView;
    private String BaseUrl="";
    private String mBaseUrl="";
    private NetRequest netrequest;
    private SPUtils spUtils;
    private String content;



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

        init();
        initdate();
    }

    private void initdate() {
        if(SPUtils.getInstance("Login_account")!=null) {
            spUtils = SPUtils.getInstance("Login_account");
            String loginStatus = spUtils.getString("loginStatus");
            if (loginStatus.equals("login_ok")) {
            //
                //    ToastUtils.show(this,loginStatus,2);

                loginsuccesstwo();
            } else {
                return;
            }
        }else{
            return;
        }
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
        Map<String, String> params = new HashMap<>();
    params.put("password", password.getText()+"");
    params.put("username",username.getText()+"");
    netrequest = new NetRequest(params, UrlUtils.NET_LOGIN,loginActivity.this,2);

    netrequest.handlerResult = new loginActivity.myHandlerResult();
    netrequest.execute();

    }
    public class myHandlerResult extends HandlerResult {

        @Override
        public void success() {
            content=netrequest.Content;




            loginsuccess();
        }

        @Override
        public void failed() {
            ToastUtils.show(loginActivity.this,"登录失败",2);
        }
    }


    private void loginsuccess() {
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("userinfo",content);
    //    intent.putExtra("password",password.getText()+"");

        //  startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        startActivity(intent);
       //  finish();
    }

    private  void loginsuccesstwo()
    {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);

    }


}
