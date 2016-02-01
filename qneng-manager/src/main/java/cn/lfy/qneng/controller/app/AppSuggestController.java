package cn.lfy.qneng.controller.app;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lfy.common.model.Message;
import cn.lfy.qneng.model.Suggest;
import cn.lfy.qneng.service.SuggestService;

import com.manager.common.util.RequestUtil;
/**
 * 终端建议意见API
 * @author leo.liao
 *
 */
@Controller
@RequestMapping("/app")
public class AppSuggestController {

	@Resource
	private SuggestService suggestService;
	
	/**
	 * 添加意见
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/suggestData")
	@ResponseBody
	public Object add(HttpServletRequest request) throws Exception {
		Message.Builder builder = Message.newBuilder();
		String content = RequestUtil.getString(request, "content");
		Suggest record = new Suggest();
		record.setContent(content);
		record.setCreateTime(new Date());
		suggestService.add(record);
		builder.setMsg("OK");
		return builder.build();
	}
}
