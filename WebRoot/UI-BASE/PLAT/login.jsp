<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-TAG/platForm.tld" prefix="PF"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<PF:basePath/>" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><PF:sysTitle />-登录页</title>
		
		<link rel="stylesheet" type="text/css" href="WEB-FACE/model/easy_ui_1_3_6/themes/gray/easyui.css">
		<script type="text/javascript" src="WEB-FACE/script/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="WEB-FACE/script/placeholder.js"></script>
		<script type="text/javascript" src="WEB-FACE/model/easy_ui_1_3_6/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="WEB-FACE/model/easy_ui_1_3_6/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="WEB-FACE/model/easy_ui_1_3_6/easyui.farm.js"></script>	
		<script type="text/javascript" src="WEB-FACE/model/easy_ui_1_3_6/ajaxfileupload.js"></script>	
		<script type="text/javascript" src="PLUGIN/pop/commons/jquery.validate.exp.js"></script>	
		<script type="text/javascript" src="PLUGIN/pop/commons/tools.js"></script>	
		
		<link rel="stylesheet" type="text/css" href="WEB-FACE/model/bootstrap-3.3.1/css/bootstrap.min.css" />
		<script type="text/javascript" src="WEB-FACE/model/bootstrap-3.3.1/js/bootstrap.min.js"></script>
		<link rel="stylesheet" style="text/css" href="WEB-FACE/model/simple-line-icons/css/simple-line-icons.css">
		<link rel="stylesheet" style="text/css" href="WEB-FACE/model/simple-line-icons/common.css">
		<link rel="stylesheet" style="text/css" href="WEB-FACE/model/simple-line-icons/style.css">
		<link rel="stylesheet" style="text/css" href="WEB-FACE/model/simple-line-icons/media-queries.css">
		<script src="<PF:basePath/>/WEB-FACE/script/Md5.js"
			language="javascript" type="text/javascript"></script>
		<style type="text/css">
body {
	margin: 0;
	padding: 0;
	background: #d3d7d7;
}  /*
	login资源表述页面的CSS
*/
html {
	overflow-y: scroll;
	font-size: 100%; /* bootstrap 从2升级到3需要改下*/
}

body {
	/*font-family: 微软雅黑, tahoma, verdana, arial, sans-serif;
	font-size: 85%;
	margin: 0;
	padding: 0;
	line-height: 1.5em;
	color: #3f3f3f;*/
}

#loginPage {
	margin: 100px auto 0 auto;
	padding: 0 0 1em 0;
	height: 100%;
	width: 700px;
	text-align: left;
	background: #e3e9ef;
	border: solid 1em #fff;
}

#pageContent {
	margin: 40px 20px 0 150px;
	padding: 0;
}

span.logo {
	margin: 2.5em 0 0 0;
	padding: 0;
	float: left;
	width: 135px;
	text-align: center;
}

.noCookiesEnabledMessage,.noJavaScriptEnabledMessage {
	color: #ed2c10;
	margin-top: 3em;
	text-align: center;
	font-size: 200%;
}

.bottomBorder {
	border-bottom: 1px solid #babec1;
}

.form-horizontal .form-group {
	margin-bottom: 12px; *
	zoom: 1;
}

h1,h2,h3 {
	line-height: 50px;
	font-weight: normal;
}

#loginform .form-group .control-label {
	font-size: 120%;
	margin-right: 10px;
}

.form-horizontal .controls {
	padding-left: 0px;
	margin-left: 0px;
}

.form-horizontal .controls:first-child { *
	padding-left: 0px;
}

#loginPage p#loginDescription {
	margin-top: 10px;
	font-size: 110%;
}

.errorMsg {
	color: #FF0000;
	margin: 1.5em 0 0;
	padding-top: .2em;
	display: none;
	font-weight: normal;
	border-top: 1px solid #babec1;
}

button {
	font-family: 微软雅黑, tahoma, verdana, arial, sans-serif;
}
h1{ font-size: 36px;}
</style>
	</head>
	<body
		style="background-image: url('WEB-FACE/img/style/bgLogin-1.jpg');">
		<div id="noCookiesEnabledMessage" class="noCookiesEnabledMessage"
			style="display: none;">
			Please enable cookies in your browser to proceed with login.
		</div>
		<span id="testCookie"></span>

		<div id="loginPage">
			<span class="logo"> <img
					src="WEB-FACE/img/style/contacts.png"
					alt="SuperMap iServer" width="130" height="130" /> </span>
			<div id="pageContent">
				<form name="loginform" id="loginFormId" method="post"
						action="admin/ALONEFRAME_LOGIN_COMMIT.do"
					class="form-singin">
					<table>
						<tr>
							<td colspan="3">
								<h3 id="header" class="bottomBorder form-signin">
									登录
									<font color="green" style="font-size: 18px;">西城区文物管理处</font>
									可移动文物信息管理系统
								</h3>
								<!-- 
								<p id="loginDescription">
									<span class="text-muted"><span class="vWord">Version</span>
									1.0</span>欢迎使用 ！
								</p>
								 -->
							</td>
						</tr>
					</table>
					<table>
						<tr>
							<td>
								<div class="form-group has-feedback has-feedback-left">
									<input name="name" type="text" class="form-control valid" placeholder="Username" aria-invalid="false">
									<i class="icon-user form-control-feedback"></i>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-group has-feedback has-feedback-left">
									<input name="password" type="password" class="form-control" placeholder="Password">
									<i class="icon-key form-control-feedback"></i>
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="form-group action clearfix">
									<button id="log_in" class="pull-left btn btn-success loginBtn" type="submit" data-loading-text="Log in">登录</button>
								</div>
							</td>
						</tr>
					</table>
			
					
				</form>
				<div id="errorMessage" class="errorMsg">${page.message}</div>
			</div>
			<div id="resultDisplay" style="color: #ffaaaa; display: none">
			</div>
		</div>
	</body>
	<script type="text/javascript">
	var mes='${page.message}';
	$(function() {
		var msg = document.getElementById("errorMessage");
		if(mes != null && mes.length > 0){
			msg.innerHTML =mes;
			$(msg).show();
		}
		$('#loginButton').bind('click', function() {
			var password = $('#passwordId').attr('value');
			var name = $('#name').attr('value');
			if(name == null || name.length <= 0){
				msg.innerHTML = "用户名不能为空";
				$(msg).show();
				return false;
			}
			if(password == null || password.length <= 0){
				msg.innerHTML = "密码不能为空";
				$(msg).show();
				return false;
			}
			if (password != null && password != '') {
				$('#passwordId').attr('value', hex_md5(password));
			}
			$('#loginFormId').submit();
			$('#loginButton').hide();
		});

		
		$('#id_loginButto').bind('mouseover ', function() {
			$('#id_loginButto').attr('src', 'WEB-FACE/img/style/land_on.png');
		});
		$('#id_loginButto').bind('mouseout ', function() {
			$('#id_loginButto').attr('src', 'WEB-FACE/img/style/land.png');
		});
		$('#passwordId').keydown(function(e) {
			if (e.keyCode == 13) {
				$('#loginButton').click();
			}
		});
	})
</script>
</html>