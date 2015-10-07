package com.manager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manager.dao.AdminDAO;
import com.manager.model.Admin;
import com.manager.model.Criteria;
import com.manager.model.PageInfo;
import com.manager.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDAO adminDAO;
    
    @Override
    public int countByCriteria(Criteria criteria) {
        return adminDAO.countByExample(criteria);
    }

    @Override
    public Long add(Admin record) {
        adminDAO.insert(record);
        return record.getId();
    }

    @Override
    public List<Admin> findListByCriteria(Criteria criteria) {
        return adminDAO.selectByExample(criteria);
    }

    @Override
    public Admin findById(Long id) {
        return adminDAO.selectByPrimaryKey(id);
    }

    @Override
    public int updateByIdSelective(Admin record) {
        return adminDAO.updateByPrimaryKeySelective(record);
    }

    @Override
    public Admin findByUsername(String username) {
        return adminDAO.selectByUsername(username);
    }

    @Override
    public PageInfo<Admin> findListByCriteria(Criteria criteria, int pageNo, int pageSize) {
        PageInfo<Admin> res = new PageInfo<Admin>(pageNo, pageSize);
        int total=this.countByCriteria(criteria);
        res.setTotal(total);
        
        criteria.setOffset(res.getOffset());
        criteria.setRows(pageSize);
        List<Admin> list = this.findListByCriteria(criteria);
        res.setData(list);
        return res;
    }
    
}
