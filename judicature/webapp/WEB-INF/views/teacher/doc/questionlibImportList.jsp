<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<title>试题导入题库管理</title>
	<meta name="decorator" content="teacher_default"/>
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
		<li class="active"><a href="${ctx}/doc/list">试题导入文档列表</a></li>
		<%-- <shiro:hasPermission name="questionlib:questionlibImport:edit"><li><a href="${ctx}/doc/form">试题导入题库添加</a></li></shiro:hasPermission> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="questionlibImport" action="${ctx}/doc/list" method="post" class="breadcrumb form-search" cssStyle="margin:0px;padding: 25px 0px 5px 50px;">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%-- <li><label>专业：</label>
				<sys:treeselect id="specialtyId" name="specialty.id" value="${course.specialty.id}" labelName="specialty.name" labelValue="${course.specialty.title}"
					title="专业" url="/questionlib/specialty/treeData" cssClass="required"/>
			</li> --%>
			<li><label>文档名称：</label>
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn tbtn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>题库</th>
				<th>文档名称</th>
				<th>出题人</th>
				<th>上传人</th>
				<th>上传者电话</th>
				<th>学校</th>
				<th>适用层次</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="questionlibImport">
			<tr>
				<td><a href="${ctx}/doc/form?id=${questionlibImport.id}">
					${questionlib:getCourseQuestionlibById(questionlibImport.questionlibId).title}
				</a></td>
				<td>
					${questionlibImport.title}
				</td>
				<td>
					${questionlibImport.writer}
				</td>
				<td>
					${questionlibImport.user.name}
				</td>
				<td>
					${questionlibImport.phone}
				</td>
				<td>
					${questionlib:getOfficeById(questionlibImport.school.id).name}
				</td>
				<td>
					${fns:getDictLabel(questionlibImport.coursePhase, 'coursephase', '')}
				</td>
				<td>
    				<a href="${ctx}/doc/form?id=${questionlibImport.id}">查看</a>
					<a href="${ctx}/doc/delete?id=${questionlibImport.id}" onclick="return confirmx('确认要删除该文档吗？', this.href)">删除</a>
					<a href="${pageContext.request.contextPath}/commonDownLoadFiles/${questionlibImport.filePath}?fileName=${fns:urlEncode(questionlibImport.title)}" target="_blank">下载</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>