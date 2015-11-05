package cn.lfy.qneng.gateway.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.lfy.qneng.gateway.GateServer;
import cn.lfy.qneng.gateway.context.HandlerMgr;
import cn.lfy.qneng.gateway.context.HandlerMgr.HandlerMeta;
import cn.lfy.qneng.gateway.disruptor.DisruptorEvent;
import cn.lfy.qneng.gateway.model.ErrorCode;
import cn.lfy.qneng.gateway.model.Node;
import cn.lfy.qneng.gateway.netty.message.AbstractRequest;
import cn.lfy.qneng.gateway.netty.message.Request;
import cn.lfy.qneng.gateway.netty.message.Response;
import cn.lfy.qneng.gateway.netty.util.CheckSumStream;

/**
 * 每个客户端连接都会附带, 处理收到的消息
 * 
 * @author Leo.liao
 * 
 */
public class MessageWorker{
    private static final Logger LOG = LoggerFactory.getLogger(MessageWorker.class);

    public final String loginIp;
    private final Channel channel;
    public String version;

    private final CheckSumStream checkSumStream;
    
    /**
     * 玩家对象
     */
    private volatile Node node;
    
    /**
     * 玩家线性线程
     */
    private volatile DisruptorEvent taskExec;
	
    public MessageWorker(Channel _channel){
        if (_channel == null){
            throw new NullPointerException("新建MessageWorker, channel为null");
        }
        this.channel = _channel;
        checkSumStream = new CheckSumStream();
        loginIp = ((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress();
        LOG.info("connection ip = {}",loginIp);
    }
    
    public void messageReceived(ByteBuf buffer) {
    	if(buffer.readableBytes() < 2){
    		LOG.info("message length error......");
            channel.close();
            return;
        }
    	//整个消息包大小
    	short length = buffer.readShort();
    	if(buffer.readableBytes() < 1){
    		LOG.error("不够读取一个校验和");
    		channel.close();
    		return;
	    }
    	
    	int checkSumByte = buffer.readUnsignedByte();
		
		try {
			checkSumStream.clearSum();
			buffer.getBytes(buffer.readerIndex(), checkSumStream,
			        buffer.readableBytes());
			if (checkSumByte != checkSumStream.getCheckSum()){
	            LOG.error("校验和错误, expected: " + checkSumStream.getCheckSum() + ", actual: " + checkSumByte);
	            channel.close();
	            return;
	        }
		} catch (Throwable e) {
			LOG.error(e.getMessage(), e);
		}
		
    	//当前请求CMD
    	short cmd = buffer.readShort();
    	//消息body
    	byte[] data = new byte[length - 3];
		buffer.readBytes(data);
		Runnable task = new MessageReceivedEvent(length, cmd, data, node, channel, this);
    	if(node == null) {
    		if(cmd == 1001) {
    			GateServer.submitLogin(task);
    		} else {
    			//没授权就想干，没门
    			ErrorCode packet = new ErrorCode();
    			packet.setCode(1);
    			packet.setMsg("没授权就想干，没门");
    			new Response(1000, channel).write(packet);
    			channel.close();
    		}
    		
    	} else {
    		taskExec.publish(task);
    	}
    }
    
    public class MessageReceivedEvent extends AbstractRequest implements Runnable, Request {
    	
        private final int length;
        
        private final int cmd;
        
        private Node node;
    	
    	private Channel channel;
    	
    	private MessageWorker messageWorker;

        public MessageReceivedEvent(int length, int cmd, byte[] data, 
        		Node node, Channel channel, MessageWorker messageWorker) {
        	super(data);
    		this.length = length;
    		this.cmd = cmd;
    		this.node = node;
    		this.channel = channel;
    		this.messageWorker = messageWorker;
    	}
        
        public void run() {
        	try {
            	HandlerMeta meta = HandlerMgr.getHandler(this.cmd);
            	if(meta == null) {
            		LOG.warn("没有对应的处理类：[cmd={}]", this.cmd);
            		return;
            	}
            	meta.getHandler().action(this, new Response(meta.getOut(), channel));
            } catch (Throwable ex) {
            	LOG.error("处理消息出错, 消息号: [cmd={}]", cmd);
            } 
        }

    	@Override
    	public int cmd() {
    		return cmd;
    	}

    	@Override
    	public Node node() {
    		return node;
    	}

    	@Override
    	public Channel channel() {
    		return channel;
    	}

    	@Override
    	public MessageWorker messageWorker() {
    		return messageWorker;
    	}
    	
    	@Override
    	public int length() {
    		return length;
    	}
    	
        @Override
        public String toString() {
        	return "Event: " + cmd;
        }
		
   }
    
    private AtomicBoolean offlineProcessed = new AtomicBoolean(false);
    
    /**
     * 组件掉线处理：设置当前MessageWorker的附加对象为null，设置玩家lastOffLineTime
     */
    public void processDisconnection() {
    	LOG.info("--------------------组件【{}】掉线-------------------", node);
    	if (offlineProcessed.compareAndSet(false, true)) {
    		if (node == null) {
                return;
            }
    	}
    }
    
    public void executeTask(Runnable event) {
    	taskExec.publish(event);
    }

    public void setTaskExec(DisruptorEvent taskExec) {
    	this.taskExec = taskExec;
    }
    public void setNode(Node node) {
    	this.node = node;
    }
	@Override
	public String toString() {
		if(node != null) {
			return node.toString();
		} else {
			return "还没入网的组件";
		}
	}
    
}