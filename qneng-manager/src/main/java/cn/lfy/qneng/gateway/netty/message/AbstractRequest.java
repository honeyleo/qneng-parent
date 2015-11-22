package cn.lfy.qneng.gateway.netty.message;

import java.io.UnsupportedEncodingException;

import cn.lfy.qneng.gateway.LogUtil;
import cn.lfy.qneng.gateway.netty.util.XmlUtil;

public abstract class AbstractRequest implements Request {

	private byte[] data = new byte[0];
	
	protected AbstractRequest(byte[] data) {
		this.data = data;
	}
	public <T> T readXML(Class<T> cls) {
		try {
			String str = new String(data, "UTF-8");
			LogUtil.LOG.info("上报XML报文：{}", str);
			return XmlUtil.readXML(str, cls);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
