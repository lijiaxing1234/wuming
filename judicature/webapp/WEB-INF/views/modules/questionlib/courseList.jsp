<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		$("#btnExport").click(function(){
			 return confirmx("确认要导出吗？",function(){
					$("#searchForm").attr("action","${ctx }/questionlib/course/export");
					$("#searchForm").submit(); 
				});
			});
		$("#btnSubmit").click(function(){
					$("#searchForm").attr("action","${ctx}/questionlib/course/");
					$("#searchForm").submit(); 
			});
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
		<li class="active"><a href="${ctx}/questionlib/course/">课程列表</a></li>
		<shiro:hasPermission name="questionlib:course:edit"><li><a href="${ctx}/questionlib/course/form">课程添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="course" action="${ctx}/questionlib/course/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>课程名称：</label>
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><li><label>所属专业：</label>
			 <sys:treeselect id="specialty" name="specialty.id" value="${course.specialty.id}" labelName="course.title" labelValue="${course.specialty.title}" 
				title="专业" url="/questionlib/specialty/treeData" cssClass="input-small" allowClear="false" notAllowSelectParent="true" /></li>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/></li>
			<shiro:hasPermission name="questionlib:course:export">
			  <li class="btns">
			  <!-- <input id="btnExport" class="btn btn-primary" type="button" value="课程导出"/> -->
			  <%-- <a href="${ctx }/questionlib/course/export" class="btn btn-primary"  >课程导出</a> --%></li>
			</shiro:hasPermission>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width:20px;">序号</th>
			  <!--   <th>专业名称</th> -->
			     
				<th>课程名称</th>
				<th>更新时间</th>
				<shiro:hasPermission name="questionlib:course:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="course" varStatus="status">
			<tr>
				<td>${status.index+1+(page.pageNo-1)*page.pageSize }</td>	
			  <%--   <td>
					${questionlib:getSpecialtyByID(course.specialtyId).title}
				</td> --%>
				<td><a href="${ctx}/questionlib/course/form?id=${course.id}">
					${course.title}
				</a></td>
				<td>
					<fmt:formatDate value="${course.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="questionlib:course:edit"><td>
    				<a href="${ctx}/questionlib/course/form?id=${course.id}">修改</a>
    				<shiro:hasPermission name="questionlib:course:delete">
						<a href="${ctx}/questionlib/course/delete?id=${course.id}" onclick="return confirmx('确认要删除该课程吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>