package com.example.nowingo.newsday.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.base.MyBaseAdapter;
import com.example.nowingo.newsday.entity.Express;

/**
 * Created by NowINGo on 2017/1/11.
 */
public class ExpressAdapter extends MyBaseAdapter<Express.ShowapiResBodyBean.DataBean> {
    public ExpressAdapter(Context context) {
        super(context);
    }
    @Override
    public View setView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null){
            vh = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.express_item,null);
            vh.tv_date = (TextView) convertView.findViewById(R.id.express_item_date);
            vh.tv_msg = (TextView) convertView.findViewById(R.id.express_item_msg);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.tv_date.setText(list.get(position).getTime());
        vh.tv_msg.setText(list.get(position).getContext());
        return convertView;
    }
    class ViewHolder{
        TextView tv_date,tv_msg;
    }
}
