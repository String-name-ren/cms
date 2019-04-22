<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<div class="bjui-pageContent">
	<form action="<%=basePath%>/system/rule/save.do" class="pageForm"
		data-toggle="validate">
		<table class="table table-condensed table-hover">
			<tbody>
				<tr>
					<td class="text-right"><label class="control-label x90">父权限：</label></td>
					<td class="text-left">${parentName}<input type="hidden" id="pId"
						name="pId" value="${pId}"></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">权限代码：</label></td>
					<td class="text-left"><input type="text" name="ruleCode"
						id="ruleCode" value="" data-rule="required length[~32]" size="20" /></td>
					<%-- <td class="text-left"><input type="text" name="ruleCode"
						id="ruleCode" value="" data-rule="required length[~32] remote[<%=basePath%>/system/rule/validateRuleCode.do]" size="20" /></td> --%>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">权限名称：</label></td>
					<td class="text-left"><input type="text" name="ruleName"
						id="ruleName" value="" data-rule="required length[~64]" size="20" /></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x85">权限类型：</label></td>
					<td class="text-left"><select name="ruleType"
						data-toggle="selectpicker">
							<option value="1">菜单</option>
							<option value="2">功能</option>
					</select></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">权限排序：</label></td>
					<td class="text-left"><input type="text" name="ruleOrder"
						id="ruleOrder" value="" data-rule="required range[1~127]" size="20"></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">权限URL：</label></td>
					<td class="text-left"><input type="text" name="ruleUrl"
						id="ruleUrl" value="" data-rule="length[~255]" size="20" /></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">权限图标：</label></td>
					<td class="text-left"><input type="text" name="rulePicture"
						id="rulePicture" value="" data-rule="length[~255]" size="20"></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">权限扩展：</label></td>
					<td class="text-left"><input type="text" name="ruleExt"
						id="ruleExt" value="" data-rule="length[~64]" size="20"></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">权限描述：</label></td>
					<td class="text-left"><textarea name="ruleDesc" id="ruleDesc" data-rule="length[~255]"
							data-toggle="autoheight" cols="30" rows="3"></textarea></td>
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
