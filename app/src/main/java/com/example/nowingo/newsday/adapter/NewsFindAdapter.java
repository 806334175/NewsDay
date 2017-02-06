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

import java.util.ArrayList;

/**
 * Created by NowINGo on 2016/12/29.
 */
public class NewsFindAdapter extends RecyclerView.Adapter<NewsFindAdapter.MyViewHolder> {

    Context context;
    ArrayList<News.ShowapiResBodyBean.PagebeanBean.ContentlistBean> arrayList;
    public NewsFindAdapter(Context context) {
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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        if (arrayList.get(position).getImageurls().size()==0){
//            holder.imageView.setImageResource(R.mipmap.ic_launcher);
            holder.imageView.setVisibility(View.GONE);
        }else {
            holder.imageView.setVisibility(View.VISIBLE);
            if (ShareManager.loadHaveImg(context)){
                holder.imageView.setImageResource(R.mipmap.no_img);
            }else {
                Glide.with(context).load(arrayList.get(position).getImageurls().get(0).getUrl()).placeholder(R.drawable.img_news_loding).error(R.drawable.img_news_lodinglose).centerCrop().into(holder.imageView);
            }
        }
        holder.textView_title.setText(arrayList.get(position).getTitle());
        holder.textView_desc.setText(arrayList.get(position).getDesc());
        holder.textView_date.setText(arrayList.get(position).getPubDate());


        //设置监听
        if (onItemClickLitener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    onItemClickLitener.onItemClick(holder.itemView,pos);
                }
            });
        }
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
