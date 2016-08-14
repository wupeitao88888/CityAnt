package com.cityant.main.fragment;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.activity.DoTaskActivity;
import com.cityant.main.activity.LocationChoiceActivity;
import com.cityant.main.activity.SearchActivity;
import com.cityant.main.adapter.FragmentHomeAdapter;
import com.iloomo.base.FragmentSupport;



import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends FragmentSupport {


//    private NOViewPagerPullableListView mlist;
//    private FragmentHomeAdapter fragmentHomeAdapter = null;
//    private PullToRefreshLayout pullview;
    private TextView position_text;
    private EditText search_edit;
    private LinearLayout sing_ll;
    private LinearLayout sing_display_ll;

    @Override
    public View setTitleBar(View view) {
        isLeftVisibility(false);
        return super.setTitleBar(view);
    }

    @Override
    public View initView() {
        final View view = LayoutInflater.from(context).inflate(R.layout.fragment_home, null);
        setTitle("主页");
        position_text = (TextView) view.findViewById(R.id.position_text);
        search_edit = (EditText) view.findViewById(R.id.search_edit);
        sing_ll = (LinearLayout) view.findViewById(R.id.sing_ll);
        sing_display_ll = (LinearLayout) view.findViewById(R.id.sing_display_ll);
        position_text.setOnClickListener(v ->LocationChoiceActivity.startActivity(context));
        search_edit.setOnClickListener(v -> SearchActivity.startActivity(context));
        sing_ll.setOnClickListener(v -> DoTaskActivity.startActivity(context));
        sing_display_ll.setOnClickListener(v -> DoTaskActivity.startActivity(context));

        FragmentHomeAdapter adapter = new FragmentHomeAdapter(context,new ArrayList<>());

        return view;
    }




}
