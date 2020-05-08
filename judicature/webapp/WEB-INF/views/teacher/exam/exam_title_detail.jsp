<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>个人作业详情</title>
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
    <form:form id="searchForm" action="${ctx}/exam/examTitleList" method="post" class="breadcrumb form-search ">
    	<input id="pageNo" name="pageNo" type="hidden" value="${studentQuestion.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${studentQuestion.pageSize}"/>
		<input id="studentId" name="studentId" type="hidden" value="${studentId}"/>
		<input id="examId" name="examId" type="hidden" value="${examId}"/>
		<input id="examClassId" name="examClassId" type="hidden" value="${examClassId}"/>
		<input id="questionId" name="questionId" type="hidden" value="${questionId}"/>
		本班总人数：<font color="red">${countPerson}</font>人
		本次答错人数：<font color="red">${gerError}</font>人
		本题错误率：${errorPercent}&nbsp;&nbsp;&nbsp;&nbsp;
		<input id="btnExamSubmit" class="btn tbtn-primary" type="button" value="返回" onclick="history.go(-1)"/>
	</form:form>

	<%-- <sys:message content="${message}"/> --%>
	<form id="examTitleDetailForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th style="text-align: center;">姓名</th>
					<th style="text-align: center;">学号</th>
					<th style="text-align: center;">电话</th>
					<th style="text-align: center; width: 120px; ">交作业时间</th>
					<th style="text-align: center;">本题回答情况</th>
				</tr>
			</thead>
			<tbody>
			  <c:forEach items="${studentQuestion.list}" var="list">
				<tr>
					<td style="text-align: center;">${list.student.name}</td>
					<td style="text-align: center;">${list.student.stdCode}</td>
					<td style="text-align: center;">${list.student.stdPhone}</td>
					<td style="text-align: center;">
						<fmt:formatDate value="${list.submitTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td style="text-align: center;">
						<c:if test="${list.detailQuestion eq '0'}">
							错误
						</c:if>
						<c:if test="${list.detailQuestion eq '1'}">
							正确
						</c:if>
					</td>
				</tr>
			</c:forEach> 
			</tbody>
		</table>
	 </form>
	 
	  <div class="pagination">${studentQuestion}</div>
</body>
</html>