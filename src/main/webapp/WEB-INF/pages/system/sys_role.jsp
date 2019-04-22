<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch"
		action="<%=basePath%>/system/role/findsByPage.do"
		method="post">
		<input type="hidden" name="pageSize" value="${page.pageSize}">
		<input type="hidden" name="pageCurrent" value="${page.pageNo}">
		<div class="bjui-searchBar">
			 <c:if test="${rule:contains(ruleCodes,'tjjs')}">
				<a href="<%=basePath%>/system/role/toAdd.do"
					class="btn btn-green" data-icon="plus" data-toggle="dialog"
					data-id="role_add" data-title="添加角色" data-width="800"
					data-height="600" data-id="dialog-mask" data-mask="true">添加角色</a>
			 </c:if> 
		</div>
	</form>
</div>
<div class="bjui-pageContent tableContent">
	<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
		<thead>
			<tr>
				<th width="100" align="center">角色编号</th>
				<th width="100" align="center">角色名称</th>
				<th align="center">角色描述</th>
				<th width="85" align="center">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.result}" var="role" varStatus="status">
				<tr data-id="${role.id}">
					<td align="center">${role.roleCode}</td>
					<td align="center">${role.roleName}</td>
					<td>${role.roleDesc}</td>
					<td align="center">
						 <c:if test="${rule:contains(ruleCodes,'bjjs')}"> 
						<a href="<%=basePath%>/system/role/toUpdate.do?id=${role.id}"
								class="btn btn-green" data-toggle="dialog" data-id="role_edit"
								data-title="编辑角色（${role.roleName}）" data-width="800"
								data-height="600" data-id="dialog-mask" data-mask="true">编辑</a>
						 </c:if> 
						 <c:if test="${rule:contains(ruleCodes,'scjs')}"> 
						<a href="<%=basePath%>/system/role/delete.do?id=${role.id}"
								class="btn btn-red" data-toggle="doajax"
								data-confirm-msg="确定要删除该行信息吗？">删除</a>
						 </c:if> 
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div class="bjui-pageFooter">
	<div class="pages">
		<span>每页&nbsp;</span>
		<div class="selectPagesize">
			<select data-toggle="selectpicker"
				data-toggle-change="changepagesize">
				<c:forEach begin="30" end="120" step="30" varStatus="s">
					<option value="${s.index}"
						${page.pageSize eq s.index ? 'selected="selected"' : ''}>${s.index}</option>
				</c:forEach>
			</select>
		</div>
		<span>&nbsp;条，共${page.totalCount}条</span>
	</div>
	<div class="pagination-box" data-toggle="pagination"
		data-total="${page.totalCount}" data-page-size="${page.pageSize}"
		data-page-current="${page.pageNo}"></div>
</div>