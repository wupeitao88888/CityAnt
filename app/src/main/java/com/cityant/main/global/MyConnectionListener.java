package com.cityant.main.global;


import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.util.NetUtils;
import com.iloomo.utils.L;
import com.iloomo.utils.ToastUtil;

/**
 * Created by wupeitao on 16/8/18.
 */
public class MyConnectionListener
        implements EMConnectionListener {
    private Context context;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case EMError.USER_REMOVED:// 显示帐号已经被移除
                    break;
                case EMError.USER_LOGIN_ANOTHER_DEVICE:// 显示帐号在其他设备登录
                    break;
                case CONNECTED:
                    ToastUtil.showShort(context,"已连接");
                    break;
                default:
                    if (NetUtils.hasNetwork(context)) {
                        //连接不到聊天服务器
                    } else {
                        //当前网络不可用，请检查网络设置
                    }
                    break;
            }
        }
    };
    private static final int CONNECTED = 10001;

    public MyConnectionListener(Context context) {
        this.context = context;
    }

    @Override
    public void onConnected() {
        Message msg = new Message();
        msg.what = CONNECTED;
        msg.obj = "";
        handler.sendMessage(msg);
    }

    @Override
    public void onDisconnected(int error) {
        Message msg = new Message();
        msg.what = error;
        msg.obj = "";
        handler.sendMessage(msg);
    }


}
