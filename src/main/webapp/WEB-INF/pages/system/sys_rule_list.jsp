<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>

<div class="bjui-pageHeader">
	<div class="bjui-searchBar">
		<a href="<%=basePath%>/system/rule/getRuleAddPage.do?pId=${pId}"
			class="btn btn-green" data-icon="plus" data-toggle="dialog"
			data-id="rule_add" data-title="添加权限" data-width="700"
			data-height="400" data-id="dialog-mask" data-mask="true">添加权限</a>
	</div>
</div>

	<table  data-width="100%" id="doc-datagrid-table4" data-nowrap="true" data-toggle="tablefixed">
	  <thead>
			<tr>
				<th align="center">权限代码</th>
				<th align="center">权限名称</th>
				<th align="center">父权限</th>
				<th align="center" width="50">权限类型</th>
				<th align="center" width="50">排序</th>
				<th align="center">权限描述</th>
				<th align="center" width="85">操作</th>
			</tr>
		</thead>
		<tbody id="doc-datagrid-table4">
			 <c:forEach items="${ruleList}" var="rule" varStatus="status">
				<tr data-id="${rule.id}">
					<td align="center">${rule.ruleCode}</td>
					<td align="center">${rule.ruleName}</td>
					<td align="center"><c:if test="${rule.parentId != 0}">${rule.parent.ruleName}</c:if></td>
					<td align="center"><c:if test="${rule.ruleType eq 1}">菜单</c:if>
						<c:if test="${rule.ruleType eq 2}">功能</c:if></td>
					<td align="center">${rule.ruleOrder}</td>
					<td>${rule.ruleDesc}</td>
					<td><a
						href="<%=basePath%>/system/rule/getRuleEditPage.do?id=${rule.id}&pId=${pId}"
						class="btn btn-green" data-toggle="dialog" data-id="rule_edit"
						data-title="编辑权限（${rule.ruleName}）" data-width="700"
						data-height="400" data-id="dialog-mask" data-mask="true">编辑</a> <a
						href="<%=basePath%>/system/rule/deleteRule.do?id=${rule.id}"
						class="btn btn-red" data-toggle="doajax"
						data-confirm-msg="确定要删除该行信息吗？">删除</a></td>
				</tr>
			</c:forEach>
		</tbody> 
	</table> 
<!-- </div> -->