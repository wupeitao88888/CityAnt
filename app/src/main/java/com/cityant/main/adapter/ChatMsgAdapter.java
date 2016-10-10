package com.cityant.main.adapter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hyphenate.easeui.bean.ChatMsgEntity;

import com.hyphenate.easeui.global.MYAppconfig;
import com.cityant.main.global.MYTaskID;
import com.iloomo.global.MApplication;
import com.iloomo.net.AsyncHttpGet;
import com.iloomo.threadpool.MyThreadPool;
import com.iloomo.utils.L;
import com.iloomo.utils.StrUtil;
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

    public static boolean isPlaying = false;
    public int selectPlay = 0;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    ChatMsgEntity chatMsg = coll.get((int) (msg.obj));
                    chatMsg.setStatus(2);
                    notifyDataSetChanged();
                    break;
            }
        }
    };

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
//        int type = entity.getType();
//
////        if (convertView == null) {
//            switch (type) {
//                case VALUE_TIME_TIP:
//                    convertView = mInflater.inflate(
//                            R.layout.chat_item_msg_time, null);
//                    break;
//                case RECEIVED_LOCATION:
//                    convertView = mInflater.inflate(
//                            R.layout.ease_row_received_location, null);
//                    break;
//                case RECEIVED_MESSAGE:
//                    convertView = mInflater.inflate(
//                            R.layout.ease_row_received_message, null);
//                    break;
//                case RECEIVED_PICTURE:
//                    convertView = mInflater.inflate(
//                            R.layout.ease_row_received_picture, null);
//                    break;
//                case RECEIVED_VIDEO_CALL:
//                    convertView = mInflater.inflate(
//                            R.layout.ease_row_received_video_call, null);
//                    break;
//                case RECEIVED_VOICE:
//                    convertView = mInflater.inflate(
//                            R.layout.ease_row_received_voice, null);
//                    break;
//                case SEND_LOCATION:
//                    convertView = mInflater.inflate(
//                            R.layout.ease_row_sent_location, null);
//                    break;
//                case SEND_MESSAGE:
//                    convertView = mInflater.inflate(
//                            R.layout.ease_row_sent_message, null);
//                    break;
//                case SEND_PICTURE:
//                    convertView = mInflater.inflate(
//                            R.layout.ease_row_sent_picture, null);
//                    break;
//                case SEND_VIDEO_CALL:
//                    convertView = mInflater.inflate(
//                            R.layout.ease_row_sent_video_call, null);
//                    break;
//                case SEND_VOICE:
//                    convertView = mInflater.inflate(
//                            R.layout.ease_row_sent_voice, null);
//                    break;
//                case RECEIVED_BEAN:
//                    convertView = mInflater.inflate(
//                            R.layout.ease_row_received_bean, null);
//                    break;
//                case SEND_BEAN:
//                    convertView = mInflater.inflate(
//                            R.layout.ease_row_sent_bean, null);
//                    break;
//                case RECEIVED_TIP:
//                    convertView = mInflater.inflate(
//                            R.layout.ease_row_received_tip, null);
//                    break;
//                case SEND_TIP:
//                    convertView = mInflater.inflate(
//                            R.layout.ease_row_sent_tip, null);
//                    break;
//                case RECEIVED_GIFT:
//                    convertView = mInflater.inflate(
//                            R.layout.ease_row_received_gift, null);
//                    break;
//                case SEND_GIFT:
//                    convertView = mInflater.inflate(
//                            R.layout.ease_row_sent_gift, null);
//                    break;
//                case RECEIVED_DOUBLE_ROB:
//                    convertView = mInflater.inflate(
//                            R.layout.ease_row_received_double_rob, null);
//                    break;
//                case SEND_DOUBLE_ROB:
//                    convertView = mInflater.inflate(
//                            R.layout.ease_row_sent_double_rob, null);
//                    break;
//                default:
//                    break;
//            }
//
////        }
//        findViewById(type, convertView, entity, position);
        return convertView;
    }


    private void findViewById(int type, View convertView, ChatMsgEntity entity, int position) {
//        switch (type) {
//            case VALUE_TIME_TIP:
//                TextView tv_sendtime = ViewHolder.get(convertView, R.id.tv_sendtime);
//                StrUtil.setText(tv_sendtime, entity.getTime());
//                break;
//            case RECEIVED_LOCATION:
//                ImageView iv_userhead = ViewHolder.get(convertView, R.id.iv_userhead);
//                TextView tv_location = ViewHolder.get(convertView, R.id.tv_location);
//                StrUtil.setText(tv_location, entity.getStreet());
//                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), iv_userhead, context);
//                TextView location_tv_userid = ViewHolder.get(convertView, R.id.tv_userid);
//                if (blean) {
//                    location_tv_userid.setVisibility(View.VISIBLE);
//                } else {
//                    location_tv_userid.setVisibility(View.GONE);
//                }
//                StrUtil.setText(location_tv_userid, entity.getUser_name());
//                break;
//            case RECEIVED_MESSAGE:
//                SpannableString spannableString = FaceConversionUtil
//                        .getInstace().getExpressionString(context,
//                                entity.getMessage());
//                ImageView iv_userheadmsg = ViewHolder.get(convertView, R.id.iv_userhead);
//                TextView tv_chatcontent = ViewHolder.get(convertView, R.id.tv_chatcontent);
//
//                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), iv_userheadmsg, context);
//                StrUtil.setText(tv_chatcontent, spannableString);
//                TextView message_tv_userid = ViewHolder.get(convertView, R.id.tv_userid);
//                if (blean) {
//                    message_tv_userid.setVisibility(View.VISIBLE);
//                } else {
//                    message_tv_userid.setVisibility(View.GONE);
//                }
//                StrUtil.setText(message_tv_userid, entity.getUser_name());
//                break;
//            case RECEIVED_PICTURE:
//                ImageView iv_userheadp = ViewHolder.get(convertView, R.id.iv_userhead);
//                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), iv_userheadp, context);
//
//                ImageView image = ViewHolder.get(convertView, R.id.image);
//                ProgressBar progress_bar = ViewHolder.get(convertView, R.id.progress_bar);
//                TextView percentage = ViewHolder.get(convertView, R.id.percentage);
////                ImageLoader.getInstance().displayImage(entity.getImgurl(), new ImageViewAware(image), null, new ImageLoadingListener() {
////                    @Override
////                    public void onLoadingStarted(String s, View view) {
////                        progress_bar.setVisibility(View.VISIBLE);
////                        percentage.setVisibility(View.VISIBLE);
////                    }
////
////                    @Override
////                    public void onLoadingFailed(String s, View view, FailReason failReason) {
////
////                    }
////
////                    @Override
////                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
////
////                    }
////
////                    @Override
////                    public void onLoadingCancelled(String s, View view) {
////                        progress_bar.setVisibility(View.GONE);
////                        percentage.setVisibility(View.GONE);
////                    }
////                }, new ImageLoadingProgressListener() {
////
////                    @Override
////                    public void onProgressUpdate(String s, View view, int i, int i1) {
////                        StrUtil.setText(percentage, i + "/" + i1);
////                    }
////                });
//                PImageLoaderUtils.displaySmallpix(entity.getImgurl(), image, context);
//                TextView picture_tv_userid = ViewHolder.get(convertView, R.id.tv_userid);
//                if (blean) {
//                    picture_tv_userid.setVisibility(View.VISIBLE);
//                } else {
//                    picture_tv_userid.setVisibility(View.GONE);
//                }
//                StrUtil.setText(picture_tv_userid, entity.getUser_name());
//                break;
//            case RECEIVED_VIDEO_CALL:
//                ImageView video_userheadp = ViewHolder.get(convertView, R.id.iv_userhead);
//                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), video_userheadp, context);
//                TextView chat_video_call_time = ViewHolder.get(convertView, R.id.tv_chatcontent);
//                StrUtil.setText(chat_video_call_time, entity.getDuration());
//                TextView video_call_tv_userid = ViewHolder.get(convertView, R.id.tv_userid);
//                if (blean) {
//                    video_call_tv_userid.setVisibility(View.VISIBLE);
//                } else {
//                    video_call_tv_userid.setVisibility(View.GONE);
//                }
//                StrUtil.setText(video_call_tv_userid, entity.getUser_name());
//                break;
//            case RECEIVED_VOICE:
//                ImageView voice_userhead = ViewHolder.get(convertView, R.id.iv_userhead);
//                RelativeLayout received_voice_bubble = ViewHolder.get(convertView, R.id.bubble);
//
//                ImageView iv_voice = ViewHolder.get(convertView, R.id.iv_voice);
//                TextView tv_length = ViewHolder.get(convertView, R.id.tv_length);
//                ImageView iv_unread_voice = ViewHolder.get(convertView, R.id.iv_unread_voice);
//                ProgressBar voice_progress_bar = ViewHolder.get(convertView, R.id.progress_bar);
//                TextView tv_userid = ViewHolder.get(convertView, R.id.tv_userid);
//                if (blean) {
//                    tv_userid.setVisibility(View.VISIBLE);
//                } else {
//                    tv_userid.setVisibility(View.GONE);
//                }
//                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), voice_userhead, context);
//                StrUtil.setText(tv_userid, entity.getUser_name());
//
//                received_voice_bubble.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (isPlaying) {
//                            stopPlayVoice(iv_voice, true, position);
//                            if (selectPlay == position)
//                                return;
//                            selectPlay = position;
//                            upLoadVoiceFiles(iv_voice, entity.getVoiceurl(), false, voice_progress_bar, position);
//                        } else {
//                            selectPlay = position;
//                            upLoadVoiceFiles(iv_voice, entity.getVoiceurl(), false, voice_progress_bar, position);
//                        }
//                    }
//                });
//                StrUtil.setText(tv_length, entity.getVoicelenth() + "’’");
//                if (entity.getVoicestatus() == 0) {
//                    iv_unread_voice.setVisibility(View.VISIBLE);
//                } else {
//                    iv_unread_voice.setVisibility(View.GONE);
//                }
//                if (!entity.isVoiceplay()) {
//                    iv_voice.setImageResource(R.drawable.ease_chatfrom_voice_playing);
//                }
//                break;
//            case SEND_LOCATION:
//                ImageView send_location_iv_userhead = ViewHolder.get(convertView, R.id.iv_userhead);
//                LinearLayout send_location_bubble = ViewHolder.get(convertView, R.id.bubble);
//                ImageView send_location_status = ViewHolder.get(convertView, R.id.msg_status);
//                TextView send_location_tv_ack = ViewHolder.get(convertView, R.id.tv_ack);
//                TextView send_location_tv_delivered = ViewHolder.get(convertView, R.id.tv_delivered);
//                ProgressBar send_location_progress_bar = ViewHolder.get(convertView, R.id.progress_bar);
//                TextView send_location_tv_location = ViewHolder.get(convertView, R.id.tv_location);
//                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), send_location_iv_userhead, context);
//                setMessageStatus(send_location_progress_bar, send_location_tv_delivered, send_location_tv_ack, send_location_status, entity.getStatus());
//                send_location_bubble.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });
//                StrUtil.setText(send_location_tv_location, entity.getStreet());
//                break;
//            case SEND_MESSAGE:
//                ImageView send_message_iv_userhead = ViewHolder.get(convertView, R.id.iv_userhead);
//                TextView send_message_tv_chatcontent = ViewHolder.get(convertView, R.id.tv_chatcontent);
//                ImageView send_message_status = ViewHolder.get(convertView, R.id.msg_status);
//                TextView send_message_tv_ack = ViewHolder.get(convertView, R.id.tv_ack);
//                TextView send_message_tv_delivered = ViewHolder.get(convertView, R.id.tv_delivered);
//                ProgressBar send_message_progress_bar = ViewHolder.get(convertView, R.id.progress_bar);
//
//                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), send_message_iv_userhead, context);
//
//                SpannableString sendspannableString = FaceConversionUtil
//                        .getInstace().getExpressionString(context,
//                                entity.getMessage());
//                StrUtil.setText(send_message_tv_chatcontent, sendspannableString);
//                setMessageStatus(send_message_progress_bar, send_message_tv_delivered, send_message_tv_ack, send_message_status, entity.getStatus());
//                break;
//            case SEND_PICTURE:
//                ImageView send_picture_iv_userhead = ViewHolder.get(convertView, R.id.iv_userhead);
//                ImageView send_picture_image = ViewHolder.get(convertView, R.id.image);
//
//                RelativeLayout send_picture_bubble = ViewHolder.get(convertView, R.id.bubble);
//
//                ImageView send_picture_status = ViewHolder.get(convertView, R.id.msg_status);
//                TextView send_picture_tv_ack = ViewHolder.get(convertView, R.id.tv_ack);
//                TextView send_picture_tv_delivered = ViewHolder.get(convertView, R.id.tv_delivered);
//                ProgressBar send_picture_status_progress_bar = ViewHolder.get(convertView, R.id.progress_bar);
//
//                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), send_picture_iv_userhead, context);
//                setMessageStatus(send_picture_status_progress_bar, send_picture_tv_delivered, send_picture_tv_ack, send_picture_status, entity.getStatus());
//
////                ImageLoader.getInstance().displayImage(entity.getImgurl(), new ImageViewAware(send_picture_image), null, new ImageLoadingListener() {
////                    @Override
////                    public void onLoadingStarted(String s, View view) {
////                        send_picture_progress_bar.setVisibility(View.VISIBLE);
////                        send_picture_percentage.setVisibility(View.VISIBLE);
////                    }
////
////                    @Override
////                    public void onLoadingFailed(String s, View view, FailReason failReason) {
////
////                    }
////
////                    @Override
////                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
////
////                    }
////
////                    @Override
////                    public void onLoadingCancelled(String s, View view) {
////                        send_picture_progress_bar.setVisibility(View.GONE);
////                        send_picture_percentage.setVisibility(View.GONE);
////                    }
////                }, new ImageLoadingProgressListener() {
////
////                    @Override
////                    public void onProgressUpdate(String s, View view, int i, int i1) {
////                        StrUtil.setText(send_picture_percentage, i + "/" + i1);
////                    }
////                });
//                PImageLoaderUtils.displaySmallpix(entity.getImgurl(), send_picture_image, context);
//                send_picture_bubble.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });
//                break;
//            case SEND_VIDEO_CALL:
//                ImageView send_video_iv_userhead = ViewHolder.get(convertView, R.id.iv_userhead);
//                TextView send_video_tv_chatcontent = ViewHolder.get(convertView, R.id.tv_chatcontent);
//                ImageView send_video_msg_status = ViewHolder.get(convertView, R.id.msg_status);
//                TextView send_video_tv_ack = ViewHolder.get(convertView, R.id.tv_ack);
//
//                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), send_video_iv_userhead, context);
//                setVideoMessageStatus(send_video_tv_ack, send_video_msg_status, entity.getStatus(), send_video_tv_chatcontent, entity);
//                break;
//            case SEND_VOICE:
//                ImageView send_voice_iv_userhead = ViewHolder.get(convertView, R.id.iv_userhead);
//                RelativeLayout send_voice_bubble = ViewHolder.get(convertView, R.id.bubble);
//                ImageView send_voice_iv_voice = ViewHolder.get(convertView, R.id.iv_voice);
//
//                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), send_voice_iv_userhead, context);
//                ImageView send_voice_status = ViewHolder.get(convertView, R.id.msg_status);
//                TextView send_voice_tv_ack = ViewHolder.get(convertView, R.id.tv_ack);
//                TextView send_voice_tv_delivered = ViewHolder.get(convertView, R.id.tv_delivered);
//                ProgressBar send_voice_progress_bar = ViewHolder.get(convertView, R.id.progress_bar);
//
//                setMessageStatus(send_voice_progress_bar, send_voice_tv_delivered, send_voice_tv_ack, send_voice_status, entity.getStatus());
//                send_voice_bubble.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (isPlaying) {
//                            stopPlayVoice(send_voice_iv_voice, true, position);
//                            if (selectPlay == position)
//                                return;
//                            selectPlay = position;
//                            upLoadVoiceFiles(send_voice_iv_voice, entity.getVoiceurl(), true, send_voice_progress_bar, position);
//                        } else {
//                            selectPlay = position;
//                            upLoadVoiceFiles(send_voice_iv_voice, entity.getVoiceurl(), true, send_voice_progress_bar, position);
//                        }
//                    }
//                });
//                if (!entity.isVoiceplay()) {
//                    send_voice_iv_voice.setImageResource(R.drawable.ease_chatto_voice_playing);
//                }
//                break;
//            case RECEIVED_BEAN:
//                ImageView received_bean_iv_userhead = ViewHolder.get(convertView, R.id.iv_userhead);
//                TextView received_bean_userid = ViewHolder.get(convertView, R.id.tv_userid);
//                LinearLayout received_bean_bubble = ViewHolder.get(convertView, R.id.bubble);
//                TextView received_bean_tv_content = ViewHolder.get(convertView, R.id.tv_content);
//
//                if (blean) {
//                    received_bean_userid.setVisibility(View.VISIBLE);
//                } else {
//                    received_bean_userid.setVisibility(View.GONE);
//                }
//                received_bean_bubble.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });
//                StrUtil.setText(received_bean_tv_content, entity.getMessage());
//                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), received_bean_iv_userhead, context);
//                break;
//            case SEND_BEAN:
//                ImageView send_bean_iv_userhead = ViewHolder.get(convertView, R.id.iv_userhead);
//                LinearLayout send_bean_bubble = ViewHolder.get(convertView, R.id.bubble);
//                TextView received_bean_tv_file_name = ViewHolder.get(convertView, R.id.tv_file_name);
//
//                ImageView send_bean_status = ViewHolder.get(convertView, R.id.msg_status);
//                TextView send_bean_tv_ack = ViewHolder.get(convertView, R.id.tv_ack);
//                TextView send_bean_tv_delivered = ViewHolder.get(convertView, R.id.tv_delivered);
//                ProgressBar send_bean_progress_bar = ViewHolder.get(convertView, R.id.progress_bar);
//                send_bean_bubble.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });
//                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), send_bean_iv_userhead, context);
//                StrUtil.setText(received_bean_tv_file_name, entity.getMessage());
//                setMessageStatus(send_bean_progress_bar, send_bean_tv_delivered, send_bean_tv_ack, send_bean_status, entity.getStatus());
//                break;
//            case RECEIVED_TIP:
//                ImageView received_tip_iv_userhead = ViewHolder.get(convertView, R.id.iv_userhead);
//                TextView received_tip_userid = ViewHolder.get(convertView, R.id.tv_userid);
//                LinearLayout received_tip_bubble = ViewHolder.get(convertView, R.id.bubble);
//                TextView received_tip_tv_content = ViewHolder.get(convertView, R.id.tv_content);
//
//                if (blean) {
//                    received_tip_userid.setVisibility(View.VISIBLE);
//                } else {
//                    received_tip_userid.setVisibility(View.GONE);
//                }
//                received_tip_bubble.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });
//                StrUtil.setText(received_tip_tv_content, entity.getMessage());
//                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), received_tip_iv_userhead, context);
//                break;
//            case SEND_TIP:
//                ImageView send_tip_iv_userhead = ViewHolder.get(convertView, R.id.iv_userhead);
//                LinearLayout send_tip_bubble = ViewHolder.get(convertView, R.id.bubble);
//                TextView send_tip_tv_file_name = ViewHolder.get(convertView, R.id.tv_file_name);
//
//                ImageView send_tip_status = ViewHolder.get(convertView, R.id.msg_status);
//                TextView send_tip_tv_ack = ViewHolder.get(convertView, R.id.tv_ack);
//                TextView send_tip_tv_delivered = ViewHolder.get(convertView, R.id.tv_delivered);
//                ProgressBar send_tip_progress_bar = ViewHolder.get(convertView, R.id.progress_bar);
//                send_tip_bubble.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });
//                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), send_tip_iv_userhead, context);
//                StrUtil.setText(send_tip_tv_file_name, entity.getMessage());
//                setMessageStatus(send_tip_progress_bar, send_tip_tv_delivered, send_tip_tv_ack, send_tip_status, entity.getStatus());
//                break;
//            case RECEIVED_GIFT:
//                ImageView received_gift_iv_userhead = ViewHolder.get(convertView, R.id.iv_userhead);
//                TextView received_gift_userid = ViewHolder.get(convertView, R.id.tv_userid);
//                LinearLayout received_gift_bubble = ViewHolder.get(convertView, R.id.bubble);
//                TextView received_gift_tv_content = ViewHolder.get(convertView, R.id.tv_content);
//
//                if (blean) {
//                    received_gift_userid.setVisibility(View.VISIBLE);
//                } else {
//                    received_gift_userid.setVisibility(View.GONE);
//                }
//                received_gift_bubble.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });
//                StrUtil.setText(received_gift_tv_content, entity.getMessage());
//                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), received_gift_iv_userhead, context);
//                break;
//            case SEND_GIFT:
//                ImageView send_gift_iv_userhead = ViewHolder.get(convertView, R.id.iv_userhead);
//                LinearLayout send_gift_bubble = ViewHolder.get(convertView, R.id.bubble);
//                TextView send_gift_tv_file_name = ViewHolder.get(convertView, R.id.tv_file_name);
//
//                ImageView send_gift_status = ViewHolder.get(convertView, R.id.msg_status);
//                TextView send_gift_tv_ack = ViewHolder.get(convertView, R.id.tv_ack);
//                TextView send_gift_tv_delivered = ViewHolder.get(convertView, R.id.tv_delivered);
//                ProgressBar send_gift_progress_bar = ViewHolder.get(convertView, R.id.progress_bar);
//                send_gift_bubble.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });
//                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), send_gift_iv_userhead, context);
//                StrUtil.setText(send_gift_tv_file_name, entity.getMessage());
//                setMessageStatus(send_gift_progress_bar, send_gift_tv_delivered, send_gift_tv_ack, send_gift_status, entity.getStatus());
//                break;
//            case RECEIVED_DOUBLE_ROB:
//                ImageView received_double_rob_iv_userhead = ViewHolder.get(convertView, R.id.iv_userhead);
//                TextView received_double_rob_userid = ViewHolder.get(convertView, R.id.tv_userid);
//                LinearLayout received_double_rob_bubble = ViewHolder.get(convertView, R.id.bubble);
//                TextView received_double_rob_tv_content = ViewHolder.get(convertView, R.id.tv_content);
//
//                if (blean) {
//                    received_double_rob_userid.setVisibility(View.VISIBLE);
//                } else {
//                    received_double_rob_userid.setVisibility(View.GONE);
//                }
//                received_double_rob_bubble.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });
//                StrUtil.setText(received_double_rob_tv_content, entity.getMessage());
//                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), received_double_rob_iv_userhead, context);
//                break;
//            case SEND_DOUBLE_ROB:
//                ImageView send_double_rob_iv_userhead = ViewHolder.get(convertView, R.id.iv_userhead);
//                LinearLayout send_double_rob_bubble = ViewHolder.get(convertView, R.id.bubble);
//                TextView send_double_rob_tv_file_name = ViewHolder.get(convertView, R.id.tv_file_name);
//
//                ImageView send_double_rob_status = ViewHolder.get(convertView, R.id.msg_status);
//                TextView send_double_rob_tv_ack = ViewHolder.get(convertView, R.id.tv_ack);
//                TextView send_double_rob_tv_delivered = ViewHolder.get(convertView, R.id.tv_delivered);
//                ProgressBar send_double_rob_progress_bar = ViewHolder.get(convertView, R.id.progress_bar);
//                send_double_rob_bubble.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });
//                PImageLoaderUtils.displayuserHand(entity.getUser_avar(), send_double_rob_iv_userhead, context);
//                StrUtil.setText(send_double_rob_tv_file_name, entity.getMessage());
//                setMessageStatus(send_double_rob_progress_bar, send_double_rob_tv_delivered, send_double_rob_tv_ack, send_double_rob_status, entity.getStatus());
//                break;
//            default:
//                break;
//        }
    }

    public void setMessageStatus(ProgressBar progress_bar, TextView tv_delivered, TextView tv_ack, ImageView msg_status, int status) {
        switch (status) {
            case -1://未发送
                break;
            case 0://发送失败
                msg_status.setVisibility(View.VISIBLE);
                progress_bar.setVisibility(View.GONE);
                break;
            case 1://发送中
                progress_bar.setVisibility(View.VISIBLE);
                break;
            case 2://已送达
                tv_delivered.setVisibility(View.VISIBLE);
                progress_bar.setVisibility(View.GONE);
                tv_ack.setVisibility(View.GONE);
                break;
            case 3://已发送
                progress_bar.setVisibility(View.GONE);
                tv_ack.setVisibility(View.VISIBLE);
                break;
            default://默认都不显示
                msg_status.setVisibility(View.GONE);
                tv_delivered.setVisibility(View.GONE);
                progress_bar.setVisibility(View.GONE);
                tv_ack.setVisibility(View.GONE);
                break;
        }
    }

    public void setVideoMessageStatus(TextView tv_ack, ImageView msg_status, int status, TextView send_video_tv_chatcontent, ChatMsgEntity entity) {
        switch (status) {
            case -1://未发送
                break;
            case 0:
                msg_status.setVisibility(View.VISIBLE);
                StrUtil.setText(send_video_tv_chatcontent, "");
                break;
            case 1:
                break;
            case 2:
                break;
            case 3://已发送
                tv_ack.setVisibility(View.VISIBLE);
                StrUtil.setText(send_video_tv_chatcontent, entity.getDuration());
                break;
            default://默认都不显示
                msg_status.setVisibility(View.GONE);
                tv_ack.setVisibility(View.GONE);
                break;
        }
    }


    MediaPlayer mediaPlayer = null;
    private AnimationDrawable voiceAnimation = null;

    public void playVoice(String filePath, ImageView voiceIconView, boolean isTo, int position) {
        if (!(new File(filePath).exists())) {
            return;
        }

        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        mediaPlayer = new MediaPlayer();
        if (MYAppconfig.isSpeakerOpened) {
            audioManager.setMode(AudioManager.MODE_NORMAL);
            audioManager.setSpeakerphoneOn(true);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
        } else {
            audioManager.setSpeakerphoneOn(false);// 关闭扬声器
            // 把声音设定成Earpiece（听筒）出来，设定为正在通话中
            audioManager.setMode(AudioManager.MODE_IN_CALL);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);
        }
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                @Override
                public void onCompletion(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    mediaPlayer.release();
                    mediaPlayer = null;
                    stopPlayVoice(voiceIconView, isTo, position);
                }

            });
            isPlaying = true;
            mediaPlayer.start();
