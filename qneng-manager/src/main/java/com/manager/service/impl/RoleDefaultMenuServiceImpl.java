package com.manager.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.manager.dao.RoleDefaultMenuDAO;
import com.manager.model.Menu;
import com.manager.model.Role;
import com.manager.model.RoleDefaultMenu;
import com.manager.service.RoleDefaultMenuService;

@Service
public class RoleDefaultMenuServiceImpl implements RoleDefaultMenuService {

    @Autowired
    private RoleDefaultMenuDAO roleDefaultMenuDAO;
    
    @Override
    public List<Menu> getMenuListByRoleId(Long roleId) {
        return roleDefaultMenuDAO.selectMenuListByRoleId(roleId);
    }

    @Override
    public void add(RoleDefaultMenu record) {
        roleDefaultMenuDAO.insert(record);
    }

    @Override
    public void deleteByRoleId(Long roleId) {
        roleDefaultMenuDAO.deleteByRoleId(roleId);
    }

    @Override
    public void deleteByMenuId(Long menuId) {
        roleDefaultMenuDAO.deleteByMenuId(menuId);
    }

    @Override
    public void saveDefaultMenus(Role role, List<Long> menuIds) {
        this.deleteByRoleId(role.getId());
        RoleDefaultMenu record=new RoleDefaultMenu();
        record.setRoleId(role.getId());
        for(Long mid:menuIds){
            record.setMenuId(mid);
            this.add(record);
        }
    }
    
    public void saveMenus(Long roleId, Set<Long> nowMenu) {
    	List<Menu> roleMenus = getMenuListByRoleId(roleId);
		Set<Long> roleSet = Sets.newHashSet();
		for(Menu m : roleMenus) {
			roleSet.add(m.getId());
		}
		Set<Long> delSet = Sets.difference(roleSet, nowMenu);
		Set<Long> newSet = Sets.difference(nowMenu, roleSet);
		for(Long menuId : delSet) {
			roleDefaultMenuDAO.delete(roleId, menuId);
		}
		for(Long menuId : newSet) {
			RoleDefaultMenu record = new RoleDefaultMenu();
			record.setRoleId(roleId);
			record.setMenuId(menuId);
			roleDefaultMenuDAO.insert(record);
		}
		
    }

}
