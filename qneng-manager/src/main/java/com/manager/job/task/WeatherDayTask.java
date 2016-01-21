package com.manager.job.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.lfy.common.job.AbstractTaskInterface;
import cn.lfy.common.utils.DateUtils;
import cn.lfy.common.utils.HttpClient;
import cn.lfy.qneng.model.Station;
import cn.lfy.qneng.service.ModuleDataDayService;
import cn.lfy.qneng.service.StationService;
import cn.lfy.qneng.service.WeatherService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.manager.model.Criteria;

@Service
public class WeatherDayTask extends AbstractTaskInterface {

	private final static Logger LOG = LoggerFactory.getLogger(WeatherDayTask.class);
	
	private final static String KEY = "ef4e9fa50900dba8907d2d70c1121e77";
	@Resource
	private ModuleDataDayService moduleDataDayService;
	@Resource
	private WeatherService weatherService;
	
	@Resource
	private StationService stationService;
	
	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		String dateTime = DateUtils.getCurrentDateTime();
		LOG.info("开始刷新天气【dateTime={}】...", dateTime);
		
		Criteria criteria = new Criteria();
		List<Station> list = stationService.findListByCriteria(criteria);
		weatherService.clear();
		long start = System.currentTimeMillis();
		for(Station station : list) {
			String city = station.getCity();
			if(city == null || "".equals(city)) {
				city = "东莞";
			}
			Map<String, String> reqParams = new HashMap<String, String>();
			try {
				reqParams.put("cityname", city);
				reqParams.put("key", KEY);
				String content = HttpClient.get("http://op.juhe.cn/onebox/weather/query", reqParams);
				LOG.info("刷新天气返回内容：{}", content);
				if(content != null && !"".equals(content)) {
					JSONObject json = JSON.parseObject(content);
					if(json != null && json.getIntValue("error_code") == 0) {
						JSONObject wea = json.getJSONObject("result").getJSONObject("data").getJSONObject("realtime").getJSONObject("weather");
						int temperature = wea.getIntValue("temperature");
						int weather = 1;
						String info = wea.getString("info");
						if(info.contains("晴")) {
							weather = 1;
						} else if(info.contains("雨")) {
							weather = 3;
						} else if(info.contains("阴")) {
							weather = 2;
						} else if(info.contains("云")) {
							weather = 2;
						}
						WeatherService.Weather w = new WeatherService.Weather();
						w.setTemp(temperature);
						w.setWeather(weather);
						weatherService.put(station.getId(), w);
					} else {
						LOG.warn("刷新天气返回错误.");
					}
				}
			} catch (Exception e) {
				LOG.error("刷新天气异常：dateTime=" + dateTime, e);
			}
		}
		LOG.info("结束刷新天气【dateTime={} cost {}ms", dateTime, (System.currentTimeMillis() - start));
	}

}
