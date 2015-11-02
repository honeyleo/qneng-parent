package cn.lfy.qneng.gateway.context;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

@Component
public class HandlerMgr implements ApplicationContextAware {

	private static Map<Integer, HandlerMeta> MAP_CMD = Maps.newConcurrentMap();
	
	private static ApplicationContext applicationContext;
	
	public static class HandlerMeta {
		
		private int in;
		
		private int out;
		
		private Handler handler;
		
		public HandlerMeta(int in, int out, Handler handler) {
			this.in = in;
			this.out = out;
			this.handler = handler;
		}

		public int getIn() {
			return in;
		}

		public int getOut() {
			return out;
		}

		public Handler getHandler() {
			return handler;
		}
		
	}
	public static HandlerMeta getHandler(int cmd) {
		return MAP_CMD.get(cmd);
	}
	
	@PostConstruct
	public void load() {
		Map<String, Handler> handlerMap = applicationContext.getBeansOfType(Handler.class);
		for(Entry<String, Handler> entry : handlerMap.entrySet()) {
			Handler handler = entry.getValue();
			Cmd cmd = handler.getClass().getAnnotation(Cmd.class);
			if(cmd == null) continue;
			int in = cmd.in();
			int out = cmd.out();
			register(new HandlerMeta(in, out, handler));
		}
	}
	
	public void register(HandlerMeta handler) {
		MAP_CMD.put(handler.in, handler);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		HandlerMgr.applicationContext = applicationContext;
	}
	
}
