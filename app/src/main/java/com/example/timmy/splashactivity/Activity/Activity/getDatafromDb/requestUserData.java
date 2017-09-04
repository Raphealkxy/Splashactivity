package com.example.timmy.splashactivity.Activity.Activity.getDatafromDb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timmy.splashactivity.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;

import static android.content.ContentValues.TAG;

public class requestUserData extends Activity implements View.OnClickListener {


    private Button btn;
    private TextView textView;
    private Toast mToast;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_user_data);
        btn= (Button) findViewById(R.id.btn);
        textView= (TextView) findViewById(R.id.showmesg);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.btn:
               postString();
                break;
            default:
                break;
        }
    }
    public void postString()
    {
        String mBaseUrl="http://192.168.253.1:8080/AMS/getuserlist";
        String url = mBaseUrl;
        OkHttpUtils
                .postString()
                .url(url)
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .content("")
                .build()
                .execute(new MyStringCallback());

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
           showTip("onResponse:" + response);
           content=response;
//            switch (id)
//            {
//                case 100:
//                    Toast.makeText(register.this, "http", Toast.LENGTH_SHORT).show();
//                    break;
//                case 101:
//                    Toast.makeText(register.this, "https", Toast.LENGTH_SHORT).show();
//                    break;
//            }

            direct();

        }

        @Override
        public void inProgress(float progress, long total, int id)
        {
            Log.e(TAG, "inProgress:" + progress);
            //  mProgressBar.setProgress((int) (100 * progress));
        }
    }
    private void direct() {
        Intent intent=new Intent(this,getDataFromDb.class);
       intent.putExtra("content",content);
        startActivity(intent);

        finish();
    }
    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }
}
