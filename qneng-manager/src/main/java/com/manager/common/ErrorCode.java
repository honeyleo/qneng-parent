package com.manager.common;

public enum ErrorCode {

	OK(0, "success"),
	ERROR(1000, "error"),
	EXIST(1001, "value.exist"),
	NOT_EXIST(1002, "value.not.exist"),
	PARAM_ILLEGAL(2000, "param.illegal"),
	PERMISSION_DENIED(1003, "permission.denied"),
	ERROR_PASSWORD(1004, "error.password"),
	ERROR_USER_INACTIVE(1005, "error.user.inactive"),
	SEC_ERROR(1006, "secCode.error"),
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
