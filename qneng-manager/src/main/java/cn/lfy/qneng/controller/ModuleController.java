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

import cn.lfy.qneng.model.Bunch;
import cn.lfy.qneng.model.Module;
import cn.lfy.qneng.service.BunchService;
import cn.lfy.qneng.service.ModuleService;

import com.manager.common.Constants;
import com.manager.common.exception.ApplicationException;
import com.manager.common.util.Page;
import com.manager.common.util.PageData;
import com.manager.common.util.RequestUtil;
import com.manager.model.Criteria;
import com.manager.model.PageInfo;

@Controller
@RequestMapping("/manager/module")
public class ModuleController implements Constants {

    public static final String LIST = "/system/module/list";

    public static final String ADD = "/system/module/edit";

    public static final String UPDATE = "/system/module/edit";

    public static final int listPageSize = 20;

    @Autowired
    private BunchService bunchService;
    @Autowired
    private ModuleService moduleService;
    
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
        PageInfo<Module> result = moduleService.findListByCriteria(criteria, pageNum, page.getShowCount());
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
        Module record = new Module();
        record.setId(id);
        moduleService.updateByIdSelective(record);
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
        List<Bunch> list = bunchService.findListByCriteria(criteria);
        request.setAttribute("bunchs", list);
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
        Module module = moduleService.findById(id);
        request.setAttribute("entity", module);

        request.setAttribute("uri", "update");
        
        Criteria criteria = new Criteria();
        List<Bunch> list = bunchService.findListByCriteria(criteria);
        request.setAttribute("bunchs", list);
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
        String no = RequestUtil.getString(request, "no");
        String appSecret = RequestUtil.getString(request, "appSecret");
        Long bunchId = RequestUtil.getLong(request, "bunchId");

        Module record = new Module();
        record.setName(name);
        record.setNo(no);
        record.setAppSecret(appSecret);
        record.setBunchId(bunchId);
        record.setCreateTime(new Date());
        moduleService.add(record);
        
        // 根据角色添加默认权限
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","success");
		mv.setViewName("common/save_result");
        return mv;
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
        String name = RequestUtil.getString(request, "name");
        String no = RequestUtil.getString(request, "no");
        String appSecret = RequestUtil.getString(request, "appSecret");
//        String model = RequestUtil.getString(request, "model");
//        String manufactory = RequestUtil.getString(request, "manufactory");
//        String installdate = RequestUtil.getString(request, "installdate");
//        Double maxVolt = RequestUtil.getDouble(request, "maxVolt");
//        Double maxCurr = RequestUtil.getDouble(request, "maxCurr");
//        Double power = RequestUtil.getDouble(request, "power");
        Long bunchId = RequestUtil.getLong(request, "bunchId");

        Module record = new Module();
        record.setId(id);
        record.setName(name);
        record.setNo(no);
        record.setAppSecret(appSecret);
        record.setBunchId(bunchId);
        moduleService.updateByIdSelective(record);
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","success");
		mv.setViewName("common/save_result");
        return mv;
    }
    
    

}
