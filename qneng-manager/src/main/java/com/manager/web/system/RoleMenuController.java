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
import com.manager.common.exception.AdminException;
import com.manager.common.util.DwzJsonUtil;
import com.manager.common.util.RequestUtil;
import com.manager.model.Menu;
import com.manager.model.Role;
import com.manager.service.MenuService;
import com.manager.service.RoleDefaultMenuService;
import com.manager.service.RoleService;

@Controller
@RequestMapping("/manager/system/role_menu")
public class RoleMenuController implements Constants
{

    @Autowired
	private MenuService menuService;

    @Autowired
	private RoleService roleService;
	
    @Autowired
	private RoleDefaultMenuService roleDefaultMenuService;

	@RequestMapping("/list")
	public String listSystemMenus(HttpServletRequest request, HttpServletResponse response) throws AdminException
	{
		Long roleId = RequestUtil.getLong(request, "roleId");
		Role role=roleService.getById(roleId);
		if (null == role)
		{
			throw new AdminException("角色不存在！");
		}
		
		List<Menu> menus = menuService.findMenuList();
		List<Menu> roleMenus = roleDefaultMenuService.getMenuListByRoleId(roleId);
		List<Long> roleMenuIds = new ArrayList<Long>();
		for (Menu menu : roleMenus)
		{
		    roleMenuIds.add(menu.getId());
		}
		request.setAttribute("menus", menus);
		request.setAttribute("roleMenuIds", roleMenuIds);
		return "/manager/system/role/role_menu_list";
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request) throws AdminException
	{
		Long roleId = RequestUtil.getLong(request, "roleId");
		List<Long> menuIds = RequestUtil.getLongs(request, "treecheckbox");
		if (null == roleId || roleId <= 0)
		{
			throw new AdminException("非法操作！");
		}
		if (null == menuIds || menuIds.isEmpty())
        {
            throw new AdminException("请选择要保存的节点！");
        }
		Role role = roleService.getById(roleId);
		if (null == role)
		{
			throw new AdminException("用户不存在！");
		}
		roleDefaultMenuService.saveDefaultMenus(role, menuIds); // 更新权限菜单
		return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getOkStatusMsg(null));
	}
}
