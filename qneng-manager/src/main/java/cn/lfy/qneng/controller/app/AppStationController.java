package cn.lfy.qneng.controller.app;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lfy.common.model.Message;
import cn.lfy.common.utils.DateUtils;
import cn.lfy.common.utils.NumberUtil;
import cn.lfy.qneng.model.Station;
import cn.lfy.qneng.service.ModuleDataService;
import cn.lfy.qneng.service.ModuleService;
import cn.lfy.qneng.service.StationService;
import cn.lfy.qneng.service.WeatherService;
import cn.lfy.qneng.service.WeatherService.Weather;
/**
 * 终端电站相关API
 * @author leo.liao
 *
 */
@Controller
@RequestMapping("/app")
public class AppStationController {

	@Resource
	private StationService stationService;
	@Resource
	private ModuleDataService moduleDataService;
	@Resource
	private WeatherService weatherService;
	@Resource
	private ModuleService moduleService;
	/**
	 * 获取电站概览
	 * @param stationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/stationOverview")
	@ResponseBody
	public Object overview(@RequestParam(value = "stationId", required = false, defaultValue = "0") Long stationId) throws Exception {
		Message.Builder builder = Message.newBuilder();
		Station station = stationService.findById(stationId);
		if(station != null) {
			builder.put("stationName", station.getName());
			builder.put("stationIntro", station.getInfo());
			Double total = moduleDataService.getTotal(stationId, null, null);
			builder.put("allCapacity", total != null ? total : 0);
			if(total != null) {
				builder.put("displacement", NumberUtil.get2Double(total/1000 * 0.785));
			}
			Double year = moduleDataService.getTotalForYear(stationId, null, null);
			builder.put("yearCapacity", year != null ? year : 0);
			Double month = moduleDataService.getTotalForMonth(stationId, null, null);
			builder.put("monthCapacity", month != null ? month : 0);
			Weather weather = weatherService.getWeatherByStationId(stationId == null ? 0L : stationId);
			builder.put("stationTemp", weather.getTemp());
			builder.put("stationWeather", weather.getWeather());
			
			Double curPower = moduleService.getStationPower(stationId);
			builder.put("curPower", curPower != null ? curPower : 0);
		}
		return builder.build();
	}
	
	/**
	 * 获取电站概览数据
	 * @param stationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/stationOverviewData")
	@ResponseBody
	public Object  overviewData(@RequestParam(value = "stationId", required = false, defaultValue = "0") Long stationId) throws Exception {
		Message.Builder builder = Message.newBuilder();
		Station station = stationService.findById(stationId);
		if(station != null) {
			Date date = new Date();
			double[] day = moduleDataService.getPowerForDate(DateUtils.date2String3(date), stationId, null, null);
			builder.put("dayPower", day);
			double[] month = moduleDataService.getCapacityForMonth(date, stationId, null, null);
			builder.put("monthCapacity", month);
			double[] year = moduleDataService.getCapacityForYear(DateUtils.date2String5(date), stationId, null, null);
			builder.put("yearCapacity", year);
		}
		return builder.build();
	}
}
