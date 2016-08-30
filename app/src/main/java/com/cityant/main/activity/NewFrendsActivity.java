package com.cityant.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cityant.main.R;
import com.cityant.main.adapter.NewFrendsAdapter;
import com.cityant.main.bean.HandleModel;
import com.cityant.main.bean.MyFrendsModel;
import com.cityant.main.bean.NewFrendsModel;
import com.cityant.main.global.MYAppconfig;
import com.cityant.main.global.MYTaskID;
import com.iloomo.base.ActivitySupport;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wupeitao on 16/8/26.
 */
public class NewFrendsActivity extends ActivitySupport implements ThreadCallBack, AdapterView.OnItemClickListener {
    private ListView newfrendslist;
    private NewFrendsAdapter newFrendsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfrends);
        newfrendslist = (ListView) findViewById(R.id.newfrendslist);
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("page", "1");
        parameter.put("page_size", "1000");

        startHttpRequst(MYAppconfig.NEWFRENDS, parameter, MYTaskID.NEWFRENDS);

    }

    public void startHttpRequst(String url,
                                Map<String, Object> parameter, int resultCode) {

        new AsyncHttpPost(this, url, parameter, resultCode,
                NewFrendsModel.class, context);
    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        switch (resultCode) {
            case MYTaskID.NEWFRENDS:
                NewFrendsModel baseModel = (NewFrendsModel) modelClass;
                newFrendsAdapter = new NewFrendsAdapter(context, baseModel.getData().getFriend_list());
                newfrendslist.setAdapter(newFrendsAdapter);
                break;
            case MYTaskID.HANDLERFRENDS:
                HandleModel handleModel = (HandleModel) modelClass;
                ToastUtil.showShort(context, handleModel.getData().getCode_message());
                break;
        }
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
        switch (resultCode) {
            case MYTaskID.NEWFRENDS:
                NewFrendsModel baseModel = (NewFrendsModel) modelClass;
                ToastUtil.showShort(context, baseModel.getData().getCode_message());
                break;
            case MYTaskID.HANDLERFRENDS:
                HandleModel handleModel = (HandleModel) modelClass;
                ToastUtil.showShort(context, handleModel.getData().getCode_message());
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    /***
     * 处理好友请求
     *
     * @param process   处理 (1:同意,2:拒绝)
     * @param friend_id 好友id
     */
    public void HandleFrends(int process, int friend_id) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("process", process);
        parameter.put("friend_id", friend_id);
        new AsyncHttpPost(this, MYAppconfig.HANDLEFRENDS, parameter, MYTaskID.HANDLERFRENDS,
                HandleModel.class, context);
    }

}
