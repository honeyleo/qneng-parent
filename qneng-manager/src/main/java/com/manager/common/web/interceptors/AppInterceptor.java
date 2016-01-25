package com.manager.common.web.interceptors;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.lfy.common.model.Message;
import cn.lfy.qneng.mgr.SessionManager;

import com.alibaba.fastjson.JSON;
import com.manager.common.Constants;
import com.manager.model.LoginAccount;

public class AppInterceptor extends HandlerInterceptorAdapter {

	private Logger log = LoggerFactory.getLogger(AppInterceptor.class);

	/** 忽略拦截请求的Url **/
	public static Set<String> ignoreUrl = new HashSet<String>();

	static {

		/*
		 * 放置忽略拦截请求的action名称
		 */
		ignoreUrl.add("/app/login"); /* 登录验证 */

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String requestUrl = request.getRequestURI();
		Enumeration<String> enumes = request.getParameterNames();
		Map<String, String> params = new HashMap<String, String>();
		if(enumes != null) {
			while(enumes.hasMoreElements()) {
				String key = enumes.nextElement();
				String value = request.getParameter(key);
				params.put(key, value);
			}
		}
		log.info("权限拦截，请求url={},params={}", requestUrl, params);
		
		request.setAttribute("reqMill", System.currentTimeMillis());
		request.setAttribute("params", params);
		if(!(ignoreUrl.contains(requestUrl))) {
			if(!LoginVerify(request)) {
				Message.Builder builder = Message.newBuilder();
				builder.setRet(3000).setMsg("sessionid验证失败，请先登录");
				response.getWriter().write(JSON.toJSONString(builder.build()));
				response.getWriter().close();
				return false;
			}
		}
		return true;
	}

	private boolean LoginVerify(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		LoginAccount user = (LoginAccount) session.getAttribute(Constants.SESSION_LOGIN_USER);
		if(user == null) {
			String sid = request.getParameter("sid");
			user = SessionManager.getLoginAccount(sid);
		}
		if(null == user || user.getId() <= 0){
			return false;
		}
		return true;
	}
	@SuppressWarnings("unchecked")
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		String requestUrl = request.getRequestURI();
		long respMill = System.currentTimeMillis();
		long reqMill = (Long)request.getAttribute("reqMill");
		Map<String, String> params = (Map<String, String>) request.getAttribute("params");
		log.info("权限拦截，请求url={},params={} cost {}.", new Object[]{requestUrl, params, (respMill - reqMill)});
	}

}
