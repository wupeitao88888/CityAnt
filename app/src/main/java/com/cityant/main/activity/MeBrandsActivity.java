package com.cityant.main.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.cityant.main.R;
import com.cityant.main.adapter.MeBrandsAdapter;
import com.cityant.main.bean.MeBrands;
import com.iloomo.base.ActivitySupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wupeitao on 16/8/26.
 */
public class MeBrandsActivity extends ActivitySupport {
    private ListView brandslist;
    private MeBrandsAdapter meBrandsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mebrands);
        brandslist = (ListView) findViewById(R.id.brandslist);
        List<MeBrands> meBrandses = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MeBrands meBrands = new MeBrands();
            meBrands.setBrand_id(i + "");
            meBrands.setBrand_name("我的品牌");
            meBrandses.add(meBrands);
        }
        meBrandsAdapter = new MeBrandsAdapter(context, meBrandses);
        brandslist.setAdapter(meBrandsAdapter);
    }
}
