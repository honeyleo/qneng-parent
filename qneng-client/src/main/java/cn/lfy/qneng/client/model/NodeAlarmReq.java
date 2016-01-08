package cn.lfy.qneng.client.model;

public class NodeAlarmReq {

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
	private Long time;
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
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "NodeAlarmReq [no=" + no + ", alarmType=" + alarmType
				+ ", memo=" + memo + ", time=" + time + "]";
	}
	
}
