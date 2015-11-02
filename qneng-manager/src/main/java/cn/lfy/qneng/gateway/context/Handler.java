package cn.lfy.qneng.gateway.context;

import cn.lfy.qneng.gateway.netty.message.Request;
import cn.lfy.qneng.gateway.netty.message.Response;

public interface Handler {

	void action(Request req, Response resp);
}
