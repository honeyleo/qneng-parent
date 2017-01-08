package cn.lfy.base.service;

import java.util.List;

import cn.lfy.base.model.User;
import cn.lfy.base.model.Criteria;
import cn.lfy.base.model.PageInfo;

public interface UserService {
    /**
     * 根据条件查询记录总数
     */
    int countByCriteria(Criteria criteria);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    Long add(User record);

    /**
     * 根据条件查询记录集
     */
    List<User> findListByCriteria(Criteria criteria);
    
    /**
     * 根据条件查询记录集
     */
    PageInfo<User> findListByCriteria(Criteria criteria, int pageNo, int pageSize);

    /**
     * 根据主键查询记录
     */
    User findById(Long id);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByIdSelective(User record);
    
    /**
     * 更加登录名查询
     * @param username
     * @return
     */
    User findByUsername(String username);
    
}