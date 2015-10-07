package com.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
