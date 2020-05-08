<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>未发布的随堂练习</title>
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
			$("#classIdSubmit").blur(function(){
				$("#searchForm").submit();
			});
			/* function submitTime(a){
				if(a==null||("").equals(a)){
					return "未提交";
			   }else{
					return a;
				}
			} */
			$("input[id^='btnSubmit_']").click(function(){
				var id=$(this).attr("id").split("_")[1];
				var examClassId=$(this).attr("id").split("_")[2];
		 		window.location.href ='${ctx}/classWork/submitExam?examId='+id+'&examClassId='+examClassId;
			 });
			$("#returnUnpublish").click(function(){
				window.location.href ='${ctx}/classWork/unpublishList';
			})
		});
		
	</script>
</head>
<body>
    <form:form id="searchForm" modelAttribute="exam" action="${ctx}/classWork/examMonitor " method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${studentList.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${studentList.pageSize}"/>
		班级：&nbsp;${school.title}
		<input type="hidden" name="examId" value="${exam.id}"/>
		作业名称：&nbsp;${exam.title}
		<input type="hidden" name="examClassId" value="${school.id}"/>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input id="btnSubmit_${exam.id}_${school.id}" class="btn tbtn-primary" type="button" value="收卷"/>
		<input id="returnUnpublish" class="btn tbtn-primary" type="button" value="返回"/>
	</form:form>

	<%-- <sys:message content="${message}"/> --%>
	<form id="examForm" modelAttribute="exam" method="post">
		<table  id="treeTable" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th style="text-align: center;">姓名</th>
					<th style="text-align: center;">学号</th>
					<th style="text-align: center;">电话</th>
					<th style="text-align: center; width: 120px;">交作业时间</th>
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
						<c:if test="${!empty list.submitTime}">
							<fmt:formatDate value="${list.submitTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</c:if>
					</td>
					<%-- <td style="text-align: center;">
						<c:if test="${list.submitTime eq null}">
							-
						</c:if>
						
							${list.answer}
					
					</td>
					<td style="text-align: center;">
						<c:if test="${list.submitTime eq null}">
							-
						</c:if>
						<c:if test="${list.submitTime}">
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