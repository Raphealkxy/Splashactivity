package com.example.timmy.splashactivity.Activity.Activity.Pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.timmy.splashactivity.Activity.Activity.base.BasePager;
import com.example.commonlibrary.utils.LogUtil;

/**
 * Created by Timmy on 2017/7/9.
 */

public class secondpager extends BasePager {
    private TextView textView;
    public secondpager(Context content) {
        super(content);
    }

    @Override
    public View initVeiw() {
        LogUtil.e("第二页已经被初始化了");
        textView=new TextView(context);
        textView.setTextSize(25);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);


        return textView;
    }

    @Override
    public void initdata() {
        super.initdata();
        LogUtil.e("第二页的数据被初始化了");

        textView.setText("你好，这是第二页");

    }
}
