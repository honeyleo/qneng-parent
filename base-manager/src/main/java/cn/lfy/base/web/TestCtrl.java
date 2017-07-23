package cn.lfy.base.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

import cn.lfy.common.framework.exception.ApplicationException;
import cn.lfy.common.model.Message;

@Api(value="test")
@Controller
public class TestCtrl {

	@RequestMapping(value = "/test/{id}")
    @ResponseBody
    @ApiOperation(value="根据ID获取用户信息",httpMethod="GET",notes="get user by id")  
    public Object test(@PathVariable String id) throws ApplicationException {
		Message.Builder builder = Message.newBuilder();
		builder.put("name", id);
		return builder.build();
	}
}
