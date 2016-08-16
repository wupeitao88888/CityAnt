package com.cityant.main.fragment;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.activity.DoTaskActivity;
import com.cityant.main.activity.HomeDetailsActivity;
import com.cityant.main.activity.LocationChoiceActivity;
import com.cityant.main.activity.SearchActivity;
import com.cityant.main.adapter.FragmentHomeAdapter;
import com.iloomo.base.FragmentSupport;



import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends FragmentSupport implements AbsListView.OnScrollListener,View.OnClickListener {

    private TextView position_text;
    private EditText search_edit;
    private LinearLayout sing_ll;
    private LinearLayout sing_display_ll;
    private ListView listView;
    private List<String> list = new ArrayList<>();
    private boolean isShow_title_tab;
    private LinearLayout home_top_ll;
    private TextView recommend_text_head;
    private TextView nearby_text_head;
    private TextView city_text_head;
    private TextView friends_text_head;
    private TextView recommend_text;
    private TextView nearby_text;
    private TextView city_text;
    private TextView friends_text;

    @Override
    public View setTitleBar(View view) {
        isLeftVisibility(false);
        return super.setTitleBar(view);
    }

    @Override
    public View initView() {
        final View view = LayoutInflater.from(context).inflate(R.layout.fragment_home, null);
        setTitle("主页");
        listView = (ListView) view.findViewById(R.id.listView);
        home_top_ll = (LinearLayout) view.findViewById(R.id.home_top_ll);
        recommend_text = (TextView) view.findViewById(R.id.recommend_text);
        nearby_text = (TextView) view.findViewById(R.id.nearby_text);
        city_text = (TextView) view.findViewById(R.id.city_text);
        friends_text = (TextView) view.findViewById(R.id.friends_text);

        View head_view = LayoutInflater.from(context).inflate(R.layout.fragment_home_head_layout, null);
        position_text = (TextView) head_view.findViewById(R.id.position_text);
        search_edit = (EditText) head_view.findViewById(R.id.search_edit);
        sing_ll = (LinearLayout) head_view.findViewById(R.id.sing_ll);
        sing_display_ll = (LinearLayout) head_view.findViewById(R.id.sing_display_ll);
        position_text.setOnClickListener(v ->LocationChoiceActivity.startActivity(context));
        search_edit.setOnClickListener(v -> SearchActivity.startActivity(context));
        sing_ll.setOnClickListener(v -> DoTaskActivity.startActivity(context));
        sing_display_ll.setOnClickListener(v -> DoTaskActivity.startActivity(context));

        View head_view_top = LayoutInflater.from(context).inflate(R.layout.fragment_home_head_top_layout, null);
        recommend_text_head = (TextView) head_view_top.findViewById(R.id.recommend_text_head);
        nearby_text_head = (TextView) head_view_top.findViewById(R.id.nearby_text_head);
        city_text_head = (TextView) head_view_top.findViewById(R.id.city_text_head);
        friends_text_head = (TextView) head_view_top.findViewById(R.id.friends_text_head);

        recommend_text_head.setOnClickListener(this);
        nearby_text_head.setOnClickListener(this);
        city_text_head.setOnClickListener(this);
        friends_text_head.setOnClickListener(this);
        recommend_text.setOnClickListener(this);
        nearby_text.setOnClickListener(this);
        city_text.setOnClickListener(this);
        friends_text.setOnClickListener(this);

        listView.addHeaderView(head_view);
        listView.addHeaderView(head_view_top);
        for(int i = 0;i<10;i++){
            list.add(i+"");
        }
        FragmentHomeAdapter adapter = new FragmentHomeAdapter(context,list);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HomeDetailsActivity.startActivity(context);
            }
        });
        return view;
    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int i1, int i2) {
        if (firstVisibleItem > 0 && !isShow_title_tab) {
            isShow_title_tab = true;
            home_top_ll.setVisibility(View.VISIBLE);
        } else if (firstVisibleItem < 1 && isShow_title_tab) {
            isShow_title_tab = false;
            home_top_ll.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.recommend_text_head:
            case R.id.recommend_text:
                setSelectTool();
                recommend_text_head.setSelected(true);
                recommend_text.setSelected(true);
                break;
            case R.id.nearby_text_head:
            case R.id.nearby_text:
                setSelectTool();
                nearby_text_head.setSelected(true);
                nearby_text.setSelected(true);
                break;
            case R.id.city_text_head:
            case R.id.city_text:
                setSelectTool();
                city_text_head.setSelected(true);
                city_text.setSelected(true);
                break;
            case R.id.friends_text_head:
            case R.id.friends_text:
                setSelectTool();
                friends_text_head.setSelected(true);
                friends_text.setSelected(true);
                break;
        }
    }

    private void setSelectTool() {
        if(recommend_text_head.isSelected()){
            recommend_text_head.setSelected(false);
        }
        if(recommend_text.isSelected()){
            recommend_text.setSelected(false);
        }
        if(nearby_text_head.isSelected()){
            nearby_text_head.setSelected(false);
        }
        if(nearby_text.isSelected()){
            nearby_text.setSelected(false);
        }
        if(city_text_head.isSelected()){
            city_text_head.setSelected(false);
        }
        if(city_text.isSelected()){
            city_text.setSelected(false);
        }
        if(friends_text_head.isSelected()){
            friends_text_head.setSelected(false);
        }
        if(friends_text.isSelected()){
            friends_text.setSelected(false);
        }
    }
}
