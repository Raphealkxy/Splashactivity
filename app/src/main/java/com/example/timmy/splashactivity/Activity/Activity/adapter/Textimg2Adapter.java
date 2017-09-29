package com.example.timmy.splashactivity.Activity.Activity.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.timmy.splashactivity.R;

import java.util.List;

/**
 * Created by Timmy on 2017/9/29.
 */

public class Textimg2Adapter extends CommonAdapter<ItemBean2>{

    public Textimg2Adapter(Context context, List<ItemBean2> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void convert(CommonViewHolder viewHolder, ItemBean2 data, int position) {
        ImageView item_left = viewHolder.getView(R.id.img);
        TextView titleTv = viewHolder.getView(R.id.text);
        ImageView item_right = viewHolder.getView(R.id.img1);
        item_left.setBackgroundResource(data.getImgleftid());
        titleTv.setText(data.getText());
        item_right.setBackgroundResource(data.getImrightid());

    }
}
