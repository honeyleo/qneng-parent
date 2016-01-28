package cn.lfy.qneng.gateway.handler;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import cn.lfy.qneng.gateway.LogUtil;
import cn.lfy.qneng.gateway.context.Cmd;
import cn.lfy.qneng.gateway.context.Handler;
import cn.lfy.qneng.gateway.model.NodeAlarmReq;
import cn.lfy.qneng.gateway.model.NodeAlarmResp;
import cn.lfy.qneng.gateway.netty.message.Request;
import cn.lfy.qneng.gateway.netty.message.Response;
import cn.lfy.qneng.model.Alarm;
import cn.lfy.qneng.service.AlarmService;

@Component
@Cmd(in = 1007, out = 1008)
public class ReportAlarmHandler implements Handler {

	@Resource
	private AlarmService alarmService;
	
	@Override
	public void action(Request req, Response resp) {
		NodeAlarmReq nodeAlarmReq = req.readXML(NodeAlarmReq.class);
		LogUtil.LOG.info("上报告警请求：{}", nodeAlarmReq);
		NodeAlarmResp nodeAlarmResp = new NodeAlarmResp();
		String no = nodeAlarmReq.getNo();
		nodeAlarmResp.setNo(no);
		nodeAlarmResp.setStatus("1");
		if(nodeAlarmReq != null) {
			Alarm alarm = new Alarm();
			BeanUtils.copyProperties(nodeAlarmReq, alarm);
			alarm.setCreateTime(new Date());
			alarmService.add(alarm);
			nodeAlarmResp.setStatus("0");
		}
		resp.write(nodeAlarmResp);
	}

}
