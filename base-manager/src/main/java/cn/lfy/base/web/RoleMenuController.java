package cn.lfy.base.web;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cn.lfy.base.model.LoginAccount;
import cn.lfy.base.model.Menu;
import cn.lfy.base.model.Role;
import cn.lfy.base.model.TreeNode;
import cn.lfy.base.service.MenuService;
import cn.lfy.base.service.RoleMenuService;
import cn.lfy.base.service.RoleService;
import cn.lfy.common.framework.exception.ApplicationException;
import cn.lfy.common.framework.exception.ErrorCode;
import cn.lfy.common.model.Message;
import cn.lfy.common.utils.RequestUtil;

@Controller
@RequestMapping("/manager/role_menu")
public class RoleMenuController
{

    @Autowired
	private MenuService menuService;

    @Autowired
	private RoleService roleService;
	
    @Autowired
	private RoleMenuService roleMenuService;

	/**
	 * 角色权限列表：角色拥有的权限默认选上
	 * @date 2015-10-09
	 * @param request
	 * @return
	 */
	@RequestMapping("/privileges")
	@ResponseBody
	public Object privileges(HttpServletRequest request, LoginAccount account) {
		Long roleId = RequestUtil.getLong(request, "id");
		Role role = roleService.getById(roleId);
		List<Menu> menus = null;
		if(account.getRoleIds().contains(1L)) {
			if(role.getId() == 1) {
				menus = menuService.findMenuList();
			} else if(role.getParentId() == 1) {
				menus = menuService.findMenuList();
			} else {
				menus = roleMenuService.getMenuListByRoleId(role.getParentId());
			}
			
		} else {
			if(account.getRoleIds().contains(role.getId())) {
				menus = roleMenuService.getMenuListByRoleId(role.getId());
			} else {
				menus = roleMenuService.getMenuListByRoleId(role.getParentId());
			}
		}
		
		List<Menu> roleMenus = roleMenuService.getMenuListByRoleId(roleId);
		HashSet<Long> roleMenuIdSet = Sets.newHashSet();
		for(Menu m : roleMenus) {
			roleMenuIdSet.add(m.getId());
		}
		List<TreeNode> treeList = Lists.newArrayList();
		for(Menu menu : menus) {
			boolean checked = false;
			boolean chkDisabled = false;
			if(account.getRoleIds().contains(1L) && role.getId() == 1) {
				checked = true;
				chkDisabled = true;
			} else {
				checked = roleMenuIdSet.contains(menu.getId());
			}
			TreeNode treeNode = new TreeNode(menu.getId(), menu.getName(), menu.getParentId(), checked);
			treeNode.setChkDisabled(chkDisabled);
			treeList.add(treeNode);
		}
		Message.Builder builder = Message.newBuilder();
		builder.data(treeList);
		return builder.build();
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
	@ResponseBody
	public Object saveMenu(HttpServletRequest request, LoginAccount currentUser) throws ApplicationException
	{
		Long roleId = RequestUtil.getLong(request, "roleId");
		String menuIds = RequestUtil.getString(request, "menuIds");
		if (null == roleId || roleId <= 0)
		{
			throw ApplicationException.newInstance(ErrorCode.PARAM_ILLEGAL, "roleId");
		}
		if (null == menuIds || "".equals(menuIds))
        {
			throw ApplicationException.newInstance(ErrorCode.PARAM_ILLEGAL, "menuIds");
        }
		Role role = roleService.getById(roleId);
		if (null == role)
		{
			throw ApplicationException.newInstance(ErrorCode.NOT_EXIST, "角色");
		}
		Iterator<String> it = Splitter.on(",").trimResults().split(menuIds).iterator();
		Set<Long> nowSet = Sets.newHashSet();
		while(it.hasNext()) {
			nowSet.add(Long.valueOf(it.next()));
		}
		roleMenuService.saveMenus(roleId, nowSet, currentUser);
		return Message.newBuilder().build();
	}
}
