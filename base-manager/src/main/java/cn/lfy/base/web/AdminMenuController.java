package cn.lfy.base.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lfy.base.model.Admin;
import cn.lfy.base.model.Menu;
import cn.lfy.base.service.AdminMenuService;
import cn.lfy.base.service.AdminService;
import cn.lfy.base.service.MenuService;
import cn.lfy.common.framework.exception.ApplicationException;
import cn.lfy.common.framework.exception.ErrorCode;
import cn.lfy.common.model.Message;
import cn.lfy.common.utils.RequestUtil;

@Controller
@RequestMapping("/manager/system/admin_menu")
public class AdminMenuController
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
	@ResponseBody
	public Object save(HttpServletRequest request) throws ApplicationException
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
		return Message.newBuilder().build();
	}
}
