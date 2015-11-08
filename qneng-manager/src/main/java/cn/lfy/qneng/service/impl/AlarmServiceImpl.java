package cn.lfy.qneng.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lfy.qneng.dao.AlarmDAO;
import cn.lfy.qneng.model.Alarm;
import cn.lfy.qneng.service.AlarmService;
import cn.lfy.qneng.vo.AlarmQuery;

import com.manager.model.Criteria;
import com.manager.model.PageInfo;

@Service
public class AlarmServiceImpl implements AlarmService {

    @Autowired
    private AlarmDAO dao;
    
    @Override
    public int countByCriteria(Criteria criteria) {
        return dao.countByExample(criteria);
    }

    @Override
    public Long add(Alarm record) {
    	dao.insert(record);
        return record.getId();
    }

    @Override
    public List<Alarm> findListByCriteria(Criteria criteria) {
        return dao.selectByExample(criteria);
    }

    @Override
    public Alarm findById(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByIdSelective(Alarm record) {
        return dao.updateByPrimaryKeySelective(record);
    }

    @Override
    public PageInfo<Alarm> findListByCriteria(Criteria criteria, int pageNo, int pageSize) {
        PageInfo<Alarm> res = new PageInfo<Alarm>(pageNo, pageSize);
        int total=this.countByCriteria(criteria);
        res.setTotal(total);
        
        criteria.setOffset(res.getOffset());
        criteria.setRows(pageSize);
        List<Alarm> list = this.findListByCriteria(criteria);
        res.setData(list);
        return res;
    }

	@Override
	public List<Alarm> list(AlarmQuery alarmQuery) {
		return dao.list(alarmQuery);
	}
    
}
