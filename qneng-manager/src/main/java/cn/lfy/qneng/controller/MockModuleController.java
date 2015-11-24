package cn.lfy.qneng.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.lfy.common.model.Message;
import cn.lfy.common.utils.DateUtils;
import cn.lfy.qneng.gateway.client.ClientManager;
import cn.lfy.qneng.gateway.client.NioClient2;
import cn.lfy.qneng.gateway.model.NodeAlarmReq;
import cn.lfy.qneng.gateway.model.NodeConfigReq;
import cn.lfy.qneng.gateway.model.NodeDataReq;
import cn.lfy.qneng.model.Bunch;
import cn.lfy.qneng.model.MockModuleData;
import cn.lfy.qneng.model.Module;
import cn.lfy.qneng.model.ModuleData;
import cn.lfy.qneng.service.BunchService;
import cn.lfy.qneng.service.ModuleDataService;
import cn.lfy.qneng.service.ModuleService;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.manager.common.Constants;
import com.manager.common.exception.ApplicationException;
import com.manager.common.util.MD5;
import com.manager.common.util.Page;
import com.manager.common.util.RequestUtil;
import com.manager.model.Criteria;
import com.manager.model.PageInfo;

@Controller
@RequestMapping("/manager/mockmodule")
public class MockModuleController implements Constants {

	public static final String LIST = "/system/mockmodule/list";

    public static final String ADD = "/system/mockmodule/edit";

    public static final String UPDATE = "/system/mockmodule/edit";
    
