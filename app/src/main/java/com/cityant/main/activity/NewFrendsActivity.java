package com.cityant.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cityant.main.R;
import com.cityant.main.adapter.NewFrendsAdapter;
import com.cityant.main.bean.HandleModel;
import com.cityant.main.bean.MyFrendsModel;
import com.cityant.main.bean.NewFrends;
import com.cityant.main.bean.NewFrendsData;
import com.cityant.main.bean.NewFrendsModel;
import com.cityant.main.global.MYAppconfig;
import com.cityant.main.global.MYTaskID;
import com.iloomo.base.ActivitySupport;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.L;
import com.iloomo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by wupeitao on 16/8/26.
 */
public class NewFrendsActivity extends ActivitySupport implements ThreadCallBack {
    private ListView newfrendslist;
    private NewFrendsAdapter newFrendsAdapter;
    private PtrClassicFrameLayout mPtrFrame;
    private int page = 1;
    private List<NewFrends> newFrends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newfrends);
        setCtenterTitle("新朋友");
        initView();
    }

    private void initView() {
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame);
        newfrendslist = (ListView) findViewById(R.id.newfrendslist);
        newFrends = new ArrayList<>();

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
//        mPtrFrame.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mPtrFrame.autoRefresh();
//            }
//        }, 100);
        newFrendsAdapter = new NewFrendsAdapter(context, newFrends);
        newfrendslist.setAdapter(newFrendsAdapter);
        updateData();
    }

    protected void updateData() {

//        DemoRequestData.getImageList(new RequestFinishHandler<JsonData>() {
//            @Override
//            public void onRequestFinish(final JsonData data) {
//                mPtrFrame.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mAdapter.getDataList().clear();
//                        mAdapter.getDataList().addAll(data.optJson("data").optJson("list").toArrayList());
        page = 1;
        netHttp();
//                        mAdapter.notifyDataSetChanged();
//                    }
//                }, 0);
//            }
//        });
    }

    public void netHttp() {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("page", page+"");
        parameter.put("page_size", 20+"");

        startHttpRequst(MYAppconfig.NEWFRENDS, parameter, MYTaskID.NEWFRENDS);
    }


    public void startHttpRequst(String url,
                                Map<String, Object> parameter, int resultCode) {

        L.e(url+"______"+resultCode);
        new AsyncHttpPost(this, url, parameter, resultCode,NewFrendsModel.class, context);
    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        switch (resultCode) {
            case MYTaskID.NEWFRENDS:
                NewFrendsModel baseModel = (NewFrendsModel) modelClass;
                if (page == 1) {
                    mPtrFrame.refreshComplete();
                    newFrends.clear();
                    newFrends.addAll(baseModel.getData().getFriend_list());
                    newFrendsAdapter.notifyDataSetChanged();
                } else {
                    newFrends.addAll(baseModel.getData().getFriend_list());
                    newFrendsAdapter.notifyDataSetChanged();
                }
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
