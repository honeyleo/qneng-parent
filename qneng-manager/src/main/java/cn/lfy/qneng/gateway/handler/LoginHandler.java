package cn.lfy.qneng.gateway.handler;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.manager.common.util.MD5;

import cn.lfy.qneng.gateway.GateServer;
import cn.lfy.qneng.gateway.LogUtil;
import cn.lfy.qneng.gateway.context.Cmd;
import cn.lfy.qneng.gateway.context.Handler;
import cn.lfy.qneng.gateway.model.Node;
import cn.lfy.qneng.gateway.model.NodeAuthReq;
import cn.lfy.qneng.gateway.model.NodeAuthResp;
import cn.lfy.qneng.gateway.netty.message.Request;
import cn.lfy.qneng.gateway.netty.message.Response;
import cn.lfy.qneng.model.Module;
import cn.lfy.qneng.service.ModuleService;

@Component
@Cmd(in = 1001, out = 1002)
public class LoginHandler implements Handler {

	@Resource
	private GateServer gateServer;
	@Resource
	private ModuleService moduleService;
	
	@Override
	public void action(Request req, Response resp) {
		NodeAuthReq nodeAuthReq = req.readXML(NodeAuthReq.class);
		LogUtil.LOG.info("组件入网请求：{}", nodeAuthReq.toString());
		NodeAuthResp nodeAuthResp = new NodeAuthResp();
		String no = nodeAuthReq.getNo();
		nodeAuthResp.setNo(no);
		Module module = moduleService.findByNo(no);
		if(module == null) {
			nodeAuthResp.setAuth("1");
		} else if(!MD5.md5(nodeAuthReq.getNo() + ":" + module.getAppSecret()).equals(nodeAuthReq.getKey())) {
			nodeAuthResp.setAuth("2");
		} else {
			nodeAuthResp.setAuth("0");
			req.messageWorker().setTaskExec(gateServer.getWorker(nodeAuthReq.getNo()));
			Node node = new Node(no, req.channel());
			req.messageWorker().setNode(node);
			gateServer.putModule(no, node);
		}
		resp.write(nodeAuthResp);
	}

}
