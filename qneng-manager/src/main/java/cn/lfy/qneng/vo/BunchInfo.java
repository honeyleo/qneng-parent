package cn.lfy.qneng.vo;

public class BunchInfo {

	/**
	 * 警告数量
	 */
	private Integer alarmNumber;
	/**
	 * 当前功率
	 */
	private Double curPower;
	/**
	 * 当前电压
	 */
	private Double curVlot;
	/**
	 * 当前电流
	 */
	private Double curCurr;
	/**
	 * 当前温度
	 */
	private Double curTemp;
	public Integer getAlarmNumber() {
		return alarmNumber;
	}
	public void setAlarmNumber(Integer alarmNumber) {
		this.alarmNumber = alarmNumber;
	}
	public Double getCurPower() {
		return curPower;
	}
	public void setCurPower(Double curPower) {
		this.curPower = curPower;
	}
	public Double getCurVlot() {
		return curVlot;
	}
	public void setCurVlot(Double curVlot) {
		this.curVlot = curVlot;
	}
	public Double getCurCurr() {
		return curCurr;
	}
	public void setCurCurr(Double curCurr) {
		this.curCurr = curCurr;
	}
	public Double getCurTemp() {
		return curTemp;
	}
	public void setCurTemp(Double curTemp) {
		this.curTemp = curTemp;
	}
	
	
}
