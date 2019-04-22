<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String header_base_path = request.getContextPath();
%>
<nav id="bjui-navbar-collapse">
	<ul class="bjui-navbar-right">
		<li class="datetime"><div>
				<span id="bjui-date"></span> <span id="bjui-clock"></span>
			</div></li>
		 		<c:if test="${!empty sessionScope.org}">
				 	<li><a href="#">
						<span>公司:${sessionScope.org.orgName}</span>
					 </a></li>
				</c:if>
				
				<c:if test="${!empty sessionScope.dept}">
					<li><a href="#">
						<span>部门:${sessionScope.dept.deptName}</span>
					 </a></li>
				</c:if>
				
		<li class="dropdown"><a href="#" class="dropdown-toggle"
			data-toggle="dropdown">欢迎：  ${sysuser.realName} &nbsp;<span
				class="caret"></span></a>
				
			<ul class="dropdown-menu" role="menu">
				<li><a href="<%=header_base_path%>/system/user/getChangePwdPage.do" data-toggle="dialog"
					data-id="changepwd_page" data-mask="true" data-width="500"
					data-height="260">&nbsp;<span class="glyphicon glyphicon-lock"></span>
						修改密码&nbsp;
				</a></li>
				<li class="divider"></li>
				<li><a href="javascript:logout();" class="red">&nbsp;<span
						class="glyphicon glyphicon-off"></span> 注销登陆
				</a></li>
			</ul></li>
		<li class="dropdown"><a href="#"
			class="dropdown-toggle theme blue" data-toggle="dropdown"
			title="切换皮肤"><i class="fa fa-tree" style="margin-top: 8px"></i></a>
			<ul class="dropdown-menu" role="menu" id="bjui-themes">
				<li><a href="javascript:;" class="theme_default"
					data-toggle="theme" data-theme="default">&nbsp;<i
						class="fa fa-tree"></i> 黑白分明&nbsp;&nbsp;
				</a></li>
				<li><a href="javascript:;" class="theme_orange"
					data-toggle="theme" data-theme="orange">&nbsp;<i
						class="fa fa-tree"></i> 橘子红了
				</a></li>
				<li><a href="javascript:;" class="theme_purple"
					data-toggle="theme" data-theme="purple">&nbsp;<i
						class="fa fa-tree"></i> 紫罗兰
				</a></li>
				<li class="active"><a href="javascript:;" class="theme_blue"
					data-toggle="theme" data-theme="blue">&nbsp;<i
						class="fa fa-tree"></i> 天空蓝
				</a></li>
				<li><a href="javascript:;" class="theme_green"
					data-toggle="theme" data-theme="green">&nbsp;<i
						class="fa fa-tree"></i> 绿草如茵
				</a></li>
			</ul></li>
	</ul>
</nav>
