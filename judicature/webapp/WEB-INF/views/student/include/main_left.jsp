<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>
<%--
<c:set var="currentPath" value="${pageContext.request.requestURI }"/>
<c:set var="currentPath" value="${fn:replace(currentPath,fns:getTeacherPath(),'')}" />
 --%>
 <style>
.content-left{overflow: hidden;height:825px;}
.content-left .content-logo > img{margin: 20px auto;}
.content-left .content-list .content-menu{overflow-x:hidden;overflow-y: scroll;width: 127%;}
.content-list .content-menu{border: 0 none;height:717px;}
.content-left,.content-left .content-menu li{background: #242833;}
.content-list .content-menu > li a,
.content-list .content-menu li.act > a,
.content-list .content-menu li.active > a,
.content-list .content-menu > li ul li a{color: #a6a5ab;}
.content-list .content-menu > li.act,
.content-list .content-menu > li.active{background: #20242d}
.content-list .content-menu > li.active > a,
.content-list .content-menu > li.act > a{border-left: 6px solid #f3bb4a;}
.content-list .content-menu > li ul li{background: #20242d;border-top: 0 none;}
.content-list .content-menu > li ul li.current{background: #1c1f26;}
.content-list .content-menu > li ul li.current i{background: #989c9f;}
.content-list .content-menu > li ul li.current a{color: #fff;}
.content-list .content-menu > li{border-bottom: 0 none;width: auto;}
.content-list {border:0 none;}
 </style>
<div class="content-left">
	<div class="content-logo">
  		<img src="${ctxStatic}/img/logo.png" alt="">
	</div>
	<div class="content-list" >
			<%--<ul class="content-menu">
				
				<li <c:if test="${fn:indexOf(currentPath, '/index') != -1 }">class="active"</c:if> > <a href="${ctx}/index"><i></i>首页</a></li>
				<li <c:if test="${fn:indexOf(currentPath, 'studentExam/examList') != -1 }">class="active"</c:if>><a href="${ctx}/studentExam/examList?examType=5"><i></i>我的考试</a></li>
				<li <c:if test="${fn:indexOf(currentPath, 'studentExam/quizList') != -1 }">class="active"</c:if>><a href="${ctx}/studentExam/quizList?examType=1"><i></i>随堂练习</a></li>
				<li <c:if test="${fn:indexOf(currentPath, 'studentExam/homeWorkList') != -1 }">class="active"</c:if>><a href="${ctx}/studentExam/homeWorkList?examType=3"><i></i>课后作业</a></li>
				<li <c:if test="${fn:indexOf(currentPath, 'studentExam/exampleList') != -1 }">class="active"</c:if>><a href="${ctx}/studentExam/exampleList?examType=4"><i></i>课堂例题</a></li>
				<li <c:if test="${fn:indexOf(currentPath, 'studentExam/wrongQuestionsList') != -1 }">class="active"</c:if>><a href="${ctx}/studentExam/wrongQuestionsList"><i></i>错题集</a></li>
				<li <c:if test="${fn:indexOf(currentPath, 'studentExam/myQuestionLib') != -1 }">class="active"</c:if>><a href="${ctx}/studentExam/myQuestionLib"><i></i>我的题库</a></li>
				<li <c:if test="${fn:indexOf(currentPath, 'studentExam/myReview') != -1 }">class="active"</c:if>><a href="${ctx}/studentExam/myReview"><i></i>我的评语</a></li>	
				<li <c:if test="${fn:indexOf(currentPath, 'studentNews/questionList') != -1 }">class="active"</c:if>><a href="${ctx}/studentNews/questionList" ><i></i>互问互答</a></li>	
				<li <c:if test="${fn:indexOf(currentPath, 'studentNews/myGrade') != -1 }">class="active"</c:if>><a href="${ctx}/studentNews/myGrade" ><i></i>我的成绩</a></li>	
				<li <c:if test="${fn:indexOf(currentPath, 'studentNews/gradeChange') != -1 }">class="active"</c:if>><a href="${ctx}/studentNews/gradeChange" ><i></i>成绩统计</a></li>	
				<li <c:if test="${fn:indexOf(currentPath, 'studentNews/sourcesList') != -1 }">class="active"</c:if>><a href="${ctx}/studentNews/sourcesList" ><i></i>学习资源</a></li>	
			</ul>
			 --%>
			 <ul class="content-menu" id="content-menu">
				<li class="active"> <a href="${ctx}/index" target="studentMainFrame" dataUrl="${ctx}/index" ><i></i>首页</a></li>
				<li><a id="left_exam" href="${ctx}/studentExam/examList?examType=5" target="studentMainFrame" dataUrl="${ctx}/studentExam/examList" ><i></i>我的考试</a></li>
				<li><a id="left_quiz" href="${ctx}/studentExam/quizList?examType=1" target="studentMainFrame" dataUrl="${ctx}/studentExam/quizList" ><i></i>随堂练习</a></li>
				<li><a id="left_homework" href="${ctx}/studentExam/homeWorkList?examType=3" target="studentMainFrame" dataUrl="${ctx}/studentExam/homeWorkList" ><i></i>课后作业</a></li>
				<li><a id="left_example" href="${ctx}/studentExam/exampleList?examType=4" target="studentMainFrame" dataUrl="${ctx}/studentExam/exampleList" ><i></i>课堂例题</a></li>
				<li><a href="${ctx}/studentExam/wrongQuestionsList" target="studentMainFrame" dataUrl="${ctx}/studentExam/wrongQuestionsList" ><i></i>错题集</a></li>
				<li><a href="${ctx}/studentExam/myQuestionLib" target="studentMainFrame" dataUrl="${ctx}/studentExam/myQuestionLib" ><i></i>我的题库</a></li>
				<li><a href="${ctx}/studentExam/myReview" target="studentMainFrame" dataUrl="${ctx}/studentExam/myReview" ><i></i>我的评语</a></li>	
				<li><a id="aaaa" href="${ctx}/studentNews/questionList" target="studentMainFrame" dataUrl="${ctx}/studentNews/questionList" ><i></i>互问互答</a></li>	
				<li><a href="${ctx}/studentNews/myGrade" target="studentMainFrame" dataUrl="${ctx}/studentNews/myGrade"  ><i></i>我的成绩</a></li>	
				<li><a href="${ctx}/studentNews/gradeChange" target="studentMainFrame" dataUrl="${ctx}/studentNews/gradeChange" ><i></i>成绩统计</a></li>	
				<li><a href="${ctx}/studentNews/sourcesList" target="studentMainFrame" dataUrl="${ctx}/studentNews/sourcesList" ><i></i>学习资源</a></li>	
			</ul>
	</div>
</div>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery.query.js"></script>
<script type="text/javascript">
 (function($){
	$(document).ready(function(){
		$("#content-menu li").on("click",function(){			
			$(this).addClass("active").siblings().removeClass("active");
			/* console.log($(this).find("a:first").attr("href")); */
			
		    var parameter=$.query.load($(this).find("a:first").attr("href")).set("message",$("#pageMsg").val());
			//console.log(parameter);
			$('#studentMainFrame').attr("src",$(this).find("a:first").attr("dataUrl")+parameter);
			$("#pageMsg").val("");
		});
		$("#content-menu li:first").trigger("click");
	});
 })($);
</script>