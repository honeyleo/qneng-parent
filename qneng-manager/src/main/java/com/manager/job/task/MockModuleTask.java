package com.manager.job.task;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.lfy.common.job.AbstractTaskInterface;

@Service
public class MockModuleTask extends AbstractTaskInterface {

	private final static Logger LOG = LoggerFactory.getLogger(MockModuleTask.class);
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		LOG.info("start mock module report data to gateway...");
		
	}

}
