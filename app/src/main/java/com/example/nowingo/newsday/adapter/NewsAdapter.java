package com.example.nowingo.newsday.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.entity.News2;
import com.example.nowingo.newsday.manager.ShareManager;
import com.example.nowingo.newsday.view.MarqueeText;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2016/12/29.
 */
public class NewsAdapter extends RecyclerView.Adapter {

    public static final int TYPE_TOP=0;
    public static final int TYPE_ITEM=1;
    public static final int TYPE_BOTTOM=2;

    public static final int DATA_LOADING=0;//数据加载中
    public static final int DATA_FINISH=1;//数据加载完成
    public static final int DATA_NOMORE=2;//没有更多数据
    //尾布局状态
    int FootState;
    //上下文
    Context context;
    //数据源
    ArrayList<News2.ResultEntity.DataEntity> arrayList;
    //构造
    public NewsAdapter(Context context) {
        this.context = context;
        //初始化尾布局状态
        FootState = DATA_FINISH;
        arrayList = new ArrayList<>();
    }

    public ArrayList<News2.ResultEntity.DataEntity> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<News2.ResultEntity.DataEntity> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            //加载头部
            case TYPE_TOP:
                View view= LayoutInflater.from(context).inflate(R.layout.news_item_top,null);
                MyViewHolderTop myTopViewHolder=new MyViewHolderTop(view);
                return myTopViewHolder;
            //加载item
            case TYPE_ITEM:
                View view1= LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);
                MyViewHolder myItemViewHolder=new MyViewHolder(view1);
                return myItemViewHolder;
            //加载尾部
            case TYPE_BOTTOM:
                View view2= LayoutInflater.from(context).inflate(R.layout.new_item_foot,null);
                MyViewHolderFoot myBottomViewHolder=new MyViewHolderFoot(view2);
                return myBottomViewHolder;
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
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MyViewHolderTop){
            if (arrayList.get(position).getThumbnail_pic_s()==null){
                ((MyViewHolderTop) holder).imageView_top.setImageResource(R.mipmap.no_img);
            }else {
                if (ShareManager.loadHaveImg(context)){
                    ((MyViewHolderTop) holder).imageView_top.setImageResource(R.mipmap.no_img);
                }else {
                    Glide.with(context).load(arrayList.get(position).getThumbnail_pic_s()).placeholder(R.drawable.img_news_loding).error(R.drawable.img_news_lodinglose).centerCrop().into(((MyViewHolderTop) holder).imageView_top);
                }
            }
            ((MyViewHolderTop) holder).textView.setText(arrayList.get(position).getTitle());
        }else if (holder instanceof MyViewHolder){
            if (arrayList.get(position).getThumbnail_pic_s()==null){
                ((MyViewHolder) holder).imageView.setImageResource(R.mipmap.no_img);
            }else {
                if (ShareManager.loadHaveImg(context)){
                    ((MyViewHolder) holder).imageView.setImageResource(R.mipmap.no_img);
                }else {
                    Glide.with(context).load(arrayList.get(position).getThumbnail_pic_s()).placeholder(R.drawable.img_news_loding).error(R.drawable.img_news_lodinglose).centerCrop().into(((MyViewHolder) holder).imageView);
                }
            }
            ((MyViewHolder) holder).textView_title.setText(arrayList.get(position).getTitle());
            ((MyViewHolder) holder).textView_desc.setText(arrayList.get(position).getAuthor_name());
            ((MyViewHolder) holder).textView_date.setText(arrayList.get(position).getDate());
        }else if (holder instanceof  MyViewHolderFoot){
            //当只有尾部时，不加载尾部
            if (position == 0){
                ((MyViewHolderFoot) holder).textView.setVisibility(View.GONE);
                ((MyViewHolderFoot) holder).progressBar.setVisibility(View.GONE);
            }else {
                switch (getFootState()) {
                    //加载完成的尾部状态
                    case DATA_FINISH:
                        ((MyViewHolderFoot) holder).textView.setVisibility(View.VISIBLE);
                        ((MyViewHolderFoot) holder).textView.setText("加载更多");
                        ((MyViewHolderFoot) holder).progressBar.setVisibility(View.GONE);
                        break;
                    //加载中的尾部状态
                    case DATA_LOADING:
                        ((MyViewHolderFoot) holder).textView.setVisibility(View.GONE);
                        ((MyViewHolderFoot) holder).progressBar.setVisibility(View.VISIBLE);
                        break;
                    //没有更多的尾部状态
                    case DATA_NOMORE:
                        ((MyViewHolderFoot) holder).textView.setVisibility(View.VISIBLE);
                        ((MyViewHolderFoot) holder).textView.setText("没有更多");
                        ((MyViewHolderFoot) holder).progressBar.setVisibility(View.GONE);
                        break;
                }
            }
        }

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

    @Override
    public int getItemCount() {
        return arrayList.size()+1;
    }


    @Override
    public int getItemViewType(int position) {
        //当没有数据源时，只返回尾部
        if (arrayList.size()==0){
            return TYPE_BOTTOM;
        }
        //当position为0时，加载头部
        if(position==0){
            return TYPE_TOP;
            //当position为最后一个时，加载尾部
        }else if(position==arrayList.size()){
            return TYPE_BOTTOM;
        }
        //加载item
        return TYPE_ITEM;
    }

    //item的缓冲类
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

    //头部的缓冲类
    class MyViewHolderTop extends RecyclerView.ViewHolder{
        ImageView imageView_top;
        MarqueeText textView;
        public MyViewHolderTop(View itemView) {
            super(itemView);
            imageView_top = (ImageView) itemView.findViewById(R.id.new_item_top_iv);
            textView = (MarqueeText) itemView.findViewById(R.id.new_item_top_tv);

        }
    }

    //尾部的缓冲类
    class MyViewHolderFoot extends RecyclerView.ViewHolder{
        TextView textView;
        ProgressBar progressBar;
        public MyViewHolderFoot(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.new_item_foot_tv);
            progressBar = (ProgressBar) itemView.findViewById(R.id.new_item_foot_pro);
        }
    }

    public int getFootState() {
        return FootState;
    }

    public void setFootState(int footState) {
        FootState = footState;
    }
    //刷新指定的item
    public void reFresh(int position){
        notifyItemChanged(position);
    }
}
