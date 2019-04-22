<%@page contentType="text/html" pageEncoding="utf-8" %>
<%@ include file="../common.jsp" %>


<link type="text/css" rel="stylesheet" href="<%=basePath%>/ueditor/themes/default/css/ueditor.css"/>
<link rel="stylesheet" href="<%=basePath%>/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="<%=basePath%>/css/zTreeStyle/demo.css" type="text/css">
<script type="text/javascript" charset="utf-8" src="<%=basePath%>/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=basePath%>/ueditor/ueditor.all.min.js"></script>
<script type="text/javascript" src="<%=basePath%>/ueditor/lang/zh-cn/zh-cn.js"></script>


<script type="text/javascript">

    function submitArticleContentForm() {
        
        //校验文章内容
        var um = UE.getEditor('contentEditor');
        var cont;
        um.ready(function () {
            //设置编辑器的内容
            <%--um.setContent('${article.content}');--%>
            cont = um.getContent();
            // alert("cont："+cont);
        });
        if ("undefined" == typeof (cont)|| null == cont || "" == cont || cont.size <= 0){
            // alert("请输入文章内容！");
            $(this).alertmsg('error', '请输入文章内容！', {displayMode:'none', displayPosition:'topcenter', okName:'确认', title:'提示信息'})
            return;
        }

        //校验栏目
        var menuIdValue = $("#menuId").val();
        if ("undefined" == typeof (menuIdValue) || null == menuIdValue || "" == menuIdValue || menuIdValue.size <= 0){
            $(this).alertmsg('error', '请选择栏目！', {displayMode:'none', displayPosition:'topcenter', okName:'确认', title:'提示信息'})
            return;
        }
        
        //验证定时发布时  时间应大于当前时间
        var start = new Date($("#deployTime").val());
        var deployTime = start.getTime();
        var now =(new Date()).getTime();
        if(deployTime < now){
            $(this).alertmsg('error', '定时发布时间不能晚于当前时间！', {displayMode:'none', displayPosition:'topcenter', okName:'确认', title:'提示信息'})
            return;
        }

        var form = $("#article_form");
        form.submit();
    }

</script>

