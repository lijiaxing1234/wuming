<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>已发布的随堂练习班级详情</title>
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
			$("input[id^='ExamSubmit_']").click(function(){
				var examId=$("#examId").val();
				var examClassId=$("#examClassId").val();
				var questionId=$(this).attr("id").split("_")[1];
				$("#examForm").attr("action", "${ctx}/classWork/examWorkClassDetail?examClassId="+examClassId+"&examId="+examId+"&questionId="+questionId);
		    	$("#examForm").submit();  
			
			});
			
		});
	</script>
</head>
<body>
    <form:form id="searchForm" modelAttribute="exam" action="${ctx}/classWork/examDetail" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="examId" name="examId" type="hidden" value="${exam.id}"/>
		<input id="examClassId" name="examClassId" type="hidden" value="${school.id}"/>
		班级：&nbsp;${school.title}&nbsp;&nbsp;&nbsp;
		作业名称：&nbsp;${exam.title}&nbsp;&nbsp;&nbsp;
		<a class="btn tbtn-primary" href="${ctx}/classWork/publishList">返回</a>
	</form:form>

	<%-- <sys:message content="${message}"/> --%>
	<form id="examForm" modelAttribute="exam" method="post">
		<table  id="treeTable" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th style="text-align: center;">题号</th>
					<th style="text-align: center;" hidden="true">题目id</th>
					<th style="text-align: center;">题干</th>
					<th style="text-align: center;">正确答案</th>
					<th style="text-align: center;">正确率</th>
					<th style="text-align: center;">答错人员</th>
				</tr>
			</thead>
			<tbody>
			  <c:forEach items="${page.list}" var="list" varStatus="index">
				<tr>
					
					<td style="text-align: center;">${index.index+1}</td>
					<td style="text-align: center;" hidden="true">${list.id}</td>
					<td style="text-align: center;">${list.title}</td>
					<td style="text-align: center;">${list.answer}</td>
					<td style="text-align: center;">${list.rightPercent}</td>
					<td style="text-align: center;">
						<input id="ExamSubmit_${list.id}" style="background: url(${ctxStatic}/icon/check.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" title="查看"/>
					</td>
				</tr>
			</c:forEach> 
			</tbody>
		</table>
	 </form>
	 <div class="pagination">${page}</div>
	 
</body>
</html>