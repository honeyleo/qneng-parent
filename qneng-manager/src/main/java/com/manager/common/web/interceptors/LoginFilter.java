package com.manager.common.web.interceptors;

import java.io.IOException;

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

	private Logger log = LoggerFactory.getLogger(LoginFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String requestUri = request.getRequestURI();
		
		log.info("登录会话拦截，请求URL："+requestUri);
		
		if(!LoginVerify(request)) {
			String httpAjax=request.getHeader("X-Requested-With");
			if(httpAjax != null && "XMLHttpRequest".equals(httpAjax)) {
				response.getWriter().write("{\"ret\":\"3000\", \"msg\":\"验证会话失败，请先登录\", \"redirectUrl\":\"\"}\\");
				response.getWriter().flush();
				response.getWriter().close();
			} else {
				response.sendRedirect(Constants.URL_MANAGER_LOGIN);
			}
			return;
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
