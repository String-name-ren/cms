<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ include file="../common.jsp" %>
<div class="bjui-pageHeader">
    <form id="pagerForm" data-toggle="ajaxsearch"
          action="<%=basePath%>/article/findArticleList.do"
          method="post">
        <input type="hidden" name="status" class="status" value="${cond.status}">
        <input type="hidden" name="publishType" class="publishType" value="${cond.publishType}">
        <div class="bjui-searchBar">
            <label>标题：</label><input type="text" value="${cond.title}"
                                     name="title" class="form-control" size="8"
                                     onkeydown="return banInputSapce(event);" onKeyup="return inputSapceTrim(event,this);">&nbsp;

            <label>作者：</label><input type="text" value="${cond.author}"
                                     name="author" class="form-control" size="8"
                                     onkeydown="return banInputSapce(event);" onKeyup="return inputSapceTrim(event,this);">&nbsp;

            <label>栏目：</label><input type="text" value="${cond.abstracts}"
                                     name="abstracts" class="form-control" size="8"
                                     onkeydown="return banInputSapce(event);" onKeyup="return inputSapceTrim(event,this);">&nbsp;

            <label>来源：</label><input type="text" value="${cond.source}"
                                     name="source" class="form-control" size="8"
                                     onkeydown="return banInputSapce(event);" onKeyup="return inputSapceTrim(event,this);">&nbsp;

            <label>关键字：</label><input type="text" value="${cond.keyWord}"
                                     name="keyWord" class="form-control" size="8"
                                      onkeydown="return banInputSapce(event);" onKeyup="return inputSapceTrim(event,this);">&nbsp;

            <label>状态:</label>
            <select onchange="$('.status').val(this.options[this.selectedIndex].value);" id="statusSelect" data-toggle="selectpicker">
                <option value="">请选择</option>
                <option value="1">未发布</option>
                <option value="2">草稿</option>
                <option value="3">已发布</option>
            </select>&nbsp;
            <label>发布类型:</label>
            <select onchange="$('.publishType').val(this.options[this.selectedIndex].value);" id="publishTypeSelect" data-toggle="selectpicker">
                <option  value="">请选择</option>
                <%--方法二 回显发布类型下拉框--%>
                <option ${cond.publishType eq 1 ?'selected':''} value="1">手动发布</option>
                <option ${cond.publishType eq 2 ?'selected':''} value="2">自动发布</option>
                <option ${cond.publishType eq 3 ?'selected':''} value="3">定时发布</option>
            </select>&nbsp;
            <button type="submit" class="btn-default" data-icon="search">查询</button>
            <a class="btn btn-orange" href="javascript:;"
               onclick="$(this).navtab('reloadForm', true);" data-icon="undo">清空查询</a>
            <div class="pull-right">
                <a href="<%=basePath%>/article/download"
                        class="btn btn-green" data-icon="download"
                        data-id="content_add"  data-width="1200"
                        data-height="900" data-id="dialog-mask" data-mask="true">下载模板</a>

                <a href="<%=basePath%>/article/uploadPage.do"
                   class="btn btn-green" data-toggle="dialog" data-icon="upload"
                   data-title="导入文章" data-width="500"
                   data-height="200" data-id="dialog-mask" data-mask="true">导入excle</a>


                <a href="<%=basePath%>/article/getArticleAddOrEditPage.do?"
                   class="btn btn-green" data-icon="plus" data-toggle="dialog"
                   data-id="user_add" data-title="添加文章" data-width="1200"
                   data-height="1200" data-id="dialog-mask" data-mask="true">添加文章</a>
                <div class="btn-group">
                    <button type="button" class="btn btn-green"
                            data-toggle="dropdown" data-icon="copy">
                        批量操作<span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu right" role="menu">
                        <li><a href="<%=basePath%>/article/deleteArticle.do"
                               data-toggle="doajaxchecked" data-confirm-msg="确定要删除选中项吗？"
                               data-idname="delids" data-group="ids">删除选中</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </form>
