package com.manager.service;

import java.util.List;

import com.manager.model.Admin;
import com.manager.model.Criteria;
import com.manager.model.PageInfo;

/**
 * 管理系统用户相关服务接口
 * @author liaopeng
 *
 */
public interface AdminService {
    /**
     * 根据条件查询记录总数
     */
    int countByCriteria(Criteria criteria);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    Long add(Admin record);

    /**
     * 根据条件查询记录集
     */
    List<Admin> findListByCriteria(Criteria criteria);
    
    /**
     * 根据条件查询记录集
     */
    PageInfo<Admin> findListByCriteria(Criteria criteria, int pageNo, int pageSize);

    /**
     * 根据主键查询记录
     */
    Admin findById(Long id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByIdSelective(Admin record);
    
    /**
     * 更加登录名查询
     * @param username
     * @return
     */
    Admin findByUsername(String username);
    /**
     * 缓存
     * @param id
     * @return
     */
    Admin getByIdInCache(Long id);
    
    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(Long id);
    
}