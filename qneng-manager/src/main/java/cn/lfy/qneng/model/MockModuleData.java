package cn.lfy.qneng.model;

public class MockModuleData {

	private Long id;
	private String no;
	private String startTime;
	private String endTime;
	/**
	 * 输入电压
	 */
	private Double inputVolt;
	/**
	 * 输出电压
	 */
	private Double outvolt;
	/**
	 * 输出电流
	 */
	private Double curr;
	/**
	 * 温度
	 */
	private Double temp;
	/**
	 * 发电量
	 */
	private Double capacity;
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	public Double getCurr() {
		return curr;
	}
	public void setCurr(Double curr) {
		this.curr = curr;
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
	
}
