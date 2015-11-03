package com.manager.common.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.lfy.common.model.Message;

import com.alibaba.fastjson.JSON;
import com.manager.common.ErrorCode;

public class ApplicationExceptionHandler implements HandlerExceptionResolver {

	private MessageSource messageSource;

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handle, Exception e) {
		ErrorCode errorCode = ErrorCode.ERROR;
		String errorMessage = "";
		String redirectUrl = null;
		if (e instanceof ApplicationException) {
			ApplicationException ae = (ApplicationException) e;
			errorCode = ae.getErrorCode();
			errorMessage = messageSource.getMessage(errorCode.getMsg(),
					ae.getMessageParams(), request.getLocale());
			redirectUrl = ((ApplicationException) e).getRedirectUrl();
		} else {
			errorMessage = messageSource.getMessage(errorCode.getMsg(), null,
					request.getLocale());
		}
		Message.Builder builder = Message.newBuilder();
		builder.setRet(errorCode.getCode());
		builder.setMsg(errorMessage);
		builder.put("redirectUrl", redirectUrl);
		try {
			response.getWriter().write(JSON.toJSONString(builder.build()));
			response.getWriter().close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}

}
