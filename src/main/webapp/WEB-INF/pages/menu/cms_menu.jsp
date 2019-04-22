<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<script type="text/javascript">
	$(function() {
		var parentId=parent.$(".parentId").val();
		if( "undefined" == typeof(parentId) || "" == parentId){
			parentId=0;
		}
		$("#menu_list").bjuiajax('doLoad', {
			url : basePath + "/menu/findMenuListByParentId.do?parentId="+parentId,
			target : "#menu_list"
		});
	});
	function layout(event, treeId, treeNode) {
		parent.$(".parentId").val(treeNode.id);
		$(event.target).bjuiajax('doLoad', {
			url : treeNode.url,
			target : treeNode.divid
		});
		event.preventDefault();
	}
</script>
<div class="bjui-pageContent">
	<div id="menuTreeDiv" style="float: left; width: 230px; height: 99.9%;">
		<fieldset style="height: 100%;">
			<legend>栏目树</legend>
			<div style="height: 96%; overflow: auto;">
				<ul id="menuTree" class="ztree" data-toggle="ztree"
					data-expand-all="true" data-on-click="layout">
					<li data-id="0" data-faicon="reply"
						data-url="<%=basePath%>/menu/findMenuListByParentId.do?parentId=0"
						data-divid="#menu_list">返回栏目列表首页</li>
					<c:forEach items="${treeList}" var="t" varStatus="s">
						<li data-id="${t.id}" data-pid="${t.parentId}"
							data-url="<%=basePath%>/menu/findMenuListByParentId.do?parentId=${t.id}"
							data-divid="#menu_list" >${t.name}</li>
					</c:forEach>
				</ul>
			</div>
		</fieldset>
	</div>
	<div style="margin-left: 240px; height: 99.9%; overflow: hidden;">
		<div style="height: 100%; overflow: hidden;">
			<fieldset style="height: 100%;">
				<legend>栏目列表</legend>
				<div id="menu_list" style="height: 96%; overflow: hidden;"></div>
			</fieldset>
		</div>
	</div>
</div>
