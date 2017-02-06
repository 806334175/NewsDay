package com.example.nowingo.newsday.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.base.MyBaseAdapter;
import com.example.nowingo.newsday.entity.CityBean;


public class CityAdapter extends MyBaseAdapter<CityBean> {
    public CityAdapter(Context context) {
        super(context);
    }

    @Override
    public View setView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh=null;
        if(convertView==null){
            vh=new ViewHolder();
            convertView=layoutInflater.inflate(R.layout.city_item,null);
            vh.tv= (TextView) convertView.findViewById(R.id.city_item_name_tv);
            //作为标签传递
            convertView.setTag(vh);
        }else{
            vh= (ViewHolder) convertView.getTag();//布局中取出标签
        }
        CityBean cityBean=list.get(position);
        vh.tv.setText(cityBean.getDistrict());
        return convertView;
    }

    /**
     * 自定义缓冲类
     */
    class ViewHolder{
          TextView tv;
    }
}
