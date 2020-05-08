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
		$(document).ready(function() {
			/* $("input[id^='rightOrError_']").click(function(){
				var isCorrect=$(this).attr("id").split("_")[1];
				var studentId=$(this).attr("id").split("_")[2];
				var examId=$(this).attr("id").split("_")[3];
				var questionId=$(this).attr("id").split("_")[4];
				var examClassId=$("#examClassId").val();
				 $.ajax({
					url:"${ctx}/classWork/updateStudentExamDetail",
					type:"post",
					data:{"isCorrect":isCorrect,"questionId":questionId,"examId":examId,"studentId":studentId},
				});
				 $("#examForm").attr("action", "${ctx}/classWork/updateStudentExamDetail?isCorrect="+isCorrect+"&examId="+examId+"&questionId="+questionId+"&studentId="+studentId+"&examClassId="+examClassId);
		    	 $("#examForm").submit(); 
			}); */
			$("#input[id^='exerciseExam_']").click(function(){
				var studentId=$(this).attr("id").split("_")[1];
				var examId=$("#examId").val();
				var examClassId=$("#examClassId").val();
				$("#examForm").attr("action", "${ctx}/teacherStudentAnswer/teacherExamList?examId="+examId+"&studentId="+studentId+"&examClassId="+examClassId);
		    	 $("#examForm").submit(); 
			});
		});
		
		function updateExamClassMark(){
			var examId=$("#examId").val();
			var examClassId=$("#examClassId").val();
			window.location.href="${ctx}/classWork/updateStudentExamStatus?examId="+examId+"&examClassId="+examClassId;
		}
	</script>
</head>
<body>
    <form:form id="searchForm" modelAttribute="exam" action="${ctx}/classWork/publishList" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="examClassId" name="examClassId" type="hidden" value="${school.id}"/>
		<input id="examId" name="examId" type="hidden" value="${exam.id}"/>
		班级：&nbsp;${school.title}&nbsp;&nbsp;&nbsp;
		作业名称：&nbsp;${exam.title}&nbsp;&nbsp;&nbsp;
		<input class="btn tbtn-primary" type="submit" value="返回"/><br/>
	</form:form>

	<form id="examForm" method="post">
		<table  id="treeTable" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th style="text-align: center;">姓名</th>
					<th style="text-align: center;">学号</th>
					<th style="text-align: center;">电话</th>
					<th style="text-align: center; width: 120px;">交作业时间</th>
					<th style="text-align: center;">操作</th>
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
							未参加测试
						</c:if>
							<fmt:formatDate value="${list.submitTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					 </td>
					<td style="text-align: center;">
						<c:if test="${list.submitTime eq null}">
							
						</c:if>
							<input id="exerciseExam_${list.student.id}" style="background: url(${ctxStatic}/icon/check.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" type="button" title="查看答题详情"/>
					 </td>
				</tr>
			</c:forEach> 
			</tbody>
		</table>
	 </form>
	 <div class="pagination">${page}</div>
	 <div align="right">
		 <input id="submitRight" class="btn tbtn-primary" type="button" value="提交" onclick="updateExamClassMark();"/>
	 </div>
</body>
</html>