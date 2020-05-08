<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<c:set var="currentPath" value="${pageContext.request.requestURI }"/>
<c:set var="currentPath" value="${fn:substring(currentPath,fn:length(fns:getTeacherPath()),fn:length(currentPath))}" />
<div class="content-left">
	<div class="content-list">
			<ul class="content-menu">
				<li <c:if test="${empty currentPath }">class="active"</c:if> > <a href="${ctx}"><i></i>首页</a></li>
				<li <c:if test="${fn:indexOf(currentPath, 'knowledge/index') != -1 }">class="active"</c:if> ><a href="${ctx}/knowledge/index"><i></i>知识点管理</a></li>
				
				<li <c:if test="${fn:indexOf(currentPath, 'questionlib') != -1 }">class="active"</c:if>><a href="${ctx }/questionlib/list"><i></i>题库维护</a></li>
				<li <c:if test="${fn:indexOf(currentPath, 'examQues/examQueslist') != -1 }">class="active"</c:if>><a href="${ctx }/examQues/examQueslist"><i></i>试题维护</a></li>
				 
				
				<li <c:if test="${fn:indexOf(currentPath, 'exam/examList') != -1 }">class="active"</c:if>><a href="${ctx}/exam/examList"><i></i>作业管理</a></li>
				<li <c:if test="${fn:indexOf(currentPath, 'classWork/unpublishList') != -1 }">class="active"</c:if>><a href="${ctx}/classWork/unpublishList"><i></i>随堂管理</a></li>
				<!-- <li><a href="javascript:;">成绩管理</a></li> -->
				<li <c:if test="${fn:indexOf(currentPath, 'examScore/examScoreList') != -1 }">class="active"</c:if>><a href="${ctx}/examScore/examScoreList"><i></i>成绩管理</a></li>
				<li <c:if test="${fn:indexOf(currentPath, 'testPaper/testPaperList') != -1 }">class="active"</c:if>><a href="${ctx}/testPaper/testPaperList"><i></i>试卷管理</a></li>
				<li <c:if test="${fn:indexOf(currentPath, 'teacherStudentAnswer/examList') != -1 }">class="active"</c:if>><a href="${ctx}/teacherStudentAnswer/examList"><i></i>教师判卷</a></li>
				
				
				<li <c:if test="${fn:indexOf(currentPath, 'knowledge/statistics') != -1 }">class="active"</c:if> ><a href="${ctx}/knowledge/statistics"><i></i>知识点统计</a></li>
				<li <c:if test="${fn:indexOf(currentPath, 'examination/student') != -1 }">class="active"</c:if>><a href="${ctx}/examination/student"><i></i>学生管理</a></li>
				
				
				<li <c:if test="${fn:indexOf(currentPath, 'bbs/queslist') != -1 }">class="active"</c:if>><a href="${ctx }/bbs/queslist"><i></i>互问互答</a></li>
				<li <c:if test="${fn:indexOf(currentPath, 'sources/index') != -1 }">class="active"</c:if> ><a href="${ctx}/sources/index"><i></i>教学资源</a></li>
				 
				 <li <c:if test="${fn:indexOf(currentPath, 'monitor/list') != -1 }">class="active"</c:if> ><a href="${ctx}/monitor/list"><i></i>考试监控</a></li>
				 <li <c:if test="${fn:indexOf(currentPath, 'statistics/list') != -1 }">class="active"</c:if> ><a href="${ctx}/statistics/list"><i></i>统计分析</a></li>
				 <li <c:if test="${fn:indexOf(currentPath, 'doc/list') != -1 }">class="active"</c:if> ><a href="${ctx}/doc/list"><i></i>文档管理</a></li>
				 <li <c:if test="${fn:indexOf(currentPath, 'template/list') != -1 }">class="active"</c:if> ><a href="${ctx}/template/list"><i></i>模板管理</a></li>
			</ul>
	</div>
</div>