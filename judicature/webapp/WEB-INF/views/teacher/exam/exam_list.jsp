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
			//查看详情
			$("input[id^='exam_']").click(function(){
				var id=$(this).attr("id").split("_")[1];
				var classId=$(this).attr("id").split("_")[2];
				var status=$(this).attr("id").split("_")[3];
		 		window.location.href ='${ctx}/exam/examClassList?examId='+id+'&schoolClassId='+classId+'&status='+status; 
			 });
			//发布作业
			$("input[id^='publish_']").click(function(){
				var id=$(this).attr("id").split("_")[1];
				var examClassId=$(this).attr("id").split("_")[2];
		 		window.location.href ='${ctx}/exam/publishExam?examId='+id+'&examClassId='+examClassId; 
			 });
			//收作业
			$("input[id^='submit_']").click(function(){
				var id=$(this).attr("id").split("_")[1];
				var examClassId=$(this).attr("id").split("_")[2];
		 		window.location.href ='${ctx}/exam/submit?examId='+id+'&examClassId='+examClassId; 
			 });
			//删除作业
			 $("input[id^='delete_']").click(function(){
					var id=$(this).attr("id").split("_")[1];
					var examClassId=$(this).attr("id").split("_")[2];
					 return confirmx("确认要删除当前试卷吗？",function(){
						 window.location.href ='${ctx}/exam/deleteExerciseExam?examId='+id+'&examClassId='+examClassId; 
						});
			 		
				 });
			//修改试卷
			 $("input[id^='update_']").click(function(){
					var examId=$(this).attr("id").split("_")[1];
					window.location.href = ("${ctx}/examination/adjustExam?id="+examId);
				 });
			//判作业
			 $("input[id^='exam2_']").click(function(){
					var examId=$(this).attr("id").split("_")[1];
					var examClassId=$(this).attr("id").split("_")[2];
					window.location.href = ('${ctx}/teacherStudentAnswer/examStudentList?examId='+examId+'&examClassId='+examClassId+'&status=homeWork');
				 });
			 $("input[id^='unSubmitPerson_']").click(function(){
					var examId=$(this).attr("id").split("_")[2];
					var examClassId=$(this).attr("id").split("_")[1];
			 		window.location.href = '${ctx}/classWork/studentUnSubmitExam?examId='+examId+'&examClassId='+examClassId; 
				 });
		});
	</script>
</head>
<body>
 <ul class="nav nav-tabs">
	<li><a href="${ctx}/exam/examListUnPublish">未完成的课后作业</a></li>
	<li class="active"><a href="${ctx}/exam/examList">已完成的课后作业</a></li>
	<%-- <li><a href="${ctx}/exam/examListOvertimeUnPublish">超时未发布的课后作业</a></li> --%>
</ul> 
 <sys:message content="${message}" />
    <form:form id="searchForm" modelAttribute="exam" action="${ctx}/exam/examList" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${examList.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${examList.pageSize}"/>
		<ul class="ul-form">
			<li><label>发布时间：</label>
				 <input id="beginTime" name="beginTime"  type="text" class="input-medium Wdate"
				value="<fmt:formatDate value='${beginTime}'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"
				onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}'})" readonly></input>
			</li>
			<li><label>结束时间：</label>
				<input id="endTime" name="endTime"  type="text" class="input-medium Wdate"
				value="<fmt:formatDate value='${endTime}'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"
				 onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly></input>
			</li>
			<li><label>班级：</label>
				<form:select path="examClass.id"  class="input-small">
					<form:option value="">全部</form:option>
				   	<form:options items="${questionlib:getCourseQuestionlibByTeacherId()}" itemLabel="examClass.title" itemValue="examClass.id"  htmlEscape="false"/>
				 </form:select>
			</li>
			<li><label>作业名称：</label>
				<input id="title" name="title" class="input-medium" value="${examTitle}" style="width: 100px;"/>
			</li>
			<li>
				&nbsp;&nbsp;<input id="btnExamSubmit" class="btn tbtn-primary" type="submit" value="搜索"/>
			</li>
		</ul>
	</form:form>

	<%-- <sys:message content="${message}"/> --%>
	<form id="examForm" method="post">
		<table  id="treeTable" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th hidden="true" style="text-align: center;">ID</th>
					<th style="text-align: center;">发布时间</th>
					<th style="text-align: center;">结束时间</th>
					<th style="text-align: center;">公布答案时间</th>
					<th style="text-align: center;">班级</th>
					<th hidden="true" style="text-align: center;">班级ID</th>
					<th style="text-align: center;">作业名称</th>
					<!-- <th style="text-align: center;">所属课程</th> -->
					<th style="text-align: center;">未答题人数</th>
					<!-- <th style="text-align: center;">状态</th> -->
					<th style="text-align: center;">操作</th>
				</tr>
			</thead>
			<tbody>
			  <c:forEach items="${examList.list}" var="list">
				<tr>
					<td hidden="true" style="text-align: center;">${list.id}</td>
					<td style="text-align: center;">
						<fmt:formatDate value="${list.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td style="text-align: center;">
						<fmt:formatDate value="${list.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td style="text-align: center;">
						<fmt:formatDate value="${list.publishAnswerTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td style="text-align: center;">${list.examClass.title}</td>
					<td hidden="true" style="text-align: center;">${list.examClass.id}</td>
					<td style="text-align: center;">
					<c:choose>
						<c:when test="${list.status eq '2'}">
							<c:if test="${list.isTeacher eq '1'}">
								 <a href="${ctx }/statistics/staticKnowledgeByexamIdAndClassId?examId=${list.id }&classId=${list.examClass.id}&backUrl=${currentUrl}">
							      ${list.title}
							    </a>
							</c:if>
							<c:if test="${list.isTeacher eq null}">
								 ${list.title}
							</c:if>
						</c:when>
						<c:otherwise>
							 ${list.title}
						</c:otherwise>
					</c:choose>
					</td>
					<%-- <td style="text-align: center;">${list.examCourse.title}</td> --%>
					<td style="text-align: center;">
						<c:if test="${list.status eq '2'}">
							${list.unSubmitPerson}
						</c:if>
					</td>
					<%-- <td style="text-align: center;">
						<c:if test="${list.status eq '0'}">
							超时未进行发布
						</c:if>
						<c:if test="${list.status eq '1'}">
							正在进行
						</c:if>
						<c:if test="${list.status eq '2'}">
							已完成
						</c:if>
					</td> --%>
					<td style="text-align: center;">
						<c:choose>
						<c:when test="${list.status eq '0'}">
							<input id='ExamQuerySubmit_${list.id}'  style="background: url(${ctxStatic}/icon/check.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="查看试卷"/>
						</c:when>
						<c:otherwise>
							<c:if test="${list.isTeacher eq '1'}">
								<a href="${ctx }/statistics/staticKnowledgeByexamIdAndClassId?examId=${list.id }&classId=${list.examClass.id}&backUrl=${currentUrl}"   style="background: url(${ctxStatic}/icon/tji.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  title="统计"></a>
								<input id="exam_${list.id}_${list.examClass.id}_${list.status}"  style="background: url(${ctxStatic}/icon/check.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="查看答题情况"/>
							</c:if>
							<c:if test="${list.isTeacher eq null}">
								<input id="exam2_${list.id}_${list.examClass.id}"  style="background: url(${ctxStatic}/icon/jq.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" type="button" title="判卷"/>
							</c:if>
						</c:otherwise>
					</c:choose>
					</td>
				</tr>
			</c:forEach> 
			</tbody>
		</table>
	 </form>
	 <div class="pagination">${examList}</div>
</body>
</html>