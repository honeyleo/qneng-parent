package cn.lfy.qneng.gateway.netty.message;

import io.netty.channel.Channel;
import cn.lfy.qneng.gateway.netty.MessageWorker;
import cn.lfy.qneng.gateway.netty.Packet;

public interface Event {

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
	 * 包体数据
	 * @return
	 */
	public byte[] data();
	
	/**
	 * 玩家
	 * @return
	 */
	public Object attachment();
	
	/**
	 * 维持的长连接管道
	 * @return
	 */
	public Channel channel();
	
	/**
	 * 往该事件写回数据包
	 * @return
	 */
	public void write(Packet packet);
	
	/**
	 * 当前玩家的消息工作者
	 * @return
	 */
	public MessageWorker messageWorker();
	
}
