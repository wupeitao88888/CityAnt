package com.cityant.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.cityant.main.R;
import com.cityant.main.adapter.StoreHouseAdapter;
import com.cityant.main.bean.GoodsListModel;
import com.cityant.main.bean.UpdateUserInfoModel;
import com.cityant.main.db.DBControlApp;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.threadpool.MyThreadPool;
import com.iloomo.utils.DialogUtil;
import com.iloomo.utils.ToastUtil;
import com.iloomo.utils.UnicodeUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by wupeitao on 16/8/13.
 */
public class StorehouseActivity extends ActivitySupport {

    private ListView goods;
    private StoreHouseAdapter storeHouseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storehouse);
        setCtenterTitle(R.string.warehouse);

        initView();
        getStoreList();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    private void initView() {
        goods = (ListView) findViewById(R.id.goods);
    }

    /***
     * 查看物流
     *
     * @param view
     */
    public void onLogisticsClick(View view) {
        mIntent(this, LogisticsActivity.class);
    }

    /***
     * 商品提出
     *
     * @param view
     */
    public void onGoodsClick(View view) {
        startActivityForResult(new Intent(this, GoodsPutActivity.class).putExtra("type", 0), 1000);
    }

    /***
     * 商品卖出
     *
     * @param view
     */
    public void onGoodssellClick(View view) {
        startActivityForResult(new Intent(this, GoodsPutActivity.class).putExtra("type", 1), 1000);
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        switch (arg1) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 1000:
                getStoreList();
                break;
        }
    }

    public void getStoreList() {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        DialogUtil.startDialogLoading(context);
        OkHttpUtils
                .post()
                .url(MYAppconfig.STORE)
                .params(parameter)
                .tag(context)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showShort(context, e.getMessage());
                        DialogUtil.stopDialogLoading(context);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        response = UnicodeUtils.decodeUnicode(response);
                        DialogUtil.stopDialogLoading(context);
                        GoodsListModel model = JSON.parseObject(response, GoodsListModel.class);
                        GoodsListModel baseMadel = (GoodsListModel) model;
                        if (baseMadel.getCode().equals("200")) {
                            storeHouseAdapter = new StoreHouseAdapter(context, baseMadel.getData().getGoods_list());
                            goods.setAdapter(storeHouseAdapter);
                        } else {
                            ToastUtil.showShort(context, baseMadel.getData().getCode_message());
                        }
                    }
                });
    }

}
