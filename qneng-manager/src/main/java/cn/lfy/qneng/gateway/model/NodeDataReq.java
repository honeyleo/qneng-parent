package cn.lfy.qneng.gateway.model;

public class NodeDataReq {

	private String no;
	/**
	 * 输入电压
	 */
	private Double inputVolt;
	/**
	 * 输出电流
	 */
	private Double outvolt;
	/**
	 * 温度
	 */
	private Double temp;
	/**
	 * 发电量
	 */
	private Double capacity;
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
	public Double getInputVolt() {
		return inputVolt;
	}
	public void setInputVolt(Double inputVolt) {
		this.inputVolt = inputVolt;
	}
	public Double getOutvolt() {
		return outvolt;
	}
	public void setOutvolt(Double outvolt) {
		this.outvolt = outvolt;
	}
	public Double getTemp() {
		return temp;
	}
	public void setTemp(Double temp) {
		this.temp = temp;
	}
	public Double getCapacity() {
		return capacity;
	}
	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "NodeDataReq [no=" + no + ", inputVolt=" + inputVolt
				+ ", outvolt=" + outvolt + ", temp=" + temp + ", capacity="
				+ capacity + ", time=" + time + "]";
	}
	
	
}
