<%
	String path1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ "/" + request.getContextPath();
%>
<div class="navbar navbar-inverse">
		  <div class="navbar-inner">
		   <div class="container-fluid">
			  <a class="brand"><small><i class="icon-leaf"></i>  清能云平台</small> </a>
			  
			  <ul class="nav ace-nav pull-right">
					<li class="light-blue user-profile">
						<a class="user-menu dropdown-toggle" href="javascript:;" data-toggle="dropdown">
							<img alt="FH" src="<%=path1%>resources/avatars/user.jpg" class="nav-user-photo" />
							<span id="user_info">
							<small>Welcome</small>
							${USER_NAME}
							</span>
							<i class="icon-caret-down"></i>
						</a>
						<ul id="user_menu" class="pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer">
							<li><a onclick="editUserH();" style="cursor:pointer;"><i class="icon-user"></i> 修改资料</a></li>
							<li class="divider"></li>
							<li><a href="<%=path1 %>manager/logout"><i class="icon-off"></i> 退出</a></li>
						</ul>
					</li>
			  </ul><!--/.ace-nav-->
		   </div><!--/.container-fluid-->
		  </div><!--/.navbar-inner-->
		</div><!--/.navbar-->
	
	
		<!--引入属于此页面的js -->
		<script type="text/javascript" src="<%=path1 %>resources/js/myjs/head.js"></script>
		<script type="text/javascript">
		
		var USER_ID = '${USER_ID}';
		</script>
