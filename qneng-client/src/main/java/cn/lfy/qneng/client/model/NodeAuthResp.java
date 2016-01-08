package cn.lfy.qneng.client.model;

import cn.lfy.qneng.client.message.AbstractPacket;

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
