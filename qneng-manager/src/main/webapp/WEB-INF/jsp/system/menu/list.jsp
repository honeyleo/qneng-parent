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
		<link href="<%=basePath %>resources/ace/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link href="<%=basePath %>resources/ace/assets/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="<%=basePath %>resources/ace/assets/css/font-awesome.min.css" />
		<link rel="stylesheet" href="<%=basePath %>resources/ace/assets/css/ace.min.css" />
		<link rel="stylesheet" href="<%=basePath %>resources/ace/assets/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="<%=basePath %>resources/ace/assets/css/ace-skins.min.css" />
		<script type="text/javascript" src="<%=basePath %>resources/ace/assets/js/jquery.min.js"></script>

<script type="text/javascript">
	//新增
	function addmenu(parentId,onMenu){
		 top.jzts();
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="新增菜单";
		 diag.URL = '<%=basePath%>manager/menu/goadd?parentId=' + parentId + '&onMenu=' + onMenu;
		 diag.Width = 223;
		 diag.Height = 256;
		 diag.CancelEvent = function(){ //关闭事件
			if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
				top.jzts(); 
				setTimeout("location.reload()",100);
			}
			diag.close();
		 };
		 diag.show();
	}
	
	//修改
	function editmenu(menuId){
		 top.jzts();
	   	 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="编辑菜单";
		 diag.URL = '<%=basePath%>manager/menu/goedit?id='+menuId;
		 diag.Width = 223;
		 diag.Height = 256;
		 diag.CancelEvent = function(){ //关闭事件
			if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
				top.jzts(); 
				setTimeout("location.reload()",100);
			}
			diag.close();
		 };
		 diag.show();
	}
	
	//编辑顶部菜单图标
	function editTb(menuId){
		 top.jzts();
	   	 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="编辑图标";
		 diag.URL = '<%=basePath%>manager/menu/toIcon?menuId='+menuId;
		 diag.Width = 530;
		 diag.Height = 150;
		 diag.CancelEvent = function(){ //关闭事件
			if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
				top.jzts(); 
				setTimeout("location.reload()",100);
			}
			diag.close();
		 };
		 diag.show();
	}
	
	function delmenu(menuId,isParent){
		var flag = false;
		if(isParent){
			if(confirm("确定要删除该菜单吗？其下子菜单将一并删除！")){
				flag = true;
			}
		}else{
			if(confirm("确定要删除该菜单吗？")){
				flag = true;
			}
		}
		if(flag){
			top.jzts();
			var url = "<%=basePath%>manager/menu/del?menuId="+menuId+"&guid="+new Date().getTime();
			$.get(url,function(data){
				top.jzts();
				document.location.reload();
			});
		}
	}
	
	function openClose(menuId,curObj,trIndex){
		var txt = $(curObj).text();
		if(txt=="展开"){
			$(curObj).text("折叠");
			$("#tr"+menuId).after("<tr id='tempTr"+menuId+"'><td colspan='5'>数据载入中</td></tr>");
			if(trIndex%2==0){
				$("#tempTr"+menuId).addClass("main_table_even");
			}
			var url = "<%=basePath%>manager/menu/sub?parentId="+menuId+"&guid="+new Date().getTime();
			$.get(url,function(data){
				if(data.length>0){
					var html = "";
					$.each(data,function(i){
						html = "<tr style='height:24px;line-height:24px;' name='subTr"+menuId+"'>";
						html += "<td></td>";
						html += "<td style='width:300px;'><span style='width:100px;display:inline;padding-left:35%;'></span>";
						if(i==data.length-1)
							html += "<img src='<%=basePath%>resources/images/joinbottom.gif' style='vertical-align: middle;'/>";
						else
							html += "<img src='<%=basePath%>resources/images/join.gif' style='vertical-align: middle;'/>";
						html += "<span style='width:100px;display:inline;'>"+this.name+"</span>";
						html += "</td>";
						html += "<td>"+this.url+"</td>";
						html += "<td class='center'>"+this.id+"</td>";
						html += "<td class='center'>"+this.orderNo+"</td>";
						html += "<td><a class='btn btn-mini btn-info' title='编辑' onclick='editmenu(\""+this.id+"\")'><i class='icon-edit'></i></a> <a class='btn btn-mini btn-danger' title='删除' onclick='delmenu(\""+this.id+"\",false)'><i class='icon-trash'></i></a></td>";
						html += "</tr>";
						$("#tempTr"+menuId).before(html);
					});
					$("#tempTr"+menuId).remove();
					if(trIndex%2==0){
						$("tr[name='subTr"+menuId+"']").addClass("main_table_even");
					}
				}else{
					$("#tempTr"+menuId+" > td").html("没有相关数据");
				}
			},"json");
		}else{
			$("#tempTr"+menuId).remove();
			$("tr[name='subTr"+menuId+"']").remove();
			$(curObj).text("展开");
		}
	}
