package cn.lfy.base.dao;

import java.util.List;

import cn.lfy.base.model.AdminRole;
import cn.lfy.base.model.Role;

public interface AdminRoleDAO {

    /**
     * 根据内勤人员id获取菜单列表
     * @param adminId
     * @return
     */
    List<Role> getRoleListByAdminId(Long adminId);
    
    /**
     * 添加内勤人员菜单
     * @param record
     */
    void insert(AdminRole record);
    
    /**
     * 删除内勤人员ID的菜单 
     * @param adminId
     */
    void deleteByAdminId(Long  adminId);
    
    /**
     * 删除某菜单 
     * @param menuId
     */
    void deleteByRoleId(Long  roleId);
    
}