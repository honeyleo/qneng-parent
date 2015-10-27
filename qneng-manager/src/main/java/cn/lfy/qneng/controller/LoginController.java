package cn.lfy.qneng.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.manager.common.ErrorCode;
import com.manager.common.exception.ApplicationException;

@Controller
@RequestMapping("/manager")
public class LoginController {

	@RequestMapping("/login2")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	
	@RequestMapping("/index2")
	public ModelAndView index() {
		return new ModelAndView("system/common/index");
	}
	
	/**
	 * 进入首页后的默认页面
	 * @return
	 */
	@RequestMapping(value="/login_default")
	public String defaultPage(){
		return "system/common/home";
	}
	
	@RequestMapping(value="/tab")
	public String tab(){
		return "system/common/tab";
	}
	@RequestMapping("/getJson")
	@ResponseBody
	public Object getJson(int id) throws ApplicationException {
		if(id == 0 ) {
			throw ApplicationException.newInstance(ErrorCode.PARAM_ILLEGAL, "id");
		}
		Map<String, Object> json = Maps.newHashMap();
		json.put("name", "廖鹏");
		return json;
	}
}
