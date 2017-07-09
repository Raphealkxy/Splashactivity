package com.example.timmy.splashactivity.Activity.Activity.base;

import android.content.Context;
import android.view.View;

/**
 * Created by Timmy on 2017/7/9.
 * 四个子页面的父类
 * 首页
 * 更多
 * 功能
 * 设置
 * 实现数据和视图分离 a.initView()，孩子强制实现该方法。b.在initdata中初始化子页面数据
 */

public abstract class BasePager {


    public final Context context;
    public View rootview;
    public boolean isInitData;

    public BasePager(Context content)
    {
        this.context=content;
        rootview=initVeiw();
    }

    /**
     * 强制孩子实现不同的页面，利用一个方法
     * @return
     */
    public abstract View initVeiw() ;

    //联网请求数据，或者绑定数据的时候重写该方法；
    public void initdata()
    {

    }
}
