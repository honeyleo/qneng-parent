package com.manager.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.manager.common.Constants;
import com.manager.common.web.Funcs;
import com.manager.model.LoginAccount;

@Controller
public class BaseController implements Constants
{
    private static final String WELCOME = "/welcome";
	protected LoginAccount getLoginUser(HttpServletRequest request) {
    	return Funcs.getSessionLoginAccount(request.getSession(false));
	}

	protected void generateExceptionAjax(HttpServletRequest request, HttpServletResponse response, String message)
	{
		String navTabId = request.getParameter("navTabId");
		String callbackType = "closeCurrent";
		String forwardUrl = request.getParameter("forwardurl");
		if (forwardUrl != null && forwardUrl.trim().length() > 0)
		{
			callbackType = "forward";
		}
		PrintWriter out;
		try
		{
			out = response.getWriter();
			out.print("{\"statusCode\":\"300\",\"message\":\"" + message + "\",\"navTabId\":\"" + navTabId
					+ "\",\"rel\":\"\",\"callbackType\":\"" + callbackType + "\",\"forwardUrl\":\"" + forwardUrl
					+ "\"}");
			out.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
    @RequestMapping("/welcome")
    public String welcome() {
        return WELCOME;
    }
	 
}
