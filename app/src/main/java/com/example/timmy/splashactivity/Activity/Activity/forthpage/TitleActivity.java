package com.example.timmy.splashactivity.Activity.Activity.forthpage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.example.timmy.splashactivity.R;

public class TitleActivity extends Activity {

    private ImageButton bn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        setContentView(R.layout.activity_title);
        bn2=(ImageButton) findViewById(R.id.button_backward);
        bn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(TitleActivity.this,faceDemo.class);
            //   startActivity(intent);
                         finish();
            }
        });
    }


}
