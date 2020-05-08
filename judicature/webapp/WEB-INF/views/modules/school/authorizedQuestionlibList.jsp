<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>已授权题库管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
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
		<li class="active"><a href="${ctx}/school/schoolcourseQuestionlib/authorizedQuestionlibList">已授权题库列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="schoolQuestionlib" action="${ctx}/school/schoolcourseQuestionlib/authorizedQuestionlibList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<ul class="ul-form">
			<li><label>题库名称：</label>
				<form:input path="courseQuestionlib.title" htmlEscape="false" maxlength="50" class="input-medium"/>
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
				<th>专业名称</th>
				<th>课程名称</th>
				<th>版本名称</th>
				<th>题库名称</th>
				<th>有效开始时间</th>
				<th>有效截止时间</th>
				<th>授权时间</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="item"  varStatus="status">
			<tr>
				<td>${status.index+1+(page.pageNo-1)*page.pageSize }</td>	
				<td>
				   ${item.specialty.title }
				</td>
				<td>
				   ${item.course.title }
				</td>
				<td>
				   ${item.courseVesion.title }
				</td>
				<td>
				   ${item.courseQuestionlib.title }
				</td>
				<td> 
				    <fmt:formatDate value="${item.validStartDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td> 
				<td>
				    <fmt:formatDate value="${item.validEndDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
				     <fmt:formatDate value="${item.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
				   <jsp:useBean id="now" class="java.util.Date" />
				<%--     <c:if test="${ (now gt item.validEndDate) || (now lt item.validStartDate ) }">
				      <font color="red">过期</font>
				    </c:if>
				    <c:if test="${now le item.validEndDate }">
				      <font color="green">正常</font>
				    </c:if>  --%>
				    <c:choose>
				       <c:when test="${now lt item.validStartDate }">未启用</c:when>
				       <c:when test="${now le item.validEndDate }"><font color="green">正常</font></c:when>
				       <c:otherwise><font color="red">过期</font></c:otherwise>
				    </c:choose>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	 <div class="pagination">${page}</div>
</body>
</html>