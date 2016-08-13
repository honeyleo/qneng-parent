package cn.lfy.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.lfy.base.model.AdminMenu;
import cn.lfy.base.model.Menu;

public interface AdminMenuDAO {

    /**
     * 根据内勤人员id获取菜单列表
     * @param adminId
     * @return
     */
    List<Menu> selectMenuListByAdminId(Long adminId);
    
    /**
     * 添加内勤人员菜单
     * @param record
     */
    void insert(AdminMenu record);
    
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
    void insertAdminDefaultMenu(@Param("adminId") Long adminId, @Param("roleId") Long roleId);
}