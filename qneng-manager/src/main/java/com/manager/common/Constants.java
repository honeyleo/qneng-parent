package com.manager.common;

import com.manager.common.web.ExcelView;
import com.manager.common.web.JSONView;

public interface Constants
{

	String SESSION_LOGIN_USER = "SESSION_USER";

	String OP_SESSION_LOGIN_USER = "OP_SESSION_USER";

	int PAGE_SIZE = 25;

	String EXCEL_FILE_NAME = "EXCEL_FILE_NAME";

	String EXCEL_SHEET_NAME = "EXCEL_SHEET_NAME";

	String EXCEL_CONTENT = "EXCEL_CONTENT";
	
	String EXCEL_CONTENT_TITLE_ROW = "row";
	
	String EXCEL_CONTENT_TITLE_COLUMN = "column";
	
	ExcelView EXCEL_VIEW = new ExcelView();

	String JSON_ROOT = "JSON_ROOT";

	JSONView JSON_VIEW = new JSONView();

	String SECRET_KEY = "2013";

	String ONLINE = "online";

	String OFFLINE = "offline";

	String TEMP_PREFIX = "temp_";
	
	Integer STATIS_TYPE_CHANNEL = 0;

	Integer STATIS_TYPE_SDK = 1;
	
	Long DEV_ADMIN_ROLE_ID=1L; // 开发者角色ID
	
	Long SUPER_ADMIN_ROLE_ID=2L; // 管理员角色ID
	
	Long TEAM_OPERATOR_ROLE_ID = 6L;// 商务团队副总监
	
	Long BUSINESS_OPERATOR_ROLE_ID = 7L;// 商务人员角色ID
	
	Long END_CUSTOMER_OPERATOR_ROLE_ID = 10L; // 终端客户帐号角色ID
	
	Long CP_CUSTOMER_OPERATOR_ROLE_ID = 12L; // CP推广客户帐号角色ID
	
	Long DIRECTOR_OPERATOR_ROLE_ID = 13L; //商务团队总监
	
//	Long PRESIDENT_OPERATOR_ROLE_ID = 14L; //商务团队总裁助理
	Long PRESIDENT_OPERATOR_ROLE_ID = 3L; //商务负责人
	
	Integer LIST_PAGESIZE = 20;
	
	String LOGINUSER_TYPE="LOGINUSER_TYPE";
	
	String OPERATOR_LOGIN_PREFIX="op";
	
	String ADMIN_LOGIN_PREFIX="adm";
	
	Long OPERATOR_ROOT_ID = 1L; // 外勤组根帐号
	
	//审核状态
	Integer CHECKED_TYPE = 1;
		
	//未审核状态
	Integer UN_CHECKED_TYPE = 0;
}