    @Autowired
    private ModuleService moduleService;
    @Resource
	private ModuleDataService moduleDataService;
    @Resource
    private BunchService bunchService;
    
    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request, Page page) throws ApplicationException {
    	String nameLike = RequestUtil.getString(request, "nameLike");
    	Criteria criteria = new Criteria();
    	if (StringUtils.isNotBlank(nameLike)) {
            criteria.put("nameLike", nameLike);
        }
        PageInfo<Module> result = new PageInfo<Module>();
        List<Module> list = moduleService.findListByCriteria(criteria);
        for(Module module : list) {
        	NioClient2 client = ClientManager.getClient(module.getNo());
        	if(client != null) {
        		module.setOnline(true);
        	}
        	ScheduledFuture<?> future = MAP_SCHEDULED.get(module.getNo());
        	if(future != null) {
        		module.setMock(true);
        	}
        }
        result.setData(list);
        request.setAttribute("result", result);
        return new ModelAndView(LIST);
    }
    
    @RequestMapping("/start")
    @ResponseBody
    public Object start(HttpServletRequest request, Page page) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        Long id = RequestUtil.getLong(request, "id");
        Module module = moduleService.findById(id);
        if(module != null) {
        	try {
				NioClient2.login(module.getNo(), MD5.md5(module.getNo() + ":" + module.getAppSecret()));
			} catch (Exception e) {
				builder.setRet(1).setMsg("启动组件异常");
			}
        }
        return builder.build();
    }
    
    @RequestMapping("/stop")
    @ResponseBody
    public Object stop(HttpServletRequest request, Page page) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        Long id = RequestUtil.getLong(request, "id");
        Module module = moduleService.findById(id);
        if(module != null) {
        	NioClient2 client = ClientManager.getClient(module.getNo());
        	if(client != null) {
        		client.close();
        	}
        }
        return builder.build();
    }
    
    @RequestMapping("/config")
    public ModelAndView config(HttpServletRequest request, Page page) throws ApplicationException {
        Long id = RequestUtil.getLong(request, "id");
        Module module = moduleService.findById(id);
        request.setAttribute("entity", module);
        return new ModelAndView("/system/mockmodule/config");
    }
    
    @RequestMapping("/configSubmit")
    @ResponseBody
    public Object configSubmit(HttpServletRequest request, Page page) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        Long id = RequestUtil.getLong(request, "id");
        String name = RequestUtil.getString(request, "name");
        String model = RequestUtil.getString(request, "model");
        String manufactory = RequestUtil.getString(request, "manufactory");
        String installdate = RequestUtil.getString(request, "installdate");
        Double maxVolt = RequestUtil.getDouble(request, "maxVolt");
        Double maxCurr = RequestUtil.getDouble(request, "maxCurr");
        Double power = RequestUtil.getDouble(request, "maxCurr");
        Module module = moduleService.findById(id);
        if(module != null) {
        	NioClient2 client = ClientManager.getClient(module.getNo());
        	if(client != null) {
        		NodeConfigReq nodeConfigReq = new NodeConfigReq();
        		nodeConfigReq.setNo(module.getNo());
        		nodeConfigReq.setName(name);
        		nodeConfigReq.setModel(model);
        		nodeConfigReq.setManufactory(manufactory);
        		nodeConfigReq.setInstalldate(installdate);
        		nodeConfigReq.setMaxVolt(maxVolt);
        		nodeConfigReq.setMaxCurr(maxCurr);
        		nodeConfigReq.setPower(power);
        		client.send(1003, nodeConfigReq);
        	}
        }
        return builder.build();
    }
    
    @RequestMapping("/data")
    public ModelAndView data(HttpServletRequest request, Page page) throws ApplicationException {
        String ids = RequestUtil.getString(request, "ids");
        request.setAttribute("ids", ids);
        return new ModelAndView("/system/mockmodule/data");
    }
    
    private static List<NodeDataReq> LIST_DATA = Lists.newArrayList();
    static {
    	NodeDataReq req = new NodeDataReq();
    	req.setInputVolt(100D);
    	req.setOutvolt(40D);
    	req.setCurr(8D);
    	req.setTemp(15D);
    	req.setCapacity(80D);
    	LIST_DATA.add(req);
    	
    	req.setInputVolt(100D);
    	req.setOutvolt(38D);
    	req.setCurr(7D);
    	req.setTemp(14D);
    	req.setCapacity(66.5D);
    	LIST_DATA.add(req);
    	
    	req.setInputVolt(100D);
    	req.setOutvolt(34D);
    	req.setCurr(7D);
    	req.setTemp(14D);
    	req.setCapacity(59.5D);
    	LIST_DATA.add(req);
    }
    
    static final ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();
    static Map<String, ScheduledFuture<?>> MAP_SCHEDULED = Maps.newConcurrentMap();
    
    @RequestMapping("/dataSubmit")
    @ResponseBody
    public Object dataSubmit(HttpServletRequest request, Page page) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        Long id = RequestUtil.getLong(request, "id");
        final Module module = moduleService.findById(id);
        if(module != null) {
        	final NioClient2 client = ClientManager.getClient(module.getNo());
        	ScheduledFuture<?> future = scheduled.scheduleAtFixedRate(new Runnable() {
				
				@Override
				public void run() {
					Random random = new Random(System.currentTimeMillis());
		        	int index = random.nextInt(LIST_DATA.size());
		        	NodeDataReq nodeDataReq = LIST_DATA.get(index);
		        	NodeDataReq dataReq = new NodeDataReq();
		        	dataReq.setInputVolt(nodeDataReq.getInputVolt());
		        	dataReq.setOutvolt(nodeDataReq.getOutvolt());
		        	dataReq.setCurr(nodeDataReq.getCurr());
		        	dataReq.setNo(module.getNo());
		        	dataReq.setTemp(nodeDataReq.getTemp());
		        	dataReq.setTime(System.currentTimeMillis());
		        	Double capacity = nodeDataReq.getCapacity();
		        	int gailv = 0;
		        	if(random.nextInt(2) == 1) {
		        		gailv = random.nextInt(GAILV);
		        	} else {
		        		gailv = 0 - random.nextInt(GAILV);
		        	}
		        	
		        	Double outvolt = nodeDataReq.getOutvolt();
					outvolt = outvolt + outvolt*(gailv/100.00);
					dataReq.setOutvolt(outvolt);
					
					Double curr = nodeDataReq.getCurr();
					curr = curr + curr*(gailv/100.00);
					dataReq.setCurr(curr);
		        	
					capacity = outvolt*curr*0.17;
		        	dataReq.setCapacity(capacity);
		        	
		        	client.send(1005, dataReq);
		        	
				}
			}, 0, 10*60*1000, TimeUnit.MILLISECONDS);
        	MAP_SCHEDULED.put(module.getNo(), future);
        	
        }
        return builder.build();
    }
    
    @RequestMapping("/cancelScheduled")
    @ResponseBody
    public Object cancelScheduled(HttpServletRequest request) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        Long id = RequestUtil.getLong(request, "id");
        final Module module = moduleService.findById(id);
        if(module != null) {
        	ScheduledFuture<?> future = MAP_SCHEDULED.get(module.getNo());
        	if(future != null) {
        		future.cancel(true);
        		MAP_SCHEDULED.remove(module.getNo());
        	}
        }
        return builder.build();
    }
    
    static int GAILV = 6;
    
    @RequestMapping("/dataSubmit2")
    @ResponseBody
    public Object dataSubmit2(HttpServletRequest request, Page page, MockModuleData mockModuleData) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        String ids = RequestUtil.getString(request, "ids");
        String[] idArr = ids.split(",");
        List<Long> idList = Lists.newArrayList();
        for(String id : idArr) {
        	if(!"".equals(id)) {
        		idList.add(Long.parseLong(id));
        	}
        }
        final String startTimeStr = mockModuleData.getStartTime() + " 00:00:00";
        final String endTimeStr = mockModuleData.getEndTime() + " 23:59:59";
        for(final Long id : idList) {
        	Thread thread = new Thread(new Runnable() {
				
				@Override
				public void run() {
					Module module = moduleService.findById(id);
			        if(module != null) {
			        	Bunch bunch = bunchService.findById(module.getBunchId());
			        	long startTime = DateUtils.strToDate(startTimeStr, DateUtils.LONGDATE_DATETIME).getTime();
			        	long endTime = DateUtils.strToDate(endTimeStr , DateUtils.LONGDATE_DATETIME).getTime();
			        	while(startTime < endTime) {
			        		ModuleData moduleData = new ModuleData();
							moduleData.setStationId(bunch.getStationId());
							moduleData.setBunchId(module.getBunchId());
							moduleData.setModuleId(module.getId());
							moduleData.setNo(module.getNo());
							
							//随机种子
							Random random = new Random(startTime);
							int index = random.nextInt(LIST_DATA.size());
							NodeDataReq nodeDataReq = LIST_DATA.get(index);
							
							moduleData.setInputVolt(nodeDataReq.getInputVolt());
							
							int gailv = 0;
				        	if(random.nextInt(2) == 1) {
				        		gailv = random.nextInt(GAILV);
				        	} else {
				        		gailv = 0 - random.nextInt(GAILV);
				        	}
				        	
				        	Double outvolt = nodeDataReq.getOutvolt();
							outvolt = outvolt + outvolt*(gailv/100.00);
							moduleData.setOutvolt(outvolt);
							
							Double curr = nodeDataReq.getCurr();
							curr = curr + curr*(gailv/100.00);
							moduleData.setCurr(curr);
							
							Double temp = nodeDataReq.getTemp();
							temp = temp - random.nextInt(5);
							moduleData.setTemp(temp);
							
							Double capacity = outvolt*curr*0.17;
							moduleData.setCapacity(capacity);
							
							moduleData.setCreateTime(new Date(startTime));
							moduleData.setTime(startTime);
							moduleDataService.add(moduleData);
			        		startTime = startTime + 10*60*1000;
			        	}
			        }
				}
			});
        	thread.start();
        }
        return builder.build();
    }
    
    private static Map<Integer, String> MAP_ALARM = Maps.newConcurrentMap();
    static {
    	MAP_ALARM.put(1, "短路告警：光伏组件输出短路");
    	MAP_ALARM.put(2, "过压告警：光伏组件过压");
    	//MAP_ALARM.put(3, "过流告警：光伏组件过流");
    	//MAP_ALARM.put(4, "过温告警：光伏组件过温");
    	//MAP_ALARM.put(5, "重启动告警：光伏组件分线盒系统重启");
    }
    @RequestMapping("/reportAlarm")
    @ResponseBody
    public Object reportAlarm(HttpServletRequest request) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
    	Long id = RequestUtil.getLong(request, "id");
        Module module = moduleService.findById(id);
        if(module != null) {
        	Random random = new Random(System.currentTimeMillis());
        	int alarmType = 1 + random.nextInt(5);
            Long time = System.currentTimeMillis();
            NodeAlarmReq alarmReq = new NodeAlarmReq();
            alarmReq.setNo(module.getNo());
            alarmReq.setAlarmType(alarmType);
            alarmReq.setMemo(MAP_ALARM.get(alarmType));
            alarmReq.setTime(time);
            NioClient2 client = ClientManager.getClient(module.getNo());
            if(client == null) return builder.build();
            client.send(1007, alarmReq);
        }
        return builder.build();
    }
    
    public static void main(String[] args) {
		int gailv = -2;
		Double capacity = 33.5;
		capacity = capacity + capacity*(gailv/100.00);
		System.out.println(capacity);
	}
}
