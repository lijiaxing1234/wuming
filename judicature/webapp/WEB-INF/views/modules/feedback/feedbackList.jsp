<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>用户反馈管理</title>
<meta name="decorator" content="default"/>
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<link href="${ctxStatic}/My97DatePicker/skin/WdatePicker.css"
	type="text/css" rel="stylesheet" />
<script type="text/javascript">
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}

	function addMemoSubmit() {
		$("#inputForm").submit();
	}

	function addMemoInfor(id){
		$("#id").val(id);
		$("#addmemo").modal('show');
	}
</script>
</head>
<body>

	<form:form id="searchForm" modelAttribute="feedback" action="${ctx }/admin/LVFeedback/list?lvCourseId=${feedback.lvCourseId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<div>
			<label>反馈内容：</label>
			<form:input path="content" htmlEscape="false" maxlength="500"
				class="input-medium" />
			<%-- &nbsp;&nbsp;<label>处理状态:</label>
			<form:select path="status">
				<form:option value="">请选择</form:option>
				<form:option value="0">未处理</form:option>
				<form:option value="1">已处理</form:option>
			</form:select> --%>
		<%-- 	&nbsp;&nbsp;<label>来源:</label>
			<form:select path="comefrom">
				<form:option value="">请选择</form:option>
				<form:options items="${platFormList}" htmlEscape="false" itemLabel="metaName" itemValue="metaId"/>
			</form:select> --%>
			<br><br><label>反馈时间:</label><input id="startDate"
				name="startDate" type="text" maxlength="20"
				class="input-medium Wdate" value="${startDate}"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'endDate\')}'});" />
			~ <input id="endDate" name="endDate" type="text" maxlength="20"
				class="input-medium Wdate" value="${endDate}"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'startDate\')}'});" />
			&nbsp;&nbsp;
			<input id="btnSubmit" class="btn btn-primary"  type="submit" value="查询" />
			<!-- 			<input id="btnSubmit" class="btn btn-primary"
				type="button" value="导出" onclick="exportAll()" /> -->
		</div>
	</form:form>
	<table id="contentTable" class="table table-striped table-bordered ">
		<thead>
			<tr>
				<th>序号</th>
				<th>用户</th>
				<!-- <th>姓名</th>
				<th>邮箱</th> -->
				<th>电话</th>
			<!-- 	<th>QQ</th> -->
				<th>反馈内容</th>
				<!-- <th>部门</th>  -->
			<!-- 	<th>来源</th> -->
				<th>时间</th>
		<!-- 		<th>处理状态</th> -->
	<!-- 			<th>处理内容</th> 
				<th>操作</th>-->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="feedBack" varStatus="s">
				<tr>
					<td>${s.index+1+page.pageSize*(page.pageNo-1)}</td>
					<td><c:if test="${empty feedBack.userId}">匿名</c:if> <c:if
							test="${!empty feedBack.userId}">${feedBack.userName }</c:if></td>
				<%-- 	<td>${feedBack.userName2 }</td>
					<td>${feedBack.email }</td> --%>
					<td>${feedBack.phone }</td>
				<%-- 	<td>${feedBack.qq }</td> --%>
					<td><span title="${feedBack.content}"><c:choose>
								<c:when test="${fn:length(feedBack.content) > 20}">
									<c:out value="${fn:substring(feedBack.content, 0, 20)}..." />
								</c:when>
								<c:otherwise>
									<c:out value="${feedBack.content} " />
								</c:otherwise>
							</c:choose> </span></td>
				<%-- 	<td>${feedBack.departmentName}</td> 
					<td> 
					</td>--%>
					<td>
					<fmt:formatDate value='${feedBack.createTime}' pattern="yyyy-MM-dd HH:mm:ss" />
					 </td>
				<%-- 	<td><c:if test="${feedBack.status==0}">未处理</c:if>
						<c:if test="${feedBack.status==1}">已处理</c:if></td> 
					<td>${feedBack.memo}</td>--%>
				<%-- 	<td><c:if test="${feedBack.status==0}">
							<a href="javaScript:void(0);" onclick="addMemoInfor('${feedBack.id}')">处理</a>
						</c:if>
					</td> --%>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination pagination-right">${page}</div>

	<%-- <div class="modal hide fade in" id="addmemo"
		style="overflow: hidden; top: 60%; width: 50%;">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h2>处理反馈</h2>
		</div>
		<form:form id="inputForm" modelAttribute="feedback"
			cssStyle="margin: 0;" action="${ctx}/admin/feedback/processFeedBack"
			method="post" class="form-horizontal">
			<div id="messageBox" class="alert alert-error" style="display: none">
				<fmt:message key="messageinfor"></fmt:message>
			</div>
			<form:hidden path="id" />
			<div class="modal-body">
				<div class="control-group">
					<label class="control-label">处理内容:</label>
					<div class="controls">
						<form:textarea path="memo" htmlEscape="false" cols="30"  rows="10"/>
					</div>
				</div>
			</div>
		</form:form>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal"><fmt:message
					key="close"></fmt:message></a> <input id="btnSubmit"
				class="btn btn-primary" type="button" onclick="addMemoSubmit()"
				value="确定" />
		</div>
	</div> --%>
</body>
</html>