package com.cityant.main.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.cityant.main.R;
import com.cityant.main.activity.demand.LocationActivity;
import com.cityant.main.bean.TagList;
import com.cityant.main.global.MYApplication;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.base.ActivitySupport;
import com.iloomo.bean.BaseModel;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.DateUtil;
import com.iloomo.utils.DialogUtil;
import com.iloomo.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lvfl on 2016/8/17.
 */
public class CreateDemandActivity extends ActivitySupport implements ThreadCallBack,View.OnClickListener {

    private static final int GO_FOR_BAIDU_MAP = 1;
    private Button commit_btn;
    private TimePickerView birthPicker;
    private List<TagList.Data.Tag_List>  tag_lists = new ArrayList<>();
    private EditText demand_title_edit;
    private EditText demand_details_edit;
    private TextView time_edit;
    private TextView man_text;
    private TextView woman_text;
    private TextView ry_text;
    private TextView online_text;
    private TextView line_text;
    private TextView address_text;
    private EditText address_details_edit;
    private TextView one_day_text;
    private TextView two_day_text;
    private TextView three_day_text;
    private TextView four_day_text;
    private EditText places_edit;
    private EditText money_edit;
    private EditText company_edit;
    private TextView total_text;
    private ImageView demand_details_image;
    private String sex_id;
    private String need_way;
    private String end_time;
    private PopupWindow popWindow;
    private String tag_id;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CreateDemandActivity.class);
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
        initBirthPicker();
        demand_details_image = (ImageView) findViewById(R.id.demand_details_image);
        demand_title_edit = (EditText) findViewById(R.id.demand_title_edit);
        demand_details_edit = (EditText) findViewById(R.id.demand_details_edit);
        time_edit = (TextView) findViewById(R.id.time_edit);

        man_text = (TextView) findViewById(R.id.man_text);
        woman_text = (TextView) findViewById(R.id.woman_text);
        ry_text = (TextView) findViewById(R.id.ry_text);

        online_text = (TextView) findViewById(R.id.online_text);
        line_text = (TextView) findViewById(R.id.line_text);

        address_text = (TextView) findViewById(R.id.address_text);
        address_details_edit = (EditText) findViewById(R.id.address_details_edit);

        one_day_text = (TextView) findViewById(R.id.one_day_text);
        two_day_text = (TextView) findViewById(R.id.two_day_text);
        three_day_text = (TextView) findViewById(R.id.three_day_text);
        four_day_text = (TextView) findViewById(R.id.four_day_text);

        places_edit = (EditText) findViewById(R.id.places_edit);
        money_edit = (EditText) findViewById(R.id.money_edit);
        company_edit = (EditText) findViewById(R.id.company_edit);

        total_text = (TextView) findViewById(R.id.total_text);

        commit_btn = (Button) findViewById(R.id.commit_btn);
        demand_details_image.setOnClickListener(this);
        time_edit.setOnClickListener(this);
        man_text.setOnClickListener(this);
        woman_text.setOnClickListener(this);
        ry_text.setOnClickListener(this);
        online_text.setOnClickListener(this);
        line_text.setOnClickListener(this);
        address_text.setOnClickListener(this);
        one_day_text.setOnClickListener(this);
        two_day_text.setOnClickListener(this);
        three_day_text.setOnClickListener(this);
        four_day_text.setOnClickListener(this);


        birthPicker.setOnTimeSelectListener(data -> {
            time_edit.setText(DateUtil.date2yyyyMMdd(data));
        });
        commit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(demand_title_edit.getText())){
                    ToastUtil.show(CreateDemandActivity.this,"请输入需求标题",ToastUtil.SHOW_TOAST);
                    return;
                }
                if(TextUtils.isEmpty(demand_details_edit.getText())){
                    ToastUtil.show(CreateDemandActivity.this,"请输入需求详情",ToastUtil.SHOW_TOAST);
                    return;
                }
                if(TextUtils.isEmpty(time_edit.getText())){
                    ToastUtil.show(CreateDemandActivity.this,"请选择时间",ToastUtil.SHOW_TOAST);
                    return;
                }
                if(TextUtils.isEmpty(sex_id)){
                    ToastUtil.show(CreateDemandActivity.this,"请选择性别",ToastUtil.SHOW_TOAST);
                    return;
                }
                if(TextUtils.isEmpty(need_way)){
                    ToastUtil.show(CreateDemandActivity.this,"请选择形式",ToastUtil.SHOW_TOAST);
                    return;
                }
                if(TextUtils.isEmpty(address_text.getText())){
                    ToastUtil.show(CreateDemandActivity.this,"请输入地址",ToastUtil.SHOW_TOAST);
                    return;
                }
                if(TextUtils.isEmpty(address_details_edit.getText())){
                    ToastUtil.show(CreateDemandActivity.this,"请输入详情地址",ToastUtil.SHOW_TOAST);
                    return;
                }
                if(TextUtils.isEmpty(end_time)){
                    ToastUtil.show(CreateDemandActivity.this,"请选择需求时间",ToastUtil.SHOW_TOAST);
                    return;
                }
                if(TextUtils.isEmpty(places_edit.getText())){
                    ToastUtil.show(CreateDemandActivity.this,"请输入名额",ToastUtil.SHOW_TOAST);
                    return;
                }
                if(TextUtils.isEmpty(money_edit.getText())){
                    ToastUtil.show(CreateDemandActivity.this,"请输入金额",ToastUtil.SHOW_TOAST);
                    return;
                }
                if(TextUtils.isEmpty(company_edit.getText())){
                    ToastUtil.show(CreateDemandActivity.this,"请输入单位",ToastUtil.SHOW_TOAST);
                    return;
                }
                if(TextUtils.isEmpty(tag_id)){
                    ToastUtil.show(CreateDemandActivity.this,"请选择标签",ToastUtil.SHOW_TOAST);
                    return;
                }
                DialogUtil.startDialogLoading(context);
                Map<String, Object> parameter = new HashMap<>();
                parameter.put("token", MYAppconfig.loginUserInfoData.getToken());
                parameter.put("latitude", MYApplication.getInstance().latitude+"");
                parameter.put("longitude", MYApplication.getInstance().longitude+"");
                parameter.put("city_name", "北京");
                parameter.put("pay_type", "0");
                parameter.put("need_way", need_way);
                parameter.put("need_title", demand_title_edit.getText().toString());
                parameter.put("tag_name", tag_id);
                parameter.put("need_content", demand_details_edit.getText().toString());
                parameter.put("need_sex", sex_id);
                parameter.put("address", address_text.getText().toString()+address_details_edit.getText().toString());
                parameter.put("need_time", time_edit.getText().toString());
                parameter.put("end_time", end_time);
                parameter.put("pay_price", money_edit.getText().toString());
                parameter.put("pay_unit", company_edit.getText().toString());
                parameter.put("need_man", places_edit.getText().toString());
                startHttpRequst(MYAppconfig.CREATE_NEED, parameter
                        , MYTaskID.CREATE_NEED);
            }
        });
        Map<String, Object> parameter = new HashMap<>();
        new AsyncHttpPost(this,MYAppconfig.TAG_LIST,parameter,MYTaskID.TAG_LIST,TagList.class,context);
    }

    private void initBirthPicker() {
        birthPicker = new TimePickerView(this, TimePickerView.Type.ALL);
        Calendar calendar = Calendar.getInstance();
        birthPicker.setRange(calendar.get(Calendar.YEAR) - 99, calendar.get(Calendar.YEAR));
        birthPicker.setTime(new Date());
        birthPicker.setCyclic(false);
        birthPicker.setCancelable(true);
    }

    public void startHttpRequst(String url,
                                Map<String, Object> parameter, int resultCode) {


        AsyncHttpPost httpRequest = new AsyncHttpPost(this, url, parameter, resultCode,
                BaseModel.class, context);

    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {
        Log.e("-----chen gong -------",resultJson);
    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        if(MYTaskID.TAG_LIST == resultCode){
            TagList tagList = (TagList) modelClass;
            tag_lists.addAll(tagList.getData().getTag_list());
            initPopupWindow();
        } else if(MYTaskID.CREATE_NEED == resultCode){
            BaseModel baseModel = (BaseModel) modelClass;
            DialogUtil.stopDialogLoading(context);
            if ("200".equals(baseModel.getCode())){
                ToastUtil.show(this,"创建成功",ToastUtil.SHOW_TOAST);
                this.finish();
            } else {
                ToastUtil.show(this,"创建失败",ToastUtil.SHOW_TOAST);
            }
        }
        Log.e("-----chen gong -------",resultJson);
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {
        Log.e("-----shi bai  -------",resultJson);
    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {
        Log.e("-----shi bai -------",resultJson);
        if(MYTaskID.CREATE_NEED == resultCode){
            DialogUtil.stopDialogLoading(context);
            ToastUtil.show(this,"创建失败",ToastUtil.SHOW_TOAST);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.time_edit:
                birthPicker.show();
                break;
            case R.id.man_text:
                sex_id = "0";
                setSexSelect();
                man_text.setSelected(true);
                break;
            case R.id.woman_text:
                sex_id = "1";
                setSexSelect();
                woman_text.setSelected(true);
                break;
            case R.id.ry_text:
                sex_id = "2";
                setSexSelect();
                ry_text.setSelected(true);
                break;
            case R.id.online_text:
                need_way = "0";
                online_text.setSelected(true);
                if(line_text.isSelected()){
                    line_text.setSelected(false);
                }
                break;
            case R.id.line_text:
                need_way = "1";
                line_text.setSelected(true);
                if(online_text.isSelected()){
                    online_text.setSelected(false);
                }
                break;
            case R.id.one_day_text:
                end_time = "1";
                setDaySelect();
                one_day_text.setSelected(true);
                break;
            case R.id.two_day_text:
                end_time = "2";
                setDaySelect();
                two_day_text.setSelected(true);
                break;
            case R.id.three_day_text:
                end_time = "3";
                setDaySelect();
                three_day_text.setSelected(true);
                break;
            case R.id.four_day_text:
                end_time = "4";
                setDaySelect();
                four_day_text.setSelected(true);
                break;
            case R.id.demand_details_image:
                popWindow.showAsDropDown(view, 0, 15);
                break;
            case R.id.address_text:
                Intent intent = new Intent(this, LocationActivity.class);
                startActivityForResult(intent, GO_FOR_BAIDU_MAP);
                break;
        }
    }

    private void setSexSelect(){
        if (man_text.isSelected()){
            man_text.setSelected(false);
        }
        if (woman_text.isSelected()){
            woman_text.setSelected(false);
        }
        if (ry_text.isSelected()){
            ry_text.setSelected(false);
        }
    }

    private void initPopupWindow(){
        View viewp = LayoutInflater.from(this).inflate(R.layout.create_demand_popup_layout, null);
        ImageView close_image = (ImageView) viewp.findViewById(R.id.close_image);
        GridView gridView = (GridView) viewp.findViewById(R.id.gridView);
        TextView commit_text = (TextView) viewp.findViewById(R.id.commit_text);
        close_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWindow.dismiss();
            }
        });
        PopAdapter popAdapter = new PopAdapter();
        gridView.setAdapter(popAdapter);
        // 创建一个PopuWidow对象
        popWindow = new PopupWindow(viewp, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
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
    }

    private void setDaySelect(){
        if (one_day_text.isSelected()){
            one_day_text.setSelected(false);
        }
        if (two_day_text.isSelected()){
            two_day_text.setSelected(false);
        }
        if (three_day_text.isSelected()){
            three_day_text.setSelected(false);
        }
        if (four_day_text.isSelected()){
            four_day_text.setSelected(false);
        }
    }

    class PopAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return tag_lists.size();
        }

        @Override
        public Object getItem(int i) {
            return tag_lists.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            TagList.Data.Tag_List tag_list = (TagList.Data.Tag_List) getItem(i);
            ViewHolder viewHolder=null;
            if (convertView == null) {
                viewHolder=new ViewHolder();
                convertView= LayoutInflater.from(context).inflate(R.layout.popup_item_layout,null);
                viewHolder.demand_item_image = (ImageView) convertView.findViewById(R.id.demand_item_image);
                convertView.setTag(viewHolder);
            }else{
                viewHolder=(ViewHolder)convertView.getTag();
            }
            setImage(tag_list.getTag_id(),viewHolder.demand_item_image);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tag_id = tag_list.getTag_id();
                    popWindow.dismiss();
                    setImage(tag_list.getTag_id(),demand_details_image);
                }
            });

            return convertView;
        }
        private void setImage(String id,ImageView imageView){
            switch (id){
                case "1":
                    Glide.with(CreateDemandActivity.this).load(R.drawable.jiaoyou).into(imageView);
                    break;
                case "2":
                    Glide.with(CreateDemandActivity.this).load(R.drawable.sheji).into(imageView);
                    break;
                case "3":
                    Glide.with(CreateDemandActivity.this).load(R.drawable.wan).into(imageView);
                    break;
                case "4":
                    Glide.with(CreateDemandActivity.this).load(R.drawable.jiaoyou).into(imageView);
                    break;
                case "5":
                    Glide.with(CreateDemandActivity.this).load(R.drawable.jiaoyou).into(imageView);
                    break;
                case "6":
                    Glide.with(CreateDemandActivity.this).load(R.drawable.jiaoyou).into(imageView);
                    break;
            }
        }
    }


    class ViewHolder{
        ImageView demand_item_image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == GO_FOR_BAIDU_MAP
                && data != null && data.getExtras() != null) {
            address_text.setText("详细地址："+data.getStringExtra("DetailedAddress"));
        }
    }
}
