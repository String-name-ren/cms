<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<script type="text/javascript">
var userId='',userName='';
function save_role(){
	userId='';
	userName='';
	$(".checked").find($('input[type="checkbox"]')).each(function(){
		userId+=$(this).attr("userId")+",";
		userName+=$(this).attr("userName")+",";
	});
	$("#user_name").val(userName.substring(0, userName.length-1));
	$("#user_id").val(userId.substring(0, userId.length-1));
	$("#close").click();
}

</script>
<div class="bjui-pageContent tableContent">
	<table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
		<thead>
			<tr>
				<th width="100" align="center">登录名</th>
				<th width="100" align="center">用户名</th>
				<th width="85" align="center">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.result}" var="user" varStatus="status">
				<tr data-id="${user.id}" class="userId_${user.id}">
					<td align="center">${user.loginName}</td>
					<td align="center">${user.realName}</td>
					<td align="center"><input type="checkbox"  id="role" userId="${user.id}" userName="${user.realName}" data-toggle="icheck" data-label=""></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
<div class="bjui-pageFooter">
	<ul>
		<li><button type="button" class="btn-close" id="close">关闭</button></li>
		<li><button type="button" class="btn-default" onclick="save_role();">保存</button></li>
	</ul>
</div>
<script type="text/javascript">
	var userId=$("#user_id").val();
	var user=userId.split(",");
	for (var i = 0; i < user.length; i++) {
		$("input[type='checkbox']").each(function(){
			if($(this).attr("userid")==user[i]){
				$(this).attr("checked","checked");
			}
		})
	}
</script>