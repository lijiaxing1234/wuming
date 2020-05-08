<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>作业列表</title>
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
			 $("input[id^='update_']").click(function(){
					var examId=$(this).attr("id").split("_")[1];
					window.location.href = ("${ctx}/examination/adjustExam?id="+examId);
				 });
		});
	</script>
</head>
<body>
<%--<fmt:formatDate value="${exam.beginDate}" pattern="yyyy-MM-dd"/> --%>
    <form:form id="searchForm" modelAttribute="exam" action="${ctx}/knowledge/statistics" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		开始时间&nbsp;
		    <input id="beginTime" name="beginTime"  type="text" class="input-medium Wdate"
				value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"
				onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}'})"></input>
		结束时间&nbsp;
		   <input id="endTime" name="endTime"  type="text" class="input-medium Wdate"
				value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"
				 onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}'})"></input>
		班级&nbsp;
		<form:select path="examClass.id"  class="input-small">
			<form:option value=""/>
		   	<form:options items="${questionlib:getCourseQuestionlibByTeacherId()}" itemLabel="examClass.title" itemValue="examClass.id"  htmlEscape="false"/>
		 </form:select>
		作业名称&nbsp;<input id="title" name="title" class="input-medium" value="${examTitle}" style="width: 100px;"/>
		<input id="btnExamSubmit" class="btn tbtn-primary" type="submit" value="搜索"/>
	</form:form>

	<%-- <sys:message content="${message}"/> --%>
	<form id="examForm" method="post">
		<table  id="treeTable" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th hidden="true" style="text-align: center;">ID</th>
					<th style="text-align: center; width: 120px; ">出题时间</th>
					<th style="text-align: center;">班级</th>
					<th hidden="true" style="text-align: center;">班级ID</th>
					<th style="text-align: center;">作业名称</th>
					<th style="text-align: center;">状态</th>
					<th style="text-align: center;">操作</th>
				</tr>
			</thead>
			<tbody>
			  <c:forEach items="${page.list}" var="list">
				<tr>
					<td hidden="true" style="text-align: center;">${list.id}</td>
					<td style="text-align: center;">
						<fmt:formatDate value="${list.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td style="text-align: center;">${list.examClass.title}</td>
					<td hidden="true" style="text-align: center;">${list.examClass.id}</td>
					<td style="text-align: center;">${list.title}</td>
					<td style="text-align: center;">
						<c:if test="${list.status eq '0'}">
							未发布
						</c:if>
						<c:if test="${list.status eq '1'}">
							正在进行
						</c:if>
						<c:if test="${list.status eq '2'}">
							已完成
						</c:if>
					</td>
					<td style="text-align: center;">
						<input id='exam_${list.id}_${list.examClass.id}_${list.status}' style="background: url(${ctxStatic}/icon/check_1.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="查看"/>
					</td>
				</tr>
			</c:forEach> 
			</tbody>
		</table>
	 </form>
	 <div class="pagination">${page}</div>
</body>
</html>