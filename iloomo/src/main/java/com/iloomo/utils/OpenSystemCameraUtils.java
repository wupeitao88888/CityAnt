package com.iloomo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.iloomo.bean.BaseModel;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.DefaultThreadPool;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.paysdk.R;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wupeitao on 16/8/1.
 */
public class OpenSystemCameraUtils {


    private Activity context;
    private File filec;
    private final int CAMERA = 103;
    private final int PHOTO = 100;
    private final int TAILOR = 101;
    private final int UpLoad = 102;


    private static OpenSystemCameraUtils openSystemCameraUtils = null;

    public OpenSystemCameraUtils(Activity context) {
        this.context = context;
    }

    public OpenSystemCameraUtils getInstance(Activity context) {
        if (openSystemCameraUtils == null)
            openSystemCameraUtils = new OpenSystemCameraUtils(context);
        return openSystemCameraUtils;
    }


    /**
     * 拍照
     */
    private void openSystemCamera() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            String filename = Environment.getExternalStorageDirectory()
                    + "/" + context.getResources().getString(R.string.app_name) + "/" + System.currentTimeMillis() + "c.jpg";
            String path = Environment.getExternalStorageDirectory() + "/" + context.getResources().getString(R.string.app_name) + "/";
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            filec = new File(filename);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(filec));
            try {
                List<ResolveInfo> infos = context.getPackageManager()
                        .queryIntentActivities(intent,
                                PackageManager.MATCH_DEFAULT_ONLY);

                if (infos == null || infos.size() == 0) {
                    ToastUtil.showShort(context, "没有找到支持的应用");
                } else {
                    context.startActivityForResult(intent, CAMERA);
                }
            } catch (Exception e) {
                ToastUtil.showShort(context, "没有相机权限,请到设置添加相机权限");
            }
        } else {
            ToastUtil.showShort(context, "sd卡不存在");
        }
    }


    /*****
     * 一般用于上传头像
     * 在onActivityResult中实现
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void onHeadResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO:// 相册
                photoResult(requestCode, resultCode, data);
                break;
            case TAILOR:// 裁剪后
                cropPictureResult(requestCode, resultCode, data);
                break;
            case CAMERA:// 相机
                cameraResult(requestCode, resultCode, data);
                break;
        }
    }


    private void cameraResult(int requestCode, int resultCode, Intent data) {
        if (filec.exists()) {
            if (filec.length() > 100) {
                Uri uri = Uri.fromFile(filec);
                cropPicture(context, uri);
            } else {
                Toast.makeText(context, "取消", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(context, "取消", Toast.LENGTH_LONG).show();
        }
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
        File temp = new File(Environment.getExternalStorageDirectory()
                .getPath() + "/糖贝安/");// 自已缓存文件夹
        if (!temp.exists()) {
            temp.mkdir();
        }
        File tempFile = new File(temp.getAbsolutePath() + "/"
                + Calendar.getInstance().getTimeInMillis() + ".jpg"); // 以时间秒为文件名

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

    private void photoResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String stringExtra = data.getStringExtra("path");
            File file = new File(stringExtra);
            Uri uri = Uri.fromFile(file);
            cropPicture(context, uri);
        } else {
            ToastUtil.showShort(context, "取消");
        }
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
        activity.startActivityForResult(innerIntent, TAILOR);
    }

    /**
     * 方法说明：上传文件
     *
     *
     * 编写日期:	2015年5月19日
     * 编写人员:   吴培涛
     *
     * @param fileurl 文件地址
     * @param neturl  网络地址 测试地址：http://www.tangbeian.cn/api/Acc_addAvatar
     * @param parames 参数 测试参数 "uid", "1453690416671587" ”avatar“, new File(fileurl)
     */
    private void uploadFile(String neturl, final String fileurl, Map<String, Object> parames) {
        // TODO Auto-generated method stub


        AsyncHttpPost httpRequest = new AsyncHttpPost(new ThreadCallBack() {

            @Override
            public void onCallbackFromThread(String resultJson, Object modelClass) {
                BaseModel baseModel = (BaseModel) modelClass;
                if ("1".equals(baseModel.getResult())) {
                    if ("99".equals(baseModel.getResultCode())) {
                        ToastUtil.showShort(context, "上传成功");
                    } else {
                        ToastUtil.showShort(context, "上传失败");
                    }
                } else {
                    ToastUtil.showShort(context, "上传失败");
                }
            }

            @Override
            public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {

            }

            @Override
            public void onCallbackFromThreadError(String resultJson, Object modelClass) {
                ToastUtil.showShort(context, "上传失败");
            }

            @Override
            public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {

            }

        }, neturl, parames, 100, BaseModel.class);
        DefaultThreadPool.getInstance().execute(httpRequest);
    }

    public void uploading(final String fileurl) {
        Map<String, Object> parames = new HashMap<String, Object>();
        parames.put("uid", "1453690416671587");
        parames.put("avatar", new File(fileurl));
    }
}
