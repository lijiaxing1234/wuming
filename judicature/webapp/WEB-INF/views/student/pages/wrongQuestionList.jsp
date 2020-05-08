<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="student_default"/>
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<title>我的错题集</title>
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
	<form:form id="searchForm" modelAttribute="question" action="${ctx}/studentExam/wrongQuestionsList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label>知识点：</label>
				<form:input path="examCode" class="input-medium"/>
			</li>
			<li>
				<label>考试类型：</label>
				<form:select path="examType" class="input-medium">
					<form:option value="">请选择</form:option>
					<form:option value="1">随堂练习</form:option>
					<form:option value="3">课后作业</form:option>
					<form:option value="5">在线考试</form:option>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn sbtn-primary" type="submit" value="查询"/></li>
		</ul>
	</form:form>
	<sys:message content="${message }"></sys:message>
	<table id="" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>考点</th>
				<th>题型</th>
				<th>难度</th>
				<th>分值</th>
				<th style="width: 80px;">习题讲解</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="question">
				<tr><!-- 题型 1：单选题 2：填空题 3：计算题 4：简答题 5：多选题 -->
					<td>${question.examCode }</td>
					<td>
						<c:if test="${question.quesType == '1' }">
							单选题
						</c:if>
						<c:if test="${question.quesType == '2' }">
							填空题
						</c:if>
						<c:if test="${question.quesType == '3' }">
							计算题
						</c:if>
						<c:if test="${question.quesType == '4' }">
							简答题
						</c:if>
						<c:if test="${question.quesType == '5' }">
							多选题
						</c:if>
						<c:if test="${question.quesType == '6' }">
							概念题
						</c:if>
						<c:if test="${question.quesType == '7' }">
							综合题
						</c:if>
						<c:if test="${question.quesType == '8' }">
							作图题
						</c:if>
						<c:if test="${question.quesType == '9' }">
							制表题
						</c:if>
						<c:if test="${question.quesType == '10' }">
							识图题
						</c:if>
						<c:if test="${question.quesType == '11' }">
							判断题
						</c:if>
					</td>
					<td>${question.quesLevel }</td>
					<td>${question.quesPoint }</td>
					<td >
						<a href="${ctx}/studentExam/getQuestionDetails?questionId=${question.id}" style="background: url(${ctxStatic}/icon/check.png) no-repeat;width:20px;height:20px;background-size:100%;cursor:pointer;display:inline-block;" title="查看"> </a>&nbsp;&nbsp;
						<a href="${ctx}/studentExam/getCongenericQuestions?questionId=${question.id}" style="background: url(${ctxStatic}/icon/zd.png) no-repeat;width:20px;height:20px;background-size:100%;cursor:pointer;display:inline-block;" title="作答"></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>