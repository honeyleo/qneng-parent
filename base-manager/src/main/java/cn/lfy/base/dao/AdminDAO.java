package cn.lfy.base.dao;

import java.util.List;

import cn.lfy.base.model.Admin;
import cn.lfy.base.model.Criteria;

public interface AdminDAO {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(Criteria example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Admin record);

    /**
     * 根据条件查询记录集
     */
    List<Admin> selectByExample(Criteria example);

    /**
     * 根据主键查询记录
     */
    Admin selectByPrimaryKey(Long id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(Admin record);
    
    /**
     * 根据登录名查询，username唯一
     * @param username
     * @return
     */
    Admin selectByUsername(String username);

}