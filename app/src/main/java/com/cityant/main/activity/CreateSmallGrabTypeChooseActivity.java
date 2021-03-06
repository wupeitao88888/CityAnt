package com.cityant.main.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.LevelListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.adapter.CreateSmallGrabTypeChooseAdapter;
import com.cityant.main.adapter.PopBrandListAdatpter;
import com.cityant.main.bean.BrandList;
import com.cityant.main.bean.BrandListModel;
import com.cityant.main.bean.ClassList;
import com.cityant.main.bean.ClassListModel;
import com.cityant.main.bean.GoodsList;
import com.cityant.main.bean.GoodsListModel;
import com.cityant.main.bean.GuessIndexModel;
import com.hyphenate.easeui.global.MYAppconfig;
import com.cityant.main.global.MYTaskID;
import com.iloomo.base.ActivitySupport;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.DialogUtil;
import com.iloomo.utils.L;
import com.iloomo.utils.StrUtil;
import com.iloomo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by wupeitao on 16/9/2.
 */
public class CreateSmallGrabTypeChooseActivity extends ActivitySupport implements AdapterView.OnClickListener, ThreadCallBack {
    private RelativeLayout class_type;
    private RelativeLayout price;
    private PopupWindow popWindow;
    private View views[];
    private ScrollView scrollView;
    private ScrollView shop_pager;
    private int scrllViewWidth = 0, scrollViewMiddle = 0;
    private TextView toolsTextViews[];
    private LinearLayout contents;
    private TextView type_text;
    private TextView price_text;
    private Button pingtaibtn;

    private int price_order = 0;//0:默认,1:从低到高,2:从高到低
    private String class_id = "";//分类id
    private String brand_id = "";//品牌id
    private String is_friend = "";//参与对象(0:所有人;1:好友)
    private String type;// (2:双人,5:五人,10:十人,100:百人,0:多人抢)

    private CreateSmallGrabTypeChooseAdapter createSmallGrabTypeChooseAdapter;
    private ListView listtype;
    private PopupWindow ptPopShow;

    private ListView brandlist;
    private PopupWindow popshow;
    private PopBrandListAdatpter popBrandListAdatpter;
    private PtrClassicFrameLayout mPtrFrame;
    private int page = 1;
    private List<GoodsList> goods_list = new ArrayList<>();

