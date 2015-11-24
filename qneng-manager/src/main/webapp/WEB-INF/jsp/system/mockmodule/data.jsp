<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="<%=basePath %>resources/css/bootstrap.min.css" rel="stylesheet" />
		<link href="<%=basePath %>resources/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="<%=basePath %>resources/css/font-awesome.min.css" />
		<!-- 下拉框 -->
		<link rel="stylesheet" href="<%=basePath %>resources/css/chosen.css" />
		<link rel="stylesheet" href="<%=basePath %>resources/css/ace.min.css" />
		<link rel="stylesheet" href="<%=basePath %>resources/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="<%=basePath %>resources/css/ace-skins.min.css" />
		<script type="text/javascript" src="<%=basePath %>resources/js/jquery-1.7.2.js"></script>
		<!--提示框-->
		<script type="text/javascript" src="<%=basePath %>resources/js/jquery.tips.js"></script>
		
<script type="text/javascript">
$(function() {
	//日期框
	$('.date-picker').datepicker();
});
	$(top.hangge());
	function save2(){
		alert($('#mockModuleForm').serialize());
		$.ajax({
            type: "POST",
            dataType: "json",
            url: "<%=basePath %>manager/mockmodule/dataSubmit2",
            data: $('#mockModuleForm').serialize(),
            success: function (data) {
            	if(data.ret == 0) {
            		$("#zhongxin").hide();
            		$("#zhongxin2").show();
            		top.Dialog.close();
            	} else {
            		bootbox.alert(data.msg, function(){
            			
            		});
            	}
            }
		});
	}
	
</script>
	</head>
<body>
	<form action="<%=basePath %>manager/mockmodule/dataSubmit" name="mockModuleForm" id="mockModuleForm" method="post">
		<input type="hidden" name="ids" id="module_id" value="${ids }"/>
		<div id="zhongxin">
		<table style="margin: 20px 10px">
			<tr>
				<td>开始时间:</td>
				<td width="200"><input class="span10 date-picker" name="startTime" id="startTime"  type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="创建日期" title="创建日期"/></td>
			</tr>
			<tr>
				<td>结束时间:</td>
				<td width="200"><input class="span10 date-picker" name="endTime" id="endTime"  type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="创建日期" title="创建日期"/></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2" height="50px">
					<a class="btn btn-mini btn-primary" onclick="save2();">开始</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="<%=basePath %>resources/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='<%=basePath %>resources/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="<%=basePath %>resources/js/bootstrap.min.js"></script>
		<script src="<%=basePath %>resources/js/ace-elements.min.js"></script>
		<script src="<%=basePath %>resources/js/ace.min.js"></script>
		<script type="text/javascript" src="<%=basePath %>resources/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="<%=basePath %>resources/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
</body>
</html>