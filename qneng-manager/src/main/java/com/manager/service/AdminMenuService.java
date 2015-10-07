package com.manager.service;

import java.util.List;

import com.manager.model.Admin;
import com.manager.model.Menu;

public interface AdminMenuService
{
    /**
     * 根据内勤人员id获取菜单列表
     * @param adminId
     * @return
     */
    List<Menu> getMeneListByAdminId(Long adminId);
    
    /**
     * 添加内勤人员菜单
     * @param record
     */
    void add(Long adminId, Long menuId);
    
    /**
     * 保存管理的权限菜单
     * @param admin
     * @param menus
     */
    void saveAdminMenus(Admin admin, List<Long> menuIds);
    
    /**
     * 删除内勤人员ID的菜单 
     * @param adminId
     */
    void deleteByAdminId(Long  adminId);
    
    /**
     * 删除某菜单 
     * @param menuId
     */
    void deleteByMenuId(Long  menuId);

    /**
     * 根据角色添加权限
     * @param adminId
     * @param roleId
     */
    void addAdminDefaultMenu(Long adminId, Long roleId);
}
