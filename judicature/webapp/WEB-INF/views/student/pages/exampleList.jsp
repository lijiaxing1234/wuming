<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="student_default"/>
<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<title>例题管理</title>
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
	<style>
	.table th{color:#fff;background:#64dfda;background-image:-webkit-linear-gradient(top,#64dfda,#64dfda);}
	.btn-primary{background:#1c9993;background-image:-webkit-linear-gradient(top,#1c9993,#1c9993);}
	.btn-primary:hover,.btn-primary:focus,.btn-primary:active,.btn-primary.active,.btn-primary.disabled,.btn-primary[disabled] {color: #ffffff;background-color: #1c9993;*background-color: #1c9993;}
	.btn-primary:active,.btn-primary.active { background-color: #1c9993 \9;}
	</style>
	
	<style>
.table-bordered td{letter-spacing:0.5px;}
</style>
</head>
<body>
	<form:form id="searchForm" modelAttribute="SExam" action="${ctx}/studentExam/exampleList" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input name="examType" type="hidden" value="4" >
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<!--  
			<li>
				<label>发布人:</label>
				<form:select path="teacher.id" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${student:getTeachersByStudentId() }" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			-->
			<li>
				<label >开始时间：</label>
				<input name="firstTime" type="text" class="input-medium Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" > --
				<input name="secondTime" type="text" class="input-medium Wdate" value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true});" >
			</li>
			<li class="btns"><input id="btnSubmit" class="btn sbtn-primary" type="submit" value="查询"/></li>
		</ul>
	</form:form>
	<sys:message content="${message }"></sys:message>
	<table id="" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width: 120px;">创建时间</th>
				<th>发布人</th>
				<th>名称</th>
				<th  style="width: 80px;">习题讲解</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="exam">
				<tr>
					<td><fmt:formatDate value="${exam.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>${exam.teacher.name }</td>
					<td>${exam.title }</td>
					<td style="text-align: center;"><a href="${ctx}/studentExam/searchExamQuestions?examId=${exam.id }&examType=4&&examDetailId=${exam.examDetailId}" style="display:inline-block; background: url(${ctxStatic}/icon/check.png) no-repeat;width:20px;height:20px;background-size:100%;cursor:pointer;" title="查看"></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>