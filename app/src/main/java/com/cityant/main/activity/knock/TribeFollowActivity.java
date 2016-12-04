package com.cityant.main.activity.knock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import com.cityant.main.R;
import com.cityant.main.adapter.konck.TribeFollowAdapter;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.bean.BaseModel;
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
    private TribeFollowAdapter adapter;

    public static void startActivity(Context context){
        Intent intent = new Intent(context,TribeFollowActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tribe_follow_layout);
        setCtenterTitle("所有关注（258）");
        initView();
    }

    private void initView() {
        follow_listView = (ListView) findViewById(R.id.follow_listView);
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        adapter = new TribeFollowAdapter(this,list);
        follow_listView.setAdapter(adapter);
    }

    private void getFollowOver(){
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        new AsyncHttpPost(this, MYAppconfig.ADDFRENDS, parameter,  MYTaskID.ADDFRENDS,
                BaseModel.class, context);
    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {

    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {

    }
}
