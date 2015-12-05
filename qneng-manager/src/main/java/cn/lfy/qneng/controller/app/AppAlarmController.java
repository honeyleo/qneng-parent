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
import cn.lfy.qneng.service.AlarmService;
import cn.lfy.qneng.vo.AlarmQuery;
import cn.lfy.qneng.vo.AlarmVo;

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
		Long date = RequestUtil.getLong(request, "date");
		if(date == null || date == 0L) {
			date = System.currentTimeMillis();
		}
		Date dateW = new Date(date);
		//1-本天；2-本月；3-本年；
		Integer time = RequestUtil.getInteger(request, "time");
		switch (time) {
//		case 2:
//			String startTime = DateUtils.date2String3(DateUtils.getFirstDayOfWeek(dateW)) + " 00:00:00";
//			String endTime = DateUtils.date2String3(DateUtils.getLastDayOfWeek(dateW)) + " 23:59:59";
//			alarmQuery.setStartTime(startTime);
//			alarmQuery.setEndTime(endTime);
//			break;
		case 3:
			String year = DateUtils.date2String5(dateW);
			alarmQuery.setStartTime(year + "-01-01 00:00:00");
			alarmQuery.setEndTime(year + "-12-31 23:59:59");
			break;
		case 2:
			String start = DateUtils.getFirstDayOfMonth(dateW);
			String end = DateUtils.getLastDayOfMonth(dateW);
			alarmQuery.setStartTime(start);
			alarmQuery.setEndTime(end);
			break;
		default:
			String dateString = DateUtils.date2String3(new Date(date));
			alarmQuery.setStartTime(dateString + " 00:00:00");
			alarmQuery.setEndTime(dateString + " 23:59:59");
			break;
		}
		Integer start = RequestUtil.getInteger(request, "curNumer");
		if(start == null) {
			start = 0;
		}
		alarmQuery.setStart(start);
		Integer limit = RequestUtil.getInteger(request, "count");
		if(limit != null) {
			alarmQuery.setLimit(limit);
		}
		List<AlarmVo> list = alarmService.list(alarmQuery);
		builder.put("data", list);
		return builder.build();
	}
}
