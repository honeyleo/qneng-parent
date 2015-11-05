package cn.lfy.qneng.gateway.handler;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import cn.lfy.qneng.gateway.LogUtil;
import cn.lfy.qneng.gateway.context.Cmd;
import cn.lfy.qneng.gateway.context.Handler;
import cn.lfy.qneng.gateway.model.NodeConfigReq;
import cn.lfy.qneng.gateway.model.NodeConfigResp;
import cn.lfy.qneng.gateway.netty.message.Request;
import cn.lfy.qneng.gateway.netty.message.Response;
import cn.lfy.qneng.model.Module;
import cn.lfy.qneng.service.ModuleService;

@Component
@Cmd(in = 1003, out = 1004)
public class ReportModuleInfoHandler implements Handler {

	@Resource
	private ModuleService moduleService;
	
	@Override
	public void action(Request req, Response resp) {
		NodeConfigReq nodeConfigReq = req.readXML(NodeConfigReq.class);
		LogUtil.LOG.info("上报组件配置信息请求：{}", nodeConfigReq);
		String no = nodeConfigReq.getNo();
		Module module = moduleService.findByNo(no);
		NodeConfigResp packet = new NodeConfigResp();
		packet.setNo(no);
		if(module == null) {
			packet.setStatus("1");
		} else {
			BeanUtils.copyProperties(nodeConfigReq, module);
			moduleService.updateByIdSelective(module);
			packet.setStatus("0");
		}
		resp.write(packet);
		
	}

}
