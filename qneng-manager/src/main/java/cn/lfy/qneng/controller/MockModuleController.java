package cn.lfy.qneng.controller;

import java.util.Date;
import java.util.List;
import java.util.Random;

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
import cn.lfy.qneng.gateway.model.NodeConfigReq;
import cn.lfy.qneng.model.Bunch;
import cn.lfy.qneng.model.MockModuleData;
import cn.lfy.qneng.model.Module;
import cn.lfy.qneng.model.ModuleData;
import cn.lfy.qneng.service.BunchService;
import cn.lfy.qneng.service.ModuleDataService;
import cn.lfy.qneng.service.ModuleService;

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
        Long id = RequestUtil.getLong(request, "id");
        Module module = moduleService.findById(id);
        MockModuleData data = new MockModuleData();
        data.setId(module.getId());
        data.setNo(module.getNo());
        data.setInputVolt(module.getMaxVolt());
        request.setAttribute("entity", data);
        return new ModelAndView("/system/mockmodule/data");
    }
    
    @RequestMapping("/dataSubmit")
    @ResponseBody
    public Object dataSubmit(HttpServletRequest request, Page page) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        Long id = RequestUtil.getLong(request, "id");
        Module module = moduleService.findById(id);
        MockModuleData data = new MockModuleData();
        data.setId(module.getId());
        data.setNo(module.getNo());
        data.setInputVolt(module.getMaxVolt());
        request.setAttribute("entity", data);
        return builder.build();
    }
    
    @RequestMapping("/dataSubmit2")
    @ResponseBody
    public Object dataSubmit2(HttpServletRequest request, Page page, MockModuleData mockModuleData) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        Long id = RequestUtil.getLong(request, "id");
        Module module = moduleService.findById(id);
        if(module != null) {
        	Bunch bunch = bunchService.findById(module.getBunchId());
        	if(bunch == null) return builder.build();
        	long startTime = DateUtils.strToDate(mockModuleData.getStartTime(), DateUtils.LONGDATE_DATETIME).getTime();
        	long endTime = DateUtils.strToDate(mockModuleData.getEndTime(), DateUtils.LONGDATE_DATETIME).getTime();
        	while(startTime < endTime) {
        		ModuleData moduleData = new ModuleData();
				moduleData.setStationId(bunch.getStationId());
				moduleData.setBunchId(module.getBunchId());
				moduleData.setModuleId(module.getId());
				moduleData.setNo(module.getNo());
				moduleData.setInputVolt(mockModuleData.getInputVolt());
				Double outvolt = mockModuleData.getOutvolt();
				Random random = new Random(startTime);
				outvolt = outvolt - random.nextInt(outvolt.intValue());
				moduleData.setOutvolt(outvolt);
				Double curr = mockModuleData.getCurr();
				curr = curr - random.nextInt(curr.intValue());
				moduleData.setCurr(curr);
				Double temp = mockModuleData.getTemp();
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
        return builder.build();
    }
}
