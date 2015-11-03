package com.manager.web.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lfy.common.model.Message;

import com.manager.common.Constants;
import com.manager.common.exception.ApplicationException;
import com.manager.common.util.Page;
import com.manager.common.util.PageData;
import com.manager.common.util.RequestUtil;
import com.manager.model.Criteria;
import com.manager.model.PageInfo;
import com.manager.model.Role;
import com.manager.model.type.StateType;
import com.manager.service.RoleService;

@Controller
@RequestMapping("/manager/role")
public class RoleController implements Constants {

    public static final int listPageSize = 20;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request, Page page) throws ApplicationException {
    	PageData pd = new PageData(request);
        Integer typeId = RequestUtil.getInteger(request, "type");
//        Integer pageNum = RequestUtil.getInteger(request, "pageNum");
        Integer pageNum = RequestUtil.getInteger(request, "currentPage");
        if (pageNum == null || pageNum <= 0) {// 判断页码是否为空
            pageNum = 1;
        }
        Criteria criteria = new Criteria();
        PageInfo<Role> result = roleService.getByCriteria(criteria, pageNum, page.getShowCount());
        request.setAttribute("result", result);
        request.setAttribute("type", typeId);
        page.setTotalResult(result.getTotal());
        page.setEntityOrField(true);
        page.getCurrentResult();
        request.setAttribute("pd", pd);
        return "/system/role/list";
    }

    @RequestMapping("/del")
    @ResponseBody
    public Object del(HttpServletRequest request) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        Long id = RequestUtil.getLong(request, "id");
        Role record = new Role();
        record.setId(id);
        record.setState(StateType.INACTIVE.getId());
        roleService.updateByIdSelective(record);
        return builder.build();
    }

    @RequestMapping("/goadd")
    public String goAdd(HttpServletRequest request) throws ApplicationException {
    	request.setAttribute("uri", "add");
        return "/system/role/edit";
    }

    @RequestMapping("/detail")
    public String detail(HttpServletRequest request) throws ApplicationException {
        Long id = RequestUtil.getLong(request, "id");
        Role role = roleService.getById(id);
        request.setAttribute("role", role);
        request.setAttribute("uri", "update");
        return "/system/role/edit";
    }

    @RequestMapping("/add")
    @ResponseBody
    public Object add(HttpServletRequest request) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        String name=RequestUtil.getString(request, "name");
        String desc=RequestUtil.getString(request, "desc");
        Role record=new Role();
        record.setName(name);
        record.setType(1);
        record.setDesc(desc);
        record.setState(1);
        roleService.insert(record);
        return builder.build();
    }

    @RequestMapping("/update")
    @ResponseBody
    public Object update(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        Long id = RequestUtil.getLong(request, "id");
        String name=RequestUtil.getString(request, "name");
        Integer type=RequestUtil.getInteger(request, "type");
        String desc=RequestUtil.getString(request, "desc");
        Integer state = RequestUtil.getInteger(request, "state");
        if(null != id){
            Role record=new Role();
            record.setId(id);
            record.setName(name);
            record.setType(type);
            record.setDesc(desc);
            record.setState(state);
            roleService.updateByIdSelective(record);
        }
        return builder.build();
    }

}
