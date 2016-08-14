package com.iloomo.securitycode;

import com.iloomo.bean.SMSRegister;

public interface SecurityCodeCallBack {
	void onTitmerCallBack(String str, String phonenumber);
	void onTitmerOverCallBack();
	void onNetErrorCallBack(String str, int po);
	void onSecurityCodeCallBack(boolean blean);
	void onSecurityCodeCallBack(boolean blean, Object userRegister);
	void onSendSecurityCodeCallBack();
	void onStartNet();
}
