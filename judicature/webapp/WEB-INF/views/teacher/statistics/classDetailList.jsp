<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>班级试卷详情</title>
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
				var examId=$("#examId").val();
				var examClassId=$("#examClassId").val();
		 		/* window.location.href = '${ctx}/statistics/questionList?studentId='+studentId+'&examId='+examId+'&examClassId='+examClassId;  */
				window.location.href = '${ctx}/statistics/personList?studentId='+studentId+'&id='+examId+'&examClass.id='+examClassId; 
			 }); 
		});
		 function examClassList(){
			 window.location.href = "${ctx}/statistics/list";
		 }
	</script>
</head>
<body>
    <form:form id="searchForm" modelAttribute="exam" action="${ctx}/statistics/studentList" method="post" class="breadcrumb form-search ">
   		<input id="pageNo" name="pageNo" type="hidden" value="${student.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${student.pageSize}"/>
		<input id="examId" name="id" type="hidden" value="${exam.id}"/>
		<input id="examClassId" name="examClass.id" type="hidden" value="${school.id}"/>
		作业名称：${exam.title}&nbsp;&nbsp;&nbsp;
		班级：${school.title}&nbsp;&nbsp;&nbsp;
		<input class="btn tbtn-primary" type="button" value="返回" onclick="examClassList();">
	</form:form>

	<form id="examClassDetailForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th  hidden="true" style="text-align: center;">ID</th>
					<th style="text-align: center;">姓名</th>
					<th style="text-align: center;">学号</th>
					<th style="text-align: center;">电话</th>
					<th style="text-align: center; width: 120px; ">交作业时间</th>
					<c:if test="${exam.examType eq '5' }">
						<th style="text-align: center;">正确率</th>
						<th style="text-align: center;">总题数</th>
						<th style="text-align: center;">答对数</th>
						<th style="text-align: center;">错误数</th>
					</c:if>
					<th style="text-align: center;">操作</th>
				</tr>
			</thead>
			<tbody>
			 <c:forEach items="${student.list}" var="list">
				<tr>
					<td  hidden="true" style="text-align: center;">${list.student.id}</td>
					<td style="text-align: center;">${list.student.name}</td>
					<td style="text-align: center;">${list.student.stdCode}</td>
					<td style="text-align: center;">${list.student.stdPhone}</td>
					<td style="text-align: center;">
						<c:if test="${list.submitTime eq null}">
							未提交
						</c:if>
						<c:if test="${!empty list.submitTime}">
							<fmt:formatDate value="${list.submitTime}" pattern="yyyy-MM-dd HH:mm:ss"/>	
						</c:if>
					</td>
					<c:if test="${exam.examType eq '5' }">
						<td style="text-align: center;">${list.rightPercent}</td>
						<td style="text-align: center;">${list.totalTitle}</td>
						<td style="text-align: center;">${list.totalRight}</td>
						<td style="text-align: center;">${list.totalError}</td>
					</c:if>
					<td style="text-align: center;">
						<input id="classExam_${list.student.id}" style="background: url(${ctxStatic}/icon/check_1.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="查看"/>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	 </form>
	  <div class="pagination">${student}</div>
</body>
</html>