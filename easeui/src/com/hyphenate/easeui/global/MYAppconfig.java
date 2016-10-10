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
    public static final String CROWD_LIST = BASEURL + API + "friend/groupList";
    public static final String NEWFRENDS = BASEURL + API + "friend/newFriendList";
    public static final String HANDLEFRENDS = BASEURL + API + "friend/processFriend";
    public static final String NEEDLIST = BASEURL + API + "need/meNeedList";
    public static final String CITY_LIST = BASEURL + API + "home/cityList";
    public static final String CREATE_NEED = BASEURL + API + "need/createNeed";
    public static final String TAG_LIST = BASEURL + API + "need/tagList";
    public static final String CLASSLIST = BASEURL + API + "rob/classList";
    public static final String GOODSLIST = BASEURL + API + "rob/goodsList";
    public static final String ADDFRENDS = BASEURL + API + "friend/addFriend";
}
