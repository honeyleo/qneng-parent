package com.manager.model;

import java.io.Serializable;
import java.util.Date;

public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 登录名
     */
    private String username;

    /**
     * 登录密码，保存md5值
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 公司的个人邮箱，邮件提醒功能
     */
    private String email;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 数据状态
     */
    private Integer state;

    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 对应的客户ID
     */
    private Long operatorId;

    /**
     * @return 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id 
	 *            主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return 登录名
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username 
	 *            登录名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return 登录密码，保存md5值
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password 
	 *            登录密码，保存md5值
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return 真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * @param realname 
	 *            真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * @return 公司的个人邮箱，邮件提醒功能
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email 
	 *            公司的个人邮箱，邮件提醒功能
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return 联系电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone 
	 *            联系电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address 
	 *            地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return 角色ID
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * @param roleid 
	 *            角色ID
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * @return 数据状态
     */
    public Integer getState() {
        return state;
    }

    /**
     * @param state 
	 *            数据状态
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * @return 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createtime 
	 *            创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Long getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Long operatorId) {
		this.operatorId = operatorId;
	}
    
    
}
