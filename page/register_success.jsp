<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
<head>
<title>注册成功</title>
<script language="javascript" type="text/javascript">
	var second;

	function go() {
		if (navigator.appName.indexOf("Explorer") > -1) {
			second = document.getElementById("totalSecond").innerText;
		} else {
			second = document.getElementById("totalSecond").textContent;
		}
		setInterval("redirect()", 1000);
	}

	function redirect() {
		if (second <= 0) {
			location.href = "main.do";
		} else {
			if (navigator.appName.indexOf("Explorer") > -1) {
				document.getElementById("totalSecond").innerText = --second;
			} else {
				document.getElementById("totalSecond").textContent = --second;
			}
		}
	}
</script>
</head>

<body onload="go();">
	<h1>
		注册成功，<span style="color: #FF0000" id="totalSecond">3</span> 秒后转向首页。
	</h1>
</body>
</html>

