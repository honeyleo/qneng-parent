package cn.lfy.common.framework.exception;

import java.util.HashMap;
import java.util.Map;

import cn.lfy.common.utils.Constants;

import com.alibaba.fastjson.JSON;

public class ServiceRuntimeException extends RuntimeException{

	private static final long serialVersionUID = 8530230165406080800L;

	private Integer code;
	
	private Integer statusCode;
	
	private String message;
	
	private Exception exception;
	
	public ServiceRuntimeException(){
		super();
	}
	
	public ServiceRuntimeException(Integer _code, Integer _statusCode, String _message){
		this.code = _code;
		this.statusCode = _statusCode;
		this.message = _message;
	}
	
	public ServiceRuntimeException(Integer _code, Integer _statusCode, String _message, Exception _exception){
		this.code = _code;
		this.statusCode = _statusCode;
		this.message = _message;
		this.exception = _exception;
	}
	
	
	public ServiceRuntimeException(Constants enums){
		this.code = enums.getCode();
		this.statusCode = enums.getStatusCode();
		this.message = enums.getMessage();
	}
	
	public ServiceRuntimeException(Constants enums, Exception _exception){
		this.code = enums.getCode();
		this.statusCode = enums.getStatusCode();
		this.message = enums.getMessage();
		this.exception = _exception;
	}
	
	public Map<String, Object> getExceptionMap(){
		Map<String, Object> eMap = new HashMap<String, Object>();
		eMap.put("code", this.code);
		eMap.put("statusCode", this.statusCode);
		eMap.put("message", this.message);
		return eMap;
	}
	
	public String toJSONString(){
		return JSON.toJSONString(getExceptionMap());
	}
	

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
