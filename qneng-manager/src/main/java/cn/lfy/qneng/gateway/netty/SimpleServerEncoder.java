package cn.lfy.qneng.gateway.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 服务端编码器：编码输出给客户端的字节流
 * @author Leo.liao
 *
 */
public class SimpleServerEncoder extends MessageToByteEncoder<Packet> {

	private static final Logger LOG = LoggerFactory.getLogger(SimpleServerEncoder.class.getName());
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out)
			throws Exception {
	}

}
