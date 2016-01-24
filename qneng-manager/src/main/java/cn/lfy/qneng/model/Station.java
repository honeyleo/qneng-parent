package cn.lfy.qneng.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 电站实体类
 * @author honeyleo
 *
 */
public class Station implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -502713508809299920L;
	
	private Long id;
	
	/**
	 * 电站名称
	 */
	private String name;
	/**
	 * 电站简介
	 */
	private String info;
	
	private String address;
	/**
	 * 电站关联用户
	 */
	private Long userId;
	
	private Date createTime;
	
	private Long lastUpdateTime;
	
	private String province;
	
	private String city;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Long lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	

}
