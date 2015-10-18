package com.manager.common.web;

import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.manager.common.Constants;
import com.manager.model.LoginAccount;
import com.manager.model.Menu;

public class PermissionTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 283399058720339755L;

	private String module;//属性名必须与JSP自定义标签的属性名一样
	   
	private Long privilege;  
	   
	public String getModule() {  
		return module;  
	}  
	   
	public void setModule(String module) {  
		this.module = module;  
	}  
	   
	public Long getPrivilege() {  
		return privilege;  
	}  
	   
	public void setPrivilege(Long privilege) {  
		this.privilege = privilege;  
	}  
	   
	@Override  
	public int doStartTag() throws JspException {  
		LoginAccount account = (LoginAccount)pageContext.getSession().getAttribute(Constants.SESSION_LOGIN_USER);
		List<Menu> menus = account.getMenus();
		boolean result = false;
		for(Menu menu : menus) {
			if(menu.getId().longValue() == getPrivilege().longValue()) {
				result = true;
				break;
			}
		}
		//EVAL_BODY_INCLUDE代表执行自定义标签中的内容，SKIP_BODY代表不执行自定义标签中的内容。  
		return result? EVAL_BODY_INCLUDE : SKIP_BODY;  
	}  
	   
}
