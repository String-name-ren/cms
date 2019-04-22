<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<script type="text/javascript">
	/* function submitEditForm() {
		var treeObj = $.fn.zTree.getZTreeObj("ruleSelectTree");
		var nodes = treeObj.getCheckedNodes(true);
		var ruleIds = new Array();
		if (nodes.length > 0) {
			for (var i = 0; i < nodes.length; i++) {
				ruleIds[i] = nodes[i].id;
			}
		}
		$("#ruleIds").val(ruleIds);
		$("#roleEditForm").submit();
	} */
	function submitEditForm() {
		$("#ruleEditForm").submit();
	} 
</script>
<div class="bjui-pageContent">
	<form id="ruleEditForm" action="<%=basePath%>/system/rule/update.do" class="pageForm"
		data-toggle="validate">
		<input type="hidden" id="ruleId" name="ruleId" value="${rule.id}" />
		<table class="table table-condensed table-hover">
			<tbody>
				<tr>
					<td class="text-right"><label class="control-label x90">父权限：</label></td>
					<td class="text-left">${parentName}<input type="hidden" id="parentId"
						name="parentId" value="${rule.parentId}"></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">权限代码：</label></td>
					<td class="text-left"><input type="text" name="ruleCode"
						id="ruleCode" value="${rule.ruleCode}" size="20" readonly /></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">权限名称：</label></td>
					<td class="text-left"><input type="text" name="ruleName"
						id="ruleName" value="${rule.ruleName}"
						data-rule="required length[~64]" size="20" /></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x85">权限类型：</label></td>
					<td class="text-left"><select name="ruleType"
						data-toggle="selectpicker">
							<option value="1"
								${rule.ruleType eq 1 ? 'selected="selected"' : ''}>菜单</option>
							<option value="2"
								${rule.ruleType eq 2 ? 'selected="selected"' : ''}>功能</option>
					</select></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">权限排序：</label></td>
					<td class="text-left"><input type="text" name="ruleOrder"
						id="ruleOrder" value="${rule.ruleOrder}" data-rule="required range[1~127]"
						size="20"></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">权限URL：</label></td>
					<td class="text-left"><input type="text" name="ruleUrl"
						id="ruleUrl" value="${rule.ruleUrl}" data-rule="length[~255]" size="20" /></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">权限图标：</label></td>
					<td class="text-left"><input type="text" name="rulePicture"
						id="rulePicture" value="${rule.rulePicture}" data-rule="length[~255]" size="20"></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">权限扩展：</label></td>
					<td class="text-left"><input type="text" name="ruleExt"
						id="ruleExt" value="${rule.ruleExt}" data-rule="length[~64]" size="20"></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">权限描述：</label></td>
					<td class="text-left"><textarea name="ruleDesc" id="ruleDesc"
							data-rule="length[~255]" data-toggle="autoheight" cols="30"
							rows="3">${rule.ruleDesc}</textarea></td>
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
