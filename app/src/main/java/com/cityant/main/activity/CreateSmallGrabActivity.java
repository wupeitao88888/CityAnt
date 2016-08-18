package com.cityant.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;

/**
 * Created by lvfl on 2016/8/18.
 */
public class CreateSmallGrabActivity extends ActivitySupport {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context,CreateSmallGrabActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_small_grab_layout);
    }
}
