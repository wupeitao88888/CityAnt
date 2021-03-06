package com.hyphenate.chatuidemo.ui;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cityant.main.activity.AddFrendsActivity;
import com.cityant.main.activity.CertifyCenterActivity;
import com.cityant.main.activity.CreateActivity;
import com.cityant.main.activity.FinishedActivity;
import com.cityant.main.activity.GuessActivity;
import com.cityant.main.activity.IdeaActivity;
import com.cityant.main.activity.JudgeActivity;
import com.cityant.main.activity.MYBeanActivity;
import com.cityant.main.activity.MyFrendActivity;
import com.cityant.main.activity.RankActivity;
import com.cityant.main.activity.SettingActivity;
import com.cityant.main.activity.ShoppingActivity;
import com.cityant.main.activity.SingupActivity;
import com.cityant.main.activity.SportsActivity;
import com.cityant.main.activity.StorehouseActivity;
import com.cityant.main.activity.TodayReseiveActivity;
import com.cityant.main.activity.UserinfoActivity;
import com.cityant.main.activity.WalletActivity;
import com.cityant.main.bean.BusEventFragmentMessage;
import com.cityant.main.utlis.AppBus;
import com.cityant.main.zxing.MipcaActivityCapture;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMConversation.EMConversationType;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chatuidemo.Constant;

import com.hyphenate.chatuidemo.db.InviteMessgeDao;
import com.hyphenate.easeui.model.EaseAtMessageHelper;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.widget.EaseConversationList.EaseConversationListHelper;
import com.hyphenate.util.NetUtils;
import com.cityant.main.R;
import com.iloomo.utils.L;
import com.squareup.otto.Subscribe;

public class ConversationListFragment extends EaseConversationListFragment implements View.OnClickListener {

    private TextView errorText;
    private PopupWindow popupWindow;

