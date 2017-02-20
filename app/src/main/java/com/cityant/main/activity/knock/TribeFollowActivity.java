package com.cityant.main.activity.knock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.cityant.main.R;
import com.cityant.main.adapter.base.CommonAdapter;
import com.cityant.main.adapter.base.ViewHolder;
import com.cityant.main.bean.konck.ActivityList;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* 品牌部落所有关注
* @author lvfl
* @time 2016/12/4 15:28
*/
public class TribeFollowActivity extends ActivitySupport implements ThreadCallBack{

    private ListView follow_listView;
    private CommonAdapter adapter;
    private String brand_id;
    private List<ActivityList.Data.UserList> user_lists = new ArrayList<>();

    public static void startActivity(Context context,String brand_id){
        Intent intent = new Intent(context,TribeFollowActivity.class);
        intent.putExtra("brand_id",brand_id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tribe_follow_layout);
        brand_id = getIntent().getStringExtra("brand_id");
        initView();
    }

    private void initView() {
        follow_listView = (ListView) findViewById(R.id.follow_listView);

        adapter = new CommonAdapter(this, R.layout.follow_item_layout, user_lists) {
            @Override
            protected void convert(ViewHolder viewHolder, Object item, int position) {
                viewHolder.setCircleImageUrl(R.id.user_image,user_lists.get(position).getUser_avar());
                viewHolder.setCircleImageUrl(R.id.user_name,user_lists.get(position).getUser_name());
            }
        };
        follow_listView.setAdapter(adapter);
        getFollowOver();
    }

    private void getFollowOver(){
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("brand_id", brand_id);
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        new AsyncHttpPost(this, MYAppconfig.BRAND_ACTIVITY_LIST, parameter,  MYTaskID.BRAND_ACTIVITY_LIST_ID,
                ActivityList.class, context);
    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        if (resultCode == MYTaskID.BRAND_ACTIVITY_LIST_ID){
            ActivityList activityList = (ActivityList) modelClass;
            setCtenterTitle("所有关注（"+activityList.data.getActive_count()+"）");
            if ("200".equals(activityList.getCode())){
                user_lists.addAll(activityList.data.user_list);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {

    }
}
