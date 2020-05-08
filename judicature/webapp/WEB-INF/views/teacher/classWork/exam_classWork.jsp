<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/teacher/include/date.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>随堂管理</title>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("input[id^='publishSubmit_']").click(function(){
				var examClassId=$(this).attr("id").split("_")[1];
				var examId=$(this).attr("id").split("_")[2];
		 		window.location.href = '${ctx}/classWork/examDetail?examClassId='+examClassId+'&examId='+examId; 
			 }); 
			$("input[id^='unpublishSubmit_']").click(function(){
				var examId=$(this).attr("id").split("_")[1];
				var examClassId=$(this).attr("id").split("_")[2];
		 		window.location.href = '${ctx}/classWork/updatePublish?examId='+examId+'&limit='+1+'&examClassId='+examClassId; 
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
   
	<font size="4px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;未发布的随堂练习</font>
	<form id="unpublishForm" method="post">
		<table  id="unpublishTable" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th hidden="true" style="text-align: center;">班级ID</th>
					<th hidden="true" style="text-align: center;">练习ID</th>
					<th style="text-align: center;">出题时间</th>
					<th style="text-align: center;">班级</th>
					<th style="text-align: center;">练习名称</th>
					<th style="text-align: center;">操作</th>
				</tr>
				
			</thead>
			<tbody>
			  <c:forEach items="${unpublishList.list}" var="list">
				<tr>
					<td hidden="true" style="text-align: center;">${list.examClass.id}</td>
					<td hidden="true" style="text-align: center;">${list.id}</td>
					<td style="text-align: center;">
						<fmt:formatDate value="${list.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td style="text-align: center;">${list.examClass.title}</td>
					<td style="text-align: center;">${list.title}</td>
					<td style="text-align: center;">
						<input id="unpublishSubmit_${list.id}_${list.examClass.id}" class="btn tbtn-primary" type="button" value="发布"/>
					</td>
				</tr>
			</c:forEach> 
			</tbody>
		</table>
		<div align="right">
			<font size="5px;">
				<a href="${ctx}/classWork/unpublishList">更多&gt;&gt;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
			</font>
		</div>
	 </form>
	 
	<font size="4px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已完成的随堂练习</font>
	<form id="publishForm" method="post">
		<table  id="publishTable" class="table table-striped table-bordered table-condensed" >
			<thead>
				<tr>
					<th hidden="true" style="text-align: center;">班级ID</th>
					<th hidden="true" style="text-align: center;">练习ID</th>
					<th style="text-align: center;">出题时间</th>
					<th style="text-align: center;">班级</th>
					<th style="text-align: center;">练习名称</th>
					<th style="text-align: center;">预答题人数</th>
					<th style="text-align: center;">未答题人数</th>
					<th style="text-align: center;">操作</th>
				</tr>
				
			</thead>
			<tbody>
			  <c:forEach items="${publishList.list}" var="list">
				<tr>
					<td hidden="true" style="text-align: center;">${list.examClass.id}</td>
					<td hidden="true" style="text-align: center;">${list.id}</td>
					<td style="text-align: center;">
						<fmt:formatDate value="${list.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td style="text-align: center;">${list.examClass.title}</td>
					<td style="text-align: center;">
					    <a href="${ctx }/statistics/staticKnowledgeByexamIdAndClassId?examId=${list.id }&classId=${list.examClass.id}&backUrl=${currentUrl}">
					      ${list.title}
					    </a>
					</td>
					<td style="text-align: center;">${list.totalPerson}</td>
					<td style="text-align: center;">${list.unSubmitPerson}</td>
					<td style="text-align: center;">
						<input id="publishSubmit_${list.examClass.id}_${list.id}" style="background: url(${ctxStatic}/icon/check.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" type="button" title="查看答题情况"/>
						<input id="unSubmitPerson_${list.examClass.id}_${list.id}" style="background: url(${ctxStatic}/icon/check_1.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;" type="button" title="查看未答题人员"/>
					</td>
				</tr>
			</c:forEach> 
			</tbody>
		</table>
		<div align="right">
			<font size="5px;">
				<a href="${ctx}/classWork/publishList">更多&gt;&gt;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
			</font>
		</div>
	 </form>	 
	 
</body>
</html>