</script>
</head>

<body>
	<br>
	<div class="page_and_btn">
		<div>
			&nbsp;&nbsp;<a class="btn btn-small btn-success" onclick="addmenu(1, 1);">新增模块</a>
		</div>
	</div>
	<br>
	<table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
		<tr>
			<th class="center"  style="width: 50px;">序号</th>
			<th class='center'>名称</th>
			<th class='center'>资源路径</th>
			<th class='center'>菜单编号</th>
			<th class='center'>排序</th>
			<th class='center'>操作</th>
		</tr>
		</thead>
		<c:choose>
			<c:when test="${not empty menuList}">
				<c:forEach items="${menuList}" var="menu" varStatus="vs">
				<tr id="tr${menu.id }">
				<td class="center">${vs.index+1}</td>
				<td class='center'><i class="${menu.icon }">&nbsp;</i>${menu.name }&nbsp;
					<c:if test="${menu.type == '1' }">
					<span class="label label-success arrowed">系统</span>
					</c:if>
					<c:if test="${menu.type != '1' }">
					<span class="label label-important arrowed-in">业务</span>
					</c:if>
				</td>
				<td>${menu.url == '#'? '': menu.url}</td>
				<td class="center">${menu.id}</td>
				<td class='center'>${menu.orderNo }</td>
				<td style="width:25%;">
				<a class="btn btn-mini btn-success" onclick="addmenu('${menu.id}', 1);">新增</a>
				<a class='btn btn-mini btn-purple' title="图标" onclick="editTb('${menu.id }')" ><i class='icon-picture'></i></a>
				<a class='btn btn-mini btn-info' title="编辑" onclick="editmenu('${menu.id }')" ><i class='icon-edit'></i></a>
				<a class='btn btn-mini btn-danger' title="删除"  onclick="delmenu('${menu.id }',true)"><i class='icon-trash'></i></a>
				</tr>
				<c:forEach items="${menu.childList}" var="sub" varStatus="subVs">
				<tr id="tr${sub.id }" style='height:24px;line-height:24px;'>
					<td></td>
					<td style='width:300px;'>
						<span style='width:100px;display:inline;padding-left:30%;'></span>
						<c:choose>
							<c:when test="${subVs.last}">
								<img src='<%=basePath%>resources/images/joinbottom.gif' style='vertical-align: middle;'/>
							</c:when>
							<c:otherwise>
								<img src='<%=basePath%>resources/images/join.gif' style='vertical-align: middle;'/>
							</c:otherwise>
						</c:choose>
						<span style='width:100px;display:inline;'>${sub.name }</span>
					</td>
					<td>${sub.url}</td>
					<td class='center'>${sub.id}</td>
					<td class='center'>${sub.orderNo}</td>
					<td>
						<a class='btn btn-mini btn-warning' onclick="openClose('${sub.id }',this,${subVs.index })" >展开</a> 
						<a class='btn btn-mini btn-success' onclick="addmenu('${sub.id}', 0);">新增</a> 
						<a class='btn btn-mini btn-info' title='编辑' onclick="editmenu('${sub.id}')"><i class='icon-edit'></i></a> 
						<a class='btn btn-mini btn-danger' title='删除' onclick="delmenu('${sub.id}',true)"><i class='icon-trash'></i></a>
					</td>
					</tr>
				</c:forEach>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<tr>
				<td colspan="100">没有相关数据</td>
				</tr>
			</c:otherwise>
		</c:choose>
	</table>
</body>
</html>