package com.cityant.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cityant.main.R;
import com.cityant.main.adapter.MeCrowdAdapter;
import com.cityant.main.bean.MeCrowdModel;
import com.cityant.main.bean.MyFrendsModel;
import com.cityant.main.global.MYAppconfig;
import com.cityant.main.global.MYTaskID;
import com.iloomo.base.ActivitySupport;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 我的群聊
 * Created by wupeitao on 16/8/26.
 */
public class MeCrowdActivity extends ActivitySupport implements ThreadCallBack, AdapterView.OnItemClickListener {
    private ListView mecrowds;
    private MeCrowdAdapter meCrowdAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mecrowd);
        setCtenterTitle(mString(R.string.mequn));

        titleBar.setFristMenuimgIsVisbility(View.VISIBLE);
        titleBar.setRightFristMenuimgListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mecrowds = (ListView) findViewById(R.id.mecrowds);
        mecrowds.setOnItemClickListener(this);
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("page", "1");
        parameter.put("page_size", "1000");

        startHttpRequst(MYAppconfig.CROWD_LIST, parameter, MYTaskID.CROWD_LIST);
    }

    public void startHttpRequst(String url,
                                Map<String, Object> parameter, int resultCode) {

        new AsyncHttpPost(this, url, parameter, resultCode,
                MeCrowdModel.class, context);
    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        switch (resultCode) {
            case MYTaskID.CROWD_LIST:
                MeCrowdModel baseModel = (MeCrowdModel) modelClass;
                meCrowdAdapter = new MeCrowdAdapter(context, baseModel.getData().getGroup_list());
                mecrowds.setAdapter(meCrowdAdapter);
                break;
        }
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
        MeCrowdModel baseModel = (MeCrowdModel) modelClass;
        ToastUtil.showShort(context, baseModel.getData().getCode_message());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
