package cn.lfy.qneng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lfy.qneng.dao.ModuleDAO;
import cn.lfy.qneng.model.Module;
import cn.lfy.qneng.service.ModuleService;

import com.manager.model.Criteria;
import com.manager.model.PageInfo;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleDAO dao;
    
    @Override
    public int countByCriteria(Criteria criteria) {
        return dao.countByExample(criteria);
    }

    @Override
    public Long add(Module record) {
    	dao.insert(record);
        return record.getId();
    }

    @Override
    public List<Module> findListByCriteria(Criteria criteria) {
        return dao.selectByExample(criteria);
    }

    @Override
    public Module findById(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByIdSelective(Module record) {
        return dao.updateByPrimaryKeySelective(record);
    }

    @Override
    public PageInfo<Module> findListByCriteria(Criteria criteria, int pageNo, int pageSize) {
        PageInfo<Module> res = new PageInfo<Module>(pageNo, pageSize);
        int total=this.countByCriteria(criteria);
        res.setTotal(total);
        
        criteria.setOffset(res.getOffset());
        criteria.setRows(pageSize);
        List<Module> list = this.findListByCriteria(criteria);
        res.setData(list);
        return res;
    }

	@Override
	public Module findByNo(String no) {
		return dao.selectByNo(no);
	}
    
}
