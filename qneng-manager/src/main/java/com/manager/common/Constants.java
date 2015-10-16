package com.manager.common;

import com.manager.common.web.ExcelView;
import com.manager.common.web.JSONView;

public interface Constants
{

	String SESSION_LOGIN_USER = "SESSION_USER";

	int PAGE_SIZE = 25;

	String EXCEL_FILE_NAME = "EXCEL_FILE_NAME";

	String EXCEL_SHEET_NAME = "EXCEL_SHEET_NAME";

	String EXCEL_CONTENT = "EXCEL_CONTENT";
	
	String EXCEL_CONTENT_TITLE_ROW = "row";
	
	String EXCEL_CONTENT_TITLE_COLUMN = "column";
	
	ExcelView EXCEL_VIEW = new ExcelView();

	String JSON_ROOT = "JSON_ROOT";

	JSONView JSON_VIEW = new JSONView();

	Integer LIST_PAGESIZE = 20;
	
	String URL_MANAGER_LOGIN = "/login";
	
}
