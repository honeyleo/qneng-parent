package cn.lfy.qneng.client.model;

import cn.lfy.qneng.client.message.AbstractPacket;

public class NodeAlarmResp extends AbstractPacket {

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
