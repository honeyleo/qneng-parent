package cn.lfy.qneng.gateway.netty.message;

import cn.lfy.qneng.gateway.netty.util.XmlUtil;

public abstract class AbstractPacket implements Packet {

	public String writeXml() {
		return XmlUtil.writeXmlDocument(this);
	}
}
