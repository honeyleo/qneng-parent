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
	<!-- jsp文件头和头部 -->
	<%@ include file="../common/top.jsp"%> 
	</head> 
<body>
<br>		
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">


	<div class="row-fluid">
	
			<form action="manager/role/list" method="post" name="roleForm" id="roleForm">
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th>序号</th>
						<th>编号</th>
						<th>角色名</th>
						<th>角色描述</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:forEach items="${requestScope.result.data}" var="role" varStatus="var">
									
					<tr>
						<td class='center' style="width: 30px;">${var.index+1}</td>
						<td>${role.id }</td>
						<td><a>${role.name }</a></td>
						<td>${role.desc }</td>
						<td style="width: 60px;">
							<div class='hidden-phone visible-desktop btn-group'>
								<a class='btn btn-mini btn-info' title="编辑" onclick="editRole('${role.id }');"><i class='icon-edit'></i></a>
								<a class='btn btn-mini btn-danger' title="删除" onclick="delRole('${role.id }','${role.name }');"><i class='icon-trash'></i></a>
								<a onclick="roleButton('${role.id }','add_qx');" class="btn btn-warning btn-mini" title="分配权限"><i class="icon-wrench icon-2x icon-only"></i></a>
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
					<a class="btn btn-small btn-success" onclick="addRole();">新增</a>
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
		<script type="text/javascript" src="<%=basePath %>resources/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="<%=basePath %>resources/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		
		
		<script type="text/javascript" src="<%=basePath %>resources/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		$(top.hangge());
		//按钮权限
		function roleButton(roleId){
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag = true;
			diag.Title = "权限分配";
			diag.URL = '<%=basePath%>manager/role_menu/button.do?roleId='+roleId;
			diag.Width = 400;
			diag.Height = 470;
			diag.CancelEvent = function(){ //关闭事件
				diag.close();
			};
			diag.show();
		}
		//新增角色
		function addRole(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增角色";
			 diag.URL = '<%=basePath%>manager/role/goadd';
			 diag.Width = 270;
			 diag.Height = 300;
			 diag.CancelEvent = function(){ //关闭事件
				if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					top.jzts();
					setTimeout("self.location.reload()",100);
				}
				diag.close();
			 };
			 diag.show();
		}
		//修改
		function editRole(ROLE_ID){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>manager/role/detail?id='+ROLE_ID;
			 diag.Width = 270;
			 diag.Height = 300;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					top.jzts();
					setTimeout("self.location.reload()",100);
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function delRole(ROLE_ID,msg,ROLE_NAME){
			bootbox.confirm("确定要删除["+ROLE_NAME+"]吗?", function(result) {
				if(result) {
					var url = "<%=basePath%>manager/role/del?id="+ROLE_ID+"&guid="+new Date().getTime();
					top.jzts();
					$.get(url,function(data){
						if("success" == data.result){
							top.jzts();
							document.location.reload();
						}
					});
				}
			});
		}
		
		</script>
	</body>
</html>

