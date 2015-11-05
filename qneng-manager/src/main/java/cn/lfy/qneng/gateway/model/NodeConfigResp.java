package cn.lfy.qneng.gateway.model;

import cn.lfy.qneng.gateway.netty.message.AbstractPacket;

public class NodeConfigResp extends AbstractPacket {

	private String no;
	
	private String status;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
