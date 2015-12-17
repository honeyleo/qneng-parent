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
	<!-- jsp文件头和头部 -->
	<%@ include file="../common/top.jsp"%> 
	</head> 
<body>
		
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">


	<div class="row-fluid">
	
			<!-- 检索  -->
			<form action="manager/mockmodule/list" method="post" name="moduleForm" id="moduleForm">
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th width="27">任务id</th>
						<th width="100">任务名称</th>
						<th width="100">任务分组</th>
						<th width="100">任务状态</th>
						<th width="100">CRON</th>
						<th>任务描述</th>
						<th>串/并行</th>
						<th>开机启动</th>
						<th>操作时间</th>
						<th>操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:forEach items="${list}" var="entity" varStatus="var">
									
					<tr>
						<td>${entity.jobId }</td>
						<td><a>${entity.jobName }</a></td>
						<td><a>${entity.jobGroup }</a></td>
						<td><a>${entity.jobStatus }</a></td>
						<td>${entity.cronExpression }</td>
						<td>${entity.desc}</td>
						<td>${entity.concurrent }</td>
						<td>
							
						</td>
						<td>${entity.opTime }</td>
					</tr>
				
				</c:forEach>
				</tbody>
			</table>
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
					<a title="批量生产发电数据" class="btn btn-small btn-info" onclick="batchReport();" >批量生产发电数据</a>
				</td>
			</tr>
		</table>
		</div>
		</form>
	</div>
 
 
 
 
	<!-- PAGE CONTENT ENDS HERE -->
  </div><!--/row-->
	
</div><!--/#page-content-->
</div><!--/.fluid-container#main-container-->
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='<%=basePath %>resources/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="<%=basePath %>resources/js/bootstrap.min.js"></script>
		<script src="<%=basePath %>resources/js/ace-elements.min.js"></script>
		<script src="<%=basePath %>resources/js/ace.min.js"></script>
		
		<script type="text/javascript" src="<%=basePath %>resources/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="<%=basePath %>resources/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		
		
		<script type="text/javascript" src="<%=basePath %>resources/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		$(top.hangge());
		
	$(function() {
			
			//下拉框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//复选框
			$('table th input:checkbox').on('click' , function(){
				var that = this;
				$(this).closest('table').find('tr > td:first-child input:checkbox')
				.each(function(){
					this.checked = that.checked;
					$(this).closest('tr').toggleClass('selected');
				});
					
			});
			
		});
		
		//启动
		function start(id){
			$.ajax({
	            type: "POST",
	            dataType: "json",
	            url: "<%=basePath %>manager/mockmodule/start",
	            data: {id:id},
	            success: function (data) {
	            	if(data.ret == 0) {
	            		$("#zhongxin").hide();
	            		$("#zhongxin2").show();
	            		setTimeout("self.location=self.location",2000);
	            	} else {
	            		bootbox.alert(data.msg, function(){
	            			
	            		});
	            	}
	            }
			});
		}
		
		//启动
		function stop(id){
			$.ajax({
	            type: "POST",
	            dataType: "json",
	            url: "<%=basePath %>manager/mockmodule/stop",
	            data: {id:id},
	            success: function (data) {
	            	if(data.ret == 0) {
	            		$("#zhongxin").hide();
	            		$("#zhongxin2").show();
	            		setTimeout("self.location=self.location",2000);
	            	} else {
	            		bootbox.alert(data.msg, function(){
	            			
	            		});
	            	}
	            }
			});
		}
		
		//修改
		function reportConfig(id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="资料";
			 diag.URL = '<%=basePath%>manager/mockmodule/config?id='+id;
			 diag.Width = 300;
			 diag.Height = 390;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		function mockReport(id){
			$.ajax({
	            type: "POST",
	            dataType: "json",
	            url: "<%=basePath %>manager/mockmodule/dataSubmit",
	            data: {id:id},
	            success: function (data) {
	            	if(data.ret == 0) {
	            		$("#zhongxin").hide();
	            		$("#zhongxin2").show();
	            		setTimeout("self.location=self.location",2000);
	            	} else {
	            		bootbox.alert(data.msg, function(){
	            			
	            		});
	            	}
	            }
			});
		}
		
		//修改
		function reportData(id){
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="资料";
			 diag.URL = '<%=basePath%>manager/mockmodule/data?id='+id;
			 diag.Width = 310;
			 diag.Height = 350;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		function reportAlarm(id){
			$.ajax({
	            type: "POST",
	            dataType: "json",
	            url: "<%=basePath %>manager/mockmodule/reportAlarm",
	            data: {id:id},
	            success: function (data) {
	            	if(data.ret == 0) {
	            		$("#zhongxin").hide();
	            		$("#zhongxin2").show();
	            		setTimeout("self.location=self.location",2000);
	            	} else {
	            		bootbox.alert(data.msg, function(){
	            			
	            		});
	            	}
	            }
			});
		}
		function cancelScheduled(id){
			$.ajax({
	            type: "POST",
	            dataType: "json",
	            url: "<%=basePath %>manager/mockmodule/cancelScheduled",
	            data: {id:id},
	            success: function (data) {
	            	if(data.ret == 0) {
	            		$("#zhongxin").hide();
	            		$("#zhongxin2").show();
	            		setTimeout("self.location=self.location",3000);
	            	} else {
	            		bootbox.alert(data.msg, function(){
	            			
	            		});
	            	}
	            }
			});
		}
		
		function batchReport() {
			var str = '';
			for(var i=0;i < document.getElementsByName('ids').length;i++)
			{
				if(document.getElementsByName('ids')[i].checked) {
				  	if(str=='') {
				  		str += document.getElementsByName('ids')[i].value;
				  	}
				  	else {
				  		str += ',' + document.getElementsByName('ids')[i].value;
				  	}
				}
			}
			var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="资料";
			 diag.URL = '<%=basePath%>manager/mockmodule/data?ids='+str;
			 diag.Width = 310;
			 diag.Height = 350;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		</script>
		
	</body>
</html>

