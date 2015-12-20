package cn.lfy.qneng.gateway.handler;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import cn.lfy.qneng.gateway.LogUtil;
import cn.lfy.qneng.gateway.context.Cmd;
import cn.lfy.qneng.gateway.context.Handler;
import cn.lfy.qneng.gateway.model.NodeDataReq;
import cn.lfy.qneng.gateway.model.NodeDataResp;
import cn.lfy.qneng.gateway.netty.message.Request;
import cn.lfy.qneng.gateway.netty.message.Response;
import cn.lfy.qneng.model.Bunch;
import cn.lfy.qneng.model.Module;
import cn.lfy.qneng.model.ModuleData;
import cn.lfy.qneng.service.BunchService;
import cn.lfy.qneng.service.ModuleDataService;
import cn.lfy.qneng.service.ModuleService;
/**
 * 上报发电数据
 * @author honeyleo
 *
 */
@Component
@Cmd(in = 1005, out = 1006)
public class ReportElectricHandler implements Handler {

	@Resource
	private ModuleDataService moduleDataService;
	@Resource 
	private ModuleService moduleService;
	@Resource
	private BunchService bunchService;
	
	@Override
	public void action(Request req, Response resp) {
		NodeDataReq nodeDataReq = req.readXML(NodeDataReq.class);
		LogUtil.LOG.info("上报发电数据请求：{}", nodeDataReq);
		NodeDataResp nodeDataResp = new NodeDataResp();
		String no = nodeDataReq.getNo();
		nodeDataResp.setNo(no);
		Module module = moduleService.findByNo(no);
		if(module != null) {
			Module tmp = new Module();
			tmp.setId(module.getId());
			tmp.setCurVlot(nodeDataReq.getOutvolt());
			tmp.setCurCurr(nodeDataReq.getCurr());
			tmp.setCurTemp(nodeDataReq.getTemp());
			tmp.setLastCapacity(nodeDataReq.getCapacity());
			moduleService.updateByIdSelective(tmp);
			Bunch bunch = bunchService.findById(module.getBunchId());
			if(bunch != null) {
				ModuleData moduleData = new ModuleData();
				BeanUtils.copyProperties(nodeDataReq, moduleData);
				if(nodeDataReq.getTime() != 0L) {
					if(nodeDataReq.getCapacity() == 0D) {
						moduleData.setCapacity(0D);
						moduleData.setPrevCapacity(0D);
					} else {
						moduleData.setCapacity(nodeDataReq.getCapacity() - module.getLastCapacity());
						moduleData.setPrevCapacity(module.getLastCapacity());
					}
					moduleData.setCurCapacity(nodeDataReq.getCapacity());
				}
				moduleData.setStationId(bunch.getStationId());
				moduleData.setBunchId(bunch.getId());
				moduleData.setModuleId(module.getId());
				moduleData.setCreateTime(new Date());
				moduleDataService.add(moduleData);
				nodeDataResp.setStatus("0");
				resp.write(nodeDataResp);
				return;
			}
		}
		nodeDataResp.setStatus("1");
		resp.write(nodeDataResp);
	}
	
}
