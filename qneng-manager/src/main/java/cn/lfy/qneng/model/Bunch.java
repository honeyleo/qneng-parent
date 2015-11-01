package cn.lfy.qneng.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 组串
 * @author honeyleo
 *
 */
public class Bunch implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9106347791347285594L;
	
	private Long id;
	
	private String name;
	
	/**
	 * 单元数
	 */
	private Integer element;
	/**
	 * 行数
	 */
	private Integer line;
	/**
	 * 列数
	 */
	private Integer row;
	/**
	 * 关联电站ID
	 */
	private Long stationId;

	private Date createTime;
	
	private Long lastUpdateTime;

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

	public Integer getElement() {
		return element;
	}

	public void setElement(Integer element) {
		this.element = element;
	}

	public Integer getLine() {
		return line;
	}

	public void setLine(Integer line) {
		this.line = line;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
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
	
	
}
