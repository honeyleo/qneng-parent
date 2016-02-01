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
						<td>
						<c:if test="${entity.concurrent == true }">
								并行
							</c:if>
							<c:if test="${entity.concurrent == false }">
								串行
							</c:if>
						<td>
							<c:if test="${entity.isBoot == true }">
								是
							</c:if>
							<c:if test="${entity.isBoot == false }">
								否
							</c:if>
						</td>
						<td>
							<div class='btn-group'>
								<a class='btn btn-mini btn-info' title="更新任务表达式" onclick="updateCron('${entity.jobId }','${entity.cronExpression }');">更新表达式</a>
								<a class='btn btn-mini btn-danger' title="马上运行一次任务" onclick="runOne('${entity.jobId }');">马上运行</a>
								<c:if test="${entity.jobStatus eq 'PAUSED' }">
									<a class='btn btn-mini btn-danger' title="恢复该任务" onclick="resume('${entity.jobId }');">恢复任务</a>
								</c:if>
							<c:if test="${entity.jobStatus ne 'PAUSED' }">
								<a class='btn btn-mini btn-danger' title="暂停该任务" onclick="pause('${entity.jobId }');">暂停任务</a>
							</c:if>
							</div>
						</td>
					</tr>
				
				</c:forEach>
				</tbody>
			</table>
			
		<div class="page-header position-relative">
		<table style="width:100%;">
			<tr>
				<td style="vertical-align:top;">
				<!--  
					<a title="新增定时任务" class="btn btn-small btn-info" onclick="add();" >新增定时任务</a>
					-->
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
		
		//更新表达式
		function updateCron(id,cron){
			
			bootbox.confirm('定时任务表达式：<input id = "updateCron" type="text" value="' + cron + '"/>', function(result) {
				if(result == true) {
					cron = $('#updateCron').val();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>manager/scheduleJob/updateCron?tm='+new Date().getTime(),
				    	data: {jobId:id,cronExpression:cron}, 
						dataType:'json',
						cache: false,
						success: function(data){
							if(data.ret == 0) {
								self.location=self.location;
							} else {
								bootbox.alert(data.msg);
							}
						}
					});
				}
			});
		}
		function runOne(id) {
			bootbox.confirm('确定要马上运行一次吗？', function(result) {
				if(result == true) {
					$.ajax({
						type: "POST",
						url: '<%=basePath%>manager/scheduleJob/runOne?tm='+new Date().getTime(),
				    	data: {jobId:id}, 
						dataType:'json',
						cache: false,
						success: function(data){
							if(data.ret == 0) {
								self.location=self.location;
							} else {
								bootbox.alert(data.msg);
							}
						}
					});
				}
			});
		}
		function pause(id) {
			bootbox.confirm('确定要暂停该定时任务吗？', function(result) {
				if(result == true) {
					$.ajax({
						type: "POST",
						url: '<%=basePath%>manager/scheduleJob/pause?tm='+new Date().getTime(),
				    	data: {jobId:id}, 
						dataType:'json',
						cache: false,
						success: function(data){
							if(data.ret == 0) {
								self.location=self.location;
							} else {
								bootbox.alert(data.msg);
							}
						}
					});
				}
			});
		}
		function resume(id) {
			bootbox.confirm('确定要恢复该定时任务吗？', function(result) {
				if(result == true) {
					$.ajax({
						type: "POST",
						url: '<%=basePath%>manager/scheduleJob/resume?tm='+new Date().getTime(),
				    	data: {jobId:id}, 
						dataType:'json',
						cache: false,
						success: function(data){
							if(data.ret == 0) {
								self.location=self.location;
							} else {
								bootbox.alert(data.msg);
							}
						}
					});
				}
			});
		}
		</script>
		
	</body>
</html>

