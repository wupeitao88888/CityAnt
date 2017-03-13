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
import com.cityant.main.bean.Address;
import com.cityant.main.bean.AddressModel;
import com.cityant.main.bean.GoodsListModel;
import com.cityant.main.bean.UniversallyModel;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.global.AppConfig;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.ToastUtil;
import com.iloomo.widget.LCDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by wupeitao on 2017/3/3.
 */

public class AddressActivity extends ActivitySupport implements View.OnClickListener {
    private ListView listtype;
    private AddressAdapter addressAdapter;
    public final static int RESULT = 1;
    private LCDialog lcDialog;
    private List<Address> mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        setCtenterTitle("地址");
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
                        addressAdapter = new AddressAdapter(context, mlist);
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
                                showInfoDialog(addressModel.getData().getAddress_list().get(position).getAddress_id(),position);
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
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_dialogcreategrab, null);
        lcDialog = new LCDialog(context, com.iloomo.paysdk.R.style.PgDialog, inflate);
        lcDialog.show();
        RelativeLayout my_close = (RelativeLayout) inflate.findViewById(R.id.my_close);
        TextView cancel = (TextView) inflate.findViewById(R.id.cancel);
        TextView affirm = (TextView) inflate.findViewById(R.id.affirm);
        TextView content = (TextView) inflate.findViewById(R.id.content);

        content.setText("是否删除？");
        my_close.setOnClickListener(this);
        cancel.setOnClickListener(this);
        affirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lcDialog.isShowing()) {
                    lcDialog.dismiss();
                }
                deleteAddress(address_id, position);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel:
                if (lcDialog.isShowing()) {
                    lcDialog.dismiss();
                }
                break;
            case R.id.my_close:
                if (lcDialog.isShowing()) {
                    lcDialog.dismiss();
                }
                break;
        }
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

//                        UniversallyModel addressModel = (UniversallyModel) modelClass;
//                        ToastUtil.showShort(context, addressModel.getData().getCode_message());
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

}
