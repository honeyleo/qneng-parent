package cn.lfy.base.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;

import cn.lfy.base.Constants;
import cn.lfy.base.model.Criteria;
import cn.lfy.base.model.LoginAccount;
import cn.lfy.base.model.Role;
import cn.lfy.base.model.TreeNode;
import cn.lfy.base.model.type.StateType;
import cn.lfy.base.service.RoleService;
import cn.lfy.common.framework.exception.ApplicationException;
import cn.lfy.common.model.Message;
import cn.lfy.common.utils.RequestUtil;

@Controller
@RequestMapping("/manager/role")
public class RoleController {

    public static final int listPageSize = 20;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request) throws ApplicationException {
        return "/system/role/role-tree";
    }
    
    @RequestMapping("/api/tree")
    @ResponseBody
    public Object api_tree(HttpServletRequest request) {
    	List<Role> roleTree = new ArrayList<Role>();
    	LoginAccount account = (LoginAccount) request.getSession().getAttribute(Constants.SESSION_LOGIN_USER);
    	Role curRole = account.getRoles().get(0);
    	roleTree.add(curRole);
    	Criteria criteria = new Criteria();
    	criteria.put("parentId", curRole.getId());
    	List<Role> roles = roleService.getByCriteria(criteria);
    	roleTree.addAll(roles);
    	while(!roles.isEmpty()) {
    		List<Role> next = new ArrayList<Role>();
    		for(Role role : roles) {
    			criteria.put("parentId", role.getId());
    			List<Role> tmpRoles = roleService.getByCriteria(criteria);
    			roleTree.addAll(tmpRoles);
    			next.addAll(tmpRoles);
    		}
    		roles = next;
    	}
		List<TreeNode> treeList = Lists.newArrayList();
		for(Role role : roleTree) {
			treeList.add(new TreeNode(role.getId(), role.getName(), role.getParentId(), false));
		}
		List<TreeNode> tree = Lists.newArrayList();
		for(TreeNode node1 : treeList){  
			tree.add(node1);   
		}
        Message.Builder builder = Message.newBuilder();
        builder.data(tree);
    	return builder.build();
    }

    @RequestMapping("/del")
    @ResponseBody
    public Object del(HttpServletRequest request) throws ApplicationException {
        Long id = RequestUtil.getLong(request, "id");
        Role record = new Role();
        record.setId(id);
        record.setState(StateType.INACTIVE.getId());
        roleService.updateByIdSelective(record);
        return Message.newBuilder().build();
    }

    @RequestMapping("/detail")
    @ResponseBody
    public Object detail(HttpServletRequest request) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        Long id = RequestUtil.getLong(request, "id");
        Role role = roleService.getById(id);
        builder.data(role);
        return builder.build();
    }

    @RequestMapping("/add")
    @ResponseBody
    public Object add(Role role, HttpServletRequest request) throws ApplicationException {
        roleService.insert(role);
        return Message.newBuilder().build();
    }

    @RequestMapping("/update")
    @ResponseBody
    public Object update(Role role, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
        roleService.updateByIdSelective(role);
        return Message.newBuilder().build();
    }

}
