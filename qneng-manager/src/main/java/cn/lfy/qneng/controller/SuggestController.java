package cn.lfy.qneng.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.lfy.common.model.Message;
import cn.lfy.qneng.model.Suggest;
import cn.lfy.qneng.service.SuggestService;

import com.manager.common.Constants;
import com.manager.common.exception.ApplicationException;
import com.manager.common.util.Page;
import com.manager.common.util.PageData;
import com.manager.common.util.RequestUtil;
import com.manager.model.Criteria;
import com.manager.model.PageInfo;

@Controller
@RequestMapping("/manager/suggest")
public class SuggestController implements Constants {

    public static final String LIST = "/system/suggest/list";

    public static final String ADD = "/system/suggest/edit";

    public static final String UPDATE = "/system/suggest/edit";

    public static final int listPageSize = 20;

    @Autowired
    private SuggestService suggestService;
    
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
        PageInfo<Suggest> result = suggestService.findListByCriteria(criteria, pageNum, page.getShowCount());
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
        suggestService.deleteByPrimaryKey(id);
        return builder.build();
    }

}
