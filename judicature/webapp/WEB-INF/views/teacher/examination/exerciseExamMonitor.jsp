<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>随堂练习监控界面</title>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<%@include file="/WEB-INF/views/teacher/include/date.jsp" %>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    } 
		var examId=$("#examId").val();
		$(document).ready(function() {
			/* $("#classIdSubmit").blur(function(){
				$("#searchForm").submit();
			}); */
			/* function submitTime(a){
				if(a==null||("").equals(a)){
					return "未提交";
			   }else{
					return a;
				}
			} */
			$("input[id^='btnSubmit_']").click(function(){
				var id=$(this).attr("id").split("_")[1];
				var classIds=$("#classIds").val();
		 		window.location.href ='${ctx}/exerciseExamination/submitExam?examId='+id+'&classIds='+classIds;
			 });
		});
		function changeClass(){
			$("#searchForm").submit();
		}
	</script>
</head>
<body>
	<input id="classIds" name="classIds" type="hidden" value="${classIds}"/>
    <form:form id="searchForm" modelAttribute="exam" action="${ctx}/exerciseExamination/seleceClassMonitor " method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${studentList.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${studentList.pageSize}"/><%-- 
		<input id="examId" name="examId" type="hidden" value="${exam.id}"/>
		<input id="classId" name="classId" type="hidden" value="${schoolClass.id}"/> --%>
		班级：&nbsp;
		<%-- <select id="classIdSubmit" name="classId">
			 <c:forEach items="${studentClassTitle}" var="list">
				<option value="${list.id}">${list.title}</option>
			</c:forEach>
		</select> --%>
		<%-- <form:select path="id"  class="input-small"  onchange="changeClass()">
			 <form:option value="请选择"/> 
		   	 <form:options items="${questionlib:getClassbyExamId(exam.id)}" itemLabel="title" itemValue="id"  htmlEscape="false"/>   
		 </form:select> --%>
		 
		 <select  class="input-small"  onchange="changeClass()" name="classId" id="classId">
		    <option  value="">请选择</option>
		    <c:forEach  var="item" items="${questionlib:getClassbyExamId(exam.id)}">
		       <option value="${item.id}" ${classId eq item.id ? "selected='selected'":""} >${item.title}</option>
		    </c:forEach>
		 </select>
	 
		 
		<input type="hidden" name="examId" value="${exam.id}"/>
		作业名称：&nbsp;${exam.title}
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input id="btnSubmit_${exam.id}" class="btn tbtn-primary" type="button" value="收卷"/>
	</form:form>

	<%-- <sys:message content="${message}"/> --%>
	<form id="examForm" modelAttribute="exam" method="post">
		<table  id="treeTable" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th style="text-align: center;">姓名</th>
					<th style="text-align: center;">学号</th>
					<th style="text-align: center;">电话</th>
					<th style="text-align: center;">交卷时间</th>
					<!-- <th style="text-align: center;">答案</th>
					<th style="text-align: center;">是否正确</th> -->
				</tr>
			</thead>
			<tbody>
			  <c:forEach items="${studentList.list}" var="list">
				<tr>
					<td style="text-align: center;">${list.student.name}</td>
					<td style="text-align: center;">${list.student.stdCode}</td>
					<td style="text-align: center;">${list.student.stdPhone}</td>
					<td style="text-align: center;">
						<c:if test="${list.submitTime eq null}">
							未提交
						</c:if>
							<fmt:formatDate value="${list.submitTime}" pattern="yyyy-hh-MM HH:mm:ss"/>
							
					</td>
					<%-- <td style="text-align: center;">${list.answer}</td>
					<td style="text-align: center;">
						<c:if test="${list.submitTime eq null}">
								
						</c:if>
						<c:if test="${!empty list.submitTime}">
							<c:if test="${list.isRight eq '0'}">
								错误
							</c:if>
							<c:if test="${list.isRight eq '1'}">
								正确
							</c:if>
						</c:if>
					</td> --%>
				</tr> 
			</c:forEach> 
			</tbody>
		</table>
	 </form>
	 <div class="pagination">${studentList}</div>
</body>
</html>