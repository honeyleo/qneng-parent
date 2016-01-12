package cn.lfy.qneng.client;

import java.io.EOFException;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.lfy.qneng.client.model.NodeAuthReq;
import cn.lfy.qneng.client.model.NodeAuthResp;
import cn.lfy.qneng.client.model.NodeConfigReq;
import cn.lfy.qneng.util.MD5;
import cn.lfy.qneng.util.XmlUtil;

public class NioClient extends Thread {

	private final static Logger LOG = LoggerFactory.getLogger(NioClient.class);
	
	private final static String IP = "120.25.14.181";
	private final static int PORT = 9000;
	
	public static interface ConnectFuture {
		void finish(NioClient client);
	}
	private Selector selector;
	private SocketChannel channel;
	private String no;

	private ScheduledFuture<?> scheduledFuture;
	
	public NioClient(String no) {
		this.no = no;
	}
	public void connect(String host, int port, ConnectFuture future) throws IOException {
		channel = SocketChannel.open();
		channel.configureBlocking(false);
		channel.connect(new InetSocketAddress(host, port));

		selector = Selector.open();
		channel.register(selector, SelectionKey.OP_CONNECT);
		process(future);
	}

	public void process(final ConnectFuture future) throws IOException {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					while (true) {
						int keyCount = selector.select();
						if (keyCount <= 0) {
							continue;
						}
						Set<SelectionKey> readyKeys = selector.selectedKeys();
						Iterator<SelectionKey> keyIt = readyKeys.iterator();
						while (keyIt.hasNext()) {
							SelectionKey key = keyIt.next();
							keyIt.remove();
							if (key.isConnectable()) {
								SocketChannel channel = (SocketChannel) key.channel();
								if (channel.isConnectionPending()) {
									channel.finishConnect();
								}
								channel.configureBlocking(false);
								future.finish(NioClient.this);
								break;
							} else if (key.isReadable()) {
								SocketChannel channel = (SocketChannel) key.channel();
								NioClient.this.read(channel);
							}
						}
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		thread.setName("Thread-" + NioClient.this.no);
		thread.start();
	}

	public void heartBeat(SocketChannel channel) {

	}

	private byte[] _read(SocketChannel channel, int length) throws IOException {
		int nrecvd = 0;
		byte[] data = new byte[length];
		ByteBuffer buffer = ByteBuffer.wrap(data);
		try {
			while (nrecvd < length) {
				long n = channel.read(buffer);
				if (n < 0)
					throw new EOFException();
				nrecvd += (int) n;
			}
		} finally {

		}
		return data;
	}

	public void read(SocketChannel channel) throws IOException {
		byte[] buf = _read(channel, 2);
		int length = ((buf[0] & 0xFF) << 8) + (buf[1] & 0xFF);
		byte[] recvData = _read(channel, length);
		ByteBuffer buffer = ByteBuffer.wrap(recvData);
		int sum = buffer.get();
		int cmd = buffer.getShort();
		LOG.info("msg[sum=" + sum + ",length=" + length + ",cmd=" + cmd + "]");
		if(cmd != 1022) {
			byte[] dst = new byte[length - 3];
			buffer.get(dst);
			String xml = new String(dst, "UTF-8");
			LOG.info("Gateway response XML=" + xml);
			switch (cmd) {
			case 1002:
				loginReponse(xml);
				break;
			case 1004:
				break;
			default:
				break;
			}
		}
	}

	public void write(byte[] bytes) throws IOException {
		ByteBuffer requestBuffer = ByteBuffer.wrap(bytes);
		while (requestBuffer.hasRemaining()) {
			channel.write(requestBuffer);
		}
		channel.register(selector, SelectionKey.OP_READ);

	}
	
	public void write(ByteBuffer buf) throws IOException {
		while (buf.hasRemaining()) {
			channel.write(buf);
		}
		channel.register(selector, SelectionKey.OP_READ);

	}
	static final ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();
	
	public static void login(final String no, final String key) throws Exception {
		NioClient client = new NioClient(no);
		client.connect(IP, PORT, new ConnectFuture() {
			
			@Override
			public void finish(final NioClient client) {
				//登录
				NodeAuthReq req = new NodeAuthReq();
				req.setNo(no);
				req.setKey(key);
				String content = XmlUtil.writeXmlDocument(req);
				byte[] bytes;
				try {
					bytes = content.getBytes("UTF-8");
					ByteBuffer body = ByteBuffer.allocate(2 + bytes.length);
					body.putShort((short)1001);
					body.put(bytes);
					body.flip();
					byte[] bodyBytes = body.array();
					int sum = sum(bodyBytes);
					
					ByteBuffer buf = ByteBuffer.allocate(3 + bodyBytes.length);
					
					buf.putShort((short)(bodyBytes.length + 1));
					buf.put((byte)sum);
					buf.put(bodyBytes);
					byte[] data = buf.array();
					System.out.println(data);
					StringBuilder sb = new StringBuilder();
					for(byte b : data) {
						String tmp = Integer.toHexString(0xFF & b);  
			            if (tmp.length() == 1)// 每个字节8为，转为16进制标志，2个16进制位  
			            {  
			                tmp = "0" + tmp;  
			            }  
			            sb.append(tmp).append(" ");  
					}
					System.out.println(sb);
					buf.flip();
					client.write(buf);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	private void loginReponse(String xml) {
		NodeAuthResp nodeAuthResp = XmlUtil.readXML(xml, NodeAuthResp.class);
		if("0".equals(nodeAuthResp.getAuth())) {
			ClientManager.add(nodeAuthResp.getNo(), NioClient.this);
			scheduledFuture = scheduled.scheduleWithFixedDelay(new Runnable() {
				
				@Override
				public void run() {
					try {
						ByteBuffer body = ByteBuffer.allocate(2);
						body.putShort((short)1021);
						body.flip();
						byte[] bodyBytes = body.array();
						int sum = sum(bodyBytes);
						
						ByteBuffer buf = ByteBuffer.allocate(3 + bodyBytes.length);
						
						buf.putShort((short)(bodyBytes.length + 1));
						buf.put((byte)sum);
						buf.put(bodyBytes);
						buf.flip();
						NioClient.this.write(buf);
					} catch(Exception e) {
						System.err.println(e);
						ClientManager.remove(no);
						close();
					}
				}
			}, 10, 10, TimeUnit.SECONDS);
			nodeConfigReq();
		}
	}
	
	/**
	 * 组件入网请求
	 */
	private void nodeConfigReq() {
		scheduled.scheduleWithFixedDelay(new Runnable() {
			
			@Override
			public void run() {
				try {
					NodeConfigReq nodeConfigReq = new NodeConfigReq();
	        		nodeConfigReq.setNo(no);
	        		nodeConfigReq.setName("test_组件");
	        		nodeConfigReq.setModel("QN1234");
	        		nodeConfigReq.setManufactory("清能");
	        		nodeConfigReq.setInstalldate("2016-01-08");
	        		nodeConfigReq.setMaxVolt(60D);
	        		nodeConfigReq.setMaxCurr(4D);
	        		nodeConfigReq.setPower(24D);
	        		NioClient.this.send(1003, nodeConfigReq);
				} catch(Exception e) {
					System.err.println(e);
					ClientManager.remove(no);
					close();
				}
			}
		}, 20, 20, TimeUnit.SECONDS);
	}
	
	public void send(int cmd, Object obj) {
		String content = XmlUtil.writeXmlDocument(obj);
		byte[] bytes;
		try {
			bytes = content.getBytes("UTF-8");
			ByteBuffer body = ByteBuffer.allocate(2 + bytes.length);
			body.putShort((short)cmd);
			body.put(bytes);
			body.flip();
			byte[] bodyBytes = body.array();
			int sum = sum(bodyBytes);
			
			ByteBuffer buf = ByteBuffer.allocate(3 + bodyBytes.length);
			
			buf.putShort((short)(bodyBytes.length + 1));
			buf.put((byte)sum);
			buf.put(bodyBytes);
			buf.flip();
			write(buf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			scheduledFuture.cancel(true);
			channel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ClientManager.remove(no);
	}
	
	public static int sum(byte[] bytes) {
		int sum = 0;
		for(byte b : bytes) {
			sum +=b;
		}
		sum = sum & 0XFF;
		return sum;
	}
	public static void main(String[] args) {
		try {
			login("1234567", MD5.md5("1234567:" + "7654321"));
//			login("qngfdz010010002", MD5.md5("qngfdz010010002:" + "qngfdz010010012_fDdfX4455"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