    public static List<Activity> aList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createsmallgrabtypechoose);
        setCtenterTitle(mString(R.string.choose));
        CreateSmallGrabTypeChooseActivity.aList.add(this);
        type = getIntent().getStringExtra("type");

        initView();
    }

    private void initView() {
        class_type = (RelativeLayout) findViewById(R.id.class_type);
        price = (RelativeLayout) findViewById(R.id.price);
        type_text = (TextView) findViewById(R.id.type_text);
        price_text = (TextView) findViewById(R.id.price_text);
        listtype = (ListView) findViewById(R.id.type);
        pingtaibtn = (Button) findViewById(R.id.pingtaibtn);

        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.rotate_header_grid_view_frame);

        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler2() {

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                getGoodsList(true);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getGoodsList(false);
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return super.checkCanDoLoadMore(frame, listtype, footer);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, listtype, header);
            }
        });
        // the following are default settings
        mPtrFrame.setResistance(1.7f); // you can also set foot and header separately
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(1000);  // you can also set foot and header separately
        // default is false
        mPtrFrame.setPullToRefresh(false);

        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);


        class_type.setOnClickListener(this);
        price.setOnClickListener(this);
        pingtaibtn.setOnClickListener(this);

        createSmallGrabTypeChooseAdapter = new CreateSmallGrabTypeChooseAdapter(context, goods_list);
        listtype.setAdapter(createSmallGrabTypeChooseAdapter);

        getType();
        showPrice();
        getBrandList();
        getGoodsList(false);
    }

    private void getGoodsList(boolean isLoadMore) {
        if (isLoadMore) {
            page++;
        } else {
            page = 1;
        }
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("page", page + "");
        parameter.put("page_size", "20");
        parameter.put("class_id", class_id + "");
        parameter.put("price_order", price_order + "");
        parameter.put("brand_id", brand_id);
        parameter.put("is_friend", "0");
        parameter.put("type", type + "");
        AsyncHttpPost httpRequest = new AsyncHttpPost(new ThreadCallBack() {
            @Override
            public void onCallbackFromThread(String resultJson, Object modelClass) {

            }

            @Override
            public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
                switch (resultCode) {
                    case MYTaskID.GOODSLIST:
                        GoodsListModel goodsListModel = (GoodsListModel) modelClass;

                        if (isLoadMore) {
                            if (goodsListModel.getData().getGoods_list().size() == 0) {
                                page--;
                            }
                            goods_list.addAll(goodsListModel.getData().getGoods_list());
                        } else {
                            goods_list.clear();
                            goods_list.addAll(goodsListModel.getData().getGoods_list());
                        }
                        createSmallGrabTypeChooseAdapter.notifyDataSetChanged();
                        mPtrFrame.refreshComplete();
                        break;
                }
            }

            @Override
            public void onCallbackFromThreadError(String resultJson, Object modelClass) {

            }

            @Override
            public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
                switch (resultCode) {
                    case MYTaskID.GOODSLIST:
                        GoodsListModel goodsListModel = (GoodsListModel) modelClass;
                        ToastUtil.showShort(context, goodsListModel.getData().getCode_message());
                        break;
                }
            }
        }, MYAppconfig.GOODSLIST, parameter, MYTaskID.GOODSLIST,
                GoodsListModel.class, context);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.class_type:
                if (popWindow != null) {
                    if (popWindow.isShowing()) {
                        popWindow.dismiss();
                    } else {
                        //设置菜单显示的位置
                        popWindow.showAsDropDown(view, 0, 15);
                    }
                } else {
                    ToastUtil.showShort(context, "暂无分类");
                }
                break;
            case R.id.price:
                if (popshow.isShowing()) {
                    popshow.dismiss();
                } else {
                    //设置菜单显示的位置
                    popshow.showAsDropDown(view, 0, 15);
                }
                break;
            case R.id.pingtaibtn:
                if (ptPopShow.isShowing()) {
                    ptPopShow.dismiss();
                } else {
                    //设置菜单显示的位置
                    ptPopShow.showAsDropDown(view, 0, 15);
                }
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void showPopup(ClassListModel classListModel) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewp = layoutInflater.inflate(R.layout.pop_createsmallgrabtypechoose, null);
        // 创建一个PopuWidow对象
        popWindow = new PopupWindow(viewp, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

        LinearLayout toolsLayout = (LinearLayout) viewp.findViewById(R.id.tools);
        scrollView = (ScrollView) viewp.findViewById(R.id.tools_scrlllview);

        shop_pager = (ScrollView) viewp.findViewById(R.id.shop_pager);
        contents = (LinearLayout) viewp.findViewById(R.id.contents);
        LinearLayout popall = (LinearLayout) viewp.findViewById(R.id.popall);

        popall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popWindow.isShowing()) {
                    popWindow.dismiss();
                }
            }
        });

        List<ClassList> class_list = classListModel.getData().getClass_list();
        toolsTextViews = new TextView[class_list.size()];
        views = new View[class_list.size()];

        for (int i = 0; i < class_list.size(); i++) {
            View itemview = LayoutInflater.from(context).inflate(R.layout.item_b_top_nav_layout, null);
            itemview.setId(i);
            itemview.setOnClickListener(new OnItemClickListener(i, class_list));
            TextView textView = (TextView) itemview.findViewById(R.id.text);
            textView.setText(class_list.get(i).getClass_name());
            View line = (View) itemview.findViewById(R.id.line);
            toolsTextViews[i] = textView;
            views[i] = line;
            TextView textViewid = (TextView) itemview.findViewById(R.id.textid);
            textViewid.setText(class_list.get(i).getClass_name());
            toolsLayout.addView(itemview);


        }
        if (class_list.size() > 0) {
            for (int y = 0; y < class_list.get(0).getChild_list().size(); y++) {
                View contentview = LayoutInflater.from(context).inflate(R.layout.item_b_top_con_layout, null);
                contentview.setId(y);
                contentview.setOnClickListener(new OnItemContentListener(0, class_list, y));
                TextView con = (TextView) contentview.findViewById(R.id.text);
                con.setText(class_list.get(0).getChild_list().get(y).getClass_name());

                TextView conid = (TextView) contentview.findViewById(R.id.textid);
                conid.setText(class_list.get(0).getChild_list().get(y).getClass_id());

                contents.addView(contentview);
            }
        }


        setColor(0);
        //popupwindow弹出时的动画		popWindow.setAnimationStyle(R.style.popupWindowAnimation);
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        popWindow.setFocusable(true);
        // 设置允许在外点击消失
        popWindow.setOutsideTouchable(false);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popWindow.setBackgroundDrawable(new LevelListDrawable());

        //软键盘不会挡着popupwindow
        popWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        //监听菜单的关闭事件
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        //监听触屏事件
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {

                return false;
            }
        });
    }


    private class OnItemClickListener implements View.OnClickListener {

        private int i;
        private List<ClassList> class_list;

        public OnItemClickListener(int i, List<ClassList> class_list) {
            this.i = i;
            this.class_list = class_list;
        }

        @Override
        public void onClick(View view) {
            setColor(i);
            contents.removeAllViews();
            for (int y = 0; y < class_list.get(i).getChild_list().size(); y++) {
                View contentview = LayoutInflater.from(context).inflate(R.layout.item_b_top_con_layout, null);
                contentview.setId(y);
                contentview.setOnClickListener(new OnItemContentListener(i, class_list, y));
                TextView con = (TextView) contentview.findViewById(R.id.text);
                con.setText(class_list.get(i).getChild_list().get(y).getClass_name());

                TextView conid = (TextView) contentview.findViewById(R.id.textid);
                conid.setText(class_list.get(i).getChild_list().get(y).getClass_id());

                contents.addView(contentview);
            }
        }
    }

    private class OnItemContentListener implements View.OnClickListener {
        private int i;
        private List<ClassList> class_list;
        private int y;

        public OnItemContentListener(int i, List<ClassList> class_list, int y) {
            this.i = i;
            this.class_list = class_list;
            this.y = y;
        }

        @Override
        public void onClick(View view) {
            StrUtil.setText(type_text, class_list.get(i).getChild_list().get(y).getClass_name().substring(0, 2));
            if (popWindow.isShowing()) {
                popWindow.dismiss();
            }
            class_id = class_list.get(i).getChild_list().get(y).getClass_id();
            getGoodsList(false);
        }
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void setColor(int color) {
        for (int i = 0; i < toolsTextViews.length; i++) {
            if (color == i) {
                toolsTextViews[i].setTextColor(ContextCompat.getColor(context, R.color.yellow));
                views[i].setBackgroundColor(ContextCompat.getColor(context, R.color.yellow));
            } else {
                toolsTextViews[i].setTextColor(ContextCompat.getColor(context, R.color.textcolorx));
                views[i].setBackgroundColor(ContextCompat.getColor(context, R.color.textl));
            }
        }
    }


    /**
     * 改变栏目位置
     *
     * @param clickPosition
     */
    private void changeTextLocation(int clickPosition) {

        int x = (views[clickPosition].getTop() - getScrollViewMiddle() + (getViewheight(views[clickPosition]) / 2));
        scrollView.smoothScrollTo(0, x);
    }

    /**
     * 返回scrollview的中间位置
     *
     * @return
     */
    private int getScrollViewMiddle() {
        if (scrollViewMiddle == 0)
            scrollViewMiddle = getScrollViewheight() / 2;
        return scrollViewMiddle;
    }

    /**
     * 返回ScrollView的宽度
     *
     * @return
     */
    private int getScrollViewheight() {
        if (scrllViewWidth == 0)
            scrllViewWidth = scrollView.getBottom() - scrollView.getTop();
        return scrllViewWidth;
    }

    /**
     * 返回view的宽度
     *
     * @param view
     * @return
     */
    private int getViewheight(View view) {
        return view.getBottom() - view.getTop();
    }

    /****
     * 获取类型
     */
    public void getType() {
        Map<String, Object> parameter = new HashMap<>();
        startHttpRequst("POST", MYAppconfig.CLASSLIST, parameter
                , MYTaskID.CLASSLIST);
    }

    public void startHttpRequst(String requestType, String url,
                                Map<String, Object> parameter, int resultCode) {
        AsyncHttpPost httpRequest;
        httpRequest = new AsyncHttpPost(this, url, parameter, resultCode,
                ClassListModel.class, context);

    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        switch (resultCode) {
            case MYTaskID.CLASSLIST:
                ClassListModel loginUserInfo = (ClassListModel) modelClass;
                showPopup(loginUserInfo);
                break;

            case MYTaskID.ROB_BRANDLIST:
                BrandListModel brandListModel = (BrandListModel) modelClass;
                showbrandList(brandListModel);
                break;
        }
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
        switch (resultCode) {
            case MYTaskID.CLASSLIST:
                ClassListModel loginUserInfo = (ClassListModel) modelClass;
                ToastUtil.showShort(context, loginUserInfo.getData().getCode_message());
                break;

            case MYTaskID.ROB_BRANDLIST:
                BrandListModel brandListModel = (BrandListModel) modelClass;
                ToastUtil.showShort(context, brandListModel.getData().getCode_message());
                break;
        }
    }


    private void showPrice() {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewp = layoutInflater.inflate(R.layout.pop_price, null);
        RelativeLayout low_height = (RelativeLayout) viewp.findViewById(R.id.low_height);
        RelativeLayout height_low = (RelativeLayout) viewp.findViewById(R.id.height_low);
        low_height.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popshow.dismiss();
                StrUtil.setText(price_text, "低~高");
                price_order = 1;
                getGoodsList(false);
            }
        });
        height_low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popshow.dismiss();
                StrUtil.setText(price_text, "高~低");
                price_order = 2;
                getGoodsList(false);
            }
        });
        // 创建一个PopuWidow对象
        popshow = new PopupWindow(viewp, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

        //popupwindow弹出时的动画		popWindow.setAnimationStyle(R.style.popupWindowAnimation);
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        popshow.setFocusable(true);
        // 设置允许在外点击消失
        popshow.setOutsideTouchable(false);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popshow.setBackgroundDrawable(new LevelListDrawable());

        //软键盘不会挡着popupwindow
        popshow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        //监听菜单的关闭事件
        popshow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        //监听触屏事件
        popshow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {

                return false;
            }
        });
    }

    private void getBrandList() {

        Map<String, Object> parameter = new HashMap<>();

        new AsyncHttpPost(this, MYAppconfig.ROB_BRANDLIST, parameter, MYTaskID.ROB_BRANDLIST,
                BrandListModel.class, context);
    }


    private void showbrandList(BrandListModel brandListModel) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewp = layoutInflater.inflate(R.layout.pop_ptpopbrandlist, null);

        brandlist = (ListView) viewp.findViewById(R.id.brandlist);
        popBrandListAdatpter = new PopBrandListAdatpter(context, brandListModel.getData().getBrand_list());
        brandlist.setAdapter(popBrandListAdatpter);
        brandlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BrandList brandList = brandListModel.getData().getBrand_list().get(position);
                pingtaibtn.setText(brandList.getBrand_name());
                brand_id = brandList.getBrand_id();
                if (ptPopShow.isShowing()) {
                    ptPopShow.dismiss();
                }
                getGoodsList(false);
            }
        });


        // 创建一个PopuWidow对象
        ptPopShow = new PopupWindow(viewp, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

        //popupwindow弹出时的动画		popWindow.setAnimationStyle(R.style.popupWindowAnimation);
        // 使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        ptPopShow.setFocusable(true);
        // 设置允许在外点击消失
        ptPopShow.setOutsideTouchable(false);
        // 设置背景，这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        ptPopShow.setBackgroundDrawable(new LevelListDrawable());

        //软键盘不会挡着popupwindow
        ptPopShow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        //监听菜单的关闭事件
        ptPopShow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        //监听触屏事件
        ptPopShow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {

                return false;
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
