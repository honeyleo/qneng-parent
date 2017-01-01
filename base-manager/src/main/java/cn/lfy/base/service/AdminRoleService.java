package cn.lfy.base.service;

import java.util.List;

import cn.lfy.base.model.Admin;
import cn.lfy.base.model.Role;

public interface AdminRoleService
{
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
    void add(Long adminId, Long menuId);
    
    /**
     * 保存管理的权限菜单
     * @param admin
     * @param roleIds
     */
    void saveAdminRoles(Admin admin, List<Long> roleIds);
    
    /**
     * 删除内勤人员ID的菜单 
     * @param adminId
     */
    void deleteByAdminId(Long  adminId);
    
    /**
     * 删除某菜单 
     * @param role
     */
    void deleteByRoleId(Long  roleId);

}
