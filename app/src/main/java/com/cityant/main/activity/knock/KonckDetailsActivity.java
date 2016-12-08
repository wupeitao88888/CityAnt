package com.cityant.main.activity.knock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cityant.main.R;

/**
* 小额抢详情
* @author lvfl
* @time 2016/12/4 17:14
*/
public class KonckDetailsActivity extends AppCompatActivity {

    public static void startActivity(Context context){
        Intent intent = new Intent(context,KonckDetailsActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konck_details_layout);
//        setCtenterTitle("详情");
    }
}
