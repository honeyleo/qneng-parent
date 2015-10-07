package com.manager.dao;

import java.util.List;

import com.manager.model.Menu;
import com.manager.model.RoleDefaultMenu;

public interface RoleDefaultMenuDAO {
    /**
     * 根据角色查询默认的菜单列表
     * @param operatorId
     * @return
     */
    List<Menu> selectMenuListByRoleId(Long roleId);
    
    /**
     * 添加角色默认菜单
     * @param record
     */
    void insert(RoleDefaultMenu record);
    
    /**
     * 删除角色的菜单 
     * @param operatorId
     */
    void deleteByRoleId(Long roleId);
    
    /**
     * 删除某菜单 
     * @param operatorId
     */
    void deleteByMenuId(Long menuId);
}