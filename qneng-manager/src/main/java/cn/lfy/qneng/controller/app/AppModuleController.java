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
/**
 * 终端组件API
 * @author leo.liao
 *
 */
@Controller
@RequestMapping("/app")
public class AppModuleController {

	private final static int ONLINE = 2400;
	
	@Resource
	private ModuleService moduleService;
	@Resource
	private ModuleDataService moduleDataService;
	@Resource
	private AlarmService alarmService;
	
	/**
	 * 根据条件查询组件列表
	 * @param bunchId
	 * @param search
	 * @return
	 * @throws Exception
	 */
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
				
				Double curPower = 0D;
				if(module.getLastUpdateTime() > (System.currentTimeMillis()/1000 - ONLINE)) {
					if(module.getCurCurr() != null && module.getCurVlot() != null) {
						curPower = module.getCurVlot()*module.getCurCurr();
					}
				}
				
				obj.put("curPower", curPower);
				
				AlarmQuery alarmQuery = new AlarmQuery();
				alarmQuery.setModuleId(module.getId());
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
			
			Double curTemp = 0D, inputVolt = 0D,curPower = 0D,curVlot = 0D,curCurr = 0D;
			if(module.getLastUpdateTime() > (System.currentTimeMillis()/1000 - ONLINE)) {
				curTemp = module.getCurTemp() != null ? module.getCurTemp() : 0;
				inputVolt = module.getInputVolt() != null ? module.getInputVolt() : 0;
				curVlot = module.getCurVlot() != null ? module.getCurVlot() : 0;
				curCurr = module.getCurCurr() != null ? module.getCurCurr() : 0;
				curPower = curVlot*curCurr;
			} 
			builder.put("curTemp", curTemp);
			builder.put("curPower", curPower);
			builder.put("inputVolt", inputVolt);
			builder.put("curVlot", curVlot);
			builder.put("curCurr", curCurr);
			
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
