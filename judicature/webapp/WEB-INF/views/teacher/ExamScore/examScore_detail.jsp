<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>补考成绩</title>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
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
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/examScore/examOnLineScore">考试成绩</a></li>
		<li class="active"><a href="${ctx}/examScore/examScore">补考成绩</a></li>
	</ul>
    <form:form class="breadcrumb form-search ">
    	 作业名称：${exam.title}&nbsp;&nbsp;&nbsp;
		班级：${school.title}
   		<input id="pageNo" name="pageNo" type="hidden" value="${examList.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${examList.pageSize}"/>
		<%-- 作业名称：${exam.title}&nbsp;&nbsp;&nbsp;
		考试时间：&nbsp;&nbsp;&nbsp;
		班级：${school.title}&nbsp;&nbsp;&nbsp;
		<br/>
		成绩维护：主观题成绩，占比重<input type="text" value="" class="input-mini"/>%&nbsp;&nbsp;&nbsp;&nbsp;
				线上成绩，占比重<input type="text" value="" class="input-mini"/>%<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				实操成绩，占比重<input type="text" value="" class="input-mini"/>%&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				平时成绩，占比重<input type="text" value="" class="input-mini"/>% --%>
	</form:form>

	<%-- <sys:message content="${message}"/> --%>
	<form id="examClassDetailForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th  hidden="true" style="text-align: center;">ID</th>
					<th style="text-align: center;">姓名</th>
					<th style="text-align: center;">学号</th>
					<th style="text-align: center;">联系电话</th>
					<th style="text-align: center;">考试成绩</th>
					<th style="text-align: center;">补考成绩</th>
				</tr>
			</thead>
			<tbody>
			 <c:forEach items="${examList.list}" var="list">
				<tr>
					<td  hidden="true" style="text-align: center;">${list.student.id}</td>
					<td style="text-align: center;">${list.student.name}</td>
					<td style="text-align: center;">${list.student.stdCode}</td>
					<td style="text-align: center;">${list.student.stdPhone}</td>
					<td style="text-align: center;">${list.studentAnswer.score}</td>
					<td style="text-align: center;">补考成绩</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	 </form>
	  <div class="pagination">${examList}</div>
</body>
</html>