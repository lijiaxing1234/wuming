<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>题目调整列表</title>
	<style type="text/css">
	   .form-actions{margin:0px; border-top: 0px; text-align: right;}
	</style>
		<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		function saveButton(){ 
			var examId=$("#examId").val();
			window.location.href ='${ctx}/questionlib/exam/check?examId='+examId; 
		}
		$("#saveExam").click{
			var examId=$("#examId").val();
			window.location.href ='${ctx}/questionlib/exam/check?examId='+examId; 
		}
	</script>
</head>
<body>

     <form:form id="searchForm"  action="${ctx}/questionlib/exam/check?examId=${examId}" method="post" class="breadcrumb form-search ">
		 <input type="hidden" id="examId" name="examId" value="${examId}" />
		&nbsp;&nbsp;&nbsp;<input id="saveExam" class="btn  " style="padding: 4px;margin: 3px;" type="button" value="返回" onclick="history.go(-1)"/>
		<%-- <a class="btn" href="${ctx}/questionlib/exam/check?examId=${examId}" class="btn btn-primary" style='text-decoration:none; font-size: 20px;'>返回</a> --%>
	</form:form>
  	<form id="updateSortForm" method="post" action="${ctx}/examination/updateSort?examdetailId=${examdetailId }&examId=${examId}" >
	<table id="contentTable"  style="margin-top: 20px;border-color:#F0FFFF" border="1" cellspacing="0" cellpadding="0" >
		<thead>
			<tr>
				<th width="50px;">编号</th>
				<th width="70px;">题型</th>
				<th>题干</th>
				<!-- <th>出题频率</th> -->
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
				<%-- <td>
				    ${item.count}
				</td> --%>
			</tr>
		</c:forEach>
			
		 </c:forEach>
		</tbody>
	</table>
	<div align="right">
	
	</div>
</form>
</body>
</html>