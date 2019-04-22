<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="common.jsp"%>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>cms管理系统</title>
<!-- bootstrap - css -->

<link href="<%=basePath%>/BJUI/themes/css/bootstrap.css" rel="stylesheet">
<link href="<%=basePath%>/BJUI/themes/css/style.css" rel="stylesheet">
<link href="<%=basePath%>/BJUI/themes/blue/core.css" id="bjui-link-theme" rel="stylesheet">
<link href="<%=basePath%>/BJUI/plugins/kindeditor_4.1.10/themes/default/default.css" rel="stylesheet">
<link href="<%=basePath%>/BJUI/plugins/colorpicker/css/bootstrap-colorpicker.min.css" rel="stylesheet">
<link href="<%=basePath%>/BJUI/plugins/niceValidator/jquery.validator.css" rel="stylesheet">
<link href="<%=basePath%>/BJUI/plugins/bootstrapSelect/bootstrap-select.css" rel="stylesheet">
<link href="<%=basePath%>/BJUI/themes/css/FA/css/font-awesome.min.css" rel="stylesheet">
<link href="<%=basePath%>/BJUI/themes/css/ie7.css" rel="stylesheet">
<script src="<%=basePath%>/BJUI/js/jquery-1.11.3.min.js"></script>
<script src="<%=basePath%>/BJUI/js/jquery.cookie.js"></script>
<script src="<%=basePath%>/BJUI/other/jquery.iframe-transport.js"></script>    
<script src="<%=basePath%>/BJUI/js/bjui-all.js"></script> 
<script src="<%=basePath%>/BJUI/plugins/swfupload/swfupload.js"></script>
<script src="<%=basePath%>/BJUI/plugins/kindeditor_4.1.10/kindeditor-all.min.js"></script>
<script src="<%=basePath%>/BJUI/plugins/kindeditor_4.1.10/lang/zh_CN.js"></script>
<script src="<%=basePath%>/BJUI/plugins/colorpicker/js/bootstrap-colorpicker.min.js"></script>
<script src="<%=basePath%>/BJUI/plugins/ztree/jquery.ztree.all-3.5.js"></script>
<script src="<%=basePath%>/BJUI/plugins/niceValidator/jquery.validator.js"></script>
<script src="<%=basePath%>/BJUI/plugins/niceValidator/jquery.validator.themes.js"></script>
<script src="<%=basePath%>/BJUI/plugins/bootstrap.min.js"></script>
<script src="<%=basePath%>/BJUI/plugins/bootstrapSelect/bootstrap-select.min.js"></script>
<script src="<%=basePath%>/BJUI/plugins/bootstrapSelect/defaults-zh_CN.min.js"></script>
<script src="<%=basePath%>/BJUI/plugins/icheck/icheck.min.js"></script>
<script src="<%=basePath%>/BJUI/plugins/dragsort/jquery.dragsort-0.5.1.min.js"></script>
<script src="<%=basePath%>/BJUI/plugins/other/jquery.autosize.js"></script>
<link href="<%=basePath%>/BJUI/plugins/uploadify/css/uploadify.css" rel="stylesheet">
<link href="<%=basePath%>/css/index.css" rel="stylesheet">
<script src="<%=basePath%>/BJUI/plugins/uploadify/scripts/jquery.uploadify.min.js"></script>
<script src="<%=basePath%>/BJUI/plugins/download/jquery.fileDownload.js"></script>
<script src="<%=basePath%>/BJUI/js/echarts/echarts.min.js"></script> 
<script type="text/javascript" src="<%=basePath%>/js/index.js"></script>


</head>
<body>
<input type="hidden" class="pId" >
<!-- 图片的放大操作 -->
<div class="coverage" style="display:none">
<div class="begImg" >
<a onclick="$('.coverage').hide()" class="handle" style="cursor:pointer;">x</a>
<a><img class="Img_"></a>
</div>
</div>
	<div id="bjui-window">
		<header id="bjui-header">
			<div class="bjui-navbar-header">
				<button type="button" class="bjui-navbar-toggle btn-default"
					data-toggle="collapse" data-target="#bjui-navbar-collapse">
					<i class="fa fa-bars"></i>
				</button>
				<a class="bjui-navbar-logo" href="#"><img
					src="<%=basePath%>/images/logoNew.png"></a>
			</div>
			<%@ include file="header.jsp"%>
			<%@ include file="left.jsp"%>
		</header>
		<div id="bjui-container">
			<div id="bjui-leftside">
				<div id="bjui-sidebar-s">
					<div class="collapse"></div>
				</div>
				<div id="bjui-sidebar">
					<div class="toggleCollapse">
						<h2>
							<i class="fa fa-bars"></i> 导航栏 <i class="fa fa-bars"></i>
						</h2>
						<a href="javascript:;" class="lock"><i class="fa fa-lock"></i></a>
					</div>
					<div class="panel-group panel-main" data-toggle="accordion"
						id="bjui-accordionmenu" data-heightbox="#bjui-sidebar"
						data-offsety="26"></div>
				</div>
			</div>
			<div id="bjui-navtab" class="tabsPage">
			 <div id="upload" style="display: none;position: absolute;top: 0;left: 0; z-index: 2; width: 100%;height: 100%;background: rgba(255, 255, 255, 0.54);">
			 <div class="bjui-maskProgress"  style="z-index: 2;">
			<i class="fa fa-cog fa-spin"></i>&nbsp;&nbsp;正在努力加载数据，请稍等...
			<div class="progressBg"><div class="progress"></div></div></div></div>
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<ul class="navtab-tab nav nav-tabs">
							<li data-url="<%=basePath%>/system/user/forwardMainPage.do"><a
								href="javascript:;"><span><i class="fa fa-home"></i>
										#maintab#</span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">
						<i class="fa fa-angle-double-left"></i>
					</div>
					<div class="tabsRight">
						<i class="fa fa-angle-double-right"></i>
					</div>
					<div class="tabsMore">
						<i class="fa fa-angle-double-down"></i>
					</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">#maintab#</a></li>
				</ul>
				<div class="navtab-panel tabsPageContent" style="height: 549px">
					<div class="navtabPage unitBox">
						<div class="bjui-pageContent" style="background: #FFF;">
							欢迎Loading...</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="footer.jsp"%>
	</div>
</body>
</html>