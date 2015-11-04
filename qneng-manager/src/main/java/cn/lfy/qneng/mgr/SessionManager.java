package cn.lfy.qneng.mgr;

import java.util.Map;

import com.google.common.collect.Maps;
import com.manager.model.LoginAccount;

public class SessionManager {

	static Map<String, LoginAccount> SESSION_MAP = Maps.newConcurrentMap();
	
	public static LoginAccount  getLoginAccount(String sid) {
		return SESSION_MAP.get(sid);
	}
	
	public static void add(String sid, LoginAccount loginAccount) {
		SESSION_MAP.put(sid, loginAccount);
	}
}
