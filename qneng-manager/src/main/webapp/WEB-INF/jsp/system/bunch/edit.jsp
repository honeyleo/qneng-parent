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
		if($("#station_id").val()==""){
			
			$("#station_id").tips({
				side:3,
	            msg:'选择电站',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#station_id").focus();
			return false;
		}
		if($("#name").val()==""){
			
			$("#name").tips({
				side:3,
	            msg:'输入名称',
	            bg:'#AE81FF',
	            time:2
	        });
			
			$("#name").focus();
			$("#name").val('');
			$("#name").css("background-color","white");
			return false;
		}else{
			$("#name").val(jQuery.trim($('#name').val()));
		}
		
		$.ajax({
            type: "POST",
            dataType: "json",
            url: "<%=basePath %>manager/bunch/${uri}",
            data: $('#bunchForm').serialize(),
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
	<form action="<%=basePath %>manager/bunch/${uri}" name="bunchForm" id="bunchForm" method="post">
		<input type="hidden" name="id" id="bunche_id" value="${entity.id }"/>
		<div id="zhongxin">
		<table>
			
			<tr class="info">
				<td>
				关联电站：<select name="stationId" id="station_id" data-placeholder="请选择电站" style="vertical-align:top;">
				<option value=""></option>
				<c:forEach items="${stations}" var="station">
					<option value="${station.id }" <c:if test="${station.id == entity.stationId }">selected</c:if>>${station.name }</option>
				</c:forEach>
				</select>
				</td>
			</tr>
			<tr>
				<td><input type="text" name="name" id="name" value="${entity.name }" maxlength="32" placeholder="这里输入组串名称" title="组串名称"/></td>
			</tr>
			<tr>
				<td><input type="number" name="element" id="element" placeholder="这里输入单元数" value="${entity.element}" title="单元数"/></td>
			</tr>
			<tr>
				<td><input type="number" name="line" id="line" placeholder="这里输入行数" value="${entity.line}" title="行数"/></td>
			</tr>
			<tr>
				<td><input type="number" name="row" id="row" placeholder="这里输入列数" value="${entity.row}" title="列数"/></td>
			</tr>
			<tr>
				<td style="text-align: center;">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
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