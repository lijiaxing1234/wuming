<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>班级作业详情</title>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    } 
		 $(document).ready(function() {
			$("input[id^='classExam_']").click(function(){
				var studentId=$(this).attr("id").split("_")[1];
				var examId=$("#examId").html();
				var examClassId=$("#examClassId").html();
				var status=$("#status").val();
				/* return confirmx("系统正在为您判客观题，您要现在开始判主观题吗？",function(){ */
		 		window.location.href = '${ctx}/teacherStudentAnswer/teacherExamList?studentId='+studentId+'&examId='+examId+'&examClassId='+examClassId+'&status='+status; 
				/* }); */
			}); 
		});
		 function returnBack(){
			var status=$("#status").val();
			if(status=="online"){
				window.location.href="${ctx}/testPaper/testPaperOnLineList";
			}
			if(status=="no"){
				window.location.href="${ctx}/testPaper/testPaperList";
			}
			if(status=="homeWork"){
				window.location.href="${ctx}/exam/examList";
			}
			if(status=="exerciseExam"){
				window.location.href="${ctx}/classWork/publishList";
			}
		} 
		function updateStatus(){
			var examId=$("#examId").html();
			var examClassId=$("#examClassId").html();
			var status=$("#status").val();
			if(status=="exerciseExam"){
				window.location.href="${ctx}/classWork/updateStudentExamStatus?examId="+examId+"&examClassId="+examClassId;
			}else{
				window.location.href='${ctx}/teacherStudentAnswer/updateExamClass?examId='+examId+'&examClassId='+examClassId+'&status='+status;
			}
		}
	</script>
</head>
<body>
    <sys:message content="${message}" />
    <form:form id="searchForm" action="${ctx}/teacherStudentAnswer/examStudentList?examId=${exam.id}&examClassId=${school.id}" method="post"  class="breadcrumb form-search ">
   		<input id="pageNo" name="pageNo" type="hidden" value="${examList.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${examList.pageSize}"/>
		<font id="examId" hidden="true">${exam.id}</font>
		<font id="examClassId" hidden="true">${school.id}</font>
		<input id="status" name="status" type="hidden" value="${status}"/>
		作业名称：${exam.title}&nbsp;&nbsp;&nbsp;
		班级：${school.title}&nbsp;&nbsp;&nbsp;
		状态：已完成&nbsp;&nbsp;&nbsp;
		<input id="btnTestPaperSubmit" class="btn tbtn-primary" type="button" value="返回" onclick="returnBack();"/>
		<input id="btnUpdate" class="btn tbtn-primary" type="button" value="已判完班内所有人的试卷" onClick="updateStatus();"/>
	</form:form>

	
	<form id="examClassDetailForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th  hidden="true" style="text-align: center;">ID</th>
					<th style="text-align: center;">姓名</th>
					<th style="text-align: center;">学号</th>
					<th style="text-align: center; width: 20px;">操作</th>
				</tr>
			</thead>
			<tbody>
			 <c:forEach items="${student.list}" var="list">
				<tr>
					<td  hidden="true" style="text-align: center;">${list.id}</td>
					<td style="text-align: center;">${list.name}</td>
					<td style="text-align: center;">${list.stdCode}</td>
					<td style="text-align: center;">
						<c:if test="${list.status eq '1' }">
							已判卷
						</c:if>
						<c:if test="${empty list.status }">
							<input id="classExam_${list.id}" style="background: url(${ctxStatic}/icon/jq.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" type="button" title="判卷"/>
						</c:if>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	 </form>
	  <div class="pagination">${student}</div>
</body>
</html>