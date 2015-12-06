<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="funcs" uri="funcs" %>
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
	$(document).ready(function(){
		if($("#user_id").val()!=""){
			$("#username").attr("readonly","readonly");
			$("#username").css("color","gray");
		}
	});
	
	//保存
	function save(){
		if($("#role_id").val()==""){
			
			$("#role_id").tips({
				side:3,
	            msg:'选择角色',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#role_id").focus();
			return false;
		}
		if($("#username").val()==""){
			
			$("#username").tips({
				side:3,
	            msg:'输入用户名',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#username").focus();
			$("#username").val('');
			$("#username").css("background-color","white");
			return false;
		}else{
			$("#username").val(jQuery.trim($('#username').val()));
		}
		
		if($("#user_id").val()=="" && $("#password").val()==""){
			
			$("#password").tips({
				side:3,
	            msg:'输入密码',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#password").focus();
			return false;
		}
		if($("#password").val()!=$("#chkpwd").val()){
			
			$("#chkpwd").tips({
				side:3,
	            msg:'两次密码不相同',
	            bg:'#AE81FF',
	            time:3
	        });
			
			$("#chkpwd").focus();
			return false;
		}
		if($("#realName").val()==""){
			
			$("#realName").tips({
				side:3,
	            msg:'输入姓名',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#realName").focus();
			return false;
		}
		
		var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
		if($("#phone").val()==""){
			
			$("#phone").tips({
				side:3,
	            msg:'输入手机号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#phone").focus();
			return false;
		}else if($("#phone").val().length != 11 && !myreg.test($("#phone").val())){
			$("#phone").tips({
				side:3,
	            msg:'手机号格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#phone").focus();
			return false;
		}
		
		$.ajax({
            type: "POST",
            dataType: "json",
            url: "<%=basePath %>manager/admin/update2",
            data: $('#userForm').serialize(),
            success: function (data) {
            	if(data.ret == 0) {
            		$("#zhongxin").hide();
            		$("#zhongxin2").show();
            		top.Dialog.close();
            		setTimeout("self.location=self.location",100);
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
	<form action="<%=basePath %>manager/admin/update2" name="userForm" id="userForm" method="post">
		<input type="hidden" name="id" id="user_id" value="${admin.id }"/>
		<div id="zhongxin">
		<table style="margin: 20px 10px">
			
			<tr class="info">
				<td>角色:</td>
				<td>
				${funcs:getRoleName(admin.roleId)}
				</td>
			</tr>
			<tr>
				<td>用户名:</td>
				<td><input type="text" name="username" id="username" value="${admin.username }" maxlength="32" placeholder="这里输入用户名" title="用户名"/></td>
			</tr>
			<tr>
				<td>姓名:</td>
				<td><input type="text" name="realName" id="realName" value="${admin.realName }" maxlength="32" placeholder="这里输入姓名" title="姓名"/></td>
			</tr>
			<tr>
				<td>密码:</td>
				<td><input type="password" name="password" id="password"  maxlength="32" placeholder="输入密码" title="密码"/></td>
			</tr>
			<tr>
				<td>确认密码:</td>
				<td><input type="password" name="chkpwd" id="chkpwd"  maxlength="32" placeholder="确认密码" title="确认密码" /></td>
			</tr>
			<tr>
				<td>手机号:</td>
				<td><input type="number" name="phone" id="phone"  value="${admin.phone }"  maxlength="32" placeholder="这里输入手机号" title="手机号"/></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2" height="50">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
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
		
		<script type="text/javascript">
		
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			//$('.date-picker').datepicker();
			
		});
		
		</script>
	
</body>
</html>