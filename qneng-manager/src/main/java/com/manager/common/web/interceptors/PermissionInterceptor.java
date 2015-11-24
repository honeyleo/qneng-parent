package com.manager.common.web.interceptors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.manager.common.ErrorCode;
import com.manager.common.exception.ApplicationException;
import com.manager.model.LoginAccount;
import com.manager.model.Menu;

public class PermissionInterceptor extends HandlerInterceptorAdapter {

	private Logger log = LoggerFactory.getLogger(PermissionInterceptor.class);

	/** 忽略拦截请求的Url **/
	public static Set<String> ignoreUrl = new HashSet<String>();

	static {

		/*
		 * 放置忽略拦截请求的action名称
		 */
		ignoreUrl.add("/manager/login"); /* 登录验证 */
		ignoreUrl.add("/manager/dologin"); /* 登录验证 */
		ignoreUrl.add("/manager/code"); /* 登录验证 */
		ignoreUrl.add("/manager/index"); /* 登录验证 */
		ignoreUrl.add("/manager/tab.do");
		ignoreUrl.add("/manager/login_default.do");
		ignoreUrl.add("/manager/logout");

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String requestUrl = request.getRequestURI();
		log.info("权限拦截，请求url：" + requestUrl);

		request.setAttribute("reqMill", System.currentTimeMillis());
		if (!(ignoreUrl.contains(requestUrl))) {

			if (!permissionVerify(request)) {
				throw ApplicationException.newInstance(ErrorCode.PERMISSION_DENIED);
			}
		}
		return super.preHandle(request, response, handler);
	}

	private boolean permissionVerify(HttpServletRequest request) {

		HttpSession session = request.getSession();
		LoginAccount user = (LoginAccount) session.getAttribute(com.manager.common.Constants.SESSION_LOGIN_USER);

		String uri = request.getRequestURI();
		List<Menu> userMenus = user.getMenus();

		if ("/manager/index".equals(uri)) {
			return true;
		}
		for(Menu menu : userMenus) {
			if(uri.equals(menu.getUrl())) {
				return true;
			}
		}
		return false;
	}
	
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String requestUrl = request.getRequestURI();
		long respMill = System.currentTimeMillis();
		long reqMill = (Long)request.getAttribute("reqMill");
		log.info("权限拦截，请求url：" + requestUrl + "，cost " + (respMill - reqMill));
	}

}
