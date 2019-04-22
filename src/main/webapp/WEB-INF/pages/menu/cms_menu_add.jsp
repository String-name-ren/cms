<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<div class="bjui-pageContent">
	<form action="<%=basePath%>/menu/save.do" class="pageForm"
		data-toggle="validate">
		<table class="table table-condensed table-hover">
			<tbody>
				<tr>
					<td class="text-right"><label class="control-label x90">父栏目：</label></td>
					<td class="text-left">${parentName}<input type="hidden" id="parentId"
						name="parentId" value="${parentId}"></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">栏目名称：</label></td>
					<td class="text-left"><input  type="text" name="name"
						id="name" value="" class="form-control" data-rule="required length[~64]" size="20" /></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x85">标题：</label></td>
					<td class="text-left"><input type="text" name="title" id="title" class="form-control" value="" size="20" /></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x85">关键字：</label></td>
					<td class="text-left"><input type="text" name="keywords" id="keywords" class="form-control" value="" size="20" /></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">描述：</label></td>
					<td class="text-left"><textarea name="description" id="description" class="form-control"
							data-toggle="autoheight" cols="30" rows="3"></textarea></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">文件保存目录：</label></td>
					<td class="text-left"><input onkeyup="value=value.replace(/[\W]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" type="text" name="directory"
					id="directory" class="form-control" value="" data-rule="required" size="20"></td>
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
