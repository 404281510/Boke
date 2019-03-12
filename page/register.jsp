<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>注册</title>
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
		document.getElementById("userNameExist").style.display = "none";
		document.getElementById("passwordEmpty").style.display = "none";
		document.getElementById("password2Error").style.display = "none";
		document.getElementById("codeEmpty").style.display = "none";
		document.getElementById("codeError").style.display = "none";
	}

	function checkSubmit() {
		hideError();

		var m1 = document.getElementById("userNameEmpty");
		var m2 = document.getElementById("passwordEmpty");
		var m3 = document.getElementById("password2Error");
		var m4 = document.getElementById("codeEmpty");

		var u = document.getElementById("userName");
		var p = document.getElementById("password");
		var p2 = document.getElementById("password2");
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

		if (p.value != p2.value) {
			p2.focus();
			m3.style.display = "block";
			return false;
		}

		if (v.value.length <= 0) {
			v.focus();
			m4.style.display = "inline-block";
			return false;
		}
		document.forms[0].submit();
	}

	function showFaceBox() {
		var box = document.getElementById("faceBox");
		var myFace = document.getElementById("myFace");
		if (box.style.display == "none") {
			box.style.display = "block";
			myFace.title = "隐藏可选头像";
		} else {
			box.style.display = "none";
			myFace.title = "显示可选头像";
		}
	}
	
	function changeFace(facePic, faceId) {
		document.getElementById("myFace").src = "images/face/"+facePic;
		document.getElementById("faceId").value=faceId;
		document.getElementById("faceBox").style.display = "none";
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
	height: 280px;
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
	background: url(images/register.png);
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
	height: 130px;
	margin: 10px 20px;
	float: left;
	display: inline;
}

.face {
	margin: 6px;
	height: 50px;
	width: 50px;
	cursor: pointer;
	border: 1px solid;
}
</style>

</head>
<body>
	<s:form name="form1" method="post" action="register_register.do">
		<input type="hidden" name="faceId" id="faceId"
			value="${default_face.id}" />
		<div id="login">
			<div id="title">
				请填写注册信息，若已有账号请<a href="login_init.do"> 点击登录 </a>。
			</div>
			<div style="width:360px;height:200px;margin:0px auto;">
				<table id="loginbox" border="0" cellpadding="0" cellspacing="0">
					<tr style="height:30px">
						<td width="70" class="bfont">用户名：</td>
						<td width="130"><s:textfield cssClass="txtbox"
								name="userName" id="userName" /></td>
						<td><span class="warning" id="userNameEmpty"
							style="display: none">用户名为空！</span><label id="userNameExist"
							class="warning" style="display: inline-block;"><s:fielderror
									fieldName="user_name_exist_error" /> </label>
						</td>
					</tr>
					<tr style="height:30px">
						<td class="bfont">密&nbsp;&nbsp;码：</td>
						<td><s:password cssClass="txtbox" name="password"
								id="password" />
						</td>
						<td><span class="warning" id="passwordEmpty"
							style="display: none">密码为空！</span>
						</td>
					</tr>
					<tr style="height:30px">
						<td class="bfont">确认密码：</td>
						<td><s:password cssClass="txtbox" name="password2"
								id="password2" />
						</td>
						<td><span class="warning" id="password2Error"
							style="display: none">确认密码不一致！</span>
						</td>
					</tr>
					<tr style="height:30px">
						<td class="bfont">验证码：</td>
						<td><s:textfield cssClass="txtbox" name="verificationCode"
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
					<tr style="height:60px;">
						<td class="bfont">头&nbsp;&nbsp;像：</td>
						<td colspan="2"><img class="face" id="myFace"
							src="images/face/${default_face.picFileName}" title="显示可选头像"
							onclick="showFaceBox()" />
						</td>
					</tr>
				</table>
			</div>
			<hr />
			<div id="bottom" onclick="checkSubmit()" style="cursor:hand"></div>
		</div>
		<p />
		<div id="faceBox"
			style="margin: 0px auto; width: 400px; display: none">
			<s:iterator value="all_faces" var="face">
				<span><img class="face" id="face${face.id}"
					src="images/face/${face.picFileName}"
					onclick="changeFace('${face.picFileName}', ${face.id})" /> </span>
			</s:iterator>
		</div>
	</s:form>
</body>
</html>
