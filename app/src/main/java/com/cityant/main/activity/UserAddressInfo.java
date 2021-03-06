package com.cityant.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.adapter.AddressAdapter;
import com.cityant.main.adapter.UserAddressAdapter;
import com.cityant.main.bean.Address;
import com.cityant.main.bean.AddressModel;
import com.cityant.main.bean.UniversallyModel;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.ToastUtil;
import com.iloomo.widget.LCDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wupeitao on 2016/10/20.
 */

public class UserAddressInfo extends ActivitySupport {
    private ListView listtype;
    private UserAddressAdapter addressAdapter;
    public final static int RESULT = 1;
    private LCDialog lcDialog;
    private List<Address> mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useraddressinfo);
        setCtenterTitle("我的收货地址");
        initView();
    }


    /***
     * 添加地址
     *
     * @param view
     */
    public void onAddAddress(View view) {
        startActivityForResult(new Intent(context, AddAddressActivity.class), RESULT);
    }


    private void initView() {
        listtype = (ListView) findViewById(R.id.type);
        getAddressList();
    }

    private void getAddressList() {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());

        new AsyncHttpPost(new ThreadCallBack() {
            @Override
            public void onCallbackFromThread(String resultJson, Object modelClass) {

            }

            @Override
            public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
                switch (resultCode) {
                    case MYTaskID.ADDRESS_INDEX:
                        AddressModel addressModel = (AddressModel) modelClass;
                        mlist = addressModel.getData().getAddress_list();
                        addressAdapter = new UserAddressAdapter(context, mlist);
                        listtype.setAdapter(addressAdapter);
                        listtype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                startActivityForResult(new Intent(context, AddAddressActivity.class).putExtra("Address", addressModel.getData().getAddress_list().get(position)), RESULT);
                            }
                        });
                        listtype.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                showInfoDialog(addressModel.getData().getAddress_list().get(position).getAddress_id(), position);
                                return true;
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
                    case MYTaskID.ADDRESS_INDEX:
                        AddressModel addressModel = (AddressModel) modelClass;
                        ToastUtil.showShort(context, addressModel.getData().getCode_message());
                        break;
                }
            }
        }, MYAppconfig.ADDRESS_INDEX, parameter, MYTaskID.ADDRESS_INDEX,
                AddressModel.class, context);
    }


    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        switch (arg0) {
            case AddressActivity.RESULT:
                getAddressList();
                break;
        }
    }


    private void showInfoDialog(String address_id, int position) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_dialogaddress, null);
        lcDialog = new LCDialog(context, com.iloomo.paysdk.R.style.PgDialog, inflate);
        lcDialog.show();
        RelativeLayout default_add = (RelativeLayout) inflate.findViewById(R.id.default_add);
        RelativeLayout delete = (RelativeLayout) inflate.findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lcDialog.isShowing()) {
                    lcDialog.dismiss();
                }
                deleteAddress(address_id, position);
            }
        });
        default_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lcDialog.isShowing()) {
                    lcDialog.dismiss();
                }
                setDefault(address_id);
            }
        });
    }


    private void deleteAddress(String address_id, int position) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("address_id", address_id);

        new AsyncHttpPost(new ThreadCallBack() {
            @Override
            public void onCallbackFromThread(String resultJson, Object modelClass) {

            }

            @Override
            public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
                switch (resultCode) {
                    case MYTaskID.ADDRESS_DELETE:
                        mlist.remove(position);
                        addressAdapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onCallbackFromThreadError(String resultJson, Object modelClass) {

            }

            @Override
            public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
                switch (resultCode) {
                    case MYTaskID.ADDRESS_DELETE:
                        UniversallyModel addressModel = (UniversallyModel) modelClass;
                        ToastUtil.showShort(context, addressModel.getData().getCode_message());
                        break;
                }
            }
        }, MYAppconfig.ADDRESS_DELETE, parameter, MYTaskID.ADDRESS_DELETE,
                UniversallyModel.class, context);
    }

    private void setDefault(String address_id) {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("address_id", address_id);

        new AsyncHttpPost(new ThreadCallBack() {
            @Override
            public void onCallbackFromThread(String resultJson, Object modelClass) {

            }

            @Override
            public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
                switch (resultCode) {
                    case MYTaskID.ADDRESS_SETDEFAULT:
                        getAddressList();
                        break;
                }
            }

            @Override
            public void onCallbackFromThreadError(String resultJson, Object modelClass) {

            }

            @Override
            public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
                switch (resultCode) {
                    case MYTaskID.ADDRESS_SETDEFAULT:
                        UniversallyModel addressModel = (UniversallyModel) modelClass;
                        ToastUtil.showShort(context, addressModel.getData().getCode_message());
                        break;
                }
            }
        }, MYAppconfig.ADDRESS_SETDEFAULT, parameter, MYTaskID.ADDRESS_SETDEFAULT,
                UniversallyModel.class, context);
    }


}
