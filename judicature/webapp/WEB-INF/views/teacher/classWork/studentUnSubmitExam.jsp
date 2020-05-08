<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>已发布的随堂练习个人详情</title>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<%@include file="/WEB-INF/views/teacher/include/date.jsp" %>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    } 
		function returnList(){
			window.location.href = '${ctx}/classWork/publishList';
		}
	</script>
</head>
<body>
    <form:form id="searchForm" modelAttribute="exam" action="${ctx}/classWork/studentUnSubmitExam?examClassId=${school.id}&examId=${exam.id}" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		班级&nbsp;${school.title}&nbsp;&nbsp;&nbsp;
		作业名称&nbsp;${exam.title}&nbsp;&nbsp;&nbsp;
		<input class="btn tbtn-primary" type="button" value="返回" onclick="returnList();"/>
	</form:form>

	<%-- <sys:message content="${message}"/> --%>
	<form id="examForm" modelAttribute="exam" method="post">
		<table  id="treeTable" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th style="text-align: center;">姓名</th>
					<th style="text-align: center;">学号</th>
					<th style="text-align: center;">电话</th>
				</tr>
			</thead>
			<tbody>
			  <c:forEach items="${page.list}" var="list">
				<tr>
					<td style="text-align: center;">${list.student.name}</td>
					<td style="text-align: center;">${list.student.stdCode}</td>
					<td style="text-align: center;">${list.student.stdPhone}</td>
				</tr>
			</c:forEach> 
			</tbody>
		</table>
	 </form>
	<div class="pagination">${page}</div>
	 
</body>
</html>