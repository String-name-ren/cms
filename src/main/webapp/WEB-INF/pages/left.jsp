<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String base_path = request.getContextPath();
%>
<div id="bjui-hnav">
	<div id="bjui-hnav-navbar-box">
		<ul id="bjui-hnav-navbar" style="display: none">
			<li class="active"><a href="javascript:;" data-toggle="slidebar"><i
					class="fa fa-check-square-o"></i> 菜单</a>
				<div class="items" data-noinit="true">
					
					 <c:forEach items="${rules}" var="fr">
						<ul class="menu-items" data-faicon="${fr.icon}"
							data-tit="${fr.name}">
							<c:forEach items="${fr.children}" var="r">
								<li><a href="<%=base_path%>/${r.url}"
									data-options="{id:'${r.tabid}', faicon:'${r.icon}',fresh:'true'}">${r.name}</a></li>
							</c:forEach>
						</ul>
					</c:forEach> 
				
			</div></li>
		</ul>
	</div>
</div>