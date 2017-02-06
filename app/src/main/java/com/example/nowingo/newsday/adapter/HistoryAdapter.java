package com.example.nowingo.newsday.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.entity.History;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/5.
 */
public class HistoryAdapter extends BaseAdapter {
    Context context;
    ArrayList<History> arrayList;

    public HistoryAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    public ArrayList<History> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<History> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
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
        MyViewHolder vh = null;
        if (convertView == null){
            vh = new MyViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.history_item,null);
            vh.textView_name = (TextView) convertView.findViewById(R.id.history_item_name);
            vh.textView_date = (TextView) convertView.findViewById(R.id.history_item_date);
            convertView.setTag(vh);
        }else {
            vh = (MyViewHolder) convertView.getTag();
        }
        vh.textView_name.setText(arrayList.get(position).getName());
        vh.textView_date.setText(arrayList.get(position).getDate());
        return convertView;
    }
    class MyViewHolder{
        TextView textView_name,textView_date;
    }
}
