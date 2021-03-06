<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>试卷管理</title>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<%@include file="/WEB-INF/views/teacher/include/date.jsp" %>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#testpaperForm").submit();
	    	return false;
	    }
		 $(document).ready(function() {
			 $("input[id^='examSubmit_']").click(function(){
				var examId=$(this).attr("id").split("_")[2];
			 	var examClassId=$(this).attr("id").split("_")[1];
			 	var type=$(this).attr("id").split("_")[3];
		 		window.location.href = '${ctx}/examScore/examOnLineScore?&examId='+examId+'&examClassId='+examClassId+'&type='+type; 
			 }); 
			 $("input[id^='saveSubmit_']").click(function(){
				 	var examClassId=$(this).attr("id").split("_")[1];
					var examId=$(this).attr("id").split("_")[2];
			 		window.location.href = '${ctx}/examScore/allStudent?&examId='+examId+'&examClassId='+examClassId; 
				 });
		}); 
	</script>
</head>
<body>
	<%-- <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/examScore/examScoreList">线下考试管理</a></li>
		<li><a href="${ctx}/examScore/examOnLineScoreList">在线考试管理</a></li>
	</ul> --%>
	<sys:message content="${message}"/>
    <form:form id="testpaperForm" modelAttribute="exam" action="${ctx}/examScore/examScoreList" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${examList.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${examList.pageSize}"/>
		<ul class="ul-form">
			<li><label>开始时间：</label>
				  <input id="beginTime" name="beginTime" style="width: 130px;" type="text" class="input-medium Wdate"
					value="<fmt:formatDate value='${beginTime}'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
					onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}'})" readonly></input>
			</li>
			<li><label>结束时间：</label>
				 <input id="endTime" name="endTime" style="width: 130px;" type="text" class="input-medium Wdate"
					value="<fmt:formatDate value='${endTime}'/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"
					 onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}'})" readonly></input>
			</li>
			<li><label>班级：</label>
				<form:select path="examClass.id"  class="input-small">
					<form:option value="">全部</form:option>
				   	<form:options items="${questionlib:getCourseQuestionlibByTeacherId()}" itemLabel="examClass.title" itemValue="examClass.id"  htmlEscape="false"/>
				 </form:select>
			</li>
			<li><label>试卷名称：</label>
				<input id="title" name="title" class="input-medium" value="${examTitle}" style="width: 100px;"/>
			</li>
			<li>
				&nbsp;&nbsp;<input id="btnTestPaperSubmit" class="btn tbtn-primary" type="submit" value="搜索"/>
			</li>
		</ul>
	</form:form>

	<form id="examForm" method="post">
		<table  id="treeTable" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<!-- <th style="text-align: center;">出题时间</th> -->
					<th style="text-align: center; width: 120px; ">开始时间</th>
					<th style="text-align: center; width: 120px; ">结束时间</th>
					<th style="text-align: center;" hidden="true">班级ID</th>
					<th style="text-align: center;">班级</th>
					<th style="text-align: center;" hidden="true">试卷ID</th>
					<th style="text-align: center;">试卷名称</th>
					<!-- <th style="text-align: center;">所属课程</th>
					<th style="text-align: center;">试卷性质</th>
					<th style="text-align: center;">状态</th> -->
					<th style="text-align: center;">成绩维护</th>
					<!-- <th style="text-align: center;">补考</th> -->
				</tr>
			</thead>
			<tbody>
			  <c:forEach items="${examList.list}" var="list">
				<tr>
					<td style="text-align: center;">
						<fmt:formatDate value="${list.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td style="text-align: center;">
						<fmt:formatDate value="${list.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td style="text-align: center;" hidden="true">${list.examClass.id}</td>
					<td style="text-align: center;">${list.examClass.title}</td>
					<td style="text-align: center;" hidden="true">${list.id}</td>
					<td style="text-align: center;">${list.title}</td>
					<%-- <td style="text-align: center;">${list.examCourse.title}</td>
					<td id="examType" style="text-align: center;">
						<c:if test="${list.examType eq '1'}">
							随堂测试
						</c:if>
						<c:if test="${list.examType eq '2'}">
							组卷考试
						</c:if>
						<c:if test="${list.examType eq '3'}">
							作业
						</c:if>
						<c:if test="${list.examType eq '4'}">
							例题
						</c:if>
						<c:if test="${list.examType eq '5'}">
							在线考试
						</c:if>
					</td>
					<td style="text-align: center;">
						<c:if test="${list.status eq '0'}">
							未发布
						</c:if>
						<c:if test="${list.status eq '1'}">
							<!-- 正在进行 -->
							未录入成绩
						</c:if>
						<c:if test="${list.status eq '2'}">
							已完成
						</c:if>
					</td> --%>
					<td style="text-align: center;">
						<c:if test="${list.examType eq '5'}">
							<input id="examSubmit_${list.examClass.id}_${list.id}_no" style="background: url(${ctxStatic}/icon/check_1.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="查看"/>
						</c:if>
						<c:if test="${list.examType eq '2'}">
							<c:if test="${list.status eq '2'}">
								<input id="examSubmit_${list.examClass.id}_${list.id}_no" style="background: url(${ctxStatic}/icon/check_1.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="查看"/>
								<input id="saveSubmit_${list.examClass.id}_${list.id}_no" style="background: url(${ctxStatic}/icon/jq.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="修改成绩"/>
							</c:if>
						</c:if>
						<c:if test="${list.examType eq '2'}">
							<c:if test="${list.status eq '1'}">
								<input id="saveSubmit_${list.examClass.id}_${list.id}_no" style="background: url(${ctxStatic}/icon/jr_1.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="录入成绩"/>
							</c:if>
						</c:if>
					</td>
					<!-- <td style="text-align: center;">补考人数/补考完成</td> -->
				</tr>
			</c:forEach> 
			</tbody>
		</table>
	 </form>
	 <div class="pagination">${examList}</div>
	 
</body>
</html>