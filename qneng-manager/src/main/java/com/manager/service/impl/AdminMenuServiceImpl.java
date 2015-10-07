package com.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manager.dao.AdminMenuDAO;
import com.manager.model.Admin;
import com.manager.model.AdminMenu;
import com.manager.model.Menu;
import com.manager.service.AdminMenuService;

@Service
public class AdminMenuServiceImpl implements AdminMenuService
{
	@Autowired
	private AdminMenuDAO adminMenuDAO;

    @Override
    public List<Menu> getMeneListByAdminId(Long adminId) {
        return adminMenuDAO.selectMenuListByAdminId(adminId);
    }

    @Override
    public void add(Long adminId, Long menuId) {
        AdminMenu record=new AdminMenu();
        record.setAdminId(adminId);
        record.setMenuId(menuId);
        adminMenuDAO.insert(record);
    }

    @Override
    public void deleteByAdminId(Long adminId) {
        adminMenuDAO.deleteByAdminId(adminId);
    }

    @Override
    public void deleteByMenuId(Long menuId) {
        adminMenuDAO.deleteByMenuId(menuId);
    }

    @Override
    public void saveAdminMenus(Admin admin, List<Long> menuIds) {
        this.deleteByAdminId(admin.getId());
        for(Long menuId:menuIds){
            this.add(admin.getId(), menuId);
        }
    }

    @Override
    public void addAdminDefaultMenu(Long adminId, Long roleId) {
        adminMenuDAO.insertAdminDefaultMenu(adminId, roleId);        
    }
	
}
