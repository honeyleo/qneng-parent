package cn.lfy.qneng.gateway.model;

import cn.lfy.qneng.gateway.netty.message.AbstractPacket;

public class NodeAuthResp extends AbstractPacket {

	private String no;
	
	private String auth;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
	
	
}
