package cn.lfy.base.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.lfy.base.form.AdminQueryForm;
import cn.lfy.base.model.Admin;
import cn.lfy.base.service.AdminService;
import cn.lfy.common.model.Criteria;
import cn.lfy.common.model.Message;
import cn.lfy.common.model.PageInfo;
import cn.lfy.common.model.RestfulResponse;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Resource
	private AdminService adminService;
	
	@RequestMapping(value = "/list", method = {RequestMethod.GET})
	public String listView() {
		return "admin/list";
	}
	@RequestMapping(value = "/add", method = {RequestMethod.GET})
	public String addView() {
		return "admin/add";
	}
	@RequestMapping(value = "/list", method = {RequestMethod.POST})
	@ResponseBody
	public RestfulResponse list(AdminQueryForm form) {
		RestfulResponse resp = new RestfulResponse();
		Criteria criteria = new Criteria();
		criteria.put("username", form.getUsername());
		PageInfo<Admin> pageInfo = adminService.findListByCriteria(criteria, form.getStart(), form.getLimit());
		resp.setSuccess(true);
		resp.setResults(pageInfo.getTotal());
		resp.setRows(pageInfo.getData());
		return resp;
	}
	@RequestMapping(value = "/add", method = {RequestMethod.POST})
	@ResponseBody
	public RestfulResponse add(Admin form) {
		RestfulResponse resp = new RestfulResponse();
		adminService.add(form);
		resp.setSuccess(true);
		return resp;
	}
	/**
     * 详情
     * 
     * @param request
     * @return
     * @throws AdminException
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Object detail(HttpServletRequest request) {
    	Message.Builder builder = Message.newBuilder();
        Long id = Long.parseLong(request.getParameter("id"));
        Admin admin = adminService.findById(id);
        builder.put("data", admin);
        return builder.build();
    }
}
