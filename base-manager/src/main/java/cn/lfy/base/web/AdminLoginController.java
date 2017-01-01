package cn.lfy.base.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cn.lfy.base.Constants;
import cn.lfy.base.model.Admin;
import cn.lfy.base.model.LoginAccount;
import cn.lfy.base.model.Menu;
import cn.lfy.base.model.Role;
import cn.lfy.base.model.type.StateType;
import cn.lfy.base.service.AdminRoleService;
import cn.lfy.base.service.AdminService;
import cn.lfy.base.service.RoleDefaultMenuService;
import cn.lfy.common.framework.exception.ApplicationException;
import cn.lfy.common.framework.exception.ErrorCode;
import cn.lfy.common.utils.MessageDigestUtil;

@Controller
public class AdminLoginController {

    private static final String ADMIN_LOGIN = "/system/login";

    private static final String INDEX = "/system/common/index";

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminRoleService adminRoleService;
    
    @Autowired
	private RoleDefaultMenuService roleDefaultMenuService;
    
    @RequestMapping("/manager/index")
    public String index(HttpServletRequest request) throws ApplicationException {
    	LoginAccount account = (LoginAccount) request.getSession().getAttribute(Constants.SESSION_LOGIN_USER);
    	List<Menu> menus = roleDefaultMenuService.selectMenuListByRoleIds(account.getRoleIdList());
        account.setMenus(menus);
        List<Menu> menuList = Lists.newArrayList();
        Set<String> uriSet = Sets.newTreeSet();
        for(Menu menu : menus) {
        	uriSet.add(menu.getUrl());
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
        account.setUriSet(uriSet);
        request.setAttribute("menuList", menuList);
        request.setAttribute("realName", account.getUser().getRealName());
        return INDEX;
    }
    
    @RequestMapping("/manager/home")
    public String home() throws ApplicationException {
        return "/system/common/home";
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
            throw ex;
        }
        return new ModelAndView(new RedirectView(request.getContextPath() + "/manager/index"), "model", null);
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
            throw new ApplicationException(ErrorCode.PARAM_ILLEGAL, "", new String[]{"用户名或密码"});
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
        	throw ApplicationException.newInstance(ErrorCode.ERROR, "密码错误");
        }
        account.setId(user.getId());
        request.getSession().setAttribute(Constants.SESSION_LOGIN_USER, account);
    }
    
    private LoginAccount getAdminLoginUser(String username){
        Admin admin = adminService.findByUsername(username);
        if(admin == null){
            return null;
        }
        List<Role> roleList = adminRoleService.getRoleListByAdminId(admin.getId());
        LoginAccount account = new LoginAccount();
        account.setRoles(roleList);
        Admin user = new Admin();
        user.setId(admin.getId());
        user.setUsername(admin.getUsername());
        user.setPassword(admin.getPassword());
        user.setRealName(admin.getRealName());
        user.setEmail(admin.getEmail());
        user.setState(admin.getState());
        account.setUser(user);

        return account;
    }

    @RequestMapping("/manager/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(Constants.SESSION_LOGIN_USER);
        session.invalidate();
        return "redirect:/login";
    }
    

}
