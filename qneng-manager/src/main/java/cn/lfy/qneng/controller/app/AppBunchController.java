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
import cn.lfy.qneng.service.AlarmService;
import cn.lfy.qneng.service.BunchService;
import cn.lfy.qneng.service.ModuleDataService;
import cn.lfy.qneng.vo.AlarmQuery;
import cn.lfy.qneng.vo.DataInfo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.manager.model.Criteria;

/**
 * 终端组串相关API类
 * @author leo.liao
 *
 */
@Controller
@RequestMapping("/app")
public class AppBunchController {

	@Resource
	private BunchService bunchService;
	@Resource
	private ModuleDataService moduleDataService;
	
	@Resource
	private AlarmService alarmService;
	
	/**
	 * 组串列表
	 * @param stationId
	 * @param search
	 * @return
	 * @throws Exception
	 */
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
				obj.put("name", bunch.getName());
				obj.put("element", bunch.getElement());
				obj.put("line", bunch.getLine());
				obj.put("row", bunch.getRow());
				DataInfo dataInfo = moduleDataService.getDataInfo(null, bunch.getId());
				Double curPower = null;
				if(dataInfo != null) {
					curPower = dataInfo.getCurPower();
				}
				curPower = curPower != null ? curPower : 0;
				obj.put("curPower", curPower);
				
				AlarmQuery alarmQuery = new AlarmQuery();
				alarmQuery.setBunchId(bunch.getId());
				String date = DateUtils.getCurrentDate();
				alarmQuery.setDate(date);
				Integer alarmNumber = alarmService.getAlarmCount(alarmQuery);
				alarmNumber = alarmNumber != null ? alarmNumber : 0;
				obj.put("alarmNumber", alarmNumber);
				
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
			builder.put("name", bunch.getName());
			Double total = moduleDataService.getTotal(null, bunchId, null);
			builder.put("allCapacity", total);
			Double year = moduleDataService.getTotalForYear(null, bunchId, null);
			builder.put("yearCapacity", year);
			Double month = moduleDataService.getTotalForMonth(null, bunchId, null);
			builder.put("monthCapacity", month);
			DataInfo dataInfo = moduleDataService.getDataInfo(null, bunchId);
			if(dataInfo != null) {
				//TODO 当前温度暂时不填，等确定再说
				builder.put("curTemp", 40);
				builder.put("inputVolt", dataInfo.getInputVolt());
				builder.put("curPower", dataInfo.getCurPower());
				builder.put("curVlot", dataInfo.getCurVlot());
				builder.put("curCurr", dataInfo.getCurCurr());
			}
			AlarmQuery alarmQuery = new AlarmQuery();
			alarmQuery.setBunchId(bunchId);
			String date = DateUtils.getCurrentDate();
			alarmQuery.setDate(date);
			Integer alarmNumber = alarmService.getAlarmCount(alarmQuery);
			alarmNumber = alarmNumber != null ? alarmNumber : 0;
			builder.put("alarmNumber", alarmNumber);
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
			double[] day = moduleDataService.getPowerForDate(DateUtils.date2String3(date), null, bunchId, null);
			builder.put("dayPower", day);
		}
		return builder.build();
	}
}
