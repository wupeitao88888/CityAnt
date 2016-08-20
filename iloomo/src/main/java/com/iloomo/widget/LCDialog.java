package com.iloomo.widget;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import com.zhy.http.okhttp.OkHttpUtils;

/**
 * <pre>
 * 业务名:
 * 功能说明:
 * 编写日期:	2015-4-21
 * 编写人员:	 吴培涛
 *
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class LCDialog extends Dialog {
    Context context;
    private View view;
    public boolean isback = false;

    public LCDialog(Context context, View view) {
        super(context); // TODO Auto-generated constructor stub
        this.context = context;
    }

    public LCDialog(Context context, int theme, View view) {
        super(context, theme);
        this.context = context;
        this.view = view;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { // TODO Auto-generated

        super.onCreate(savedInstanceState);
        this.setContentView(view);
//        if(isback){
//            this.setCancelable(false);// 设置点击屏幕Dialog不消失
//        }

        Window window = getWindow();
        // window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
//		window.setWindowAnimations(R.style.mystyle); // 添加动画

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        OkHttpUtils.getInstance().cancelTag(context);
        if (isback) {

            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);

    }

    public void setIsback(boolean isback) {
        this.isback = isback;
    }
}

