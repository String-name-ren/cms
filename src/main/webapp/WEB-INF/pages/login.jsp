<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ include file="common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cms管理系统登录</title>
<script src="<%=basePath%>/BJUI/js/jquery-1.11.3.min.js"></script>
<script src="<%=basePath%>/BJUI/js/jquery.cookie.js"></script>
<link href="<%=basePath%>/BJUI/themes/css/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
* {font-family: "Verdana", "Tahoma", "Lucida Grande", "Microsoft YaHei", "Hiragino Sans GB", sans-serif;}
body {
    background: url(<%=basePath%>/images/loginbg_01.jpg) no-repeat center center fixed;
    -webkit-background-size: cover;
    -moz-background-size: cover;
    -o-background-size: cover;
    background-size: cover;
}
a:link {color: #285e8e;}
.main_box {
    position: absolute; top:50%; left:50%; margin-top:-260px; margin-left: -300px; padding: 30px; width:600px; height:460px;
    background: #FAFAFA; background: rgba(255,255,255,0.5); border: 1px #DDD solid;
    border-radius: 5px;
    -webkit-box-shadow: 1px 5px 8px #888888; -moz-box-shadow: 1px 5px 8px #888888; box-shadow: 1px 5px 8px #888888;
}
.main_box .setting {position: absolute; top: 5px; right: 10px; width: 10px; height: 10px;}
.main_box .setting a {color: #FF6600;}
.main_box .setting a:hover {color: #555;}
.login_logo {margin-bottom: 20px; height: 45px; text-align: center;}
.login_logo img {height: 45px;}
.login_msg {text-align: center; font-size: 16px;}
.login_form {padding-top: 20px; font-size: 16px;}
.login_box .form-control {display: inline-block; *display: inline; zoom: 1; width: auto; font-size: 18px;}
.login_box .form-control.x319 {width: 319px;}
.login_box .form-control.x164 {width: 164px;}
.login_box .form-group {margin-bottom: 20px;}
.login_box .form-group label.t {width: 120px; text-align: right; cursor: pointer;}
.login_box .form-group.space {padding-top: 15px; border-top: 1px #FFF dotted;}
.login_box .form-group img {margin-top: 1px; height: 32px; vertical-align: top;}
.login_box .m {cursor: pointer;}
.bottom {text-align: center; font-size: 12px;}
</style>
<script type="text/javascript">
var COOKIE_NAME = 'sys__username';
$(function() {
    choose_bg();
	changeCode();
	if ($.cookie(COOKIE_NAME)){
	    $("#j_username").val($.cookie(COOKIE_NAME));
	    $("#j_password").focus();
	    $("#j_remember").attr('checked', true);
	} else {
		$("#j_username").focus();
	}
	$("#captcha_img").click(function(){
		changeCode();
	});
	$("#login_form").submit(function(){
		var issubmit = true;
		var i_index  = 0;
		$(this).find('.in').each(function(i){
			if ($.trim($(this).val()).length == 0) {
				$(this).css('border', '1px #ff0000 solid');
				issubmit = false;
				if (i_index == 0)
					i_index  = i;
			}
		});
		if (!issubmit) {
			$(this).find('.in').eq(i_index).focus();
			return false;
		}
		var checked = $("[id='j_remember']:checked")
		if (checked && checked.length > 0) {
			$.cookie(COOKIE_NAME, $("#j_username").val(), {expires: 15});
		} else {
			$.cookie(COOKIE_NAME, null);  //删除cookie
		}
		$("#login_ok").attr("disabled", true).val('登陆中..');
	});
});
function genTimestamp(){
	var time = new Date();
	return time.getTime();
}
function changeCode(){
	$("#captcha_img").attr("src", basePath+"/system/user/getVerifyCode.do?t="+genTimestamp());
}
function choose_bg() {
	var bg = Math.floor(Math.random() * 9 + 1);
	$('body').css('background-image', 'url(<%=basePath%>/images/loginbg_0'+ bg +'.jpg)');
}
function verifyName(obj){
	$.ajax({
		type : "post",
		url : "<%=basePath%>/system/white/verifyName.do?username="+$(obj).val(),
		dataType:"json",
		success : function(result) {
			
			$("#verify").attr("value",result.statusCode);
			$("#phoneNote").val(result.filename);
			$("#message").val(result.message);
			if(result.statusCode==0 && result.message =='null' &&  result.filename !=null ){
				var str='<label for="j_password" class="t">手机验证码：</label> <input id="phone" value="" name="phone"  class="form-control x164 in"> <input type="button" id="gain" value="获取验证码"  onclick="note(this);">';
				$(".phoneCode").html(str);
				/* $("#gain").click(); */
			}else{
				$(".phoneCode").empty();
			}
		}
	})
}

var countdown=60;
function note(val) {
	if(countdown==60){	
		$.post("<%=basePath%>/system/white/phoneNote.do?phone="+$("#phoneNote").val(),function(data){
			if(data!=200){
				$("#errorMsg").html("发送短信验证码失败，重新发送");
				return true;
			}else{
				settime(val);
			}
		})
	}
} 
function settime(val){
	if (countdown == 0) { 
		val.value="获取验证码";
		countdown=60;
		$("#gain").removeAttr("disabled");
		$.post("<%=basePath%>/system/white/removePhone.do");
		return
	} else { 
		val.setAttribute("disabled", true); 
		val.value="倒计时(" + countdown + "s)"; 
		countdown--; 
	} 
	setTimeout(function() { 
	settime(val) 
	},1000) 
}
</script>
</head>
<body>
<div class="main_box">
    <div class="setting"><a href="javascript:;" onclick="choose_bg();" title="更换背景"><span class="glyphicon glyphicon-th-large"></span></a></div>
	<div class="login_box">
        <div class="login_logo">
            <img src="<%=basePath%>/images/logo-cms.png" >
        </div>
			<div class="login_msg">
	      		<font color="red" id="errorMsg">${errorMsg}</font>
	    	</div>
        <div class="login_form">
            <input type="hidden" value="${randomKey }" id="j_randomKey" />
    		<form action="<%=basePath%>/system/user/index.do" id="login_form" method="post">
                <input type="hidden" name="jfinal_token" value="${jfinal_token }" />
                <input type="hidden" name="verify" id="verify"/>
                 <input type="hidden" name="message" id="message"/>
                <input type="hidden" name="phoneNote" id="phoneNote"/>
    			<div class="form-group">
    				<label for="j_username" class="t">用户名：</label> <input id="j_username" value="" name="username" onchange="" type="text" class="form-control x319 in" autocomplete="off">
    			</div>
    			<div class="form-group">
    				<label for="j_password" class="t">密　码：</label> <input id="j_password" value="" name="password" type="password" class="form-control x319 in">
    			</div>
    			<div class="phoneCode form-group">
    				
    			</div>
    			<div class="form-group">
    				<label for="j_captcha" class="t">验证码：</label> <input id="j_captcha" name="verifyCode" type="text" class="form-control x164 in">
    				<img id="captcha_img" alt="点击更换" title="点击更换" src="" class="m">
    			</div>
    			<div class="form-group">
                    <label class="t"></label>
                    <label for="j_remember" class="m"><input id="j_remember" type="checkbox">&nbsp;记住用户名</label>
    			</div>
    			<div class="form-group space">
                    <label class="t"></label>　　　
    				<input type="submit" id="login_ok" value="&nbsp;登&nbsp;录&nbsp;" class="btn btn-primary btn-lg">&nbsp;&nbsp;&nbsp;&nbsp;
    				<input type="reset" value="&nbsp;重&nbsp;置&nbsp;" class="btn btn-default btn-lg">
    			</div>
    		</form>
        </div>
	</div>
	<div class="bottom">Copyright &copy; 2016 <a href="https://www.beadwallet.com/" target="_blank">上海水象网络科技有限公司</a></div>
</div>
</body>
</html>