package cn.lfy.qneng.vo;

public class ModuleQuery {

	private Long stationId;
	
	private Long bunchId;
	
	private Long moduleId;
	
	private String startTime;
	
	private String endTime;

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
	
	
}
