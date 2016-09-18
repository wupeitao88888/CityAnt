package com.cityant.main.adapter;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.bean.ChatMsgEntity;
import com.cityant.main.utlis.FaceConversionUtil;
import com.iloomo.global.MApplication;
import com.iloomo.net.AsyncHttpGet;
import com.iloomo.utils.PImageLoaderUtils;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.zhy.http.okhttp.callback.FileCallBack;

import okhttp3.Call;
import okhttp3.Request;


public class ChatMsgAdapter extends BaseAdapter {

    public final int VALUE_TIME_TIP = 0;

    public final int RECEIVED_LOCATION = 1;
    public final int RECEIVED_MESSAGE = 2;
    public final int RECEIVED_PICTURE = 3;
    public final int RECEIVED_VIDEO_CALL = 4;
    public final int RECEIVED_VOICE = 5;

    public final int SEND_LOCATION = 6;
    public final int SEND_MESSAGE = 7;
    public final int SEND_PICTURE = 8;
    public final int SEND_VIDEO_CALL = 9;
    public final int SEND_VOICE = 10;

    public final int RECEIVED_BEAN = 11;
    public final int SEND_BEAN = 12;

    public final int RECEIVED_TIP = 13;
    public final int SEND_TIP = 14;

    public final int RECEIVED_GIFT = 15;
    public final int SEND_GIFT = 16;

    public final int RECEIVED_DOUBLE_ROB = 17;
    public final int SEND_DOUBLE_ROB = 18;

    private boolean blean = false;

    private List<ChatMsgEntity> coll;
    private LayoutInflater mInflater;
    private Context context;

