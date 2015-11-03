package cn.lfy.common.model;

import java.util.HashMap;
import java.util.Map;

public class Message extends HashMap<String,Object> {

	private static final long serialVersionUID = -8476898437116996416L;

	public static Builder newBuilder() {
		return new Builder();
	}
	
	private Message(Builder builder) {
		this.put("ret", builder.ret);
		this.put("msg", builder.msg);
		this.putAll(builder.data);
	}
	public static class Builder {
		
		private int ret;
		
		private String msg;
		
		private Map<String,Object> data = new HashMap<String, Object>();
		
		public Builder() {
		}
		
		public Builder setRet(int ret) {
			this.ret = ret;
			return this;
		}

		public Builder setMsg(String msg) {
			this.msg = msg;
			return this;
		}
		
		public Builder put(String key, Object value) {
			data.put(key, value);
			return this;
		}
		public Message build() {
			return new Message(this);
		}
	}
	
}
