package com.cityant.main.utlis;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.cityant.main.R;
import com.cityant.main.model.OnPermissionsMsg;
import com.iloomo.utils.L;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by wupeitao on 2017/1/8.
 */

public class PermissionsUtils {
    public static PermissionsUtils permissionsUtils;
    private Context context;
    private RxPermissions rxPermissions;


    public static PermissionsUtils getPermissionsUtils(Context context) {
        if (permissionsUtils == null) {
            permissionsUtils = new PermissionsUtils(context);
        }
        return permissionsUtils;
    }

    public PermissionsUtils(Context context) {
        this.context = context;
        rxPermissions = new RxPermissions((Activity) context);
        rxPermissions.setLogging(true);
    }

    public void getPermissions() {
        getWRITE_EXTERNAL_STORAGE();
    }

    public void getWRITE_EXTERNAL_STORAGE() {
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            L.e(""+aBoolean);
//                            getLocaltion();
                            sendMsg();
                        } else {
                            showPermissionsInfo("请允许\"城市蚂蚁\"访问您设备上的照片、媒体内容和文件，这对APP的运行有很大的影响");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                }, new Action0() {
                    @Override
                    public void call() {

                    }
                });
    }


    public void getLocaltion(){
        rxPermissions.request(Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {


                        } else {
                            showPermissionsInfo("请允许\"城市蚂蚁\"访问您设备上的照片、媒体内容和文件，这对APP的运行有很大的影响");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                }, new Action0() {
                    @Override
                    public void call() {

                    }
                });
    }

    private void sendMsg() {
        if (onPermissionsMsg != null)
            onPermissionsMsg.onWRITE_EXTERNAL_STORAGE(true);
    }


    public void showPermissionsInfo(String info) {
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(context, R.style.p_dialog);

//        normalDialog.setIcon(R.drawable.ic_launcher);
        normalDialog.setTitle("权限申请提示");
        normalDialog.setMessage(info);
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getWRITE_EXTERNAL_STORAGE();
                    }
                });
        // 显示
        normalDialog.show();
    }

    public OnPermissionsMsg onPermissionsMsg;

    public void setOnPermissionsMsg(OnPermissionsMsg onPermissionsMsg) {
        this.onPermissionsMsg = onPermissionsMsg;
    }
}



