package cn.lfy.qneng.gateway.handler;

import org.springframework.stereotype.Component;

import cn.lfy.qneng.gateway.context.Cmd;
import cn.lfy.qneng.gateway.context.Handler;
import cn.lfy.qneng.gateway.model.NodeAuthReq;
import cn.lfy.qneng.gateway.model.NodeAuthResp;
import cn.lfy.qneng.gateway.netty.message.Request;
import cn.lfy.qneng.gateway.netty.message.Response;

@Component
@Cmd(in = 1001, out = 1002)
public class LoginHandler implements Handler {

	@Override
	public void action(Request req, Response resp) {
		NodeAuthReq nodeAuthReq = req.readXML(NodeAuthReq.class);
		System.out.println(nodeAuthReq);
		resp.write(new NodeAuthResp());
	}

}
