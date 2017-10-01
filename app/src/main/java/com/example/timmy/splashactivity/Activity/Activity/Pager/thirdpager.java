package com.example.timmy.splashactivity.Activity.Activity.Pager;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timmy.splashactivity.Activity.Activity.HandlerResult;
import com.example.timmy.splashactivity.Activity.Activity.Isv.third_check;
import com.example.timmy.splashactivity.Activity.Activity.Isv.third_register;
import com.example.timmy.splashactivity.Activity.Activity.NetRequest;
import com.example.timmy.splashactivity.Activity.Activity.adapter.recyclerAdapter;
import com.example.timmy.splashactivity.Activity.Activity.base.BasePager;
import com.example.timmy.splashactivity.Activity.Activity.face.faceDemo;
import com.example.timmy.splashactivity.Activity.Activity.getDatafromDb.getDataFromDb;
import com.example.timmy.splashactivity.Activity.Activity.getDatafromDb.requestUserData;
import com.example.timmy.splashactivity.Activity.Activity.utils.LogUtil;
import com.example.timmy.splashactivity.R;
import com.timmy.data.UrlUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        view = View.inflate(context, R.layout.thirdfragment, null);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);


        return view;
    }

    @Override
    public void initdata() {
        super.initdata();
        LogUtil.e("第三页的数据被初始化了");
        datas_str = new String[]{"获取表单", "点名", "人脸识别", "查询模型"};
        datas = new ArrayList<>();
        for (int i = 0; i < datas_str.length; i++)
            datas.add(datas_str[i]);


        //实现recyclerview自己的适配器
        recyclerAdapter myrecyclerview = new recyclerAdapter(context, datas);

        //设置适配器
        recyclerview.setAdapter(myrecyclerview);


        //设置LayoutManger
        recyclerview.setLayoutManager(new GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false));
        myrecyclerview.setOnImageViewClickListener(new recyclerAdapter.OnImageViewClickListener() {
            @Override
            public void onImageViewClick(View view, int position) {
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(context, requestUserData.class);
                        context.startActivity(intent);
                        break;

                    case 1:
                        intent = new Intent(context, third_check.class);
                        context.startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(context, faceDemo.class);
                        context.startActivity(intent);
                    default:
                        break;


                }


            }


        });

    }


}