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

import com.google.common.collect.Lists;
import com.manager.common.Constants;
import com.manager.common.exception.AdminException;
import com.manager.common.util.RequestUtil;
import com.manager.model.Menu;
import com.manager.service.MenuService;


@Controller
@RequestMapping("/manager/menu")
public class MenuController implements Constants {

    @Resource
    private MenuService menuService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request) throws AdminException {
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
    public Object sub(HttpServletRequest request) throws AdminException {
        Integer parentId=RequestUtil.getInteger(request, "parentId");
        List<Menu> menus=menuService.listSubMenuByParentId(String.valueOf(parentId));
        return menus;
    }

    @RequestMapping("/goadd")
    public String goAdd(HttpServletRequest request, HttpServletResponse response) throws AdminException {
        Long parentId=RequestUtil.getLong(request, "parentId");
        Menu menu=menuService.getById(parentId);
        request.setAttribute("parentId", parentId);
        request.setAttribute("parentIdPath", menu.getParentIdPath() + menu.getId() + "$");
        return "/system/menu/edit";
    }
    
    @RequestMapping("/goedit")
    public String goEditor(HttpServletRequest request, HttpServletResponse response) throws AdminException {
        Long id=RequestUtil.getLong(request, "id");
        if(null != id) {
            Menu menu=menuService.getById(id);
            request.setAttribute("menu", menu);
            request.setAttribute("parentId", menu.getParentId());
        }
        return "/system/menu/edit";
    }

    @RequestMapping("/goselect")
    public String goSelect(HttpServletRequest request, HttpServletResponse response) throws AdminException {
//        Integer typeId=RequestUtil.getInteger(request, "type");
        List<Menu> menus=menuService.findMenuList();
        request.setAttribute("menus", menus);
        return "/manager/system/menu/menu_select";
    }

    @RequestMapping("/save")
    public ModelAndView save(HttpServletRequest request) throws AdminException {
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
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","success");
		mv.setViewName("common/save_result");
        return mv;
    }

    @RequestMapping("/del")
    public String deleteTreeNode(HttpServletRequest request) throws AdminException {
        List<Long> menuIds=RequestUtil.getLongs(request, "treecheckbox");
        if(null == menuIds || menuIds.isEmpty()) {
            throw new AdminException("请选择要删除的节点！");
        }
        for(Long menuId: menuIds) {
            menuService.deleteById(menuId);
        }
        return list(request);
    }
}
