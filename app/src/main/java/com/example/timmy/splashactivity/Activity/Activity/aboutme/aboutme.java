package com.example.timmy.splashactivity.Activity.Activity.aboutme;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.commonlibrary.utils.SPUtils;
import com.example.timmy.splashactivity.R;

public class aboutme extends Activity {


    private ImageButton bn2;
    private TextView mTextTitle;
    private TextView mTextView;
    private TextView mNumTitle;
    private TextView mNumContent;
    private TextView mDepartTitle;
    private TextView mDepartContent;
    private TextView mTelTitle;
    private TextView mTelContent;
    private TextView mEmailTitle;
    private TextView mEmailContent;
    private SPUtils spUtils;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);
        initui();
        initdata();

    }

    private void initui() {
        mTextTitle = (TextView) findViewById(R.id.text_title);
        mTextView = (TextView) findViewById(R.id.textView);
        mNumTitle = (TextView) findViewById(R.id.num_title);
        mNumContent = (TextView) findViewById(R.id.num_content);
        mDepartTitle = (TextView) findViewById(R.id.depart_title);
        mDepartContent = (TextView) findViewById(R.id.depart_content);
        mTelTitle = (TextView) findViewById(R.id.tel_title);
        mTelContent = (TextView) findViewById(R.id.tel_content);
        mEmailTitle = (TextView) findViewById(R.id.email_title);
        mEmailContent = (TextView) findViewById(R.id.email_content);
        bn2=(ImageButton) findViewById(R.id.button_backward);
        bn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(TitleActivity.this,MainActivity.class);
                //   startActivity(intent);
                finish();
            }
        });

    }

    private void initdata() {
        if(SPUtils.getInstance("Login_account")!=null) {
            spUtils = SPUtils.getInstance("Login_account");
            String userNumber = spUtils.getString("userNumber");
            String telephone=spUtils.getString("telephone");
            String Email=spUtils.getString("Email");
            mEmailContent.setText(Email);
            mNumContent.setText(userNumber);
            mTelContent.setText(telephone);



        }
    }
}
