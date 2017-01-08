package cn.lfy.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.lfy.base.model.UserRole;
import cn.lfy.base.model.Role;

public interface UserRoleDAO {

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
    void insert(UserRole record);
    
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
    
    /**
     * 删除某菜单 
     * @param adminId
     * @param roleId
     */
    void delete(@Param("adminId")Long adminId, @Param("roleId")Long roleId);
    
}