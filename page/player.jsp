<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<title>${video.title }</title>
<link href="css/youku.css" type="text/css" rel="stylesheet">
<link href="css/play.css" type="text/css" rel="stylesheet">
</head>

<body class="page_ugc">
	<div id="miniheader" class="s_miniHeader">
		<div class="headerBox">
			<div class="ucenter">
				<div class="userlog" id="userlog">
					<div class="logpre" id="logpre" style="display: block; width: 150">
						<s:if test="#session.logined_user == null">
							<a href="register_init.do">注册</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
						<a href="login_init.do">登录</a>
						</s:if>
						<s:else>
							<a href="login_logout.do">注销</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
						<a href="upload_init.do">上传视频</a>
						</s:else>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="window">
		<div class="screen">
			<div class="s_body">
				<div class="s_main layout_121">
					<div id="vpvideotitlev5_wrap">
						<div id="vpvideotitlev5">
							<div class="base">
								<div class="base_info">
									<div class="guide">
										<div class="crumbs">${video.channel.name} 频道</div>
									</div>
									<h1 class="title">${video.title} </h1>
								</div>
								<div class="clear"></div>
							</div>
							<span style="width: 0px; height: 0px;"> </span>
						</div>
					</div>
					<div class="play_ugc">
						<div class="play_area" id="playBox">
							<div class="playBox" id="playerBox">
								<div class="playArea">
									<div class="player" id="player">
										<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
											codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0"
											width="100%" height="100%">
											<param name="movie" value="FlvPlayer.swf" />
											<param name="quality" value="high" />
											<param name="allowFullScreen" value="true" />
											<param name="FlashVars"
												value="vcastr_file=<s:property value="@edu.ahpu.boke.util.Const@UPLOAD_FOLDER"/>/${video.serverFileName}.flv&IsAutoPlay=1" />
											<embed src="FlvPlayer.swf" allowfullscreen="true"
												flashvars="vcastr_file=<s:property value="@edu.ahpu.boke.util.Const@UPLOAD_FOLDER"/>/${video.serverFileName}.flv&IsAutoPlay=1"
												quality="high"
												pluginspage="http://www.macromedia.com/go/getflashplayer"
												type="application/x-shockwave-flash" width="100%"
												height="100%" />
										</object>
									</div>
								</div>
							</div>
							<div class="clear"></div>
						</div>
					</div>

					<div class="mainCol">
						<div id="vpactionv5_wrap">
							<div id="vpactionv5">
								<div class="interactArea">
									<div class="fns">
										<div class="fn" id="fn_updown">
											<div class="fn2nd" id="fn_up">
												<a href="player_ding.do?videoId=${videoId }"><em
													class="ico_up"></em><em class="stat" id="upVideoTimes">${video.goodCommentCount}</em>
												</a>
											</div>
											<div class="fn2nd" id="fn_down">
												<a href="player_cai.do?videoId=${videoId }"><em
													class="ico_down"></em><em class="stat" id="downVideoTimes">${video.badCommentCount}</em>
												</a>
											</div>
										</div>

										<!-- pc -->
										<div class="fn" id="fn_favorite">
											<div class="handle" id="fn_favo">
												<a href="javascript:void(0);" link="null"
													_hzcharset="hz-4007353">收藏</a>
											</div>
										</div>
										<div class="fn" id="fn_download">
											<div class="handle">
												<a href="javascript:void(0);" link="null">下载</a>
											</div>
										</div>
										<div class="fn" id="fn_stat"
											style="float:right;margin-right:0;">
											<span class="playstat" id="videoTotalPV"> <label>播放:</label>
												<strong class="stat">${video.playCount }</strong> </span>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div id="vpvideoinfov5_wrap">
							<div id="vpvideoinfov5">
								<div class="paikeArea">
									<div class="commentcon">
										<div class="userInfo">
											<div class="userPhoto">
												<img src="images/face/${video.user.face.picFileName }">
											</div>
											<div class="bar">
												<a href="home_init.do?userId=${video.user.id }"
													target="_blank" class="userName">${video.user.name }</a>
											</div>
										</div>
										<div class="con">
											<div class="panel">
												<span class="timestamp"><s:date
														name="#video.uploadTime" format="yyyy-MM-dd" /> </span> <span
													class="via">上传</span>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="commentArea" id="commentArea">
							<div id="commentAreaBox">
								<span style="display:none" id="totalComment">28</span>
								<div class="commentAction loginBefore" id="commentAction"
									style="display:block">
									<s:form action="player_comment.do" method="post">
										<div class="commentTextArea">
											<input type="hidden" name="videoId" value="${video.id }"/>
											<s:textarea cssClass="defaultext" id="content"
												name="commentContent" />
										</div>
										<div class="toolbar" id="toolbar">
											<div class="action">
												<div class="form_btn form_btn_m form_btnsub_m">
													<span class="form_btn_text" onclick="document.forms[0].submit();">发表评论</span>
												</div>
											</div>
											<div class="clear"></div>
											<div class="clear"></div>
										</div>
									</s:form>
								</div>
							</div>
							<div id="videocomment" class="videoComment">
								<div class="qPager" id="pageCommentBar">
									<div class="stat">共 ${video.commentCount } 条评论</div>
								</div>
								<div id="videobodycommentlist">
									<div id="Comments" class="box nBox">
										<div class="body">
											<div class="comments">
												<div id="div_new_comment"></div>
												<s:iterator value="comments" var="comment">
													<div class="comment">
														<div class="commentcon">
															<div class="userPhoto">
																<img src="images/face/${comment.user.face.picFileName }">
															</div>
															<div class="bar">
																<a target="_blank"
																	href="home_init.do?userId=${comment.user.id }">${comment.user.name
																	}</a>
															</div>
															<div class="con">
																<div class="text">
																	<p>
																		${comment.content }<br>
																	</p>
																</div>
																<div class="panel">
																	<span class="timestamp"><s:date
																			name="#comment.time" format="yyyy-MM-dd HH:mm:ss" />
																	</span>
																</div>
															</div>
														</div>
													</div>
												</s:iterator>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>