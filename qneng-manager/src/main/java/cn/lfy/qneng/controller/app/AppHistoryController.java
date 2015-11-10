package cn.lfy.qneng.controller.app;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lfy.common.model.Message;
import cn.lfy.common.utils.DateUtils;
import cn.lfy.qneng.service.ModuleDataService;

@Controller
@RequestMapping("/app")
public class AppHistoryController {

	@Resource
	private ModuleDataService moduleDataService;
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
		switch (time) {
		case 2:
			double[] month = moduleDataService.getCapacityForMonth(new Date(date), stationId, bunchId, moduleId);
			builder.put("monthCapacity", month);
			break;
		case 3:
			double[] year = moduleDataService.getCapacityForYear(DateUtils.date2String5(new Date(date)), stationId, bunchId, moduleId);
			builder.put("yearCapacity", year);
			break;
		default:
			double[] day = moduleDataService.getPowerForDate(DateUtils.date2String3(new Date(date)), stationId, bunchId, moduleId);
			builder.put("dayPower", day);
			builder.put("curTemp", 40);
			builder.put("alarmNumber", 0);
			builder.put("dayWeather", 0);
			builder.put("dayCapacity", 0);
			break;
		}
		return builder.build();
	}
}
