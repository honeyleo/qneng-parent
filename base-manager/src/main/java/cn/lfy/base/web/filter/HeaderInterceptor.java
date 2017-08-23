package cn.lfy.base.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class HeaderInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (request.getHeader(HttpHeaders.ORIGIN) != null) {
	        response.addHeader("Access-Control-Allow-Origin", request.getHeader(HttpHeaders.ORIGIN));
	        response.addHeader("Access-Control-Allow-Credentials", "true");
	        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, HEAD");
	        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
	        response.addHeader("Access-Control-Max-Age", "3600");
	   }
	   return true;
	}

}
