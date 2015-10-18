<%
	String pathl = request.getContextPath();
	String basePathl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+pathl+"/";
%>
		<!-- 本页面涉及的js函数，都在head.jsp页面中     -->
		<div id="sidebar" class="menu-min">

				<div id="sidebar-shortcuts">

					

					

				</div><!-- #sidebar-shortcuts -->


				<ul class="nav nav-list">

					<li class="active" id="fhindex">
					  <a href="manager/index"><i class="icon-dashboard"></i><span>后台首页</span></a>
					</li>



			<c:forEach items="${menuList}" var="menu">
				<c:if test="${menu.onMenu == 1}">
				<li id="lm${menu.id }">
					  <a style="cursor:pointer;" class="dropdown-toggle" >
						<i class="${menu.icon == null ? 'icon-desktop' : menu.icon}"></i>
						<span>${menu.name }</span>
						<b class="arrow icon-angle-down"></b>
					  </a>
					  <ul class="submenu" style="display: block;">
							<c:forEach items="${menu.childList}" var="sub">
								<c:if test="${sub.onMenu == 1}">
								<c:choose>
									<c:when test="${not empty sub.url}">
									<li id="z${sub.id }">
									<a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('z${sub.id }','lm${menu.id }','${sub.name }','${sub.url }')"><i class="icon-double-angle-right"></i>${sub.name }</a></li>
									</c:when>
									<c:otherwise>
									<li><a href="javascript:void(0);"><i class="icon-double-angle-right"></i>${sub.name }</a></li>
									</c:otherwise>
								</c:choose>
								</c:if>
							</c:forEach>
				  		</ul>
				</li>
				</c:if>
			</c:forEach>

				</ul><!--/.nav-list-->

				<div id="sidebar-collapse"><i class="icon-double-angle-left"></i></div>

			</div><!--/#sidebar-->

