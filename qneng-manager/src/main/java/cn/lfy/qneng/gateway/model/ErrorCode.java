package cn.lfy.qneng.gateway.model;

import cn.lfy.qneng.gateway.netty.message.AbstractPacket;

public class ErrorCode extends AbstractPacket {

	private String no;
	
	private int code;
	
	private String msg;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
