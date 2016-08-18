package cn.lfyun.wx.form;

public class Response {

	private String message;
    private String code;
    private Object data;

    public Response failure(String msg){
        this.message = msg;
        this.code = "false";
        return this;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
