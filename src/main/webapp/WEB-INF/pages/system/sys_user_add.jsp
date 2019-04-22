<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../common.jsp" %>
<script type="text/javascript">

</script>
<div class="bjui-pageContent">
    <form id="addUserForm" action="<%=basePath%>/system/user/saveUser.do" class="pageForm"
          data-toggle="validate">
        <input type="hidden" class="departmentid" name="department.id"/>
        <table class="table table-condensed table-hover">
            <tbody>
            <tr>
                <td class="text-right"><label class="control-label x90">登录用户名：</label></td>
                <td class="text-left"><input type="text" name="loginName"
                                             id="loginName" value=""
                                             data-rule="required length[~64] remote[<%=basePath%>/system/user/validateUserLoginName.do]"
                                             size="20"/></td>
            </tr>
            <tr>
                <td class="text-right"><label class="control-label x90">姓名：</label></td>
                <td class="text-left"><input type="text" name="realName"
                                             id="realName" value="" data-rule="required length[~64]" size="20"/></td>
            </tr>
            <tr>
                <td class="text-right"><label class="control-label x90">邮箱：</label></td>
                <td class="text-left"><input type="text" name="email"
                                             id="email" value="" data-rule="required length[~255] email" size="20"></td>
            </tr>
            <tr>
                <td class="text-right"><label class="control-label x90">性别：</label></td>
                <td class="text-left"><input type="radio" id="user_sex_man"
                                             name="sex" checked data-toggle="icheck" value="1" data-label="男"/>&nbsp;&nbsp;<input
                        type="radio" id="user_sex_woman" name="sex" data-toggle="icheck"
                        value="2" data-label="女"/></td>
            </tr>
            <tr>
                <td class="text-right"><label class="control-label x90">手机号：</label></td>
                <td class="text-left"><input type="text" name="phone"
                                             id="phone" value="" data-rule="mobile" size="20"
                                             data-rule="required length[~64] mobile"/></td>
            </tr>
            <tr>
                <td class="text-right"><label class="control-label x90">状态：</label></td>
                <td class="text-left"><input type="radio" id="user_status_1"
                                             name="status" checked data-toggle="icheck" value="1"
                                             data-label="启用">&nbsp;&nbsp;<input type="radio"
                                                                                id="user_status_2" name="status"
                                                                                data-toggle="icheck" value="0"
                                                                                data-label="禁用"></td>
            </tr>
            <tr>
                <td class="text-right"><label class="control-label x90">所属角色：</label></td>
                <td class="text-left"><input type="hidden" name="role.id" id="role_id"
                                             value="${roleId}"><input type="text" name="role.roleName" id="role_name"
                                                                      value="${roleName}" readonly size="15"
                                                                      data-toggle="lookup"
                                                                      data-title="选择角色"
                                                                      data-url="<%=basePath%>/system/role/getRoleSelectPage.do"
                                                                      data-rule="required" data-group="role"
                                                                      data-width="600"
                                                                      data-height="300"></td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li>
            <button type="button" class="btn-close">关闭</button>
        </li>
        <li>
            <button type="submit" class="btn-default">保存</button>
        </li>
    </ul>
</div>
