<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="teacher_blank"/>
<style type="text/css">
 .content-right{ overflow: visible;}
 /* html{ overflow: visible;} */
 	   html{min-width:1200px;}
</style>
<script type="text/javascript">
 (function($){
	$(document).ready(function(){
		/* $("#content-menu li").on("click",function(){			
			$(this).addClass("active").siblings().removeClass("active");
			$('#teacherMainFrame').attr("src",$(this).find("a:first").attr("href"));
		});
		$("#content-menu li:first").trigger("click"); */
		
		
		
	    $(".navlist > li > a").click(function(){
			$(this).parent().addClass("active").siblings().removeClass("active");
			$(this).parent().children("ul").slideToggle();
			$(this).parent().siblings().children("ul").slideUp();
		})
		$(".navlist ul > li").click(function(){
			$(this).addClass("current").siblings().removeClass("current");
			
			 $('#teacherMainFrame').attr("src",$(this).find("a:first").attr("href"));
		})
		
	});
	
 })($);
 
 function  removeJbox(){
	 $("div[id*='jbox-addExam']").each(function(){ //删除jbox
		  var jBoxId=$(this).attr("id");
		  console.log(jBoxId);
		  $(this).remove();
		/*   $.jBox.getIframe(jBoxId).jBox.close(true);  
		   top.$.jBox.close(jBoxId);*/
	  });
 }
 
 function  gotoIndex(){
	 top.window.location.replace("${ctx}");
 }
</script>
<title>首页</title>

