package com.example.timmy.splashactivity.Activity.Activity.modify;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.commonlibrary.utils.SPUtils;
import com.example.commonlibrary.utils.ToastUtils;
import com.example.timmy.splashactivity.Activity.Activity.HandlerResult;
import com.example.timmy.splashactivity.Activity.Activity.NetRequest;
import com.example.timmy.splashactivity.Activity.Activity.aboutme.aboutme;
import com.example.timmy.splashactivity.R;
import com.timmy.data.UrlUtils;

import java.util.HashMap;
import java.util.Map;

public class modifyInfo extends Activity {

    private EditText mUpdateNum;
    private EditText mUpdateTelephone;
    private EditText mUpadateEmail;
    private Button mUpdateinfoSubmit;
    private LinearLayout updateInfoTitlebar;
   private ImageButton left;
    private ImageButton right;
    private TextView center;
  private Map<String, String> params;
 private NetRequest netrequest;
private SPUtils spUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_info);
        initui();
        initdata();


    }

    private void initdata() {
        center.setText("修改信息");
        Intent intent=getIntent();
       mUpdateNum.setText(intent.getStringExtra("username"));
        mUpdateTelephone.setText(intent.getStringExtra("telephone"));
        mUpadateEmail.setText(intent.getStringExtra("Email"));
         left.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });

        mUpdateinfoSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spUtils= SPUtils.getInstance("Login_account");
                //    spUtils.put("username",username);
                params = new HashMap<>();
                // params.put("password", password);
                params.put("username",spUtils.getString("userNumber"));

                params.put("newusername", mUpdateNum.getText()+"");
                params.put("email",mUpadateEmail.getText()+"");
                params.put("telphone",mUpdateTelephone.getText()+"");

                netrequest = new NetRequest(params, UrlUtils.NET_MODIFIEDINFO,modifyInfo.this,1);

                netrequest.handlerResult = new myHandlerResult();
                netrequest.execute();
            }
        });


    }
   class myHandlerResult extends HandlerResult{

       @Override
       public void success() {
           spUtils= SPUtils.getInstance("Login_account");
            spUtils.put("username",mUpdateNum.getText()+"");
           spUtils.put("Email", mUpadateEmail.getText()+"");
           spUtils.put("telephone",mUpdateTelephone.getText()+"");

           ToastUtils.show(modifyInfo.this,"修改成功",2);
           Intent intent=new Intent(modifyInfo.this,aboutme.class);
           startActivity(intent);
           finish();
       }

       @Override
       public void failed() {
           ToastUtils.show(modifyInfo.this,"修改失败",2);

       }
   }


    private void initui() {
        mUpdateNum = (EditText) findViewById(R.id.update_num);
        mUpdateTelephone = (EditText) findViewById(R.id.update_telephone);
        mUpadateEmail = (EditText) findViewById(R.id.upadate_email);
        mUpdateinfoSubmit = (Button) findViewById(R.id.updateinfo_submit);
        updateInfoTitlebar= (LinearLayout) findViewById(R.id.updateinfoTitlebar);
        left= (ImageButton) updateInfoTitlebar.findViewById(R.id.button_backward);
     //   right= (ImageButton) updateInfoTitlebar.findViewById(R.id.button_edit);
        center= (TextView) updateInfoTitlebar.findViewById(R.id.text_title);
        mUpdateinfoSubmit= (Button) findViewById(R.id.updateinfo_submit);
        left.setVisibility(View.VISIBLE);
        left.setBackgroundResource(R.drawable.back_arrow);
        center.setText("修改信息");
        //right.setTextAlignment();
    }

}
