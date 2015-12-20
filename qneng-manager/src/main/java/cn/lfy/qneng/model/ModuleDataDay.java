package cn.lfy.qneng.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 发电数据统计：天
 * 
 * @author honeyleo
 * 
 */
public class ModuleDataDay implements Serializable {

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
	 * 发电量
	 */
	private Double capacity;
	
	private Date date;
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
	public Double getCapacity() {
		return capacity;
	}
	public void setCapacity(Double capacity) {
		this.capacity = capacity;
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
