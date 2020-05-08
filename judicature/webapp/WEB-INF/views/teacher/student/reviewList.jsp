<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="teacher_default"/>
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<title>我的评语</title>
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
	
	<form:form id="searchForm" modelAttribute="studentReview" action="${ctx}/examination/reviewList?studentId=${ studentReview.studentId}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<%-- <ul class="ul-form">
			<li>
				<label>老师</label>
				<form:select path="teacher.id" class="input-medium">
					<form:option value="" label=""/>
					<!-- 这个学生所有的老师 -->
					<form:options items="${student:getTeachersByStudentId() }" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn tbtn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul> --%>
	</form:form>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width:20px;">序号</th>
				<th>评语</th>
				<th  width: 120px; >时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="review" begin="0" varStatus="i" step="1">
				<tr>
					<td>${i.count}</td>
					<td  style="text-align: left;word-break:break-all;" onclick="$('#a_${review.id}').toggle()">
					      ${fns:abbr(fns:replaceHtml(review.content),60)} 
					</td>
					<td >
						<fmt:formatDate value="${review.crateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
					 	<a href="${ctx}//examination/reviewList/delete?id=${review.id}&studentId=${ studentReview.studentId}" onclick="return confirmx('确认要删除吗？', this.href)" style="background: url(${ctxStatic}/icon/del.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  title="删除"></a>
					</td>
				</tr>
				<tr id="a_${review.id}" style="background:#fdfdfd;display:none;">
				   <td colspan="4" style=" text-align: left;word-break:break-all;">${fns:replaceHtml(review.content)}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
	
	<div class="form-actions pagination-left">
		 <a href="${ctx}/examination/student?repage" class="btn tbtn-primary">返回</a>
	</div>
</body>
</html>