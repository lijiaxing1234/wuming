<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	<title>试卷调整列表</title>
	 <%@include file="/WEB-INF/views/teacher/include/validation.jsp" %>
	<%@ include file="/WEB-INF/views/teacher/include/date.jsp" %>
	<style type="text/css">
	   .form-actions{margin:0px; border-top: 0px; text-align: right;}
	</style>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		function saveButton(){ 
			
			var examId= "${examId}"//$("#examId").val();
			window.location.href ='${ctx}/testPaper/testPaperUpExam?examId='+examId; 
		}
	</script>
</head>
<body>

     <form:form id="searchForm"  action="${ctx}/testPaper/testPaperUpExam?examId=${examId}" method="post" class="breadcrumb form-search ">
		 <input type="hidden" id="examId" name="examId" value="${examId}" />
		<!-- <input id="saveExam" class="btn tbtn-primary" type="button" value="返回" onclick="saveButton();"/> -->
		<input id="saveExam" class="btn tbtn-primary" type="button" value="返回" onclick="saveButton();"/>
	</form:form>
  	<form id="updateSortForm" method="post" action="${ctx}/examination/updateSort?examdetailId=${examdetailId }&examId=${examId}" >
	<table id="contentTable" class="table table-striped table-bordered table-condensed"  style="margin-top: 20px;">
		<thead>
			<tr>
				<th>编号</th>
				<th>题型</th>
				<th>题干</th>
				<th>出题频率</th>
			</tr>
		</thead>
		<tbody>
		 <c:forEach items="${dataMap}" var="itemKey" varStatus="index">
			<tr id="${itemKey.key}" pId="0">
				<td style="text-align: lef;">${(index.index+1)}</td>
				<td colspan="5" style="text-align: left;"> ${fns:getDictLabel(itemKey.key,'question_type','')} </td>
			</tr>
			
			<c:forEach items="${itemKey.value}" var="item" varStatus="index">
			<tr  id="${item.question.id }" pId="${itemKey.key}" >
			    <td></td>
				<td style="text-align:right;">${(index.index+1)}</td>
				<td>${item.question.title}</td>
				<td>
				    ${item.count}
				</td>
			</tr>
		</c:forEach>
			
		 </c:forEach>
		</tbody>
	</table>

</form>
</body>
</html>