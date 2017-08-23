package cn.lfy.base.web;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import cn.lfy.common.framework.exception.ApplicationException;
import cn.lfy.common.model.Message;
import cn.lfy.common.model.ResultDTO;

@Api(value="test")
@Controller
public class TestCtrl extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(TestCtrl.class);
	
	@Value("${env}")
	private String env;
	
	@RequestMapping(value = "/test/{id}")
    @ResponseBody
    @ApiOperation(value="根据ID获取用户信息",httpMethod="GET",notes="get user by id")  
    public Object test(@PathVariable String id) throws ApplicationException {
		Message.Builder builder = Message.newBuilder();
		builder.put("name", id);
		builder.put("env", env);
		return builder.build();
	}
	
	@RequestMapping("/api/test/login")
	@ResponseBody
	public ResultDTO<String> login(String username) {
		HttpServletResponse response = getResponse();
		String token = UUID.randomUUID().toString();
		System.out.println(token);
		Cookie cookie = new Cookie("Authorization", token);
		response.addCookie(cookie);
		response.setHeader("Authorization", token);
		ResultDTO<String> resultDTO = new ResultDTO<>();
		resultDTO.setData(token);
		return resultDTO;
	}
	
	@RequestMapping("/api/test/me")
	@ResponseBody
	public ResultDTO<String> me(String username) {
		HttpServletRequest request = getRequest();
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies) {
			logger.info("cookie name={} value={}", cookie.getName(), cookie.getValue());
		}
		String Authorization = request.getHeader("Authorization");
		System.out.println("Authorization=" + Authorization);
		ResultDTO<String> resultDTO = new ResultDTO<>();
		resultDTO.setData(username);
		return resultDTO;
	}
	
}
