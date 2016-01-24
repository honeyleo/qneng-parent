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
			<form action="manager/station/list" method="post" name="stationForm" id="userForm">
			<table>
				<tr>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="nameLike" value="${pd.nameLike }" placeholder="这里输入关键词" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
				</tr>
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th>序号</th>
						<th>编号</th>
						<th>电站名称</th>
						<th>省市</th>
						<th>详细地址</th>
						<th>关联用户</th>
						<th><i class="icon-time hidden-phone"></i>创建时间</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:forEach items="${requestScope.result.data}" var="entity" varStatus="var">
									
					<tr>
						<td class='center' style="width: 30px;">${var.index+1}</td>
						<td>${entity.id }</td>
						<td><a>${entity.name }</a></td>
						<td>${entity.address }</td>
						<td>${entity.province }${entity.city }</td>
						<td>${funcs:getUserName(entity.userId)}</td>
						<td>${funcs:formatDateTime(entity.createTime,'yyyy-MM-dd HH:mm:ss')}</td>
						<td style="width: 60px;">
							<div class='hidden-phone visible-desktop btn-group'>
								<a class='btn btn-mini btn-info' title="编辑" onclick="editUser('${entity.id }');">修改</a>
								<a class='btn btn-mini btn-danger' title="删除" onclick="delUser('${entity.id }','${entity.name }');">删除</a>
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
					<a class="btn btn-small btn-success" onclick="add();">新增</a>
				</td>
				<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
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
		
		//检索
		function search(){
			top.jzts();
			$("#stationForm").submit();
		}
		
		//新增
		function add(){
			 //top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>manager/station/goadd';
			 diag.Width = 320;
			 diag.Height = 430;
			 diag.CancelEvent = function(){ //关闭事件
				 self.location=self.location;
				diag.close();
			 };
			 diag.show();
		}
		
		//修改
		function editUser(id){
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="资料";
			 diag.URL = '<%=basePath%>manager/station/detail?id='+id;
			 diag.Width = 320;
			 diag.Height = 430;
			 diag.CancelEvent = function(){ //关闭事件
				 self.location=self.location;
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function delUser(id,msg){
			bootbox.confirm("确定要删除["+msg+"]吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>manager/station/del?id="+id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						if(data.ret == 0) {
							nextPage(${page.currentPage});
						} else {
							bootbox.alert(data.msg);
						}
					});
				}
			});
		}
		
		</script>
		
	</body>
</html>

