package cn.lfy.qneng.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.lfy.qneng.dao.ModuleDataDayDAO;
import cn.lfy.qneng.service.ModuleDataDayService;

@Service
public class ModuleDataDayServiceImpl implements ModuleDataDayService {

	@Resource
	private ModuleDataDayDAO moduleDataDayDAO;
	
	@Override
	public void deal(String date) {
		if(date != null && !"".equals(date)) {
			moduleDataDayDAO.insertOrUpdate(date + " 00:00:00", date + " 23:59:59");
		}
	}

	@Override
	public void deal(String startDate, String endDate) {
		if(startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
			moduleDataDayDAO.insertOrUpdate(startDate + " 00:00:00", endDate + " 23:59:59");
		}
	}

}
