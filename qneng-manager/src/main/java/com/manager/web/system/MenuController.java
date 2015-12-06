package com.manager.web.system;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.lfy.common.model.Message;

import com.google.common.collect.Lists;
import com.manager.common.Constants;
import com.manager.common.ErrorCode;
import com.manager.common.exception.ApplicationException;
import com.manager.common.util.RequestUtil;
import com.manager.model.Menu;
import com.manager.service.MenuService;


@Controller
@RequestMapping("/manager/menu")
public class MenuController implements Constants {

    @Resource
    private MenuService menuService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request) throws ApplicationException {
    	List<Menu> menus = menuService.findMenuList();
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
        return "/system/menu/list";
    }
    
    @RequestMapping("/sub")
    @ResponseBody
    public Object sub(HttpServletRequest request) throws ApplicationException {
        Integer parentId=RequestUtil.getInteger(request, "parentId");
        List<Menu> menus=menuService.listSubMenuByParentId(String.valueOf(parentId));
        return menus;
    }

    @RequestMapping("/goadd")
    public String goAdd(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
        Long parentId=RequestUtil.getLong(request, "parentId");
        Menu menu=menuService.getById(parentId);
        request.setAttribute("parentId", parentId);
        int onMenu=RequestUtil.getInteger(request, "onMenu");
        request.setAttribute("onMenu", onMenu);
        request.setAttribute("parentIdPath", menu.getParentIdPath() + menu.getId() + "$");
        return "/system/menu/edit";
    }
    
    @RequestMapping("/goedit")
    public String goEditor(HttpServletRequest request, HttpServletResponse response) throws ApplicationException {
        Long id=RequestUtil.getLong(request, "id");
        if(null != id) {
            Menu menu=menuService.getById(id);
            request.setAttribute("menu", menu);
            request.setAttribute("parentId", menu.getParentId());
            request.setAttribute("onMenu", menu.getOnMenu());
            request.setAttribute("parentIdPath", menu.getParentIdPath() + menu.getId() + "$");
        }
        return "/system/menu/edit";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Object save(HttpServletRequest request) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        Long id=RequestUtil.getLong(request, "id");
        String name=RequestUtil.getString(request, "name");
        Integer type=RequestUtil.getInteger(request, "type");
        Long parentId=RequestUtil.getLong(request, "parentId");
        String lcode=RequestUtil.getString(request, "parentIdPath");
        Integer orderNo=RequestUtil.getInteger(request, "orderNo");
        String url=RequestUtil.getString(request, "url");
        String remark=RequestUtil.getString(request, "remark");
        Integer onMenu = RequestUtil.getInteger(request, "onMenu");
        Menu record=new Menu();
        record.setId(id);
        record.setName(name);
        record.setType(type);
        record.setParentId(parentId);
        record.setParentIdPath(lcode);
        record.setOrderNo(orderNo);
        record.setRemark(remark);
        record.setUrl(url);
        record.setState(1);
        record.setOnMenu(onMenu);
        menuService.save(record);
        return builder.build();
    }

    @RequestMapping("/del")
    @ResponseBody
    public Object deleteTreeNode(HttpServletRequest request) throws ApplicationException {
    	Message.Builder builder = Message.newBuilder();
        Long menuId =RequestUtil.getLong(request, "menuId");
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
	@ResponseBody
	public Object editicon(HttpServletRequest request)throws Exception{
		Message.Builder builder = Message.newBuilder();
		Long id = RequestUtil.getLong(request, "menuId");
		String icon = RequestUtil.getString(request, "icon");
		menuService.updateIcon(id, icon);
		return builder.build();
	}
}
