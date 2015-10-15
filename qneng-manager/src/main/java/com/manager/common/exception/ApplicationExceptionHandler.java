package com.manager.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.manager.common.ErrorCode;

public class ApplicationExceptionHandler implements HandlerExceptionResolver {

	private MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handle, Exception e) {
		ModelAndView mv = new ModelAndView("/WEB-INF/jsp/error.jsp");
		ErrorCode errorCode = ErrorCode.ERROR;
		String errorMessage = "";
		if (e instanceof ApplicationException) {
			ApplicationException ae = (ApplicationException) e;
			errorCode = ae.getErrorCode();
			errorMessage = messageSource.getMessage(errorCode.getMsg(),
					ae.getMessageParams(), request.getLocale());
		} else {
			errorMessage = messageSource.getMessage(errorCode.getMsg(), null,
					request.getLocale());
		}
		mv.addObject("code", errorCode.getCode());
		mv.addObject("msg", errorMessage);
		return mv;
	}

}
