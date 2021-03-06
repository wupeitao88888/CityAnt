package com.cityant.main.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cityant.main.R;
import com.cityant.main.adapter.MyFrendsAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chatuidemo.Constant;
import com.hyphenate.chatuidemo.ui.ChatActivity;
import com.hyphenate.easeui.bean.MyFrends;
import com.cityant.main.bean.MyFrendsModel;
import com.hyphenate.easeui.db.DBControl;
import com.hyphenate.easeui.global.MYAppconfig;
import com.cityant.main.global.MYTaskID;
import com.iloomo.base.ActivitySupport;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.threadpool.MyThreadPool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.cityant.main.utlis.*;
import com.iloomo.utils.ToastUtil;

/**
 * Created by wupeitao on 16/8/19.
 */
public class MyFrendActivity extends ActivitySupport implements AdapterView.OnItemClickListener, ThreadCallBack, View.OnClickListener {
    private ListView myfrendslist;
    private MyFrendsAdapter myFrendsAdapter;
    private HashMap<String, Integer> selector;// 存放含有索引字母的位置
    private String[] indexStr = {"#", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};
    private int height;// 字体高度
    private boolean flag = false;
    private LinearLayout layoutIndex;
    private TextView tv_show;
    private List<MyFrends> myFrendses;
    private List<MyFrends> newmyFrends = new ArrayList<>();
    private RelativeLayout mequn_chatlist;
    private RelativeLayout mebrand;
    private RelativeLayout addNewFrend;

    private final int F_REFARESH = 1001;
    private final int F_NETWORKFINISH = 1002;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case F_REFARESH:
                    myFrendses = (List<MyFrends>) msg.obj;
                    String[] allNames = sortIndex(myFrendses);
                    sortList(allNames);
                    selector = new HashMap<String, Integer>();

