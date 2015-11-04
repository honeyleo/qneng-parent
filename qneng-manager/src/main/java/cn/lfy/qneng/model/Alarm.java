package cn.lfy.qneng.model;

import java.io.Serializable;
import java.util.Date;

public class Alarm implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 407471538319349202L;
	
	private Long id;
	
	/**
	 * 设备唯一标识
	 */
	private String no;
	/**
	 * 警告类型
	 */
	private Integer alarmType;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 发送时间
	 */
	private long time;
	
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Integer getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(Integer alarmType) {
		this.alarmType = alarmType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}
