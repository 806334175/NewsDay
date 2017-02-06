package com.example.nowingo.newsday.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.activity.ChangeChooseActivity;
import com.example.nowingo.newsday.adapter.NewsViewPagerAdapter;
import com.example.nowingo.newsday.db.ChannelDbExpress;
import com.example.nowingo.newsday.entity.Channel;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2016/12/21.
 */
public class NewsFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView imageView_change;
    ArrayList<Fragment> arrayList;
    ArrayList<Channel> channelArrayList;
    NewsViewPagerAdapter newsViewPagerAdapter;
    ChannelDbExpress cde;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = (TabLayout) view.findViewById(R.id.fragment_news_tablayout);
        viewPager = (ViewPager) view.findViewById(R.id.fragment_news_viewpager);
        imageView_change = (ImageView) view.findViewById(R.id.fragment_news_changechoose_iv);
        initdata();
    }

    private void initdata(){
        //通过获得子碎片管理器，构造适配器
        newsViewPagerAdapter = new NewsViewPagerAdapter(getChildFragmentManager());
        //获得数据库操作器
        cde = new ChannelDbExpress(getContext());
        channelArrayList = new ArrayList<>();
        //通过是否选择获得集合
        channelArrayList = cde.getNameByChoose("true");
        //设置title数据
        newsViewPagerAdapter.setStringArrayList(channelArrayList);
        arrayList = new ArrayList<>();
        //更具不同的title传给不同的fragment
        for (int i = 0; i <channelArrayList.size() ; i++) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("A",channelArrayList.get(i));
                NewsViewPagerFragment newsViewPagerFragment=new NewsViewPagerFragment();
            //将bundle传给子Fragment
                newsViewPagerFragment.setArguments(bundle);
            //添加Fragment数据
                arrayList.add(newsViewPagerFragment);
        }
        //设置Fragment数据
        newsViewPagerAdapter.setArrayList(arrayList);
        //设置标题颜色
        tabLayout.setTabTextColors(Color.parseColor("#338fce"),Color.parseColor("#fdb527"));
        //设置标题下横线颜色
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#338fce"));
        //设置模式
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //设置页面切换动画
        viewPager.setPageTransformer(true,new ZoomOutPageTransformer());
        //给viewpager设置适配器
        viewPager.setAdapter(newsViewPagerAdapter);
        //将tablayout和viewpager绑定
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onResume() {
        super.onResume();
        imageView_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangeChooseActivity.class);
                startActivityForResult(intent,1);
            }
        });
//        initdata();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            initdata();
        }
    }

    /**
     * viewpager页面切换动画
     */
    public class ZoomOutPageTransformer implements ViewPager.PageTransformer
    {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;


        public void transformPage(View view, float position)
        {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1)
            { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0)
                {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else
                {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE)
                        / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else
            { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }

}
