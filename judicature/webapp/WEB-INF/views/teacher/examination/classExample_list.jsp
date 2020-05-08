<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>作业列表</title>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<%@include file="/WEB-INF/views/teacher/include/date.jsp" %>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
		$(document).ready(function() {
			//发布例题
			 $("input[id^='publish_']").click(function(){
					var examId=$(this).attr("id").split("_")[1];
					var examClassId=$(this).attr("id").split("_")[2];
					window.location.href = ("${ctx}/classExample/publish?examId="+examId+"&examClassId="+examClassId);
				 });
			 //删除例题
			 $("input[id^='delete_']").click(function(){
						var examId=$(this).attr("id").split("_")[1];
						var examClassId=$(this).attr("id").split("_")[2];
					 return confirmx("确认要删除当前试卷吗？",function(){
						window.location.href = ("${ctx}/classExample/delete?examId="+examId+"&examClassId="+examClassId);
					 });
				 });
			 //查看例题
			 $("input[id^='update_']").click(function(){
						var examId=$(this).attr("id").split("_")[1];
						var examClassId=$(this).attr("id").split("_")[2];
						window.location.href = ("${ctx}/classExample/update?examId="+examId+"&examClassId="+examClassId);
				
				 });
		});
	</script>
</head>
<body>
<%--<fmt:formatDate value="${exam.beginDate}" pattern="yyyy-MM-dd"/> --%>
    <form:form id="searchForm" modelAttribute="exam" action="${ctx}/classExample/exampleList" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<%--  创建时间&nbsp;
		    <input id="beginTime" name="beginTime1"  type="text" class="input-medium Wdate"
				value="<fmt:formatDate value='${beginTime}'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> --%>
			
		班级：&nbsp;
		<form:select path="examClass.id"  class="input-small">
			<form:option value="">全部</form:option>
		   	<form:options items="${questionlib:getCourseQuestionlibByTeacherId()}" itemLabel="examClass.title" itemValue="examClass.id"  htmlEscape="false"/>
		 </form:select>
		例题名称：&nbsp;<input id="title" name="title" class="input-medium" value="${examTitle}" style="width: 100px;"/>
		<input id="btnExamSubmit" class="btn tbtn-primary" type="submit" value="搜索"/>
	</form:form>

	<%-- <sys:message content="${message}"/> --%>
	<form id="examForm" method="post">
		<table  id="treeTable" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th hidden="true" style="text-align: center;">ID</th>
					<th style="text-align: center; width: 120px; ">出题时间</th>
					<th style="text-align: center;">班级</th>
					<th hidden="true" style="text-align: center;">班级ID</th>
					<th style="text-align: center;">例题名称</th>
					<th style="text-align: center;">所属课程</th>
					<th style="text-align: center;">状态</th>
					<th style="text-align: center;">操作</th>
				</tr>
			</thead>
			<tbody>
			  <c:forEach items="${page.list}" var="list">
				<tr>
					<td hidden="true" style="text-align: center;">${list.id}</td>
					<td style="text-align: center;">
						<fmt:formatDate value="${list.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td style="text-align: center;">${list.examClass.title}</td>
					<td hidden="true" style="text-align: center;">${list.examClass.id}</td>
					<td style="text-align: center;">
					     ${list.title}
					</td>
					<td style="text-align: center;">${list.examCourse.title}</td>
					<td style="text-align: center;">
						<c:if test="${list.status eq '0'}">
							未发布
						</c:if>
						<c:if test="${list.status eq '1'}">
							正在进行
						</c:if>
						<c:if test="${list.status eq '2'}">
							已发布
						</c:if>
					</td>
					<td style="text-align: center;">
					<c:choose>
						<c:when test="${list.status eq '0'}">
							<input id='publish_${list.id}_${list.examClass.id}' style="background: url(${ctxStatic}/icon/fb.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="发布"/>
							<%-- <input id='update_${list.id}_${list.examClass.id}' class="btn btn-primary" type="button" value="修改"/> --%>
							<input id='delete_${list.id}_${list.examClass.id}' style="background: url(${ctxStatic}/icon/del.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="删除"/>
						</c:when>
						<c:otherwise>
							<input id='update_${list.id}_${list.examClass.id}' style="background: url(${ctxStatic}/icon/check_1.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="查看"/>
							<input id='delete_${list.id}_${list.examClass.id}' style="background: url(${ctxStatic}/icon/del.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="删除"/>
						</c:otherwise>
					</c:choose>
					</td>
				</tr>
			</c:forEach> 
			</tbody>
		</table>
	 </form>
	 <div class="pagination">${page}</div>
</body>
</html>