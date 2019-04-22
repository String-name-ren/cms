<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<div class="bjui-pageContent">
	<form action="<%=basePath%>/system/white/update.do" class="pageForm"
		data-toggle="validate">
		<input type="hidden" class="departmentid" name="department.id"/>
		<input type="hidden"  name="ipCode" id="ipCode" value="${ip}"/>
		<table class="table table-condensed table-hover">
			<tbody>
				<tr>
					<td class="text-right"><label class="control-label x90">Ip地址：</label></td>
					<td class="text-left"><input type="text" name="ip" id="ip" value="${ip}"
						size="20" /></td>
				</tr>
				 <tr>
					<td class="text-right"><label class="control-label x90">登录人：</label></td>
					<td class="text-left"><input type="hidden" name="userId" id="user_id"
						value="${user.userId}"><input type="text" name="userName" id="user_name"
						value="${user.userName}" readonly size="15" data-toggle="lookup"
						data-title="选择登录人"
						data-url="<%=basePath%>/system/white/requertUser.do"
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
