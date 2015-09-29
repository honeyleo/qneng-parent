package cn.lfy.common.framework.exception.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.lfy.common.framework.exception.ServiceRuntimeException;
import cn.lfy.common.utils.Constants;
import cn.lfy.common.utils.LogUtil;
public class ExceptionHandler implements HandlerExceptionResolver {

	private static final String DEFALUT_EXCEPTION_VIEW = "common/ajaxDone";

	private static final Logger log = LoggerFactory.getLogger(ExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		if (null != ex) {
			ex = adaptException(ex, request);
			String httpAjax = request.getHeader("X-Requested-With");

			if (httpAjax != null && "XMLHttpRequest".equals(httpAjax)) {
				return handlerAjax(ex, request, response);
			} else {
				return handlerDefault(request, response, ex);
			}
		}
		return null;
	}

	/**
	 * 适配异常类型
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	private ServiceRuntimeException adaptException(Exception ex, HttpServletRequest request) {
		if (!(ex instanceof ServiceRuntimeException)) {
			log.error(LogUtil.getLogStr(request), ex);
			return new ServiceRuntimeException(Constants.NONE, ex);
		}

		ServiceRuntimeException ServiceRuntimeException = (ServiceRuntimeException) ex;
		if (null != ServiceRuntimeException.getException()) {
			log.warn(LogUtil.getLogStr(request), ServiceRuntimeException);
		} else {
			log.warn(ServiceRuntimeException.getMessage() + LogUtil.getLogStr(request));
		}

		return ServiceRuntimeException;
	}

	/**
	 * ajax异常处理器
	 * 
	 * @param ex
	 * @param request
	 * @param response
	 * @return
	 */
	private ModelAndView handlerAjax(Exception ex, HttpServletRequest request,
			HttpServletResponse response) {
		
		ServiceRuntimeException exception = (ServiceRuntimeException) ex;
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setCharacterEncoding("UTF-8");
			response.setStatus(exception.getStatusCode());
			out.println(((ServiceRuntimeException) ex).toJSONString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			log.error(LogUtil.getLogStr(request));
			log.error(((ServiceRuntimeException) ex).toString());
			if (null != out){
				out.flush();
				out.close();
			}
		}
		return new ModelAndView();
	}

	/**
	 * 普通异常处理器
	 * 
	 * @param request
	 * @param response
	 * @param ex
	 * @return
	 */
	private ModelAndView handlerDefault(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		ModelAndView mav = new ModelAndView(DEFALUT_EXCEPTION_VIEW);
		Map<String, Object> map = ((ServiceRuntimeException) ex).getExceptionMap();
		for (Entry<String, Object> entry : map.entrySet()) {
			mav.addObject(entry.getKey(), entry.getValue());
		}
		return mav;
	}

}
