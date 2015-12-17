package com.manager.dao;

import java.util.List;

import cn.lfy.common.job.model.ScheduleJob;

public interface ScheduleJobDAO {

	/**
	 * 获取所有可用的调度配置
	 * 
	 * @return
	 */
	public List<ScheduleJob> findAllEnabled(String idc);
	
	/**
	 * 获取所有调度配置
	 * 
	 * @return
	 */
	public List<ScheduleJob> findAll(String idc);

	public Integer update(ScheduleJob scheduleJob);

	public Integer updateStatus(ScheduleJob scheduleJob);

	public ScheduleJob loadJobById(Long scheduleJobId);

	public Integer create( ScheduleJob scheduleJob );

	public Integer delete( Long scheduleJobId );

	public Integer countByNameAndGroup( ScheduleJob scheduleJob );
}
