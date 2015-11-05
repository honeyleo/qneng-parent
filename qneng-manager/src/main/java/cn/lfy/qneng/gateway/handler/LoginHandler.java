package cn.lfy.qneng.gateway.handler;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.lfy.qneng.gateway.GateServer;
import cn.lfy.qneng.gateway.context.Cmd;
import cn.lfy.qneng.gateway.context.Handler;
import cn.lfy.qneng.gateway.model.NodeAuthReq;
import cn.lfy.qneng.gateway.model.NodeAuthResp;
import cn.lfy.qneng.gateway.netty.message.Request;
import cn.lfy.qneng.gateway.netty.message.Response;

@Component
@Cmd(in = 1001, out = 1002)
public class LoginHandler implements Handler {

	@Resource
	private GateServer gateServer;
	
	@Override
	public void action(Request req, Response resp) {
		NodeAuthReq nodeAuthReq = req.readXML(NodeAuthReq.class);
		System.out.println(nodeAuthReq);
		req.messageWorker().setTaskExec(gateServer.getWorker(nodeAuthReq.getNo()));
		NodeAuthResp nodeAuthResp = new NodeAuthResp();
		nodeAuthResp.setNo(nodeAuthReq.getNo());
		nodeAuthResp.setAuth("OK");
		resp.write(nodeAuthResp);
	}

}
