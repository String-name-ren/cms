<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
 <div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch"
		action="<%=basePath%>/system/user/findUserByCriteriaAndPage.do"
		method="post"> 
		 <input type="hidden" name="pageSize" value="${page.pageSize}">
		 <input type="hidden" name="pageCurrent" value="${page.pageNo}">  
		 <input type="hidden" name="status" class="userStatus">
		<div class="bjui-searchBar">

			 <label>姓名：</label><input type="text" value="${u.realName}"
				name="realName" class="form-control" size="8">&nbsp; <label>状态:</label>

			<select onchange="$('.userStatus').val(this.options[this.selectedIndex].value);">
				<option value="">全部</option>
				<option value="0">禁用</option>
				<option value="1">启用</option>
			</select>&nbsp;
			<button type="button" class="showMoreSearch" data-toggle="moresearch"
				data-name="custom2">
				<i class="fa fa-angle-double-down"></i>
			</button>
			<button type="submit" class="btn-default" data-icon="search">查询</button>
			&nbsp; <a class="btn btn-orange" href="javascript:;"
				onclick="$(this).navtab('reloadForm', true);" data-icon="undo">清空查询</a>
			<div class="pull-right">
					<c:if test="${rule:contains(ruleCodes,'tjyh')}">  
						<a href="<%=basePath%>/system/user/getUserAddPage.do"
							class="btn btn-green" data-icon="plus" data-toggle="dialog"
							data-id="user_add" data-title="添加用户" data-width="600"
							data-height="400" data-id="dialog-mask" data-mask="true">添加用户</a>
					</c:if> 
					<%--<div class="btn-group">
						<button type="button" class="btn-default dropdown-toggle"
							data-toggle="dropdown" data-icon="copy">
							批量操作<span class="caret"></span>
						</button>
						<ul class="dropdown-menu right" role="menu">
							<c:if test="${rule:contains(ruleCodes,'yhplsc')}">
								<li><a href="<%=basePath%>/system/user/bulkDeleteUser.do"
									data-toggle="doajaxchecked" data-confirm-msg="确定要删除选中项吗？"
									data-idname="delids" data-group="ids">删除选中</a>
								</li>
							</c:if>
						</ul>
					</div>--%>
			</div>
		</div>
		<div class="bjui-moreSearch">
			 <label>创建时间：</label><input type="text" id="userBeginTime"
				value='${beginTime}'
				name="beginTime" size="20" data-toggle="datepicker"
				data-pattern="yyyy-MM-dd" />—<input type="text"
				id="userEndTime" name="endTime" size="20"
				value='${endTime}'
				data-toggle="datepicker" data-pattern="yyyy-MM-dd" />
		</div>
	
	</form> 
</div>
<div class="bjui-pageContent tableContent">
	<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
		<thead>
			<tr>
				<th width="26" align="center"><input type="checkbox"
					class="checkboxCtrl" data-group="ids" data-toggle="icheck"></th>
				<td>登录用户名</td>
				<td>姓名</td>
				<td width="30">性别</td>
				<td>联系方式</td>
				<td>邮箱</td>
				<td width="50">状态</td>
				<td>角色</td>
				<td width="130">创建时间</td>
				<td width="130">更新时间</td>
				<td width="85">操作</td>
			</tr>
		</thead>
		<tbody>
			 <c:forEach items="${page.result}" var="user" varStatus="status">
				<tr data-id="${user.id}">
					<td align="center"><input type="checkbox" name="ids"
						data-toggle="icheck" value="${user.id}"></td>
					<td>${user.loginName}</td>
					<td>${user.realName}</td>
				     <td><c:if test="${user.sex eq 1}">男</c:if> <c:if
							test="${user.sex eq 2}">女</c:if></td>
					<td>${user.phone}</td>
					 <td style="text-align: left">${user.email}</td>
					 <td><c:if test="${user.status eq 1}">启用</c:if> <c:if
							test="${user.status eq 0}">禁用</c:if>
					</td>   
					 <td>${user.password}</td>  
					<td><fmt:formatDate value='${user.createTime}'
							pattern='yyyy-MM-dd HH:mm:ss' /></td>
					<td><fmt:formatDate value='${user.updateTime}'
							pattern='yyyy-MM-dd HH:mm:ss' /></td>
					
						<td>
							 <c:if test="${rule:contains(ruleCodes,'bjyh')}"> 
								<a
									href="<%=basePath%>/system/user/getUserEditPage.do?id=${user.id}"
									class="btn btn-green" data-toggle="dialog" data-id="user_edit"
									data-title="编辑用户（${user.realName}）" data-width="600"
									data-height="400" data-id="dialog-mask" data-mask="true">编辑</a>
							</c:if>	 
							
							 <c:if test="${rule:contains(ruleCodes,'scyh')}"> 
								<a href="<%=basePath%>/system/user/deleteUser.do?id=${user.id}"
									class="btn btn-red" data-toggle="doajax"
									data-confirm-msg="确定要删除该行信息吗？">删除</a>
							 </c:if> 
						</td> 
				</tr>
			</c:forEach> 
		</tbody>
	</table>
</div> 

<!-- 分页 -->
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