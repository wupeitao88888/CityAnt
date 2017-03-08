package com.cityant.main.activity;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.TimePickerView;
import com.cityant.main.R;
import com.cityant.main.bean.EvaluateListModel;
import com.cityant.main.bean.UniversallyModel;
import com.cityant.main.bean.UpdateUserInfoModel;
import com.cityant.main.bean.UserInfoData;
import com.cityant.main.bean.UserInfoModel;
import com.cityant.main.db.DBControlApp;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.easeui.bean.LoginUserInfoData;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.adapter.PMyAdapter;
import com.iloomo.base.ActivitySupport;
import com.iloomo.global.MApplication;
import com.iloomo.mphoto.CameraManager;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.threadpool.MyThreadPool;
import com.iloomo.ui.PMyPhoto;
import com.iloomo.ui.PViewPagerPActivity;
import com.iloomo.utils.DateUtil;
import com.iloomo.utils.DialogUtil;
import com.iloomo.utils.L;
import com.iloomo.utils.LCSharedPreferencesHelper;
import com.iloomo.utils.PImageLoaderUtils;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ToastUtil;
import com.iloomo.utils.UnicodeUtils;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wupeitao on 16/8/13.
 */
public class UserinfoActivity extends ActivitySupport implements ThreadCallBack {
    public final static int request = 0;
    private TextView nickname;
    private ImageView userhead_img;
    private TimePickerView birthPicker;
    private TextView sex;
    private TextView adress;
    private TextView bothday;
    private final int REFRESH = 1001;
    private final int TAILOR = 101;
    private PopupWindow popWindow;
    private RelativeLayout re_choose_open;
    private Button photo;
    private Button creame;
    private Button cancel;
    private Button man;
    private Button woman;
    private Button cancel_sex;
    private RelativeLayout re_choose_open_sex;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REFRESH:
                    Bundle bundle = (Bundle) msg.obj;
                    UserInfoData userInfoData = (UserInfoData) bundle.getSerializable("userinfo");
                    LoginUserInfoData loginUserInfoData = (LoginUserInfoData) bundle.getSerializable("logininfo");
                    StrUtil.setText(nickname, loginUserInfoData.getUser_name());
                    PImageLoaderUtils.getInstance().setImageHead(userhead_img, loginUserInfoData.getUser_avar(), context);

                    if ("0".equals(userInfoData.getSex())) {
                        StrUtil.setText(sex, "女");
                    } else {
                        StrUtil.setText(sex, "男");
                    }

