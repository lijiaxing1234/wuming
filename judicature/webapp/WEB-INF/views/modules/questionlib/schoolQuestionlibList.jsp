<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学校数据库管理</title>
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
		<li class="active"><a href="${ctx}/questionlib/schoolQuestionlib/list?schoolId=${schoolId}">学校题库授权列表</a></li>
		<shiro:hasPermission name="questionlib:schoolQuestionlib:edit"><li><a href="${ctx}/questionlib/schoolQuestionlib/form?schoolId=${schoolId}">学校题库授权</a></li></shiro:hasPermission>
	</ul>
		 
	<form:form id="searchForm" modelAttribute="schoolQuestionlib" action="${ctx}/questionlib/schoolQuestionlib/list?schoolId=${schoolId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<%-- <ul class="ul-form">
			<li><label>学校：</label>
				<form:input path="schoolId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>题库：</label>
				<form:input path="questionlibId" htmlEscape="false" maxlength="32" class="input-medium"/>
			</li>
			<li><label>有效期开始时间：</label>
				<input name="validStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${schoolQuestionlib.validStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>有效期结束时间：</label>
				<input name="validEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${schoolQuestionlib.validEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li><label>使用状态：</label>
				<form:select path="state" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul> --%>
	</form:form>
	<sys:message content="${message}" type="warning"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width:20px;">序号</th>
				<th>学校</th>
				<th>负责人</th>
				<th>电话</th>
				<th>题库</th>
				<th>有效期开始时间</th>
				<th>有效期结束时间</th>
				<th>使用状态</th>
				<th>更新时间</th>
				<shiro:hasPermission name="questionlib:schoolQuestionlib:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="schoolQuestionlib" varStatus="status">
			<tr>
				<td>${status.index+1+(page.pageNo-1)*page.pageSize}</td>
				  
				<td>${questionlib:getOfficeById(schoolQuestionlib.schoolId).name }</td> 
				<td>${questionlib:getOfficeById(schoolQuestionlib.schoolId).master }</td>
				<td>${questionlib:getOfficeById(schoolQuestionlib.schoolId).phone }</td>
				
				<td><a href="${ctx}/questionlib/schoolQuestionlib/form?id=${schoolQuestionlib.id}&schoolId=${schoolId}">
					${questionlib:getCourseQuestionlibById(schoolQuestionlib.questionlibId).title}
				</a></td>
				<td>
					<fmt:formatDate value="${schoolQuestionlib.validStartDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<fmt:formatDate value="${schoolQuestionlib.validEndDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
				   <jsp:useBean id="now" class="java.util.Date" />
				  <c:choose>
				       <c:when test="${now lt schoolQuestionlib.validStartDate }">未启用</c:when>
				       <c:when test="${now le schoolQuestionlib.validEndDate }"><font color="green">正常</font></c:when>
				       <c:otherwise><font color="red">过期</font></c:otherwise>
				    </c:choose>
				<%-- 	${fns:getDictLabel(schoolQuestionlib.state, 'schoolstate', '')} --%>
				</td>
				<td>
					<fmt:formatDate value="${schoolQuestionlib.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="questionlib:schoolQuestionlib:edit"><td>
    				<a href="${ctx}/questionlib/schoolQuestionlib/form?id=${schoolQuestionlib.id}&schoolId=${schoolId}">修改</a>
					<shiro:hasPermission name="questionlib:schoolQuestionlib:delete">	
						<a href="${ctx}/questionlib/schoolQuestionlib/delete?id=${schoolQuestionlib.id}&schoolId=${schoolId}" onclick="return confirmx('确认要删除该学校数据库吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<div style="height:200px; ">&nbsp</div>
</body>
</html>