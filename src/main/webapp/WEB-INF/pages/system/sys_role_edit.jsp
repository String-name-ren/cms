<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<!-- <script type="text/javascript">
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
		$("#roleEditForm").submit();
	} 
</script> -->

<script type="text/javascript">
	function submitEditForm() {
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
	}
</script>
<div class="bjui-pageContent">
	<form id="roleEditForm" action="<%=basePath%>/system/role/update.do"
		class="pageForm" data-toggle="validate">
		<div
			style="float: left; width: 430px; height: 99.9%; overflow: hidden;">
			<div class="form-group">
				<label class="control-label x90">角色代码：</label> <input type="text"
					name="roleCode" id="roleCode" value="${role.roleCode}" readonly 
					size="20" />
			</div>
			<div class="form-group">
				<label class="control-label x90">角色名称：</label> <input type="text"
					name="roleName" id="roleName" value="${role.roleName}"
					data-rule="required length[~64]" size="20" />
			</div>
			<div class="form-group">
				<label class="control-label x90">角色描述：</label>
				<textarea name="roleDesc" id="roleDesc" data-rule="length[~512]"
					data-toggle="autoheight" cols="30" rows="3">${role.roleDesc}</textarea>
			</div>
			<input type="hidden" id="ruleIds" name="ruleIds"/>
			<input type="hidden" id="roleId" name="roleId" value="${role.id}"/>
		</div>
		
		 <div id="ruleSelectTreeDiv" style="margin-left: 440px; height: 99.9%;">
			<fieldset style="height: 500px;">
				<legend>选择权限</legend>
				<div style="height: 96%; overflow: auto;">
					<ul id="ruleSelectTree" class="ztree" data-toggle="ztree"
						data-expand-all="true" data-check-enable="true">
						<c:forEach items="${treeList}" var="t" varStatus="s">
							<li data-id="${t.id}" data-pid="${t.parentId}"
								data-checked="${t.checked}" data-faicon="${t.imgPath}">${t.name}</li>
						</c:forEach>
					</ul>
				</div>
			</fieldset>
		</div> 
	</form>
</div>
<div class="bjui-pageFooter">
	<ul>
		<li><button type="button" class="btn-close">关闭</button></li>
		<li><button type="button" class="btn-default"
				onclick="submitEditForm();">保存</button></li>
	</ul>
</div>
