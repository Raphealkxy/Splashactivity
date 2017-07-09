package com.example.timmy.splashactivity.Activity.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timmy.splashactivity.R;

public class loginActivity extends Activity implements View.OnClickListener {



    private Toast mToast;
    private Button register;
    private Button login;
    private EditText username;
    private EditText password;
    private String usernamestr;
    private String passwordstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }

    private void initView() {
        register= (Button) findViewById(R.id.register_user);
        login= (Button) findViewById(R.id.login);
        username= (EditText) findViewById(R.id.username);
        password= (EditText) findViewById(R.id.password);
        register.setOnClickListener(this);
        login.setOnClickListener(this);
        mToast = Toast.makeText(loginActivity.this, "", Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_user:
                register();
                break;
            case R.id.login:
                login();
                break;
        }
    }

    private void login() {
        usernamestr=username.getText().toString();
        passwordstr=password.getText().toString();
        if(TextUtils.isEmpty(usernamestr)&&TextUtils.isEmpty(passwordstr)) {
            showTip("输入的用户名或者密码不能为空");
            return;
        }

        if(usernamestr.equals("15416118")&&passwordstr.equals("123456"))
        {

            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
         }else{
            showTip("输入的用户名或者密码有误");
        }
    }

    private void register() {

        usernamestr=username.getText().toString();
        passwordstr=password.getText().toString();
        if(TextUtils.isEmpty(usernamestr)&&TextUtils.isEmpty(passwordstr)) {
            showTip("输入的用户名或者密码不能为空");
               return;
        }
        if(usernamestr.equals("15416118")&&passwordstr.equals("123456"))
            {
                showTip("注册成功");
            }



    }

    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }
}
