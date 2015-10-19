<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- #section:basics/sidebar -->
			<div id="sidebar" class="sidebar                  responsive">
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
				</script>

				<ul class="nav nav-list">
					
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
									<a style="cursor:pointer;" target="mainFrame"  href="${sub.url }" onclick="siMenu('z${sub.id }','lm${menu.id }','${sub.name }','${sub.url }')"><i class="icon-double-angle-right"></i>${sub.name }</a></li>
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
				</ul><!-- /.nav-list -->

				<!-- #section:basics/sidebar.layout.minimize -->
				<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
					<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
				</div>

				<!-- /section:basics/sidebar.layout.minimize -->
				<script type="text/javascript">
					try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
				</script>
			</div>