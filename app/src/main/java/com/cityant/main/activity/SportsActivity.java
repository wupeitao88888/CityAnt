package com.cityant.main.activity;

import android.os.Bundle;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;

/**
 * Created by wupeitao on 16/8/13.
 */
public class SportsActivity extends ActivitySupport {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports);
        setCtenterTitle(R.string.activity);
    }
}
