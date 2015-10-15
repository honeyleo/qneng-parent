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
		<title>菜单</title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="<%=basePath %>resources/css/bootstrap.min.css" rel="stylesheet" />
		<link href="<%=basePath %>resources/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="<%=basePath %>resources/css/font-awesome.min.css" />
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
					if($("#name").val()==""){
						
						$("#name").tips({
							side:3,
				            msg:'请输入菜单名称',
				            bg:'#AE81FF',
				            time:2
				        });
						
						$("#name").focus();
						return false;
					}
					if($("#url").val()==""){
						$("#url").val('#');
					}
					if($("#orderNo").val()==""){
						
						$("#orderNo").tips({
							side:1,
				            msg:'请输入菜单序号',
				            bg:'#AE81FF',
				            time:2
				        });
						
						$("#orderNo").focus();
						return false;
					}
					
					if(isNaN(Number($("#orderNo").val()))){
						
						$("#orderNo").tips({
							side:1,
				            msg:'请输入菜单序号',
				            bg:'#AE81FF',
				            time:2
				        });
						
						$("#orderNo").focus();
						$("#orderNo").val(1);
						return false;
					}
					
					$("#menuForm").submit();
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				}
				
		</script>
		
	</head>
	
	<body>
		<form action="<%=basePath %>manager/menu/save" name="menuForm" id="menuForm" method="post">
			<input type="hidden" name="id" id="id" value="${menu.id}"/>
			<input type="hidden" name="parentId" id="parentId" value="${parentId}"/>
			<input type="hidden" name="parentIdPath" id="parentIdPath" value="${parentIdPath}"/>
			<input type="hidden" name="type" id="type" value="1"/>
			<div id="zhongxin">
			<table>
				<tr>
					<td><input type="text" name="name" id="name" placeholder="这里输入菜单名称" value="${menu.name }" title="名称"/></td>
				</tr>
				<tr>
					<td><input type="text" name="url" id="url" placeholder="这里输入链接地址" value="${menu.url }" title="地址"/></td>
				</tr>
				<tr>
					<td><input type="number" name="orderNo" id="orderNo" placeholder="这里输入序号" value="${menu.orderNo}" title="序号"/></td>
				</tr>
				<tr>
					<td>
						<select name="onMenu" id="onMenu" data-placeholder="请选择是否为菜单">
							<option value="1" <c:if test="${menu.onMenu == 1 }">selected</c:if>>是</option>
							<option value="0" <c:if test="${menu.onMenu == 0 }">selected</c:if>>否</option>
						</select>
					</td>
				</tr>
				<tr>
				<td style="text-align: center; padding-top: 10px;">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
			</table>
			</div>
			<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><img src="<%=basePath %>resources/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		</form>
	</body>
</html>