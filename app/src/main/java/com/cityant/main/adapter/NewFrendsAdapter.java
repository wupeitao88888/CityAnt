package com.cityant.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.bean.HandleModel;
import com.cityant.main.bean.NewFrends;
import com.hyphenate.easeui.global.MYAppconfig;
import com.cityant.main.global.MYTaskID;
import com.iloomo.base.CommonAdapter;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.PImageLoaderUtils;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ToastUtil;
import com.iloomo.utils.ViewHolder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wupeitao on 16/8/26.
 */
public class NewFrendsAdapter extends CommonAdapter {
    public NewFrendsAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        NewFrends meCrows = (NewFrends) mDatas.get(i);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_newfrends, null);
        }
        ImageView newfrendshead = (ImageView) ViewHolder.get(convertView, R.id.newfrendshead);
        TextView newfrendsname = (TextView) ViewHolder.get(convertView, R.id.newfrendsname);
        TextView status = (TextView) ViewHolder.get(convertView, R.id.status);
        Button agree = (Button) ViewHolder.get(convertView, R.id.agree);
        Button refuse = (Button) ViewHolder.get(convertView, R.id.refuse);
        StrUtil.setText(newfrendsname, meCrows.getUser_name());
        PImageLoaderUtils.displayuserHand(meCrows.getUser_avar(), newfrendshead, context);
        if ("2".equals(meCrows.getState())) {
            StrUtil.setText(status, "验证中");
            status.setVisibility(View.VISIBLE);
            agree.setVisibility(View.GONE);
            refuse.setVisibility(View.GONE);
        } else {
            status.setVisibility(View.GONE);
            agree.setVisibility(View.VISIBLE);
            refuse.setVisibility(View.VISIBLE);
            agree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HandleFrends('1', meCrows.getFriend_id(),i);
                }
            });
            refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HandleFrends('2', meCrows.getFriend_id(),i);
                }
            });
        }
        return convertView;
    }


    /***
     * 处理好友请求
     *
     * @param process   处理 (1:同意,2:拒绝)
     * @param friend_id 好友id
     */
    public void HandleFrends(int process, String friend_id,int position) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("process", process+"");
        parameter.put("friend_id", friend_id);
        new AsyncHttpPost(new ThreadCallBack() {
            @Override
            public void onCallbackFromThread(String resultJson, Object modelClass) {

            }

            @Override
            public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
                switch (resultCode) {
                    case MYTaskID.HANDLERFRENDS:
                        HandleModel handleModel = (HandleModel) modelClass;
                        ToastUtil.showShort(context, handleModel.getData().getCode_message());
                        mDatas.remove(position);
                        notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onCallbackFromThreadError(String resultJson, Object modelClass) {

            }

            @Override
            public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
                switch (resultCode) {
                    case MYTaskID.HANDLERFRENDS:
                        HandleModel handleModel = (HandleModel) modelClass;
                        ToastUtil.showShort(context, handleModel.getData().getCode_message());
                        break;
                }
            }
        }, MYAppconfig.HANDLEFRENDS, parameter, MYTaskID.HANDLERFRENDS,
                HandleModel.class, context);
    }


}
