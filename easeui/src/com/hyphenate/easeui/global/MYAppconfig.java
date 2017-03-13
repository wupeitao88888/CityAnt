package com.hyphenate.easeui.global;

import com.hyphenate.easeui.bean.LoginUserInfoData;
import com.iloomo.global.AppConfig;

/**
 * Created by wupeitao on 16/8/14.
 */
public class MYAppconfig extends AppConfig {
    public static LoginUserInfoData loginUserInfoData;
    public static boolean isSpeakerOpened = true;//是否使用扬声器 true:是使用扬声器 false:使用听筒
    public static final String USERLOGIN = BASEURL + API + "user/login";
    public static final String HOME_INDEX = BASEURL + API + "home/index";
    public static final String HOME_SEARCH = BASEURL + API + "home/search";
    public static final String NEED_NEEDINFO = BASEURL + API + "need/needInfo";
    public static final String FREND_LIST = BASEURL + API + "friend/friendList";
    public static final String CROWD_LIST = BASEURL + API + "group/index";
    public static final String NEWFRENDS = BASEURL + API + "friend/newFriendList";
    public static final String HANDLEFRENDS = BASEURL + API + "friend/processFriend";
    public static final String NEEDLIST = BASEURL + API + "need/meNeedList";
    public static final String CITY_LIST = BASEURL + API + "home/cityList";
    public static final String CREATE_NEED = BASEURL + API + "need/createNeed";
    public static final String TAG_LIST = BASEURL + API + "need/tagList";
    public static final String CLASSLIST = BASEURL + API + "rob/classList";
    public static final String GOODSLIST = BASEURL + API + "rob/goodsList";
    public static final String ADDFRENDS = BASEURL + API + "friend/addFriend";
    public static final String TODAYINCOME = BASEURL + API + "userPrice/todayIncome";
    public static final String EVALUATELIST = BASEURL + API + "setting/evaluateList";
    public static final String FEEDBACK = BASEURL + API + "setting/feedback";
    public static final String USERINFO = BASEURL + API + "user/index";
    public static final String USERUPDATE = BASEURL + API + "user/update";
    public static final String STORE = BASEURL + API + "bank/index";
    public static final String SALE = BASEURL + API + "bank/sale";
    public static final String beanList = BASEURL + API + "bean/beanList";
    public static final String BEANSALE = BASEURL + API + "bean/sale";
    public static final String INCOME = BASEURL + API + "userPrice/todayIncome";
    public static final String GUESS_INDEX = BASEURL + API + "guess/index";
    public static final String GUESS_CREATE = BASEURL + API + "guess/create";
    public static final String ROB_BRANDLIST = BASEURL + API + "rob/brandList";
    public static final String TRIBE_BRAND_INDEX = BASEURL + API + "brand/index";

    public static final String ROB_CREATE = BASEURL + API + "rob/create";
    public static final String BRAND_INFO = BASEURL + API + "brand/info";// 品牌部落详情
    public static final String BRAND_ACTIVITY_LIST = BASEURL + API + "brand/activeList";// 品牌部落关注列表
    public static final String GROUP_ADD = BASEURL + API + "group/add";// 添加群聊
    public static final String GROUP_REMOVE = BASEURL + API + "group/remove";// 移除群聊
    public static final String ROB_MYROB = BASEURL + API + "rob/myRob";// 与我相关
    public static final String ROB_DETAILS_INFO = BASEURL + API + "rob/info";// 抢详情
    public static final String ROB_INDEX = BASEURL + API + "rob/index";// 抢首页

    public static final String ADDRESS_INDEX = BASEURL + API + "address/index";//地址列表
    public static final String ADDRESS_AREA = BASEURL + API + "address/areaList";//地址列表
    public static final String ADDRESS_ADD = BASEURL + API + "address/add";//地址添加
    public static final String ADDRESS_UPDATE = BASEURL + API + "address/update";//地址添加
    public static final String ADDRESS_DELETE = BASEURL + API + "address/delete";//地址删除
    public static final String ADDRESS_DEFAULT = BASEURL + API + "address/defaultInfo";//我的默认地址
    public static final String ADDRESS_SETDEFAULT = BASEURL + API + "address/setDefault";//设置默认地址
    public static final String USERPRICE_PAYPRICE= BASEURL + API + "userPrice/payPrice";//充值余额

}
