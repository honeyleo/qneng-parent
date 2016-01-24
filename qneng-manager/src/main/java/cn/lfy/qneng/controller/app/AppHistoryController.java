package cn.lfy.qneng.controller.app;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lfy.common.model.Message;
import cn.lfy.common.utils.DateUtils;
import cn.lfy.qneng.service.AlarmService;
import cn.lfy.qneng.service.ModuleDataService;
import cn.lfy.qneng.service.WeatherService;
import cn.lfy.qneng.vo.AlarmQuery;

@Controller
@RequestMapping("/app")
public class AppHistoryController {

	@Resource
	private ModuleDataService moduleDataService;
	
	@Resource
	private AlarmService alarmService;
	
	@Resource
	private WeatherService weatherService;
	/**
	 * 获取电站概览数据
	 * @param stationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/history")
	@ResponseBody
	public Object  history(@RequestParam(value = "stationId", required = false, defaultValue = "0") Long stationId, 
			@RequestParam(value = "time", required = false, defaultValue = "0") Integer time, 
			@RequestParam(value = "bunchId", required = false, defaultValue = "0") Long bunchId, 
			@RequestParam(value = "moduleId", required = false, defaultValue = "0") Long moduleId, 
			@RequestParam(value = "date", required = false, defaultValue = "0") Long date) throws Exception {
		Message.Builder builder = Message.newBuilder();
		stationId = stationId == 0L ? null : stationId;
		bunchId = bunchId == 0L ? null : bunchId;
		moduleId = moduleId == 0L ? null : moduleId;
		if(date == 0) {
			date = System.currentTimeMillis();
		}
		
		Date dateW = new Date(date);
		
		AlarmQuery alarmQuery = new AlarmQuery();
		alarmQuery.setStationId(stationId);
		alarmQuery.setBunchId(bunchId);
		alarmQuery.setModuleId(moduleId);
		
		switch (time) {
		case 2:
			double[] month = moduleDataService.getCapacityForMonth(new Date(date), stationId, bunchId, moduleId);
			builder.put("monthCapacity", month);
			String start = DateUtils.getFirstDayOfMonth(dateW);
			String end = DateUtils.getLastDayOfMonth(dateW);
			alarmQuery.setStartTime(start);
			alarmQuery.setEndTime(end);
			break;
		case 3:
			double[] year = moduleDataService.getCapacityForYear(DateUtils.date2String5(new Date(date)), stationId, bunchId, moduleId);
			builder.put("yearCapacity", year);
			
			String yearString = DateUtils.date2String5(dateW);
			alarmQuery.setStartTime(yearString + "-01-01 00:00:00");
			alarmQuery.setEndTime(yearString + "-12-31 23:59:59");
			break;
		default:
			double[] day = moduleDataService.getPowerForDate(DateUtils.date2String3(new Date(date)), stationId, bunchId, moduleId);
			builder.put("dayPower", day);
			//TODO 当前温度暂时不填，等确定再说
			builder.put("curTemp", weatherService.getWeatherByStationId(stationId).getTemp());
			builder.put("dayWeather", weatherService.getWeatherByStationId(stationId).getWeather());
			
			alarmQuery.setDate(DateUtils.date2String3(new Date(date)));
			
			Double dayCapacity = moduleDataService.getCapacityForDate(DateUtils.date2String3(new Date(date)), stationId, bunchId, moduleId);
			builder.put("dayCapacity", dayCapacity != null ? dayCapacity : 0);
			break;
		}
		
		Integer alarmNumber = alarmService.getAlarmCount(alarmQuery);
		alarmNumber = alarmNumber != null ? alarmNumber : 0;
		builder.put("alarmNumber", alarmNumber);
		return builder.build();
	}
}
