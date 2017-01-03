package cn.lfy.base.model;

import java.util.Set;
import java.util.TreeSet;

public class LoginAccount extends BaseEntity {
	
	private static final long serialVersionUID = 4049274926799482032L;

	private Admin user;
	
	private Set<Role> roles = new TreeSet<Role>();
	
	private Set<Long> roleIds;
	
	private Set<String> uriSet;
	
	/**
	 * 登录账号信息关联的用户
	 * @return
	 */
	public Admin getUser() {
		return user;
	}
	
	public void setUser(Admin user) {
		this.user = user;
	}
	/**
	 * 登录账号信息关联的角色列表
	 * @return
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	/**
	 * 登录账号信息管理的角色ID列表
	 * @return
	 */
	public Set<Long> getRoleIds() {
		if(roleIds == null) {
			roleIds = new TreeSet<Long>();
			for(Role role : roles) {
				roleIds.add(role.getId());
			}
		}
		return roleIds;
	}

	public Set<String> getUriSet() {
		return uriSet;
	}

	public void setUriSet(Set<String> uriSet) {
		this.uriSet = uriSet;
	}

	public void setRoleIds(Set<Long> roleIds) {
		this.roleIds = roleIds;
	}
	

}
