<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
{
	"statusCode":"${statusCode}", 
	"message":"${param.callbackType ne 'forwardConfirm' ? message : ''}", 
	"confirmMsg":"${param.callbackType eq 'forwardConfirm' ? message : ''}",
	"navTabId":"${empty param.navTabId ? navTabId : param.navTabId}", 
	"rel":"${param.rel}",
	"callbackType":"${empty param.callbackType ? callbackType : param.callbackType}",
	"forwardUrl":"${empty forwardUrl ? param.forwardUrl : forwardUrl}",
	"render":"${empty render ? param.render : render}"
}