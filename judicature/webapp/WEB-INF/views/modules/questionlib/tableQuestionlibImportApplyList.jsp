<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>操作管理</title>
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
		<li class="active"><a href="${ctx}/questionlib/tableQuestionlibImportApply/">试题入库申请列表</a></li>
		<%-- <li><a href="${ctx}/questionlib/tableQuestionlibImportApply/form">试题入库申请添加</a></li> --%>
	</ul>
	<form:form id="searchForm" modelAttribute="tableQuestionlibImportApply" action="${ctx}/questionlib/tableQuestionlibImportApply/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
		    <li><label>学校：</label><sys:treeselect id="company" name="schoolId" value="${tableQuestionlibImportApply.schoolId}" labelName="schoolName" labelValue="${tableQuestionlibImportApply.schoolName}" 
				title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="false"/></li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>学校</th>
				<th>题库</th>
				<th>专业</th>
				<th>文档名称</th>
				<td>状态</td>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td>${item.schoolName}</td>
			    <td>${questionlib:getCourseQuestionlibById(item.queslibImport.questionlibId).title}</td>
			    <td>
					${questionlib:getSpecialtyByID(item.queslibImport.specialtyId).title}
				</td>
				<td>
					${item.queslibImport.title}
				</td>
				<td>${fns:getDictLabel(item.status,"questionlib_import_apply_status","")}
				</td>
				<td>
				    <c:choose>
				        <c:when test="${(item.status eq -1) or (item.status eq 2)}">
				        		已处理
				         </c:when>
				         <c:otherwise>
				            <a   href="${ctx}/questionlib/tableQuestionlibImportApply/audit?quesImportId=${item.queslibImportId}_2_${item.schoolId}" onclick="return confirmx('确认同意吗？', this.href)" class="btn btn-primary"   >同意</a> 
				        	<a   href="${ctx}/questionlib/tableQuestionlibImportApply/audit?quesImportId=${item.queslibImportId}_-1_${item.schoolId}" onclick="return confirmx('确认不同意吗？', this.href)" class="btn btn-primary" >不同意</a> 
				         </c:otherwise>
				    </c:choose>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>