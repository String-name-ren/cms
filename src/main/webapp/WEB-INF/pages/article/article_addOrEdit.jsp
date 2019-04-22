<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ include file="../common.jsp" %>
<%--<link rel="stylesheet" href="<%=basePath%>/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/css/zTreeStyle/demo.css" type="text/css">--%>
<script type="text/javascript">
    function submitArticleContentForm() {
        //校验栏目
        var menuIdValue = $("#menuId").val();
        if ("undefined" == typeof (menuIdValue) || null == menuIdValue || "" == menuIdValue || menuIdValue.size <= 0){
            BJUI.alertmsg("error","请选择栏目！");
            return;
        }
        //验证定时发布时  时间应大于当前时间
        var type = $('#publishType option:selected').val();
        if(type == 3){
            var start = new Date($("#deployTime").val());
            var deployTime = start.getTime();
            var now =(new Date()).getTime();
            if(deployTime < now){
                BJUI.alertmsg("error","定时发布时间不能晚于当前时间！");
                return;
            }
        }
        var form = $("#article_form");
        form.submit();
    }
    function menuChange(event, treeId, treeNode){
        $("#menuNameCn").val(treeNode.name);
        $("#menuName").val(treeNode.menuName);
        $("#menuId").val(treeNode.id);
    }
</script>
<div class="bjui-pageContent">
    <form id="article_form"
          action="<%=basePath%>/article/saveOrUpdate.do"
          class="pageForm" data-toggle="validate">
        <input type="hidden" name="id" value="${article.id}"/>
        <legend>基本信息</legend>
        <table class="table table-condensed table-hover" id="productContent">
            <tbody>
            <tr>
                <td class="text-right" width="50px"><label class="x85">标题:</label></td>
                <td class="text-left">
                    <input type="text" id="title" name="title" style="width: 800px" class="form-control" data-rule="required" value="${article.title}"/>
                </td>
            </tr>
            <tr>
                <td class="text-right"><label class="row-label">内容:</label></td>
                <td>
                    <textarea data-rule="required" name="content" cols="80" rows="20" data-upload-json="<%=basePath%>/system/upload/uploadImage.do" data-min-height="300" weight="1000" data-toggle="kindeditor"
                              data-items="'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
        'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
        'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
        'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
        'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
        'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image',
        'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
        'anchor'">
                        ${article.content}
                    </textarea>
                </td>
            </tr>
            <tr>
                <td class="text-right"><label class="row-label">作者:</label></td>
                <td class="text-left">
                    <input type="text" name="author" value="${article.author}" class="form-control"/>
                </td>
            </tr>
            <tr>
                <td class="text-right"><label class="row-label">栏目:</label></td>
                <td>
                    <input id="menuName" type="hidden" name="menuName" readonly value="${article.menuName}" style="width:120px;" class="form-control" />
                    <input id="menuId" type="hidden" name="menuId" readonly value="${article.menuId}" style="width:120px;" class="form-control" />
                    <input readonly="readonly" id="menuNameCn" type="text" value="${article.menuNameCn}" name="menuNameCn" data-toggle="selectztree" data-tree="#j_select_tree1">
                    <span style="display:none">
                       <ul data-on-check="menuChange" data-check-enable="true" data-chk-style="radio" id="j_select_tree1" class="ztree" data-toggle="ztree" data-nodes='${treeJsonStr}'></ul>
                    </span>
                </td>
            </tr>

            <tr>
                <td class="text-right"><label class="row-label">摘要:</label></td>
                <td class="text-left">
                    <input type="text" id="abstracts" name="abstracts" value="${article.abstracts}" class="form-control"/>
                </td>
            </tr>
            <tr>
                <td class="text-right"><label class="row-label">来源:</label></td>
                <td class="text-left">
                    <input type="text" name="source" value="水象分期" class="form-control"/>
                </td>
            </tr>
            <tr>
                <td class="text-right"><label class="row-label">关键字:</label></td>
                <td class="text-left">
                    <input type="text" name="keyWord" value="${article.keyWord}" class="form-control" placeholder="多个关键字请用','隔开"/>
                </td>
            </tr>
            <tr>
                <td class="text-right"><label class="row-label">发布类型:</label></td>
                <td class="text-left">
<%--
                    <input type="hidden" name="publishType" id="publishType" value="${article.publishType}" class="form-control"/>
--%>
                    <select id="publishType" size='50' name="publishType" onchange="changePublishType()" data-toggle="selectpicker">
                        <option value="1" ${article.publishType eq 1 ?'selected':''}>手动发布</option>
                        <option value="2" ${article.publishType eq 2 ?'selected':''}>自动发布</option>
                        <option value="3" ${article.publishType eq 3 ?'selected':''}>定时发布</option>
                    </select>
                    <span id="timingTime" style="display:none">
                        <input data-toggle="datepicker" data-pattern="yyyy-MM-dd HH:mm:ss" type="text" id="deployTime" value="<fmt:formatDate value='${article.deployTime}' pattern='yyyy-MM-dd HH:mm:ss'/>" name="deployTime" />
                    </span>
                </td>
            </tr>
            <tr>
                <td class="text-right"><label class="row-label">seo标题:</label></td>
                <td class="text-left">
                    <input type="text" name="seoTitle" value="${article.seoTitle}" class="form-control"/>
                </td>
            </tr>
            <tr>
                <td class="text-right"><label class="row-label">seo描述:</label></td>
                <td class="text-left">
                    <input type="text" name="seoDescription" value="${article.seoDescription}"/>
                </td>
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
            <button type="button" class="btn-default" onclick="submitArticleContentForm();">保存</button>
        </li>
    </ul>
</div>

<script>
    function changePublishType(){
        var type = $('#publishType option:selected').val();
        if (type == 3){
            $("#timingTime").show();
        } else {
            $("#timingTime").hide();
        }
    }
   //定时发布时  发布时间回显赋值
    var publishType = "${article.publishType}";
    if ("undefined" !=typeof (publishType) && null != publishType && '' != publishType){
        if (publishType == 3){
            $("#timingTime").show();
        }
    }else{
        //默认值 1手动发布
        $("#publishType").val("2");
    }
</script>