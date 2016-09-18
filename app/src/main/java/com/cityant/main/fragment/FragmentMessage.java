package com.cityant.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cityant.main.R;
import com.cityant.main.activity.MYChatActivity;
import com.cityant.main.activity.MyFrendActivity;
import com.cityant.main.adapter.FragmentMessageAdapter;
import com.cityant.main.bean.MessageList;
import com.cityant.main.bean.MyFrends;
import com.cityant.main.utlis.ColorRandomizer;
import com.iloomo.base.FragmentSupport;
import com.iloomo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FragmentMessage extends FragmentSupport {

    private ListView msglist;
    private FragmentMessageAdapter fragmentMessageAdapter;

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


        List<MessageList> msgList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MessageList messageList = new MessageList();
            messageList.setFriend_id(i + "");
            messageList.setUser_avar("");
            messageList.setCount(i + "");
            messageList.setTime("2016-09-16 11:30");
            messageList.setLastmsg("城市蚂蚁你用着怎么样？");
            messageList.setMsgid(i + "");
            messageList.setUser_name("用户" + i);
            msgList.add(messageList);
        }
        fragmentMessageAdapter = new FragmentMessageAdapter(context, msgList);
        msglist.setAdapter(fragmentMessageAdapter);
        msglist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyFrends myFrends = new MyFrends();
                myFrends.setFriend_id(msgList.get(i).getFriend_id());
                myFrends.setUser_name(msgList.get(i).getUser_name());
                myFrends.setUser_avar(msgList.get(i).getUser_avar());
                startActivity(new Intent(context, MYChatActivity.class).putExtra("MyFrend", myFrends));
            }
        });
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}