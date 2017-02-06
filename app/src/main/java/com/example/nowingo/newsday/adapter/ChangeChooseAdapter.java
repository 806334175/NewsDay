package com.example.nowingo.newsday.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.entity.Channel;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2016/12/28.
 */
public class ChangeChooseAdapter extends RecyclerView.Adapter<ChangeChooseAdapter.MyViewHolder> {

    Context context;
    ArrayList<Channel> arrayList;

    public ChangeChooseAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    public ArrayList<Channel> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Channel> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.changechoose_item,null));
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.button.setText(arrayList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                OnClick.setItemClick(holder.itemView,pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView button;
        public MyViewHolder(View itemView) {
            super(itemView);
            button = (TextView) itemView.findViewById(R.id.changechoose_choose_btn);
        }
    }

    public interface OnClick{
        void setItemClick(View view, int postion);
    }
    private OnClick OnClick;

    public ChangeChooseAdapter.OnClick getOnClick() {
        return OnClick;
    }

    public void setOnClick(ChangeChooseAdapter.OnClick onClick) {
        OnClick = onClick;
    }

    //增加数据
    public void addData(Channel channel) {
        arrayList.add(channel);
        notifyItemInserted(arrayList.size()-1);
    }
    //移除数据
    public void removeData(int p) {
        arrayList.remove(p);
        notifyItemRemoved(p);
    }
}
