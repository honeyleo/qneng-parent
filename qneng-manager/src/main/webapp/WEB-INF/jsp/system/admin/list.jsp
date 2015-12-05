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
			<form action="manager/admin/list" method="post" name="userForm" id="userForm">
			<table>
				<tr>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="USERNAME" value="${pd.username }" placeholder="这里输入关键词" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					<td><input class="span10 date-picker" name="startDate" id="startDate"  value="${pd.startDate}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="创建日期" title="创建日期"/></td>
					<td><input class="span10 date-picker" name="endDate" name="endDate"  value="${pd.endDate}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" style="width:88px;" placeholder="创建日期" title="创建日期"/></td>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();" title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
				</tr>
			</table>
			<!-- 检索  -->
		
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th>序号</th>
						<th>编号</th>
						<th>用户名</th>
						<th>姓名</th>
						<th>手机号</th>
						<th>角色名</th>
						<th><i class="icon-time hidden-phone"></i>创建时间</th>
						<th>状态</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:forEach items="${requestScope.result.data}" var="user" varStatus="var">
									
					<tr>
						<td class='center' style="width: 30px;">
							<input type='checkbox' name='ids' value="${user.id }" id="${user.email }" alt="${user.phone }"/><span class="lbl"></span></label>
						</td>
						<td class='center' style="width: 30px;">${var.index+1}</td>
						<td>${user.id }</td>
						<td><a>${user.username }</a></td>
						<td>${user.realName }</td>
						<td>${user.phone }</td>
						<td>${funcs:getRoleName(user.roleId)}</td>
						<td>${funcs:formatDateTime(user.createTime,'yyyy-MM-dd HH:mm:ss')}</td>
						<td>
						<c:if test="${user.state == 1}">
							正常
						</c:if>
						<c:if test="${user.state == 0}">
							停用
						</c:if>
						</td>
						<td style="width: 60px;">
							<div class='hidden-phone visible-desktop btn-group'>
								<funcs:permission privilege="20" module="用戶管理_编辑">
									<a class='btn btn-mini btn-info' title="编辑" onclick="editUser('${user.id }');">修改</i></a>
								</funcs:permission>
								<funcs:permission privilege="22" module="用戶管理_删除">
									<a class='btn btn-mini btn-danger' title="删除" onclick="delUser('${user.id }','${user.username }');">删除</a>
								</funcs:permission>
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
					<funcs:permission privilege="18" module="用戶管理_新增">
						<a class="btn btn-small btn-success" onclick="add();">新增</a>
					</funcs:permission>
					<funcs:permission privilege="23" module="用戶管理_批量删除">
						<a title="批量删除" class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" >删除</i></a>
					</funcs:permission>
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
		
		//检索
		function search(){
			top.jzts();
			$("#userForm").submit();
		}
		
		//新增
		function add(){
			 //top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>manager/admin/goadd';
			 diag.Width = 300;
			 diag.Height = 350;
			 diag.CancelEvent = function(){ //关闭事件
				 /* if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 nextPage(${page.currentPage});
					 }
				} */
				self.location=self.location;
				diag.close();
			 };
			 diag.show();
		}
		
		//修改
		function editUser(user_id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="资料";
			 diag.URL = '<%=basePath%>manager/admin/detail?id='+user_id;
			 diag.Width = 300;
			 diag.Height = 350;
			 diag.CancelEvent = function(){ //关闭事件
				 self.location=self.location;
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function delUser(userId,msg){
			bootbox.confirm("确定要删除["+msg+"]吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>manager/admin/del?id="+userId+"&tm="+new Date().getTime();
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
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					var emstr = '';
					var phones = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += ',' + document.getElementsByName('ids')[i].value;
						  	
						  	if(emstr=='') emstr += document.getElementsByName('ids')[i].id;
						  	else emstr += ';' + document.getElementsByName('ids')[i].id;
						  	
						  	if(phones=='') phones += document.getElementsByName('ids')[i].alt;
						  	else phones += ';' + document.getElementsByName('ids')[i].alt;
						  }
					}
					if(str==''){
						bootbox.dialog("您没有选择任何内容!", 
							[
							  {
								"label" : "关闭",
								"class" : "btn-small btn-success",
								"callback": function() {
									//Example.show("great success");
									}
								}
							 ]
						);
						
						$("#zcheckbox").tips({
							side:3,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>manager/admin/delAll?tm='+new Date().getTime(),
						    	data: {userIds:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									if(data.ret == 0) {
										nextPage(${page.currentPage});
									} else {
										bootbox.alert(data.msg);
									}
								}
							});
						}
						
						
					}
				}
			});
		}
		
		</script>
		
		<script type="text/javascript">
		$(function() {
			
			//日期框
			$('.date-picker').datepicker();
			
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
		
		</script>
		
	</body>
</html>

