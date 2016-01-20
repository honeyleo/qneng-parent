package com.manager.job.task;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.lfy.common.job.AbstractTaskInterface;
import cn.lfy.common.utils.DateUtils;
import cn.lfy.common.utils.HttpClient;
import cn.lfy.qneng.service.ModuleDataDayService;
import cn.lfy.qneng.service.WeatherService;

@Service
public class WeatherDayTask extends AbstractTaskInterface {

	private final static Logger LOG = LoggerFactory.getLogger(WeatherDayTask.class);
	
	@Resource
	private ModuleDataDayService moduleDataDayService;
	@Resource
	private WeatherService weatherService;
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String dateTime = DateUtils.getCurrentDateTime();
		LOG.info("开始刷新天气【dateTime={}】...", dateTime);
		long start = System.currentTimeMillis();
		Map<String, String> reqParams = new HashMap<String, String>();
		try {
			String content = HttpClient.get("http://op.juhe.cn/onebox/weather/query", reqParams);
			LOG.info("刷新天气返回内容：{}", content);
			if(content != null && !"".equals(content)) {
				JSONObject json = JSON.parseObject(content);
				JSONObject wea = json.getJSONObject("result").getJSONObject("data").getJSONObject("realtime").getJSONObject("weather");
				
			}
		} catch (Exception e) {
			LOG.error("刷新天气异常：dateTime=" + dateTime, e);
		}
		LOG.info("结束刷新天气【dateTime={} cost {}ms", dateTime, (System.currentTimeMillis() - start));
	}

}
