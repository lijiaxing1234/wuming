<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>个人作业详情</title>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		/* function skip(){
			loading("正在提交，请稍后...");
	    	$("#examPersonDetailForm").attr("action","${ctx}/exam/examTitleList");
	    	$("#examPersonDetailForm").submit();
		} */
		$(document).ready(function() {
			$("input[id^='ExamSubmit_']").click(function(){
				var questionId=$(this).attr("id").split("_")[1];
				var examId=$("#examId").html();
			 	var examClassId=$("#examClassId").html();
		 		window.location.href = '${ctx}/exam/examTitleList?questionId='+questionId+'&examId='+examId+'&examClassId='+examClassId; 
			 }); 
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
    <form:form id="searchForm" action="${ctx}/statistics/list" method="post" class="breadcrumb form-search ">
 		<input id="pageNo" name="pageNo" type="hidden" value="${studentQuestion.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${studentQuestion.pageSize}"/>
		<font id="examId" hidden="true">${exam.id}</font>
		<font id="examClassId" hidden="true">${school.id}</font>
		作业名称：${exam.title}&nbsp;&nbsp;&nbsp;
		班级：${school.title}&nbsp;&nbsp;&nbsp;
		姓名：${student.name}&nbsp;&nbsp;&nbsp;
		状态：${exam.status}&nbsp;&nbsp;&nbsp;
		
	</form:form>

	<%-- <sys:message content="${message}"/> --%>
	<form id="examPersonDetailForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th style="text-align: center;">本班总人数</th>
					<th style="text-align: center;">本班错误率</th>
					<th style="text-align: center;">此学生答题情况</th>
					<th style="text-align: center;" hidden="true">题目ID</th>
					<th style="text-align: center;">题干</th>
					<th style="text-align: center;">学生统计</th>
				</tr>
			</thead>
			<tbody>
			  <c:forEach items="${studentQuestion.list}" var="list">
				<tr>
					<td style="text-align: center;">${list.classPerson}</td>
					<td style="text-align: center;">${list.errorPercent}</td>
					<td style="text-align: center;">
						<c:if test="${list.studentAnswer.isCorrect eq null}">
							错误
						</c:if>
						<c:if test="${list.studentAnswer.isCorrect eq '0'}">
							错误
						</c:if>
						<c:if test="${list.studentAnswer.isCorrect eq '1'}">
							正确
						</c:if>
					</td>
					<td style="text-align: center;" hidden="true">${list.teacherVersionQuestion.id}</td>
					<td style="text-align: center;">${list.teacherVersionQuestion.title}</td>
					<td style="text-align: center;">
						<input id="ExamSubmit_${list.teacherVersionQuestion.id}" class="btn tbtn-primary" type="button" value="查看"/>
					</td>
				</tr>
			</c:forEach> 
			</tbody>
		</table>
	 </form>
	  <div class="pagination">${studentQuestion}</div>
</body>
</html>