</div>
<div class="bjui-pageContent tableContent">
    <table data-toggle="tablefixed" data-width="100%" data-nowrap="true">
        <thead>
        <tr>
            <th width="26" align="center">
                <input type="checkbox" class="checkboxCtrl" data-group="ids" data-toggle="icheck">
            </th>
            <th width="20" align="center">ID</th>
            <th width="60" align="center">标题</th>
            <th width="60" align="center">摘要</th>
            <th width="40" align="center">作者</th>
            <th width="60" align="center">栏目</th>
            <th width="60" align="center">来源</th>
            <th width="60" align="center">关键字</td>
            <th width="30" align="center">浏览次数</td>
            <th width="30" align="center">状态</th>
            <th width="30" align="center">发布类型</th>
            <th width="70" align="center">发布时间</th>
            <th width="70" align="center">创建时间</th>
            <th width="100" align="center">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.list}" var="article" varStatus="status">
            <tr data-id="${article.id}">
                <td align="center"><input type="checkbox" name="ids"
                                          data-toggle="icheck" value="${article.id}"></td>
                <td align="center">${article.id}</td>
                <td align="center"><a target="_blank" href="${serverProtocol}${article.articleAddress}">${article.title}</a></td>
                <td align="center">${article.abstracts}</td>
                <td align="center"><a target="_blank" href="${serverProtocol}${serverIp}:${serverPort}/author/list_01?author=${article.author}">${article.author}</a></td>
                <td align="center"><a target="_blank" href="${serverProtocol}${serverIp}:${serverPort}/${article.menuName}">${article.menuNameCn}</a></td>
                <td align="center"><a target="_blank" href="${serverProtocol}${serverIp}:${serverPort}/source/list_01?source=${article.source}">${article.source}</a></td>
                <td align="center">
                    <c:forTokens items="${article.keyWord}" delims="," var="word" varStatus="status">
                        <a target="_blank" href="${serverProtocol}${serverIp}:${serverPort}/keyWord/list_01?keyWord=${word}">${word}</a>
                        <c:if test="${ !status.last}">
                            ,
                        </c:if>
                    </c:forTokens>
                </td>
                <td align="center">${article.browseCount}</td>
                <td align="center">
                    <c:if test="${article.status==1}">未发布</c:if>
                    <c:if test="${article.status==2}">草稿</c:if>
                    <c:if test="${article.status==3}">已发布</c:if>
                </td>
                <td align="center">
                    <c:if test="${article.publishType eq 1}">手动发布</c:if>
                    <c:if test="${article.publishType eq 2}">自动发布</c:if>
                    <c:if test="${article.publishType eq 3}">定时发布</c:if>
                </td>
                <td align="center"><fmt:formatDate value='${article.deployTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
                <td align="center"><fmt:formatDate value='${article.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/>
                </td>
                <td>
                    <a href="<%=basePath%>/article/getArticleAddOrEditPage.do?id=${article.id}"
                       class="btn btn-green" data-toggle="dialog"
                        data-title="编辑" data-width="1200"
                       data-height="1200" data-id="dialog-mask" data-mask="true">编辑</a>
                    <c:if test="${article.status != 3 && article.publishType != 3}">
                        <a
                                href="<%=basePath%>/article/operationAticleStatus.do?status=3&id=${article.id}"
                                class="btn btn-blue" data-toggle="doajax"
                                data-title="发布"
                                data-height="1200" data-id="dialog-mask" data-mask="true" data-confirm-msg="确定要发布该行信息吗？">发布</a>
                    </c:if>
                    <c:if test="${article.status == 1 && article.publishType == 1}">
                        <a
                                href="<%=basePath%>/article/operationAticleStatus.do?status=2&id=${article.id}"
                                class="btn btn-orange" data-toggle="doajax"
                                data-title="移至草稿箱"
                                data-height="1200" data-id="dialog-mask" data-confirm-msg="确定要该行信息移至草稿箱吗？" data-mask="true">移至草稿箱</a>
                    </c:if>
                    <a href="<%=basePath%>/article/deleteArticle.do?id=${article.id}"
                       class="btn btn-red" data-toggle="doajax"
                       data-confirm-msg="确定要删除该行信息吗？">删除</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- 分页 -->
<div class="bjui-pageFooter">
    <div class="pages">
        <span>每页&nbsp;</span>
        <div class="selectPagesize">
            <select data-toggle="selectpicker"
                    data-toggle-change="changepagesize">
                <c:forEach begin="30" end="120" step="30" varStatus="s">
                    <option value="${s.index}"
                        ${page.pageSize eq s.index ? 'selected="selected"' : ''}>${s.index}</option>
                </c:forEach>
            </select>
        </div>
        <span>&nbsp;条，共${page.total}条</span>
    </div>
    <div class="pagination-box" data-toggle="pagination"
         data-total="${page.total}" data-page-size="${page.pageSize}"
         data-page-current="${page.pageNum}"></div>
</div>
<script>

    /**
     *  方法一
     * 查询条件 状态下拉框初始化框赋值 回显
     **/
    var status = "${cond.status}";
    if (null != status && '' != status){
        $("#statusSelect").val(status);
    }


    /**
     * 是否去除所有空格
     * @param str
     * @param is_global 如果为g或者G去除所有的
     * @returns
     */
    function Trim(str,is_global)
    {
        var result;
        result = str.replace(/(^\s+)|(\s+$)/g,"");
        if(is_global.toLowerCase()=="g")
        {
            result = result.replace(/\s/g,"");
        }
        return result;

    }

    /**
     * 空格输入去除
     * @param e
     * @returns {Boolean}
     */
    function inputSapceTrim(e,this_temp)
    {
        this_temp.value = Trim(this_temp.value,"g");
        var keynum;
        if(window.event) // IE
        {
            keynum = e.keyCode
        }
        else if(e.which) // Netscape/Firefox/Opera
        {
            keynum = e.which
        }
        if(keynum == 32){
            return false;
        }
        return true;
    }

    /**
     * 禁止空格输入
     * @param e
     * @returns {Boolean}
     */
    function banInputSapce(e)
    {
        var keynum;
        if(window.event) // IE
        {
            keynum = e.keyCode
        }
        else if(e.which) // Netscape/Firefox/Opera
        {
            keynum = e.which
        }
        if(keynum == 32){
            return false;
        }
        return true;
    }
</script>