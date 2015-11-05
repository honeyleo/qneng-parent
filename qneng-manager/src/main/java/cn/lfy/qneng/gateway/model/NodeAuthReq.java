package cn.lfy.qneng.gateway.model;


public class NodeAuthReq {

	private String no;
	
	private String key;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "NodeAuthReq [no=" + no + ", key=" + key + "]";
	}
	
	
	
}
