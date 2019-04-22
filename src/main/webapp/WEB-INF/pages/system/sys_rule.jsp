<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<script type="text/javascript">
	$(function() {
		var pId=parent.$(".pId").val();
		if(pId==""){
			pId=0
		}
		$("#rule_list").bjuiajax('doLoad', {
			url : basePath + "/system/rule/findRuleListByParentId.do?pId="+pId,
			target : "#rule_list"
		});
	});
	function layout(event, treeId, treeNode) {
		parent.$(".pId").val(treeNode.id);
		$(event.target).bjuiajax('doLoad', {
			url : treeNode.url,
			target : treeNode.divid
		});
		event.preventDefault();
	}
</script>
<div class="bjui-pageContent">
	<div id="ruleTreeDiv" style="float: left; width: 230px; height: 99.9%;">
		<fieldset style="height: 100%;">
			<legend>权限树</legend>
			<div style="height: 96%; overflow: auto;">
				<ul id="ruleTree" class="ztree" data-toggle="ztree"
					data-expand-all="true" data-on-click="layout">
					<li data-id="0" data-faicon="reply"
						data-url="<%=basePath%>/system/rule/findRuleListByParentId.do?pId=0"
						data-divid="#rule_list">返回权限列表首页</li>
					<c:forEach items="${treeList}" var="t" varStatus="s">
						<li data-id="${t.id}" data-pid="${t.parentId}"
							data-faicon="${t.imgPath}"
							data-url="<%=basePath%>/system/rule/findRuleListByParentId.do?pId=${t.id}"
							data-divid="#rule_list" >${t.name}</li>
					</c:forEach>
				</ul>
			</div>
		</fieldset>
	</div>
	<div style="margin-left: 240px; height: 99.9%; overflow: hidden;">
		<div style="height: 100%; overflow: hidden;">
			<fieldset style="height: 100%;">
				<legend>权限列表</legend>
				<div id="rule_list" style="height: 96%; overflow: hidden;"></div>
			</fieldset>
		</div>
	</div>
</div>
