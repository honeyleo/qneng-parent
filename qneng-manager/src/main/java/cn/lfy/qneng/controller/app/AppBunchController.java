package cn.lfy.qneng.controller.app;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lfy.common.model.Message;
import cn.lfy.common.utils.DateUtils;
import cn.lfy.qneng.model.Bunch;
import cn.lfy.qneng.service.BunchService;
import cn.lfy.qneng.service.ModuleDataService;
import cn.lfy.qneng.vo.BunchInfo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.manager.model.Criteria;

@Controller
@RequestMapping("/app")
public class AppBunchController {

	@Resource
	private BunchService bunchService;
	@Resource
	private ModuleDataService moduleDataService;
	
	@RequestMapping("/bunchList")
	@ResponseBody
	public Object list(@RequestParam(value = "stationId", required = false, defaultValue = "0") Long stationId, 
			@RequestParam(value = "search", required = false, defaultValue = "") String search) throws Exception {
		Message.Builder builder = Message.newBuilder();
		Criteria criteria = new Criteria();
		criteria.put("stationId", stationId);
		if(!Strings.isNullOrEmpty(search)) {
			criteria.put("nameLike", search);
		}
		List<Bunch> list = bunchService.findListByCriteria(criteria);
		if(list != null) {
			JSONArray array = new JSONArray();
			for(Bunch bunch : list) {
				JSONObject obj = new JSONObject();
				obj.put("bunchId", bunch.getId());
				obj.put("element", bunch.getElement());
				obj.put("line", bunch.getLine());
				obj.put("row", bunch.getRow());
				obj.put("curPower", 0);
				array.add(obj);
			}
			builder.put("data", array);
		}
		return builder.build();
	}
	
	/**
	 * 获取组串概览
	 * @param stationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bunchOverview")
	@ResponseBody
	public Object overview(@RequestParam(value = "bunchId", required = false, defaultValue = "0") Long bunchId) throws Exception {
		Message.Builder builder = Message.newBuilder();
		Bunch bunch = bunchService.findById(bunchId);
		if(bunch != null) {
			builder.put("bunchId", bunch.getId());
			Double total = moduleDataService.getBunchTotal(bunchId);
			builder.put("allCapacity", total);
			Double year = moduleDataService.getBunchTotalForYear(bunchId);
			builder.put("yearCapacity", year);
			Double month = moduleDataService.getBunchTotalForMonth(bunchId);
			builder.put("monthCapacity", month);
			BunchInfo bunchInfo = moduleDataService.getBunchInfo(bunchId);
			if(bunchInfo != null) {
				builder.put("curTemp", bunchInfo.getCurTemp());
				builder.put("curPower", bunchInfo.getCurPower());
				builder.put("curVlot", bunchInfo.getCurVlot());
				builder.put("curCurr", bunchInfo.getCurCurr());
				builder.put("alarmNumber", bunchInfo.getAlarmNumber());
			}
		}
		return builder.build();
	}
	/**
	 * 获取组串概览数据
	 * @param stationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/bunchOverviewData")
	@ResponseBody
	public Object  overviewData(@RequestParam(value = "bunchId", required = false, defaultValue = "0") Long bunchId) throws Exception {
		Message.Builder builder = Message.newBuilder();
		Bunch bunch = bunchService.findById(bunchId);
		if(bunch != null) {
			Date date = new Date();
			double[] day = moduleDataService.getBunchPowerForDate(DateUtils.date2String3(date), bunchId);
			builder.put("dayPower", day);
		}
		return builder.build();
	}
}
