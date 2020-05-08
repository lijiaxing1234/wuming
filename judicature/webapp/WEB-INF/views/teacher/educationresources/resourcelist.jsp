<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="teacher_blank" />
<title>知识点管理</title>
<%@include file="/WEB-INF/views/include/treetable.jsp"%>
<%@include file="/WEB-INF/views/teacher/include/validation.jsp"%>
<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel:30}).show();
			$("#listForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				//errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>

	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sources/list?knoId=${knoId}">教学资源列表</a></li>
		<li><a 	href="${ctx}/sources/form?knoId=${knoId}">教学资源添加</a></li>
	</ul>
	<sys:message content="${message}" />
	<form:form id="searchForm" modelAttribute="edresources"	action="${ctx}/sources/list?knoId=${knoId}" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<label>资源名称：</label>
		<form:input path="resName" cssClass="input-medium" />
		&nbsp;&nbsp;<input id="btnSubmit" class="btn tbtn-primary" type="submit" value="查询" />
	</form:form>

	<table id="treeTable" 	class="table table-striped table-bordered table-condensed" 	style="margin-top: 20px;">
		<thead>
			<tr>
				<th>编号</th>
				<th>资源名称</th>
				<th width: 120px;>时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="item" varStatus="index">
				<tr>
					<td>${index.index+1}</td>
					<td>${item.resName }</td>
					<td>
					    <fmt:formatDate value='${item.createDate}' 	pattern='yyyy-MM-dd' />
					</td>
					<td>
					    <a  	href="${ctx}/sources/form?id=${item.id}&knoId=${knoId}" style="background: url(${ctxStatic}/icon/xg.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  title="修改"> </a>
					    <a  href="${ctx}/sources/delete?id=${item.id}&knoId=${knoId}" onclick="return confirmx('确定要删除吗？',this.href);" style="background: url(${ctxStatic}/icon/del.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" title="删除"></a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>