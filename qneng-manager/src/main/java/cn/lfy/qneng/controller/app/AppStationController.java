package cn.lfy.qneng.controller.app;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lfy.common.model.Message;
import cn.lfy.qneng.model.Station;
import cn.lfy.qneng.service.StationService;

@Controller
@RequestMapping("/app")
public class AppStationController {

	@Resource
	private StationService stationService;
	
	@RequestMapping("/stationOverview")
	@ResponseBody
	public Object overview(@RequestParam(value = "stationId", required = false, defaultValue = "0") Long stationId) throws Exception {
		Message.Builder builder = Message.newBuilder();
		Station station = stationService.findById(stationId);
		if(station != null) {
			builder.put("stationName", station.getName());
			builder.put("stationInfo", station.getInfo());
		}
		return builder.build();
	}
}
