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
	$(top.hangge());
	//保存
	function save(){
		alert($('#mockModuleForm').serialize());
		$.ajax({
            type: "POST",
            dataType: "json",
            url: "<%=basePath %>manager/mockmodule/dataSubmit",
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
		<input type="hidden" name="id" id="module_id" value="${entity.id }"/>
		<div id="zhongxin">
		<table style="margin: 20px 10px">
			<tr>
				<td>开始时间:</td>
				<td width="100"><input type="text" name="startTime" id="startTime" value="${entity.startTime }" maxlength="32" /></td>
			</tr>
			<tr>
				<td>结束时间:</td>
				<td width="100"><input type="text" name="endTime" id="endTime" value="${entity.endTime }" maxlength="32" /></td>
			</tr>
			<tr>
				<td>输入电压:</td>
				<td width="100"><input type="text" name="inputVolt" id="inputVolt" value="${entity.inputVolt }" maxlength="32" /></td>
			</tr>
			<tr>
				<td>输出电压:</td>
				<td width="100"><input type="text" name="outvolt" id="outvolt" value="${entity.outvolt }" maxlength="32" /></td>
			</tr>
			<tr>
				<td>电流:</td>
				<td><input type="text" name="curr" id="curr" value="${entity.curr }" maxlength="32" /></td>
			</tr>
			<tr>
				<td>温度:</td>
				<td><input type="text" name="temp" id="temp" value="${entity.temp }" maxlength="32"/></td>
			</tr>
			<tr>
				<td>发电量:</td>
				<td><input type="text" name="capacity" id="capacity" value="${entity.capacity }" maxlength="32"/></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2" height="50px">
					<a class="btn btn-mini btn-primary" onclick="save();">启动上报发电数据</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="btn btn-mini btn-primary" onclick="save2();">时间段</a>&nbsp;&nbsp;&nbsp;&nbsp;
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
</body>
</html>