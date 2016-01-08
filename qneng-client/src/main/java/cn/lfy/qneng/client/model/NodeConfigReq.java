package cn.lfy.qneng.client.model;

public class NodeConfigReq {

	/**
	 * 组件唯一码
	 */
	private String no;
	
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
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
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
	@Override
	public String toString() {
		return "NodeConfigReq [no=" + no + ", name=" + name + ", model="
				+ model + ", manufactory=" + manufactory + ", installdate="
				+ installdate + ", maxVolt=" + maxVolt + ", maxCurr=" + maxCurr
				+ ", power=" + power + "]";
	}
	
	
}
