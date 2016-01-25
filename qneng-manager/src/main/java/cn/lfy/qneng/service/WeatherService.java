package cn.lfy.qneng.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class WeatherService {

	static Weather DEFAULT = new Weather();
	static Map<Long, Weather> MAP = new ConcurrentHashMap<Long, Weather>();
	public void put(Long stationId, Weather weather) {
		MAP.put(stationId, weather);
	}
	public Weather getWeatherByStationId(Long stationId) {
		Weather weather = MAP.get(stationId);
		if(weather == null) {
			return DEFAULT;
		}
		return weather;
	}
	public void clear() {
		MAP.clear();
	}
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
	
	public static void main(String[] args) {
		WeatherService service = new WeatherService();
		service.getWeatherByStationId(0L);
	}
}
