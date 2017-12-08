package com.example.core.constants;

public enum ResultCodeMsg {
	SYSTEM_ERR(30001, "系统繁忙");
	//...
	
	private int errorCode;
	private String errorMsg;
	private ResultCodeMsg(int errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public static void main(String[] args) {
		System.out.println(ResultCodeMsg.SYSTEM_ERR.getErrorCode());
		System.out.println(ResultCodeMsg.SYSTEM_ERR.getErrorMsg());
	}
}
