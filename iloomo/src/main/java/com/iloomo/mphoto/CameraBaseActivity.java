package com.iloomo.mphoto;

import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.iloomo.base.PBaseActivity;


public class CameraBaseActivity extends PBaseActivity {
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        CameraManager.getInst().addActivity(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CameraManager.getInst().removeActivity(this);
    }

    /**
     * TOAST
     *
     * @param msg    消息
     * @param period 时长
     */
    public void toast(String msg, int period) {
        Toast.makeText(this, msg, period).show();
    }
}
