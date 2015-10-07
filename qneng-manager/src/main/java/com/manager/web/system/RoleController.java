package com.manager.web.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.manager.common.Constants;
import com.manager.common.exception.AdminException;
import com.manager.common.util.DwzJsonUtil;
import com.manager.common.util.RequestUtil;
import com.manager.model.Criteria;
import com.manager.model.PageInfo;
import com.manager.model.Role;
import com.manager.model.type.StateType;
import com.manager.service.RoleService;

@Controller
@RequestMapping("/manager/system/role")
public class RoleController implements Constants {

    public static final int listPageSize = 20;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request) throws AdminException {
        Integer typeId = RequestUtil.getInteger(request, "type");
        Integer pageNum = RequestUtil.getInteger(request, "pageNum");
        if (pageNum == null || pageNum <= 0) {// 判断页码是否为空
            pageNum = 1;
        }
        Criteria criteria = new Criteria();
        PageInfo<Role> result = roleService.getByCriteria(criteria, pageNum, listPageSize);
        request.setAttribute("result", result);
        request.setAttribute("type", typeId);
        return "/manager/system/role/role_list";
    }

    @RequestMapping("/del")
    public ModelAndView del(HttpServletRequest request) throws AdminException {
        Long id = RequestUtil.getLong(request, "id");
        Role record = new Role();
        record.setId(id);
        record.setState(StateType.INACTIVE.getId());
        roleService.updateByIdSelective(record);
        return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getOkStatusMsg("删除成功"));
    }

    @RequestMapping("/goadd")
    public String goAdd(HttpServletRequest request) throws AdminException {
        return "/manager/system/role/role_add";
    }

    @RequestMapping("/detail")
    public String detail(HttpServletRequest request) throws AdminException {
        Long id = RequestUtil.getLong(request, "id");
        Role role = roleService.getById(id);
        request.setAttribute("role", role);
        return "/manager/system/role/role_detail";
    }

    @RequestMapping("/add")
    public ModelAndView add(HttpServletRequest request) throws AdminException {
        String name=RequestUtil.getString(request, "name");
        Integer type=RequestUtil.getInteger(request, "type");
        String desc=RequestUtil.getString(request, "desc");
        Role record=new Role();
        record.setName(name);
        record.setType(type);
        record.setDesc(desc);
        record.setState(1);
        roleService.insert(record);
        return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getOkStatusMsg("添加成功"));
    }

    @RequestMapping("/update")
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response) throws AdminException {
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
        return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getOkStatusMsg("更新成功"));
    }

}
