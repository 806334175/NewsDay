package com.example.nowingo.newsday.quicksidebardemo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.quicksidebar.QuickSideBarTipsView;
import com.bigkoo.quicksidebar.QuickSideBarView;
import com.bigkoo.quicksidebar.listener.OnQuickSideBarTouchListener;
import com.example.nowingo.newsday.R;
import com.example.nowingo.newsday.db.WeatherExpress;
import com.example.nowingo.newsday.manager.ShareManager;
import com.example.nowingo.newsday.quicksidebardemo.constants.DataConstants;
import com.example.nowingo.newsday.quicksidebardemo.model.City;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.lang.reflect.Type;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class CityActivity extends AppCompatActivity implements OnQuickSideBarTouchListener {
    RecyclerView recyclerView;
    HashMap<String,Integer> letters = new HashMap<>();
    QuickSideBarView quickSideBarView;
    QuickSideBarTipsView quickSideBarTipsView;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        quickSideBarView = (QuickSideBarView) findViewById(R.id.quickSideBarView);
        quickSideBarTipsView = (QuickSideBarTipsView) findViewById(R.id.quickSideBarTipsView);
        editText = (EditText) findViewById(R.id.acticity_city_ed);
        Log.i("msg",WeatherExpress.getInstance(this).getCityMsg("").size()+"");
         set();
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                set(s.toString());
//            }
//        });


    }
//    public void set(String msg){
//        //设置监听
//        quickSideBarView.setOnQuickSideBarTouchListener(this);
//
//
//        //设置列表数据和浮动header
//        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//
//        // Add the sticky headers decoration
//        CityListWithHeadersAdapter adapter = new CityListWithHeadersAdapter();
//
//        //GSON解释出来
//        Type listType = new TypeToken<LinkedList<City>>(){}.getType();
//        Gson gson = new Gson();
//        final LinkedList<City> cities = gson.fromJson(DataConstants.cityDataList, listType);
////        final ArrayList<City> cities = WeatherExpress.getInstance(this).getCityBB(msg);
//
//        ArrayList<String> customLetters = new ArrayList<>();
//
//        int position = 0;
//        for(City city: cities){
//            String letter = city.getFirstLetter();
//            //如果没有这个key则加入并把位置也加入
//            if(!letters.containsKey(letter)){
//                letters.put(letter,position);
//                customLetters.add(letter);
//            }
//            position++;
//        }
//
//        //不自定义则默认26个字母
//        quickSideBarView.setLetters(customLetters);
//        adapter.addAll(cities);
//        recyclerView.setAdapter(adapter);
//        adapter.setOnItemClick(new CityListWithHeadersAdapter.OnItemClick() {
//            @Override
//            public void ItemClick(int p) {
//                ShareManager.saveCity(CityActivity.this,cities.get(p).getCityName());
//                Intent intent = new Intent();
//                intent.setAction("cityname");
//                sendBroadcast(intent);
//                finish();
//            }
//        });
//
//        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
//        recyclerView.addItemDecoration(headersDecor);
//
//        // Add decoration for dividers between list items
//        recyclerView.addItemDecoration(new DividerDecoration(this));
//    }



    public void set(){
        //设置监听
        quickSideBarView.setOnQuickSideBarTouchListener(this);


        //设置列表数据和浮动header
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Add the sticky headers decoration
        CityListWithHeadersAdapter adapter = new CityListWithHeadersAdapter();

        //GSON解释出来
        Type listType = new TypeToken<LinkedList<City>>(){}.getType();
        Gson gson = new Gson();
        final LinkedList<City> cities = gson.fromJson(DataConstants.cityDataList, listType);
//        final ArrayList<City> cities = WeatherExpress.getInstance(this).getCityAA();

        ArrayList<String> customLetters = new ArrayList<>();

        int position = 0;
        for(City city: cities){
            String letter = city.getFirstLetter();
            //如果没有这个key则加入并把位置也加入
            if(!letters.containsKey(letter)){
                letters.put(letter,position);
                customLetters.add(letter);
            }
            position++;
        }

        //不自定义则默认26个字母
        quickSideBarView.setLetters(customLetters);
        adapter.addAll(cities);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClick(new CityListWithHeadersAdapter.OnItemClick() {
            @Override
            public void ItemClick(int p) {
                ShareManager.saveCity(CityActivity.this,cities.get(p).getCityName());
                Intent intent = new Intent();
                intent.setAction("cityname");
                sendBroadcast(intent);
                finish();
            }
        });

        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        recyclerView.addItemDecoration(headersDecor);

        // Add decoration for dividers between list items
        recyclerView.addItemDecoration(new DividerDecoration(this));
    }

    @Override
    public void onLetterChanged(String letter, int position, float y) {
        quickSideBarTipsView.setText(letter, position, y);
        //有此key则获取位置并滚动到该位置
        if(letters.containsKey(letter)) {
            recyclerView.scrollToPosition(letters.get(letter));
        }
    }

    @Override
    public void onLetterTouching(boolean touching) {
        //可以自己加入动画效果渐显渐隐
        quickSideBarTipsView.setVisibility(touching? View.VISIBLE:View.INVISIBLE);
    }

    private static class CityListWithHeadersAdapter extends CityListAdapter<RecyclerView.ViewHolder>
            implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_item, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            TextView textView = (TextView) holder.itemView;
            textView.setText(getItem(position).getCityName());

            if (onItemClick!=null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClick.ItemClick(position);
                    }
                });
            }
        }

        @Override
        public long getHeaderId(int position) {
            return getItem(position).getFirstLetter().charAt(0);

        }

        @Override
        public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_header, parent, false);
            return new RecyclerView.ViewHolder(view) {
            };
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder,int position) {
            TextView textView = (TextView) holder.itemView;
            textView.setText(String.valueOf(getItem(position).getFirstLetter()));
            holder.itemView.setBackgroundColor(getRandomColor());
        }

        private int getRandomColor() {
            SecureRandom rgen = new SecureRandom();
            return Color.HSVToColor(150, new float[]{
                    rgen.nextInt(359), 1, 1
            });
        }
        public interface OnItemClick{
            void ItemClick(int p);
        }
        private OnItemClick onItemClick;

        public void setOnItemClick(OnItemClick onItemClick) {
            this.onItemClick = onItemClick;
        }
    }


}
