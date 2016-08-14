package com.iloomo.securitycode;

public interface SecurityCodeCallBack {
	void onTitmerCallBack(String str, String phonenumber);
	void onTitmerOverCallBack();
	void onNetErrorCallBack(String str, int po);
	void onSecurityCodeCallBack(boolean blean);
	void onSendSecurityCodeCallBack();
	void onStartNet();
}
