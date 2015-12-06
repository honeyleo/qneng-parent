package com.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.manager.dao.RoleDAO;
import com.manager.model.Criteria;
import com.manager.model.PageInfo;
import com.manager.model.Role;
import com.manager.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDAO roleDAO;
    
    @Override
    public int countByCriteria(Criteria criteria) {
        return roleDAO.countByExample(criteria);
    }

    @Override
    public int insert(Role record) {
        return roleDAO.insert(record);
    }

    @Override
    public List<Role> getByCriteria(Criteria criteria) {
        return roleDAO.selectByExample(criteria);
    }
    @Override
    public Role getById(Long id) {
        return roleDAO.selectByPrimaryKey(id);
    }
    
    @Cacheable(value = "commonCache", key = "'Role_id_' + #id")
    @Override
    public Role getByIdInCache(Long id) {
        return roleDAO.selectByPrimaryKey(id);
    }

    @Override
    public int updateByIdSelective(Role record) {
        return roleDAO.updateByPrimaryKeySelective(record);
    }


    @Override
    public PageInfo<Role> getByCriteria(Criteria criteria, int pageNo,
            int pageSize) {
        PageInfo<Role> res = new PageInfo<Role>(pageNo, pageSize);
        int total=this.countByCriteria(criteria);
        res.setTotal(total);
        
        criteria.setOffset(res.getOffset());
        criteria.setRows(pageSize);
        List<Role> list = this.getByCriteria(criteria);
        res.setData(list);
        return res;
    }

	@Override
	public int deleteByPrimaryKey(Long id) {
		return roleDAO.deleteByPrimaryKey(id);
	}

}
