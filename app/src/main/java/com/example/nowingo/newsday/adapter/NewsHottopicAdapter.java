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
import com.example.nowingo.newsday.entity.News;
import com.example.nowingo.newsday.manager.ShareManager;
import com.example.nowingo.newsday.view.MarqueeText;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2017/1/7.
 */
public class NewsHottopicAdapter extends RecyclerView.Adapter{
    public static final int TYPE_IMG=0;
    public static final int TYPE_ITEM=1;

    Context context;
    ArrayList<News.ShowapiResBodyBean.PagebeanBean.ContentlistBean> arrayList;

    public NewsHottopicAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    public ArrayList<News.ShowapiResBodyBean.PagebeanBean.ContentlistBean> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<News.ShowapiResBodyBean.PagebeanBean.ContentlistBean> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_IMG:
                View view = LayoutInflater.from(context).inflate(R.layout.news_item_top,parent,false);
                MyImgViewHolder myImgViewHolder = new MyImgViewHolder(view);
                return myImgViewHolder;
            case TYPE_ITEM:
                View view2 = LayoutInflater.from(context).inflate(R.layout.news_item,null);
                MyViewHolder myViewHolder = new MyViewHolder(view2);
                return myViewHolder;
        }
        return null;
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyImgViewHolder){
            if (ShareManager.loadHaveImg(context)){
                ((MyImgViewHolder) holder).imageView.setImageResource(R.mipmap.no_img);
            }else {
                Glide.with(context).load(arrayList.get(position).getImageurls().get(0).getUrl()).placeholder(R.drawable.img_news_loding).error(R.drawable.img_news_lodinglose).centerCrop().into(((MyImgViewHolder) holder).imageView);
            }
            ((MyImgViewHolder) holder).textView_title.setText(arrayList.get(position).getTitle());
        }else if (holder instanceof MyViewHolder){
            ((MyViewHolder) holder).imageView.setVisibility(View.GONE);
            ((MyViewHolder) holder).textView_title.setText(arrayList.get(position).getTitle());
            ((MyViewHolder) holder).textView_desc.setText(arrayList.get(position).getDesc());
            ((MyViewHolder) holder).textView_date.setText(arrayList.get(position).getPubDate());
        }
        if (onItemClickLitener!=null){
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

    @Override
    public int getItemViewType(int position) {
        if (arrayList.get(position).getImageurls().size() == 0){
            return TYPE_ITEM;
        }else {
            return TYPE_IMG;
        }
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

    class MyImgViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        MarqueeText textView_title;
        public MyImgViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.new_item_top_iv);
            textView_title = (MarqueeText) itemView.findViewById(R.id.new_item_top_tv);
        }
    }
}
