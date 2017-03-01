package com.cityant.main.activity.knock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.cityant.main.R;
import com.cityant.main.adapter.MyFragmentPagerAdapter;
import com.cityant.main.base.BaseToolbarActivity;
import com.cityant.main.bean.konck.RobDetails;
import com.cityant.main.fragment.konck.GoodsDetailsFragment;
import com.cityant.main.fragment.konck.ParticipantFragment;
import com.cityant.main.global.MYTaskID;
import com.hyphenate.easeui.global.MYAppconfig;
import com.iloomo.net.AsyncHttpPost;
import com.iloomo.net.ThreadCallBack;
import com.iloomo.utils.PImageLoaderUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
* 小额抢详情
* @author lvfl
* @time 2016/12/4 17:14
*/
public class KonckDetailsActivity extends BaseToolbarActivity implements ThreadCallBack{

    @Bind(R.id.pagerStrip)
    protected TabLayout pagerStrip;
    @Bind(R.id.viewpager)
    protected ViewPager viewpager;
    @Bind(R.id.goods_image)
    protected ImageView goods_image;
    @Bind(R.id.money_goods_text)
    protected TextView money_goods_text;
    @Bind(R.id.konck_title_text)
    protected TextView konck_title_text;
    @Bind(R.id.goods_name)
    protected TextView goods_name;
    @Bind(R.id.fired_text)
    protected TextView fired_text;
    @Bind(R.id.need_people)
    protected TextView need_people;
    @Bind(R.id.poor_people)
    protected TextView poor_people;
    @Bind(R.id.over_time_text)
    protected TextView over_time_text;
    private String rob_id;
    private ArrayList<RobDetails.Data.userList> par_list;
    private ArrayList<String> goods_list;

    public static void startActivity(Context context,String rob_id){
        Intent intent = new Intent(context,KonckDetailsActivity.class);
        intent.putExtra("rob_id",rob_id);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_konck_details_layout;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        rob_id = getIntent().getStringExtra("rob_id");
        initToolbar();
        initFragmentPager(viewpager,pagerStrip);
    }

    private void initToolbar() {
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        mToolbar.setNavigationOnClickListener(v -> KonckDetailsActivity.this.finish());
        ActionBarHelper helper = createActionBarHelper();
        helper.init();
        helper.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("rob_id", rob_id);
        new AsyncHttpPost(this, MYAppconfig.ROB_DETAILS_INFO, parameter, MYTaskID.ROB_DETAILS_INFO_ID,
                RobDetails.class, this);
    }

    public void initFragmentPager(ViewPager viewPager, TabLayout pagerSlidingTabStrip) {
        final ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ParticipantFragment.newInstance(par_list));
        fragmentList.add(GoodsDetailsFragment.newInstance(goods_list));

        List<String> titleList = new ArrayList<>();
        titleList.add("参与人员");
        titleList.add("商品详情");
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList, titleList));
        pagerSlidingTabStrip.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onCallbackFromThread(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThread(String resultJson, int resultCode, Object modelClass) {
        if (resultCode == MYTaskID.ROB_DETAILS_INFO_ID){
            try {
                RobDetails robDetails = (RobDetails) modelClass;
                if ("200".equals(robDetails.getCode())){
                    if (null != robDetails.data){
                        PImageLoaderUtils.getInstance().displayIMG(robDetails.data.getGoods_img(),goods_image,this);
                        money_goods_text.setText(robDetails.data.getRob_price());
                        konck_title_text.setText(robDetails.data.getGoods_title());
    //                goods_name.setText(robDetails.data.get());
    //                fired_text.setText(robDetails.data.get());
                        need_people.setText("总需人数："+ robDetails.data.getGoods_title());
                        poor_people.setText(Html.fromHtml("<font color = #666666 >还需人数：</font><font color=#f4cc08>"+robDetails.data.getPoor_man()+"</font>"));
                        switch (robDetails.data.getState()){
                            case "1":
                                over_time_text.setText("进行中");
                                break;
                            case "2":
                                over_time_text.setText("已完结");
                                break;
                            case "3":
                                over_time_text.setText("已过期");
                                break;
                        }
                        par_list.addAll(robDetails.data.user_list);
                        goods_list.addAll(robDetails.data.getGoods_imgs());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCallbackFromThreadError(String resultJson, Object modelClass) {

    }

    @Override
    public void onCallBackFromThreadError(String resultJson, int resultCode, Object modelClass) {

    }
}
