<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传视频文件</title>
<link type="text/css" rel="stylesheet" href="css/youku.css">
<link type="text/css" rel="stylesheet" href="css/upload.css">
<script type="text/javascript">
	function upload() {
		document.forms[0].submit();
	}
</script>
</head>
<body>
	<div align="center">
		<div class="s_main" style="background:#F6F6F6;">
			<div class="uploadTitle">
				<h3>上传视频</h3>
			</div>
			<div class="space20"></div>
			<div id="upload-container">
				<div class="videoInfo">
					<s:form action="upload_upload.do" method="post" enctype="multipart/form-data">
						<div class="space20"></div>
						<div class="items">
							<span class="f_14">文件：</span>
							<s:file cssClass="form_input" name="video" cssStyle="height: 30px" />
						</div>
						<div class="space20"></div>
						<div class="items">
							<span class="f_14">标题：</span>
							<s:textfield cssClass="form_input" name="title"
								cssStyle="height: 30px" />
						</div>
						<div class="space20"></div>
						<div class="items">
							<span class="f_14">简介：</span>
							<s:textarea cssClass="form_input" name="description" />
						</div>
						<div class="space20"></div>
						<div class="items">
							<span class="f_14">频道：</span>
							<s:select cssClass="form_input" name="channelId"
								list="all_channels" listKey="id" listValue="name"
								cssStyle="width: 200px; height: 30px" />
						</div>
						<div class="space20"></div>
						<div class="items">
							<span class="f_14">标签：</span>
							<s:textfield cssClass="form_input" name="tags"
								cssStyle="height: 30px" />
						</div>
						<div class="space20"></div>
						<div style="border: 1px dashed;"></div>
						<span style="float: right; margin-right: 20px; margin-top: 20px"><img
							src="images/upload.png" style="cursor: pointer;"
							onclick="upload();" />
						</span>
						<div class="space20"></div>
					</s:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>