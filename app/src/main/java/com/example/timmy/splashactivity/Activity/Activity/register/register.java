package com.example.timmy.splashactivity.Activity.Activity.register;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timmy.splashactivity.Activity.Activity.HandlerResult;
import com.example.timmy.splashactivity.Activity.Activity.NetRequest;
import com.example.timmy.splashactivity.R;
import com.timmy.data.UrlUtils;


import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;



@ContentView(R.layout.activity_register_modified)
public class register extends Activity {

    private TextView id;
    private TextView textView;
    private TextView username;
    private TextView password;
    private TextView telephone;
    private TextView email;
    private TextView sex;
    private Map<String, String> params;
    private NetRequest netrequest;

    private String origin = register.class.getName();


    private void showTip(String mess) {
        Toast.makeText(register.this, mess, Toast.LENGTH_SHORT).show();
    }


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

        initUI();


    }

    public class myHandlerResult extends HandlerResult {

        @Override
        public void success() {
            //direct();
            Intent intent = new Intent(register.this, registerFace.class);
            intent.putExtra("id", id.getText() + "");
            intent.putExtra("username", username.getText() + "");
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(register.this).toBundle());

            finish();
        }

        @Override
        public void failed() {
            showTip("注册失败");
        }
    }

    private void initdata() {
       params = new HashMap<>();
        // params.put("password", password);
        params.put("username", username.getText() + "");
        params.put("password", password.getText() + "");
        params.put("id", id.getText() + "");
        params.put("telphone", telephone.getText() + "");
        params.put("email", email.getText() + "");
        params.put("sex", sex.getText() + "");
    }

    private void initUI() {
        id = (TextView) findViewById(R.id.registerId);
        username = (TextView) findViewById(R.id.registerUsername);
        password = (TextView) findViewById(R.id.registerPassword);
        sex = (TextView) findViewById(R.id.registerSex);
        email = (TextView) findViewById(R.id.registerEmail);
        telephone = (TextView) findViewById(R.id.registerTelephone);
        textView = (TextView) findViewById(R.id.showmesg);


    }


    @Event(value = R.id.register_submit)
    private void submit(View view) {

        initdata();
        Toast.makeText(register.this,params.toString(),Toast.LENGTH_SHORT).show();
       netrequest = new NetRequest(params, UrlUtils.NET_REGISTER,register.this);

        netrequest.handlerResult = new register.myHandlerResult();
        netrequest.execute();
    }
}

