package com.example.timmy.splashactivity.Activity.Activity.aboutme;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.timmy.splashactivity.R;

public class aboutme extends Activity {


    private ImageButton bn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);
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
}
