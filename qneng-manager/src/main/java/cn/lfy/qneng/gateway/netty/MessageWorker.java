package cn.lfy.qneng.gateway.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.lfy.qneng.gateway.disruptor.DisruptorEvent;
import cn.lfy.qneng.gateway.netty.message.Event;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

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
    public String channelId;
    public String version;

    private static TreeSet<Short> ignoreCmd = Sets.newTreeSet();
    
    /**
     * 玩家对象
     */
    private volatile Object attachment;
    /**
     * 玩家线性线程
     */
    private volatile DisruptorEvent taskExec;
    /**
     * 玩家消息工作者事件锁
     */
    private Map<Short, Long> lockCache = Maps.newConcurrentMap();
    
    /**
     * 玩家事件锁
     * @param cmd
     */
    public void lock(short cmd) {
    	if(ignoreCmd.contains(cmd)) {
    		return;
    	}
		LOG.info("{} lock cmd={}",attachment, cmd);
		long lastAccessTime = System.currentTimeMillis();
		lockCache.put(cmd, lastAccessTime);
	}
    
    /**
	 * 检查玩家事件是否已锁
	 * @param cmd
	 * @return
	 */
	public boolean checkLock(short cmd) {
		if(ignoreCmd.contains(cmd)) {
			return false;
		}
		Long lastAccessTime = lockCache.get(cmd);
		if (lastAccessTime != null) {
			// 锁异常处理: 距离上次访问时间大于1秒钟自动解锁
			if (System.currentTimeMillis() - lastAccessTime > 1 * 1000) {
				unLock(cmd);
				return false;
			}
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 解锁玩家事件
	 * @param cmd
	 */
	public void unLock(short cmd) {
		Long lastAccessTime = lockCache.remove(cmd);
		if(lastAccessTime != null) {
			long time = System.currentTimeMillis() - lastAccessTime;
			if(time > 100) {
				LOG.warn("{} unlock cmd={}------------------总耗时[time={}].", new Object[]{attachment, cmd, time});
			}
		}
	}
    
    public MessageWorker(Channel _channel){
        if (_channel == null){
            throw new NullPointerException("新建MessageWorker, channel为null");
        }
        this.channel = _channel;
        loginIp = ((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress();
        LOG.info("connection ip = {}",loginIp);
    }
    
    public void messageReceived(ByteBuf buffer) {
    	if(buffer.readableBytes() < 1){
    		LOG.info("message length error......");
            channel.close();
            return;
        }
    	//整个消息包大小
    	short length = buffer.readShort();
    	//当前请求CMD
    	short cmd = buffer.readShort();
    	//消息body
    	byte[] data = new byte[length - 2];
		buffer.readBytes(data);
		
    	if(attachment == null) {
    		processLogin(length, data, cmd);
    	} else {
    	}
    }
    
    private void processLogin(int length, byte[] data, int cmd) {
		try {
			switch (cmd) {
			case 1:
			default:
			}
		} catch (Exception e) {
		}
    }
    
    public class MessageReceivedEvent implements Runnable, Event {
    	
        private byte[] data;

        private final int length;
        
        private final int cmd;
        
        private Object attachment;
    	
    	private Channel channel;
    	
    	private MessageWorker messageWorker;

        public MessageReceivedEvent(int length, int cmd, byte[] data, 
        		Object attachment, Channel channel, MessageWorker messageWorker) {
    		this.length = length;
    		this.cmd = cmd;
    		this.data = data;
    		this.attachment = attachment;
    		this.channel = channel;
    		this.messageWorker = messageWorker;
    	}
        
        public void run() {
        	try {
            	switch (cmd) {
        			case 1:
        				logout();
        				break;
        			default:
        		}
            } catch (Throwable ex) {
            	LOG.error("处理消息出错, 消息号: [cmd={}]", cmd);
            } 
        }

    	@Override
    	public int cmd() {
    		return cmd;
    	}

    	@Override
    	public Object attachment() {
    		return attachment;
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
    	public byte[] data() {
    		return data;
    	}
    	@Override
    	public void write(Packet packet) {
    		channel.write(packet);
    		messageWorker.unLock((short)cmd);
    	}
    	
        @Override
        public String toString() {
        	return "Event: " + cmd;
        }
		
   }
    
    private AtomicBoolean offlineProcessed = new AtomicBoolean(false);
    
    /**
     * 玩家掉线处理：设置当前MessageWorker的附加对象为null，设置玩家lastOffLineTime
     */
    public void processDisconnection() {
    	LOG.info("--------------------玩家【{}】掉线-------------------", attachment);
    	if (offlineProcessed.compareAndSet(false, true)) {
    		if (attachment == null) {
                return;
            }
    	}
    }
    
    public void logout() {
    }
    
    public void executeTask(Runnable event) {
    	taskExec.publish(event);
    }

	@Override
	public String toString() {
		if(attachment != null) {
			return attachment.toString();
		} else {
			return "还没登陆的玩家";
		}
	}
    
}