                    StrUtil.setText(adress, userInfoData.getArea());
                    StrUtil.setText(bothday, userInfoData.getBirthday());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        setCtenterTitle(R.string.userinfo);
        initView();
    }


    @Override
    protected void onStart() {
        super.onStart();
        MyThreadPool.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                //数据里面有'二维码'，性别，生日，地区
                UserInfoData userInfoData = DBControlApp.getInstance(context).selectUserinfo();
                //需要的数据 有头像，和昵称
                LoginUserInfoData loginUserInfoData = DBControlApp.getInstance(context).selectLoginInfo();
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putSerializable("userinfo", userInfoData);
                bundle.putSerializable("logininfo", loginUserInfoData);
                message.obj = bundle;
                message.what = REFRESH;
                handler.sendMessage(message);
            }
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (PMyAdapter.mSelectedImage.size() > 0) {
            photoResult();
        }
    }


    private void photoResult() {
        if (PMyAdapter.mSelectedImage != null) {
            File file = new File(PMyAdapter.mSelectedImage.get(0));
            Uri uri = Uri.fromFile(file);
            cropPicture(this, uri);
            PMyAdapter.mSelectedImage.clear();
        } else {
            ToastUtil.showShort(context, "取消");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TAILOR:// 裁剪后
                cropPictureResult(requestCode, resultCode, data);
                break;
            case AddAddressActivity.request:
                if (data != null) {
                    Bundle bg = data.getBundleExtra("addressdata");
                    StringBuilder sb = new StringBuilder();
                    sb.append(bg.getString("province") + " ");
                    sb.append(bg.getString("city") + " ");
                    sb.append(bg.getString("district") + " ");
                    sb.append(bg.getString("street") + " ");
                    setArea(sb.toString(), bg);
                }

                break;
        }

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
        String path = sdDir.toString() + "/Android/data/" + replace + "/img/";
        File dbFolder = new File(path);
        // 目录不存在则自动创建目录
        if (!dbFolder.exists()) {
            dbFolder.mkdirs();
        }
        return path;
    }

    private void cropPictureResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            ToastUtil.showShort(context, "上传失败!");
            return;
        }
        Bitmap bitmap = data.getParcelableExtra("data");
        if (bitmap == null) {
            ToastUtil.showShort(context, "上传失败!");
            return;
        }
        File temp = new File(getSDPath());// 自已缓存文件夹
        if (!temp.exists()) {
            temp.mkdir();
        }
        File tempFile = new File(getSDPath()
                + Calendar.getInstance().getTimeInMillis() + ".jpg"); // 以时间秒为文件名

        // BitmapUtil.saveBitmapToFile();
        // 图像保存到文件中
        FileOutputStream foutput = null;
        try {
            foutput = new FileOutputStream(tempFile);
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 70, foutput)) {
                // UploadImage(tempFile);
                foutput.flush();
                foutput.close();
                foutput = null;
                String murl = tempFile.getAbsolutePath();
                uploading(murl);
            }

            if (bitmap != null) {
                bitmap.recycle();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            ToastUtil.showShort(context, "上传失败!");
            if (bitmap != null) {
                bitmap.recycle();
            }
        }
    }

    public void uploading(final String url) {
        Map<String, String> params = new HashMap<>();
        params.put("token", MYAppconfig.loginUserInfoData.getToken());
        new AsyncHttpPost(MYAppconfig.USERUPDATE, params, "user_avar ", new File(url), new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                L.e(e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                L.e(response);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                L.e(progress + "");
            }

            @Override
            public boolean validateReponse(Response response, int id) {
                return super.validateReponse(response, id);
            }

            @Override
            public void onBefore(Request request, int id) {
                super.onBefore(request, id);
                DialogUtil.startDialogLoading(context, false);
            }

            @Override
            public void onAfter(int id) {
                super.onAfter(id);
                DialogUtil.stopDialogLoading(context);
                PImageLoaderUtils.getInstance().setImageHead(userhead_img, url, context);
                getUserInfo();
            }
        }, context);
    }

    private void initBirthPicker() {
        birthPicker = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        Calendar calendar = Calendar.getInstance();
        birthPicker.setRange(calendar.get(Calendar.YEAR) - 99, calendar.get(Calendar.YEAR));
        birthPicker.setTime(new Date());
        birthPicker.setCyclic(false);
        birthPicker.setCancelable(true);
    }

    // 裁剪图片
    public void cropPicture(Activity activity, Uri uri) {
        Intent innerIntent = new Intent("com.android.camera.action.CROP");
        innerIntent.setDataAndType(uri, "image/*");
        innerIntent.putExtra("crop", "true");// 才能出剪辑的小方框，不然没有剪辑功能，只能选取图片
        innerIntent.putExtra("aspectX", 1); // 放大缩小比例的X
        innerIntent.putExtra("aspectY", 1);// 放大缩小比例的X 这里的比例为： 1:1
        innerIntent.putExtra("outputX", 320); // 这个是限制输出图片大小
        innerIntent.putExtra("outputY", 320);
        innerIntent.putExtra("return-data", true);
        innerIntent.putExtra("scale", true);
        startActivityForResult(innerIntent, TAILOR);
    }

    private void initView() {
        initBirthPicker();
        nickname = (TextView) findViewById(R.id.nickname);
        userhead_img = (ImageView) findViewById(R.id.userhead_img);
        sex = (TextView) findViewById(R.id.sex);
        adress = (TextView) findViewById(R.id.adress);
        bothday = (TextView) findViewById(R.id.bothday);
        getUserInfo();
        birthPicker.setOnTimeSelectListener(data -> {
            String stringByFormat = DateUtil.getStringByFormat(data, DateUtil.dateFormatYMD);

            Map<String, String> parameter = new HashMap<>();
            parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
            parameter.put("birthday", stringByFormat);
//            new AsyncHttpPost(this, MYAppconfig.USERUPDATE, parameter, MYTaskID.UPDATEUSERBOTHDAY,
//                    UpdateUserInfoModel.class, context);

            DialogUtil.startDialogLoading(context);
            OkHttpUtils
                    .post()
                    .url(MYAppconfig.USERUPDATE)
                    .params(parameter)
                    .tag(context)
                    .build()
                    .execute(new StringCallback() {

                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtil.showShort(context, e.getMessage());
                            DialogUtil.stopDialogLoading(context);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            response = UnicodeUtils.decodeUnicode(response);
                            DialogUtil.stopDialogLoading(context);
                            UpdateUserInfoModel model = JSON.parseObject(response, UpdateUserInfoModel.class);
                            UpdateUserInfoModel baseMadel = (UpdateUserInfoModel) model;
                            StrUtil.setText(bothday, stringByFormat);
                            if (baseMadel.getCode().equals("200")) {
                                ToastUtil.showShort(context, baseMadel.getData().getCode_message());
                                MyThreadPool.getInstance().submit(new Runnable() {
                                    @Override
                                    public void run() {
                                        DBControlApp.getInstance(context).updateUserInfoBothday(stringByFormat);
                                    }
                                });
                            } else {
                                ToastUtil.showShort(context, baseMadel.getData().getCode_message());
                            }
                        }
                    });


        });
    }

    public void getUserInfo() {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());

        new AsyncHttpPost(this, MYAppconfig.USERINFO, parameter, MYTaskID.USERINFO,
                UserInfoModel.class, context);
    }

    private static Boolean existHttpPath(String httpPath) {
        URL httpurl = null;
        try {
            httpurl = new URL(httpPath);
            URLConnection rulConnection = httpurl.openConnection();
            rulConnection.getInputStream();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /***
     * 点击头像查看
     *
     * @param view
     */
    public void onUserheadClick(View view) {
        if (existHttpPath(MYAppconfig.loginUserInfoData.getUser_avar())) {
            Intent intent = new Intent(this, PViewPagerPActivity.class);
            ArrayList<String> photoStrings = new ArrayList<>();
            photoStrings.add(MYAppconfig.loginUserInfoData.getUser_avar());
            intent.putStringArrayListExtra("photos", photoStrings);
            intent.putExtra("index", 0);
            startActivity(intent);
        } else {
            ToastUtil.showShort(context, "请先上传图片");
        }
    }

    /***
     * 点击更换头像
     *
     * @param view
     */
    public void onUserheadChangeClick(View view) {
        showPopup(view);
    }

    /***
     * 点击修改昵称
     *
     * @param view
     */
    public void onNickNameChangeClick(View view) {
        mIntent(context, UpdateUserName.class);
    }

    /***
     * 查看我的二维码
     *
     * @param view
     */
    public void onMeTwoCodeClick(View view) {
        mIntent(context, UserCodeInfo.class);
    }

    /****
     * 收货地址查看与更改
     *
     * @param view
     */
    public void onReseiveAdressClick(View view) {
        mIntent(context, UserAddressInfo.class);
    }

    public void onReseiveSexClick(View view) {
        showSexChoose(view);
    }

    /***
     * 修改出生年月
     */
    public void onBothdayClick(View view) {
        birthPicker.show();
    }

    /***
     * 修改地址
     *
     * @param view
     */
    public void onAdressClick(View view) {
        Intent mIntent = new Intent();
        mIntent.setClass(context, AddressAllActivity.class);
        startActivityForResult(mIntent, request);
    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        switch (resultCode) {
            case MYTaskID.USERINFO:

                MyThreadPool.getInstance().submit(new Runnable() {
                    @Override
                    public void run() {
                        UserInfoModel userInfoModel = (UserInfoModel) modelClass;
                        DBControlApp.getInstance(context).insertUserInfo(userInfoModel.getData());

                        //数据里面有'二维码'，性别，生日，地区
                        UserInfoData userInfoData = DBControlApp.getInstance(context).selectUserinfo();
                        //需要的数据 有头像，和昵称
                        LoginUserInfoData loginUserInfoData = DBControlApp.getInstance(context).selectLoginInfo();
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userinfo", userInfoData);
                        bundle.putSerializable("logininfo", loginUserInfoData);
                        message.obj = bundle;
                        message.what = REFRESH;
                        handler.sendMessage(message);
                    }
                });
                break;
        }
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
        switch (resultCode) {
            case MYTaskID.USERINFO:
                UserInfoModel userInfoModel = (UserInfoModel) modelClass;
                ToastUtil.showShort(context, userInfoModel.getData().getCode_message());
                break;
        }
    }


    private void showPopup(View parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_pop_choose_media, null);
        // 创建一个PopuWidow对象
        popWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        initPop(view);

        //popupwindow弹出时的动画		popWindow.setAnimationStyle(R.style.popupWindowAnimation);
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        popWindow.setFocusable(true);
        // 设置允许在外点击消失
        popWindow.setOutsideTouchable(false);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popWindow.setBackgroundDrawable(new LevelListDrawable());

        //软键盘不会挡着popupwindow
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置菜单显示的位置
        popWindow.showAtLocation(parent, Gravity.BOTTOM, 4, 0);

        //监听菜单的关闭事件
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        //监听触屏事件
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {

                return false;
            }
        });
    }

    private PopupWindow PopupWindowsex;

    private void showSexChoose(View parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_pop_choose_sex, null);
        // 创建一个PopuWidow对象
        PopupWindowsex = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        initPopSex(view);

        //popupwindow弹出时的动画		popWindow.setAnimationStyle(R.style.popupWindowAnimation);
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        PopupWindowsex.setFocusable(true);
        // 设置允许在外点击消失
        PopupWindowsex.setOutsideTouchable(false);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        PopupWindowsex.setBackgroundDrawable(new LevelListDrawable());

        //软键盘不会挡着popupwindow
        PopupWindowsex.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置菜单显示的位置
        PopupWindowsex.showAtLocation(parent, Gravity.BOTTOM, 4, 0);

        //监听菜单的关闭事件
        PopupWindowsex.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        //监听触屏事件
        PopupWindowsex.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {

                return false;
            }
        });
    }

    private void initPop(View view) {
        re_choose_open = (RelativeLayout) view.findViewById(R.id.re_choose_open);
        photo = (Button) view.findViewById(R.id.photo);
        creame = (Button) view.findViewById(R.id.creame);
        cancel = (Button) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popWindow.isShowing()) {
                    popWindow.dismiss();
                }
            }
        });
        creame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraManager.getInst().openCamera(context);
                if (popWindow.isShowing()) {
                    popWindow.dismiss();
                }
            }
        });
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PMyPhoto.class);
                startActivity(intent);
                if (popWindow.isShowing()) {
                    popWindow.dismiss();
                }
            }
        });
        re_choose_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (popWindow.isShowing()) {
                    popWindow.dismiss();
                }
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.playTogether(

//                ObjectAnimator.ofFloat(myView, "translationX", 0, 90),
                ObjectAnimator.ofFloat(re_choose_open, "translationY", 500, 0)
        );
        set.setDuration(500).start();
    }


    private void initPopSex(View view) {
        re_choose_open_sex = (RelativeLayout) view.findViewById(R.id.re_choose_open);
        man = (Button) view.findViewById(R.id.man);
        woman = (Button) view.findViewById(R.id.woman);
        cancel_sex = (Button) view.findViewById(R.id.cancel);
        re_choose_open_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PopupWindowsex.isShowing()) {
                    PopupWindowsex.dismiss();
                }
            }
        });
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PopupWindowsex.isShowing()) {
                    PopupWindowsex.dismiss();
                }
                setSex("1");

            }
        });
        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (PopupWindowsex.isShowing()) {
                    PopupWindowsex.dismiss();
                }
                setSex("0");
            }
        });
        cancel_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PopupWindowsex.isShowing()) {
                    PopupWindowsex.dismiss();
                }
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.playTogether(

//                ObjectAnimator.ofFloat(myView, "translationX", 0, 90),
                ObjectAnimator.ofFloat(re_choose_open_sex, "translationY", 500, 0)
        );
        set.setDuration(500).start();
    }


    public void setSex(String sexStr) {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("sex", sexStr);
        DialogUtil.startDialogLoading(context);
        OkHttpUtils
                .post()
                .url(MYAppconfig.USERUPDATE)
                .params(parameter)
                .tag(context)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showShort(context, e.getMessage());
                        DialogUtil.stopDialogLoading(context);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        response = UnicodeUtils.decodeUnicode(response);
                        DialogUtil.stopDialogLoading(context);
                        UpdateUserInfoModel model = JSON.parseObject(response, UpdateUserInfoModel.class);
                        UpdateUserInfoModel baseMadel = (UpdateUserInfoModel) model;
                        if ("0".equals(sexStr)) {
                            StrUtil.setText(sex, "女");
                        } else {
                            StrUtil.setText(sex, "男");
                        }
                        if (baseMadel.getCode().equals("200")) {
                            ToastUtil.showShort(context, baseMadel.getData().getCode_message());
                            MyThreadPool.getInstance().submit(new Runnable() {
                                @Override
                                public void run() {
                                    DBControlApp.getInstance(context).updateUserInfoSex(sexStr);
                                }
                            });
                        } else {
                            ToastUtil.showShort(context, baseMadel.getData().getCode_message());
                        }
                    }
                });
    }

    public void setArea(String area, Bundle bg) {

        Map<String, String> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("area", area);
        DialogUtil.startDialogLoading(context);
        new AsyncHttpPost(new ThreadCallBack() {
            @Override
            public void onCallbackFromThread(String resultJson, Object modelClass) {

            }

            @Override
            public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
                DialogUtil.stopDialogLoading(context);
                switch (resultCode) {
                    case MYTaskID.USERINFO_AREA:
//                        UniversallyModel addressModel = (UniversallyModel) modelClass;
                        adress.setText("");
                        adress.append(bg.getString("province") + " ");
                        adress.append(bg.getString("city") + " ");
                        adress.append(bg.getString("district") + " ");
                        adress.append(bg.getString("street") + " ");

                        MyThreadPool.getInstance().submit(new Runnable() {
                            @Override
                            public void run() {
                                DBControlApp.getInstance(context).updateUserInfoArwa(area);
                            }
                        });
                        break;
                }
            }

            @Override
            public void onCallbackFromThreadError(String resultJson, Object modelClass) {

            }

            @Override
            public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
                DialogUtil.stopDialogLoading(context);
                switch (resultCode) {
                    case MYTaskID.USERINFO_AREA:
                        UniversallyModel addressModel = (UniversallyModel) modelClass;
                        ToastUtil.showShort(context, addressModel.getData().getCode_message());
                        break;
                }
            }
        }, MYAppconfig.USERUPDATE, parameter, MYTaskID.USERINFO_AREA,
                UniversallyModel.class, context);
    }


}
