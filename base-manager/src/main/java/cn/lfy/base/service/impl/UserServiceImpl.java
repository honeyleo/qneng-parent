package cn.lfy.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lfy.base.dao.UserDAO;
import cn.lfy.base.model.User;
import cn.lfy.base.model.Criteria;
import cn.lfy.base.model.PageInfo;
import cn.lfy.base.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;
    
    @Override
    public int countByCriteria(Criteria criteria) {
        return userDAO.countByExample(criteria);
    }

    @Override
    public Long add(User record) {
        userDAO.insert(record);
        return record.getId();
    }

    @Override
    public List<User> findListByCriteria(Criteria criteria) {
        return userDAO.selectByExample(criteria);
    }

    @Override
    public User findById(Long id) {
        return userDAO.selectByPrimaryKey(id);
    }

    @Override
    public int updateByIdSelective(User record) {
        return userDAO.updateByPrimaryKeySelective(record);
    }

    @Override
    public User findByUsername(String username) {
        return userDAO.selectByUsername(username);
    }

    @Override
    public PageInfo<User> findListByCriteria(Criteria criteria, int pageNo, int pageSize) {
        PageInfo<User> res = new PageInfo<User>(pageNo, pageSize);
        int total=this.countByCriteria(criteria);
        res.setTotal(total);
        
        criteria.setOffset(res.getOffset());
        criteria.setRows(pageSize);
        List<User> list = this.findListByCriteria(criteria);
        res.setData(list);
        return res;
    }
    
}
