package cn.lfy.qneng.client.message;

import cn.lfy.qneng.util.XmlUtil;

public abstract class AbstractPacket implements Packet {

	public String writeXml() {
		return XmlUtil.writeXmlDocument(this);
	}
}
