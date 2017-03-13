package com.cityant.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.cityant.main.R;
import com.cityant.main.adapter.AddressAdapter;
import com.cityant.main.adapter.AddressAllAdapter;
import com.cityant.main.bean.AddressList;
import com.cityant.main.bean.AddressListModel;
import com.cityant.main.bean.AddressModel;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by wupeitao on 2017/3/6.
 */

public class AddressAllActivity extends ActivitySupport {

    private AddressAllAdapter addressAllAdapter;
    private ListView citylist;
    private Bundle bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addressall);
        setCtenterTitle("请选择地区");
        citylist = (ListView) findViewById(R.id.citylist);
        bd = new Bundle();
        getCity("", "province");
    }


    public void getCity(String code, String city) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("code", code);

        new AsyncHttpPost(new ThreadCallBack() {
            @Override
            public void onCallbackFromThread(String resultJson, Object modelClass) {

            }

            @Override
            public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
                switch (resultCode) {
                    case MYTaskID.ADDRESS_AREA:
                        AddressListModel addressModel = (AddressListModel) modelClass;
                        addressAllAdapter = new AddressAllAdapter(context, addressModel.getData().getArea_list());
                        citylist.setAdapter(addressAllAdapter);
                        citylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                AddressList addressList = (AddressList) addressModel.getData().getArea_list().get(position);
                                bd.putString(city, addressList.getName());
                                bd.putString(city + "Code", addressList.getCode());
                                if ("province".equals(city)) {
                                    getCity(addressList.getCode(), "city");
                                } else if ("city".equals(city)) {
                                    getCity(addressList.getCode(), "district");
                                } else if ("district".equals(city)) {
                                    getCity(addressList.getCode(), "street");
                                } else if ("street".equals(city)) {
                                    Intent mIntent = new Intent();
                                    mIntent.putExtra("addressdata", bd);
                                    setResult(AddAddressActivity.request, mIntent);
                                    finish();
                                }

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
                switch (resultCode) {
                    case MYTaskID.ADDRESS_AREA:
                        AddressListModel addressModel = (AddressListModel) modelClass;
                        ToastUtil.showShort(context, addressModel.getData().getCode_message());
                        break;
                }
            }
        }, MYAppconfig.ADDRESS_AREA, parameter, MYTaskID.ADDRESS_AREA,
                AddressListModel.class, context);
    }

}
