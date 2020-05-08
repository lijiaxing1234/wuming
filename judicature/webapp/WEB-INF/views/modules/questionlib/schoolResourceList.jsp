<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学校资源管理</title>
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
		<li class="active"><a href="${ctx}/questionlib/school/">学校列表</a></li>
		<shiro:hasPermission name="questionlib:school:edit"><li><a href="${ctx}/questionlib/school/form">学校添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="school" action="${ctx}/questionlib/school/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>联系人</th>
				<th>电话</th>
				<th>邮箱</th>
				<th>状态</th>
				<shiro:hasPermission name="questionlib:school:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="school">
			<tr>
				<td><a href="${ctx}/questionlib/school/form?id=${school.id}">
					${school.title}
				</a></td>
				<td>
					${school.relName}
				</td>
				<td>
					${school.relPhone}
				</td>
				<td>
					${school.relEmail}
				</td>
				<td>
					${fns:getDictLabel(school.state, 'schoolstate', '')}
				</td>
				<shiro:hasPermission name="questionlib:school:edit"><td>
    				<a href="${ctx}/questionlib/school/form?id=${school.id}">修改</a>
					<a href="${ctx}/questionlib/school/delete?id=${school.id}" onclick="return confirmx('确认要删除该学校吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>