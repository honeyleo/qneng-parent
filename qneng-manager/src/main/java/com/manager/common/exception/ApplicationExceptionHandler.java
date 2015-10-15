package com.manager.common.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.manager.common.ErrorCode;

public class ApplicationExceptionHandler implements HandlerExceptionResolver {

	private MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handle, Exception e)
	{
		Map<String, Object> model = new HashMap<String, Object>();
		ErrorCode errorCode = ErrorCode.ERROR;
		String errorMessage = messageSource.getMessage(errorCode.getMsg(), null, request.getLocale());
		String view = "error";
		if (e instanceof ApplicationException)
		{
			ApplicationException ae = (ApplicationException) e;
			errorCode = ae.getErrorCode();
			errorMessage = messageSource.getMessage(errorCode.getMsg(), ae.getMessageParams(), request.getLocale());
		}
		model.put("code", errorCode.getCode());
		model.put("message", errorMessage);
		return new ModelAndView("/WEB-INF/jsp/" + view + ".jsp", model);
	}

}
