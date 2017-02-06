package com.example.nowingo.newsday.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.entity.Collect;
import com.example.nowingo.newsday.entity.News;
import com.example.nowingo.newsday.entity.News2;
import com.example.nowingo.newsday.manager.ShareManager;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/6.
 */
public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.MyViewHolder>{
    Context context;
    ArrayList<Collect> arrayList;

    public CollectAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    public ArrayList<Collect> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Collect> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    /**
     * 用回调机制写监听
     */
    public interface OnItemClickLitener{
        void onItemClick(View view, int postion);
    }
    private OnItemClickLitener onItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener){
        this.onItemClickLitener = onItemClickLitener;
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (arrayList.get(position).getFrom().equals("2")){
            News.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean = new Gson().fromJson(arrayList.get(position).getMsg(), News.ShowapiResBodyBean.PagebeanBean.ContentlistBean.class);
            if (contentlistBean.getImageurls().size()==0){
                holder.imageView.setVisibility(View.GONE);
            }else {
                holder.imageView.setVisibility(View.VISIBLE);
                if (ShareManager.loadHaveImg(context)){
                    holder.imageView.setImageResource(R.mipmap.no_img);
                }else {
                    Glide.with(context).load(contentlistBean.getImageurls().get(0).getUrl()).placeholder(R.drawable.img_news_loding).error(R.drawable.img_news_lodinglose).centerCrop().into(holder.imageView);
                }
            }
            holder.textView_title.setText(contentlistBean.getTitle());
            holder.textView_desc.setText(contentlistBean.getDesc());
            holder.textView_date.setText(contentlistBean.getPubDate());
        }else if (arrayList.get(position).getFrom().equals("1")){
            News2.ResultEntity.DataEntity dataEntity = new Gson().fromJson(arrayList.get(position).getMsg(), News2.ResultEntity.DataEntity.class);
            if (dataEntity.getThumbnail_pic_s()==null){
                holder.imageView.setImageResource(R.mipmap.no_img);
            }else {
                if (ShareManager.loadHaveImg(context)){
                    holder.imageView.setImageResource(R.mipmap.no_img);
                }else {
                    Glide.with(context).load(dataEntity.getThumbnail_pic_s()).placeholder(R.drawable.img_news_loding).error(R.drawable.img_news_lodinglose).centerCrop().into(((MyViewHolder) holder).imageView);
                }
            }
            holder.textView_title.setText(dataEntity.getTitle());
            holder.textView_desc.setText(dataEntity.getAuthor_name());
            holder.textView_date.setText(dataEntity.getDate());
        }
        if (onItemClickLitener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickLitener.onItemClick(holder.itemView,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView_title,textView_desc,textView_date;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.news_item_img);
            textView_title = (TextView) itemView.findViewById(R.id.news_item_title);
            textView_desc = (TextView) itemView.findViewById(R.id.news_item_desc);
            textView_date = (TextView) itemView.findViewById(R.id.news_item_date);
        }
    }
}
