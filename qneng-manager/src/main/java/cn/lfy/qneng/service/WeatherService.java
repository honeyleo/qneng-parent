package cn.lfy.qneng.service;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

@Service
public class WeatherService {

	public static class Weather {
		private int temp;
		private int weather;
		public int getTemp() {
			return temp;
		}
		public void setTemp(int temp) {
			this.temp = temp;
		}
		public int getWeather() {
			return weather;
		}
		public void setWeather(int weather) {
			this.weather = weather;
		}
		
	}
	
	static final ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();
	private static Weather weather = new Weather();
	
	public static Weather getWeather() {
		return weather;
	}
	@PostConstruct
	public void init() {
		scheduled.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				Random random = new Random(System.currentTimeMillis());
				int temp = 10 + random.nextInt(16);
				int weather = 1 + random.nextInt(3);
				WeatherService.weather.setTemp(temp);
				WeatherService.weather.setWeather(weather);
			}
		}, 0, 1, TimeUnit.DAYS);
	}
	@PreDestroy
	public void shutdown() {
		scheduled.shutdownNow();
	}
}
