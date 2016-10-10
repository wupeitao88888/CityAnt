package com.iloomo.global;



import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


//import com.baidu.location.BDLocation;
//import com.baidu.location.BDLocationListener;
//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;
import com.baidu.mobstat.StatService;
import com.iloomo.model.ApplicationLocationListener;
import com.iloomo.mphoto.App;
import com.iloomo.utils.L;
import com.umeng.analytics.MobclickAgent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.cookie.CookieJarImpl;
import com.zhy.http.okhttp.cookie.store.PersistentCookieStore;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import okhttp3.OkHttpClient;


/**
 * @author wpt
 */
public class MApplication extends App {
    private static List<Activity> activityList = new LinkedList<Activity>();
    //    public static  String VERSION;
    public static Context context;
    private static MApplication app;
    public static String DBMANE = "iloomo.db";

//    private LocationClient mLocationClient = null;
//    private BDLocationListener myListener = new MyLocationListener();
    private ApplicationLocationListener applicationLocationListener;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        context = getApplicationContext();
        app=this;
        checkDataBase();
        // 调试百度统计SDK的Log开关，可以在Eclipse中看到sdk打印的日志，发布时去除调用，或者设置为false
        StatService.setDebugOn(false);
        /***
         * 友盟统计
         */
        MobclickAgent.setDebugMode(true);
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
//        mLocationClient = new LocationClient(getApplicationContext());     //声明Location
//        mLocationClient.registerLocationListener(myListener);    //注册监听函数
//        mLocationClient.start();
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


    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = getSDPath() + DBMANE;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            //database does't exist yet.
            File dbFile = new File(getSDPath());
            if (!dbFile.exists()) {
                dbFile.mkdirs();
            }
            File dbFiles = new File(getSDPath() + DBMANE);
            try {
                dbFiles.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        } else {
            return "";
        }
        String replace = MApplication.context.getPackageName();
        String path = sdDir.toString() + "/Android/data/" + replace + "/database/";
        File dbFolder = new File(path);
        // 目录不存在则自动创建目录
        if (!dbFolder.exists()) {
            dbFolder.mkdirs();
        }
        return path;
    }
    public static String getVoicePath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        } else {
            return "";
        }
        String replace = MApplication.context.getPackageName();
        String path = sdDir.toString() + "/Android/data/" + replace + "/voice/";
        File dbFolder = new File(path);
        // 目录不存在则自动创建目录
        if (!dbFolder.exists()) {
            dbFolder.mkdirs();
        }
        return path;
    }

    public double latitude;
    public double longitude;
    public String street;
    public String city;
    public String district;
    public boolean lbs = false;

//    class MyLocationListener implements BDLocationListener {
//
//        @Override
//        public void onReceiveLocation(BDLocation bdLocation) {
//            L.e("error code : " + bdLocation.getLocType());
//
//
//            if ("161".equals(bdLocation.getLocType() + "")) {
//                //定位成功
//                L.e("纬度 : " + bdLocation.getLatitude());
//                L.e("经度 : " + bdLocation.getLongitude());
//                L.e("街道：" + bdLocation.getStreet());
//                lbs = true;
//                latitude = bdLocation.getLatitude();
//                longitude = bdLocation.getLongitude();
//                city = bdLocation.getCity();
//                district = bdLocation.getDistrict();
//                street = bdLocation.getStreet();
//                if (applicationLocationListener != null) {
//                    applicationLocationListener.onReceiveLocation(bdLocation);
//                }
//            } else if ("66".equals(bdLocation.getLocType() + "")) {
//                lbs = true;
//                L.e("纬度 : " + bdLocation.getLatitude());
//                L.e("经度 : " + bdLocation.getLongitude());
//                L.e("街道：" + bdLocation.getStreet());
//                latitude = bdLocation.getLatitude();
//                longitude = bdLocation.getLongitude();
//                street = bdLocation.getStreet();
//                if (applicationLocationListener != null) {
//                    applicationLocationListener.onReceiveLocation(bdLocation);
//                }
//            } else {
//                //定位失败
//                lbs = false;
//                if (applicationLocationListener != null) {
//                    applicationLocationListener.fild();
//                }
//            }
//            mLocationClient.stop();
//        }
//    }

    public void setOnReceiveLocation(ApplicationLocationListener var1) {
        this.applicationLocationListener = var1;
    }


//    public void startLBS() {
//        mLocationClient = new LocationClient(getApplicationContext());     //声明Location
//        mLocationClient.registerLocationListener(myListener);    //注册监听函数
//        mLocationClient.start();
//        initLocation();
//    }
//
//    private void initLocation() {
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
//        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
//        int span = 1000;
//        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
//        option.setOpenGps(true);//可选，默认false,设置是否使用gps
//        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
//        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
//        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
//        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
//        mLocationClient.setLocOption(option);
//    }


}
