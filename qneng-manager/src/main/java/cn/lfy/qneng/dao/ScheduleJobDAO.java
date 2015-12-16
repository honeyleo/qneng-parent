package cn.lfy.qneng.dao;

import java.util.List;

import cn.lfy.common.job.model.ScheduleJob;

public interface ScheduleJobDAO {

	List<ScheduleJob> findAllEnabled(String idc);
}
