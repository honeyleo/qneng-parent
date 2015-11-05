package cn.lfy.qneng.gateway.model;

import io.netty.channel.Channel;
import cn.lfy.qneng.gateway.netty.message.Packet;
import cn.lfy.qneng.gateway.netty.message.Response;

public class Node {

	private String no;
	
	private Channel channel;

	public Node(String no, Channel channel) {
		super();
		this.no = no;
		this.channel = channel;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	public void write(int cmd, Packet packet) {
		if(channel != null && channel.isActive()) {
			new Response(cmd, channel).write(packet);
		}
	}
	
}
