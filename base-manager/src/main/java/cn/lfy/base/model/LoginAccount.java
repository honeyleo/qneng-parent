package cn.lfy.base.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LoginAccount extends BaseEntity {
	
	private static final long serialVersionUID = 4049274926799482032L;

	private Admin user;
	
	private List<Role> roles = new ArrayList<Role>();
	
	private List<Long> roleIdList;
	
	private List<Menu> menus;

	private Set<String> uriSet;
	
	public Admin getUser() {
		return user;
	}

	public void setUser(Admin user) {
		this.user = user;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public List<Long> getRoleIdList() {
		if(roleIdList == null) {
			roleIdList = new ArrayList<Long>();
			for(Role role : roles) {
				roleIdList.add(role.getId());
			}
		}
		return roleIdList;
	}

	public Set<String> getUriSet() {
		return uriSet;
	}

	public void setUriSet(Set<String> uriSet) {
		this.uriSet = uriSet;
	}

	public void setRoleIdList(List<Long> roleIdList) {
		this.roleIdList = roleIdList;
	}
	

}
