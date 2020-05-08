<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学生管理</title>
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
	<ul class="nav nav-tabs" style="margin-top: 5px;">
		<li class="active"><a href="${ctx}/questionlib/student/findClassStudentByClassId?schoolClass.id=${student.schoolClass.id }">学生列表</a></li>
		<shiro:hasPermission name="questionlib:student:edit"><li><a href="${ctx}/questionlib/student/toAddOrUpdateStudentPage?schoolClass.id=${student.schoolClass.id }">学生添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="student" action="" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>学生姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-medium"/>
			</li>
			<li><label>学号：</label>
				<form:input path="stdCode" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>手机号：</label>
				<form:input path="stdPhone" htmlEscape="false" maxlength="11" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询" onclick="submitForm()"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>学生姓名</th>
				<th>班级</th>
				<th>学号(登录名)</th>
				<th>电话号</th>
				<th>邮箱</th>
				<th>修改时间</th>
				<shiro:hasPermission name="questionlib:student:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="student">
			<tr>
				<td>
					${student.name}
				</td>
				<td>
					${student.schoolClass.title}
				</td>
				<td>
					${student.stdCode}
				</td>
				<td>
					${student.stdPhone}
				</td>
				<td>
					${student.stdEmail}
				</td>
				<td>
					<fmt:formatDate value="${student.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="questionlib:student:edit"><td>
    				<a href="${ctx}/questionlib/student/toAddOrUpdateStudentPage?id=${student.id}">修改</a>
					<a href="${ctx}/questionlib/student/classStudentDelete?id=${student.id}" onclick="return confirmx('确认要删除该学生吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	<script type="text/javascript">
		function closeShowHideDiv(){
			$('#showHideDiv', window.parent.document).hide();
		}
		
		function submitForm(){
			$("#searchForm").attr("action","${ctx}/questionlib/student/findClassStudentByClassId");
			$("#searchForm").submit();
		}
		
	</script>
</body>
</html>