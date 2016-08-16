package com.cityant.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvfl on 2016/8/16.
 */
public class HomeDetailsActivity extends ActivitySupport {

    private ListView details_listView;
    private List<String> list = new ArrayList<>();

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, HomeDetailsActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_details_layout);
        initView();
    }

    private void initView() {
        setCtenterTitle("详情");
        View view = LayoutInflater.from(context).inflate(R.layout.activity_home_details_head_layout,null);
        details_listView = (ListView) findViewById(R.id.details_listView);
        details_listView.addHeaderView(view);
        for(int i = 0;i<4;i++){
            list.add(i+"");
        }
        DetailsAdapter adapter = new DetailsAdapter();
        details_listView.setAdapter(adapter);
    }

    class DetailsAdapter extends BaseAdapter{

        public DetailsAdapter(){

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            String string = (String) getItem(i);
            if(null == view){
                view = LayoutInflater.from(context).inflate(R.layout.home_details_item_layout,null);
            }


            return view;
        }
    }
}
