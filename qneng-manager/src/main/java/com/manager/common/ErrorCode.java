package com.manager.common;

public enum ErrorCode {

	OK(200, "success"),
	ERROR(1, "error")
	;
	
	private int code;
	
	private String msg;
	
	private ErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
	
}
