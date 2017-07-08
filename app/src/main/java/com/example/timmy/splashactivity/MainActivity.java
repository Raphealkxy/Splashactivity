package com.example.timmy.splashactivity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity {


    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       radioGroup= (RadioGroup) findViewById(R.id.rg_bottom_tag);
        radioGroup.check(R.id.rb_firstPage);
    }
}
