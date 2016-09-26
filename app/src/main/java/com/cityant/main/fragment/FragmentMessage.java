package com.cityant.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.activity.MYChatActivity;
import com.cityant.main.activity.MyFrendActivity;
import com.cityant.main.activity.RegisterActivity;
import com.cityant.main.adapter.FragmentMessageAdapter;
import com.cityant.main.bean.BusEventFragmentMessage;
import com.cityant.main.bean.MessageList;
import com.cityant.main.bean.MyFrends;
import com.cityant.main.db.DBControl;
import com.cityant.main.utlis.AppBus;
import com.cityant.main.utlis.ColorRandomizer;
import com.iloomo.base.FragmentSupport;
import com.iloomo.threadpool.MyThreadPool;
import com.iloomo.utils.L;
import com.iloomo.utils.ToastUtil;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FragmentMessage extends FragmentSupport {

    private ListView msglist;
    private FragmentMessageAdapter fragmentMessageAdapter;
    private TextView error_content;
    private RelativeLayout fl_error_item;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case FEFARESH:
                    if (fragmentMessageAdapter == null) {
                        fragmentMessageAdapter = new FragmentMessageAdapter(context, messageLists);
                        msglist.setAdapter(fragmentMessageAdapter);
                    } else {
                        fragmentMessageAdapter.notifyDataSetChanged();
                    }
                    break;
            }
        }
    };

    private final int FEFARESH = 1001;

    @Override
    public View setTitleBar(View view) {
        isLeftVisibility(false);
        titleBar.setFristMenuimgIsVisbility(View.VISIBLE);
        titleBar.setSecondMenuimgIsVisbility(View.VISIBLE);
        titleBar.setRightFristMenuimgListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, MyFrendActivity.class));
            }
        });
        titleBar.setRightSecondMenuimgListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showShort(context, "右边第一个");

            }
        });
        return super.setTitleBar(view);
    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_message, null);
        setTitle("消息");
        msglist = (ListView) view.findViewById(R.id.msglist);
        error_content = (TextView) view.findViewById(R.id.error_content);
        fl_error_item = (RelativeLayout) view.findViewById(R.id.fl_error_item);

//        List<MessageList> msgList = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//            MessageList messageList = new MessageList();
//            messageList.setFriend_id(i + "");
//            messageList.setUser_avar("http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fupload.cbg.cn%2F2015%2F0311%2F1426053651305.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2574381543%2C3066317494%26fm%3D21%26gp%3D0.jpg");
//            messageList.setCount(i + "");
//            messageList.setTime("2016-09-16 11:30");
//            messageList.setLastmsg("城市蚂蚁你用着怎么样？");
//            messageList.setMsgid(i + "");
//            messageList.setUser_name("唐嫣" + i);
//            msgList.add(messageList);
//        }

        msglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyFrends myFrends = new MyFrends();
                myFrends.setFriend_id(messageLists.get(i).getFriend_id());
                myFrends.setUser_name(messageLists.get(i).getUser_name());
                myFrends.setUser_avar(messageLists.get(i).getUser_avar());
                startActivity(new Intent(context, MYChatActivity.class).putExtra("MyFrend", myFrends));
            }
        });
        getConversitionlist();
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onStart() {
        super.onStart();
        //注册到bus事件总线中
        AppBus.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppBus.getInstance().unregister(this);
    }

    /**
     * 定义订阅者，Activity中发布的消息，在此处会接收到，在此之前需要先在程序中register，看
     * 上面的onStart和onStop函数
     */
    @Subscribe
    public void setContent(BusEventFragmentMessage data) {
        switch (data.getContent()) {
            case 1://刷新列表
                L.e("有新消息啦");
                getConversitionlist();
                break;
            case 2://是连接不到聊天服务器
                fl_error_item.setVisibility(View.VISIBLE);
                error_content.setText(context.getResources().getString(R.string.disconnectioned));
                break;
            case 3://当前网络不可用，请检查网络设置
                fl_error_item.setVisibility(View.VISIBLE);
                error_content.setText(context.getResources().getString(R.string.network_disconnectioned));
                break;
            case 4:
                fl_error_item.setVisibility(View.GONE);
                break;
        }
    }

    @Subscribe
    public void onDataChange(String sss) {
        System.out.println("====" + sss);
    }

    private List<MessageList> messageLists;

    public void getConversitionlist() {
        if (messageLists == null) {
            messageLists = new ArrayList<>();
        }

        MyThreadPool.getInstance().submit(new Runnable() {
            @Override
            public void run() {
                messageLists.clear();
                messageLists.addAll(DBControl.getInstance(context).selectConversationlist());
                L.e("会话列表" + messageLists.size());
                Message message = new Message();
                message.what = FEFARESH;
                message.obj = "";
                mHandler.sendMessage(message);
            }
        });


    }

}