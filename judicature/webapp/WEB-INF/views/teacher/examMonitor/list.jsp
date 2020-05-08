<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>考试监控</title>
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
			$("input[id^='exam_']").click(function(){
				var examId=$(this).attr("id").split("_")[1];
				var classId=$(this).attr("id").split("_")[2];
		 		window.location.href ='${ctx}/monitor/findExamStudentDetail?examId='+examId+'&classId='+classId; 
			 });
			
		});
		
	</script>
</head>
<body>
	 <form:form id="searchForm" modelAttribute="exam" action="${ctx}/monitor/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${examAndClass.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${examAndClass.pageSize}"/>
		<font size="4px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;正在考试的班级列表</font>
	</form:form>
	<form id="examForm" method="post">
		<table  id="treeTable" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th hidden="true" style="text-align: center;">考试ID</th>
					<th style="text-align: center;">班级</th>
					<th hidden="true" style="text-align: center;">班级ID</th>
					<th style="text-align: center;">试卷名称</th>
					<th style="text-align: center;">操作</th>
				</tr>
			</thead>
			<tbody>
			  <c:forEach items="${examAndClass.list}" var="list">
				<tr>
					<td hidden="true" style="text-align: center;">${list.exam.id}</td>
					<td style="text-align: center;">${list.schoolClass.title}</td>
					<td hidden="true" style="text-align: center;">${list.schoolClass.id}</td>
					<td style="text-align: center;">${list.exam.title}</td>
					<td style="text-align: center;">
						<input id='exam_${list.exam.id}_${list.schoolClass.id}' style="background: url(${ctxStatic}/icon/check_1.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="查看"/>
					</td>
				</tr>
			</c:forEach> 
			</tbody>
		</table>
	 </form>
	 <div class="pagination">${examAndClass}</div>
</body>
</html>