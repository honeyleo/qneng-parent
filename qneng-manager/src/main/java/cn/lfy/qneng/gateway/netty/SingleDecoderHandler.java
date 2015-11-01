package cn.lfy.qneng.gateway.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * 客户端直接连接到服务器
 * @author Leo.liao
 *
 */
public class SingleDecoderHandler extends LengthFieldBasedFrameDecoder {
	
	private final static Logger LOG = LoggerFactory.getLogger(SingleDecoderHandler.class.getName());
	
    private MessageWorker worker;
    private final ChannelGroup channelGroup;
    private Channel channel;
    
    public SingleDecoderHandler(ChannelGroup _channelGroup) {
        super(4096, 0, 2, 0, 0);
        this.channelGroup = _channelGroup;
    }

    @Override
	protected ByteBuf extractFrame(ChannelHandlerContext ctx, ByteBuf buffer,
			int index, int length) {
    	try {
    		worker.messageReceived(buffer);
    	} catch (Exception e) {
    		LOG.error("extractFrame exception.{}", e.getMessage());
    		LOG.error(e.getMessage(),e);
    	}
		return null;
	}

    @Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    	channel = ctx.channel();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		channelGroup.add(channel);
        worker = new MessageWorker(channel);
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		worker.processDisconnection();
		super.channelUnregistered(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		LOG.error("--------------------------------设备【{}】exceptionCaught.{}", worker, cause.getMessage());
		ctx.close().sync();
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent e = (IdleStateEvent) evt;
			if (e.state() == IdleState.READER_IDLE) {
				LOG.info("-----------------------------------------------------------------设备【{}】读超时，关闭连接---------------------------------------------------------------------------------", worker);
				ctx.close().sync();
			} else if (e.state() == IdleState.WRITER_IDLE) {
				LOG.info("-------------------------------------------------------------------设备【{}】写超时，关闭连接-----------------------------------------------------------------------------------", worker);
				ctx.close().sync();
			}
		}
	}
	
}
