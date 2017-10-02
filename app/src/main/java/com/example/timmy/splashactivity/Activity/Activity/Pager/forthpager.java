package com.example.timmy.splashactivity.Activity.Activity.Pager;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commonlibrary.utils.SPUtils;
import com.example.timmy.splashactivity.Activity.Activity.aboutme.aboutme;
import com.example.timmy.splashactivity.Activity.Activity.adapter.ItemBean2;
import com.example.timmy.splashactivity.Activity.Activity.adapter.Textimg2Adapter;
import com.example.timmy.splashactivity.Activity.Activity.base.BasePager;
import com.example.timmy.splashactivity.Activity.Activity.forthpage.TitleActivity;
import com.example.timmy.splashactivity.Activity.Activity.login.loginActivity;
import com.example.commonlibrary.utils.LogUtil;
import com.example.timmy.splashactivity.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Timmy on 2017/7/9.
 */

public class forthpager extends BasePager {

    private int[] imageidleft;
    private int []imageidright;
    private String[] name;
    private TextView textView;
    private View view;
    private ListView listView;
    private ListView listViewtwo;
    private List<ItemBean2> firstlistviewdata;
    private List<ItemBean2> secondlistviewdata;

    private String[] datas;
    private String[] datas_2;
   // private forthPagerAdapter mforthPagerAdapter;
    private Textimg2Adapter textimg2Adapter;
    private Textimg2Adapter textimg2Adapter1;

    public forthpager(Context content) {
        super(content);
    }
    private Button layout;
    private SPUtils spUtils;
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
                spUtils=SPUtils.getInstance("Login_account");
                spUtils.clear();
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
        firstlistviewdata = new ArrayList<ItemBean2>();
        secondlistviewdata = new ArrayList<ItemBean2>();
        imageidright=new int[]{R.drawable.web_icon_right_dis1,R.drawable.web_icon_right_dis1,R.drawable.web_icon_right_dis1};
        imageidleft=new int[]{R.drawable.pass,R.drawable.pass,R.drawable.pass};
        for(int i=0; i<3; i++){
            ItemBean2 bean = new ItemBean2(imageidleft[i],imageidright[i],datas[i]);
            ItemBean2 bean1 = new ItemBean2(imageidleft[i],imageidright[i],datas_2[i]);

            firstlistviewdata.add(bean);
            secondlistviewdata.add(bean1);
        }
        //设置设置适配器
//mforthPagerAdapter=new forthPagerAdapter(context,datas);
        textimg2Adapter=new Textimg2Adapter(context,firstlistviewdata,R.layout.layout_item);
    listView.setAdapter(textimg2Adapter);
    listViewtwo.setAdapter(new Textimg2Adapter(context,secondlistviewdata,R.layout.layout_item));
    }
}
