package com.cityant.main.activity.knock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.adapter.konck.TribeDetailsAdapter;
import com.iloomo.base.ActivitySupport;
import com.iloomo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
* 部落详情
* @author lvfl
* @time 2016/12/4 12:54
*/
public class TribeDetailsActivity extends ActivitySupport {

    private String title_name;
    private ListView tribe_listView;
    private TribeDetailsAdapter adapter;
    private ImageView tribe_head_image;
    private TextView follow_num_text;
    private TextView hot_num_text;
    private TextView tribe_single_text;
    private RelativeLayout follow_btn_rl;
    private RelativeLayout money_rl;
    private RelativeLayout chat_rl;
    private RelativeLayout more_follow_rl;
    private LinearLayout follow_ll;
    private LinearLayout brand_array_ll;
    private TextView post_knock;
    private TextView konck_record;


    public static void startActivity(Context context, String title_name){
        Intent intent = new Intent(context,TribeDetailsActivity.class);
        intent.putExtra("title_name",title_name);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title_name = getIntent().getStringExtra("title_name");
        setContentView(R.layout.activity_tribe_details_layout);
        setCtenterTitle(title_name+"");
        setRightTitleRes(R.drawable.icon_fenxiang);
        setRightTitleImageListener(v -> {
            ToastUtil.show(this,"分享",ToastUtil.SHOW_TOAST);
        });
        initView();
    }

    private void initView() {
        tribe_listView = (ListView) findViewById(R.id.tribe_listView);
        View head_view = LayoutInflater.from(this).inflate(R.layout.tribe_details_head_layout,null);
        tribe_head_image = (ImageView) head_view.findViewById(R.id.tribe_head_image);
        follow_num_text = (TextView) head_view.findViewById(R.id.follow_num_text);
        hot_num_text = (TextView) head_view.findViewById(R.id.hot_num_text);
        tribe_single_text = (TextView) head_view.findViewById(R.id.tribe_single_text);
        follow_btn_rl = (RelativeLayout) head_view.findViewById(R.id.follow_btn_rl);
        money_rl = (RelativeLayout) head_view.findViewById(R.id.money_rl);
        chat_rl = (RelativeLayout) head_view.findViewById(R.id.chat_rl);
        more_follow_rl = (RelativeLayout) head_view.findViewById(R.id.more_follow_rl);
        follow_ll = (LinearLayout) head_view.findViewById(R.id.follow_ll);
        brand_array_ll = (LinearLayout) head_view.findViewById(R.id.brand_array_ll);
        post_knock = (TextView) head_view.findViewById(R.id.post_knock);
        konck_record = (TextView) head_view.findViewById(R.id.konck_record);

        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        adapter = new TribeDetailsAdapter(context,list);
        tribe_listView.addHeaderView(head_view);
        tribe_listView.setAdapter(adapter);

        for( int i = 0; i < 10 ; i++ ){
            brand_array_ll.addView(addView());
        }
        for( int i = 0; i < 10 ; i++ ){
            follow_ll.addView(addFollowView());
        }
        more_follow_rl.setOnClickListener(v -> {
            TribeFollowActivity.startActivity(this);
        });
        money_rl.setOnClickListener(v -> {
            TheWageActivity.startActivity(this);
        });
    }

    private View addFollowView() {
        LayoutInflater inflater3 = LayoutInflater.from(this);
        View view = inflater3.inflate(R.layout.follow_add_view_layout, null);
        return view;
    }
    private View addView() {
        LayoutInflater inflater3 = LayoutInflater.from(this);
        View view = inflater3.inflate(R.layout.brand_item_layout, null);

        view.setOnClickListener(v -> {
            TribeDetailsActivity.startActivity(getContext(),"屈臣氏");
        });

        return view;
    }
}
