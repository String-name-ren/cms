<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/4/17
  Time: 17:32
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="../common.jsp"%>
<html>
<head>
    <title>淘宝上传</title>
    <script>
        function submitForm() {
            var obj_file = document.getElementById("excelFile");
            if(obj_file.value==""){
//                alert("请先选择上传文件");
                BJUI.alertmsg("error","请先选择上传文件");
                return;
            }
            if(window.confirm('你确定要批量导入文章吗？')){
                $("#addForm").bjuiajax('ajaxForm', {
                    url:"<%=basePath%>/article/uploadArticle.do",
                    callback:function(result){
                        if(result.statusCode==200){
// //                            alert("导入成功!");
//                             if(result.secc>0 && result.err==0){
// //                                alert("导入成功条数为："+result.secc);
//                                 BJUI.alertmsg("info","导入成功条数为："+result.secc);
//                             }else if(result.secc==0 && result.err>0){
// //                                alert("导入失败条数为："+result.err);
//                                 BJUI.alertmsg("info","导入失败条数为："+result.err);
//                             }else {
// //                                alert("导入成功条数为："+result.secc+"  导入失败条数为："+result.err);
//                                 BJUI.alertmsg("info","导入成功条数为："+result.secc+"  导入失败条数为："+result.err);
//                             }
                            BJUI.alertmsg("ok",result.message);
                            $(this).navtab('reloadForm', true);
                        }else {
//                            alert("导入失败!");
                            BJUI.alertmsg("error","导入失败!");
                        }
                        $(".btn-close").click();
                    }
                });
                return true;
            }else{
                return false;
            }
        }

    </script>
    <style>
        #tab .form-control {width: 100%;}
        .n-myinvalid {
            border-color: #FF0000;
            border: 1px solid red;
            outline: 0;
            -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(255, 0, 0, 0.6);
            box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(255, 0, 0, 0.6);
        }
    </style>
</head>
<body>
<div class="bjui-pageContent">
    <form id="addForm" action="<%=basePath%>/mall/TBBoutique/uploadTBBoutiqueFile.do"
          class="pageForm" data-toggle="validate" enctype="multipart/form-data">
        <table class="table table-condensed table-hover">
            <tbody>
            <tr>
                <td class="text-left" style="width:120px"><label class="control-label x120">上传Excel文件：</label> </td>
                <td class="text-left"><input type="file" name="excelFile" id="excelFile"
                                              accept=".xls,.xlsx"/></td>
            </tr>
            </tbody>
        </table>
    </form>
</div>
<div class="bjui-pageFooter">
    <ul>
        <li><button type="button" class="btn-close">关闭</button></li>
        <li><button type="button" class="btn-default"
                    onclick="submitForm();">上传</button></li>
    </ul>
</div>
</body>
</html>
