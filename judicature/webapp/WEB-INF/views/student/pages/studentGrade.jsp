<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="student_default"/>
<title>我的成绩</title>
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
	 <form:form id="searchForm" modelAttribute="studentTask" action="${ctx}/studentNews/myGrade" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>考试类型：</label>
				<form:select path="examType" class="input-medium">
					<form:option value="">请选择</form:option>
					<form:option value="1">随堂练习</form:option>
					<form:option value="3">课后作业</form:option>
					<form:option value="5">在线考试</form:option>
					<form:option value="2">组卷考试</form:option>
				</form:select>
			
			</li>
			<li class="btns"><input id="btnSubmit" class="btn sbtn-primary" type="submit" value="查询"/></li>
		</ul>
	</form:form>
	<table id="myGradTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width:20px;">序号</th>
				<th>考试类型</th>
				<th>名称</th>
				<th style="width: 120px;">作答时间</th>
				<th style="width: 80px;">正确/总数</th>
				<!-- <th>最高分/最低分/平均分</th> -->
				<th style="width: 100px;">所得分数/总分</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="studentTask" varStatus="status">
				<tr>
					<td>
						${status.index+1 }
					</td>
					<td>
							<c:if test="${studentTask.examType == 1 }">
								随堂练习
							</c:if>
							<c:if test="${studentTask.examType == 5 }">
								在线考试
							</c:if>
							<c:if test="${studentTask.examType == 3 }">
								课后作业
							</c:if>
							<c:if test="${studentTask.examType == 2 }">
								组卷考试
							</c:if>
					<td>
						<c:if test="${studentTask.examType != 2 }">
							<a href="${ctx}/studentExam/searchExamQuestions?examDetailId=${studentTask.examDetailId }&&examId=${studentTask.examid}">
						</c:if>
							${studentTask.title }
						<c:if test="${studentTask.examType != 2 }">
							</a>
						</c:if>
					</td>
					<td><fmt:formatDate value="${studentTask.starttime }" pattern="yyyy-MM-dd HH:mm:ss"/>
					<td>
						<c:if test="${studentTask.examType == 2 }">
							无
						</c:if>
						<c:if test="${studentTask.examType != 2 }">
							<c:if test="${studentTask.isMark eq 1}">
								${studentTask.rightCount }/${studentTask.totalCount }
							</c:if>
							<c:if test="${studentTask.isMark != 1}">
								 未判卷 
							
							</c:if>
						</c:if>
					<td>
						<c:if test="${studentTask.examType == 5 or  studentTask.examType == 2}">
							<c:if test="${studentTask.isMark == 1 }">
								${studentTask.score }/${studentTask.totalfraction}
							</c:if>
							<c:if test="${studentTask.isMark != 1 }">
								<a href="javascript:void(0)"  >未判卷</a>
							/${studentTask.totalfraction}
							</c:if>					
						</c:if>
						<c:if test="${studentTask.examType != 5 and  studentTask.examType != 2}">
							无
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>