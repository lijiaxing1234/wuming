<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>
<!-- <div class="course-footer">
   <p class="container">
	 	<a href="javascript:;">版权信息</a>
		<a href="javascript:;">免费条款</a>
		<a href="javascript:;">帮助文件</a>
		<a href="javascript:;">联系我们</a>
   </p>
</div> -->
<style type="text/css">
.m-footer{height: 125px;padding-top: 30px;background: #242833;border-top: 0 none;color: #fff;}
.m-footer p{text-align: center;line-height: 30px;}
.footer-p {height: 125px;padding-top: 30px;background: #242833;border-top: 0 none;color: #fff;}
.footer-p p{text-align:center;font-size:16px;}
.footer-p p a{color:#99a5a9;margin-left:170px;}
.footer-p p a:first-child {margin-left:0;}
.footer-p p i{width:30px;height:20px;background:url(${ctxStatic}/img/about.png) no-repeat;background-size:100%;display:inline-block;position:relative;top:6px;margin-right:10px;}
.footer-p p a:nth-child(2) i{height:22px;background:url(${ctxStatic}/img/tg.png) no-repeat;background-size:100%;}
.footer-p p a:nth-child(3) i{height:25px;background:url(${ctxStatic}/img/dj.png) no-repeat;background-size:100%;}
.footer-p p a:nth-child(4) i{height:27px;background:url(${ctxStatic}/img/fw.png) no-repeat;background-size:100%;}
</style>
<div class="m-footer">
<p>© 题库     地址：北京市东城区青年湖南街13号     邮编：100011 </p>
<p>京ICP备12046843号-12</p>
  <p>京公网安备 11010102002668号</p> 
</div> 
<div class="footer-p">
<p><a href="${prc}/help?tp=aboutUs"  target="_blank"><i></i>关于我们</a><a href="javascript:;"><i></i>在线投稿</a><a href="javascript:;"><i></i>缺题登记</a><a href="${prc}/help?tp=service"  target="_blank"><i></i>售后服务</a></p>
</div>
