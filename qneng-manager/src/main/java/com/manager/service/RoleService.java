package com.manager.service;

import java.util.List;

import com.manager.model.Criteria;
import com.manager.model.PageInfo;
import com.manager.model.Role;

public interface RoleService {
    /**
     * 根据条件查询记录总数
     */
    int countByCriteria(Criteria criteria);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(Role record);

    /**
     * 根据条件查询记录集
     */
    List<Role> getByCriteria(Criteria criteria);
    
    PageInfo<Role> getByCriteria(Criteria criteria, int pageNo, int pageSize);

    /**
     * 根据主键查询记录
     */
    Role getById(Long id);
    
    /**
     * 根据主键查询记录
     */
    Role getByIdInCache(Long id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByIdSelective(Role record);
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);

}