<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<c:set var="currentPath" value="${pageContext.request.requestURI }"/>
<c:set var="currentPath" value="${fn:replace(currentPath,fns:getTeacherPath(),'')}" />
<div class="main-left">
		<div class="main-list">
			<div class="main-menu">
				<p <c:if test="${fn:indexOf(currentPath, '') != -1 }">class="active"</c:if> > <a href="${ctx}">首页</a></p>
				<p><a href="javascript:;">题库维护</a></p>
				<p><a href="javascript:;">作业管理</a></p>
				<p><a href="javascript:;">随堂管理</a></p>
				<p><a href="javascript:;">成绩管理</a></p>
				<p <c:if test="${fn:indexOf(currentPath, 'examination/list') != -1 }">class="active"</c:if>><a href="${ctx}/examination/list">试卷管理</a></p>
				<p><a href="javascript:;">互问互答</a></p>
				<p <c:if test="${fn:indexOf(currentPath, 'knowledge/index') != -1 }">class="active"</c:if> ><a href="${ctx}/knowledge/index">知识点管理</a></p>
				<p><a href="javascript:;">学生管理</a></p>
				<p><a href="javascript:;">统计分析</a></p>
				<p><a href="javascript:;">教学资源</a></p>
				<p><a href="javascript:;">文档库管理</a></p>
				<p><a href="javascript:;">模板管理</a></p>
			</div>
		</div>
</div>