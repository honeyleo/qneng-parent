package cn.lfy.qneng.controller.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lfy.common.model.Message;
import cn.lfy.qneng.mgr.SessionManager;
import cn.lfy.qneng.model.Station;
import cn.lfy.qneng.service.StationService;

import com.manager.common.Constants;
import com.manager.common.ErrorCode;
import com.manager.common.exception.ApplicationException;
import com.manager.common.util.MessageDigestUtil;
import com.manager.common.util.RequestUtil;
import com.manager.model.Admin;
import com.manager.model.Criteria;
import com.manager.model.LoginAccount;
import com.manager.model.type.StateType;
import com.manager.service.AdminService;

@Controller
@RequestMapping("/app")
public class AppLoginController implements Constants {

	@Autowired
    private AdminService adminService;
	@Autowired
	private StationService stationService;
	
	@RequestMapping("/login")
	@ResponseBody
	public Object login(HttpServletRequest request) throws Exception {
		Message.Builder builder = Message.newBuilder();
		String username = RequestUtil.getString(request, "username");
		if(StringUtils.isEmpty(username)) {
			throw ApplicationException.newInstance(ErrorCode.PARAM_ILLEGAL, "username");
		}
		String password = RequestUtil.getString(request, "password");
		if(StringUtils.isEmpty(password)) {
			throw ApplicationException.newInstance(ErrorCode.PARAM_ILLEGAL, "password");
		}
		try {
            password = MessageDigestUtil.getSHA256(password).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
		LoginAccount account = getAdminLoginUser(username);
		if(account == null) {
			throw ApplicationException.newInstance(ErrorCode.NOT_EXIST, "username");
		}
		Admin user = account.getUser();
		if (user == null) {
			throw ApplicationException.newInstance(ErrorCode.NOT_EXIST, "username");
        }
        if (user.getState().equals(StateType.INACTIVE)) {
        	throw ApplicationException.newInstance(ErrorCode.ERROR);
        }
        if (!password.equals(user.getPassword())) {
        	throw ApplicationException.newInstance(ErrorCode.ERROR);
        }
        
        Station station = null;
        Criteria criteria = new Criteria();
        criteria.put("userId", user.getId());
        List<Station> list = stationService.findListByCriteria(criteria);
        if(list != null && !list.isEmpty()) {
        	station = list.get(0);
        }
        account.setId(user.getId());
        HttpSession session = request.getSession(true);
        session.setAttribute(SESSION_LOGIN_USER, account);
        String sid = session.getId();
        SessionManager.add(sid, account);
        builder.put("sid", sid);
        if(station != null) {
        	builder.put("stationId", station.getId());
        }
		return builder.build();
	}
	
	private LoginAccount getAdminLoginUser(String username){
        Admin admin = adminService.findByUsername(username);
        if(admin == null){
            return null;
        }
        LoginAccount account = new LoginAccount();
        Admin user = new Admin();
        user.setId(admin.getId());
        user.setUsername(admin.getUsername());
        user.setPassword(admin.getPassword());
        user.setRealName(admin.getRealName());
        user.setEmail(admin.getEmail());
        user.setRoleId(admin.getRoleId());
        user.setState(admin.getState());
        user.setOperatorId(admin.getOperatorId());
        account.setUser(user);

        return account;
    }
}
