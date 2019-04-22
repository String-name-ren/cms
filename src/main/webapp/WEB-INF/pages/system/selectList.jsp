<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
 <div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch"
		action="<%=basePath%>/system/white/selectList.do"
		method="post"> 
		<div class="bjui-searchBar">
			<label>录入人：</label><input type="text" value="${userName}"
				name="userName" class="form-control" size="8">&nbsp; 
			<label>登入人：</label><input type="text" value="${sysUserName}"
				name="sysUserName" class="form-control" size="8">&nbsp; 
			<label>更新时间:</label>
			 	<input type="text" id="startTime" readonly 
				value="${startTime}"
				name="startTime" size="20" data-toggle="datepicker"
				data-pattern="yyyy-MM-dd" />—<input type="text" readonly
				id="endTime" name="endTime" size="20"
				value="${endTime}"
				data-toggle="datepicker" data-pattern="yyyy-MM-dd" />	
			<button type="submit" class="btn-default" data-icon="search">查询</button>
			&nbsp;
			<div class="pull-right">
				<a href="<%=basePath%>/system/white/skipAdd.do"
					class="btn btn-green" data-icon="plus" data-toggle="dialog"
					data-id="user_add" data-title="添加白名单" data-width="600"
					data-height="300" data-id="dialog-mask" data-mask="true">添加白名单</a>
			</div>
		</div>
	</form> 
</div>
<div class="bjui-pageContent tableContent">
	<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
		<thead>
			<tr>
				<th align="center">ip</th>
				<th align="center">录入人</th>
				<th align="center">登录人</th>
				<th align="center">更新时间</th>
				<th align="center">操作</th>
			</tr>
		</thead>
		<tbody>
			 <c:forEach items="${page.result}" var="user" varStatus="status">
				<tr data-id="${user.id}">
					<td align="center">${user.ip}</td>
					<td align="center">${user.real_name}</td>
					<td align="center">${user.realName}</td>
					<td align="center"><fmt:formatDate value='${user.update_time}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
					<td align="center">
						<a href="<%=basePath%>/system/white/updateSk.do?ip=${user.ip}"
							class="btn btn-green" data-toggle="dialog" data-id="user_edit"
							data-title="编辑" data-width="600"
							data-height="300" data-id="dialog-mask" data-mask="true">编辑</a>
						<a href="<%=basePath%>/system/white/delete.do?ip=${user.ip}"
							class="btn btn-red" data-toggle="doajax"
							data-confirm-msg="确定要删除该行信息吗？">删除</a>
						</td> 
				</tr>
			</c:forEach> 
		</tbody>
	</table>
</div> 
   <div class="bjui-pageFooter">
	<div class="pages">
		<span>共${page.totalCount}条</span>
	</div>
	<div class="pagination-box" data-toggle="pagination"
		data-total="${page.totalCount}" data-page-size="${page.pageSize}"
		data-page-current="${page.pageNo}"></div>
</div> 