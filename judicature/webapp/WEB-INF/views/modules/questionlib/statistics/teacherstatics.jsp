<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>试卷管理</title>
	<meta name="decorator" content="default"/>
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">老师统计</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="teacherDTO" action="${ctx}/questionlib/teacherStatics/teacher" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label class="control-label">老师名称：</label>
				<form:input path="teacherName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width:20px;">序号</th>
				<th>老师名称</th>
				<th>课程数量</th>
				<th>随堂练习</th>
				<th>组卷考试</th>
				<th>课后作业</th>
				<th>课堂例题</th>
				<th>在线考试</th>
			
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="teacher" varStatus="s">
			<tr>
				<td>${s.index+1+page.pageSize*(page.pageNo-1)}</td>
				<td>
					${teacher.teacherName}
				</td>
				<td>
					${teacher.courseCount}
				</td>
				<td>
					${teacher.classTestCount}
				</td>
				<td>
					${teacher.examCount}
				</td>
				<td>
					${teacher.homeworkCount}
				</td>
				<td>
					${teacher.exampleCount}
				</td>
				<td>
					${teacher.onlineExamCount}
				</td>

			
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>