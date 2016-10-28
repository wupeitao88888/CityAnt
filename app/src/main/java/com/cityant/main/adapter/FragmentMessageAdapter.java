package com.cityant.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cityant.main.R;
import com.hyphenate.easeui.bean.MessageList;
import com.iloomo.base.CommonAdapter;
import com.iloomo.utils.DateUtil;
import com.iloomo.utils.PImageLoaderUtils;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ViewHolder;

import java.util.List;

/**
 * Created by wupeitao on 16/9/16.
 */
public class FragmentMessageAdapter extends CommonAdapter {

    public FragmentMessageAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MessageList meCrows = (MessageList) mDatas.get(i);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_fragmentmessageadapter, null);
        }
        TextView unread_msg = (TextView) ViewHolder.get(convertView, R.id.unread_msg);
        TextView name = (TextView) ViewHolder.get(convertView, R.id.name);
        TextView departname = (TextView) ViewHolder.get(convertView, R.id.departname);
        TextView time = (TextView) ViewHolder.get(convertView, R.id.time);
        ImageView avatar = (ImageView) ViewHolder.get(convertView, R.id.avatar);


        StrUtil.setText(departname, meCrows.getLastmsg());
        StrUtil.setText(name, meCrows.getUser_name());

        if (!TextUtils.isEmpty(meCrows.getCount())) {
            int count = Integer.parseInt(meCrows.getCount());
            if (count > 0) {
                if (count > 100) {
                    StrUtil.setText(unread_msg, "99+");
                }else{
                    StrUtil.setText(unread_msg, meCrows.getCount());
                }
                unread_msg.setVisibility(View.VISIBLE);
            } else {
                unread_msg.setVisibility(View.GONE);
            }
        } else {
            unread_msg.setVisibility(View.GONE);
        }

        try {
            StrUtil.setText(time, DateUtil.getDateToString(Long.parseLong(meCrows.getTime())));
        } catch (Exception e) {
            StrUtil.setText(time, meCrows.getTime());
        }

        PImageLoaderUtils.getInstance().displayuserHand(meCrows.getUser_avar(), avatar, context);
        return convertView;
    }
}
