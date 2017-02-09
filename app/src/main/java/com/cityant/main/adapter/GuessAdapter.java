package com.cityant.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.activity.CreateSmallGrabTypeChooseActivity;
import com.cityant.main.bean.CreateSmallGreabType;
import com.cityant.main.bean.GuessIndexList;
import com.cityant.main.model.OnSelectTextViewListener;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.CommonAdapter;
import com.iloomo.utils.PImageLoaderUtils;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ViewHolder;
import com.zhy.http.okhttp.utils.ImageUtils;

import java.util.List;

/**
 * Created by wupeitao on 16/9/3.
 */
public class GuessAdapter extends CommonAdapter {

    public GuessAdapter(Context context, List mDatas) {
        super(context, mDatas);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        GuessIndexList meCrows = (GuessIndexList) mDatas.get(i);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_guessadapter, null);
        }

        TextView from_username = ViewHolder.get(convertView, R.id.from_username);
        Button from_address = ViewHolder.get(convertView, R.id.from_address);

        TextView sub = ViewHolder.get(convertView, R.id.sub);
        TextView add = ViewHolder.get(convertView, R.id.add);

        TextView question = ViewHolder.get(convertView, R.id.question);

        TextView answer_a = ViewHolder.get(convertView, R.id.answer_a);
        TextView answer_b = ViewHolder.get(convertView, R.id.answer_b);
        TextView answer_c = ViewHolder.get(convertView, R.id.answer_c);
        TextView answer_d = ViewHolder.get(convertView, R.id.answer_d);

        TextView select_a = ViewHolder.get(convertView, R.id.select_a);
        TextView select_b = ViewHolder.get(convertView, R.id.select_b);
        TextView select_c = ViewHolder.get(convertView, R.id.select_c);
        TextView select_d = ViewHolder.get(convertView, R.id.select_d);

        TextView submit = ViewHolder.get(convertView, R.id.submit);

        ImageView from_usericon = ViewHolder.get(convertView, R.id.from_usericon);


        StrUtil.setText(from_username, meCrows.getUser_name());
        PImageLoaderUtils.getInstance().setImageHead(from_usericon, meCrows.getUser_avar(), context);


        if (meCrows.getIsAd() == 1) {//是广告
            StrUtil.setText(from_address, "推广");
        } else {//不是广告？？？？？？？？？？
            StrUtil.setText(from_address, "好友");
        }

        StrUtil.setText(add, "+"+meCrows.getOk_bean());
        StrUtil.setText(sub, "-"+meCrows.getFail_bean());

        StrUtil.setText(question, meCrows.getQuestion());


        try {
            if (!TextUtils.isEmpty(meCrows.getKeyList().get(0))) {
                StrUtil.setText(answer_a, "A、" + meCrows.getKeyList().get(0));
            } else {
                answer_a.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            StrUtil.setText(answer_a, "A、");
        }


        try {
            if (!TextUtils.isEmpty(meCrows.getKeyList().get(1))) {
                StrUtil.setText(answer_b, "B、" + meCrows.getKeyList().get(1));
            } else {
                answer_b.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            StrUtil.setText(answer_b, "B、");
        }
        try {
            if (!TextUtils.isEmpty(meCrows.getKeyList().get(2))) {
                StrUtil.setText(answer_c, "C、" + meCrows.getKeyList().get(2));
            } else {
                answer_c.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            StrUtil.setText(answer_c, "C、");
        }


        try {
            if (!TextUtils.isEmpty(meCrows.getKeyList().get(3))) {
                StrUtil.setText(answer_d, "D、" + meCrows.getKeyList().get(3));
            } else {
                answer_d.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            StrUtil.setText(answer_d, "D、");
        }


        return convertView;
    }


    private void selectAnswer(TextView select_a, TextView select_b, TextView select_c, TextView select_d, int type, Button submit, int position) {

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        select_a.setOnClickListener(new OnSelectTextViewListener(select_a, select_b, select_c, select_d, type, submit, position));
        select_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        select_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        select_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
