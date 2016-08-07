package cn.lfy.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.lfy.base.dao.AdminDAO;
import cn.lfy.base.model.Admin;
import cn.lfy.base.service.AdminService;
import cn.lfy.common.model.Criteria;
import cn.lfy.common.model.PageInfo;

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
    public PageInfo<Admin> findListByCriteria(Criteria criteria, int start, int limit) {
        PageInfo<Admin> res = new PageInfo<Admin>();
        int total=this.countByCriteria(criteria);
        res.setTotal(total);
        
        criteria.setOffset(start);
        criteria.setRows(limit);
        List<Admin> list = this.findListByCriteria(criteria);
        res.setData(list);
        return res;
    }
    
    @Cacheable(value = "commonCache", key = "'user_id_' + #id")
    @Override
    public Admin getByIdInCache(Long id) {
        return adminDAO.selectByPrimaryKey(id);
    }

	@Override
	public int deleteByPrimaryKey(Long id) {
		return adminDAO.deleteByPrimaryKey(id);
	}
    
}
