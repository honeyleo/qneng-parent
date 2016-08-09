<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户管理</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="keywords" content="" />
    <meta name="description" content="" />
    <link href="<%=basePath%>resources/css/bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>resources/css/jquery.dataTables.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>resources/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>resources/css/common.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>resources/css/jquery-ui.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>resources/css/asyncbox.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>resources/css/box.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="panel_con main_con">
    <div class="current_position">
        <ol>
            <li class="glyphicon glyphicon-folder-open icon_home"></li>
            <li>当前位置：</li>
            <li>系统管理&nbsp;&nbsp;&gt; </li>
            <li class="active">用户管理</li>
        </ol>
    </div>
    <div class="panel panel-default main_contents">
        <div class="panel-body search_list">
            <form class="navbar-form navbar-left" role="search">
            	<div class="col-width">
                    <span class="label_style">状态：</span>
                    <div class="btn-group">
                        <button type="button" class="btn btn-default btn-default-disable dropDown-style"><i
                                id="search_dropDown-status" value="">全部</i></button>
                        <button type="button"
                                class="btn btn-default dropdown-toggle  btn-default-disable search_status_list"
                                data-toggle="dropdown">
                            <span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
                        </button>
                        <ul class="dropdown-menu modify_search_status scrollBar" role="menu">
                            <li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value="">全部</a></li>
                            <li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value="1">有效</a></li>
                            <li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value="0">不可用</a></li>
                        </ul>
                    </div>
                    <input type="hidden" value="50" name="status" />
                </div>
                <div class="col-width">
                    <span class="label_style">用户名：</span>
                    <div class="form-group drop_com">
                        <input type="text" id="gameName" class="form-control searchName" placeholder="填写用户名">
                    </div>
                </div>
                <div class="col-width" style="display:none">
                    <span class="label_style">姓名：</span>
                    <div class="form-group drop_com">
                        <input type="text" id="appId" class="form-control searchName" value="" readonly />
                    </div>
                </div>
                <div class="col-width">
                    <span class="label_style">手机号：</span>
                    <div class="form-group drop_com">
                        <input type="text" id="strategyName" class="form-control searchName" placeholder="公告标题">
                    </div>
                </div>
                <div class="col-width">
                    <button class="btn btn-primary add_dialog applic_btn" data-toggle="modal" data-target="#editorDialog">新建</button>
                    <button type="button" class="btn btn-primary J_search applic_btn">查询</button>
                </div>
            </form>

        </div>
        <table id="example" class="display custom-table fixed_table" cellspacing="0" >
            <thead>
            <tr>
                <th width="40px">序号</th>
                <th>用户ID</th>
                <th>用户名</th>
                <th>姓名</th>
                <th width="140px">手机号</th>
                <th>状态</th>
                <th width="180px">创建时间</th>
                <th >角色</th>
                <th width="140px">操作</th>
            </tr>
            </thead>
            <tbody id="table" style="margin-left:15px;border:1px solid #000;"></tbody>
        </table>
    </div>
</div>
<!-- 弹出框 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">修改</h4>
            </div>
            <div class="modal-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary J_sure">确定</button>
                <button type="button" class="btn btn-primary J_delete_sure none">确定</button>
                <button type="button" class="btn btn-primary J_add_sure none">确定</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="editorDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog application_dialog">
        <div class="modal-content" id="editorModal">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">编辑用户</h4>
            </div>
            <div class="modal-body1" style="padding: 20px;">
                <table cellspacing="0" width="100%" class="modifyTable">
                    <tr>
                    <th>用户名：</th>
                    <td>
                        <input type="text" class="form-control input_common300" placeholder="用户名">
                    </td>
                </tr>
                    <tr>
                        <th>密码：</th>
                        <td>
                            <input type="text" class="form-control input_common300" id="noticeLink" placeholder="用户密码">
                        </td>
                    </tr>
                    <tr>
	                    <th>姓名：</th>
	                    <td>
	                        <input type="text" class="form-control input_common300" placeholder="用户姓名">
	                    </td>
	                </tr>
	                <tr>
	                    <th>手机号：</th>
	                    <td>
	                        <input type="text" class="form-control input_common300" placeholder="手机号">
	                    </td>
	                </tr>
                    <tr>
                        <th>状态：</th>
                        <td>
                            <div class="btn-group">
                                <button type="button" class="btn btn-default btn-default-disable dropDown-style"><i
                                        id="search_dropDown-status1" value="1">有效</i></button>
                                <button type="button"
                                        class="btn btn-default dropdown-toggle  btn-default-disable search_status_list"
                                        data-toggle="dropdown">
                                    <span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
                                </button>
                                <ul class="dropdown-menu modify_search_status1 scrollBar" role="menu">
                                    <li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value="1">有效</a></li>
                                    <li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value="0">禁用</a></li>
                                </ul>
                            </div>
                            <input type="hidden" value="50" name="status1" />
                        </td>
                    </tr>
                </table>
            </div>
            <div id="strategyTextView" style="display:none;">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary J_sure" id="updContent">确定</button>
                <button type="button" class="btn btn-primary J_add_sure none">确定</button>
                <button type="button" class="btn btn-default J_close_sure" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
<script id="infoDialog" type="text/x-jquery-tmpl">
  <div class="modify">
  <table cellspacing="0" width="100%" class="modifyTable">
    <tr>
    <th>游戏ID：</th>
     <td><span class="gameIdText"></span></td>
     <th>游戏名称：</th>
     <td><span class="gameNameText"></span></td>
    </tr>
   <tr>
        <th>公告ID</th>
        <td><span class="strategyId"></span></td>
        <th>公告标题：</th>
        <td><span class="strategyIdText"></span></td>
  </tr>
   <tr>
        <th>状态：</th>
        <td><span class="statusText"></span></td>
        <th>开始时间：</th>
        <td><span class="startTimeText"></span></td>
    </tr>
     <tr>
        <th>结束时间：</th>
        <td><span class="endTimeText"></span></td>
        <th>更新时间：</th>
        <td><span class="updateTime"></span></td>
    </tr>
     <tr>
        <th>横屏图片：</th>
        <td>
            <img src="" class="landscapeText imgCommon" />
        </td>
        <th>竖屏图片：</th>
        <td>
            <img src="" class="verticalText imgCommon" />
        </td>
    </tr>
    <tr>
        <th>跳转链接：</th>
        <td colspan="3"><a href="" class="urlText"></a></td>
    </tr>
    <tr>
        <th>内容：</th>
        <td colspan="3"><div class="contentText"></div></td>
    </tr>
  </table>
  </div>
</script>

<script src="<%=basePath%>resources/js/common/common.js"></script>
<script src="<%=basePath%>resources/js/common/bootstrap.js"></script>
<script src="<%=basePath%>resources/js/common/bootstrap-typeahead.js"></script>
<script src="<%=basePath%>resources/js/common/jquery.dataTables.js"></script>
<script src="<%=basePath%>resources/js/common/dataTables.bootstrap.js"></script>
<script src="<%=basePath%>resources/js/common/jquery-asyncbox.js"></script>
<script src="<%=basePath%>resources/js/common/jquery-ui.min.js"></script>
<script src="<%=basePath%>resources/js/common/public.js"></script>
<script src="<%=basePath%>resources/js/admin.js"></script>
</body>
</html>