    @Override
    protected void initView() {
        super.initView();
        View errorView = (LinearLayout) View.inflate(getActivity(), R.layout.em_chat_neterror_item, null);
        titleBar.setFristMenuimgIsVisbility(View.VISIBLE);
        titleBar.setRightFristMenuimg(R.drawable.add_menu);
        titleBar.setLeftImage(R.drawable.tongxunl);
        titleBar.setRightFristMenuimgListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPopupWindow();
                popupWindow.showAsDropDown(view, 0, 25);
            }
        });
        titleBar.setOnclickBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(getActivity(), MyFrendActivity.class));
            }
        });
        errorItemContainer.addView(errorView);
        errorText = (TextView) errorView.findViewById(R.id.tv_connect_errormsg);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        // register context menu
        registerForContextMenu(conversationListView);
        conversationListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EMConversation conversation = conversationListView.getItem(position);
                String username = conversation.getUserName();
                if (username.equals(EMClient.getInstance().getCurrentUser()))
                    Toast.makeText(getActivity(), R.string.Cant_chat_with_yourself, Toast.LENGTH_SHORT).show();
                else {
                    // start chat acitivity
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    if (conversation.isGroup()) {
                        if (conversation.getType() == EMConversationType.ChatRoom) {
                            // it's group chat
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);
                        } else {
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                        }

                    }
                    // it's single chat
                    intent.putExtra(Constant.EXTRA_USER_ID, username);
                    startActivity(intent);
                }
            }
        });
        //red packet code : 红包回执消息在会话列表最后一条消息的展示
        conversationListView.setConversationListHelper(new EaseConversationListHelper() {
            @Override
            public String onSetItemSecondaryText(EMMessage lastMessage) {
//                if (lastMessage.getBooleanAttribute(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, false)) {
//                    String sendNick = lastMessage.getStringAttribute(RedPacketConstant.EXTRA_RED_PACKET_SENDER_NAME, "");
//                    String receiveNick = lastMessage.getStringAttribute(RedPacketConstant.EXTRA_RED_PACKET_RECEIVER_NAME, "");
//                    String msg;
//                    if (lastMessage.direct() == EMMessage.Direct.RECEIVE) {
//                        msg = String.format(getResources().getString(R.string.msg_someone_take_red_packet), receiveNick);
//                    } else {
//                        if (sendNick.equals(receiveNick)) {
//                            msg = getResources().getString(R.string.msg_take_red_packet);
//                        } else {
//                            msg = String.format(getResources().getString(R.string.msg_take_someone_red_packet), sendNick);
//                        }
//                    }
//                    return msg;
//                }
                return null;
            }
        });
        super.setUpView();
        //end of red packet code
    }

    @Override
    protected void onConnectionDisconnected() {
        super.onConnectionDisconnected();
        if (NetUtils.hasNetwork(getActivity())) {
            errorText.setText(R.string.can_not_connect_chat_server_connection);
        } else {
            errorText.setText(R.string.the_current_network);
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.em_delete_message, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean deleteMessage = false;
        if (item.getItemId() == R.id.delete_message) {
            deleteMessage = true;
        } else if (item.getItemId() == R.id.delete_conversation) {
            deleteMessage = false;
        }
        EMConversation tobeDeleteCons = conversationListView.getItem(((AdapterContextMenuInfo) item.getMenuInfo()).position);
        if (tobeDeleteCons == null) {
            return true;
        }
        if (tobeDeleteCons.getType() == EMConversationType.GroupChat) {
            EaseAtMessageHelper.get().removeAtMeGroup(tobeDeleteCons.getUserName());
        }
        try {
            // delete conversation
            EMClient.getInstance().chatManager().deleteConversation(tobeDeleteCons.getUserName(), deleteMessage);
            InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
            inviteMessgeDao.deleteMessage(tobeDeleteCons.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        refresh();

        // update unread count
        ((MainActivity) getActivity()).updateUnreadLabel();
        return true;
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
                refresh();
                break;
            case 2://是连接不到聊天服务器
//                fl_error_item.setVisibility(View.VISIBLE);
//                error_content.setText(context.getResources().getString(R.string.disconnectioned));
                break;
            case 3://当前网络不可用，请检查网络设置
//                fl_error_item.setVisibility(View.VISIBLE);
//                error_content.setText(context.getResources().getString(R.string.network_disconnectioned));
                break;
            case 4:
//                fl_error_item.setVisibility(View.GONE);
                break;
        }
    }

    @Subscribe
    public void onDataChange(String sss) {
        System.out.println("====" + sss);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.shaoshao://扫一扫
                Intent intent = new Intent(getContext(), MipcaActivityCapture.class);
                startActivity(intent);
                break;
            case R.id.addfiend://添加好友
                startActivity(new Intent(getContext(), AddFrendsActivity.class));
                break;
            case R.id.g_start://群发起
                break;
            case R.id.pinpaibuluo://品牌部落
                break;
        }
    }

    /**
     * 创建PopupWindow
     */
    protected void initPopuptWindow() {
        // TODO Auto-generated method stub
        // 获取自定义布局文件activity_popupwindow_left.xml的视图
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupWindow_view = layoutInflater.inflate(R.layout.layout_menuright, null);

        RelativeLayout addfiend = (RelativeLayout) popupWindow_view.findViewById(R.id.addfiend);
        RelativeLayout g_start = (RelativeLayout) popupWindow_view.findViewById(R.id.g_start);
        RelativeLayout pinpaibuluo = (RelativeLayout) popupWindow_view.findViewById(R.id.pinpaibuluo);
        RelativeLayout shaoshao = (RelativeLayout) popupWindow_view.findViewById(R.id.shaoshao);
        addfiend.setOnClickListener(this);
        g_start.setOnClickListener(this);
        pinpaibuluo.setOnClickListener(this);
        shaoshao.setOnClickListener(this);

        // 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
        popupWindow = new PopupWindow(popupWindow_view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT, true);
        // 设置动画效果
//        popupWindow.setAnimationStyle(R.style.AnimationFade);
        // 点击其他地方消失
        popupWindow_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });
    }

    /***
     * 获取PopupWindow实例
     */
    private void getPopupWindow() {
        if (null != popupWindow) {
            popupWindow.dismiss();
            return;
        } else {
            initPopuptWindow();
        }
    }
}
