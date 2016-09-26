package com.cityant.main.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cityant.main.R;
import com.cityant.main.adapter.ChatMsgAdapter;
import com.cityant.main.bean.ChatMsgEntity;
import com.cityant.main.bean.MyFrends;
import com.cityant.main.utlis.AudioRecorder;
import com.cityant.main.utlis.FaceConversionUtil;
import com.cityant.main.utlis.UploadVoice;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.iloomo.base.ActivitySupport;
import com.iloomo.threadpool.MyThreadPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

import static com.cityant.main.R.id.username;

/**
 * Created by wupeitao on 16/8/20.
 */
public class MYChatActivity extends ActivitySupport {
    public static InputMethodManager mInputMethodManager;
    public static float inSampleSize;
    private int screenHeight;
    private int screenWidth;
    private PtrClassicFrameLayout mPtrFrame;
    private ListView chatListView;
    private ChatMsgAdapter chatMsgAdapter;
    private static final int MAX_TIME = 60; // 最长录制时间，单位秒，0为无时间限制
    private static final int MIN_TIME = 1; // 最短录制时间，单位秒，0为无时间限制，建议设为1
    private static final int RECORD_NO = 0; // 不在录音
    private static final int RECORD_ING = 1; // 正在录音
    private static final int RECODE_ED = 2; // 完成录音
    private int recordState = 0; // 录音的状态
    private String voicePath = null;// 录音地址
    private String voiceName = null;// 录音文件名
    private float recodeTime = 0.0f; // 录音的时间
    private double voiceValue = 0.0; // 麦克风获取的音量值
    private Button btnRecord;// 录音按钮
    private AudioRecorder mRecorder;
    private Dialog voiceDialog;// 提示对话框
    private ImageView dialog_img, dialog_img2;
    private Toast toast;
    int j = 0;
    private int[] bitm = new int[]{R.drawable.ll9,R.drawable.ll8,R.drawable.ll7,R.drawable.ll6,R.drawable.ll5,R.drawable.ll4,R.drawable.ll3,R.drawable.ll2,R.drawable.ll1,R.drawable.ll0};
    private MyFrends myFrends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mychat);
        myFrends= (MyFrends) getIntent().getSerializableExtra("MyFrend");
        setCtenterTitle(myFrends.getUser_name());
        mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        setScreen();
        calculateEmojiSize();
        initView();
    }

    private void initView() {
        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_list_view_frame);
        chatListView = (ListView) findViewById(R.id.chatListView);
        btnRecord = (Button) findViewById(R.id.btn_record);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                updateData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

        // 录音模块
        btnRecord.setOnTouchListener(new View.OnTouchListener() {

            private float yStart, yEnd, yEndUP;// 滑动纵坐标

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 录音方法:按住录音
                        if (recordState != RECORD_ING) {
                            if(toast != null){
                                toast.cancel();
                            }
                            voiceName = System.currentTimeMillis() + "voice";
                            mRecorder = new AudioRecorder(voiceName);
                            recordState = RECORD_ING;

                            showVoiceDialog();
                            btnRecord.setText("松开结束");
                            try {
                                mRecorder.start();
                                voicePath = getAmrPath(voiceName);
                            } catch (IOException e) {
                                //没有权限
                            }
                            mythread();
                            // 记录坐标
                            yStart = event.getY();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        yEndUP = event.getY();// 移动到的位置,如果移动距离大于60dp ,取消录音发送

                        // 结束录音,并判断录音是否符合要求
                        if (recordState == RECORD_ING) {
                            recordState = RECODE_ED;
                            btnRecord.setText("按住说话");
                            if (voiceDialog.isShowing()) {
                                voiceDialog.dismiss();
                            }
                            try {
                                mRecorder.stop();
                                voiceValue = 0.0;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (recodeTime < MIN_TIME) {
                                showWarnToast();
                                recordState = RECORD_NO;
                            } else {

                                // 录音成功
                                if ((yStart - yEndUP) > 100) {
                                    // 取消的录音
                                } else {

                                    UploadVoice upVoice = new UploadVoice();
                                    try {
                                        // 上传语音文件
                                        upVoice.uploadVoice(voicePath, context);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        break;
                    case MotionEvent.ACTION_MOVE:
                        //					if((yStart - yEnd) > 40){
                        //						btnRecord.setText("按住说话");
                        //					}
                        // 设置取消录音,滑动取消录音
                        yEnd = event.getY();// 移动到的位置,如果移动距离大于100dp ,取消录音发送
                        if ((yStart - yEnd) > 100) {
                            dialog_img.setVisibility(View.GONE);
                            dialog_img2.setVisibility(View.VISIBLE);

                        } else if ((yStart - yEnd) <= 100) {
                            dialog_img.setVisibility(View.VISIBLE);
                            dialog_img2.setVisibility(View.GONE);
                        }
                    default:

                        break;
                }

                return false;
            }

        });


        List<ChatMsgEntity> list = new ArrayList<>();

        ChatMsgEntity chatMsgEntitytime = new ChatMsgEntity();
        chatMsgEntitytime.setTime("2016-9-19 16:58");
        list.add(chatMsgEntitytime);

        ChatMsgEntity chatMsgEntityfrom = new ChatMsgEntity();
        chatMsgEntityfrom.setMessage("哈哈哈");
        chatMsgEntityfrom.setType(2);
        chatMsgEntityfrom.setUser_name("唐嫣");
        chatMsgEntityfrom.setUser_avar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fupload.cbg.cn%2F2015%2F0311%2F1426053651305.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2574381543%2C3066317494%26fm%3D21%26gp%3D0.jpg");
        list.add(chatMsgEntityfrom);

        ChatMsgEntity chatMsgEntity = new ChatMsgEntity();
        chatMsgEntity.setMessage("笑个屁啊！");
        chatMsgEntity.setType(7);
        chatMsgEntity.setUser_name("陈乔恩");
        chatMsgEntity.setUser_avar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fpic1.hebei.com.cn%2F003%2F007%2F300%2F00300730022_8bacabd8.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D3896723671%2C287425269%26fm%3D11%26gp%3D0.jpg");
        chatMsgEntity.setStatus(3);
        list.add(chatMsgEntity);

        ChatMsgEntity chatMsgEntityFromLocaltion = new ChatMsgEntity();
        chatMsgEntityFromLocaltion.setStreet("玉桥街道");
        chatMsgEntityFromLocaltion.setType(1);
        chatMsgEntityFromLocaltion.setUser_name("唐嫣");
        chatMsgEntityFromLocaltion.setUser_avar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fupload.cbg.cn%2F2015%2F0311%2F1426053651305.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2574381543%2C3066317494%26fm%3D21%26gp%3D0.jpg");
        list.add(chatMsgEntityFromLocaltion);

        ChatMsgEntity chatMsgEntityToLocaltion = new ChatMsgEntity();
        chatMsgEntityToLocaltion.setStreet("玉桥街道");
        chatMsgEntityToLocaltion.setType(6);
        chatMsgEntityToLocaltion.setUser_name("陈乔恩");
        chatMsgEntityToLocaltion.setUser_avar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fpic1.hebei.com.cn%2F003%2F007%2F300%2F00300730022_8bacabd8.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D3896723671%2C287425269%26fm%3D11%26gp%3D0.jpg");
        chatMsgEntityToLocaltion.setStatus(3);
        list.add(chatMsgEntityToLocaltion);

        ChatMsgEntity chatMsgEntityFromPicture = new ChatMsgEntity();
        chatMsgEntityFromPicture.setImgurl("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201509%2F08%2F20150908043153_d3aFK.jpeg&thumburl=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D3140580295%2C3685944628%26fm%3D21%26gp%3D0.jpg");
        chatMsgEntityFromPicture.setType(3);
        chatMsgEntityFromPicture.setUser_name("唐嫣");
        chatMsgEntityFromPicture.setUser_avar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fupload.cbg.cn%2F2015%2F0311%2F1426053651305.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2574381543%2C3066317494%26fm%3D21%26gp%3D0.jpg");
        list.add(chatMsgEntityFromPicture);


        ChatMsgEntity chatMsgEntityToPicture = new ChatMsgEntity();
        chatMsgEntityToPicture.setImgurl("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fimgsrc.baidu.com%2Fbaike%2Fpic%2Fitem%2F500fd9f9d72a6059c8788ac42d34349b023bba80.jpg&thumburl=http%3A%2F%2Fimgsrc.baidu.com%2Fbaike%2Fpic%2Fitem%2F500fd9f9d72a6059c8788ac42d34349b023bba80.jpg");
        chatMsgEntityToPicture.setType(8);
        chatMsgEntityToPicture.setUser_name("陈乔恩");
        chatMsgEntityToPicture.setUser_avar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fpic1.hebei.com.cn%2F003%2F007%2F300%2F00300730022_8bacabd8.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D3896723671%2C287425269%26fm%3D11%26gp%3D0.jpg");
        chatMsgEntityToPicture.setStatus(3);
        list.add(chatMsgEntityToPicture);

        ChatMsgEntity chatMsgEntityFromVideoCall = new ChatMsgEntity();
        chatMsgEntityFromVideoCall.setDuration("通话两分钟");
        chatMsgEntityFromVideoCall.setType(4);
        chatMsgEntityFromVideoCall.setUser_name("唐嫣");
        chatMsgEntityFromVideoCall.setUser_avar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fupload.cbg.cn%2F2015%2F0311%2F1426053651305.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2574381543%2C3066317494%26fm%3D21%26gp%3D0.jpg");
        list.add(chatMsgEntityFromVideoCall);


        ChatMsgEntity chatMsgEntityToVideoCall = new ChatMsgEntity();
        chatMsgEntityToVideoCall.setDuration("通话两分钟");
        chatMsgEntityToVideoCall.setType(9);
        chatMsgEntityToVideoCall.setUser_name("陈乔恩");
        chatMsgEntityToVideoCall.setUser_avar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fpic1.hebei.com.cn%2F003%2F007%2F300%2F00300730022_8bacabd8.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D3896723671%2C287425269%26fm%3D11%26gp%3D0.jpg");
        chatMsgEntityToVideoCall.setStatus(3);
        list.add(chatMsgEntityToVideoCall);

        ChatMsgEntity chatMsgEntityFromVoice = new ChatMsgEntity();
        chatMsgEntityFromVoice.setVoiceurl("http://hao.1015600.com/upload/ring/000/970/ea4ae3e95bd52588b4f033a940adfdbf.mp3");
        chatMsgEntityFromVoice.setVoicestatus(0);
        chatMsgEntityFromVoice.setVoicelenth("35");
        chatMsgEntityFromVoice.setType(5);
        chatMsgEntityFromVoice.setUser_name("唐嫣");
        chatMsgEntityFromVoice.setUser_avar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fupload.cbg.cn%2F2015%2F0311%2F1426053651305.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2574381543%2C3066317494%26fm%3D21%26gp%3D0.jpg");
        chatMsgEntityFromVoice.setVoiceplay(false);
        list.add(chatMsgEntityFromVoice);

        ChatMsgEntity chatMsgEntityFromVoice2 = new ChatMsgEntity();
        chatMsgEntityFromVoice2.setVoiceurl("http://hao.1015600.com/upload/ring/000/970/ea4ae3e95bd52588b4f033a940adfdbf.mp3");
        chatMsgEntityFromVoice2.setVoicestatus(1);
        chatMsgEntityFromVoice2.setVoicelenth("35");
        chatMsgEntityFromVoice2.setType(5);
        chatMsgEntityFromVoice2.setUser_name("唐嫣");
        chatMsgEntityFromVoice2.setUser_avar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fupload.cbg.cn%2F2015%2F0311%2F1426053651305.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2574381543%2C3066317494%26fm%3D21%26gp%3D0.jpg");
        chatMsgEntityFromVoice2.setVoiceplay(false);
        list.add(chatMsgEntityFromVoice2);


        ChatMsgEntity chatMsgEntityToVoice = new ChatMsgEntity();
        chatMsgEntityToVoice.setVoiceurl("http://5.1015600.com/download/ring/000/028/1d6a1769fdcc97e332d6ef3e8b06d1b0.mp3");
        chatMsgEntityToVoice.setVoicestatus(0);
        chatMsgEntityToVoice.setVoicelenth("20");
        chatMsgEntityToVoice.setType(10);
        chatMsgEntityToVoice.setUser_name("陈乔恩");
        chatMsgEntityToVoice.setUser_avar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fpic1.hebei.com.cn%2F003%2F007%2F300%2F00300730022_8bacabd8.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D3896723671%2C287425269%26fm%3D11%26gp%3D0.jpg");
        chatMsgEntityToVoice.setVoiceplay(false);
        chatMsgEntityToVoice.setStatus(3);
        list.add(chatMsgEntityToVoice);

        ChatMsgEntity chatMsgEntityFromBean = new ChatMsgEntity();
        chatMsgEntityFromBean.setType(11);
        chatMsgEntityFromBean.setUser_name("唐嫣");
        chatMsgEntityFromBean.setUser_avar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fupload.cbg.cn%2F2015%2F0311%2F1426053651305.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2574381543%2C3066317494%26fm%3D21%26gp%3D0.jpg");
        chatMsgEntityFromBean.setMessage("蚂蚁豆");
        chatMsgEntityFromBean.setMessageid("123");
        list.add(chatMsgEntityFromBean);


        ChatMsgEntity chatMsgEntityToBean = new ChatMsgEntity();
        chatMsgEntityToBean.setType(12);
        chatMsgEntityToBean.setUser_name("陈乔恩");
        chatMsgEntityToBean.setUser_avar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fpic1.hebei.com.cn%2F003%2F007%2F300%2F00300730022_8bacabd8.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D3896723671%2C287425269%26fm%3D11%26gp%3D0.jpg");
        chatMsgEntityToBean.setStatus(3);
        chatMsgEntityToBean.setMessage("蚂蚁豆");
        chatMsgEntityToBean.setMessageid("123");
        list.add(chatMsgEntityToBean);

        ChatMsgEntity chatMsgEntityFromTip = new ChatMsgEntity();
        chatMsgEntityFromTip.setType(13);
        chatMsgEntityFromTip.setUser_name("唐嫣");
        chatMsgEntityFromTip.setUser_avar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fupload.cbg.cn%2F2015%2F0311%2F1426053651305.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2574381543%2C3066317494%26fm%3D21%26gp%3D0.jpg");
        chatMsgEntityFromTip.setMessage("小费");
        chatMsgEntityFromTip.setMessageid("123");
        list.add(chatMsgEntityFromTip);

        ChatMsgEntity chatMsgEntityToTip = new ChatMsgEntity();
        chatMsgEntityToTip.setType(14);
        chatMsgEntityToTip.setUser_name("陈乔恩");
        chatMsgEntityToTip.setUser_avar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fpic1.hebei.com.cn%2F003%2F007%2F300%2F00300730022_8bacabd8.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D3896723671%2C287425269%26fm%3D11%26gp%3D0.jpg");
        chatMsgEntityToTip.setStatus(3);
        chatMsgEntityToTip.setMessage("小费");
        chatMsgEntityToTip.setMessageid("123");
        list.add(chatMsgEntityToTip);

        ChatMsgEntity chatMsgEntityFromGift = new ChatMsgEntity();
        chatMsgEntityFromGift.setType(15);
        chatMsgEntityFromGift.setUser_name("唐嫣");
        chatMsgEntityFromGift.setUser_avar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fupload.cbg.cn%2F2015%2F0311%2F1426053651305.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2574381543%2C3066317494%26fm%3D21%26gp%3D0.jpg");
        chatMsgEntityFromGift.setMessage("礼物");
        chatMsgEntityFromGift.setMessageid("123");
        list.add(chatMsgEntityFromGift);

        ChatMsgEntity chatMsgEntityToGift = new ChatMsgEntity();
        chatMsgEntityToGift.setType(16);
        chatMsgEntityToGift.setUser_name("陈乔恩");
        chatMsgEntityToGift.setUser_avar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fpic1.hebei.com.cn%2F003%2F007%2F300%2F00300730022_8bacabd8.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D3896723671%2C287425269%26fm%3D11%26gp%3D0.jpg");
        chatMsgEntityToGift.setStatus(3);
        chatMsgEntityToGift.setMessage("礼物");
        chatMsgEntityToGift.setMessageid("123");
        list.add(chatMsgEntityToGift);

        ChatMsgEntity chatMsgEntityFromDoubleRob = new ChatMsgEntity();
        chatMsgEntityFromDoubleRob.setType(17);
        chatMsgEntityFromDoubleRob.setUser_name("唐嫣");
        chatMsgEntityFromDoubleRob.setUser_avar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fupload.cbg.cn%2F2015%2F0311%2F1426053651305.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2574381543%2C3066317494%26fm%3D21%26gp%3D0.jpg");
        chatMsgEntityFromDoubleRob.setMessage("双人抢");
        chatMsgEntityFromDoubleRob.setMessageid("123");
        list.add(chatMsgEntityFromDoubleRob);


        ChatMsgEntity chatMsgEntityToDoubleRob = new ChatMsgEntity();
        chatMsgEntityToDoubleRob.setType(16);
        chatMsgEntityToDoubleRob.setUser_name("陈乔恩");
        chatMsgEntityToDoubleRob.setUser_avar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fpic1.hebei.com.cn%2F003%2F007%2F300%2F00300730022_8bacabd8.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D3896723671%2C287425269%26fm%3D11%26gp%3D0.jpg");
        chatMsgEntityToDoubleRob.setStatus(3);
        chatMsgEntityToDoubleRob.setMessage("礼物");
        chatMsgEntityToDoubleRob.setMessageid("123");
        
        list.add(chatMsgEntityToGift);

        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(myFrends.getUser_name());
//获取此会话的所有消息
        List<EMMessage> messages = conversation.getAllMessages();

        chatMsgAdapter = new ChatMsgAdapter(context, list);
        chatListView.setAdapter(chatMsgAdapter);


        // the following are default settings
        mPtrFrame.setResistance(1.7f);
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(200);
        mPtrFrame.setDurationToCloseHeader(1000);
        // default is false
        mPtrFrame.setPullToRefresh(false);
        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
//        mPtrFrame.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mPtrFrame.autoRefresh();
//            }
//        }, 100);
        scrollMyListViewToBottom();
    }


    private void scrollMyListViewToBottom() {
        chatListView.post(new Runnable() {
            @Override
            public void run() {
                chatListView.setSelection(ListView.FOCUS_DOWN);//刷新到底部
            }
        });
    }

    protected void updateData() {

//        DemoRequestData.getImageList(new RequestFinishHandler<JsonData>() {
//            @Override
//            public void onRequestFinish(final JsonData data) {
//                mPtrFrame.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        mAdapter.getDataList().clear();
//                        mAdapter.getDataList().addAll(data.optJson("data").optJson("list").toArrayList());
        mPtrFrame.refreshComplete();
//                        mAdapter.notifyDataSetChanged();
//                    }
//                }, 0);
//            }
//        });
    }

    /**
     * 根据分辨率计算表情的尺寸
     *
     * @return float
     */
    private void calculateEmojiSize() {
        // 计算当前分辨率与1280*800的比例
        float heightRatio = (float) screenHeight / (float) 1280;
        float widthRatio = (float) screenWidth / (float) 800;
        float inSampleSize1 = heightRatio > widthRatio ? heightRatio
                : widthRatio;
        inSampleSize = inSampleSize1 < 1 ? inSampleSize1 : 2f / 3f;
    }

    private void setScreen() {
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        if (screenWidth > screenHeight) {
            screenWidth = screenWidth ^ screenHeight;
            screenHeight = screenWidth ^ screenHeight;
            screenWidth = screenWidth ^ screenHeight;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        chatMsgAdapter.stopThread();
    }

    // 录音时显示Dialog
    void showVoiceDialog() {
        voiceDialog = new Dialog(this, R.style.DialogStyle);
        j = 0;
        voiceDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        voiceDialog.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        voiceDialog.setContentView(R.layout.my_dialog);
        dialog_img = (ImageView) voiceDialog.findViewById(R.id.dialog_img);
        dialog_img2 = (ImageView) voiceDialog.findViewById(R.id.dialog_img2);
        voiceDialog.show();
    }

    // 录音时间太短时Toast显示
    void showWarnToast() {
        if(toast != null){
            toast.cancel();
        }else{
            toast = new Toast(this);
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setPadding(20, 20, 20, 20);

            // 定义一个ImageView
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.voice_to_short); // 图标

            TextView mTv = new TextView(this);
            mTv.setText("时间太短   录音失败");
            mTv.setTextSize(14);
            mTv.setTextColor(Color.WHITE);// 字体颜色
            // mTv.setPadding(0, 10, 0, 0);

            // 将ImageView和ToastView合并到Layout中
            linearLayout.addView(imageView);
            linearLayout.addView(mTv);
            linearLayout.setGravity(Gravity.CENTER);// 内容居中
            linearLayout.setBackgroundResource(R.drawable.record_bg);// 设置自定义toast的背景

            toast.setView(linearLayout);
            toast.setGravity(Gravity.CENTER, 0, 0);// 起点位置为中间 100为向下移100dp
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();
            toast = null;
        }

    }

    // 计时线程
    private Runnable ImgThread = new Runnable() {
        @Override
        public void run() {

            recodeTime = 0.0f;
            while (recordState == RECORD_ING) {
                if (recodeTime >= 50) {
                    imgHandle.sendEmptyMessage(2);
                }
                if (recodeTime >= MAX_TIME && MAX_TIME != 0) {
                    imgHandle.sendEmptyMessage(0);
                } else {
                    try {
                        Thread.sleep(200);
                        recodeTime += 0.2;
                        if (recordState == RECORD_ING && recodeTime < 50) {
                            imgHandle.sendEmptyMessage(1);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        Handler imgHandle = new Handler() {
            int count = 0;
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case 0:
                        if (recordState == RECORD_ING) {
                            recordState = RECODE_ED;
                            if (voiceDialog.isShowing()) {
                                voiceDialog.dismiss();
                            }
                            try {
                                mRecorder.stop();
                                voiceValue = 0.0;

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (recodeTime < 1.0) {
                                showWarnToast();
                                recordState = RECORD_NO;
                            } else {
                                // 录音成功
                                btnRecord.setText("按住说话");
                                UploadVoice upVoice = new UploadVoice();
                                try {
                                    // 上传语音文件
                                    voicePath = getAmrPath(voiceName);
                                    upVoice.uploadVoice(voicePath, context);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        break;
                    case 1:

                        setDialogImage();
                        break;
                    case 2:
                        count++;

                        if (count % 5 == 0) {
                            if(j < 10){
                                dialog_img.setImageResource(bitm[j++ % 10]);
                            }
                        }

                        break;
                    default:
                        break;

                }
            }
        };
    };
    // 录音线程
    void mythread() {
        MyThreadPool.getInstance().submit(ImgThread);
    }

    // 录音Dialog图片随声音大小切换
    void setDialogImage() {
        voiceValue = mRecorder.getAmplitude();
        if (voiceValue > 10.0 && voiceValue < 1000) {
            dialog_img.setImageResource(R.drawable.sy7);
        } else if (voiceValue > 1000.0 && voiceValue < 3200) {
            dialog_img.setImageResource(R.drawable.sy6);
        } else if (voiceValue > 3200.0 && voiceValue < 5000) {
            dialog_img.setImageResource(R.drawable.sy5);
        }else if (voiceValue > 5000.0 && voiceValue < 8000.0) {
            dialog_img.setImageResource(R.drawable.sy4);
        } else if (voiceValue > 8000.0 && voiceValue < 14000.0) {
            dialog_img.setImageResource(R.drawable.sy3);
        } else if (voiceValue > 14000.0 && voiceValue < 20000) {
            dialog_img.setImageResource(R.drawable.sy2);
        }else if (voiceValue > 20000.0) {
            dialog_img.setImageResource(R.drawable.sy1);
        }
    }
    public String getAmrPath(String path) {
        String amrPaht = mRecorder.sanitizePath(path);
        return amrPaht;
    }
}
