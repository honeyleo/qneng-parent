package cn.lfy.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lfy.base.dao.AdminRoleDAO;
import cn.lfy.base.model.Admin;
import cn.lfy.base.model.AdminRole;
import cn.lfy.base.model.Role;
import cn.lfy.base.service.AdminRoleService;

@Service
public class AdminRoleServiceImpl implements AdminRoleService
{
	@Autowired
	private AdminRoleDAO adminRoleDAO;

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

    @Override
    public void saveAdminRoles(Admin admin, List<Long> roleIds) {
        this.deleteByAdminId(admin.getId());
        for(Long roleId : roleIds){
            this.add(admin.getId(), roleId);
        }
    }

}
