<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="student_default"/>
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
		
		function alerts(mess, closed){
			top.$.jBox.info(mess, '评语', {closed:function(){
				if (typeof closed == 'function') {
					closed();
				}
			}});
			top.$('.jbox-body .jbox-icon').css('top','55px');
		}
		
	</script>
</head>
<body>
	
	<form:form id="searchForm" modelAttribute="studentReview" action="${ctx}/studentExam/myReview" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li>
				<label>老师：</label>
				<form:select path="teacher.id" class="input-medium">
					<form:option value="" label="请选择"/>
					<!-- 这个学生所有的老师 -->
					<form:options items="${student:getTeachersByStudentId() }" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn sbtn-primary" type="submit" value="查询"/></li>
		</ul>
	</form:form>
	<table class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width:20px;">序号</th>
				<th>评语</th>
				<th style="width: 120px;">时间</th>
				<th>教师</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list }" var="review" begin="0" varStatus="i" step="1">
				<tr>
					<td>${i.count}</td>
					<td style="text-align: left;word-break:break-all;color: #0088cc;" onclick="$('#a_${review.id}').toggle()">
					    <a>${fns:abbr(fns:replaceHtml(review.content),55)}</a>
					    <%--
						<div style="width:132px;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;margin: 0 auto;text-align: left;word-break:break-all;">
							<a href="javascript:alerts ('${fns:replaceHtml(review.content)}');"> ${fns:abbr(fns:replaceHtml(review.content),55)}</a>
						</div> --%>
					</td>
					<td>
						<fmt:formatDate value="${review.crateTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>${review.teacher.name }</td>
				</tr>
				<tr id="a_${review.id}" style="background:#fdfdfd;display:none;">
						<td colspan="4" style=" text-align: left;word-break:break-all;">${fns:replaceHtml(review.content)}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>