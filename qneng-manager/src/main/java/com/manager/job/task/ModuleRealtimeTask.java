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
public class ModuleRealtimeTask extends AbstractTaskInterface {

	private final static Logger LOG = LoggerFactory.getLogger(ModuleRealtimeTask.class);
	
	@Resource
	private ModuleDataDayService moduleDataDayService;
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		LOG.info("组件实时把数据统计到天统计表...");
		long start = System.currentTimeMillis();
		String date = DateUtils.getCurrentDate();
		moduleDataDayService.deal(date);
		LOG.info("组件实时统计数据花费时间:{}ms", (System.currentTimeMillis() - start));
	}

}
