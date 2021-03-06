package com.manager.model;

import java.util.List;

public class TreeNode {

	private long id;  
    private String name;  
    private long parentId;  
    private List<TreeNode> nodes;
    private boolean checked = false;
    
    public TreeNode(long id,String name, long parentId, boolean checked) {
    	this.id = id;
    	this.name = name;
    	this.parentId = parentId;
    	this.checked = checked;
    }
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public List<TreeNode> getNodes() {
		return nodes;
	}
	public void setNodes(List<TreeNode> nodes) {
		this.nodes = nodes;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
    
    
}
