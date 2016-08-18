package cn.lfyun.wx.web;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lfy.common.model.Message;
import cn.lfyun.wx.form.LoginForm;
import cn.lfyun.wx.form.UserBean;

@Controller
public class ValidatorController {

	@RequestMapping("/login")
	@ResponseBody
	public Object login(@Valid LoginForm form, BindingResult result) {
		System.out.println(result.getFieldError().getDefaultMessage());
		Object[] objects = result.getFieldError().getArguments();
		System.out.println("objects[0]=" + objects[0]);
		Message.Builder builder = Message.newBuilder();
		return builder.build();
	}
	
	@RequestMapping("/creanteUser")
    public @ResponseBody UserBean creanteUser(@Valid UserBean userBean){
     return userBean;
 }
}
