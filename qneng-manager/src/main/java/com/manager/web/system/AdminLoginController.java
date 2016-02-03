package com.manager.web.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lfy.common.model.Message;
import cn.lfy.qneng.model.Station;
import cn.lfy.qneng.service.StationService;

import com.google.common.collect.Lists;
import com.manager.common.Constants;
import com.manager.common.ErrorCode;
import com.manager.common.exception.ApplicationException;
import com.manager.common.util.MessageDigestUtil;
import com.manager.common.web.Funcs;
import com.manager.model.Admin;
import com.manager.model.Criteria;
import com.manager.model.LoginAccount;
import com.manager.model.Menu;
import com.manager.model.type.StateType;
import com.manager.service.AdminMenuService;
import com.manager.service.AdminService;
import com.manager.service.MenuService;
import com.manager.service.RoleDefaultMenuService;

/**
 * 后台管理登录和主界面逻辑
 * @author liaopeng
 *
 */
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
    
    @Autowired
	private StationService stationService;
    
    /**
     * 管理后台主界面：根据session中的用户查询出该用户的权限菜单
     * @param request
     * @return
     * @throws ApplicationException
     */
    @RequestMapping("/manager/index")
    public String index(HttpServletRequest request) throws ApplicationException {
        LoginAccount account = Funcs.getSessionLoginAccount(request.getSession());
        request.setAttribute("USER_ID", account.getId());
        request.setAttribute("USER_NAME", account.getUser().getRealName());
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

    /**
     * 跳转登录页
     * @return
     * @throws ApplicationException
     */
    @RequestMapping("/")
    public String manager() throws ApplicationException {
        return ADMIN_LOGIN;
    }
    /**
     * 跳转登录页
     * @return
     * @throws ApplicationException
     */
    @RequestMapping("/login")
    public String login() throws ApplicationException {
        return ADMIN_LOGIN;
    }

    /**
     * 登录验证接口
     * @param request
     * @param response
     * @return
     * @throws ApplicationException
     */
    @RequestMapping("/dologin")
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
    	doLogin(request, response);
    	return builder.build();
    }

    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String code = request.getParameter("code");

        if (null == username || username.trim().length() == 0
                || null == password || password.length() == 0) {
            throw ApplicationException.newInstance(ErrorCode.PARAM_ILLEGAL, "用户名或密码");
        }
        if(code == null || code.trim().length() == 0) {
        	throw ApplicationException.newInstance(ErrorCode.PARAM_ILLEGAL, "验证码");
        }
        String codeS = (String)request.getSession().getAttribute("sessionCode");
        if(!code.equalsIgnoreCase(codeS)) {
        	throw ApplicationException.newInstance(ErrorCode.SEC_ERROR);
        }
        try {
            password = MessageDigestUtil.getSHA256(password).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        LoginAccount account = getAdminLoginUser(username);
        if (account == null) {
            throw ApplicationException.newInstance(ErrorCode.NOT_EXIST, "用户名");
        }
        Admin user = account.getUser();
        if (user == null) {
            throw ApplicationException.newInstance(ErrorCode.NOT_EXIST, "用户名");
        }
        if (user.getState().equals(StateType.INACTIVE)) {
        	throw ApplicationException.newInstance(ErrorCode.ERROR_USER_INACTIVE);
        }
        if (!password.equals(user.getPassword())) {
        	throw ApplicationException.newInstance(ErrorCode.ERROR_PASSWORD);
        }
        account.setId(user.getId());
        
        Station station = null;
        Criteria criteria = new Criteria();
        criteria.put("userId", user.getId());
        List<Station> list = stationService.findListByCriteria(criteria);
        if(list != null && !list.isEmpty()) {
        	station = list.get(0);
        }
        account.setStation(station);
        request.getSession().setAttribute(SESSION_LOGIN_USER, account);
        request.getSession().removeAttribute("sessionCode");
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
        return "redirect:login";
    }
    

}
