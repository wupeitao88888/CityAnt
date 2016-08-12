package com.iloomo.global;



import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.baidu.mobstat.StatService;
import com.iloomo.mphoto.App;
import com.squareup.otto.Bus;
import com.umeng.analytics.MobclickAgent;


/**
 * @author wpt
 */
public class MApplication extends App {
    private static List<Activity> activityList = new LinkedList<Activity>();
    //    public static  String VERSION;
    public static Context context;
    private static MApplication app;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        context = getApplicationContext();
        app=this;
        // 调试百度统计SDK的Log开关，可以在Eclipse中看到sdk打印的日志，发布时去除调用，或者设置为false
        StatService.setDebugOn(false);
        /***
         * 友盟统计
         */
        MobclickAgent.setDebugMode(true);
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
    }

    public static synchronized MApplication getInstance() {
        return app;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        MobclickAgent.onKillProcess(context);
        System.gc();
        System.exit(0);
    }
    
    
   

    
    
  
}
