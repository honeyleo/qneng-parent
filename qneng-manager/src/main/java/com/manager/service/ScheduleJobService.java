package com.manager.service;

import java.util.List;

import org.quartz.SchedulerException;

import cn.lfy.common.job.model.ScheduleJob;


/**
 * ScheduleJob service类
 *
 * @author jianbin
 * @date 2014-12-9 下午3:24:46
 */
public interface ScheduleJobService {

	/**
	 * 获取所有
	 * @return
	 * @throws SchedulerException
	 */
	List<ScheduleJob> getAllJob() throws SchedulerException;

	/**
	 * 取所有NORMAL的task
	 * @return
	 * @throws SchedulerException
	 */
//	List<ScheduleJob> findAllEnabled() throws SchedulerException;
	
	/**
	 * 暂停任务
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	Integer pauseJob( ScheduleJob scheduleJob ) throws SchedulerException;

	/**
	 * 和暂停任务相对
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	Integer resumeJob( ScheduleJob scheduleJob ) throws SchedulerException;

	/**
	 * 先停任务；删除任务后，所对应的trigger也将被删除
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	Integer deleteJob( ScheduleJob scheduleJob ) throws SchedulerException;

	/**
	 * 新增一个定时任务,如果任务状态为NORMAL,则部署这个任务
	 * @param scheduleJob
	 * @return 新增数量
	 * @throws SchedulerException
	 */
	Integer createJob( ScheduleJob scheduleJob ) throws SchedulerException;

	/**
	 * 部署一个定时任务
	 * @param scheduleJob
	 * @return
	 * @throws SchedulerException
	 */
	Integer scheduleJob( ScheduleJob scheduleJob ) throws SchedulerException;

	/**
	 * 立即运行任务
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	Integer triggerJob( ScheduleJob scheduleJob ) throws SchedulerException;

	/**
	 * 更新任务的时间表达式
	 * 更新之后，任务将立即按新的时间表达式执行
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	Integer rescheduleJob( ScheduleJob scheduleJob ) throws SchedulerException;

	/**
	 * 查看任务详情
	 * @param scheduleJobId
	 * @throws SchedulerException
	 */
	ScheduleJob viewJob( Long scheduleJobId ) throws SchedulerException;

	/**
	 * 更新任务的时间表达式
	 * 更新之后，任务将立即按新的时间表达式执行
	 * @param scheduleJob
	 * @throws SchedulerException
	 */
	Integer updateJob( ScheduleJob scheduleJob ) throws SchedulerException;
	
}
