<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>更换课程</title>
<link rel="shortcut icon" href="favicon.ico">
	<link rel="stylesheet" href="${ctxStatic}/hgkc/2.3.1/css_default/bootstrap.css">
	<link rel="stylesheet" href="${ctxStatic}/hgkc/css/main.css">
	<script type="text/javascript" src="${ctxStatic}/hgkc/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/js/date.js"></script>
	<script type="text/javascript">
	$(function() {
		$("#datetime").text(getCurDate());
		$("#select_courseId").on(
				"change",
				function() {
					var $this = $('#select_courseId option:selected');
					id = $this.val(), courseId = $this.attr("courseId");
					top.window.location.replace('${ctx}/changeCacheCourseVersion?id=' + id + "&courseId=" + courseId);
				});
		});
	</script>
	<style>
	html{min-width:1220px;}
	.header-top{height: 45px;line-height: 45px;background: #f7f7f7;border-bottom: 1px solid #e0e0e0;}
	.header-top-left{float: left;}
	.header-top-left span{color: #1283c8;font-weight: bold;margin: 0 5px;}
	.header-top-right{float: right;}
	.header-top-right p{float: left;}
	.header-top-right p i{background: url(${ctxStatic}/modules/teacher/img/quit_icon.png) no-repeat center;width: 15px;height: 14px;display: inline-block;margin: 0 5px 0 10px;position: relative;top: 1px;}
	.course-header .c-left{background:none;width:auto;height:auto;}
	.bgr{position:absolute;top:0;z-index:-1;width:100%;margin:0 auto;min-width:1220px;}
	.s-main{background:url(${ctxStatic}/index/images/bg2.jpg) repeat-y top center;height:auto;}
	.course-header,.courlist-content{background:none;}
	.course-header{height:auto;}
	.course-header .c-left img{width:auto;height:auto;margin:65px 0 30px;}
	.courlist-content{height:auto;width:1220px;background:#fff;margin:0 auto;}
	.courlist-content .container{width:1100px ! important;padding:40px 60px;}
	.cour-list li{width:22%;text-align:center;}
	.cour-list li a{width:100%;color:#fff;}
	.cour-list li h2{line-height:45px;font-size:24px;padding:20px 25px 5px;}
	.cour-list li p{font-size:22px;line-height:35px;}
	.m-height{height:auto;min-height:590px;}
	/************************footer*******************************/
	.s-footer{height: 130px;padding-top: 30px;border-top: 1px solid #e0e0e0;}
	.s-footer p{text-align: center;line-height: 30px;}
</style>
</head>
<body>
	<%
		String[] colorStrArr = {"green","magenta","deepblue","lightblue","yellow","yellow","lightblue","deepblue","magenta","green","green","magenta","deepblue","lightblue","yellow","yellow","lightblue","deepblue","magenta","green"};
		int i = 0;
	%>
	<%-- <div class="header-top">
			<div class="container clearfix">
				<p class="header-top-left">欢迎您,<span>${user.name }</span>同学</p>
				<div class="header-top-right">
					<p id="datetime">2016年7月 25号 星期二 12:00</p>
					<p class="qiut"><a href="${ctx }/loginOut"><i></i>退出</a></p>
				</div>
			</div>
		</div> --%>
	<%-- <img src="${ctxStatic}/modules/teacher/img/bg.png" alt="" class="bgr"> --%>
<div class="s-main">
	<div class="course-header">
		<div class="container clearfix">
			<div class="c-left">
				<img src="${ctxStatic}/index/images/s_logo.png" alt="">
			</div>
	
			<%-- <div class="c-right">
				<div class="ri-left">
					<p>
						欢迎您,<span>${user.name }</span>同学
					</p>
					<p>
						今天是：<span id="datetime"></span>
					</p>
				</div>
				<div class="quit">
					<p>
						<a href="${ctx }/loginOut"><i></i>退出</a>
					</p>
				</div>
	
			</div>
		</div> --%>
	</div>
</div>
	<div class="courlist-content">
		<div class="container">
			<div class="m-height">
				<ul class="cour-list clearfix">
					<c:forEach items="${examCountList}" var="obj">
						<li class="<%=colorStrArr[i++] %>">
							<a title="${obj.titleVersionName }" href="${ctx}/changeCacheCourseVersion?courseId=${obj.courseId}" style="text-decoration:none;">
									<h2>${obj.versionName }</h2>
									<p>考试数量：${obj.examCount }</p>
									<p>作业数量：${obj.homeworkCount }</p>
							</a>
						</li>
						<%
							if(i >= 19){
								i = 0;
							}
						%>
					</c:forEach>
				</ul>
			</div>
			
		</div>
	</div>
</div>
	<div class="s-footer">
		<p>© 题库     地址：北京市东城区青年湖南街13号     邮编：100011 </p>
		<p>京ICP备12046843号-12</p>
		<p>京公网安备 11010102002668号</p>
	</div>
</body>
</html>