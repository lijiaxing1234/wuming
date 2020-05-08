<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="student_default"/>
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<title>学习资源</title>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		function downloadResource(resourceId){
			window.location.href = "${ctx}/studentExam/downloadResource?resourceId="+resourceId;
		}
	</script>
</head>
<body>
	
	<form:form id="searchForm" modelAttribute="edresources" action="${ctx}/studentNews/sourcesList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label>资源名称：</label>
				<form:input path="resName" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn sbtn-primary" type="submit" value="查询"/></li>
		</ul>
	</form:form>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width:20px;">序号</th>
				<th>资源名称</th>
				<th>上传人</th>
				<th>所属知识点</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="resource" begin="0" varStatus="i" step="1">
				<tr>
					<td>${i.count}</td>
					<td>${resource.resName }</td>
					<td>${resource.userName }</td>
					<td>${resource.knowledgeName }</td>
					<td>
						<%-- <button class="btn sbtn-primary" onclick="downloadResource('${resource.id}')">下载</button> --%>
						<a href="${pageContext.request.contextPath}/commonDownLoadFiles${resource.resFile }" >下载</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>