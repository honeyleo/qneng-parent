package com.manager.web.system;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.lfy.common.model.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.manager.common.Constants;
import com.manager.common.ErrorCode;
import com.manager.common.exception.ApplicationException;
import com.manager.common.util.RequestUtil;
import com.manager.model.Menu;
import com.manager.model.TreeNode;
import com.manager.service.MenuService;


@Controller
@RequestMapping("/manager/menu")
public class MenuController implements Constants {

    @Resource
    private MenuService menuService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request) throws ApplicationException {
        return "/system/menu/list-menu";
    }
    
    @RequestMapping("/api/list")
    @ResponseBody
    public Object api_list() {
    	List<Menu> menus = menuService.findMenuList();
		List<TreeNode> treeList = Lists.newArrayList();
		for(Menu menu : menus) {
			treeList.add(new TreeNode(menu.getId(), menu.getName(), menu.getParentId(), false));
		}
		List<TreeNode> tree = Lists.newArrayList();
		for(TreeNode node1 : treeList){  
			tree.add(node1);   
		}
        Message.Builder builder = Message.newBuilder();
        builder.data(tree);
    	return builder.build();
    }
    
    @RequestMapping("/sub")
    @ResponseBody
    public Object sub(HttpServletRequest request) throws ApplicationException {
        Integer parentId=RequestUtil.getInteger(request, "parentId");
        List<Menu> menus=menuService.listSubMenuByParentId(String.valueOf(parentId));
        return menus;
    }

    @RequestMapping("/update")
    @ResponseBody
    public Object update(Menu menu, HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        Long parentId=RequestUtil.getLong(request, "id");
        Menu menuDb=menuService.getById(parentId);
        
        menuDb.setName(menu.getName());
        menuDb.setUrl(menu.getUrl());
        menuDb.setOrderNo(menu.getOrderNo());
        menuDb.setOnMenu(menu.getOnMenu());
        menuService.updateByIdSelective(menuDb);
        return builder.build();
    }
    
    @RequestMapping("/detail")
    @ResponseBody
    public Object detail(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        Long id=RequestUtil.getLong(request, "id");
        if(null != id) {
            Menu menu=menuService.getById(id);
            builder.data(menu);
        }
        return builder.build();
    }

    @RequestMapping("/add")
    @ResponseBody
    public Object add(Menu menu) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        menuService.save(menu);
        return builder.build();
    }

    @RequestMapping("/del")
    @ResponseBody
    public Object deleteTreeNode(HttpServletRequest request) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        Long menuId =RequestUtil.getLong(request, "id");
        if(null == menuId) {
            throw ApplicationException.newInstance(ErrorCode.PARAM_ILLEGAL, "menuId");
        }
        menuService.deleteById(menuId);
        return builder.build();
    }
    
    /**
	 * 请求编辑菜单图标页面
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/toIcon")
	public ModelAndView toEditicon(String menuId)throws Exception{
		ModelAndView mv = new ModelAndView();
		mv.addObject("menuId", menuId);
		mv.setViewName("system/menu/icon");
		return mv;
	}
	
	/**
	 * 保存菜单图标 (顶部菜单)
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/editIcon")
	public ModelAndView editicon(HttpServletRequest request)throws Exception{
		ModelAndView mv = new ModelAndView();
		Long id = RequestUtil.getLong(request, "menuId");
		String icon = RequestUtil.getString(request, "icon");
		menuService.updateIcon(id, icon);
		mv.addObject("msg","success");
		mv.setViewName("common/save_result");
		return mv;
	}
}
