package cn.lfy.qneng.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 组件
 * @author honeyleo
 *
 */
public class Module implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3660103175024147940L;
	
	private Long id;
	/**
	 * 组件唯一码
	 */
	private String no;
	/**
	 * 设备密钥
	 */
	private String appSecret;
	
	private String name;
	/**
	 * 型号
	 */
	private String model;
	/**
	 * 厂家
	 */
	private String manufactory;
	/**
	 * 安装日期
	 */
	private String installdate;
	
	/**
	 * 核定电压
	 */
	private Double maxVolt;
	
	/**
	 * 核定电流
	 */
	private Double maxCurr;
	/**
	 * 核定功率
	 */
	private Double power;

	/**
	 * 关联组串ID
	 */
	private Long BunchId;
	
	private Date createTime;
	
	private Long lastUpdateTime;
	/**
	 * 输入电压
	 */
	private Double inputVolt;
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
	/**
	 * 最后一次上传的发电量
	 */
	private Double lastCapacity = 0D;
	
	private boolean online;
	
	private boolean mock;
	
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

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getManufactory() {
		return manufactory;
	}

	public void setManufactory(String manufactory) {
		this.manufactory = manufactory;
	}

	public String getInstalldate() {
		return installdate;
	}

	public void setInstalldate(String installdate) {
		this.installdate = installdate;
	}

	public Double getMaxVolt() {
		return maxVolt;
	}

	public void setMaxVolt(Double maxVolt) {
		this.maxVolt = maxVolt;
	}

	public Double getMaxCurr() {
		return maxCurr;
	}

	public void setMaxCurr(Double maxCurr) {
		this.maxCurr = maxCurr;
	}

	public Double getPower() {
		return power;
	}

	public void setPower(Double power) {
		this.power = power;
	}

	public Long getBunchId() {
		return BunchId;
	}

	public void setBunchId(Long bunchId) {
		BunchId = bunchId;
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

	public Double getInputVolt() {
		return inputVolt;
	}

	public void setInputVolt(Double inputVolt) {
		this.inputVolt = inputVolt;
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

	public Double getLastCapacity() {
		return lastCapacity;
	}

	public void setLastCapacity(Double lastCapacity) {
		this.lastCapacity = lastCapacity;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public boolean isMock() {
		return mock;
	}

	public void setMock(boolean mock) {
		this.mock = mock;
	}

}
