package cn.lfy.qneng.gateway.handler;

import org.springframework.stereotype.Component;

import cn.lfy.qneng.gateway.context.Cmd;
import cn.lfy.qneng.gateway.context.Handler;
import cn.lfy.qneng.gateway.netty.message.Request;
import cn.lfy.qneng.gateway.netty.message.Response;

@Component
@Cmd(in = 1021, out = 1022)
public class PingHandler implements Handler {

	@Override
	public void action(Request req, Response resp) {
		resp.write(null);
	}

}
