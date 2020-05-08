<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<title>我的题库</title>
<style>
.pagination ul > li > a{border-color:#e5e5e5;}
.pagination ul > .controls > a{border-color:#e5e5e5;}
.nav-tabs{font-weight:normal;}
.pagination ul > .disabled > a{color:#666;}
.pagination{width:auto;margin:20px 0;text-align:center;}
</style>
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
	<style>
	.table th{color:#fff;background:#64dfda;background-image:-webkit-linear-gradient(top,#64dfda,#64dfda);}
	.btn-primary{background:#1c9993;background-image:-webkit-linear-gradient(top,#1c9993,#1c9993);}
	.btn-primary:hover,.btn-primary:focus,.btn-primary:active,.btn-primary.active,.btn-primary.disabled,.btn-primary[disabled] {color: #ffffff;background-color: #1c9993;*background-color: #1c9993;}
	.btn-primary:active,.btn-primary.active { background-color: #1c9993 \9;}
	</style>
</head>
<body>
	<form:form id="searchForm" modelAttribute="question" action="${ctx}/studentNews/knowledgePointsList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label>考点：</label>
				<form:input path="examCode"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn sbtn-primary" type="submit" value="查询"/></li>
		</ul>
	</form:form>
	<sys:message content="${message }"></sys:message>
	<table id="" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>考点</th>
				<th style="width: 80px;">题型</th>
				<th style="width: 50px;">难度</th>
				<th  style="width: 50px;">分值</th>
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
					<td style="text-align: center;"><a href="${ctx}/studentExam/questionLibGetQuestionDetails?questionId=${question.id}" style="background: url(${ctxStatic}/icon/check.png) no-repeat;width:20px;height:20px;background-size:100%;cursor:pointer;display:inline-block;" title="查看"> </a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>