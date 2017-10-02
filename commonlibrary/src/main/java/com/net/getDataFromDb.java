package com.net;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.entity.User;
import com.example.commonlibrary.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class getDataFromDb extends Activity implements AdapterView.OnItemClickListener{

    private GridView gridview;
    private List<Integer> booleans;
    private TableAdapter adapter;
    private List<String> list;
    private String content;
    private Toast mToast;
    private List<User>listUser=new ArrayList<User>();
    private String [] columnName=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data_from_db);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        Intent intent = getIntent();
        content=intent.getStringExtra("content");
        columnName=intent.getStringArrayExtra("columName");
        gridview = (GridView) findViewById(R.id.gridview);
        Gson gson = new Gson();

        //  net.sf.json.JSONArray jsonArray= net.sf.json.JSONArray.fromObject(content);
//       listUser= net.sf.json.JSONArray.toList(jsonArray,User.class);
        listUser= gson.fromJson(content,
                new TypeToken<List<User>>() {
                }.getType());
        //  User user=listUser.get(1);
        // showTip(user.getUsername());
        list = new ArrayList<>();
        booleans = new ArrayList<>();
        adapter = new TableAdapter();
        gridview.setAdapter(adapter);

        // 添加消息处理
        gridview.setOnItemClickListener(this);

        //添加表头
        addHeader();

        //添加数据测试
        addData();
        //  showTip(content);
    }

    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }
    public void addHeader() {
        String items[] =columnName;
        for (String strText : items) {
            booleans.add(0);
            list.add(strText);
        }
        adapter.notifyDataSetChanged(); //更新数据
    }

    public void addData() {

        // String titles[] = {"早操", "早自习", "上课", "晚自习", "晚寝", "活动点到"};
        for (int i = 0; i < listUser.size(); i++) {
            for (int j = 0; j < 5; j++) {
                if (j == 0) {
                    list.add(listUser.get(i).getUserNumber());
                } else if(j==1) {
                    list.add(listUser.get(i).getUsername());
                }else if(j==2) {
                    list.add(listUser.get(i).getUsername());
                }else if(j==3) {
                    list.add(listUser.get(i).getSex());
                }else if(j==4) {
                    list.add(listUser.get(i).getTelephone());
                }
                if (i % 2 == 0) {
                    booleans.add(1);

                } else {
                    booleans.add(2);
                }
            }
        }
        adapter.notifyDataSetChanged(); //更新数据
    }

    //清空列表
    public void RemoveAll() {
        list.clear();
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getDataFromDb.this, list.get(position), Toast.LENGTH_SHORT).show();

    }
    class TableAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(getDataFromDb.this, R.layout.grid_item, null);
                holder = new ViewHolder();
                convertView.setTag(holder);
                holder.tv_item = (TextView) convertView.findViewById(R.id.tv_item);
                holder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tv_item.setText(list.get(position));

            if (booleans.get(position) == 0) {
                //表头颜色
                holder.tv_item.setBackgroundColor(Color.parseColor("#95c5ef"));
                holder.ll_item.setBackgroundColor(Color.WHITE);
            } else if (booleans.get(position) == 1) {
                //奇数行颜色
                holder.tv_item.setBackgroundColor(Color.parseColor("#EAEBEB"));
                holder.ll_item.setBackgroundColor(Color.parseColor("#dadada"));
            } else {
                //偶数行颜色
                holder.tv_item.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.ll_item.setBackgroundColor(Color.parseColor("#dadada"));
            }
            return convertView;
        }

        class ViewHolder {
            private TextView tv_item;
            private LinearLayout ll_item;
        }
    }
}
