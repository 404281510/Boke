<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>登录</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link rel="stylesheet" type="text/css" href="css/thickbox.css" />
<script type="text/javascript">
	function changeVerificationCode() {
		var verificationCodeImage = document
				.getElementById("verificationCodeImage");
		verificationCodeImage.src = "verification_code.do?random="
				+ new Date().getTime();
	}

	function hideError() {
		document.getElementById("userNameEmpty").style.display = "none";
		document.getElementById("passwordEmpty").style.display = "none";
		document.getElementById("codeEmpty").style.display = "none";
		document.getElementById("codeError").style.display = "none";
		document.getElementById("invalidUser").style.display = "none";
	}

	function checkSubmit() {
		hideError();

		var m1 = document.getElementById("userNameEmpty");
		var m2 = document.getElementById("passwordEmpty");
		var m3 = document.getElementById("codeEmpty");

		var u = document.getElementById("userName");
		var p = document.getElementById("password");
		var v = document.getElementById("verificationCode");

		if (u.value.length <= 0) {
			u.focus();
			m1.style.display = "block";
			return false;
		}

		if (p.value.length <= 0) {
			p.focus();
			m2.style.display = "block";
			return false;
		}

		if (v.value.length <= 0) {
			v.focus();
			m3.style.display = "inline-block";
			return false;
		}
		document.forms[0].submit();
	}
</script>

<style type="text/css">
body {
	font: 12px "宋体";
	margin-top: 50px;
}

a {
	color: #000;
	text-decoration: none;
}

a:hover {
	color: #F30;
	text-decoration: underline;
}

.txtbox {
	border: 1px solid #E79F50;
	height: 20px;
	line-height: 22px;
	width: 120px;
}

#login {
	width: 400px;
	height: 200px;
	overflow: hidden;
	margin: 0px auto;
	border: solid 1px;
}

#title {
	width: 400px;
	height: 25px;
	line-height: 25px;
	color: #F60;
	font-weight: 700;
	background-color: #FDEBD9;
	text-align: center;
	margin: 0px auto;
}

#bottom {
	width: 101px;
	height: 34px;
	background: url(images/login.png);
	float: right;
	line-height: 25px;
	margin-right: 20px;
}

.warning {
	color: #FF0000;
	font: 12px "宋体";
}

#loginbox {
	width: 330px;
	height: 100px;
	margin: 10px 20px;
	float: left;
	display: inline;
}
</style>

</head>
<body>
	<s:form name="form1" method="post" action="login_login.do">
		<div id="login">
			<div id="title">
				登录后才能上传视频和评论，若没有账号请<a href="register_init.do"> 点击注册 </a>。
			</div>
			<div style="width:360px;height:120px;margin:0px auto;">
				<table id="loginbox" border="0" cellpadding="0" cellspacing="0">
					<tr style="height:30px">
						<td width="60" class="bfont">用户名：</td>
						<td width="130"><s:textfield cssClass="txtbox"
								name="userName" value="daniel" id="userName" />
						</td>
						<td><span class="warning" id="userNameEmpty"
							style="display: none">用户名为空！</span><label id="invalidUser"
							class="warning" style="display: inline-block;"><s:fielderror
									fieldName="invalid_user_error" /> </label>
						</td>
					</tr>
					<tr style="height:30px">
						<td class="bfont">密&nbsp;&nbsp;码：</td>
						<td><s:password cssClass="txtbox" name="password"
								value="daniel" id="password" />
						</td>
						<td><span class="warning" id="passwordEmpty"
							style="display: none">密码为空！</span>
						</td>
					</tr>
					<tr style="height:30px">
						<td class="bfont">验证码：</td>
						<td><input class="txtbox" type="text" name="verificationCode"
							id="verificationCode" />
						</td>
						<td><img id="verificationCodeImage"
							src="verification_code.do" height="19"
							onclick="changeVerificationCode()" title="点击换一张"
							style="cursor:pointer;" /> <label id="codeError" class="warning"
							style="display: inline-block;"><s:fielderror
									fieldName="verification_code_error" /> </label><label class="warning"
							id="codeEmpty" style="display: none">验证码为空！</label>
						</td>
					</tr>
				</table>
			</div>
			<hr />
			<div id="bottom" onclick="checkSubmit()" style="cursor:hand"  ></div>
		</div>
	</s:form>
</body>
</html>
