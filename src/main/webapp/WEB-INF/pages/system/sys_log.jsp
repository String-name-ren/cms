<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch"
		action="<%=basePath%>/system/manage/queryLogin.do"
		method="post">
		操作人：<input type="text" class="operator" id="operator" value='${log.operator}' name="operator" >
		<%-- 操作描述：<input type="text" class="description" id="description" value='${log.description}' name="description"> --%>
		<%-- 	操作时间:
				<input type="text" id="operateTime"
				value='${operateTime}'
				name="operateTime" size="20" data-toggle="datepicker"
				data-pattern="yyyy-MM-dd" /> --%>
			
			 <label>操作时间：</label>
			 	<input type="text" id="startTime"
				value="${startTime}"
				name="startTime" size="20" data-toggle="datepicker"
				data-pattern="yyyy-MM-dd" />—<input type="text"
				id="endTime" name="endTime" size="20"
				value="${endTime}"
				data-toggle="datepicker" data-pattern="yyyy-MM-dd" />
		
		<button type="submit" class="btn-default" data-icon="search">查询</button>
	</form>
</div>
<div class="bjui-pageContent tableContent">
	<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
		 <thead data-id="1">
			<tr>
				<td>操作人</td>
				<td>操作时间</td>
				<td>操作描述</td>
				<td>操作结果</td>
				<td>操作IP</td>
			</tr>
		</thead>
		<tbody>
			 <c:forEach items="${page.result}" var="logs" varStatus="status">
				<tr data-id="${logs.id}">
					<td>${logs.operator}</td>
					<td><fmt:formatDate value='${logs.operateTime}' pattern='yyyy-MM-dd HH:mm:ss' /></td>
					<td>${logs.description}</td>
					<td>${logs.operateResult}</td>
					<td>${logs.ip}</td>
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
