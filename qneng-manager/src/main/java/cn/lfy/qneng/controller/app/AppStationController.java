package cn.lfy.qneng.controller.app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lfy.common.model.Message;

@Controller
@RequestMapping("/app")
public class AppStationController {

	@RequestMapping("/stationOverview")
	@ResponseBody
	public Object overview(@RequestParam(value = "stationId", required = true) Long stationId) throws Exception {
		Message.Builder builder = Message.newBuilder();
		return builder.build();
	}
}
