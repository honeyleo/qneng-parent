package cn.lfy.qneng.controller.app;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lfy.common.model.Message;
import cn.lfy.common.utils.DateUtils;
import cn.lfy.qneng.model.Station;
import cn.lfy.qneng.service.ModuleDataService;
import cn.lfy.qneng.service.StationService;
import cn.lfy.qneng.vo.StationInfo;

@Controller
@RequestMapping("/app")
public class AppStationController {

	@Resource
	private StationService stationService;
	@Resource
	private ModuleDataService moduleDataService;
	
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
			builder.put("stationInfo", station.getInfo());
			Double total = moduleDataService.getStationTotal(stationId);
			builder.put("allCapacity", total);
			if(total != null) {
				builder.put("displacement", total * 0.785);
			}
			Double year = moduleDataService.getStationTotalForYear(stationId);
			builder.put("yearCapacity", year);
			Double month = moduleDataService.getStationTotalForMonth(stationId);
			builder.put("monthCapacity", month);
			StationInfo stationInfo = moduleDataService.getStationInfo(stationId);
			if(stationInfo != null) {
				builder.put("stationWeather", stationInfo.getWeather());
				builder.put("stationTemp", stationInfo.getTemp());
				builder.put("curPower", stationInfo.getCurPower());
			}
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
			double[] day = moduleDataService.getStationPowerForDate(DateUtils.date2String3(date), stationId);
			builder.put("dayPower", day);
			double[] month = moduleDataService.getStationForMonth(DateUtils.date2String4(date), stationId);
			builder.put("monthCapacity", month);
			double[] year = moduleDataService.getStationForYear(DateUtils.date2String5(date), stationId);
			builder.put("yearCapacity", year);
		}
		return builder.build();
	}
}
