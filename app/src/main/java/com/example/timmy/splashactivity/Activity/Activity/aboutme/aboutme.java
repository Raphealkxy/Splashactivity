package com.example.timmy.splashactivity.Activity.Activity.aboutme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.commonlibrary.utils.SPUtils;
import com.example.timmy.splashactivity.Activity.Activity.modify.modifyInfo;
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
    private LinearLayout linerlayout;
    private ImageButton left;
    private TextView center;
    private ImageButton right;
   private  String usernumber;
    private String username;
   private String telephone;
   private   String Email;




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
        linerlayout= (LinearLayout) findViewById(R.id.personinfoTitlebar);
        left= (ImageButton) linerlayout.findViewById(R.id.button_backward);
        right= (ImageButton) linerlayout.findViewById(R.id.button_edit);
        center= (TextView) linerlayout.findViewById(R.id.text_title);
        center.setText("个人资料");
        left.setBackgroundResource(R.drawable.back_arrow);
        right.setBackgroundResource(R.drawable.edit);
        left.setVisibility(View.VISIBLE);
        right.setVisibility(View.VISIBLE);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(aboutme.this,modifyInfo.class);
                intent.putExtra("username",username);
                intent.putExtra("telephone",telephone);
                intent.putExtra("Email",Email);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initdata() {
        if(SPUtils.getInstance("Login_account")!=null) {
            spUtils = SPUtils.getInstance("Login_account");
            usernumber = spUtils.getString("userNumber");
            telephone=spUtils.getString("telephone");
            username=spUtils.getString("username");

            Email=spUtils.getString("Email");
            mEmailContent.setText(Email);
            mNumContent.setText(usernumber);
            mTelContent.setText(telephone);
            mTextView.setText(username);



        }
    }
}
