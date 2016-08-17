package com.cityant.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cityant.main.R;
import com.iloomo.base.ActivitySupport;

/**
 * Created by lvfl on 2016/8/17.
 */
public class CreateDemandActivity extends ActivitySupport{

    private Button commit_btn;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context,CreateDemandActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_demand_layout);
        setCtenterTitle("创建需求");
        initView();
    }

    private void initView() {
        commit_btn = (Button) findViewById(R.id.commit_btn);
        commit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO 网络请求
//                string   token   用户令牌
//                int   latitude   当前用户纬度
//                int   longitude   当前用户经度
//                int   city_id   城市id
//                int   pay_type   支付方式(0:支付宝,1:微信)
//                int   need_way   形式(0:线上,1:线下)
//                string   need_title   标题
//                int   tag_id   标签id
//                string   need_content   详情
//                int   need_sex   性别要求(0:男，1：女，2：不限)
//                string   address   地点
//                string   need_time   时间格式(Y-m-d H:i:s)
//                string   end_time   报名截止日期格式(Y-m-d H:i:s)
//                string   pay_price   单价
//                string   pay_unit   单位
//                int   need_man   所需名额


            }
        });
    }

}
