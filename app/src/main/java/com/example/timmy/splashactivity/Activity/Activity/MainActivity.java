package com.example.timmy.splashactivity.Activity.Activity;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.commonlibrary.utils.SPUtils;
import com.example.commonlibrary.utils.ToastUtils;
import com.example.timmy.splashactivity.Activity.Activity.Pager.firstpager;
import com.example.timmy.splashactivity.Activity.Activity.Pager.forthpager;
import com.example.timmy.splashactivity.Activity.Activity.Pager.secondpager;
import com.example.timmy.splashactivity.Activity.Activity.Pager.thirdpager;
import com.example.timmy.splashactivity.Activity.Activity.base.BasePager;
import com.example.timmy.splashactivity.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

import java.util.ArrayList;

@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

    private FrameLayout frameLayout;
    private static ArrayList<BasePager> basePagers;
    private RadioGroup radioGroup;
    private static  int positon;
    private TextView textView_tab;
    private SPUtils spUtils;
   private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
       initdata();
        textView_tab= (TextView) findViewById(R.id.Tab_text_1);
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

    private void initdata() {
        username=getIntent().getStringExtra("username");
        password=getIntent().getStringExtra("password");
        spUtils= SPUtils.getInstance("Login_account");
    //    spUtils.put("username",username);
        spUtils.put("loginStatus","login_ok");
        spUtils.put("username",username);
        spUtils.put("password",password);

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

        ft.replace(R.id.fl_main_content,new myFragment());




        //提交事务
        ft.commit();
    }
    public static class myFragment extends Fragment{
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            BasePager b1=getPager();
            if (b1!=null){
                return b1.rootview;
            }
            return null;
        }
    }
    public static   BasePager getPager() {
        BasePager basePager = basePagers.get(positon);
        if (basePager != null&&!basePager.isInitData) {
            basePager.initdata();
            basePager.isInitData=true;
        }
            return basePager;

    }
}
