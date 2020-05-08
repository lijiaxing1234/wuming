<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="teacher_default"/>
<title>模板管理</title>
<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
		$(document).ready(function() {
		});
</script>
</head>
<body>
 
   <form:form id="searchForm" modelAttribute="examination" action="${ctx}/template/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		 <label for="">模板名称：</label>
		 <form:input path="title" cssClass="input-medium" />
		&nbsp;&nbsp;<input id="btnSubmit" class="btn tbtn-primary" type="submit" value="查询"/>
	</form:form>
	<sys:message content="${message}"/>
	<form id="listForm">
		<table  class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th width: 20%;>序号</th>
					<th style="width: 70%;">模板名称</th>
				<!-- 	<th>组卷次数</th> -->
					<th width: 20%;>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${page.list}" varStatus="index">
					<tr>
						<td>${(index.index+1) + (page.pageNo-1)*page.pageSize }</td>
						<td>${item.title}</td>
						<td>
							<a  href="${ctx }/template/usetemplate?id=${item.id}" style="background: url(${ctxStatic}/icon/mb.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" title="使用模板" ></a>
							<a  href="${ctx }/template/deleteTemplate?id=${item.id}"  style="background: url(${ctxStatic}/icon/del.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  onclick="return confirmx('确认要删除该模板吗？', this.href)" title="删除"></a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="pagination">
	  		${page}
	  	</div>
	 </form>


</body>
</html>