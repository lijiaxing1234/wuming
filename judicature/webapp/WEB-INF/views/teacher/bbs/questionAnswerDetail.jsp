<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="teacher_default"/>
<title>bbs论坛</title>
<%@include file="/WEB-INF/views/teacher/include/validation.jsp" %>
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
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width:20px;">序号</th>
			    <th>内容</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${uQuestionAnswerList}" var="answer" varStatus="status">
			<tr>
				<td>${status.index+1 }</td>	
			    <td>
					${answer.detail}
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<form:form id="submitForm" modelAttribute="uQuestionAnswer" action="${ctx}/studentNews/saveAnswer" method="post" class="breadcrumb form-search">
		<form:input path="detail"/>
		<input type="submit"  >
	</form:form>
	
	
</body>
</html>