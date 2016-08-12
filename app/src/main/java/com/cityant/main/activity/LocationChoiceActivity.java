package com.cityant.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.cityant.main.R;
import com.cityant.main.adapter.LocationChoiceAdapter;
import com.iloomo.base.ActivitySupport;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by lvfl on 2016/8/10.
 */
public class LocationChoiceActivity extends ActivitySupport {

    private ListView listView;
    private List<String> list = new ArrayList<>();

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LocationChoiceActivity.class);
        context.startActivity(intent);
    }

    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_choice_layout);
        setRemoveTitle();
        initView();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);
        for (int i = 0; i < 10; i++) {
            list.add(i + "");
        }
        LocationChoiceAdapter adapter = new LocationChoiceAdapter(list, context);
        listView.setAdapter(adapter);


    }
}