<SCRIPT type="text/javascript">
    /*树形菜单JS start*/
    var setting = {
        check: {
            enable: true,
            chkStyle: "radio",
            radioType: "all"
        },
        view: {
            dblClickExpand: false
        },
        data: {
            simpleData: {
                enable: true,
                pIdKey: "parentId"
            }
        },
        callback: {
            onClick: onClick,
            onCheck: onCheck
        }
    };
    function onClick(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
    }

    function onCheck(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            nodes = zTree.getCheckedNodes(true),
            v = "",
            //获取id by qtj
            i = "";
        // console.log("nodes:");
        // console.log(nodes);
        for (var i=0, l=nodes.length; i<l; i++) {
            v += nodes[i].name + ",";
        }

        //单选为一个node
        i = nodes[0].id;

        if (v.length > 0 ) v = v.substring(0, v.length-1);
        var cityObj = $("#menuName");
        cityObj.attr("value", v);
        //menuId赋值
        $("#menuId").attr("value",i);
        // alert("id:"+i);
        // alert("name:"+v);
    }

    function showMenu() {
        var cityObj = $("#menuName");
        var cityOffset = $("#menuName").offset();
        $("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
        $("#menuContent").offset({left: cityOffset.left, top: cityOffset.top+$("#menuName")[0].clientHeight})

        // $("#menuContent").css({left:0 + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
        $("body").bind("mousedown", onBodyDown);
    }
    function hideMenu() {
        $("#menuContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }
    function onBodyDown(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "citySel" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
            hideMenu();
        }
    }

    $(document).ready(function(){
        var treeJsonStr =eval("(" + '${treeJsonStr}' + ")");
        // console.log("treeJsonStr:");
        // console.log(treeJsonStr);
        $.fn.zTree.init($("#treeDemo"), setting, treeJsonStr);
    });
    /*树形菜单JS end*/

</SCRIPT>

<div class="bjui-pageContent">
    <form id="article_form"
          action="<%=basePath%>/article/saveOrUpdate.do"
          class="pageForm" data-toggle="validate" >
        <input type="hidden" name="id" value="${article.id}"/>
        <legend>基本信息</legend>
        <table class="table table-condensed table-hover" id="productContent">
            <tbody>
            <tr>
                <td class="text-right" width="50px"><label class="row-label">标题:</label></td>
                <td class="text-left">
                    <input disabled="true" type="text" id="title" name="title" style="width: 800px" class="form-control" data-rule="required length[~32]" value="${article.title}"/>
                </td>
            </tr>
            <tr>
                <td class="text-right">
                    <label class="control-label x90">内容：</label>
                </td>
                <td class="text-left" disabled="true">
                    <script type="text/plain" id="contentEditor" name="content" style="width: 800px;height: 200px; ">
                    </script>
                    <script type = "text/javascript" >
                    var um = UE.getEditor('contentEditor');
                    um.ready(function () {
                        //设置编辑器的内容
                        um.setContent('${article.content}');
                        um.setDisabled();

                    });
                    </script>
                </td>
            </tr>
            <tr>
                <td class="text-right" style="height: 100%;text-align:right;width:100%;display:inline-block;"><label class="row-label">栏目:</label></td>
                <td>
                    <input id="menuId" type="hidden" name="menuId" readonly value="${article.menuId}" style="width:120px;" class="form-control" />
                        <div class="content_wrap">
                            <div class="zTreeDemoBackground left">
                                <ul class="list">
                                    <li class="title"><input disabled="true" id="menuName" name="menuName" type="text" readonly value="${article.menuName}" style="width:120px;"  class="form-control"/>
                                        &nbsp;<%--<a id="menuBtn" href="#" onclick="showMenu(); return false;">select</a>--%></li>
                                </ul>
                            </div>
                        </div>
                        <div id="menuContent" class="menuContent" style="display:none; position: absolute;left:262px;top:0;z-index:9;">
                            <ul id="treeDemo" class="ztree" style="margin-top:0; width:180px; height: 300px;"></ul>
                        </div>
                </td>
            </tr>
            <tr>
                <td class="text-right"><label class="row-label">作者:</label></td>
                <td class="text-left">
                    <input disabled="true" type="text" name="author" value="${article.author}" class="form-control" data-rule="required length[~32]"/>
                </td>
            </tr>
            <tr>
                <td class="text-right"><label class="row-label">摘要:</label></td>
                <td class="text-left">
                    <input  disabled="true" type="text" id="abstracts" name="abstracts" value="${article.abstracts}" class="form-control" data-rule="required length[~32]"/>
                </td>
            </tr>
            <tr>
                <td class="text-right"><label class="row-label">来源:</label></td>
                <td class="text-left">
                    <input disabled="true" type="text" name="source" value="水象分期" class="form-control" data-rule="required length[~32]"/>
                </td>
            </tr>
            <tr>
                <td class="text-right"><label class="row-label">关键字:</label></td>
                <td class="text-left">
                    <input disabled="true" type="text" name="keyWord" value="${article.keyWord}" class="form-control" placeholder="多个关键字请用','隔开" data-rule="required length[~32]"/>
                </td>
            </tr>
            <tr>
                <td class="text-right"><label class="row-label">发布类型:</label></td>
                <td class="text-left">
                    <input type="hidden" name="publishType" id="publishType" value="${article.publishType}" class="form-control"/>
                    <select disabled="true" id="publishTypeSelect" onchange="$('#publishType').val(this.options[this.selectedIndex].value);if (this.value == 3){$('#timingTime').html('<input type=\'text\' id=\'deployTime\' name=\'deployTime\' size=\'20\' data-rule=\'required \' data-toggle=\'datepicker\' data-pattern=\'yyyy-MM-dd HH:mm:ss\' class=\'form-control\' style=\'padding-right: 15px; width: 200px;\'>')}else {$('#timingTime').html('')};" data-toggle="selectpicker">
                        <option value="1" ${article.publishType eq 1 ?'selected':''}>手动发布</option>
                        <option value="2" ${article.publishType eq 2 ?'selected':''}>自动发布</option>
                        <option value="3" ${article.publishType eq 3 ?'selected':''}>定时发布</option>
                    </select>
                    <span id="timingTime"></span>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<script>
    //定时发布时  发布时间回显赋值
    var publishType = "${article.publishType}";
    if ("undefined" !=typeof (publishType) && null != publishType && '' != publishType){
        if (publishType == 3){
            var time1 = "${article.deployTime}";
            $("#timingTime").html("<input type='text' id='deployTime' name='deployTime' value='"+Todate(time1)+"'  data-rule='required ' size='20' data-toggle='datepicker' data-pattern='yyyy-MM-dd HH:mm:ss' class='form-control' style='padding-right: 15px; width: 200px;'>")
        }
    }else{
        //默认值 1手动发布
        $("#publishType").val("1");
    }

    /**
     * 将时间Sun Sep 18 00:00:00 CST 2011 转换为 yyyy-MM-dd HH:mm:ss(24制)<br>
     * 方 法 名：Todate <br>
     * @param num  需要转换的时间
     * @returns {String} String 格式化后的时间
     */
    function Todate(num) {
        if(num==undefined||num==""){
            return " ";
        }
        num = num + ""; //给字符串后就一个空格
        var date = "";
        var month = new Array();
        month["Jan"] = '01'; month["Feb"] = '02'; month["Mar"] = '03'; month["Apr"] = '04';
        month["May"] = '05'; month["Jun"] = '06'; month["Jul"] = '07'; month["Aug"] = '08';
        month["Sep"] = '09'; month["Oct"] = '10'; month["Nov"] = '11'; month["Dec"] = '12';
        var week = new Array();
        week["Mon"] = "一"; week["Tue"] = "二"; week["Wed"] = "三"; week["Thu"] = "四";
        week["Fri"] = "五"; week["Sat"] = "六"; week["Sun"] = "日";
        str = num.split(" "); //根据空格组成数组
        date = str[5] + "-"; //就是在2008的后面加一个“-”
        //通过修改这里可以得到你想要的格式
        date = date + month[str[1]] + "-" + str[2] + " " + str[3];
        //date=date+" 周"+week[str[0]];
        return date;
    }


</script>