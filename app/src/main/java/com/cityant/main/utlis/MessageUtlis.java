package com.cityant.main.utlis;

import android.content.Context;

import com.cityant.main.adapter.ChatMsgAdapter;
import com.hyphenate.easeui.bean.ChatMsgEntity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.db.DBControl;
import com.iloomo.threadpool.MyThreadPool;
import com.iloomo.utils.L;

/**
 * Created by wupeitao on 2016/10/8.
 */

public class MessageUtlis {
    public static MessageUtlis dbControl;
    private Context context;

    public MessageUtlis(Context context) {
        this.context = context;
    }

    public static MessageUtlis getInstance(Context context) {
        if (dbControl == null) {
            dbControl = new MessageUtlis(context);
        }
        return dbControl;
    }


    public void sendMessage(ChatMsgEntity chatMsgEntity, ChatMsgAdapter chatMsgAdapter) {
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = chatMsgEntity.getEmMessage();
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                MyThreadPool.getInstance().submit(new Runnable() {
                    @Override
                    public void run() {
                        DBControl.getInstance(context).updateChatMessageStatus(chatMsgEntity.getMessageid(), "2");
                    }
                });
                L.e("消息发送成功");
                chatMsgAdapter.setStatus(chatMsgEntity, 2);

            }

            @Override
            public void onError(int i, String s) {
                MyThreadPool.getInstance().submit(new Runnable() {
                    @Override
                    public void run() {
                        DBControl.getInstance(context).updateChatMessageStatus(chatMsgEntity.getMessageid(), "0");
                    }
                });
                L.e("消息发送失败");
                chatMsgAdapter.setStatus(chatMsgEntity, 0);

            }

            @Override
            public void onProgress(int i, String s) {
            }
        });
    }


}
