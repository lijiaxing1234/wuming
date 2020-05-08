<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程版本管理</title>
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
		<li class="active"><a href="${ctx}/questionlib/sCourseVersion/">课程版本列表</a></li>
		<shiro:hasPermission name="questionlib:sCourseVersion:edit"><li><a href="${ctx}/questionlib/sCourseVersion/form">课程版本添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="SCourseVersion" action="${ctx}/questionlib/sCourseVersion/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>版本名称：</label>
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<%-- <li><label>课程：</label>
				<form:select path="course.id" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${questionlib:getSchoolNotCourse()}" itemLabel="title" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li> --%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>版本名称</th>
				<th>课程</th>
				<shiro:hasPermission name="questionlib:sCourseVersion:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sCourseVersion">
			<tr>
				<td>
					${sCourseVersion.title}
				</td>
				<td>
					${sCourseVersion.course.title }
				</td>
				
				<td>
				<shiro:hasPermission name="questionlib:sCourseVersion:deleteSchoolVersion">
					<a href="${ctx}/questionlib/sCourseVersion/form?id=${sCourseVersion.id}&schoolVersionId=${sCourseVersion.schoolVersionId }">修改</a>
					<a href="${ctx}/questionlib/sCourseVersion/deleteSchoolVersion?courseVersionId=${sCourseVersion.id}" onclick="return confirmx('确认要删除该课程版本吗？', this.href)">删除</a>
			</shiro:hasPermission>	</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>