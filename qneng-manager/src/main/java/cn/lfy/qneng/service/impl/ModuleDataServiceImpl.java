package cn.lfy.qneng.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lfy.common.utils.DateUtils;
import cn.lfy.qneng.dao.ModuleDAO;
import cn.lfy.qneng.dao.ModuleDataDAO;
import cn.lfy.qneng.dao.ModuleDataDayDAO;
import cn.lfy.qneng.model.ModuleData;
import cn.lfy.qneng.service.ModuleDataService;
import cn.lfy.qneng.vo.ModuleQuery;
import cn.lfy.qneng.vo.PowerDataInfo;

import com.manager.model.Criteria;
import com.manager.model.PageInfo;

@Service
public class ModuleDataServiceImpl implements ModuleDataService {

    @Autowired
    private ModuleDataDAO dao;
    @Resource
    private ModuleDAO moduleDao;
    @Resource
    private ModuleDataDayDAO moduleDataDayDAO;
    
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

	@Override
	public Double getTotal(Long stationId, Long bunchId, Long moduleId) {
		ModuleQuery query = new ModuleQuery();
		query.setStationId(stationId);
		query.setBunchId(bunchId);
		query.setModuleId(moduleId);
		Double total = moduleDataDayDAO.getCapacity(query);
		return total;
	}

	@Override
	public Double getTotalForYear(Long stationId, Long bunchId, Long moduleId) {
		ModuleQuery query = new ModuleQuery();
		query.setStationId(stationId);
		query.setBunchId(bunchId);
		query.setModuleId(moduleId);
		String date = DateUtils.date2String5(new Date());
		query.setStartTime(date + "-01-01");
		query.setEndTime(date + "-12-31");
		Double year = moduleDataDayDAO.getCapacity(query);
		return year;
	}

	@Override
	public Double getTotalForMonth(Long stationId, Long bunchId, Long moduleId) {
		ModuleQuery query = new ModuleQuery();
		query.setStationId(stationId);
		query.setBunchId(bunchId);
		query.setModuleId(moduleId);
		Calendar cal = Calendar.getInstance();
		query.setStartTime(DateUtils.getFirstDayOfMonth(cal.getTime()));
		query.setEndTime(DateUtils.getLastDayOfMonth(cal.getTime()));
		Double month = moduleDataDayDAO.getCapacity(query);
		return month;
	}

	@Override
	public double[] getPowerForDate(String date, Long stationId, Long bunchId, Long moduleId) {
		ModuleQuery query = new ModuleQuery();
		query.setStationId(stationId);
		query.setBunchId(bunchId);
		query.setModuleId(moduleId);
		query.setStartTime(date + " 00:00:00");
		query.setEndTime(date + " 23:59:59");
		double[] data = new double[24];
		for(int i = 0; i < 24; i ++) {
			data[i] = 0;
		}
		List<PowerDataInfo> list = dao.getDayPowerData(query);
		for(int i = 0; i < 24; i++) {
			for(PowerDataInfo info : list) {
				if(info.getValue() == i) {
					data[i] += info.getCurPower();
				}
			}
		}
		return data;
	}

	@Override
	public double[] getCapacityForMonth(Date date, Long stationId, Long bunchId, Long moduleId) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayNum = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		String startTime = DateUtils.getFirstDayOfMonth(date);
		String endTime = DateUtils.getLastDayOfMonth(date);
		ModuleQuery query = new ModuleQuery();
		query.setStationId(stationId);
		query.setBunchId(bunchId);
		query.setModuleId(moduleId);
		query.setStartTime(startTime);
		query.setEndTime(endTime);
		double[] data = new double[dayNum];
		for(int i = 0; i < dayNum; i ++) {
			data[i] = 0;
		}
		List<PowerDataInfo> list = moduleDataDayDAO.getMonthCapacityData(query);
		for(int i = 1; i <= dayNum; i++) {
			for(PowerDataInfo info : list) {
				if(info.getValue() == i) {
					data[i - 1] = info.getCapacity();
					break;
				}
			}
		}
		return data;
	}

	@Override
	public double[] getCapacityForYear(String date, Long stationId, Long bunchId, Long moduleId) {
		String startTime = date + "-01-01";
		String endTime = date + "-12-31";
		ModuleQuery query = new ModuleQuery();
		query.setStationId(stationId);
		query.setBunchId(bunchId);
		query.setModuleId(moduleId);
		query.setStartTime(startTime);
		query.setEndTime(endTime);
		double[] data = new double[12];
		for(int i = 0; i < 12; i ++) {
			data[i] = 0;
		}
		List<PowerDataInfo> list = moduleDataDayDAO.getYearCapacityData(query);
		for(int i = 1; i <= 12; i++) {
			for(PowerDataInfo info : list) {
				if(info.getValue() == i) {
					data[i - 1] = info.getCapacity();
					break;
				}
			}
		}
		return data;
	}

	@Override
	public Double getCapacityForDate(String date, Long stationId, Long bunchId,
			Long moduleId) {
		ModuleQuery query = new ModuleQuery();
		query.setStationId(stationId);
		query.setBunchId(bunchId);
		query.setModuleId(moduleId);
		query.setStartTime(date);
		query.setEndTime(date);
		Double total = moduleDataDayDAO.getCapacity(query);
		return total;
	}

}