//            if (isTo) {
//                voiceIconView.setImageResource(R.drawable.voice_to_icon);
//            } else {
//                voiceIconView.setImageResource(R.drawable.voice_from_icon);
//            }

            voiceAnimation = (AnimationDrawable) voiceIconView.getDrawable();
            voiceAnimation.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void upLoadVoiceFiles(ImageView voiceIconView, String httpurl, boolean isTo, ProgressBar voice_progress_bar, final int position) {
        ChatMsgEntity chatMsgEntity = coll.get(position);
        chatMsgEntity.setVoiceplay(true);
        File file = new File(MApplication.getVoicePath() + getUrlFileName(httpurl));
        if (file.exists()) {
            if (voice_progress_bar != null)
                voice_progress_bar.setVisibility(View.GONE);
            playVoice(MApplication.getVoicePath() + getUrlFileName(httpurl), voiceIconView, isTo, position);
            return;
        }
        if (position != selectPlay) {
            return;
        }
        AsyncHttpGet asyncHttpGet = new AsyncHttpGet(httpurl, new FileCallBack(MApplication.getVoicePath(), getUrlFileName(httpurl)) {
            @Override
            public void onBefore(Request request, int id) {
                if (voice_progress_bar != null)
                    voice_progress_bar.setVisibility(View.VISIBLE);
            }

            @Override
            public void inProgress(float progress, long total, int id) {

            }

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(File response, int id) {
                if (voice_progress_bar != null)
                    voice_progress_bar.setVisibility(View.GONE);
                if (position == selectPlay) {
                    playVoice(response.getAbsolutePath(), voiceIconView, isTo, position);
                }
            }
        }, context, MYTaskID.UPLOADINGVOICEFILE);
    }

    public void stopPlayVoice(ImageView voiceIconView, boolean isTo, int position) {
        voiceAnimation.stop();
//        if (!isTo) {
//            voiceIconView.setImageResource(R.drawable.ease_chatfrom_voice_playing);
//        } else {
//            voiceIconView.setImageResource(R.drawable.ease_chatto_voice_playing);
//        }
        // stop play voice
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        isPlaying = false;
        ChatMsgEntity chatMsgEntity = coll.get(selectPlay);
        chatMsgEntity.setVoiceplay(false);

        notifyDataSetChanged();
    }


    public String getUrlFileName(String url) {
        String filename = null;
        // 从路径中获取
        if (filename == null || "".equals(filename)) {
            filename = url.substring(url.lastIndexOf("/") + 1, url.length());
        }
        return filename;
    }


    public void stopThread() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        } catch (Exception e) {

        }
        isPlaying = false;
        mediaPlayer = null;
    }

    public void setStatus(ChatMsgEntity chatMsgEntity, int status) {

        MyThreadPool.getInstance().submit(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < coll.size(); i++) {
                    L.e("++++++++++++++++++++++++++++++++++++"+chatMsgEntity.getMessageid()+"/"+coll.get(i).getMessageid());
                    if (chatMsgEntity.getMessageid().equals(coll.get(i).getMessageid())) {

                        Message message = new Message();
                        message.obj = i;
                        message.what = 100;
                        handler.sendMessage(message);
                        break;
                    }
                }

            }
        });
    }
}

