package com.manager.web.system;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.manager.common.Constants;
import com.manager.common.ErrorCode;
import com.manager.common.exception.ApplicationException;
import com.manager.common.util.DwzJsonUtil;
import com.manager.common.util.RequestUtil;
import com.manager.model.Admin;
import com.manager.model.Menu;
import com.manager.service.AdminMenuService;
import com.manager.service.AdminService;
import com.manager.service.MenuService;

@Controller
@RequestMapping("/manager/system/admin_menu")
public class AdminMenuController implements Constants
{

    @Autowired
	private MenuService menuService;

    @Autowired
	private AdminService adminService;
	
    @Autowired
	private AdminMenuService adminMenuService;

	@RequestMapping("/list")
	public String listSystemMenus(HttpServletRequest request, HttpServletResponse response) throws ApplicationException
	{
		Long adminId = RequestUtil.getLong(request, "adminId");
		Admin admin=adminService.findById(adminId);
		if (null == admin)
		{
			throw ApplicationException.newInstance(ErrorCode.NOT_EXIST, "用户");
		}
		List<Menu> menus = menuService.findMenuList();
		List<Menu> adminMenus = adminMenuService.getMeneListByAdminId(admin.getId());
		List<Long> adminMenuIds = new ArrayList<Long>();
		for (Menu menu : adminMenus)
		{
			adminMenuIds.add(menu.getId());
		}
		request.setAttribute("menus", menus);
		request.setAttribute("adminMenuIds", adminMenuIds);
		return "/manager/system/admin/admin_menu_list";
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request) throws ApplicationException
	{
		Long adminId = RequestUtil.getLong(request, "adminId");
		List<Long> menuIds = RequestUtil.getLongs(request, "treecheckbox");
		if (null == adminId || adminId <= 0)
		{
			throw ApplicationException.newInstance(ErrorCode.PARAM_ILLEGAL, "adminId");
		}
		if (null == menuIds || menuIds.isEmpty())
        {
			throw ApplicationException.newInstance(ErrorCode.PARAM_ILLEGAL, "menuIds");
        }
		Admin admin = adminService.findById(adminId);
		if (null == admin)
		{
			throw ApplicationException.newInstance(ErrorCode.NOT_EXIST, "用户");
		}
		adminMenuService.saveAdminMenus(admin, menuIds); // 更新权限菜单
		return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getOkStatusMsg(null));
	}
}
