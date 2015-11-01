package cn.lfy.qneng.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.lfy.qneng.model.Station;
import cn.lfy.qneng.service.StationService;

import com.manager.common.Constants;
import com.manager.common.exception.ApplicationException;
import com.manager.common.util.Page;
import com.manager.common.util.PageData;
import com.manager.common.util.RequestUtil;
import com.manager.model.Admin;
import com.manager.model.Criteria;
import com.manager.model.PageInfo;
import com.manager.service.AdminService;

@Controller
@RequestMapping("/manager/station")
public class StationController implements Constants {

    public static final String LIST = "/system/station/list";

    public static final String ADD = "/system/station/edit";

    public static final String UPDATE = "/system/station/edit";

    public static final int listPageSize = 20;

    @Autowired
    private StationService stationService;
    
    @Autowired
    private AdminService adminService;

    /**
     * 用户详情列表
     * 
     * @param request
     * @return ModelAndView
     * @throws AdminException
     */
    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request, Page page) throws ApplicationException {
    	PageData pd = new PageData(request);
        String realName = RequestUtil.getString(request, "realName");
        String nameLike = RequestUtil.getString(request, "nameLike");
//        Integer pageNum = RequestUtil.getInteger(request, "pageNum");
        Integer pageNum = RequestUtil.getInteger(request, "currentPage");
        if (pageNum == null || pageNum <= 0) {// 判断页码是否为空
            pageNum = 1;
        }
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(nameLike)) {
            criteria.put("nameLike", nameLike);
        }
        if (StringUtils.isNotBlank(realName)) {
            criteria.put("nameLike", realName);
        }
        PageInfo<Station> result = stationService.findListByCriteria(criteria, pageNum, page.getShowCount());
        request.setAttribute("result", result);
        page.setPd(pd);
        page.setTotalResult(result.getTotal());
        page.setEntityOrField(true);
        page.getCurrentResult();
        request.setAttribute("pd", pd);
        return new ModelAndView(LIST);
    }
    
    /**
     * 删除用户
     * 
     * @param request
     * @return ModelAndView
     * @throws AdminException
     */
    @RequestMapping("/del")
    public ModelAndView del(HttpServletRequest request) throws ApplicationException {
        Long id = RequestUtil.getLong(request, "id");
        Station record = new Station();
        record.setId(id);
        stationService.updateByIdSelective(record);
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","success");
		mv.setViewName("common/save_result");
        return mv;
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
        Criteria criteria = new Criteria();
        List<Admin> list = adminService.findListByCriteria(criteria);
        request.setAttribute("users", list);
        return new ModelAndView(ADD);
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
        Station station = stationService.findById(id);
        request.setAttribute("entity", station);

        request.setAttribute("uri", "update");
        
        Criteria criteria = new Criteria();
        List<Admin> list = adminService.findListByCriteria(criteria);
        request.setAttribute("users", list);
        return new ModelAndView(UPDATE);
    }

    /**
     * 添加
     * 
     * @param request
     * @return ModelAndView
     * @throws AdminException
     */
    @RequestMapping("/add")
    public ModelAndView add(HttpServletRequest request) throws ApplicationException {
        String name = RequestUtil.getString(request, "name");
        String address = RequestUtil.getString(request, "address");
        String userId = RequestUtil.getString(request, "userId");

        Station record = new Station();
        record.setName(name);
        record.setAddress(address);
        if(userId != null) {
        	record.setUserId(Long.parseLong(userId));
        }
        record.setCreateTime(new Date());
        stationService.add(record);
        
        return new ModelAndView("/common/save_result", "msg", "success");
    }

    /**
     * 更新
     * 
     * @param request
     * @return ModelAndView
     * @throws AdminException
     */
    @RequestMapping("/update")
    public ModelAndView update(HttpServletRequest request,
            HttpServletResponse response) throws ApplicationException {
        Long id = RequestUtil.getLong(request, "id");
        String address = RequestUtil.getString(request, "address");
        String name = RequestUtil.getString(request, "name");
        Long userId = RequestUtil.getLong(request, "userId");

        Station record = new Station();
        record.setId(id);
        
        record.setName(name);
        record.setAddress(address);
        record.setUserId(userId);
        stationService.updateByIdSelective(record);
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","success");
		mv.setViewName("common/save_result");
        return mv;
    }
    
    

}
