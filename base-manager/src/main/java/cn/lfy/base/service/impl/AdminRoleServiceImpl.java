package cn.lfy.base.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lfy.base.dao.AdminRoleDAO;
import cn.lfy.base.dao.RoleDAO;
import cn.lfy.base.model.AdminRole;
import cn.lfy.base.model.Role;
import cn.lfy.base.service.AdminRoleService;
import cn.lfy.common.framework.exception.ApplicationException;
import cn.lfy.common.framework.exception.ErrorCode;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

@Service
public class AdminRoleServiceImpl implements AdminRoleService
{
	@Autowired
	private AdminRoleDAO adminRoleDAO;
	
	@Autowired
	private RoleDAO roleDAO;

    @Override
    public List<Role> getRoleListByAdminId(Long adminId) {
        return adminRoleDAO.getRoleListByAdminId(adminId);
    }

    @Override
    public void add(Long adminId, Long menuId) {
        AdminRole record=new AdminRole();
        record.setAdminId(adminId);
        record.setRoleId(menuId);
        adminRoleDAO.insert(record);
    }

    @Override
    public void deleteByAdminId(Long adminId) {
        adminRoleDAO.deleteByAdminId(adminId);
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        adminRoleDAO.deleteByRoleId(roleId);
    }

    public void saveRoles(Long userId, List<Long> roleIds, Set<Role> currentUserRoles) {
    	List<Role> roles = roleDAO.getRoles(roleIds);
    	if(roles.size() != roleIds.size()) {
    		throw ApplicationException.newInstance(ErrorCode.PARAM_ILLEGAL, "roleIds");
    	}
    	Map<Long, Role> userRoleMap = Maps.newHashMap();
    	//有效的角色ID
    	Set<Long> nowRoleIds = Sets.newHashSet();
    	for(Role role : roles) {
    		nowRoleIds.add(role.getId());
    		userRoleMap.put(role.getId(), role);
    	}
    	List<Role> userRoles = getRoleListByAdminId(userId);
    	Set<Long> userRolesSet = Sets.newHashSet();
    	
    	for(Role role : userRoles) {
    		userRolesSet.add(role.getId());
    		userRoleMap.put(role.getId(), role);
    	}
    	Set<Long> delSet = Sets.difference(userRolesSet, nowRoleIds);
		Set<Long> newSet = Sets.difference(nowRoleIds, userRolesSet);
		for(Long delRoleId : delSet) {
			Role role = userRoleMap.get(delRoleId);
			//判断操作者的用户的角色ID是否在该角色的父路径里
			if(!isCurrentUserRolesOfChild(role, currentUserRoles)) {
				throw ApplicationException.newInstance(ErrorCode.UNAUTHORIZED_OPERATE);
			}
		}
		for(Long roleId : newSet) {
			Role role = userRoleMap.get(roleId);
			if(!isCurrentUserRolesOfChild(role, currentUserRoles)) {
				throw ApplicationException.newInstance(ErrorCode.UNAUTHORIZED_OPERATE);
			}
		}
		for(Long delRoleId : delSet) {
			adminRoleDAO.delete(userId, delRoleId);
		}
		for(Long roleId : newSet) {
			this.add(userId, roleId);
		}
    }
    
    /**
     * 判断role是否是currentUserRoles（当前用户角色集合）的子孙
     * @param role
     * @param currentUserRoles
     * @return
     */
    private boolean isCurrentUserRolesOfChild(Role role, Set<Role> currentUserRoles) {
    	for(Role crole : currentUserRoles) {
    		int idx = role.getParentIdPath().indexOf(crole.getParentIdPath() + crole.getId() + "$");
    		if(idx >= 0) {
    			return true;
    		}
    	}
    	return false;
    }

}
