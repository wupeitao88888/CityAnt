package com.iloomo.bean;

import java.io.Serializable;

public class BaseModel implements Serializable {
	private String message;
	private String code;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/***
	 *__________用于上传头像演示_______start
	 */
	private String result;
	private String resultCode;
	private String resultMsg;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	/***
	 *__________用于上传头像演示_______end
	 */
}
