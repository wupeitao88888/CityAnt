package com.cityant.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.adapter.AddressAllAdapter;
import com.cityant.main.bean.Address;
import com.cityant.main.bean.AddressList;
import com.cityant.main.bean.AddressListModel;
import com.cityant.main.bean.UniversallyModel;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wupeitao on 2017/3/3.
 */

public class AddAddressActivity extends ActivitySupport {

    private EditText username;
    private EditText moblie;
    private EditText address;
    private TextView area_info;
    private RelativeLayout area_re;
    public final static int request = 0;
    private Address addressbean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addaddresss);
        setCtenterTitle("设置地址");
        setRightTitle("保存");

        username = (EditText) findViewById(R.id.username);
        moblie = (EditText) findViewById(R.id.moblie);
        address = (EditText) findViewById(R.id.address);
        area_info = (TextView) findViewById(R.id.area_info);
        area_re = (RelativeLayout) findViewById(R.id.area_re);

        setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(username.getText().toString())) {
                    ToastUtil.showShort(context, "姓名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(moblie.getText().toString())) {
                    ToastUtil.showShort(context, "手机不能为空");
                    return;
                }
                if (TextUtils.isEmpty(area_info.getText().toString())) {
                    ToastUtil.showShort(context, "地区不能为空");
                    return;
                }
                if (TextUtils.isEmpty(address.getText().toString())) {
                    ToastUtil.showShort(context, "详情地址不能为空");
                    return;
                }
                if (addressbean == null) {
                    addCity();
                } else {
                    updataCity();
                }

            }
        });
        area_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent();
                mIntent.setClass(context, AddressAllActivity.class);
                startActivityForResult(mIntent, request);
            }
        });
        Intent intent = getIntent();
        addressbean = (Address) intent.getSerializableExtra("Address");
        if (addressbean != null) {
            username.setText(addressbean.getName());
            moblie.setText(addressbean.getMobile());
            area_info.setText(addressbean.getArea());
            address.setText(addressbean.getAddress());
        }


    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        switch (arg0) {
            case AddAddressActivity.request:
                if (arg2 != null) {
                    Bundle bg = arg2.getBundleExtra("addressdata");
                    area_info.setText("");
                    area_info.append(bg.getString("province") + " ");
                    area_info.append(bg.getString("city") + " ");
                    area_info.append(bg.getString("district") + " ");
                    area_info.append(bg.getString("street") + " ");
                }
                break;

        }
    }


    public void addCity() {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("name", username.getText().toString());
        parameter.put("mobile", moblie.getText().toString());
        parameter.put("area", area_info.getText().toString());
        parameter.put("address", address.getText().toString());


        new AsyncHttpPost(new ThreadCallBack() {
            @Override
            public void onCallbackFromThread(String resultJson, Object modelClass) {

            }

            @Override
            public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
                switch (resultCode) {
                    case MYTaskID.ADDRESS_ADD:
                        UniversallyModel addressModel = (UniversallyModel) modelClass;
                        ToastUtil.showShort(context, addressModel.getData().getCode_message());
                        Intent intent = new Intent();
                        intent.putExtra("isrefresh", true);
                        setResult(AddAddressActivity.request, intent);
                        finish();
                        break;
                }
            }

            @Override
            public void onCallbackFromThreadError(String resultJson, Object modelClass) {

            }

            @Override
            public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
                switch (resultCode) {
                    case MYTaskID.ADDRESS_ADD:
                        UniversallyModel addressModel = (UniversallyModel) modelClass;
                        ToastUtil.showShort(context, addressModel.getData().getCode_message());
                        break;
                }
            }
        }, MYAppconfig.ADDRESS_ADD, parameter, MYTaskID.ADDRESS_ADD,
                UniversallyModel.class, context);

    }

    public void updataCity() {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("name", username.getText().toString());
        parameter.put("mobile", moblie.getText().toString());
        parameter.put("area", area_info.getText().toString());
        parameter.put("address", address.getText().toString());
        parameter.put("address_id", addressbean.getAddress_id());


        new AsyncHttpPost(new ThreadCallBack() {
            @Override
            public void onCallbackFromThread(String resultJson, Object modelClass) {

            }

            @Override
            public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
                switch (resultCode) {
                    case MYTaskID.ADDRESS_UPDATE:
                        UniversallyModel addressModel = (UniversallyModel) modelClass;
                        ToastUtil.showShort(context, addressModel.getData().getCode_message());
                        Intent intent = new Intent();
                        intent.putExtra("isrefresh", true);
                        setResult(AddAddressActivity.request, intent);
                        finish();
                        break;
                }
            }

            @Override
            public void onCallbackFromThreadError(String resultJson, Object modelClass) {

            }

            @Override
            public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
                switch (resultCode) {
                    case MYTaskID.ADDRESS_UPDATE:
                        UniversallyModel addressModel = (UniversallyModel) modelClass;
                        ToastUtil.showShort(context, addressModel.getData().getCode_message());
                        break;
                }
            }
        }, MYAppconfig.ADDRESS_UPDATE, parameter, MYTaskID.ADDRESS_UPDATE,
                UniversallyModel.class, context);

    }

}