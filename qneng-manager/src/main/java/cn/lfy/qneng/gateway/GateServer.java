package cn.lfy.qneng.gateway;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.DefaultThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.lfy.qneng.gateway.netty.SimpleChannelOutboundHandler;
import cn.lfy.qneng.gateway.netty.SimpleServerEncoder;
import cn.lfy.qneng.gateway.netty.SingleDecoderHandler;

public class GateServer {

	private int bossThread;
	private int workerThread;
	private int port;
	static ServerBootstrap serverBootstrap;

	private static ChannelGroup allChannels = new DefaultChannelGroup(new DefaultEventExecutorGroup(1).next());
	private static Logger logger = LoggerFactory.getLogger(GateServer.class);
	
	public void start() {
		// Configure the server.
		EventLoopGroup bossGroup = new NioEventLoopGroup(bossThread, new DefaultThreadFactory("BOSS"));
		EventLoopGroup workerGroup = new NioEventLoopGroup(workerThread, new DefaultThreadFactory("NETTY_WORKER"));
		
		try {
			serverBootstrap = new ServerBootstrap();
			
			serverBootstrap.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.option(ChannelOption.TCP_NODELAY, true)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.option(ChannelOption.SO_RCVBUF, 1048576)
				.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline()
							.addLast("idleStateHandler", new IdleStateHandler(300, 300, 0))
							.addLast(new SingleDecoderHandler(allChannels))
							.addLast("encoder", new SimpleServerEncoder())
							.addLast(new SimpleChannelOutboundHandler())
							;
					}
				});
			allChannels.add(serverBootstrap.bind(port).channel());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		logger.info("网关服务器启动成功，开始监听{} 端口...", port);
	}
	public void setPort(int port) {
		this.port = port;
	}
	public void setBossThread(int bossThread) {
		this.bossThread = bossThread;
	}
	public void setWorkerThread(int workerThread) {
		this.workerThread = workerThread;
	}
	
}
