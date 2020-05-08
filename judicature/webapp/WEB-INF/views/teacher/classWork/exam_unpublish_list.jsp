<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>未发布的随堂练习</title>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<%@include file="/WEB-INF/views/teacher/include/date.jsp" %>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchunpublishForm").submit();
	    	return false;
	    }
		$(document).ready(function() {
			//发布随堂练习
			$("input[id^='examSubmit_']").click(function(){
				var examId=$(this).attr("id").split("_")[1];
				var classId=$(this).attr("id").split("_")[2];
		 		window.location.href = '${ctx}/classWork/updatePublish?examId='+examId+'&examClassId='+classId; 
			 });
			//结束随堂练习
			$("input[id^='examEnd_']").click(function(){
				var id=$(this).attr("id").split("_")[1];
				var examClassId=$(this).attr("id").split("_")[2];
		 		window.location.href ='${ctx}/classWork/submitExam?examId='+id+'&examClassId='+examClassId;
			 });
			//跳至监控界面
			 $("input[id^='examMonitor_']").click(function(){
					var id=$(this).attr("id").split("_")[1];
					var examClassId=$(this).attr("id").split("_")[2];
			 		window.location.href ='${ctx}/classWork/examMonitor?examId='+id+'&examClassId='+examClassId;
				 });
			//删除随堂练习
			 $("input[id^='examDelete_']").click(function(){
					var id=$(this).attr("id").split("_")[1];
					var examClassId=$(this).attr("id").split("_")[2];
			 		window.location.href ='${ctx}/classWork/deleteExerciseExam?examId='+id+'&examClassId='+examClassId;
				 });
			//修改随堂练习
			 $("input[id^='updateExercise_']").click(function(){
					var id=$(this).attr("id").split("_")[1];
					var examClassId=$(this).attr("id").split("_")[2];
			 		window.location.href ='${ctx}/exerciseExamination/updateExercise?id='+id+'&classDataRelation='+examClassId;
				 });
		});
		
	</script>
</head>
<body>
	 <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/classWork/unpublishList">未完成的随堂练习</a></li>
		<li><a href="${ctx}/classWork/publishList">已完成的随堂练习</a></li>
	</ul> 
    <form:form id="searchunpublishForm" modelAttribute="exam" action="${ctx}/classWork/unpublishList" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${examList.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${examList.pageSize}"/>
		<ul class="ul-form">
			<li><label>创建时间：</label>
				<input id="beginTime" name="beginTime"  style="width: 130px;" type="text" class="input-medium Wdate"
				value="<fmt:formatDate value='${beginTime}'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" readonly></input>
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
				&nbsp;&nbsp;<input id="btnExamSubmit" class="btn tbtn-primary" type="submit" value="搜索"/>
			</li>
		</ul>
	</form:form>

	<%-- <sys:message content="${message}"/> --%>
	<form id="unpublishForm" modelAttribute="exam" method="post">
		<table  id="treeTable" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th hidden="true" style="text-align: center;">班级ID</th>
					<th hidden="true" style="text-align: center;">练习ID</th>
					<th style="text-align: center; width: 120px;">创建时间</th>
					<th style="text-align: center;">练习用时</th>
					<th style="text-align: center;">班级</th>
					<th style="text-align: center;">练习名称</th>
					<th style="text-align: center;">操作</th>
				</tr>
				
			</thead>
			<tbody>
			  <c:forEach items="${examList.list}" var="list">
				<tr>
					<td hidden="true" style="text-align: center;">${list.examClass.id}</td>
					<td hidden="true" style="text-align: center;">${list.id}</td>
					<td style="text-align: center;">
						<fmt:formatDate value="${list.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td style="text-align: center;">${list.examHours}分钟</td>
					<td style="text-align: center;">${list.examClass.title}</td>
					<td style="text-align: center;">${list.title}</td>
					<td style="text-align: center;">
					<c:if test="${list.status eq '0'}">
						<input id="examSubmit_${list.id}_${list.examClass.id}" style="background: url(${ctxStatic}/icon/fb.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" type="button" title="发布"/>
						<input id="updateExercise_${list.id}_${list.examClass.id}" style="background: url(${ctxStatic}/icon/xg.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="修改"/>
						<input id="examDelete_${list.id}_${list.examClass.id}" style="background: url(${ctxStatic}/icon/del.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="删除"/>
					</c:if>
					<c:if test="${list.status eq '1'}">
						<input id="examEnd_${list.id}_${list.examClass.id}" style="background: url(${ctxStatic}/icon/sj.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="收卷"/>
						<input id="examMonitor_${list.id}_${list.examClass.id}" style="background: url(${ctxStatic}/icon/jk.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="随堂监控"/>
					</c:if>
					</td>
				</tr>
			</c:forEach> 
			</tbody>
		</table>
	 </form>
	 <div class="pagination">${examList}</div>
	 
</body>
</html>