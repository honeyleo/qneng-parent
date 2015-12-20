package com.manager.job.task;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.lfy.common.job.AbstractTaskInterface;
import cn.lfy.common.utils.DateUtils;
import cn.lfy.qneng.service.ModuleDataDayService;

@Service
public class ModuleDayTask extends AbstractTaskInterface {

	private final static Logger LOG = LoggerFactory.getLogger(ModuleDayTask.class);
	
	@Resource
	private ModuleDataDayService moduleDataDayService;
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String date = DateUtils.getYesterdayDate();
		LOG.info("组件天任务把昨天【date={}】数据统计到天统计表...", date);
		long start = System.currentTimeMillis();
		moduleDataDayService.deal(date);
		LOG.info("组件天任务【date={}】统计数据花费时间:{}ms", date, (System.currentTimeMillis() - start));
	}

}
