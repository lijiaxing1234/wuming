<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>作业列表</title>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<%@include file="/WEB-INF/views/teacher/include/date.jsp" %>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	 <form:form id="searchForm" modelAttribute="exam" action="${ctx}/monitor/findExamStudentDetail" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="classId" name="classId" type="hidden" value="${school.id}"/>
		<input id="examId" name="examId" type="hidden" value="${exam.id}"/>
		考试名称：${exam.title}&nbsp;&nbsp;&nbsp;&nbsp;
		班级名称：${school.title}&nbsp;&nbsp;&nbsp;
		开始时间：<fmt:formatDate value="${exam.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;&nbsp;&nbsp;
		结束时间：<fmt:formatDate value="${exam.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
		<br/><br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		全班共 ${totalPerson} 人&nbsp;&nbsp;&nbsp;
		 ${answerPersons} 人参加考试&nbsp;&nbsp;&nbsp;
		${submitPersons}人已交卷&nbsp;&nbsp;&nbsp;
		${unSubmitPersons}人未交卷&nbsp;&nbsp;&nbsp;
		<a href="${ctx}/monitor/list" class="btn tbtn-primary">返回</a>
	</form:form>
	<form id="examForm" method="post">
		<table  id="treeTable" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th style="text-align: center;">姓名</th>
					<th style="text-align: center;">学号</th>
					<th style="text-align: center;">电话</th>
					<th style="text-align: center;">是否交卷</th>
					<th style="text-align: center; width: 120px; ">交卷时间</th>
				</tr>
			</thead>
			<tbody>
			  <c:forEach items="${page.list}" var="list">
				<tr>
					<td style="text-align: center;">${list.student.name}</td>
					<td style="text-align: center;">${list.student.stdCode}</td>
					<td style="text-align: center;">${list.student.stdPhone}</td>
					<td style="text-align: center;">
						<c:if test="${list.submitTime eq null}">
							否
						</c:if>
						<c:if test="${not empty list.submitTime}">
							是
						</c:if>
					</td>
					<td style="text-align: center;">
						<fmt:formatDate value="${list.submitTime}" pattern="yyyy-MM-dd HH:mm:ss "/>
					</td>
				</tr>
			</c:forEach> 
			</tbody>
		</table>
	 </form>
	 <div class="pagination">${page}</div>
</body>
</html>