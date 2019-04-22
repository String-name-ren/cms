<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<script type="text/javascript">
function delete_(id){
	$("#contract").bjuiajax("doAjax", {
		url : "<%=basePath%>/system/manage/delete.do?delids="+id,
		type : "post",
		callback : function(result){
			if(result.statusCode=='200'){
				$(".org_home").submit();
			}else{
				BJUI.alertmsg('warn', result.message);
			}
		},
		loadingmask : true
	});
}
</script>
<div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch" class="org_home"
		action="<%=basePath%>/system/manage/institutionSelect.do"
		method="post">
		<input type="hidden" name="id" value="${id}">
		<input type="hidden" name="pid" value="${pid}">
		<input type="hidden" name="pageSize" value="${page.pageSize}">
		 <input type="hidden" name="pageCurrent" value="${page.pageNo}">   
		<div class="bjui-searchBar">
			 机构名称：<input type="text" class="orgName" id="orgName" name="orgName" value="${orgName}"/> 
			<button type="submit" class="btn-default" data-icon="search">查询</button>
			&nbsp;
			<div class="pull-right">
				<c:if test="${rule:contains(ruleCodes,'tjjg')}"> 
				<a href="<%=basePath%>/system/manage/addition.do?&id=${id}"
					class="btn btn-green" data-icon="plus" data-toggle="dialog"
					data-id="user_add" data-title="添加机构" data-width="400"
					data-height="150" data-id="dialog-mask" data-mask="true">添加机构</a>
					</c:if> 
			</div>
		</div>
	</form>
</div>
<div class="bjui-pageContent tableContent">
	<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
		<thead>
			<tr>
				<th width="26" align="center"><input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
				<th>机构名称</th>
				<th>机构编码</th>
				<th >机构描述</th>
				<th >操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.result}" var="orgS" varStatus="status">
				<tr data-id="${orgS.id}">
					<td align="center"><input type="checkbox" name="ids"
						data-toggle="icheck" value="${orgS.id}"></td>
					<td>${orgS.orgName}</td>
					<td>${orgS.orgCode}</td>
					<td>${orgS.orgDesc}</td>
					<td>
						<c:if test="${rule:contains(ruleCodes,'bjjg')}">	
							<a href="<%=basePath%>/system/manage/update.do?id=${orgS.id}&pid=${id}"
								class="btn btn-green" data-toggle="dialog" data-id="user_edit"
								data-title="编辑机构（）" data-width="600"
								data-height="300" data-id="dialog-mask" data-mask="true">编辑</a>
						</c:if>	
						<c:if test="${rule:contains(ruleCodes,'scjg')}">
							<a id="contract" class="btn btn-red"
									data-confirm-msg="确定要删除该行信息吗？" onclick="delete_(${orgS.id});">删除</a>
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
