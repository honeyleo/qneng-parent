package cn.lfy.qneng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lfy.qneng.dao.ModuleDataDAO;
import cn.lfy.qneng.model.ModuleData;
import cn.lfy.qneng.service.ModuleDataService;

import com.manager.model.Criteria;
import com.manager.model.PageInfo;

@Service
public class ModuleDataServiceImpl implements ModuleDataService {

    @Autowired
    private ModuleDataDAO dao;
    
    @Override
    public int countByCriteria(Criteria criteria) {
        return dao.countByExample(criteria);
    }

    @Override
    public Long add(ModuleData record) {
    	dao.insert(record);
        return record.getId();
    }

    @Override
    public List<ModuleData> findListByCriteria(Criteria criteria) {
        return dao.selectByExample(criteria);
    }

    @Override
    public ModuleData findById(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<ModuleData> findListByCriteria(Criteria criteria, int pageNo, int pageSize) {
        PageInfo<ModuleData> res = new PageInfo<ModuleData>(pageNo, pageSize);
        int total=this.countByCriteria(criteria);
        res.setTotal(total);
        
        criteria.setOffset(res.getOffset());
        criteria.setRows(pageSize);
        List<ModuleData> list = this.findListByCriteria(criteria);
        res.setData(list);
        return res;
    }
    
}
