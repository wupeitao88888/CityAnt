package com.cityant.main.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cityant.main.R;
import com.cityant.main.adapter.GoodsPutAdapter;
import com.cityant.main.adapter.StoreHouseAdapter;
import com.cityant.main.bean.GoodsList;
import com.cityant.main.bean.GoodsListModel;
import com.cityant.main.bean.UserInfoModel;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.utils.BigDecimalUtil;
import com.iloomo.utils.DialogUtil;
import com.iloomo.utils.L;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ToastUtil;
import com.iloomo.utils.UnicodeUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

/**
 * Created by wupeitao on 16/8/13.
 */
public class GoodsPutActivity extends ActivitySupport {
    private ListView goods;
    private RelativeLayout price_re;
    private TextView allpace;
    private RelativeLayout GoodsClick;
    private RelativeLayout GoodssellClick;
    private GoodsPutAdapter goodsPutAdapter;
    private RelativeLayout address_re;
    private RelativeLayout freightClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goodsput);
        goods = (ListView) findViewById(R.id.goods);
        price_re = (RelativeLayout) findViewById(R.id.price_re);
        allpace = (TextView) findViewById(R.id.allpace);
        GoodsClick = (RelativeLayout) findViewById(R.id.GoodsClick);
        GoodssellClick = (RelativeLayout) findViewById(R.id.GoodssellClick);
        address_re = (RelativeLayout) findViewById(R.id.address_re);
        freightClick = (RelativeLayout) findViewById(R.id.freightClick);
        if (0 == getIntent().getIntExtra("type", 0)) {
            setCtenterTitle(R.string.goodsput);
            initTypeOut();
        } else {
            setCtenterTitle("商品卖出");
            price_re.setVisibility(View.VISIBLE);
            GoodssellClick.setVisibility(View.VISIBLE);
            GoodsClick.setVisibility(View.GONE);
            address_re.setVisibility(View.GONE);
            freightClick.setVisibility(View.GONE);
        }
        getStoreList();
    }

    private void initTypeOut() {
        price_re.setVisibility(View.GONE);
        GoodssellClick.setVisibility(View.GONE);
        address_re.setVisibility(View.GONE);
        freightClick.setVisibility(View.GONE);
    }

    /***
     * 调转到地址选择界面
     *
     * @param view
     */
    public void onAddressClick(View view) {

    }

    /**
     * 商品提出
     *
     * @param view
     */
    public void onGoodsClick(View view) {
        if (freightClick.getVisibility() == View.GONE) {
            address_re.setVisibility(View.VISIBLE);
            freightClick.setVisibility(View.VISIBLE);
        } else {
            String aGoodsOut = goodsPutAdapter.getAGoodsOut();
            ToastUtil.showShort(context, aGoodsOut);
        }
    }

    /****
     * 商品卖出
     *
     * @param view
     */
    public void onGoodssellClick(View view) {

        showSexChoose(view);
    }

    public void getStoreList() {
        Map<String, String> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        DialogUtil.startDialogLoading(context);
        OkHttpUtils
                .post()
                .url(MYAppconfig.STORE)
                .params(parameter)
                .tag(context)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showShort(context, e.getMessage());
                        DialogUtil.stopDialogLoading(context);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        response = UnicodeUtils.decodeUnicode(response);
                        DialogUtil.stopDialogLoading(context);
                        GoodsListModel model = JSON.parseObject(response, GoodsListModel.class);
                        GoodsListModel baseMadel = (GoodsListModel) model;
                        if (baseMadel.getCode().equals("200")) {

                            List<GoodsList> goods_list = baseMadel.getData().getGoods_list();

                            for (int i = 0; i < 20; i++) {
                                GoodsList goodsList = new GoodsList();
                                goodsList.setGoods_id(i + "");
                                goodsList.setGoods_img("");
                                goodsList.setGoods_price("100" + i);
                                goodsList.setNumber("" + i);
                                goodsList.setGoods_title("水杯一个，加油吧" + i);
                                goodsList.setOutnumber(Integer.parseInt(goodsList.getNumber()));
                                goods_list.add(goodsList);
                            }

                            goodsPutAdapter = new GoodsPutAdapter(context, goods_list);
                            goods.setAdapter(goodsPutAdapter);

                            goodsPutAdapter.setCountChangeLienster(new GoodsPutAdapter.CountChangeLienster() {
                                @Override
                                public void change(List mDatas) {
                                    getAGoodsOut(mDatas);
                                }
                            });
                            getAGoodsOut(goods_list);
                        } else {
                            ToastUtil.showShort(context, baseMadel.getData().getCode_message());
                        }
                    }
                });
    }

    private double Price_all = 0;

    public void getAGoodsOut(List mDatas) {

        double mul = 0;
        for (int i = 0; i < mDatas.size(); i++) {
            GoodsList goodsList = (GoodsList) mDatas.get(i);
            double price = Double.parseDouble(goodsList.getGoods_price());
            double count = goodsList.getOutnumber();

            mul = mul + BigDecimalUtil.mul(price, count);

        }
        Price_all = mul;
        StrUtil.setText(allpace, mul + "元");
    }

    private PopupWindow PopupWindowsex;

    private void showSexChoose(View parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_pop_submit, null);
        // 创建一个PopuWidow对象
        PopupWindowsex = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        initPop(view);

        //popupwindow弹出时的动画		popWindow.setAnimationStyle(R.style.popupWindowAnimation);
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        PopupWindowsex.setFocusable(true);
        // 设置允许在外点击消失
        PopupWindowsex.setOutsideTouchable(false);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        PopupWindowsex.setBackgroundDrawable(new LevelListDrawable());

        //软键盘不会挡着popupwindow
        PopupWindowsex.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //设置菜单显示的位置
        PopupWindowsex.showAtLocation(parent, Gravity.BOTTOM, 4, 0);

        //监听菜单的关闭事件
        PopupWindowsex.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        //监听触屏事件
        PopupWindowsex.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {

                return false;
            }
        });
    }

    private void initPop(View view) {
        TextView priceall = (TextView) view.findViewById(R.id.priceall);
        TextView price_mul = (TextView) view.findViewById(R.id.price_mul);
        TextView price = (TextView) view.findViewById(R.id.price);
        Button cancel = (Button) view.findViewById(R.id.cancel);
        Button commit = (Button) view.findViewById(R.id.commit);

        StrUtil.setText(priceall, "价值总计：" + Price_all + "");
        double mul = BigDecimalUtil.mul(Price_all, 0.1, 2);
        StrUtil.setText(price_mul, "平台扣除：" + Price_all + "*0.1=" + mul);
        double add = BigDecimalUtil.add(Price_all, mul, 2);
        StrUtil.setText(price, "实际收入：" + add);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PopupWindowsex.isShowing()) {
                    PopupWindowsex.dismiss();
                }
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PopupWindowsex.isShowing()) {
                    PopupWindowsex.dismiss();
                }
                sellGoods();
            }
        });
    }


    private void sellGoods() {
        String aGoodsOut = goodsPutAdapter.getAGoodsOut();
        Map<String, String> parameter = new HashMap<>();
        parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
        parameter.put("goods", aGoodsOut);
        DialogUtil.startDialogLoading(context);
        OkHttpUtils
                .post()
                .url(MYAppconfig.SALE)
                .params(parameter)
                .tag(context)
                .build()
                .execute(new StringCallback() {

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.showShort(context, e.getMessage());
                        DialogUtil.stopDialogLoading(context);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        response = UnicodeUtils.decodeUnicode(response);
                        DialogUtil.stopDialogLoading(context);
                        UserInfoModel model = JSON.parseObject(response, UserInfoModel.class);
                        if ("200".equals(model.getCode())) {
                            ToastUtil.showShort(context, model.getData().getCode_message());
                            Intent intent = new Intent();
                            setResult(1000, intent);
                            finish();
                        } else {
                            ToastUtil.showShort(context, model.getData().getCode_message());
                        }
                    }
                });
    }

}