    @SuppressWarnings("-access")
    public ChatMsgAdapter(Context context, List<ChatMsgEntity> coll) {
        this.coll = coll;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public void IsGroup(boolean blean) {
        this.blean = blean;
    }

    public int getCount() {
        return coll.size();
    }

    public Object getItem(int position) {
        return coll.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    /**
     * 根据数据源的position返回需要显示的的layout的type
     * <p>
     * type的值必须从0开始
     */
    @Override
    public int getItemViewType(int position) {
        ChatMsgEntity entity = coll.get(position);
        int type = entity.getType();
        return type;
    }

    /**
     * 返回所有的layout的数量
     */
    @Override
    public int getViewTypeCount() {
        return 19;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        final ChatMsgEntity entity = coll.get(position);
        int type = entity.getType();

        if (convertView == null) {
            switch (type) {
                case VALUE_TIME_TIP:
                    convertView = mInflater.inflate(
                            R.layout.chat_item_msg_time, null);
                    break;
                case RECEIVED_LOCATION:
                    convertView = mInflater.inflate(
                            R.layout.ease_row_received_location, null);
                    break;
                case RECEIVED_MESSAGE:
                    convertView = mInflater.inflate(
                            R.layout.ease_row_received_message, null);
                    break;
                case RECEIVED_PICTURE:
                    convertView = mInflater.inflate(
                            R.layout.ease_row_received_picture, null);
                    break;
                case RECEIVED_VIDEO_CALL:
                    convertView = mInflater.inflate(
                            R.layout.ease_row_received_video_call, null);
                    break;
                case RECEIVED_VOICE:
                    convertView = mInflater.inflate(
                            R.layout.ease_row_received_voice, null);
                    break;
                case SEND_LOCATION:
                    convertView = mInflater.inflate(
                            R.layout.ease_row_sent_location, null);
                    break;
                case SEND_MESSAGE:
                    convertView = mInflater.inflate(
                            R.layout.ease_row_sent_message, null);
                    break;
                case SEND_PICTURE:
                    convertView = mInflater.inflate(
                            R.layout.ease_row_sent_picture, null);
                    break;
                case SEND_VIDEO_CALL:
                    convertView = mInflater.inflate(
                            R.layout.ease_row_sent_video_call, null);
                    break;
                case SEND_VOICE:
                    convertView = mInflater.inflate(
                            R.layout.ease_row_sent_voice, null);
                    break;
                case RECEIVED_BEAN:
                    convertView = mInflater.inflate(
                            R.layout.ease_row_received_bean, null);
                    break;
                case SEND_BEAN:
                    convertView = mInflater.inflate(
                            R.layout.ease_row_sent_bean, null);
                    break;
                case RECEIVED_TIP:
                    convertView = mInflater.inflate(
                            R.layout.ease_row_received_tip, null);
                    break;
                case SEND_TIP:
                    convertView = mInflater.inflate(
                            R.layout.ease_row_sent_tip, null);
                    break;
                case RECEIVED_GIFT:
                    convertView = mInflater.inflate(
                            R.layout.ease_row_received_gift, null);
                    break;
                case SEND_GIFT:
                    convertView = mInflater.inflate(
                            R.layout.ease_row_sent_gift, null);
                    break;
                case RECEIVED_DOUBLE_ROB:
                    convertView = mInflater.inflate(
                            R.layout.ease_row_received_double_rob, null);
                    break;
                case SEND_DOUBLE_ROB:
                    convertView = mInflater.inflate(
                            R.layout.ease_row_sent_double_rob, null);
                    break;
                default:
                    break;
            }

        }
        findViewById(type, convertView, entity);
        return convertView;
    }


    private void findViewById(int type, View convertView, ChatMsgEntity entity) {
        switch (type) {
            case VALUE_TIME_TIP:
                TextView tv_sendtime = ViewHolder.get(convertView, R.id.tv_sendtime);
                StrUtil.setText(tv_sendtime, entity.getTime());
                break;
            case RECEIVED_LOCATION:
                ImageView iv_userhead = ViewHolder.get(convertView, R.id.iv_userhead);
                TextView tv_location = ViewHolder.get(convertView, R.id.tv_location);
                StrUtil.setText(tv_location, entity.getStreet());
                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), iv_userhead, context);
                break;
            case RECEIVED_MESSAGE:
                SpannableString spannableString = FaceConversionUtil
                        .getInstace().getExpressionString(context,
                                entity.getMessage());
                ImageView iv_userheadmsg = ViewHolder.get(convertView, R.id.iv_userhead);
                TextView tv_chatcontent = ViewHolder.get(convertView, R.id.tv_chatcontent);

                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), iv_userheadmsg, context);
                StrUtil.setText(tv_chatcontent, spannableString);
                break;
            case RECEIVED_PICTURE:
                ImageView iv_userheadp = ViewHolder.get(convertView, R.id.iv_userhead);
                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), iv_userheadp, context);

                ImageView image = ViewHolder.get(convertView, R.id.image);
                ProgressBar progress_bar = ViewHolder.get(convertView, R.id.progress_bar);
                TextView percentage = ViewHolder.get(convertView, R.id.percentage);
                ImageLoader.getInstance().displayImage(entity.getImgurl(), new ImageViewAware(image), null, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {
                        progress_bar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {

                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {

                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {
                        progress_bar.setVisibility(View.GONE);
                    }
                }, new ImageLoadingProgressListener() {

                    @Override
                    public void onProgressUpdate(String s, View view, int i, int i1) {
                        StrUtil.setText(percentage, i + "/" + i1);
                    }
                });
                break;
            case RECEIVED_VIDEO_CALL:
                ImageView video_userheadp = ViewHolder.get(convertView, R.id.iv_userhead);
                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), video_userheadp, context);
                TextView chat_video_call_time = ViewHolder.get(convertView, R.id.tv_chatcontent);
                StrUtil.setText(chat_video_call_time, entity.getDuration());
//                if (blean) {
//                    tv_userid.setVisibility(View.VISIBLE);
//                }else{
//                    tv_userid.setVisibility(View.GONE);
//                }
                break;
            case RECEIVED_VOICE:
                ImageView voice_userhead = ViewHolder.get(convertView, R.id.iv_userhead);
                ImageView iv_voice = ViewHolder.get(convertView, R.id.iv_voice);
                TextView tv_length = ViewHolder.get(convertView, R.id.tv_length);
                ImageView iv_unread_voice = ViewHolder.get(convertView, R.id.iv_unread_voice);
                ProgressBar voice_progress_bar = ViewHolder.get(convertView, R.id.progress_bar);
                TextView tv_userid = ViewHolder.get(convertView, R.id.tv_userid);
                if (blean) {
                    tv_userid.setVisibility(View.VISIBLE);
                }else{
                    tv_userid.setVisibility(View.GONE);
                }
                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), voice_userhead, context);



                break;
            case SEND_LOCATION:
                break;
            case SEND_MESSAGE:
                break;
            case SEND_PICTURE:
                break;
            case SEND_VIDEO_CALL:
                break;
            case SEND_VOICE:
                break;
            case RECEIVED_BEAN:
                break;
            case SEND_BEAN:
                break;
            case RECEIVED_TIP:
                break;
            case SEND_TIP:
                break;
            case RECEIVED_GIFT:
                break;
            case SEND_GIFT:
                break;
            case RECEIVED_DOUBLE_ROB:
                break;
            case SEND_DOUBLE_ROB:
                break;
            default:
                break;
        }
    }
}
