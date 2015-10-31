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
    <link href="<%=basePath%>resources/plugins/uploadify/uploadify.css" rel="stylesheet" />
    <link href="<%=basePath%>resources/plugins/uploadify/html5uploader.css" rel="stylesheet" />
    <link href="<%=basePath%>resources/css/jquery-ui.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>resources/css/asyncbox.css" rel="stylesheet" type="text/css" />
    <link href="<%=basePath%>resources/plugins/textEditor/default.css" rel="stylesheet" type="text/css"/>
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
					<span class="label_style">公告类型：</span>
			        <div class="form-group drop_com" style="width:190px">
			            <label class="radio-inline">
			              <input type="radio" name="searchInlineRadioOptions" id="searchInlineRadioOptions1" value="0" checked> 全部
			            </label>
			            <label class="radio-inline">
			              <input type="radio" name="searchInlineRadioOptions" id="searchInlineRadioOptions2" value="1"> 全局
			            </label>
			            <label class="radio-inline">
			              <input type="radio" name="searchInlineRadioOptions" id="searchInlineRadioOptions3" value=""> 普通
			            </label>
			        </div>
			        <input type="hidden" name="noticeType" id="noticeType"/>
			    </div>
                <div class="col-width">
                    <span class="label_style">游戏名称：</span>
                    <div class="form-group drop_com">
                        <input type="text" id="gameName" class="form-control searchName" placeholder="填写并选中结果">
                    </div>
                </div>
                <div class="col-width" style="display:none">
                    <span class="label_style">游戏ID：</span>
                    <div class="form-group drop_com">
                        <input type="text" id="appId" class="form-control searchName" value="" placeholder="选中结果后展示ID" readonly />
                    </div>
                </div>
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
                            <li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value="1">上线</a></li>
                            <li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value="0">下线</a></li>
                        </ul>
                    </div>
                    <input type="hidden" value="50" name="status" />
                </div>
                <div class="col-width">
                    <span class="label_style">公告标题：</span>
                    <div class="form-group drop_com">
                        <input type="text" id="strategyName" class="form-control searchName" placeholder="公告标题">
                    </div>
                </div>
                <div class="col-width-1">
                    <span class="label_style">公告时间：</span>
                    <div class="form-group drop_com date_time">
                        <input type="text" id="receiveStartTime" class="form-control" placeholder="开始时间">
                    </div>
                    -
                    <div class="form-group drop_com date_time">
                        <input type="text" id="receiveEndTime" class="form-control" placeholder="结束时间">
                    </div>
                </div>
                <div class="col-width">
                    <button class="btn btn-primary add_dialog applic_btn" data-toggle="modal" data-target="#editorDialog">新建公告</button>
                    <button type="button" class="btn btn-primary J_search applic_btn">查询</button>
                </div>
            </form>

        </div>
        <div class="alert alert-info alert-dismissible" role="alert">
            <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
            <strong>特别注意：</strong> 游戏名称，当输入文字后需要选中下拉框，才能查询到该游戏，否则查询不到，请知悉！
        </div>
        <table id="example" class="display custom-table fixed_table" cellspacing="0" width="100%">
            <thead>
            <tr>
                <th>序号</th>
                <th>游戏ID</th>
                <th>游戏名称</th>
                <th>公告ID</th>
                <th>公告标题</th>
                <th>状态</th>
                <th >开始时间</th>
                <th >结束时间</th>
                <th >更新时间</th>
                <th >操作</th>
            </tr>
            </thead>
            <tbody id="table"></tbody>
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
                <h4 class="modal-title">编辑公告</h4>
            </div>
            <div class="modal-body1" style="padding: 20px;">
                <table cellspacing="0" width="100%" class="modifyTable">
                	<tr>
						<th>全局公告：</th>
				        <td>
				            <label class="radio-inline">
				              <input type="radio" name="inlineRadioOptions" id="inlineRadioOptions1" value="0"> 否
				            </label>
				            <label class="radio-inline">
				              <input type="radio" name="inlineRadioOptions" id="inlineRadioOptions2" value="1"> 是
				            </label>
				        </td>
				    </tr>
                    <tr>
                        <th>游戏名称：</th>
                        <td>
                            <div class="edit-input">
                                <input type="text" id="gameName2" class="form-control searchName input_common300" data-id="" placeholder="填写并选中结果">
                            </div>
                            <div class="edit-input"><!-- jianbin: 不显示APPID, 改为在APP_NAME中下拉时显示 -->
                               <input type="text" id="appId2" class="form-control searchName input_common" value="" placeholder="选中结果后展示ID" readonly />
                            </div>
                        </td>
                    </tr>
                    <tr>
                    <th>公告标题：</th>
                    <td>
                        <input type="text" class="form-control input_common300" id="strategyTitle" placeholder="公告标题">
                        <input type="hidden" class="form-control input_common" id="strategyId">
                    </td>
                </tr>
                    <tr>
                        <th>跳转链接：</th>
                        <td>
                            <input type="text" class="form-control" id="noticeLink" placeholder="公告链接">
                        </td>
                    </tr>
                    <tr>
                        <th>游戏状态：</th>
                        <td>
                            <div class="btn-group">
                                <button type="button" class="btn btn-default btn-default-disable dropDown-style"><i
                                        id="search_dropDown-status1" value="1">上线</i></button>
                                <button type="button"
                                        class="btn btn-default dropdown-toggle  btn-default-disable search_status_list"
                                        data-toggle="dropdown">
                                    <span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
                                </button>
                                <ul class="dropdown-menu modify_search_status1 scrollBar" role="menu">
                                    <li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value="1">上线</a></li>
                                    <li role='presentation'><a role='menuitem' tabindex='-1' href='javascript:void(0);' value="0">下线</a></li>
                                </ul>
                            </div>
                            <input type="hidden" value="50" name="status1" />
                        </td>
                    </tr>
                    <tr>
                        <th>横屏图片：</th>
                        <td class="vertical">
                            <!--<div class="modify_icon">-->
                                <!--<img src="{{= icon}}" alt="logo" class="icon_img img_style" />-->
                                <!--<input type="hidden" value="" id="iconImg" name="iconM">-->
                                <!--<a class="del_img J_del_img" href="javascript:;">x</a>-->
                            <!--</div>-->
                            <div id="uploadPortrait" class="icon browse_file"></div>
                            <span class="ver_tips none" id="icon_name"></span>
                        </td>
                    </tr>
                    <tr>
                        <th>竖屏图片：</th>
                        <td class="landscape">
                            <!--<div class="modify_icon">-->
                                <!--<img src="{{= icon}}" alt="logo" class="icon_img1 img_style" />-->
                                <!--<input type="hidden" value="" id="iconImg1" name="iconM1">-->
                                <!--<a class="del_img J_del_img" href="javascript:;">x</a>-->
                            <!--</div>-->
                            <div id="uploadLandscape" class="icon browse_file"></div>
                            <span class="ver_tips none" id="icon_name1"></span>
                        </td>
                    </tr>
                    <tr>
                        <th>公告时间：</th>
                        <td>
                                <div class="form-group drop_com edit-input date_time">
                                    <input type="text" id="startTime1" class="form-control " placeholder="开始时间">
                                </div>

                                <div class="form-group drop_com edit-input date_time">
                                    <input type="text" id="endTime1" class="form-control" placeholder="结束时间">
                                </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" class="strategyGray">公告内容：</td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            <div class="edit_text" tabindex="0" id="editText">
                            <textarea name="content" style="width:745px;height:200px;visibility:hidden;"></textarea>
                            <input type="hidden" id="contentAppId" name="contentAppId"/>
                            <!--<div id='editorUpload'></div>-->
                            <div style="margin-top: 20px;">
