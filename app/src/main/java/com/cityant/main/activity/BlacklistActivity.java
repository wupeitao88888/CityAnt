package com.cityant.main.activity;

import android.os.Bundle;
import android.view.View;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;

/**
 * Created by wupeitao on 16/8/17.
 */
public class BlacklistActivity extends ActivitySupport {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);
        setCtenterTitle(R.string.blacklist);
        setRightTitle(mString(R.string.Relieve));
        setRightTitleImageListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
