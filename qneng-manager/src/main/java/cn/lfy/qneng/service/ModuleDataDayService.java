package cn.lfy.qneng.service;

public interface ModuleDataDayService {

	/**
	 * 处理某一天的记录
	 * date 日期
	 */
	void deal(String date);
	/**
	 * 处理某一时间段的记录
	 * @param startDate
	 * @param endDate
	 */
	void deal(String startDate, String endDate);
}
