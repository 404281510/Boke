<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>播客首页</title>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<link rel="stylesheet" type="text/css" href="css/video.css">
<link rel="stylesheet" type="text/css" href="css/footer.css">

<STYLE>
.joynav UL LI {
	PADDING-BOTTOM: 0px;
	PADDING-LEFT: 6px;
	PADDING-RIGHT: 14px;
	PADDING-TOP: 2px
}

.joynav UL LI.channel {
	PADDING-BOTTOM: 0px;
	PADDING-LEFT: 15px;
	PADDING-RIGHT: 8px;
	PADDING-TOP: 2px
}

.joynav UL LI.end {
	PADDING-RIGHT: 14px !important
}

.joynav UL LI.startjoy {
	Z-INDEX: 1;
	POSITION: relative;
	PADDING-LEFT: 38px;
	MARGIN-LEFT: 0px
}

.joynav UL LI.hotjoy {
	Z-INDEX: 1;
	POSITION: relative;
	PADDING-RIGHT: 38px;
	MARGIN-RIGHT: 0px
}

.joynav UL LI.spech1.channel {
	BACKGROUND: none transparent scroll repeat 0% 0%;
	MARGIN-LEFT: 0px
}

.topBar {
	border-bottom: #d2d2d2 1px dashed;
	margin-bottom: 10px;
	padding: 5px;
	height: 20px
}

.loginBar {
	float: right;
}
</STYLE>

<script type="text/javascript">
	function selectChannel() {
		var channelId = "${channelId}";
		var channels = document.getElementById("channels").childNodes;
		for (i = 0; i < channels.length; i++) {
			if (channels[i].id == "channel_" + channelId) {
				channels[i].className = "cur";
			} else {
				channels[i].className = "";
			}
		}

		var orderId = "${orderId}";
		if (orderId != 1 && orderId != 2 && orderId != 3 && orderId != 4) {
			orderId = 1;
		}
		for (i = 1; i <= 4; i++) {
			if (i == orderId) {
				document.getElementById("order_" + i).className = "cur";
			} else {
				document.getElementById("order_" + i).className = "";
			}
		}
	}
</script>
</head>

<BODY onload="selectChannel();">
	<s:form action="main.do" method="post">
		<DIV class="main">
			<DIV class="topBar">
				<DIV class="loginBar">
					<s:if test="#session.logined_user == null">
						<a href="register_init.do">注册</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
						<a href="login_init.do">登录</a>
					</s:if>
					<s:else>
						<a href="login_logout.do">注销</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
						<a href="upload_init.do">上传视频</a>
					</s:else>
				</DIV>
			</DIV>
			<DIV class="leftnav">
				<DIV class="cate" id="channels">
					<H3>视频分类</H3>
					<s:iterator value="all_channels" var="c">
						<H4 id="channel_${c.id}">
							<EM><A href="#"></A> </EM><SPAN><A
								href="main.do?channelId=${c.id}&orderId=${orderId}">${c.name}</A>
							</SPAN>
						</H4>
					</s:iterator>
				</DIV>
			</DIV>
			<DIV class=content>
				<DIV class=road>${channel.name} 频道</DIV>
				<DIV class=seeti>
					<UL>
						<LI id="order_1"><A
							href="main.do?channelId=${channelId}&orderId=1">最新发布</A></LI>
						<LI id="order_2"><A
							href="main.do?channelId=${channelId}&orderId=2">最多播放</A></LI>
						<LI id="order_3"><A
							href="main.do?channelId=${channelId}&orderId=3">最多评论</A></LI>
						<LI id="order_4"><A
							href="main.do?channelId=${channelId}&orderId=4">最多好评</A></LI>
					</UL>
					<DIV class=seetime>
						发布时间： <SELECT id=periodId onchange=javascript:periodSele();
							name=period>
							<OPTION value=0>全部</OPTION>
							<OPTION value=1>本日</OPTION>
							<OPTION value=2>本周</OPTION>
							<OPTION value=3>本月</OPTION>
						</SELECT>
					</DIV>
				</DIV>
				<DIV class=msglist3>
					<s:iterator value="#page_bean.contents" var="video"
						status="st">
						<s:set name="duration" value="video.duration" />
						<s:set name="minute"
							value="@edu.ahpu.boke.util.CommonUtils@toInt(duration/60)" />
						<s:set name="second"
							value="@edu.ahpu.boke.util.CommonUtils@toInt(duration%60)" />
						<DL <s:if test="#st.index%5==0">class="nomar"</s:if>>
							<DT class=noplay>
								<A href="player_init.do?videoId=${video.id}" target=_blank><IMG
									border=0 alt="${video.title }"
									src='<s:property value="@edu.ahpu.boke.util.Const@UPLOAD_FOLDER"/>/${video.picFileName}.jpg'
									width=120 height=90 /><SPAN class=min1>${minute}:${second}</SPAN>
								</A>
							</DT>
							<DD>
								<H3>
									<A href="player_init.do?videoId=${video.id}" target=_blank>${video.title}</A>
								</H3>
							</DD>
							<DD>
								<EM>发布：<s:date name="#video.uploadTime" format="yyyy-MM-dd" />
								</EM>
							</DD>
							<DD>
								<EM>播放：</EM><SPAN id=playstat_563702>${video.playCount}</SPAN>
							</DD>
							<DD>
								<EM>评论：</EM><SPAN id=cmtstat_563702>${video.commentCount}</SPAN>
							</DD>
						</DL>
					</s:iterator>
				</DIV>
				<DIV class="page">
					<s:if test="#page_bean.currentPage==1">
						<SPAN>首　页</SPAN>
						<SPAN>上一页</SPAN>
					</s:if>
					<s:else>
						<A href="main.do?channelId=${channelId}&orderId=${orderId}&page=1">首　页</A>
						<A href="main.do?channelId=${channelId}&orderId=${orderId}&page=${page_bean.currentPage-1}">上一页</A>
					</s:else>

					<s:iterator begin="#page_bean.startPage"
						end="#page_bean.endPage" var="p">
						<s:if test="#p==#page_bean.currentPage">
							<SPAN class=cur>${p}</SPAN>
						</s:if>
						<s:else>
							<A
								href="main.do?channelId=${channelId}&orderId=${orderId}&page=${p}">${p}</A>
						</s:else>
					</s:iterator>
					<s:if
						test="#page_bean.currentPage==#page_bean.pageCount">
						<SPAN>下一页</SPAN>
						<SPAN>末 页</SPAN>
					</s:if>
					<s:else>
						<A href="main.do?channelId=${channelId}&orderId=${orderId}&page=${page_bean.currentPage+1}">下一页</A>
						<A href="main.do?channelId=${channelId}&orderId=${orderId}&page=${page_bean.pageCount}">末　页</A>
					</s:else>
				</DIV>
			</DIV>
		</DIV>
		<DIV class="footer">
			
		</DIV>
	</s:form>
</BODY>
</HTML>
