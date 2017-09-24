package com.example.timmy.splashactivity.Activity.Activity.Pager;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timmy.splashactivity.Activity.Activity.aboutme.aboutme;
import com.example.timmy.splashactivity.Activity.Activity.adapter.forthPagerAdapter;
import com.example.timmy.splashactivity.Activity.Activity.base.BasePager;
import com.example.timmy.splashactivity.Activity.Activity.forthpage.TitleActivity;
import com.example.timmy.splashactivity.Activity.Activity.login.loginActivity;
import com.example.timmy.splashactivity.Activity.Activity.utils.LogUtil;
import com.example.timmy.splashactivity.R;

/**
 * Created by Timmy on 2017/7/9.
 */

public class forthpager extends BasePager {
    private TextView textView;
    private View view;
    private ListView listView;
    private ListView listViewtwo;
    private String[] datas;
    private String[] datas_2;
    private forthPagerAdapter mforthPagerAdapter;
    public forthpager(Context content) {
        super(content);
    }
    private Button layout;
    @Override
    public View initVeiw() {
        LogUtil.e("第四页已经被初始化了");
       view=View.inflate(context, R.layout.forth_fragment,null);
        listView= (ListView) view.findViewById(R.id.forth_listview);
        listViewtwo= (ListView) view.findViewById(R.id.forth_listview_2);
        layout= (Button) view.findViewById(R.id.logout);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logoutIntent = new Intent(context, loginActivity.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(logoutIntent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    Intent intent = new Intent(context, aboutme.class);
                    context.startActivity(intent);
                }else {
                    String data=datas_2[position];
                    Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                }
            }
        });
        listViewtwo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==2) {
                    Intent intent = new Intent(context, TitleActivity.class);
                    context.startActivity(intent);
                }else {
                    String data=datas_2[position];
                    Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void initdata() {
        super.initdata();
        LogUtil.e("第四页的数据被初始化了");
          //初始化数据
       datas=new String[]{"个人信息","账号设置","应用安全"};
        datas_2=new String[]{"推送设置","清除缓存","关于软件"};
        //设置设置适配器
mforthPagerAdapter=new forthPagerAdapter(context,datas);
    listView.setAdapter(mforthPagerAdapter);
    listViewtwo.setAdapter(new forthPagerAdapter(context,datas_2));
    }
}