</head>
<body>
    
    <div class="main-content">
		<div class="container clearfix">
			<!--   左边内容部分   -->
			<div class="main-left">
			    <div class="img-logo">
  					<img src="${ctxStatic}/img/logo.png" alt="">
  				</div>
				<div class="main-left-con">
					<ul class="navlist">
						<li class="act"><a href="${ctx}/index"  target="teacherMainFrame"><i></i>首页</a></li>
						<li><a href="${ctx}/knowledge/index" target="teacherMainFrame"><i></i>知识点管理</a></li>
						
						<li>
							<a href="javascript:void(0);"><i></i>自建试题<em></em></a>
							<ul>
								<li><a href="${ctx }/questionlib/list" target="teacherMainFrame"><i></i>题库维护</a></li>
								<li><a href="${ctx }/examQues/examQueslist" target="teacherMainFrame"><i></i>试题维护</a></li>
								<%-- <li><a href="${ctx}/doc/list" target="teacherMainFrame"><i></i>文档管理</a></li> --%>
							</ul>
						</li>
						 
						
						<li><a href="${ctx}/classExample/exampleList" target="teacherMainFrame"><i></i>课堂例题</a></li>
						
						<li><a href="${ctx}/classWork/unpublishList" target="teacherMainFrame"><i></i>随堂评测</a></li>
						
						<%-- <li><a href="javascript:void(0);" target="teacherMainFrame"><i></i>随堂评测<em></em></a> <!-- ${ctx}/classWork/unpublishList 随堂管理 -->
							<ul>
							   <li><a href="${ctx}/classWork/publishList" target="teacherMainFrame"><i></i>已完成</a></li>
							   <li><a href="${ctx}/classWork/unpublishList" target="teacherMainFrame"><i></i>未完成</a></li>
								
							</ul>
						</li> --%>
						
						<li><a id='homework' href="${ctx}/exam/examListUnPublish" target="teacherMainFrame"><i></i>课后作业</a></li>
						<%-- <li><a href="javascript:void(0);" target="teacherMainFrame"><i></i>课后作业<em></em></a> <!-- ${ctx}/exam/examListUnPublish 作业管理 -->
							<ul>
							   <li><a href="${ctx}/exam/examList" target="teacherMainFrame"><i></i>已完成</a></li>
							   <li><a href="${ctx}/exam/examListUnPublish" target="teacherMainFrame"><i></i>未完成</a></li>
								
							</ul>
						</li> --%>
						<li><a id='examManage' href="javascript:void(0);" target="teacherMainFrame"><i></i>考试管理<em></em></a> <!-- ${ctx}/examScore/examScoreList" 成绩管理 -->
							<ul>
							   <%-- <li><a href="${ctx}/examScore/examOnLineScoreList" target="teacherMainFrame"><i></i>线上</a></li>
							   <li><a href="${ctx}/examScore/examScoreList" target="teacherMainFrame"><i></i>线下</a></li> --%>
							    <li><a id='onLineExam' href="${ctx}/testPaper/testPaperOnLineListUnSubmit" target="teacherMainFrame"><i></i>线上</a></li>
							   <li><a href="${ctx}/testPaper/testPaperListUnSubmit" target="teacherMainFrame"><i></i>线下</a></li>
							   <li><a href="${ctx}/monitor/list" target="teacherMainFrame"><i></i>考试监控</a></li>
							</ul>
						</li>
						
						<li><a href="javascript:void(0);" target="teacherMainFrame"><i></i>教学诊断<em></em></a> <!--  <li><a href="${ctx}/statistics/list" target="teacherMainFrame"><i></i>统计分析</a></li> -->
							<ul>
							   <li><a href="${ctx}/knowledge/statistics" target="teacherMainFrame"><i></i>知识点统计</a></li>
							   <li><a href="${ctx}/statistics/list" target="teacherMainFrame"><i></i>班级-学生统计</a></li>
							   <!-- <li><a href="javascript:void(0);" target="teacherMainFrame"><i></i>过程分数以及占比、成绩的报告</a></li> -->
							</ul>
						</li>
						
						
						<%-- <li><a href="${ctx}/testPaper/testPaperList" target="teacherMainFrame"><i></i>试卷管理</a></li>
						<li><a href="${ctx}/teacherStudentAnswer/examOnLineList" target="teacherMainFrame"><i></i>教师判卷</a></li> --%>
						
						 <li><a href="${ctx}/template/list" target="teacherMainFrame"><i></i>快速评测</a></li>
						 <li><a href="${ctx}/sources/index" target="teacherMainFrame"><i></i>教学资源</a></li>
						 <li><a href="${ctx}/examination/student" target="teacherMainFrame"><i></i>学生评语</a></li>
						 <li><a id="thuwenhuda" href="${ctx }/bbs/queslist" target="teacherMainFrame"><i></i>互问互答</a></li>
					</ul>
					<script type="text/javascript"  src="${ctxStatic }/modules/teacher/js/zUI.js"></script>
				</div>
			</div>
			<div class="content-right">
			        <c:import url="${ctx }/header" />
					<iframe id="teacherMainFrame" name="teacherMainFrame" src="${ctx}/index" style="overflow: hidden;" scrolling="yes" frameborder="no" width="100%" height="775px"></iframe>
			</div>
    	</div>
    	
    </div>
	<%-- <div class="cour-content container clearfix">	
		<div class="content-left">
			<div class="content-list">
					<ul class="content-menu" id="content-menu">
						<li class="active"><a href="${ctx}/index"  target="teacherMainFrame"><i></i>首页</a></li>
						<li><a href="${ctx}/knowledge/index" target="teacherMainFrame"><i></i>知识点管理</a></li>
						
						<li>
							<a href="javascript:void(0);">自建试题</a>
							<ul>
								<li><a href="${ctx }/questionlib/list" target="teacherMainFrame"><i></i>题库维护</a></li>
								<li><a href="${ctx }/examQues/examQueslist" target="teacherMainFrame"><i></i>试题维护</a></li>
								<li><a href="${ctx}/doc/list" target="teacherMainFrame"><i></i>文档管理</a></li>
							</ul>
						</li>
						 
						
						<li><a href="${ctx}/classExample/exampleList" target="teacherMainFrame"><i></i>课堂例题</a></li>
						<li><a href="javascript:void(0);" target="teacherMainFrame"><i></i>随堂评测</a> <!-- ${ctx}/classWork/unpublishList 随堂管理 -->
							<ul>
							   <li><a href="javascript:void(0);" target="teacherMainFrame"><i></i>已完成</a></li>
							   <li><a href="javascript:void(0);" target="teacherMainFrame"><i></i>未完成</a></li>
								
							</ul>
						</li>
						<li><a href="javascript:void(0);" target="teacherMainFrame"><i></i>作业管理</a> <!-- ${ctx}/exam/examListUnPublish 作业管理 -->
							<ul>
							   <li><a href="javascript:void(0);" target="teacherMainFrame"><i></i>已完成</a></li>
							   <li><a href="javascript:void(0);" target="teacherMainFrame"><i></i>未完成</a></li>
								
							</ul>
						</li>
						<li><a href="javascript:void(0);" target="teacherMainFrame"><i></i>考试管理</a> <!-- ${ctx}/examScore/examScoreList" 成绩管理 -->
							<ul>
							   <li><a href="javascript:void(0);" target="teacherMainFrame"><i></i>线上</a></li>
							   <li><a href="javascript:void(0);" target="teacherMainFrame"><i></i>线下</a></li>
							   <li><a href="${ctx}/monitor/list"" target="teacherMainFrame"><i></i>考试监控</a></li>
							</ul>
						</li>
						
						<li><a href="javascript:void(0);" target="teacherMainFrame"><i></i>统计分析</a> <!--  <li><a href="${ctx}/statistics/list" target="teacherMainFrame"><i></i>统计分析</a></li> -->
							<ul>
							   <li><a href="${ctx}/knowledge/statistics" target="teacherMainFrame"><i></i>知识点统计</a></li>
							   <li><a href="javascript:void(0);" target="teacherMainFrame"><i></i>班级-学生统计</a></li>
							   <li><a href="javascript:void(0);" target="teacherMainFrame"><i></i>过程分数以及占比、成绩的报告</a></li>
							</ul>
						</li>
						
						
						<li><a href="${ctx}/testPaper/testPaperList" target="teacherMainFrame"><i></i>试卷管理</a></li>
						<li><a href="${ctx}/teacherStudentAnswer/examOnLineList" target="teacherMainFrame"><i></i>教师判卷</a></li>
						
						 <li><a href="${ctx}/template/list" target="teacherMainFrame"><i></i>快速评测</a></li>
						 <li><a href="${ctx}/sources/index" target="teacherMainFrame"><i></i>教学资源</a></li>
						 <li><a href="${ctx}/examination/student" target="teacherMainFrame"><i></i>学生评语</a></li>
						 <li><a href="${ctx }/bbs/queslist" target="teacherMainFrame"><i></i>互问互答</a></li>
					</ul>
			</div>
		</div>
		<div class="content-right">
		   <iframe id="teacherMainFrame" name="teacherMainFrame" src="" style="overflow: scroll;" scrolling="yes" frameborder="no" width="100%" height="100%"></iframe>
		</div>
	</div> --%>
    <%@include file="/WEB-INF/views/teacher/include/footer.jsp" %>		
	<script type="text/javascript">
	   (function($){
		     function wSize(){
		    	 try{
				   var container = $("div.main-content > div.container"),
				       container_left=container.find("div.main-left:first");
				       container_right=container.find("div.content-right:first");
				       container_right.width(parseInt(container.width())-parseInt(container_left.width())-50);
				       
		    	 }catch(e){
		    		 
		    	 }
		     }
	        $(window).resize(function(){
	    		wSize();
	    	});
	        wSize();
	   })(jQuery);
	</script>
</body>
</html>