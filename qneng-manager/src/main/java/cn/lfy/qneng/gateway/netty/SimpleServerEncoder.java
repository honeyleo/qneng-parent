package cn.lfy.qneng.gateway.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.lfy.qneng.gateway.netty.message.Packet;
import cn.lfy.qneng.gateway.netty.message.Response;
import cn.lfy.qneng.gateway.netty.util.CheckSumStream;

/**
 * 服务端编码器：编码输出给客户端的字节流
 * @author Leo.liao
 *
 */
public class SimpleServerEncoder extends MessageToByteEncoder<Response> {

	static final Logger LOG = LoggerFactory.getLogger(SimpleServerEncoder.class.getName());
	
	@Override
	protected void encode(ChannelHandlerContext ctx, Response response, ByteBuf out)
			throws Exception {
		Packet packet = response.getPacket();
		int size = 3;
		int cmd = response.getCmd();
		ByteBuf buf = null;
		byte[] data = null;
		if(packet != null) {
			data = packet.writeXml().getBytes("UTF-8");
			buf = PooledByteBufAllocator.DEFAULT.buffer(data.length + 2);
			buf.writeShort(cmd);
			buf.writeBytes(data);
			size += data.length;
		} else {
			buf = PooledByteBufAllocator.DEFAULT.buffer(2);
			buf.writeShort(cmd);
		}
		
		CheckSumStream checkSumStream = new CheckSumStream();
		buf.getBytes(buf.readerIndex(), checkSumStream, buf.readableBytes());
		
		out.writeShort(size);
		out.writeByte(checkSumStream.getCheckSum());
		out.writeShort(cmd);
		
		if(data != null) {
			out.writeBytes(data);
		}
	}
	
	public static void main(String[] args) {
		ByteBuf buf = PooledByteBufAllocator.DEFAULT.buffer();
		buf.writeShort(1002);
		try {
			CheckSumStream checkSumStream = new CheckSumStream();
			buf.writeBytes("liaopeng".getBytes("UTF-8"));
			buf.getBytes(buf.readerIndex(), checkSumStream,
			        buf.readableBytes());
			System.out.println(checkSumStream.getCheckSum());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
