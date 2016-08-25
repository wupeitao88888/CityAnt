package com.cityant.main.global;

import com.cityant.main.bean.LoginUserInfoData;
import com.iloomo.global.AppConfig;

/**
 * Created by wupeitao on 16/8/14.
 */
public class MYAppconfig extends AppConfig  {
    public static LoginUserInfoData loginUserInfoData;

    public static final String USERLOGIN=BASEURL+API+"/user/login";
    public static final String HOME_INDEX=BASEURL+API+"/home/index";
    public static final String HOME_SEARCH=BASEURL+API+"/home/search";
    public static final String NEED_NEEDINFO=BASEURL+API+"/need/needInfo";
    public static final String FREND_LIST = BASEURL + API + "chats/friendList";

}
