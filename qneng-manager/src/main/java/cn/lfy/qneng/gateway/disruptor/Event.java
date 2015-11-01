package cn.lfy.qneng.gateway.disruptor;

public class Event {

	private Runnable event;

	public void setEvent(Runnable event) {
		this.event = event;
	}
	
	public void run() {
		this.event.run();
	}
	
}
