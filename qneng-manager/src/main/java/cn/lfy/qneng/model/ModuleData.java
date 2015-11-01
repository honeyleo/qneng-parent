package cn.lfy.qneng.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 发电数据
 * 
 * @author honeyleo
 * 
 */
public class ModuleData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -453010897951914998L;

	private Long id;
	/**
	 * 设备唯一码
	 */
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
	
	private Date createTime;
	/**
	 * 关联电站ID
	 */
	private Long stationId;
	/**
	 * 关联组串ID
	 */
	private Long bunchId;
	/**
	 * 关联组件ID
	 */
	private Long moduleId;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getStationId() {
		return stationId;
	}
	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
	public Long getBunchId() {
		return bunchId;
	}
	public void setBunchId(Long bunchId) {
		this.bunchId = bunchId;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}
	
	

}
