package com.cityant.main.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;

/**
 * Created by wupeitao on 16/8/19.
 */
public class MyFrendActivity extends ActivitySupport {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfrend);
        setCtenterTitle(R.string.addressbook);
    }
}
