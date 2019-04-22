<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<div class="bjui-pageContent">
	<form id="menuEditForm" action="<%=basePath%>/menu/update.do" class="pageForm"
		data-toggle="validate">
		<input type="hidden" id="id" name="id" value="${menu.id}" />
		<table class="table table-condensed table-hover">
			<tbody>
				<tr>
					<td class="text-right"><label class="control-label x90">父栏目：</label></td>
					<td class="text-left">${parentName}<input type="hidden" id="parentId"
						name="parentId" value="${menu.parentId}"></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">栏目名称：</label></td>
					<td class="text-left"><input  type="text" name="name" data-rule="required length[~64]"
						id="name" value="${menu.name}" class="form-control" size="20" /></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">标题：</label></td>
					<td class="text-left"><input type="text" name="title"
						id="title" value="${menu.title}" class="form-control"
                        data-rule="required length[~64]" size="20" /></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x85">关键字：</label></td>
						<td class="text-left"><input type="text" name="keywords"
						id="keywords" value="${menu.keywords}" class="form-control"
                        data-rule="required length[~64]" size="20" /></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">描述：</label></td>
					<td class="text-left"><input type="text" name="description" class="form-control"
						id="description" value="${menu.description}" data-rule="required"
						size="20"></td>
				</tr>
				<tr>
					<td class="text-right"><label class="control-label x90">文件保存目录：</label></td>
					<td class="text-left"><input onkeyup="value=value.replace(/[\W]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[^\d]/g,''))" type="text" name="directory" class="form-control"
						id="directory" value="${menu.directory}" data-rule="required" size="20" /></td>
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
