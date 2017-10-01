package com.example.timmy.splashactivity.Activity.Activity.getDatafromDb;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timmy.splashactivity.Activity.Activity.HandlerResult;
import com.example.timmy.splashactivity.Activity.Activity.NetRequest;
import com.example.timmy.splashactivity.R;
import com.timmy.data.UrlUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.x;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;

import static android.content.ContentValues.TAG;

@ContentView(R.layout.activity_request_user_data)
public class requestUserData extends Activity {


    private TextView textView;
    private Toast mToast;
    private String content;
    private Button btn;
    private NetRequest netRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        textView= (TextView) findViewById(R.id.showmesg);
       btn= (Button) findViewById(R.id.btn);
        Map<String, String> params = new HashMap<>();
        // params.put("password", password);
        params.put("username","kxy");
        netRequest=new NetRequest(params,UrlUtils.NET_GETUSERLIST,this,2);
        netRequest.handlerResult=new myHanlderResult();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata();
            }
        });
    }


    public void getdata()
    {
        netRequest.execute();



    }


    private void direct() {
        Intent intent=new Intent(this,getDataFromDb.class);
       intent.putExtra("content",content);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

        finish();
    }
    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    class myHanlderResult extends   HandlerResult{

        @Override
        public void success() {

            content=netRequest.Content;

            direct();
        }

        @Override
        public void failed() {
              showTip("数据获取失败");
        }
    }

}
