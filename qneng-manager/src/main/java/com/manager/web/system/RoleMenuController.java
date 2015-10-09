package com.manager.web.system;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
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
@RequestMapping("/manager/role_menu")
public class RoleMenuController implements Constants
{

    @Autowired
	private MenuService menuService;

    @Autowired
	private RoleService roleService;
	
    @Autowired
	private RoleDefaultMenuService roleDefaultMenuService;

    private long ROOT = 1;
    
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
	
	/**
	 * 角色权限列表：角色拥有的权限默认选上
	 * @date 2015-10-09
	 * @param request
	 * @return
	 */
	@RequestMapping("/button")
	public ModelAndView button(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("/system/role/button");
		Long roleId = RequestUtil.getLong(request, "roleId");
		List<Menu> menus = menuService.findMenuList();
		List<Menu> roleMenus = roleDefaultMenuService.getMenuListByRoleId(roleId);
		HashSet<Long> roleMenuIdSet = Sets.newHashSet();
		for(Menu m : roleMenus) {
			roleMenuIdSet.add(m.getId());
		}
		Map<Long, Menu> map = Maps.newConcurrentMap();
	        for(Menu menu : menus) {
	        	if(menu.getParentId() == -1) {
	        		continue;
	        	}
	        	else if(menu.getParentId() == ROOT) {
	        		map.put(menu.getId(), menu);
	        	} else if(menu.getOnMenu() == 1) {
	        		Menu m = map.get(menu.getParentId());
	        		if(m.getChildList() == null) {
	        			 List<Menu> childList = new ArrayList<Menu>();
	        			 m.setChildList(childList);
	        		}
	        		m.getChildList().add(menu);
	        	}
	        }
	        List<Menu> menuList = Lists.newArrayList(map.values());
	        JSONArray jsonTree = new JSONArray();
	        for(Menu m : menuList) {
	        	JSONObject node = new JSONObject();
	        	node.put("id", m.getId());
	        	node.put("name", m.getName());
	        	if(roleMenuIdSet.contains(m.getId())) {
	        		node.put("checked", true);
	        	}
	        	if(m.getChildList() != null) {
	        		JSONArray subNodeArray = new JSONArray();
	        		for(Menu sub : m.getChildList()) {
	        			JSONObject subNode = new JSONObject();
	        			subNode.put("id", sub.getId());
	        			subNode.put("name", sub.getName());
	        			if(roleMenuIdSet.contains(sub.getId())) {
	        				subNode.put("checked", true);
	    	        	}
	        			subNodeArray.add(subNode);
	        		}
	        		node.put("nodes", subNodeArray);
	        	}
	        	jsonTree.add(node);
	        }
	        JSONArray rootArr = new JSONArray();
	        JSONObject root = new JSONObject();
	        Menu rootMenu = menus.get(0);
	        root.put("id", rootMenu.getId());
	        root.put("name", rootMenu.getName());
	        root.put("nodes", jsonTree);
	        if(roleMenuIdSet.contains(rootMenu.getId())) {
	        	root.put("checked", true);
	        }
	        rootArr.add(root);
	        mv.addObject("zTreeNodes", rootArr.toJSONString());
	        mv.addObject("roleId", roleId);
		return mv;
	}
	/**
	 * 保存角色对应权限：做差集
	 * 删除权限：（1，2，3，4）-（1，2，5）=3，4
	 * 新增权限：（1，2，5）-（1，2，3，4）=5
	 * @param request
	 * @return
	 * @throws AdminException
	 */
	@RequestMapping("/saveMenu")
	public ModelAndView saveMenu(HttpServletRequest request) throws AdminException
	{
		Long roleId = RequestUtil.getLong(request, "roleId");
		String menuIds = RequestUtil.getString(request, "menuIds");
		if (null == roleId || roleId <= 0)
		{
			throw new AdminException("非法操作！");
		}
		if (null == menuIds || "".equals(menuIds))
        {
            throw new AdminException("请选择要保存的节点！");
        }
		Role role = roleService.getById(roleId);
		if (null == role)
		{
			throw new AdminException("用户不存在！");
		}
		Iterator<String> it = Splitter.on(",").trimResults().split(menuIds).iterator();
		Set<Long> nowSet = Sets.newHashSet();
		while(it.hasNext()) {
			nowSet.add(Long.valueOf(it.next()));
		}
		
		roleDefaultMenuService.saveMenus(roleId, nowSet); // 更新权限菜单
		return new ModelAndView(JSON_VIEW, JSON_ROOT, DwzJsonUtil.getOkStatusMsg(null));
	}
}
