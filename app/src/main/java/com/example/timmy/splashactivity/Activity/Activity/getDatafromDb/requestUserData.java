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

import com.example.timmy.splashactivity.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        textView= (TextView) findViewById(R.id.showmesg);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

    }


@Event(value=R.id.btn)
    public void getdata(View view)
    {

//    File file = new File(uri);
//    if (!file.exists())
//    {
//        Toast.makeText(registerFace.this, "文件不存在，请修改文件路径", Toast.LENGTH_SHORT).show();
//        return;
//    }
        Map<String, String> params = new HashMap<>();
       // params.put("password", password);
       params.put("username","kxy");

        Map<String, String> headers = new HashMap<>();
        headers.put("APP-Key", "APP-Secret222");
        headers.put("APP-Secret", "APP-Secret111");


        String url = "http://192.168.253.1:8080/AMSFull/action_getuserlist";

        com.zhy.http.okhttp.OkHttpUtils.post()//
                //.addFile()//
                .url(url)//
                .params(params)//
                .headers(headers)//
                .build()//
                .execute(new requestUserData.MyStringCallback());
    }
//    public void postString()
//    {
//        String mBaseUrl="http://192.168.253.1:8080/AMSFull/action_getuserlist";
//        String url = mBaseUrl;
//        OkHttpUtils
//                .postString()
//                .url(url)
//                .mediaType(MediaType.parse("application/json; charset=utf-8"))
//                .content("")
//                .build()
//                .execute(new MyStringCallback());
//
//    }
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
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

        finish();
    }
    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }
}
