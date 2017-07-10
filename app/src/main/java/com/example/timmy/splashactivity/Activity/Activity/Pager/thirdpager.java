package com.example.timmy.splashactivity.Activity.Activity.Pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.timmy.splashactivity.Activity.Activity.base.BasePager;
import com.example.timmy.splashactivity.Activity.Activity.utils.LogUtil;
import com.example.timmy.splashactivity.R;

/**
 * Created by Timmy on 2017/7/9.
 */

public class thirdpager extends BasePager {

    private TextView textView;
    public thirdpager(Context content) {
        super(content);
    }
private View view;
    @Override
    public View initVeiw() {
        LogUtil.e("第三页已经被初始化了");
      view=View.inflate(context, R.layout.thirdfragment,null);


        return view;
    }

    @Override
    public void initdata() {
        super.initdata();
        LogUtil.e("第三页的数据被初始化了");


    }
}
