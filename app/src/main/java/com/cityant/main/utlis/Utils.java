package com.cityant.main.utlis;

import android.app.ProgressDialog;
import android.content.Context;

/**
* 工具
* @author Lvfl
* created at 2016/12/7 16:14
*/
public class Utils {

    public static ProgressDialog getProgressDialog(Context c, String title,
                                                   String message) {
        ProgressDialog mProDialog = ProgressDialog.show(c, null, message, true,
                true);
        mProDialog.setCanceledOnTouchOutside(false);
        return mProDialog;
    }
}
