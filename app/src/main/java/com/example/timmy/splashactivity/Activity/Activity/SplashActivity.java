package com.example.timmy.splashactivity.Activity.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.example.timmy.splashactivity.R;

public class SplashActivity extends Activity {


    private static final String TAG=SplashActivity.class.getSimpleName();
    private Handler handler=new Handler();
    //如果用匿名类的话，在进入app界面，立马退出app，还会出现一次。记得要在最后将destory移除
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        //让界面延迟执行，注意线程是本线程，不是新开辟的线程
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startMainActivity();
                Log.e(TAG,"当前线程名称=="+Thread.currentThread().getName());

            }
        },2000);
    }

    /**
     * 跳转到主页面，并且把当前页面关掉
     */
    private void startMainActivity() {

       Intent intent=new Intent(this,loginActivity.class);
   startActivity(intent);
        finish();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //
        Log.e(TAG,"onTouchEvent==Action"+event.getAction());
        startMainActivity();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
