package com.iloomo.global;

// TODO: Auto-generated Javadoc

/**
 *
 *
 * @author wpt
 * @version v1.0
 * @date：2014-07-03 下午1:33:39
 */
public class AppConfig {

    public static final String BASEURL = "http://123.56.182.208";
    public static final String API = "/mayi/api.php/";
    //微信订单下单地址
    public static final String GET_WEIXINPAY = BASEURL + API + "Weixin_payUnifiedorder";
    //获取验证码请求参数  String phonenumber,String vtype//应用到什么地方的类型比如是注册还是找回密码等等
    public static final String GET_CODE = BASEURL + API + "user/sendCode";
    // 校验验证码  String phonenumber,String vcode vcode是验证码校验验证码请求参数
    public static final String SEND_CODE = BASEURL + API + "user/register";
    //忘记密码
    public static final String FORGETPASSWOD = BASEURL + API + "user/forgetPassword";

    /** 开发者联系方式 **/
    public static String developEmail = "2859912441@qq.com";
    /**
     * UI设计的基准宽度.
     */
    public static int UI_WIDTH = 720;

    /**
     * UI设计的基准高度.
     */
    public static int UI_HEIGHT = 1080;

    /**
     * 默认 SharePreferences文件名.
     */
    public static String SHARED_PATH = "app_share";

    /**
     * 默认下载文件地址.
     */
    public static String DOWNLOAD_ROOT_DIR = "download";

    /**
     * 默认下载图片文件地址.
     */
    public static String DOWNLOAD_IMAGE_DIR = "images";

    /**
     * 默认下载文件地址.
     */
    public static String DOWNLOAD_FILE_DIR = "files";

    /**
     * APP缓存目录.
     */
    public static String CACHE_DIR = "cache";

    /**
     * DB目录.
     */
    public static String DB_DIR = "db";

    /**
     * 默认缓存超时时间设置.
     */
    public static int IMAGE_CACHE_EXPIRES_TIME = 3600 * 24 * 3;

    /**
     * 缓存大小  单位10M.
     */
    public static int MAX_CACHE_SIZE_INBYTES = 10 * 1024 * 1024;
    /**
     * Textview为空.
     */
    public static String TEXTVIEW_NULL = "TextView为空";
    /**
     * Text为空.
     */
    public static String TEXT_NULL = "Text为空";

}