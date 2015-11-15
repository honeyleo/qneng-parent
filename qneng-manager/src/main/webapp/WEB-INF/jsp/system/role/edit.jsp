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
		<link rel="stylesheet" href="<%=basePath %>resources/css/ace.min.css" />
		<link rel="stylesheet" href="<%=basePath %>resources/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="<%=basePath %>resources/css/ace-skins.min.css" />
		<script type="text/javascript" src="<%=basePath %>resources/js/jquery-1.7.2.js"></script>
		
<script type="text/javascript">
	
	top.hangge();
	
	//保存
	function save(){
		if($("#roleName").val()==""){
			$("#roleName").focus();
			return false;
		}
		$.ajax({
            type: "POST",
            dataType: "json",
            url: "<%=basePath %>manager/role/${uri}",
            data: $('#form1').serialize(),
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
		<form action="<%=basePath %>manager/role/${uri}" name="form1" id="form1"  method="post">
		<input type="hidden" name="id" id="id" value="${role.id}"/>
			<div id="zhongxin">
			<table style="margin: 20px 10px">
				<tr>
					<td style="padding-top: 13px;">角色名称:</td>
				</tr>
				<tr>
					<td><input type="text" name="name" id="name" value="${role.name}" placeholder="这里输入名称" title="名称" /></td>
				</tr>
				<tr>
				<td style="width:70px;text-align: left;padding-top: 13px;">角色描述:
				<textarea style="width:95%;height:100px;" rows="10" cols="10" name="desc" id="desc" title="角色描述" maxlength="1000" placeholder="这里输入角色描述">${role.desc}</textarea>
				<div><font color="#808080">请不要多于1000字</font></div>
				</td>
			</tr>
				<tr>
					<td style="text-align: center;">
						<a class="btn btn-mini btn-primary" onclick="save();">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
						<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
					</td>
				</tr>
			</table>
			</div>
		</form>
	
	<div id="zhongxin2" class="center" style="display:none"><img src="<%=basePath %>resources/images/jzx.gif"  style="width: 50px;" /><br/><h4 class="lighter block green"></h4></div>
		<!-- 引入 -->
		<script src="<%=basePath %>resources/js/jquery-1.9.1.min.js"></script>
		<script type="text/javascript">window.jQuery || document.write("<script src='<%=basePath %>resources/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="<%=basePath %>resources/js/bootstrap.min.js"></script>
		<script src="<%=basePath %>resources/js/ace-elements.min.js"></script>
		<script src="<%=basePath %>resources/js/ace.min.js"></script>
</body>
</html>
