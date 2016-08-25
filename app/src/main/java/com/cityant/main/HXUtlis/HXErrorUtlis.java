package com.cityant.main.HXUtlis;

import android.content.Context;

import com.cityant.main.R;
import com.hyphenate.EMError;
import com.iloomo.utils.ToastUtil;


/**
 * Created by wupeitao on 16/8/18.
 */
public class HXErrorUtlis {

    public static HXErrorUtlis hxErrorUtlis;
    private Context context;

    public HXErrorUtlis(Context context) {
        this.context = context;
    }

    public static HXErrorUtlis getHxErrorUtlis(Context context) {
        if (hxErrorUtlis == null)
            hxErrorUtlis = new HXErrorUtlis(context);
        return hxErrorUtlis;
    }

    public void showError(int error) {
        switch (error) {
            case EMError.CHATROOM_ALREADY_JOINED:
                ToastUtil.showShort(context, mString(R.string.CHATROOM_ALREADY_JOINED));
                break;
            case EMError.CHATROOM_INVALID_ID:
                ToastUtil.showShort(context, mString(R.string.CHATROOM_INVALID_ID));
                break;
            case EMError.CHATROOM_MEMBERS_FULL:
                ToastUtil.showShort(context, mString(R.string.CHATROOM_MEMBERS_FULL));
                break;
            case EMError.CHATROOM_NOT_EXIST:
                ToastUtil.showShort(context, mString(R.string.CHATROOM_NOT_EXIST));
                break;
            case EMError.CHATROOM_NOT_JOINED:
                ToastUtil.showShort(context, mString(R.string.CHATROOM_NOT_JOINED));
                break;
            case EMError.CHATROOM_PERMISSION_DENIED:
                ToastUtil.showShort(context, mString(R.string.CHATROOM_PERMISSION_DENIED));
                break;
            case EMError.EM_NO_ERROR:
                ToastUtil.showShort(context, mString(R.string.EM_NO_ERROR));
                break;
            case EMError.FILE_DOWNLOAD_FAILED:
                ToastUtil.showShort(context, mString(R.string.FILE_DOWNLOAD_FAILED));
                break;
            case EMError.FILE_INVALID:
                ToastUtil.showShort(context, mString(R.string.FILE_INVALID));
                break;
            case EMError.FILE_NOT_FOUND:
                ToastUtil.showShort(context, mString(R.string.FILE_NOT_FOUND));
                break;
            case EMError.FILE_UPLOAD_FAILED:
                ToastUtil.showShort(context, mString(R.string.FILE_UPLOAD_FAILED));
                break;
            case EMError.GENERAL_ERROR:
                ToastUtil.showShort(context, mString(R.string.GENERAL_ERROR));
                break;
            case EMError.GROUP_ALREADY_JOINED:
                ToastUtil.showShort(context, mString(R.string.GROUP_ALREADY_JOINED));
                break;
            case EMError.GROUP_INVALID_ID:
                ToastUtil.showShort(context, mString(R.string.GROUP_INVALID_ID));
                break;
            case EMError.GROUP_MEMBERS_FULL:
                ToastUtil.showShort(context, mString(R.string.GROUP_MEMBERS_FULL));
                break;
            case EMError.GROUP_NOT_EXIST:
                ToastUtil.showShort(context, mString(R.string.GROUP_NOT_EXIST));
                break;
            case EMError.GROUP_NOT_JOINED:
                ToastUtil.showShort(context, mString(R.string.GROUP_NOT_JOINED));
                break;
            case EMError.GROUP_PERMISSION_DENIED:
                ToastUtil.showShort(context, mString(R.string.GROUP_PERMISSION_DENIED));
                break;
            case EMError.INVALID_APP_KEY:
                ToastUtil.showShort(context, mString(R.string.INVALID_APP_KEY));
                break;
            case EMError.INVALID_PASSWORD:
                ToastUtil.showShort(context, mString(R.string.INVALID_PASSWORD));
                break;
            case EMError.INVALID_URL:
                ToastUtil.showShort(context, mString(R.string.INVALID_URL));
                break;
            case EMError.INVALID_USER_NAME:
                ToastUtil.showShort(context, mString(R.string.INVALID_USER_NAME));
                break;
            case EMError.MESSAGE_ENCRYPTION_ERROR:
                ToastUtil.showShort(context, mString(R.string.MESSAGE_ENCRYPTION_ERROR));
                break;
            case EMError.MESSAGE_INCLUDE_ILLEGAL_CONTENT:
                ToastUtil.showShort(context, mString(R.string.MESSAGE_INCLUDE_ILLEGAL_CONTENT));
                break;
            case EMError.MESSAGE_INVALID:
                ToastUtil.showShort(context, mString(R.string.MESSAGE_INVALID));
                break;
            case EMError.MESSAGE_SEND_TRAFFIC_LIMIT:
                ToastUtil.showShort(context, mString(R.string.MESSAGE_SEND_TRAFFIC_LIMIT));
                break;
            case EMError.NETWORK_ERROR:
                ToastUtil.showShort(context, mString(R.string.NETWORK_ERROR));
                break;
            case EMError.SERVER_BUSY:
                ToastUtil.showShort(context, mString(R.string.SERVER_BUSY));
                break;
            case EMError.SERVER_NOT_REACHABLE:
                ToastUtil.showShort(context, mString(R.string.SERVER_NOT_REACHABLE));
                break;
            case EMError.SERVER_TIMEOUT:
                ToastUtil.showShort(context, mString(R.string.SERVER_TIMEOUT));
                break;
            case EMError.SERVER_UNKNOWN_ERROR:
                ToastUtil.showShort(context, mString(R.string.SERVER_UNKNOWN_ERROR));
                break;
            case EMError.USER_ALREADY_EXIST:
                ToastUtil.showShort(context, mString(R.string.USER_ALREADY_EXIST));
                break;
            case EMError.USER_ALREADY_LOGIN:
                ToastUtil.showShort(context, mString(R.string.USER_ALREADY_LOGIN));
                break;
            case EMError.USER_AUTHENTICATION_FAILED:
                ToastUtil.showShort(context, mString(R.string.USER_AUTHENTICATION_FAILED));
                break;
            case EMError.USER_BINDDEVICETOKEN_FAILED:
                ToastUtil.showShort(context, mString(R.string.USER_BINDDEVICETOKEN_FAILED));
                break;
            case EMError.USER_ILLEGAL_ARGUMENT:
                ToastUtil.showShort(context, mString(R.string.USER_ILLEGAL_ARGUMENT));
                break;
            case EMError.USER_LOGIN_ANOTHER_DEVICE:
                ToastUtil.showShort(context, mString(R.string.USER_LOGIN_ANOTHER_DEVICE));
                break;
            case EMError.USER_NOT_FOUND:
                ToastUtil.showShort(context, mString(R.string.USER_NOT_FOUND));
                if (null != onUserNotFoundListener){
                    onUserNotFoundListener.onUserNotFound();
                }
                break;
            case EMError.USER_NOT_LOGIN:
                ToastUtil.showShort(context, mString(R.string.USER_NOT_LOGIN));
                break;
            case EMError.USER_REG_FAILED:
                ToastUtil.showShort(context, mString(R.string.USER_REG_FAILED));
                break;
            case EMError.USER_REMOVED:
                ToastUtil.showShort(context, mString(R.string.USER_REMOVED));
                break;
            case EMError.USER_UNBIND_DEVICETOKEN_FAILED:
                ToastUtil.showShort(context, mString(R.string.USER_UNBIND_DEVICETOKEN_FAILED));
                break;
            case EMError.USER_UPDATEINFO_FAILED:
                ToastUtil.showShort(context, mString(R.string.USER_UPDATEINFO_FAILED));
                break;
            default:
                ToastUtil.showShort(context, mString(R.string.USER_FAILED));
                break;
        }

    }

    private String mString(int code) {
        return context.getResources().getString(code);
    }

    public OnUserNotFoundListener onUserNotFoundListener;

    public void setOnUserNotFoundListener(OnUserNotFoundListener onUserNotFoundListener) {
        this.onUserNotFoundListener = onUserNotFoundListener;
    }

}
