package cn.lfy.qneng.client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientManager {

	private static final Map<String, NioClient> map = new ConcurrentHashMap<String, NioClient>();
	
	public static void add(String no, NioClient client) {
		map.put(no, client);
	}
	
	public static NioClient getClient(String no) {
		return map.get(no);
	}
	
	public static void remove(String no) {
		map.remove(no);
	}
}
