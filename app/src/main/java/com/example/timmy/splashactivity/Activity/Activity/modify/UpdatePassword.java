package com.example.timmy.splashactivity.Activity.Activity.modify;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.commonlibrary.utils.SPUtils;
import com.example.commonlibrary.utils.ToastUtils;
import com.example.timmy.splashactivity.Activity.Activity.HandlerResult;
import com.example.timmy.splashactivity.Activity.Activity.NetRequest;
import com.example.timmy.splashactivity.Activity.Activity.login.loginActivity;
import com.example.timmy.splashactivity.Activity.Activity.register.register;
import com.example.timmy.splashactivity.R;
import com.timmy.data.UrlUtils;

import java.util.HashMap;
import java.util.Map;

public class UpdatePassword extends Activity {

    private LinearLayout tittlelayout;
    private TextView textView;
    private ImageButton back;
    private ImageButton edit;
    private Button submitpassword;
    private TextView newpassword;
    private TextView oldpassword;
    private TextView oknewpassword;
    private String password;
    private  String username;
    private SPUtils spUtils;
    private Map<String, String> params;
    private NetRequest netrequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
//        //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        //设置状态栏颜色
        int mycolor = getResources().getColor(R.color.mystatuscolor);
        getWindow().setStatusBarColor(mycolor);
        initui();
        initdata();
    }

    private void initdata() {

        if(SPUtils.getInstance("Login_account")!=null) {
            spUtils = SPUtils.getInstance("Login_account");
          username=  spUtils.getString("username");
            password=spUtils.getString("password");
        }
        textView.setText("修改密码");
        back.setBackgroundResource(R.drawable.back_arrow);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submitpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newpass=newpassword.getText()+"";
                String oknewwpass=oknewpassword.getText()+"";
                String oldpass=oldpassword.getText()+"";
                if(!newpass.equals(oknewwpass)){
                    ToastUtils.show(UpdatePassword.this,"新密码前后输入不一致",2);
                    return;
                }
                if(!oldpass.equals(password)){
                  //  ToastUtils.show(UpdatePassword.this,password,2);

                    ToastUtils.show(UpdatePassword.this,"旧密码输入错误",2);
                    return;
                }

                if(oldpass.equals(newpass))
                {
                    ToastUtils.show(UpdatePassword.this,"新旧密码一致",2);
                    return;
                }

                params = new HashMap<>();
                // params.put("password", password);
                params.put("username", username);
                params.put("newpassword",newpass);
                netrequest = new NetRequest(params, UrlUtils.NET_MODIFIEDPASSWORD,UpdatePassword.this,1);

                netrequest.handlerResult = new myHandlerResult();
                netrequest.execute();



            }
        });

    }

    class myHandlerResult extends HandlerResult
    {
        @Override
        public void success() {
            ToastUtils.show(UpdatePassword.this,"修改密码成功，请重新登录",3);
            spUtils=SPUtils.getInstance("Login_account");
            spUtils.clear();
            Intent logoutIntent = new Intent(UpdatePassword.this, loginActivity.class);
            logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(logoutIntent);

        }

        @Override
        public void failed() {
            ToastUtils.show(UpdatePassword.this,"修改密码失败",2);


        }
    }

    private void initui() {
        tittlelayout= (LinearLayout) findViewById(R.id.updatepasswordTitlebar);
        textView= (TextView) tittlelayout.findViewById(R.id.text_title);
        back= (ImageButton) tittlelayout.findViewById(R.id.button_backward);
        edit= (ImageButton) tittlelayout.findViewById(R.id.button_edit);
        submitpassword= (Button) findViewById(R.id.updatepassword_submit);
        newpassword= (TextView) findViewById(R.id.newpassword);
        oldpassword= (TextView) findViewById(R.id.oldpassword);
        oknewpassword= (TextView) findViewById(R.id.okpassword);
    }
}
