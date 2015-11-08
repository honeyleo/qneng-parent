package cn.lfy.qneng.controller.app;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lfy.common.model.Message;
import cn.lfy.common.utils.DateUtils;
import cn.lfy.qneng.model.Alarm;
import cn.lfy.qneng.service.AlarmService;
import cn.lfy.qneng.vo.AlarmQuery;

import com.manager.common.util.RequestUtil;

@Controller
@RequestMapping("/app")
public class AppAlarmController {
	
	@Resource
	private AlarmService alarmService;

	@RequestMapping("/alarmList")
	@ResponseBody
	public Object list(HttpServletRequest request) throws Exception {
		Message.Builder builder = Message.newBuilder();
		AlarmQuery alarmQuery = new AlarmQuery();
		Long stationId = RequestUtil.getLong(request, "stationId");
		if(stationId != null && stationId != 0) {
			alarmQuery.setStationId(stationId);
		}
		Long bunchId = RequestUtil.getLong(request, "bunchId");
		if(bunchId != null && bunchId != 0) {
			alarmQuery.setBunchId(bunchId);
		}
		Long moduleId = RequestUtil.getLong(request, "moduleId");
		if(moduleId != null && moduleId != 0) {
			alarmQuery.setModuleId(moduleId);
		}
		//1-本天；2-本周；3-本年；
		Integer time = RequestUtil.getInteger(request, "time");
		if(time != null && time != 0) {
			if(time == 1) {
				String dateString = DateUtils.getCurrentDate();
				alarmQuery.setStartTime(dateString + " 00:00:00");
				alarmQuery.setEndTime(dateString + " 23:59:59");
			} else if(time == 2) {
				Date date = new Date();
				String startTime = DateUtils.date2String3(DateUtils.getFirstDayOfWeek(date)) + " 00:00:00";
				String endTime = DateUtils.date2String3(DateUtils.getLastDayOfWeek(date)) + " 23:59:59";
				alarmQuery.setStartTime(startTime);
				alarmQuery.setEndTime(endTime);
			} else if(time == 3) {
				String year = DateUtils.getNowDate(DateUtils.LONGDATE_DATEYEAR);
				alarmQuery.setStartTime(year + "-01-01 00:00:00");
				alarmQuery.setEndTime(year + "-12-31 23:59:59");
			}
		}
		Long date = RequestUtil.getLong(request, "date");
		if(date != null && date != 0) {
			String dateString = DateUtils.date2String3(new Date(date));
			alarmQuery.setStartTime(dateString + " 00:00:00");
			alarmQuery.setEndTime(dateString + " 23:59:59");
		}
		List<Alarm> list = alarmService.list(alarmQuery);
		builder.put("data", list);
		return builder.build();
	}
}
