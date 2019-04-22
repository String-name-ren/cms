<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ include file="../common.jsp" %>
<div class="bjui-pageContent">
    <form id="globalField_form" action="<%=basePath%>/global/field/update" class="pageForm" data-toggle="validate">
        <input type="hidden" name="id" value="${globalField.id}"/>
        <table class="table table-condensed table-hover" id="productContent">
            <tbody>
                <tr>
                    <td class="text-right"><label class="row-label">标题:</label></td>
                    <td class="text-left">
                        <input type="text" id="title" name="title" class="form-control" value="${globalField.title}"/>
                    </td>
                </tr>
                <tr>
                    <td class="text-right"><label class="row-label">关键字:</label></td>
                    <td class="text-left">
                        <input type="text" name="keyword" value="${globalField.keyword}" class="form-control"/>
                    </td>
                </tr>
                <tr>
                    <td class="text-right"><label class="row-label">描述:</label></td>
                    <td class="text-left">
                        <input type="text" id="description" name="description" value="${globalField.description}" class="form-control"/>
                    </td>
                </tr>
                <tr>
                    <td class="text-right"><label class="row-label"></label></td>
                    <td class="text-left">
                        <button type="submit" class="btn-default">保存</button>
                    </td>
                </tr>
            </tbody>
        </table>
    </form>
</div>