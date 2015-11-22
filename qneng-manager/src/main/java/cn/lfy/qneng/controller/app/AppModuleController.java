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
import cn.lfy.qneng.model.Module;
import cn.lfy.qneng.service.AlarmService;
import cn.lfy.qneng.service.ModuleDataService;
import cn.lfy.qneng.service.ModuleService;
import cn.lfy.qneng.vo.AlarmQuery;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.manager.model.Criteria;

@Controller
@RequestMapping("/app")
public class AppModuleController {

	@Resource
	private ModuleService moduleService;
	@Resource
	private ModuleDataService moduleDataService;
	@Resource
	private AlarmService alarmService;
	
	@RequestMapping("/moduleList")
	@ResponseBody
	public Object list(@RequestParam(value = "bunchId", required = false, defaultValue = "0") Long bunchId, 
			@RequestParam(value = "search", required = false, defaultValue = "") String search) throws Exception {
		Message.Builder builder = Message.newBuilder();
		Criteria criteria = new Criteria();
		criteria.put("bunchId", bunchId);
		if(!Strings.isNullOrEmpty(search)) {
			criteria.put("nameLike", search);
		}
		List<Module> list = moduleService.findListByCriteria(criteria);
		if(list != null) {
			JSONArray array = new JSONArray();
			for(Module module : list) {
				JSONObject obj = new JSONObject();
				obj.put("moduleId", module.getId());
				obj.put("name", module.getName());
				obj.put("serial", module.getNo());
				obj.put("curPower", 0);
				array.add(obj);
			}
			builder.put("data", array);
		}
		return builder.build();
	}
	
	/**
	 * 获取组件概览
	 * @param stationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/moduleOverview")
	@ResponseBody
	public Object overview(@RequestParam(value = "moduleId", required = false, defaultValue = "0") Long moduleId) throws Exception {
		Message.Builder builder = Message.newBuilder();
		Module module = moduleService.findById(moduleId);
		if(module != null) {
			builder.put("moduleId", module.getId());
			builder.put("name", module.getName());
			Double total = moduleDataService.getTotal(null, null, moduleId);
			builder.put("allCapacity", total != null ? total : 0);
			Double year = moduleDataService.getTotalForYear(null, null, moduleId);
			builder.put("yearCapacity", year != null ? year : 0);
			Double month = moduleDataService.getTotalForMonth(null, null, moduleId);
			builder.put("monthCapacity", month != null ? month : 0);
			builder.put("model", module.getModel());
			builder.put("serial", module.getNo());
			builder.put("installdate", module.getInstalldate());
			builder.put("manufactory", module.getManufactory());
			builder.put("curTemp", module.getCurTemp());
			builder.put("curPower", module.getCurVlot() * module.getCurCurr());
			builder.put("curVlot", module.getCurVlot());
			builder.put("curCurr", module.getCurCurr());
			AlarmQuery alarmQuery = new AlarmQuery();
			alarmQuery.setModuleId(moduleId);
			String date = DateUtils.getCurrentDate();
			alarmQuery.setDate(date);
			Integer alarmNumber = alarmService.getAlarmCount(alarmQuery);
			alarmNumber = alarmNumber != null ? alarmNumber : 0;
			builder.put("alarmNumber", alarmNumber);
		}
		return builder.build();
	}
	
	/**
	 * 获取组件概览数据
	 * @param moduleId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/moduleOverviewData")
	@ResponseBody
	public Object  overviewData(@RequestParam(value = "moduleId", required = false, defaultValue = "0") Long moduleId) throws Exception {
		Message.Builder builder = Message.newBuilder();
		Module module = moduleService.findById(moduleId);
		if(module != null) {
			Date date = new Date();
			double[] day = moduleDataService.getPowerForDate(DateUtils.date2String3(date), null, null, moduleId);
			builder.put("dayPower", day);
		}
		return builder.build();
	}
}
