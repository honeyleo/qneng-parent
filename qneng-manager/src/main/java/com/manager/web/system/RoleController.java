package com.manager.web.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.lfy.common.model.Message;

import com.alibaba.fastjson.JSONObject;
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
        return "/system/role/role-list";
    }
    @RequestMapping("/api/list")
    @ResponseBody
    public Object api_list(HttpServletRequest request, Page page) throws ApplicationException {
        Integer pageNum = RequestUtil.getInteger(request, "currentPage");
        Criteria criteria = new Criteria();
        PageInfo<Role> result = roleService.getByCriteria(criteria, pageNum, page.getShowCount());
        Message.Builder builder = Message.newBuilder();
        builder.total(result.getTotal());
        builder.data(result.getData());
        return builder.build();
    }

    @RequestMapping("/del")
    @ResponseBody
    public Object del(HttpServletRequest request) throws ApplicationException {
        Long id = RequestUtil.getLong(request, "id");
        Role record = new Role();
        record.setId(id);
        record.setState(StateType.INACTIVE.getId());
        roleService.updateByIdSelective(record);
        return Message.newBuilder().build();
    }

    @RequestMapping("/goadd")
    public String goAdd(HttpServletRequest request) throws ApplicationException {
    	request.setAttribute("uri", "add");
        return "/system/role/edit";
    }

    @RequestMapping("/detail")
    @ResponseBody
    public Object detail(HttpServletRequest request) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        Long id = RequestUtil.getLong(request, "id");
        Role role = roleService.getById(id);
        builder.data(role);
        return builder.build();
    }

    @RequestMapping("/add")
    @ResponseBody
    public Object add(Role role, HttpServletRequest request) throws ApplicationException {
        roleService.insert(role);
        return Message.newBuilder().build();
    }

    @RequestMapping("/update")
    @ResponseBody
    public Object update(Role role, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
        roleService.updateByIdSelective(role);
        return Message.newBuilder().build();
    }

}
