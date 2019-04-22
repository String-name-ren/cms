<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>


<div class="bjui-pageContent">
	<form action="<%=basePath%>/system/user/updateUser.do" class="pageForm"
		data-toggle="validate">
		<!-- 获取下拉列表选中的角色id -->
		<input type="hidden" class="roleId" name="roles.id"/>
		<!-- 获取下拉列表选中的部门id -->
		<input type="hidden" class="departmentid" name="department.id"/>
		<input type="hidden" name="id" value="${user.id}" />
		<table class="table table-condensed table-hover">
			<tbody>
				<tr>
					<td class="text-right"><label class="control-label x90">登录用户名：</label></td>
					<td class="text-left"><input type="text" name="loginName"
						id="loginName" value="${user.loginName}" readonly size="20" /></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">姓名：</label></td>
					<td class="text-left"><input type="text" name="realName"
						id="realName" value="${user.realName}"
						data-rule="required length[~64]" size="20" /></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">邮箱：</label></td>
					<td class="text-left"><input type="text" name="email"
						id="email" value="${user.email}" data-rule="length[~255] email"
						size="20"></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">性别：</label></td>
					<td class="text-left"><input type="radio" id="user_sex_man"
						name="sex" ${user.sex eq 1 ? 'checked' : ''} data-toggle="icheck"
						value="1" data-label="男" />&nbsp;&nbsp;<input type="radio"
						id="user_sex_woman" name="sex" ${user.sex eq 2 ? 'checked' : ''}
						data-toggle="icheck" value="2" data-label="女" /></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">手机号：</label></td>
					<td class="text-left"><input type="text" name="phone"
						id="phone" value="${user.phone}" data-rule="mobile" size="20" /></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">状态：</label></td>
					<td class="text-left"><input type="radio" id="user_status_1"
						name="status" ${user.status eq 1 ? 'checked' : ''}
						data-toggle="icheck" value="1" data-label="启用">&nbsp;&nbsp;<input
						type="radio" id="user_status_2" name="status"
						${user.status eq "0" ? 'checked' : ''} data-toggle="icheck"
						value="0" data-label="禁用"></td>
				</tr>
			  
			   <tr>
					<td class="text-right"><label class="control-label x90">所属角色：</label></td>
					<td class="text-left"><input type="hidden" name="role.id" id="role_id"
						value="${roleId}"><input type="text" name="role.roleName" id="role_name"
						value="${roleName}" readonly size="15" data-toggle="lookup"
						data-title="选择角色"
						data-url="<%=basePath%>/system/role/getRoleSelectPage.do"
						data-rule="required" data-group="role" data-width="600"
						data-height="300"></td>
				</tr>
			</tbody>
		</table>
	</form>
</div>
<div class="bjui-pageFooter">
	<ul>
		<li><button type="button" class="btn-close">关闭</button></li>
		<li><button type="submit" class="btn-default">保存</button></li>
	</ul>
</div>
