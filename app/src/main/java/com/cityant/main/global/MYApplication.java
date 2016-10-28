package com.cityant.main.global;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.hyphenate.easeui.bean.LoginUserInfoData;

import com.cityant.main.utlis.FaceConversionUtil;
import com.hyphenate.chatuidemo.DemoApplication;
import com.hyphenate.easeui.db.DBControl;
import com.hyphenate.easeui.db.DbHelper;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.threadpool.MyThreadPool;
import com.iloomo.utils.L;
import com.iloomo.utils.LCSharedPreferencesHelper;

import java.util.Iterator;
import java.util.List;


/**
 * Created by wupeitao on 16/8/18.
 */
public class MYApplication extends DemoApplication {
    private final int GETLOGINFO = 100;
    private LCSharedPreferencesHelper lcSharedPreferencesHelper;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GETLOGINFO:
                    MYAppconfig.loginUserInfoData = (LoginUserInfoData) msg.obj;
                    break;
            }
        }
    };
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        checkDb();
        initDate();
//        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(false);
        //初始化
//        EMClient.getInstance().init(context, options);
//        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
//        EMClient.getInstance().setDebugMode(true);

        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(context.getPackageName())) {
            L.e("enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        FaceConversionUtil.getInstace().getFileText(context);

//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());

    }

    private void initDate() {
        MyThreadPool.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Message message = new Message();
                    message.what = GETLOGINFO;
                    message.obj = DBControl.getInstance(context).selectLoginInfo();
                    handler.sendMessage(message);
                } catch (Exception e) {

                }

            }
        });


    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    private void checkDb() {
        lcSharedPreferencesHelper = LCSharedPreferencesHelper.instance(context, LCSharedPreferencesHelper.ILOOMO);
        DbHelper dbHelper = new DbHelper(context);
        DBControl dbControl = new DBControl(context, dbHelper);
        try {
            if (TextUtils.isEmpty(lcSharedPreferencesHelper.getValue(LCSharedPreferencesHelper.UPDATE_DB))) {
                lcSharedPreferencesHelper.putValue(LCSharedPreferencesHelper.UPDATE_DB, DBControl.DB_VERSION);
                dbControl.deleteAllTab();
                dbControl.createAllTab();
            } else {
                if (!DBControl.DB_VERSION.equals(lcSharedPreferencesHelper.getValue(LCSharedPreferencesHelper.UPDATE_DB))) {
                    lcSharedPreferencesHelper.putValue(LCSharedPreferencesHelper.UPDATE_DB, DBControl.DB_VERSION);
                    dbControl.deleteAllTab();
                    dbControl.createAllTab();
                }
            }
        } catch (Exception e) {
        }
    }
}
