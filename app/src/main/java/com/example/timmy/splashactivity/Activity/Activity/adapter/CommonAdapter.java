package com.example.timmy.splashactivity.Activity.Activity.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Timmy on 2017/9/29.
 */

public abstract class CommonAdapter<T> extends BaseAdapter {

    public Context mContext;

    public List<T> list;

    public int layoutId;

    public CommonAdapter(Context context, List<T> list, int layoutId){
        this.mContext = context;
        this.list = list;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        CommonViewHolder holder = CommonViewHolder.get(mContext, convertView, position, layoutId, parent);
        convert(holder, list.get(position), position);
        return holder.getConvertView();
    }
    //这个就是留给具体Adapter实现的方法
    public abstract void convert(CommonViewHolder viewHolder, T data, int position);
}
