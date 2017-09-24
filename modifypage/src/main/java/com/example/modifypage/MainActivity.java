package com.example.modifypage;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.modifypage.base.BasePager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        forthpager forth=new forthpager(this);
//        forth.initVeiw();
//          forth.initdata();
        setfragment();

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
                        BasePager basePager = new forthpager(MainActivity.this);
                        if (basePager != null)
                            return basePager.rootview;
                        return super.onCreateView(inflater, container, savedInstanceState);
                    }
                }


        );
        //提交事务
        ft.commit();
    }
}
