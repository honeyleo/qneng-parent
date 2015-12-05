package cn.lfy.qneng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.lfy.qneng.dao.BunchDAO;
import cn.lfy.qneng.model.Bunch;
import cn.lfy.qneng.service.BunchService;

import com.manager.model.Criteria;
import com.manager.model.PageInfo;

@Service
public class BunchServiceImpl implements BunchService {

    @Autowired
    private BunchDAO dao;
    
    @Override
    public int countByCriteria(Criteria criteria) {
        return dao.countByExample(criteria);
    }

    @Override
    public Long add(Bunch record) {
    	dao.insert(record);
        return record.getId();
    }

    @Override
    public List<Bunch> findListByCriteria(Criteria criteria) {
        return dao.selectByExample(criteria);
    }

    @Override
    public Bunch findById(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByIdSelective(Bunch record) {
        return dao.updateByPrimaryKeySelective(record);
    }

    @Override
    public PageInfo<Bunch> findListByCriteria(Criteria criteria, int pageNo, int pageSize) {
        PageInfo<Bunch> res = new PageInfo<Bunch>(pageNo, pageSize);
        int total=this.countByCriteria(criteria);
        res.setTotal(total);
        
        criteria.setOffset(res.getOffset());
        criteria.setRows(pageSize);
        List<Bunch> list = this.findListByCriteria(criteria);
        res.setData(list);
        return res;
    }
    
    @Cacheable(value = "commonCache", key = "'bunch_id_' + #id")
    @Override
    public Bunch getByIdInCache(Long id) {
        return dao.selectByPrimaryKey(id);
    }

	@Override
	public int deleteByPrimaryKey(Long id) {
		return dao.deleteByPrimaryKey(id);
	}
    
}
