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
			//查看试卷
			 $("input[id^='ExamQuerySubmit_']").click(function(){
				 var examId=$(this).attr("id").split("_")[1]; 
				window.location.href = ("${ctx}/testPaper/testPaperUpExam?examId="+examId);
			 });
		});
	</script>
</head>
<body>
<ul class="nav nav-tabs">
	<li><a href="${ctx}/exam/examListUnPublish">未完成的课后作业</a></li>
	<li><a href="${ctx}/exam/examList">已完成的课后作业</a></li>
	<li class="active"><a href="${ctx}/exam/examListOvertimeUnPublish">超时未发布的课后作业</a></li>
</ul>
 <sys:message content="${message}" />
    <form:form id="searchForm" modelAttribute="exam" action="${ctx}/exam/examList" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${examList.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${examList.pageSize}"/>
		<ul class="ul-form">
			<li><label  style="width: 120px;">出题时间：</label>
				 <input id="beginTime" name="beginTime"  type="text" class="input-medium Wdate"
				value="<fmt:formatDate value='${beginTime}'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"
				onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}'})" readonly></input>
			</li>
			<li><label  style="width: 120px;">结束时间：</label>
				<input id="endTime" name="endTime"  type="text" class="input-medium Wdate"
				value="<fmt:formatDate value='${endTime}'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"
				 onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly></input>
			</li>
			<li><label>班级：</label>
				<form:select path="examClass.id"  class="input-small">
					<form:option value="">全部</form:option>
				   	<form:options items="${questionlib:getCourseQuestionlibByTeacherId()}" itemLabel="examClass.title" itemValue="examClass.id"  htmlEscape="false"/>
				 </form:select>
			</li>
			<li><label>作业名称：</label>
				<input id="title" name="title" class="input-medium" value="${examTitle}" style="width: 100px;"/>
			</li>
			<li>
				&nbsp;&nbsp;<input id="btnExamSubmit" class="btn tbtn-primary" type="submit" value="搜索"/>
			</li>
		</ul>
	</form:form>

	<%-- <sys:message content="${message}"/> --%>
	<form id="examForm" method="post">
		<table  id="treeTable" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th hidden="true" style="text-align: center;">ID</th>
					<th style="text-align: center; width: 120px; ">结束时间</th>
					<th style="text-align: center;   width: 120px; ">公布答案时间</th>
					<th style="text-align: center;">班级</th>
					<th hidden="true" style="text-align: center;">班级ID</th>
					<th style="text-align: center;">作业名称</th>
					<th style="text-align: center;">所属课程</th>
					<th style="text-align: center;">状态</th>
					<th style="text-align: center;">操作</th>
				</tr>
			</thead>
			<tbody>
			  <c:forEach items="${examList.list}" var="list">
				<tr>
					<td hidden="true" style="text-align: center;">${list.id}</td>
					<td style="text-align: center;">
						<fmt:formatDate value="${list.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td style="text-align: center;">
						<fmt:formatDate value="${list.publishAnswerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td style="text-align: center;">${list.examClass.title}</td>
					<td hidden="true" style="text-align: center;">${list.examClass.id}</td>
					<td style="text-align: center;">
						${list.title}
					</td>
					<td style="text-align: center;">${list.examCourse.title}</td>
					<td style="text-align: center;">
						<c:if test="${list.status eq '0'}">
							超时未进行发布
						</c:if>
					</td>
					<td style="text-align: center;">
						<input id='ExamQuerySubmit_${list.id}'  style="background: url(${ctxStatic}/icon/check.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="查看试卷"/>
					</td>
				</tr>
			</c:forEach> 
			</tbody>
		</table>
	 </form>
	 <div class="pagination">${examList}</div>
</body>
</html>