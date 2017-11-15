package cn.lfyun.wx.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.lfy.common.utils.MessageDigestUtil;
import cn.lfyun.wx.util.CommonUtil;
import cn.lfyun.wx.util.WeixinAuthToken;

@Controller
@RequestMapping("/wx")
public class WeixinController {

	private static final String TOKEN = "honeyleo_lafite";
	
	private static final Logger logger = LoggerFactory.getLogger(WeixinController.class);
	
	@RequestMapping("/auth")
	public void auth(HttpServletRequest request, HttpServletResponse response) {
		
		logger.info("Weixin auth param={}", request.getParameterMap());
		
		try {
			
			String signature = request.getParameter("signature");		//微信加密签名
			String timestamp = request.getParameter("timestamp");		//时间戳
			String nonce	 = request.getParameter("nonce");			//随机数
			String echostr 	 = request.getParameter("echostr");			//字符串
			
			if(null != signature && null != timestamp && null != nonce && null != echostr){/* 接口验证  */
			    List<String> list = new ArrayList<String>(3) { 
				    private static final long serialVersionUID = 2621444383666420433L; 
				    public String toString() {
				               return this.get(0) + this.get(1) + this.get(2); 
				           } 
				   }; 
				   list.add(TOKEN);
				   list.add(timestamp); 
				   list.add(nonce); 
				   Collections.sort(list);
				   String tmpStr = MessageDigestUtil.getSHA1(list.toString());
				   
				    if (signature.equals(tmpStr)) { 
				           response.getWriter().write(echostr);
				     }else { 
				    	 response.getWriter().write(echostr);
			       } 
				    response.getWriter().flush();
				    response.getWriter().close(); 
			}else{/* 消息处理  */
				logger.info("进入消息处理...");
				response.reset();
			}
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	private static final String oauth2Url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
//	private static final String APPID = "wx4772d838839345d4";
//	private static final String SECRET = "cc7305e3c176bd02f879ebc422625afd";
	
	public static String APPID = "wx93508d59f8465d57";
	public static String SECRET = "ef1da523c7593951c142298e31c3a7c3";
	
	@RequestMapping("/getBaseInfoByCode")
	public void getBaseInfoByCode(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Weixin invoke getBaseInfoByCode param={}", request.getParameterMap());
		String code = request.getParameter("code");
		String url = oauth2Url.replace("APPID", APPID).replace("CODE", code).replace("SECRET", SECRET);
		String responseContent = CommonUtil.httpsRequest(url, "GET", null);
		logger.info("Request Weixin url={} Response content={}", url, responseContent);
		JSONObject jsonObject = JSON.parseObject(responseContent);
		WeixinAuthToken weixinAuthToken = null;
		if(jsonObject.containsKey("errcode")) {
			logger.warn("Request Weixin User Info Error");
		} else {
			weixinAuthToken = JSON.parseObject(responseContent, WeixinAuthToken.class);
		}
		logger.info("Parse JSON For Object {}", weixinAuthToken);
	}
}
