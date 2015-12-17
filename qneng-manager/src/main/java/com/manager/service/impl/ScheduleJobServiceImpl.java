package com.manager.service.impl;

import java.util.List;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.lfy.common.job.QuartzJobController;
import cn.lfy.common.job.model.ScheduleJob;

import com.manager.dao.ScheduleJobDAO;
import com.manager.service.ScheduleJobService;

@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

	@Autowired
	ScheduleJobDAO scheduleJobDao;
	
	@Autowired
	QuartzJobController quartzJobController;

	@Override
	public List<ScheduleJob> getAllJob() throws SchedulerException {
		return scheduleJobDao.findAll("DEFAULT");
	}
//
//	@Override
//	public List<ScheduleJob> findAllEnabled() throws SchedulerException {
//		return scheduleJobDao.findAllEnabled();
//	}
	
	@Override
	public Integer pauseJob( ScheduleJob scheduleJob ) throws SchedulerException {
		quartzJobController.pauseJob( scheduleJob );
		scheduleJob.setJobStatus( ScheduleJob.STATUS_PAUSED );
		return scheduleJobDao.updateStatus( scheduleJob );
	}

	@Override
	public Integer resumeJob( ScheduleJob scheduleJob ) throws SchedulerException {
		quartzJobController.resumeJob( scheduleJob );
		scheduleJob.setJobStatus( ScheduleJob.STATUS_NORMAL );
		return scheduleJobDao.updateStatus( scheduleJob );
	}

	@Override
	public Integer deleteJob( ScheduleJob scheduleJob ) throws SchedulerException {
		quartzJobController.deleteJob( scheduleJob );
		return scheduleJobDao.delete( scheduleJob.getJobId() );
	}

	@Override
	public Integer scheduleJob( ScheduleJob scheduleJob ) throws SchedulerException {
		
		if(ScheduleJob.STATUS_NORMAL.equals( scheduleJob.getJobStatus() ) ) {
			quartzJobController.scheduleSingleJob( scheduleJob );
		}
		return 1;
	}

	@Override
	public Integer triggerJob( ScheduleJob scheduleJob ) throws SchedulerException {
		quartzJobController.triggerJob( scheduleJob );
		return 1;
	}

	@Override
	public Integer createJob( ScheduleJob scheduleJob ) throws SchedulerException {
		int existsCount = scheduleJobDao.countByNameAndGroup( scheduleJob );
		if( existsCount > 0 ) {//
			throw new SchedulerException("指定的任务名称与任务分组已存在");
		}
		
		Integer createCount = scheduleJobDao.create( scheduleJob );
		if(ScheduleJob.STATUS_NORMAL.equals( scheduleJob.getJobStatus() ) ) {
			quartzJobController.scheduleSingleJob( scheduleJob );
		}
		return createCount;
	}

	@Override
	public Integer rescheduleJob( ScheduleJob scheduleJob ) throws SchedulerException {
		ScheduleJob dbJob = scheduleJobDao.loadJobById( scheduleJob.getJobId() );
		dbJob.setCronExpression( scheduleJob.getCronExpression() );
		
		quartzJobController.rescheduleJob( dbJob );
		
		return scheduleJobDao.update( dbJob );
	}

	public ScheduleJob viewJob( Long scheduleJobId ) throws SchedulerException {
		ScheduleJob scheduleJob = scheduleJobDao.loadJobById( scheduleJobId );
		scheduleJob.setIsRunning( quartzJobController.isJobRunning( scheduleJob ) );
		return scheduleJob;
	}

	@Override
	public Integer updateJob( ScheduleJob scheduleJob ) throws SchedulerException {
		int existsCount = scheduleJobDao.countByNameAndGroup( scheduleJob );
		if( existsCount > 0 ) {//
			throw new SchedulerException("存在重复的任务名称与任务分组");
		}
		return scheduleJobDao.update( scheduleJob );
	}

}
