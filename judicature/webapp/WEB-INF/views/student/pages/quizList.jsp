<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="student_default"/>
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<title>随堂练习</title>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="SExam" action="${ctx}/studentExam/quizList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input name="examType"type="hidden" value="1" >
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-medium" />
			</li>
			<!-- 
			<li>
				<label>发布人:</label>
				<form:select path="teacher.id" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${student:getTeachersByStudentId() }" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			 -->
			<li>
				<label>开始时间：</label>
				<input name="firstTime" type="text" class="input-medium Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" > --
				<input name="secondTime" type="text" class="input-medium Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" >
			</li>
			<li class="btns"><input id="btnSubmit" class="btn sbtn-primary" type="submit" value="查询"/></li>
		</ul>
	</form:form>
	<sys:message content="${message }"></sys:message>
	<table id="" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width: 120px;">考试时间</th>
				<th>发布人</th>
				<th>名称</th>
				<th style="width: 80px;">正确/总数</th>
				<!-- <th>成绩 -->
				<!-- <th>时长(分钟)</th> -->
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="exam">
				<tr>
					<td><fmt:formatDate value="${exam.beginTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${exam.teacher.name }</td>
					<td>${exam.title }</td>
					<%-- <td>${exam.score } --%>
					<%-- <td>${exam.durationTime }</td> --%>
					<td><c:if test="${exam.isMark eq 1 }">
							${exam.rightCount }/${exam.totalCount }
						</c:if>
						<c:if test="${exam.isMark != 1 }">
							<a href="javascript:void(0)"  >未判卷</a>
							
						</c:if>
					<td>
						<c:if test="${exam.quizExamStatus == 1 }">
							<%-- <a target="_blank" href="${ctx}/studentAnswer/form?examId=${exam.id }&returnUrl=/studentExam/quizList&examType=1">进入</a> --%>
							<a target="_top" href="${ctx}/studentAnswer/form?examId=${exam.id }&returnUrl=/studentExam/quizList&examType=1" style="background: url(/static/icon/jr.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" title="进入"></a>
						</c:if>
						<c:if test="${exam.quizExamStatus == 4 }">
							<a href="${ctx}/studentExam/searchExamQuestions?examId=${exam.id }&examType=1&&examDetailId=${exam.examDetailId}" style="background: url(${ctxStatic}/icon/check.png) no-repeat;width:20px;height:20px;background-size:100%;cursor:pointer;display:inline-block;" title="查看"></a>
							
						</c:if>
						<c:if test="${exam.quizExamStatus == 2 }">
							<a href="${ctx}/studentExam/getMissQuestions?examId=${exam.id }" style="background: url(${ctxStatic}/icon/ycg.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" title="已错过"> </a>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>