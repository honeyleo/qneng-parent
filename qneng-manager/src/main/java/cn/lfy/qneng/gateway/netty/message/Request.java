package cn.lfy.qneng.gateway.netty.message;

import io.netty.channel.Channel;
import cn.lfy.qneng.gateway.model.Node;
import cn.lfy.qneng.gateway.netty.MessageWorker;

public interface Request {

	/**
	 * 包大小：cmd+data
	 * @return
	 */
	public int length();
	
	/**
	 * 请求的cmd
	 * @return
	 */
	public int cmd();

	/**
	 * 玩家
	 * @return
	 */
	public Node node();
	
	/**
	 * 维持的长连接管道
	 * @return
	 */
	public Channel channel();
	
	/**
	 * 当前玩家的消息工作者
	 * @return
	 */
	public MessageWorker messageWorker();
	
	public <T> T readXML(Class<T> cls);
	
}
