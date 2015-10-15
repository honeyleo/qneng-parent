package com.manager.common.exception;

import com.manager.common.ErrorCode;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = -834654235881227655L;

	private ErrorCode errorCode;

	private String[] messageParams;
	
	private String redirectUrl;
	
	private ApplicationException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
	public static ApplicationException newInstance(ErrorCode errorCode, String... messageParams) {
		ApplicationException exception = new ApplicationException(errorCode);
		exception.messageParams = messageParams;
		return exception;
	}
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public String[] getMessageParams() {
		return messageParams;
	}
	public ApplicationException redirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
		return this;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	
	public static void main(String[] args) {
		ApplicationException.newInstance(ErrorCode.OK);
		ApplicationException.newInstance(ErrorCode.ERROR, "http://baidu.com");
		System.out.println();
	}
	
}
