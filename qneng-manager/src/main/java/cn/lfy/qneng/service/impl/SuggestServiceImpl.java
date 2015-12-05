package cn.lfy.qneng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lfy.qneng.dao.SuggestDAO;
import cn.lfy.qneng.model.Suggest;
import cn.lfy.qneng.service.SuggestService;

import com.manager.model.Criteria;
import com.manager.model.PageInfo;

@Service
public class SuggestServiceImpl implements SuggestService {

    @Autowired
    private SuggestDAO dao;
    
    @Override
    public int countByCriteria(Criteria criteria) {
        return dao.countByExample(criteria);
    }

    @Override
    public Long add(Suggest record) {
    	dao.insert(record);
        return record.getId();
    }

    @Override
    public List<Suggest> findListByCriteria(Criteria criteria) {
        return dao.selectByExample(criteria);
    }

    @Override
    public Suggest findById(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByIdSelective(Suggest record) {
        return dao.updateByPrimaryKeySelective(record);
    }

    @Override
    public PageInfo<Suggest> findListByCriteria(Criteria criteria, int pageNo, int pageSize) {
        PageInfo<Suggest> res = new PageInfo<Suggest>(pageNo, pageSize);
        int total=this.countByCriteria(criteria);
        res.setTotal(total);
        
        criteria.setOffset(res.getOffset());
        criteria.setRows(pageSize);
        List<Suggest> list = this.findListByCriteria(criteria);
        res.setData(list);
        return res;
    }

	@Override
	public int deleteByPrimaryKey(Long id) {
		return dao.deleteByPrimaryKey(id);
	}

}
