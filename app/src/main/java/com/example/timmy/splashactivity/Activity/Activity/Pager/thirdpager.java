package com.example.timmy.splashactivity.Activity.Activity.Pager;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.timmy.splashactivity.Activity.Activity.adapter.recyclerAdapter;
import com.example.timmy.splashactivity.Activity.Activity.base.BasePager;
import com.example.timmy.splashactivity.Activity.Activity.utils.LogUtil;
import com.example.timmy.splashactivity.R;

import java.util.ArrayList;

/**
 * Created by Timmy on 2017/7/9.
 */

public class thirdpager extends BasePager {

    private String[] datas_str;
    private RecyclerView recyclerview;
    private ArrayList<String> datas;
    public thirdpager(Context content) {
        super(content);
    }
private View view;
    @Override
    public View initVeiw() {
        LogUtil.e("第三页已经被初始化了");
      view=View.inflate(context, R.layout.thirdfragment,null);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);


        return view;
    }

    @Override
    public void initdata() {
        super.initdata();
        LogUtil.e("第三页的数据被初始化了");
        datas_str=new String[]{"注册","点名","删除模型","查询模型"};
       datas = new ArrayList<>();
        for(int i=0;i<datas_str.length;i++)
            datas.add(datas_str[i]);


        //实现recyclerview自己的适配器
        recyclerAdapter myrecyclerview =new recyclerAdapter(context,datas);

        //设置适配器
        recyclerview.setAdapter(myrecyclerview);


        //设置LayoutManger
        recyclerview.setLayoutManager(new GridLayoutManager(context,4,GridLayoutManager.VERTICAL,false));

    }
}
