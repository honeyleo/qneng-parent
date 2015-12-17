package com.manager.web.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.lfy.common.job.model.ScheduleJob;
import cn.lfy.common.model.Message;

import com.manager.common.Constants;
import com.manager.common.ErrorCode;
import com.manager.common.exception.ApplicationException;
import com.manager.common.util.Page;
import com.manager.common.util.RequestUtil;
import com.manager.service.ScheduleJobService;

@Controller
@RequestMapping("/manager/scheduleJob")
public class ScheduleJobController implements Constants {

    public static final String ADMIN_LIST = "/system/scheduleJob/list";

    public static final String ADD_ADMIN = "/system/scheduleJob/edit";

    public static final String UPDATE_ADMIN = "/system/scheduleJob/edit";

    @Autowired
    private ScheduleJobService scheduleJobService;

    /**
     * 用户详情列表
     * 
     * @param request
     * @return ModelAndView
     * @throws AdminException
     */
    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request, Page page) throws ApplicationException {
        List<ScheduleJob> list;
		try {
			list = scheduleJobService.getAllJob();
			request.setAttribute("list", list);
		} catch (SchedulerException e) {
			throw ApplicationException.newInstance(ErrorCode.ERROR);
		}
        return new ModelAndView(ADMIN_LIST);
    }
    
    /**
     * 删除用户
     * 
     * @param request
     * @return ModelAndView
     * @throws AdminException
     */
    @RequestMapping("/del")
    @ResponseBody
    public Object del(ScheduleJob scheduleJob) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
    	try {
			scheduleJobService.deleteJob(scheduleJob);
		} catch (SchedulerException e) {
			throw ApplicationException.newInstance(ErrorCode.ERROR);
		}
        return builder.build();
    }
    
    /**
     * 添加
     * 
     * @param request
     * @return
     * @throws AdminException
     */
    @RequestMapping("/goadd")
    public ModelAndView goAdd(HttpServletRequest request) throws ApplicationException {
        request.setAttribute("uri", "add");
        return new ModelAndView(ADD_ADMIN);
    }

    /**
     * 详情
     * 
     * @param request
     * @return
     * @throws AdminException
     */
    @RequestMapping("/detail")
    public ModelAndView detail(HttpServletRequest request) throws ApplicationException {
        Long id = RequestUtil.getLong(request, "id");
        
        ScheduleJob scheduleJob;
		try {
			scheduleJob = scheduleJobService.viewJob(id);
			request.setAttribute("entity", scheduleJob);
		} catch (SchedulerException e) {
			throw ApplicationException.newInstance(ErrorCode.ERROR);
		}

        request.setAttribute("uri", "update");
        return new ModelAndView(UPDATE_ADMIN);
    }
    
    /**
     * 添加
     * 
     * @param request
     * @return ModelAndView
     * @throws AdminException
     */
    @RequestMapping("/add")
    @ResponseBody
    public Object add(ScheduleJob scheduleJob) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        try {
			scheduleJobService.createJob(scheduleJob);
		} catch (SchedulerException e) {
			throw ApplicationException.newInstance(ErrorCode.ERROR);
		}
        
        return builder.build();
    }

    /**
     * 更新
     * 
     * @param request
     * @return ModelAndView
     * @throws AdminException
     */
    @RequestMapping("/update")
    @ResponseBody
    public Object update(ScheduleJob scheduleJob) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        try {
			scheduleJobService.updateJob(scheduleJob);
		} catch (SchedulerException e) {
			throw ApplicationException.newInstance(ErrorCode.ERROR);
		}
        return builder.build();
    }
    
}
