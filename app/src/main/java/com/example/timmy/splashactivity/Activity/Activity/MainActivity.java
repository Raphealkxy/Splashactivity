package com.example.timmy.splashactivity.Activity.Activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.timmy.splashactivity.Activity.Activity.Pager.firstpager;
import com.example.timmy.splashactivity.Activity.Activity.Pager.forthpager;
import com.example.timmy.splashactivity.Activity.Activity.Pager.secondpager;
import com.example.timmy.splashactivity.Activity.Activity.Pager.thirdpager;
import com.example.timmy.splashactivity.Activity.Activity.base.BasePager;
import com.example.timmy.splashactivity.R;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private FrameLayout frameLayout;
    private ArrayList<BasePager> basePagers;
    private RadioGroup radioGroup;
    private int positon;
    private TextView textView_tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView_tab= (TextView) findViewById(R.id.Tab_text);
        radioGroup = (RadioGroup) findViewById(R.id.rg_bottom_tag);
        basePagers = new ArrayList<>();
        basePagers.add(new firstpager(this));
        basePagers.add(new secondpager(this));
        basePagers.add(new thirdpager(this));
        basePagers.add(new forthpager(this));

        //设置radiogroup的一个监听
        radioGroup.setOnCheckedChangeListener(new myOnCheckedChangeLister());

        radioGroup.check(R.id.rb_firstPage);


    }

    class myOnCheckedChangeLister implements RadioGroup.OnCheckedChangeListener {


        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                default:
                    positon = 0;
                    textView_tab.setText("首页");
                    break;
                case R.id.rb_secondPage:
                    positon = 1;
                    textView_tab.setText("更多");
                    break;
                case R.id.rb_thirthPage:
                    positon = 2;
                    textView_tab.setText("点名");
                    break;
                case R.id.rb_forthPage:
                    positon = 3;
                    textView_tab.setText("用户");
                    break;

            }

            setfragment();
        }
    }


    private void setfragment() {
        //得到fragmentManger
        FragmentManager manager = getSupportFragmentManager();
        //开启事务
        android.support.v4.app.FragmentTransaction ft = manager.beginTransaction();
        //替换
        ft.replace(R.id.fl_main_content, new Fragment() {


                    @Nullable
                    @Override
                    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
                        BasePager basePager = getPager();
                        if (basePager != null)
                            return basePager.rootview;
                        return super.onCreateView(inflater, container, savedInstanceState);
                    }
                }


        );
        //提交事务
        ft.commit();
    }

    private BasePager getPager() {
        BasePager basePager = basePagers.get(positon);
        if (basePager != null&&!basePager.isInitData) {
            basePager.initdata();
            basePager.isInitData=true;
        }
            return basePager;

    }
}
