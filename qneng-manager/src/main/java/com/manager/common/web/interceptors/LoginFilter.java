package com.manager.common.web.interceptors;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import com.manager.common.Constants;
import com.manager.model.LoginAccount;

public class LoginFilter extends OncePerRequestFilter {

	private Logger log = LoggerFactory.getLogger(OncePerRequestFilter.class);
	
	/** 忽略拦截请求的Url **/
	private static Set<String> ignoreUrl = new HashSet<String>();
	
	static{

		/*
		 * 放置忽略拦截请求的action名称
		 */
		ignoreUrl.add("/");
		ignoreUrl.add("/manager/code");
		ignoreUrl.add("/manager/login");
		ignoreUrl.add("/manager/dologin");
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String requestUrl = request.getRequestURI();
		log.info("session拦截，请求url："+requestUrl);
		
		if(requestUrl != null && !"".equals(requestUrl)){
			if(!(ignoreUrl.contains(requestUrl))){
				log.info("登录会话拦截，请求url："+requestUrl);
				
				if(!LoginVerify(request)) {
					String httpAjax=request.getHeader("X-Requested-With");
					if(httpAjax != null && "XMLHttpRequest".equals(httpAjax)) {
						request.getRequestDispatcher("common/ajaxDone").forward(request, response); 
					} else {
						response.sendRedirect("/manager/login");
					}
					return;
				}
			}
		}
		filterChain.doFilter(request, response);
	}

	
	private boolean LoginVerify(HttpServletRequest request){
		
		HttpSession session = request.getSession();
		LoginAccount user = (LoginAccount) session.getAttribute(Constants.SESSION_LOGIN_USER);
		if(null == user || user.getId() <= 0){
			return false;
		}
		return true;
	}
	
	
	
}
