package cn.lfy.base.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import cn.lfy.base.Constants;
import cn.lfy.base.model.Admin;
import cn.lfy.base.model.Criteria;
import cn.lfy.base.model.LoginAccount;
import cn.lfy.base.model.Role;
import cn.lfy.base.model.TreeNode;
import cn.lfy.base.service.AdminRoleService;
import cn.lfy.base.service.AdminService;
import cn.lfy.base.service.RoleService;
import cn.lfy.common.framework.exception.ApplicationException;
import cn.lfy.common.framework.exception.ErrorCode;
import cn.lfy.common.model.Message;
import cn.lfy.common.utils.RequestUtil;

@Controller
@RequestMapping("/manager/admin_role")
public class AdminRoleController
{

	@Autowired
	private RoleService roleService;
	
    @Autowired
	private AdminService adminService;
	
    @Autowired
	private AdminRoleService adminMenuService;

    @RequestMapping("/api/tree")
    @ResponseBody
    public Object api_tree(HttpServletRequest request) {
    	List<Role> roleTree = new ArrayList<Role>();
    	Long adminId = RequestUtil.getLong(request, "id");
    	List<Role> list = adminMenuService.getRoleListByAdminId(adminId);
    	Set<Long> roleSet = Sets.newHashSet();
    	for(Role role : list) {
    		roleSet.add(role.getId());
    	}
    	LoginAccount account = (LoginAccount) request.getSession().getAttribute(Constants.SESSION_LOGIN_USER);
    	List<Role> roles = account.getRoles();
    	roleTree.addAll(roles);
    	while(!roles.isEmpty()) {
    		List<Role> next = new ArrayList<Role>();
    		for(Role role : roles) {
    			Criteria criteria = new Criteria();
    			criteria.put("parentId", role.getId());
    			List<Role> tmpRoles = roleService.getByCriteria(criteria);
    			roleTree.addAll(tmpRoles);
    			next.addAll(tmpRoles);
    		}
    		roles = next;
    	}
		List<TreeNode> treeList = Lists.newArrayList();
		boolean chkDisabled = false;
		if(account.getRoleIdList().contains(Long.valueOf(1L)) && roleSet.contains(Long.valueOf(1L))) {
			chkDisabled = true;
		} else if(roleSet.contains(Long.valueOf(1L))) {
			chkDisabled = true;
		}
		for(Role role : roleTree) {
			boolean checked = roleSet.contains(role.getId());
			TreeNode treeNode = new TreeNode(role.getId(), role.getName(), role.getParentId(), checked);
			treeNode.setChkDisabled(chkDisabled);
			treeList.add(treeNode);
		}
		List<TreeNode> tree = Lists.newArrayList();
		for(TreeNode node1 : treeList){  
			tree.add(node1);   
		}
        Message.Builder builder = Message.newBuilder();
        builder.data(tree);
    	return builder.build();
    }
    
	@RequestMapping("/save")
	@ResponseBody
	public Object save(HttpServletRequest request) throws ApplicationException
	{
		Long adminId = RequestUtil.getLong(request, "adminId");
		String menuIdsString = RequestUtil.getString(request, "menuIds");
		if(StringUtils.isBlank(menuIdsString)) {
			throw ApplicationException.newInstance(ErrorCode.PARAM_ILLEGAL, "menuIds");
		}
		Iterator<String> it = Splitter.on(",").split(menuIdsString).iterator();
		List<Long> menuIds = Lists.newArrayList();
		while(it.hasNext()) {
			menuIds.add(Long.parseLong(it.next()));
		}
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
		adminMenuService.saveAdminRoles(admin, menuIds); // 更新权限菜单
		return Message.newBuilder().build();
	}
}