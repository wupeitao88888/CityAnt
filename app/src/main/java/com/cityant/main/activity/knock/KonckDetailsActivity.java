package com.cityant.main.activity.knock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;

/**
* 小额抢详情
* @author lvfl
* @time 2016/12/4 17:14
*/
public class KonckDetailsActivity extends ActivitySupport {

    public static void startActivity(Context context){
        Intent intent = new Intent(context,KonckDetailsActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konck_details_layout);
        setCtenterTitle("详情");
    }
}
