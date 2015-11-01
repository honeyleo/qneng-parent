package cn.lfy.qneng.gateway.disruptor;


public class EventFactory implements com.lmax.disruptor.EventFactory<Event> {

	@Override
	public Event newInstance() {
		return new Event();
	}

}
