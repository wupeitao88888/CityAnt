package com.cityant.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;

/**
 * Created by wupeitao on 16/8/13.
 */
public class StorehouseActivity extends ActivitySupport {

    private ListView goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storehouse);
        setCtenterTitle(R.string.warehouse);

        initView();
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
        startActivity(new Intent(this, GoodsPutActivity.class));
    }

    /***
     * 商品卖出
     *
     * @param view
     */
    public void onGoodssellClick(View view) {
    }

}