<!--                                 <span>是否启用</span> 
                                <input type="radio" name="editorShow" value="1" style="margin-left: 30px;" id="showY"/>是
                                <input type="radio" name="editorShow" value="0" style="margin-left: 30px;" id="showN"/>否-->
                                <button type="button" class="btn btn-primary right" id="showPreview">预览内容</button>
                            </div>
                            </div>
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
<script id="modeDialog" type="text/x-jquery-tmpl">
  <div class="modify">
  <table cellspacing="0" width="100%" class="modifyTable">
    <tr>
     <th>游戏名称：</th>
    <td>
            <input type="text" id="gameName2" class="form-control searchName input_common" data-id="" placeholder="填写并选中结果">
            <input type="text" id="appId2" class="form-control searchName input_common margin_top" value="" placeholder="选中结果后展示ID" readonly />
        </td>
         </tr>
          <tr>
      <th>公告标题：</th>
     <td>
        <input type="text" class="form-control input_common" id="strategyTitle" placeholder="公告标题">
    </td>
          </tr>
          <tr>
    <td colspan="2">公告内容：</td>
    </tr>
    <tr>
        <td colspan="2">
        </td>
    </tr>
  </table>
  </div>
</script>
<script id="iconTemplate" type="text/x-jquery-tmpl">
    <div class="modify_icon">
        <img src="{{= url}}" alt="logo" class="icon_img img_style" />
        <input type="hidden" value="{{= url}}" id="iconM" name="iconM">
        <a class="del_img J_del_img del_img_style" href="javascript:;">x</a>
    </div>
</script>
<script id="iconTemplate1" type="text/x-jquery-tmpl">
    <div class="modify_icon">
        <img src="{{= url}}" alt="logo" class="icon_img1 img_style" />
        <input type="hidden" value="{{= url}}" id="iconM1" name="iconM1">
        <a class="del_img J_del_img del_img_style" href="javascript:;">x</a>
    </div>
</script>
<script src="<%=basePath%>resources/js/common/common.js"></script>
<script src="<%=basePath%>resources/js/common/bootstrap.js"></script>
<script src="<%=basePath%>resources/js/common/bootstrap-typeahead.js"></script>
<script src="<%=basePath%>resources/js/common/jquery.dataTables.js"></script>
<script src="<%=basePath%>resources/js/common/dataTables.bootstrap.js"></script>
<script src="<%=basePath%>resources/js/common/jquery-ui-datepicker.js"></script>
<script src="<%=basePath%>resources/js/common/jquery-asyncbox.js"></script>
<script src="<%=basePath%>resources/plugins/uploadify/jquery-uploadify.min.js"></script>
<script src="<%=basePath%>resources/plugins/uploadify/jquery.html5uploader.js"></script>
<script src="<%=basePath%>resources/js/common/jquery-ui.min.js"></script>
<script src="<%=basePath%>resources/js/common/jquery-ui-timepicker-addon.js"></script>
<script src="<%=basePath%>resources/js/common/jquery-ui-timepicker-zh-CN.js"></script>
<script src="<%=basePath%>resources/js/common/public.js"></script>
<script src="<%=basePath%>resources/plugins/textEditor/kindeditor-min.js"></script>
<script src="<%=basePath%>resources/plugins/textEditor/zh_CN.js"></script>
<script src="<%=basePath%>resources/js/common/jquery.windows-engine.js"></script>
<script src="<%=basePath%>resources/js/notice.js"></script>
</body>
</html>
