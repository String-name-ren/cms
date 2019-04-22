<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<script type="text/javascript">
var roleId='',roleName='';
function save_role(){
	roleId='';
	roleName='';
	$(".checked").find($('input[type="checkbox"]')).each(function(){
		roleId+=$(this).attr("roleId")+",";
		roleName+=$(this).attr("roleName")+",";
	});
	console.log(roleId)
	$("#role_name").val(roleName.substring(0, roleName.length-1));
	$("#role_id").val(roleId.substring(0, roleId.length-1));
	$("#close").click();
}

</script>
<div class="bjui-pageHeader">
	<form id="pagerForm" data-toggle="ajaxsearch"
		action="<%=basePath%>/system/role/getRoleSelectPage.do" method="post">
		<input type="hidden" name="pageSize" value="${page.pageSize}">
		<input type="hidden" name="pageCurrent" value="${page.pageNo}">
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
				<tr data-id="${role.id}" class="roleId_${role.id}">
					<td align="center">${role.roleCode}</td>
					<td align="center">${role.roleName}</td>
					<td align="center">${role.roleDesc}</td>
					<td align="center"><input type="checkbox"  id="role" roleId="${role.id}" roleName="${role.roleName}" data-toggle="icheck" data-label=""></td>
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
	var roleId=$("#role_id").val();
	var role=roleId.split(",");
	for (var i = 0; i < role.length; i++) {
		$("input[type='checkbox']").each(function(){
			if($(this).attr("roleid")==role[i]){
				$(this).attr("checked","checked");
			}
		})
	}
</script>