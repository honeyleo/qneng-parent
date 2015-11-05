package cn.lfy.qneng.gateway.netty.message;

import io.netty.channel.Channel;

public class Response {

	private int cmd;
	private Channel channel;
	
	public Response(int cmd, Channel channel) {
		this.channel = channel;
		this.cmd = cmd;
	}
	
	private Packet packet;

	public int getCmd() {
		return cmd;
	}

	public Packet getPacket() {
		return packet;
	}

	public void write(Packet packet) {
		this.packet = packet;
		channel.write(this);
	}
	
	
}
