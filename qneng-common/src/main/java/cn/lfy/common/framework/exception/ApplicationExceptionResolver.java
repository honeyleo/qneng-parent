package cn.lfy.common.framework.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.lfy.common.utils.ParamUtil;

@Component
public class ApplicationExceptionResolver implements HandlerExceptionResolver
{
	private static Logger logger = LoggerFactory.getLogger(ApplicationExceptionResolver.class);
	private MessageSource messageSource;

	private static final String VIEW_ERROR = "error";
	
	public void setMessageSource(MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handle, Exception e)
	{
		/**
		 * 拼接异常时URL参数
		 */
		Map<String, String[]> p = request.getParameterMap();
		String params = ParamUtil.concatAll(p);
		String requestUrl = request.getRequestURI() + "|" + ( null == p || p.isEmpty() ? "{}" : params );
		
		Map<String, Object> model = new HashMap<String, Object>();
		int errorcode = ErrorCode.SERVER_UNKNOW;
		String errorMessage = "";
		if (e instanceof ApplicationException)
		{
			ApplicationException ae = (ApplicationException) e;
			errorcode = ae.getErrorCode();
			try {
				errorMessage = messageSource.getMessage(ErrorKey.getKey(ae.getErrorCode(), ErrorCode.SERVER_UNKNOW), ae.getMessageParams(), request.getLocale());
			} catch(Throwable e2) {
				logger.error("MessageSource getMessage error.errorCode=" + errorcode + ",msg=" + ae.getDetailMessage(), e2);
				errorMessage = ae.getDetailMessage();
			}
			String eMessage = String.format("%d  %s  %s %s", errorcode, errorMessage, ae.getContextParams(), requestUrl);
			if (ae.isLogged())
			{
				logger.error(eMessage, e);
			}
			else
			{
				logger.error(eMessage);
			}
		} 
		else 
		{
			errorMessage = messageSource.getMessage(ErrorKey.getKey(ErrorCode.SERVER_UNKNOW), null, request.getLocale());
			logger.error("SERVER_ERROR_" + e.getMessage() + requestUrl, e);
		}
		model.put("ret", errorcode);
		model.put("msg", errorMessage);
		return new ModelAndView("/WEB-INF/jsp/" + VIEW_ERROR + ".jsp", model);
	}
}
