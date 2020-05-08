<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="student_default"/>
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<title>试题管理</title>
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
	<%
		Date date = new Date();
		request.setAttribute("date", date);
	%>
	<form:form id="searchForm" modelAttribute="SExam" action="${ctx}/studentExam/examList?examType=5" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
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
				<input name="firstTime" type="text" class="input-medium Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" >  -- 
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
				<th style="width: 50px;">成绩</th>
				<th style="width: 120px;">开考时间</th>
				<!-- <th>形式</th> -->
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="exam">
				<tr>
					<td><fmt:formatDate value="${exam.beginTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${exam.teacher.name }</td>
					<td>${exam.title }</td>
					<td>
						<c:if test="${exam.isMark == 1}">
							${exam.score }
						</c:if>
						<c:if test="${exam.isMark != 1}">
							 
							<a href="javascript:void(0)">未判卷</a>
							
						</c:if>
					<td><fmt:formatDate value="${exam.beginTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<%-- <td>
						<c:if test="${exam.testPaperType == '0' }">
							其他
						</c:if>
						<c:if test="${exam.testPaperType == 'A' }">
							A卷
						</c:if>
						<c:if test="${exam.testPaperType == 'B' }">
							B卷
						</c:if>
					</td> --%>
					<td>
						<c:if test="${exam.examStatus == 2 }">
							<a href="${ctx}/studentExam/getMissQuestions?examId=${exam.id }" style="background: url(${ctxStatic}/icon/ycg.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" title="已错过"></a>
						</c:if>
						<c:if test="${exam.examStatus == 3 }">
							<c:choose>
								<c:when test="${exam.beginTime >= date }">
									<span>未开始</span>
								</c:when>
								<c:when test="${exam.beginTime < date }">
									<%-- <a target="_blank" href="${ctx}/studentAnswer/form?examId=${exam.id }&returnUrl=/studentExam/examList&examType=5">进入</a> --%>
									<a target="_top" href="${ctx}/studentAnswer/form?examId=${exam.id }&returnUrl=/studentExam/examList&examType=5" style="background: url(${ctxStatic}/icon/jr.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" title="进入"> </a>
								</c:when>
							</c:choose>
						</c:if>
						<c:if test="${exam.examStatus == 1 }">
							<a href="${ctx}/studentExam/searchExamQuestions?examId=${exam.id }&examType=5&&examDetailId=${exam.examDetailId}" style="display:inline-block; background: url(${ctxStatic}/icon/check.png) no-repeat;width:20px;height:20px;background-size:100%;cursor:pointer;" title="查看"></a>
							
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>