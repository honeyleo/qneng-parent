package com.manager.web.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.common.collect.Lists;
import com.manager.common.Constants;
import com.manager.common.ErrorCode;
import com.manager.common.exception.ApplicationException;
import com.manager.common.util.DwzJsonUtil;
import com.manager.common.util.MessageDigestUtil;
import com.manager.common.util.RequestUtil;
import com.manager.common.web.Funcs;
import com.manager.model.Admin;
import com.manager.model.LoginAccount;
import com.manager.model.Menu;
import com.manager.model.type.StateType;
import com.manager.service.AdminMenuService;
import com.manager.service.AdminService;
import com.manager.service.MenuService;
import com.manager.service.RoleDefaultMenuService;

@Controller
public class AdminLoginController implements Constants {

    private static final String ADMIN_LOGIN = "/system/login";

    private static final String INDEX = "/system/common/index";


    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminMenuService adminMenuService;

    @Autowired
    private MenuService menuService;

    @Autowired
	private RoleDefaultMenuService roleDefaultMenuService;
    
    @RequestMapping("/manager/index")
    public String index(HttpServletRequest request) throws ApplicationException {
        LoginAccount account = Funcs.getSessionLoginAccount(request.getSession());
        List<Menu> menus = roleDefaultMenuService.getMenuListByRoleId(account.getUser().getRoleId());
        account.setMenus(menus);
        List<Menu> menuList = Lists.newArrayList();
        for(Menu menu : menus) {
        	if(menu.getParentId() == -1) {
        		continue;
        	}
        	else if(menu.getParentId() == 1) {
        		menuList.add(menu);
        	} else if(menu.getOnMenu() == 1) {
        		Menu parent = null;
        		for(Menu m : menuList) {
        			if(m.getId() == menu.getParentId()) {
        				parent = m;
        				break;
        			}
        		}
        		if(parent.getChildList() == null) {
        			 List<Menu> childList = new ArrayList<Menu>();
        			 parent.setChildList(childList);
        		}
        		parent.getChildList().add(menu);
        	}
        }
        request.setAttribute("menuList", menuList);
        return INDEX;
    }

    @RequestMapping("/")
    public String manager() throws ApplicationException {
        return ADMIN_LOGIN;
    }
    
    @RequestMapping("/login")
    public String login() throws ApplicationException {
        return ADMIN_LOGIN;
    }

    @RequestMapping("/dologin")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
        try {
            doLogin(request, response);
        } catch (ApplicationException ex) {
            String errorMsg = ex.getMessage();
            if (RequestUtil.isAjax(request)) {
                return new ModelAndView(JSON_VIEW, JSON_ROOT,
                        DwzJsonUtil.getErrorStatusMsg(errorMsg));
            } else {
                Map<String, Object> model = new HashMap<String, Object>();
                model.put("loginErrInfo", errorMsg);
                return new ModelAndView(ADMIN_LOGIN, "model", model);
            }
        }
        if (RequestUtil.isAjax(request)) {
            return new ModelAndView(JSON_VIEW, JSON_ROOT,
                    DwzJsonUtil.getOkStatusMsg(null));
        } else {
            return new ModelAndView(new RedirectView(request.getContextPath() + "/manager/index"), "model", null);
        }
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            password = MessageDigestUtil.getSHA256(password).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null == username || username.trim().length() == 0
                || null == password || password.length() == 0) {
            throw ApplicationException.newInstance(ErrorCode.PARAM_ILLEGAL, "用户名或密码");
        }
        LoginAccount account = getAdminLoginUser(username);
        Admin user = account.getUser();
        if (user == null) {
            throw ApplicationException.newInstance(ErrorCode.ERROR);
        }
        if (user.getState().equals(StateType.INACTIVE)) {
        	throw ApplicationException.newInstance(ErrorCode.ERROR);
        }
        if (!password.equals(user.getPassword())) {
        	throw ApplicationException.newInstance(ErrorCode.ERROR);
        }
        account.setId(user.getId());
        request.getSession().setAttribute(SESSION_LOGIN_USER, account);
    }
    
    private LoginAccount getAdminLoginUser(String username){
        Admin admin = adminService.findByUsername(username);
        if(admin == null){
            return null;
        }
        LoginAccount account = new LoginAccount();
        Admin user = new Admin();
        user.setId(admin.getId());
        user.setUsername(admin.getUsername());
        user.setPassword(admin.getPassword());
        user.setRealName(admin.getRealName());
        user.setEmail(admin.getEmail());
        user.setRoleId(admin.getRoleId());
        user.setState(admin.getState());
        user.setOperatorId(admin.getOperatorId());
        account.setUser(user);

        return account;
    }

    @RequestMapping("/manager/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(SESSION_LOGIN_USER);
        session.invalidate();
        return ADMIN_LOGIN;
    }
    

}
