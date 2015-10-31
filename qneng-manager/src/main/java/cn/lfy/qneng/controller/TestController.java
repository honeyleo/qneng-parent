package cn.lfy.qneng.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/test")
public class TestController {

	@RequestMapping("/list")
	@ResponseBody
	public Object list(HttpServletRequest req) throws Exception {
		System.out.println(req.getParameter("currentPage"));
		System.out.println(req.getParameter("pageSize"));
		int currentPage = Integer.parseInt(req.getParameter("currentPage"));
		int pageSize = Integer.parseInt(req.getParameter("pageSize"));
		InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("list.json");
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		String json = br.readLine();
		JSONObject jsonObject = JSON.parseObject(json);
		int total = jsonObject.getIntValue("total");
		JSONArray arr = jsonObject.getJSONArray("value");
		int start = (currentPage) * pageSize;
		int end = start + pageSize;
		if(end > total) {
			end = total;
		}
		List<Object> list = arr.subList(start, end);
		jsonObject.put("value", list);
		return jsonObject;
	}
}
