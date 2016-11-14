package cn.lfyun.wx.web;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lfy.common.model.Message;
import cn.lfyun.wx.form.LoginForm;
import cn.lfyun.wx.form.UserBean;
import cn.lfyun.wx.service.RandomBean;

import com.nettyrpc.test.client.HelloService;

@Controller
public class ValidatorController {

	@Resource
	private RandomBean randomBean;
	@Autowired
	private HelloService helloService;
	
	private AtomicInteger count = new AtomicInteger(0);
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
	public @ResponseBody
	UserBean creanteUser(@Valid UserBean userBean) {
		return userBean;
	}
	
	@RequestMapping("/random")
	public @ResponseBody
	Object random() {
		int c = count.incrementAndGet();
		long start = System.currentTimeMillis();
		String content = randomBean.random();
		System.out.println("count=" + c + " cost " + (System.currentTimeMillis() - start) + "ms " + content);
		return Message.newBuilder().data(content).build();
	}
	@RequestMapping("/hello")
	public @ResponseBody
	Object hello() {
		long start = System.currentTimeMillis();
		String content = helloService.hello("廖鹏");
		System.out.println("invoke cost " + (System.currentTimeMillis() - start) + "ms " + content);
		return Message.newBuilder().data(content).build();
	}
}
