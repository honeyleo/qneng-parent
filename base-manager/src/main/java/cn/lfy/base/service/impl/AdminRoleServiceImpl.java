package cn.lfy.base.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;

import cn.lfy.base.dao.AdminRoleDAO;
import cn.lfy.base.dao.RoleDAO;
import cn.lfy.base.model.AdminRole;
import cn.lfy.base.model.Role;
import cn.lfy.base.service.AdminRoleService;
import cn.lfy.common.framework.exception.ApplicationException;
import cn.lfy.common.framework.exception.ErrorCode;

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

    public void saveRoles(Long userId, List<Long> roleIds) {
    	List<Role> roles = roleDAO.getRoles(roleIds);
    	if(roles.size() != roleIds.size()) {
    		throw ApplicationException.newInstance(ErrorCode.PARAM_ILLEGAL, "roleIds");
    	}
    	Set<Long> nowRoleIds = Sets.newHashSet(roleIds);
    	List<Role> curUserRoles = getRoleListByAdminId(userId);
    	Set<Long> curUserRolesSet = Sets.newHashSet();
    	for(Role role : curUserRoles) {
    		curUserRolesSet.add(role.getId());
    	}
    	Set<Long> delSet = Sets.difference(curUserRolesSet, nowRoleIds);
		Set<Long> newSet = Sets.difference(nowRoleIds, curUserRolesSet);
		for(Long delRoleId : delSet) {
			adminRoleDAO.delete(userId, delRoleId);
		}
		for(Long roleId : newSet) {
			this.add(userId, roleId);
		}
    }

}