                    for (int j = 0; j < indexStr.length; j++) {// 循环字母表，找出newPersons中对应字母的位置
                        for (int i = 0; i < newmyFrends.size(); i++) {
                            if (newmyFrends.get(i).getUser_name().equals(indexStr[j])) {
                                selector.put(indexStr[j], i);
                            }
                        }
                    }
                    myFrendsAdapter.notifyDataSetChanged();
                    break;
                case F_NETWORKFINISH:
                    refareshDate();
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfrend);
        setCtenterTitle(R.string.addressbook);
        initView();
    }

    private void initView() {
        myfrendslist = (ListView) findViewById(R.id.myfrendslist);
        layoutIndex = (LinearLayout) this.findViewById(R.id.layout);
        tv_show = (TextView) findViewById(R.id.tv);
        tv_show.setVisibility(View.GONE);
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_headerview, null);
        mequn_chatlist = (RelativeLayout) inflate.findViewById(R.id.mequn_chatlist);
        mebrand = (RelativeLayout) inflate.findViewById(R.id.mebrand);
        addNewFrend = (RelativeLayout) inflate.findViewById(R.id.addNewFrend);
        myfrendslist.addHeaderView(inflate);


        mequn_chatlist.setOnClickListener(this);
        mebrand.setOnClickListener(this);
        addNewFrend.setOnClickListener(this);
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("page", "1");
        parameter.put("page_size", "1000");

        startHttpRequst(MYAppconfig.FREND_LIST, parameter, MYTaskID.FRENDS_LIST);

        myFrendsAdapter = new MyFrendsAdapter(context, newmyFrends);
        myfrendslist.setAdapter(myFrendsAdapter);
        myfrendslist.setOnItemClickListener(this);
        refareshDate();
    }

    /***
     * 刷新数据
     */
    public void refareshDate() {
        MyThreadPool.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                List<MyFrends> myFren = DBControl.getInstance(context).selectMyFrends();
                Message message = new Message();
                message.what = F_REFARESH;
                message.obj = myFren;
                handler.sendMessage(message);
            }
        });
    }


    public void startHttpRequst(String url,
                                Map<String, Object> parameter, int resultCode) {

        new AsyncHttpPost(this, url, parameter, resultCode,
                MyFrendsModel.class, context);
    }


    /**
     * 重新排序获得一个新的List集合
     *
     * @param allNames
     */
    private void sortList(String[] allNames) {
        newmyFrends.clear();
        for (int i = 0; i < allNames.length; i++) {
            if (allNames[i].length() != 1) {
                for (int j = 0; j < myFrendses.size(); j++) {
                    if (allNames[i].equals(myFrendses.get(j).getPinYinName())) {
                        MyFrends p = new MyFrends();
                        p.setUser_name(myFrendses.get(j).getUser_name());
                        p.setUser_avar(myFrendses.get(j).getUser_avar());
                        p.setFriend_id(myFrendses.get(j).getFriend_id());
                        p.setPinYinName(myFrendses.get(j).getPinYinName());
                        p.setIsGroup("Chat");
                        newmyFrends.add(p);
                    }
                }
            } else {
                MyFrends p = new MyFrends();
                p.setUser_name(allNames[i]);
                newmyFrends.add(p);
            }
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // 在oncreate里面执行下面的代码没反应，因为oncreate里面得到的getHeight=0
        if (!flag) {// 这里为什么要设置个flag进行标记，我这里不先告诉你们，请读者研究，因为这对你们以后的开发有好处
            height = layoutIndex.getMeasuredHeight() / indexStr.length;
            getIndexView();
            flag = true;
        }
    }

    /**
     * 绘制索引列表
     */
    public void getIndexView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, height);
        for (int i = 0; i < indexStr.length; i++) {
            final TextView tv = new TextView(this);
            tv.setLayoutParams(params);
            tv.setText(indexStr[i]);
            tv.setPadding(10, 0, 10, 0);
            layoutIndex.addView(tv);
            layoutIndex.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event)

                {
                    float y = event.getY();
                    int index = (int) (y / height);
                    if (index > -1 && index < indexStr.length) {// 防止越界
                        String key = indexStr[index];
                        if (selector.containsKey(key)) {
                            int pos = selector.get(key);
                            if (myfrendslist.getHeaderViewsCount() > 0) {// 防止ListView有标题栏，本例中没有。
                                myfrendslist.setSelectionFromTop(
                                        pos + myfrendslist.getHeaderViewsCount(), 0);
                            } else {
                                myfrendslist.setSelectionFromTop(pos, 0);// 滑动到第一项
                            }
                            tv_show.setVisibility(View.VISIBLE);
                            tv_show.setText(indexStr[index]);
                        }
                        tv_show.setVisibility(View.VISIBLE);
                        tv_show.setText(indexStr[index]);
                    }
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            layoutIndex.setBackgroundColor(Color
                                    .parseColor("#606060"));
                            break;

                        case MotionEvent.ACTION_MOVE:

                            break;
                        case MotionEvent.ACTION_UP:
                            layoutIndex.setBackgroundColor(Color
                                    .parseColor("#33606060"));
                            tv_show.setVisibility(View.GONE);
                            break;
                    }
                    return true;
                }
            });
        }
    }

    /**
     * 获取排序后的新数据
     *
     * @param persons
     * @return
     */
    public String[] sortIndex(List<MyFrends> persons) {
        TreeSet<String> set = new TreeSet<String>();
        // 获取初始化数据源中的首字母，添加到set中
        for (MyFrends person : persons) {
            set.add(StringHelper.getPinYinHeadChar(person.getUser_name()).substring(
                    0, 1));
        }
        // 新数组的长度为原数据加上set的大小
        String[] names = new String[persons.size() + set.size()];
        int i = 0;
        for (String string : set) {
            names[i] = string;
            i++;
        }
        String[] pinYinNames = new String[persons.size()];
        for (int j = 0; j < persons.size(); j++) {
            persons.get(j).setPinYinName(
                    StringHelper
                            .getPingYin(persons.get(j).getUser_name().toString()));
            pinYinNames[j] = StringHelper.getPingYin(persons.get(j).getUser_name()
                    .toString());
        }
        // 将原数据拷贝到新数据中
        System.arraycopy(pinYinNames, 0, names, set.size(), pinYinNames.length);
        // 自动按照首字母排序
        Arrays.sort(names, String.CASE_INSENSITIVE_ORDER);
        return names;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i - 1 > -1 && i - 1 < newmyFrends.size()) {
            MyFrends myFrends = newmyFrends.get(i - 1);
            if (myFrends.getFriend_id().equals(EMClient.getInstance().getCurrentUser()))
                ToastUtil.showShort(context, R.string.Cant_chat_with_yourself);
            else {
                // start chat acitivity
                Intent intent = new Intent(context, ChatActivity.class);
//                if(conversation.isGroup()){
//                    if(conversation.getType() == EMConversation.EMConversationType.ChatRoom){
//                        // it's group chat
//                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
//                    }else{
//                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
//                    }

//                }
                // it's single chat
                intent.putExtra(Constant.EXTRA_USER_ID, myFrends.getFriend_id());
                startActivity(intent);
            }
//            startActivity(new Intent(context, MYChatActivity.class).putExtra("MyFrend", newmyFrends.get(i - 1)));
        }
    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        switch (resultCode) {
            case MYTaskID.FRENDS_LIST:
                MyFrendsModel baseModel = (MyFrendsModel) modelClass;
                List<MyFrends> myFrends = baseModel.getData().getFriend_list();

                MyThreadPool.getInstance().submit(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < baseModel.getData().getFriend_list().size(); i++) {
                            DBControl.getInstance(context).insertFrends(baseModel.getData().getFriend_list().get(i));
                        }
                        Message message = new Message();
                        message.obj = "";
                        message.what = F_NETWORKFINISH;
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
        MyFrendsModel baseModel = (MyFrendsModel) modelClass;
        ToastUtil.showShort(context, baseModel.getData().getCode_message());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mequn_chatlist:
                mIntent(context, MeCrowdActivity.class);
                break;
            case R.id.mebrand:
                mIntent(context, MeBrandsActivity.class);
                break;
            case R.id.addNewFrend:
                mIntent(context, NewFrendsActivity.class);
                break;
        }
    }


}
