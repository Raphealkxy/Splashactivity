package com.example.timmy.splashactivity.Activity.Activity.Pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timmy.splashactivity.Activity.Activity.adapter.firstPagerAdapter;
import com.example.timmy.splashactivity.Activity.Activity.base.BasePager;
import com.example.timmy.splashactivity.Activity.Activity.utils.LogUtil;
import com.example.timmy.splashactivity.R;

/**
 * Created by Timmy on 2017/7/9.
 */

public class firstpager extends BasePager {


    private ListView listView;
    private String[] datas;
    private firstPagerAdapter mfirstPagerAdapter;
    public firstpager(Context content) {
        super(content);
    }

    @Override
    public View initVeiw() {
      LogUtil.e("首页已经被初始化了");
      View view=View.inflate(context, R.layout.first_fragment,null);
      listView= (ListView) view.findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data=datas[position];
                Toast.makeText(context,data,Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void initdata() {
        super.initdata();
        LogUtil.e("首页的数据被初始化了");
        //准备数据
        datas=new String[]{"OKHttp", "xUtils3","Retrofit2","Fresco","Glide","greenDao","RxJava","volley","Gson","FastJson","picasso","evenBus","jcvideoplayer","pulltorefresh","Expandablelistview","UniversalVideoView","....."};
    //设置适配器
        mfirstPagerAdapter=new firstPagerAdapter(context,datas);
         listView.setAdapter(mfirstPagerAdapter);
    }
}
