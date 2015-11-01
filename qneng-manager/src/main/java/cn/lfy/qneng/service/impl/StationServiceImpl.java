package cn.lfy.qneng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import cn.lfy.qneng.dao.StationDAO;
import cn.lfy.qneng.model.Station;
import cn.lfy.qneng.service.StationService;

import com.manager.model.Criteria;
import com.manager.model.PageInfo;

@Service
public class StationServiceImpl implements StationService {

    @Autowired
    private StationDAO dao;
    
    @Override
    public int countByCriteria(Criteria criteria) {
        return dao.countByExample(criteria);
    }

    @Override
    public Long add(Station record) {
    	dao.insert(record);
        return record.getId();
    }

    @Override
    public List<Station> findListByCriteria(Criteria criteria) {
        return dao.selectByExample(criteria);
    }

    @Override
    public Station findById(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByIdSelective(Station record) {
        return dao.updateByPrimaryKeySelective(record);
    }

    @Override
    public PageInfo<Station> findListByCriteria(Criteria criteria, int pageNo, int pageSize) {
        PageInfo<Station> res = new PageInfo<Station>(pageNo, pageSize);
        int total=this.countByCriteria(criteria);
        res.setTotal(total);
        
        criteria.setOffset(res.getOffset());
        criteria.setRows(pageSize);
        List<Station> list = this.findListByCriteria(criteria);
        res.setData(list);
        return res;
    }
    
    @Cacheable(value = "commonCache", key = "'station_id_' + #id")
    @Override
    public Station getByIdInCache(Long id) {
        return dao.selectByPrimaryKey(id);
    }
    
}
