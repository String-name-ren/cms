<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<div class="bjui-pageHeader">
	<div class="bjui-searchBar">
		<a href="<%=basePath%>/menu/getMenuAddPage.do?parentId=${parentId}"
			class="btn btn-green" data-icon="plus" data-toggle="dialog"
			data-id="menu_add" data-title="添加栏目" data-width="700"
			data-height="400" data-id="dialog-mask" data-mask="true">添加栏目</a>
	</div>
</div>
<table  data-width="100%" id="doc-datagrid-table4" data-nowrap="true" data-toggle="tablefixed">
  <thead>
		<tr>
			<th align="center">栏目id</th>
			<th align="center">栏目名称</th>
			<th align="center">父栏目</th>
			<th align="center" width="50">标题</th>
			<th align="center" width="50">关键字</th>
			<th align="center">栏目描述</th>
			<th align="center">文件保存目录</th>
			<th align="center" width="85">操作</th>
		</tr>
	</thead>
	<tbody>
		 <c:forEach items="${menuList}" var="menu" varStatus="status">
			<tr data-id="${menu.id}">
			 <td align="center">${menu.id}</td>
			 <td align="center"><a target="_blank" href="${serverProtocol}${serverIp}:${serverPort}/${menu.directory}">${menu.name}</a></td>
				<td align="center"><c:if test="${menu.parentId != 0}">${menu.parent.name}</c:if></td>
				<td align="center">${menu.title}</td>
				<td align="center">${menu.keywords}</td>
				<td>${menu.description}</td>
				<td>${menu.directory}</td>
			 	<td><a
					href="<%=basePath%>/menu/getMenuEditPage.do?id=${menu.id}&pId=${menu.parentId}"
					class="btn btn-green" data-toggle="dialog" data-id="menu_edit"
					data-title="编辑栏目（${menu.name}）" data-width="700"
					data-height="400" data-id="dialog-mask" data-mask="true">编辑</a> <a
					href="<%=basePath%>/menu/delete.do?id=${menu.id}"
					class="btn btn-red" data-toggle="doajax"
					data-confirm-msg="确定要删除该行信息吗？">删除</a>
			 	</td>
			</tr>
		</c:forEach>
	</tbody>
</table>