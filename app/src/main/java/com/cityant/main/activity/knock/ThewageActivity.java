package com.cityant.main.activity.knock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.cityant.main.R;
import com.cityant.main.adapter.konck.TheWageAdapter;
import com.iloomo.base.ActivitySupport;

import java.util.ArrayList;
import java.util.List;

/**
* 领工资
* @author lvfl
* @time 2016/12/4 15:49
*/
public class TheWageActivity extends ActivitySupport {

    private ListView money_listView;
    private TheWageAdapter adapter;

    public static void startActivity(Context context){
        Intent intent = new Intent(context,TheWageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_wage_layout);
        setCtenterTitle("领工资");
        initView();
    }

    private void initView() {
        money_listView = (ListView) findViewById(R.id.money_listView);
        View head_view = LayoutInflater.from(this).inflate(R.layout.money_head_view_layout,null);

        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        adapter = new TheWageAdapter(this,list);
        money_listView.addHeaderView(head_view);
        money_listView.setAdapter(adapter);

    }
}
