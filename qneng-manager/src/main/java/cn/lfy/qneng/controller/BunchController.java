package cn.lfy.qneng.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.lfy.common.model.Message;
import cn.lfy.qneng.model.Bunch;
import cn.lfy.qneng.model.Station;
import cn.lfy.qneng.service.BunchService;
import cn.lfy.qneng.service.StationService;

import com.manager.common.Constants;
import com.manager.common.exception.ApplicationException;
import com.manager.common.util.Page;
import com.manager.common.util.PageData;
import com.manager.common.util.RequestUtil;
import com.manager.model.Criteria;
import com.manager.model.PageInfo;

@Controller
@RequestMapping("/manager/bunch")
public class BunchController implements Constants {

    public static final String LIST = "/system/bunch/list";

    public static final String ADD = "/system/bunch/edit";

    public static final String UPDATE = "/system/bunch/edit";

    public static final int listPageSize = 20;

    @Autowired
    private BunchService bunchService;
    @Autowired
    private StationService stationService;
    
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
        PageInfo<Bunch> result = bunchService.findListByCriteria(criteria, pageNum, page.getShowCount());
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
    @ResponseBody
    public Object del(HttpServletRequest request) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        Long id = RequestUtil.getLong(request, "id");
        Bunch record = new Bunch();
        record.setId(id);
        bunchService.updateByIdSelective(record);
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
        Criteria criteria = new Criteria();
        List<Station> list = stationService.findListByCriteria(criteria);
        request.setAttribute("stations", list);
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
        Bunch bunch = bunchService.findById(id);
        request.setAttribute("entity", bunch);

        request.setAttribute("uri", "update");
        
        Criteria criteria = new Criteria();
        List<Station> list = stationService.findListByCriteria(criteria);
        request.setAttribute("stations", list);
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
    @ResponseBody
    public Object add(HttpServletRequest request) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        String name = RequestUtil.getString(request, "name");
        Integer element = RequestUtil.getInteger(request, "element");
        Integer line = RequestUtil.getInteger(request, "line");
        Integer row = RequestUtil.getInteger(request, "row");
        Long stationId = RequestUtil.getLong(request, "stationId");

        Bunch record = new Bunch();
        record.setName(name);
        record.setElement(element);
        record.setLine(line);
        record.setRow(row);
        record.setStationId(stationId);
        
        record.setCreateTime(new Date());
        bunchService.add(record);
        
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
    public Object update(HttpServletRequest request,
            HttpServletResponse response) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        Long id = RequestUtil.getLong(request, "id");
        String name = RequestUtil.getString(request, "name");
        Integer element = RequestUtil.getInteger(request, "element");
        Integer line = RequestUtil.getInteger(request, "line");
        Integer row = RequestUtil.getInteger(request, "row");
        Long stationId = RequestUtil.getLong(request, "stationId");

        Bunch record = new Bunch();
        record.setId(id);
        
        record.setName(name);
        record.setElement(element);
        record.setLine(line);
        record.setRow(row);
        record.setStationId(stationId);
        bunchService.updateByIdSelective(record);
        return builder.build();
    }
    
    

}
