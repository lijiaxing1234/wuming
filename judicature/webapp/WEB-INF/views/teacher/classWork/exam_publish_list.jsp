<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>已发布的随堂练习</title>
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
			$("input[id^='publishSubmit_']").click(function(){
				var examClassId=$(this).attr("id").split("_")[1];
				var examId=$(this).attr("id").split("_")[2];
		 		window.location.href = '${ctx}/classWork/examDetail?examClassId='+examClassId+'&examId='+examId; 
			 }); 
			$("input[id^='unSubmitPerson_']").click(function(){
				var examId=$(this).attr("id").split("_")[2];
				var examClassId=$(this).attr("id").split("_")[1];
		 		window.location.href = '${ctx}/classWork/studentUnSubmitExam?examId='+examId+'&examClassId='+examClassId; 
			 });
			 $("input[id^='exam2_']").click(function(){
				var examId=$(this).attr("id").split("_")[1];
				var examClassId=$(this).attr("id").split("_")[2];
				window.location.href = ('${ctx}/teacherStudentAnswer/examStudentList?examId='+examId+'&examClassId='+examClassId+'&status=exerciseExam');
			 });
		});
		
	</script>
</head>
<body>
  <ul class="nav nav-tabs">
		<li><a href="${ctx}/classWork/unpublishList">未完成的随堂练习</a></li>
		<li class="active"><a href="${ctx}/classWork/publishList">已完成的随堂练习</a></li>
	</ul> 
	<sys:message content="${message}"/>
    <form:form id="searchForm" modelAttribute="exam" action="${ctx}/classWork/publishList" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${examList.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${examList.pageSize}"/>
		<ul class="ul-form">
			<li><label>发布时间：</label>
				<input id="beginTime" name="beginTime"  type="text" class="input-medium Wdate"
				value="<fmt:formatDate value='${beginTime}'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" readonly></input>
			</li>
			<li><label>班级：</label>
				<form:select path="examClass.id"  class="input-small">
					<form:option value="">全部</form:option>
				   	<form:options items="${questionlib:getCourseQuestionlibByTeacherId()}" itemLabel="examClass.title" itemValue="examClass.id"  htmlEscape="false"/>
				 </form:select>
			</li>
			<li><label>练习名称：</label>
				<input id="title" name="title" class="input-medium" value="${examTitle}" style="width: 100px;"/>
			</li>
			<li>
				&nbsp;&nbsp;<input id="publishSubmit" class="btn tbtn-primary" type="submit" value="搜索"/>
			</li>
		</ul>
		
	</form:form>
	<form id="examForm" modelAttribute="exam" method="post">
		<table  id="treeTable" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th hidden="true" style="text-align: center;">班级ID</th>
					<th hidden="true" style="text-align: center;">练习ID</th>
					<th style="text-align: center; width: 120px;">发布时间</th>
					<th style="text-align: center; width: 120px;" >练习用时</th>
					<th style="text-align: center;">班级</th>
					<th style="text-align: center;">练习名称</th>
					<th style="text-align: center;">全班总人数</th>
					<th style="text-align: center;">未答题人数</th>
					<th style="text-align: center;">操作</th>
				</tr>
			</thead>
			<tbody>
			  <c:forEach items="${examList.list}" var="list">
				<tr>
					<td hidden="true" style="text-align: center;">${list.examClass.id}</td>
					<td hidden="true" style="text-align: center;">${list.id}</td>
					<td style="text-align: center;">
						<fmt:formatDate value="${list.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td style="text-align: center;">${list.examHours}分钟</td>
					<td style="text-align: center;">${list.examClass.title}</td>
					<td style="text-align: center;">
						<c:if test="${list.isTeacher eq '1'}">
						 	<a href="${ctx }/statistics/staticKnowledgeByexamIdAndClassId?examId=${list.id }&classId=${list.examClass.id}&backUrl=${currentUrl}">
						      ${list.title}
						    </a>
							
						</c:if>
						<c:if test="${list.isTeacher eq null}">
							 ${list.title}
						</c:if>
					   
					</td>
					<td style="text-align: center;">${list.totalPerson}</td>
					<td style="text-align: center;">${list.unSubmitPerson}</td>
					<td style="text-align: center;">
						<c:if test="${list.isTeacher eq '1'}">
							<a href="${ctx }/statistics/staticKnowledgeByexamIdAndClassId?examId=${list.id }&classId=${list.examClass.id}&backUrl=${currentUrl}"  style="background: url(${ctxStatic}/icon/tji.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  title="统计"> </a>
							 
							<%-- <input id="publishSubmit_${list.examClass.id}_${list.id}" class="btn tbtn-primary" type="button" value="查看答题情况"/> --%>
							<input id="publishSubmit_${list.examClass.id}_${list.id}" style="background: url(${ctxStatic}/icon/check.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" type="button" title="查看答题情况">
						</c:if>
						<c:if test="${list.isTeacher eq null}">
						<%-- 	<input id="exam2_${list.id}_${list.examClass.id}" class="btn tbtn-primary" type="button" value="判卷"/> --%>
							<input id="exam2_${list.id}_${list.examClass.id}" style="background: url(${ctxStatic}/icon/jq.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" type="button" title="判卷">
						</c:if>
						<%-- <input id="unSubmitPerson_${list.examClass.id}_${list.id}" class="btn tbtn-primary" type="button" value="查看未答题人员"/> --%>
						<input id="unSubmitPerson_${list.examClass.id}_${list.id}" style="background: url(${ctxStatic}/icon/check_1.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" type="button" title="查看未答题人员">
					</td>
				</tr>
			</c:forEach> 
			</tbody>
		</table>
	 </form>
	 <div class="pagination">${examList}</div>
	 
</body>
</html>