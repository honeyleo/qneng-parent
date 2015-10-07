	<%
	String path2 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ "/" + request.getContextPath();
	%>
	<meta charset="utf-8" />
	<title>FH Admin</title>
	<meta name="description" content="overview & stats" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<!-- basic styles -->
	<link href="<%=path2 %>resources/css/bootstrap.min.css" rel="stylesheet" />
	<link href="<%=path2 %>resources/css/bootstrap-responsive.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="<%=path2 %>resources/css/font-awesome.min.css" />
	<!-- page specific plugin styles -->
	<!-- 下拉框-->
	<link rel="stylesheet" href="<%=path2 %>resources/css/chosen.css" />
	<!-- ace styles -->
	<link rel="stylesheet" href="<%=path2 %>resources/css/ace.min.css" />
	<link rel="stylesheet" href="<%=path2 %>resources/css/ace-responsive.min.css" />
	<link rel="stylesheet" href="<%=path2 %>resources/css/ace-skins.min.css" />
	<script type="text/javascript" src="<%=path2 %>resources/js/jquery-1.7.2.js"></script>
	<link rel="stylesheet" href="<%=path2 %>resources/css/datepicker.css" /><!-- 日期框 -->
	<!--引入弹窗组件start-->
	<script type="text/javascript" src="<%=path2 %>plugins/attention/zDialog/zDrag.js"></script>
	<script type="text/javascript" src="<%=path2 %>plugins/attention/zDialog/zDialog.js"></script>
	<!--引入弹窗组件end-->
	<script type="text/javascript" src="<%=path2 %>resources/js/jquery.tips.js"></script>