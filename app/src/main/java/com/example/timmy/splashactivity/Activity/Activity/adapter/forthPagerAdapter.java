package com.example.timmy.splashactivity.Activity.Activity.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Timmy on 2017/7/10.
 */

public class forthPagerAdapter extends BaseAdapter {

    private Context mcontext;
    private  String[] datas;
    public forthPagerAdapter(Context context, String[] datas)
    {

        this.mcontext=context;
        this.datas=datas;
    }
    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textview=new TextView(mcontext);
        textview.setPadding(10,30,0,30);
        textview.setTextColor(Color.BLACK);
        textview.setTextSize(20);
        textview.setText(datas[position]);
        return textview;

    }
}


