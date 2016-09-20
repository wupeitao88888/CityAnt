package com.cityant.main.bean;

import com.hyphenate.chat.EMMessage;

public class ChatMsgEntity {
    /*类型 0：时间，
     *1：接收地址，
     * 2：接收消息，
     * 3：接收图片，
     * 4：接收语音电话，
     * 5：接收语音，
     * 6：发送地址，
     * 7：发送消息，
     * 8，发送图片，
     * 9：发起语音电话，10：发送语音，11：接收蚂蚁豆，
     * 12：发送蚂蚁豆，13：接收小费，14：发送小费，
     * 15：接收礼物，16：发送礼物，17：接收双人抢，
     * 18：发送双人抢
     *
     */
    private int type;
    private String token;
    private String mobile;//手机号
    private String user_name;//名字
    private String user_avar;//头像
    private String message;//消息内容
    private String messageid;//消息id
    private String time;//时间
    private String street;//街道
    private String longitude;//经度
    private String latitude;//纬度
    private String duration;//聊天的时间长度
    private String imgurl;//图片的URL
    private int status;//消息状态 -1://未发送0://发送失败1://发送中2://已送达3://已发送
    private int voicestatus;//是否播放过
    private String voiceurl;//语音URL
    private String voicelenth;//语音的长度
    private boolean voiceplay;//是否增长播放

    public boolean isVoiceplay() {
        return voiceplay;
    }

    public void setVoiceplay(boolean voiceplay) {
        this.voiceplay = voiceplay;
    }

    public String getVoicelenth() {
        return voicelenth;
    }

    public void setVoicelenth(String voicelenth) {
        this.voicelenth = voicelenth;
    }

    public int getVoicestatus() {
        return voicestatus;
    }

    public void setVoicestatus(int voicestatus) {
        this.voicestatus = voicestatus;
    }

    public String getVoiceurl() {
        return voiceurl;
    }

    public void setVoiceurl(String voiceurl) {
        this.voiceurl = voiceurl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_avar() {
        return user_avar;
    }

    public void setUser_avar(String user_avar) {
        this.user_avar = user_avar;
    }
}
