package com.manager.web.system;

import java.util.Iterator;
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

import com.google.common.base.Splitter;
import com.manager.common.Constants;
import com.manager.common.ErrorCode;
import com.manager.common.exception.ApplicationException;
import com.manager.common.util.MessageDigestUtil;
import com.manager.common.util.Page;
import com.manager.common.util.PageData;
import com.manager.common.util.RequestUtil;
import com.manager.model.Admin;
import com.manager.model.Criteria;
import com.manager.model.PageInfo;
import com.manager.model.Role;
import com.manager.model.type.StateType;
import com.manager.service.AdminMenuService;
import com.manager.service.AdminService;
import com.manager.service.RoleService;

@Controller
@RequestMapping("/manager/admin")
public class AdminController implements Constants {

    public static final String ADMIN_LIST = "/system/admin/list";

    public static final String ADD_ADMIN = "/system/admin/edit";

    public static final String UPDATE_ADMIN = "/system/admin/edit";

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;
    
    @Autowired
    private AdminMenuService adminMenuService;

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
        String username = RequestUtil.getString(request, "username");
//        Integer pageNum = RequestUtil.getInteger(request, "pageNum");
        Integer pageNum = RequestUtil.getInteger(request, "currentPage");
        if (pageNum == null || pageNum <= 0) {// 判断页码是否为空
            pageNum = 1;
        }
        Criteria criteria = new Criteria();
        if (StringUtils.isNotBlank(username)) {
            criteria.put("usernameLike", username);
        }
        if (StringUtils.isNotBlank(realName)) {
            criteria.put("realNameLike", realName);
        }
        PageInfo<Admin> result = adminService.findListByCriteria(criteria, pageNum, page.getShowCount());
        request.setAttribute("result", result);
        page.setPd(pd);
        page.setTotalResult(result.getTotal());
        page.setEntityOrField(true);
        page.getCurrentResult();
        request.setAttribute("pd", pd);
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
    public Object del(HttpServletRequest request) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        Long id = RequestUtil.getLong(request, "id");
        Admin record = new Admin();
        record.setId(id);
        record.setState(StateType.INACTIVE.getId());
        if(!(id == 1 || id == 2)) {
        	adminService.deleteByPrimaryKey(id);
        }
        return builder.build();
    }
    
    /**
     * 删除用户
     * 
     * @param request
     * @return ModelAndView
     * @throws AdminException
     */
    @RequestMapping("/delAll")
    @ResponseBody
    public Object delAll(HttpServletRequest request) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        String userIds = RequestUtil.getString(request, "userIds");
        Iterator<String> it = Splitter.on(",").trimResults().split(userIds).iterator();
        while(it.hasNext()) {
        	Long id = Long.parseLong(it.next());
        	if(!(id == 1 || id == 2)) {
                adminService.deleteByPrimaryKey(id);
        	}
        	
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
        List<Role> roles = roleService.getByCriteria(new Criteria());
        request.setAttribute("roles", roles);
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
        Admin admin = adminService.findById(id);
        request.setAttribute("admin", admin);
        request.setAttribute("operatorId", admin.getOperatorId());

        List<Role> roles = roleService.getByCriteria(new Criteria());
        request.setAttribute("roles", roles);
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
    public Object add(HttpServletRequest request) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        String username = RequestUtil.getString(request, "username");
        Admin extAdmin = adminService.findByUsername(username);
        if (extAdmin != null) {
            throw ApplicationException.newInstance(ErrorCode.EXIST, "用户名");
        }
        String password = RequestUtil.getString(request, "password");
        try {
            password = MessageDigestUtil.getSHA256(password).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String realName = RequestUtil.getString(request, "realName");
        String email = RequestUtil.getString(request, "email");
        String phone = RequestUtil.getString(request, "phone");
        String address = RequestUtil.getString(request, "address");
        Long roleId = RequestUtil.getLong(request, "roleId");
        Long newOperatorId = RequestUtil.getLong(request, "operator.id");

        Admin record = new Admin();
        record.setUsername(username);
        record.setPassword(password);
        record.setRealName(realName);
        record.setEmail(email);
        record.setPhone(phone);
        record.setAddress(address);
        record.setRoleId(roleId);
        record.setState(1);
        if (roleId > 2) {
        	record.setOperatorId(newOperatorId);
        }
        Long adminId=adminService.add(record);
        
        // 根据角色添加默认权限
        adminMenuService.addAdminDefaultMenu(adminId, roleId);
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
        String password = RequestUtil.getString(request, "password");
        String realName = RequestUtil.getString(request, "realName");
        String email = RequestUtil.getString(request, "email");
        String phone = RequestUtil.getString(request, "phone");
        String address = RequestUtil.getString(request, "address");
        Long roleId = RequestUtil.getLong(request, "roleId");
        Integer state = RequestUtil.getInteger(request, "state");

        Admin record = new Admin();
        record.setId(id);
        if (StringUtils.isNotBlank(password)) {// 判断password是否为空
            try {
                password = MessageDigestUtil.getSHA256(password).toUpperCase();
            } catch (Exception e) {
                e.printStackTrace();
            }
            record.setPassword(password);
        }
        if (StringUtils.isNotBlank(realName)) {// 判断realname是否为空
            record.setRealName(realName);
        }
        record.setEmail(email);
        record.setPhone(phone);
        record.setAddress(address);
        record.setRoleId(roleId);
        record.setState(state);
        adminService.updateByIdSelective(record);
        return builder.build();
    }
    
    

}
