<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	
	<title>统计分析</title>
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
			$("input[id^='exam_']").click(function(){
				var id=$(this).attr("id").split("_")[1];
				var classId=$(this).attr("id").split("_")[2];
		 		window.location.href ='${ctx}/statistics/studentList?id='+id+'&examClass.id='+classId; 
			 });
		});
	</script>
	<style>
	.table th{color:#fff;background:#64dfda;background-image:-webkit-linear-gradient(top,#64dfda,#64dfda);}
	.btn-primary{background:#1c9993;background-image:-webkit-linear-gradient(top,#1c9993,#1c9993);}
	.btn-primary:hover,.btn-primary:focus,.btn-primary:active,.btn-primary.active,.btn-primary.disabled,.btn-primary[disabled] {color: #ffffff;background-color: #1c9993;*background-color: #1c9993;}
	.btn-primary:active,.btn-primary.active { background-color: #1c9993 \9;}
	.pagination{margin:18px auto ! important ;}
	</style>
</head>
<body>
    <form:form id="searchForm" modelAttribute="exam" action="${ctx}/statistics/list" method="post" class="breadcrumb form-search " style="padding-top:0;padding-left:20px;">
		<input id="pageNo" name="pageNo" type="hidden" value="${examList.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${examList.pageSize}"/>
		<ul class="ul-form">
			<li><label>班级：</label>
				<form:select path="examClass.id"  class="input-small">
					<form:option value="">全部</form:option>
				   	<form:options items="${questionlib:getCourseQuestionlibByTeacherId()}" itemLabel="examClass.title" itemValue="examClass.id"  htmlEscape="false"/>
				 </form:select>
			</li>
			<li><label>试卷名称：</label>
				<input id="title" name="title" class="input-medium" value="${exam.title}" style="width: 100px;"/>
			</li>
			<li><label>组卷方式：</label>
				<form:select path="examMode"  class="input-medium">
					<form:option value="">全部</form:option>
				   <form:options items="${fns:getDictList('examination_examMode')}" itemLabel="label" itemValue="value"  htmlEscape="false"/>
				 </form:select>
			</li>
			<li><label>试卷类型：</label>
				 <form:select path="examType"  class="input-medium">
					<form:option value="">全部</form:option>
				   	<%-- <form:options items="${fns:getDictList('examination_examtype')}" itemLabel="label" itemValue="value"  htmlEscape="false"/> --%>
				 	<form:option  value="2">组卷考试</form:option>
				   	<form:option  value="5">在线考试</form:option>
				 </form:select>
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
					<th style="text-align: center;  width: 120px; ">出题时间</th>
					<th style="text-align: center;">班级</th>
					<th hidden="true" style="text-align: center;">班级ID</th>
					<th style="text-align: center;">试卷名称</th>
					<!-- <th style="text-align: center;">所属课程</th> -->
					<th style="text-align: center;">试卷类型</th>
					<th style="text-align: center;">组卷方式</th>
					<!-- <th style="text-align: center;">状态</th> -->
					<th style="text-align: center;">操作</th>
				</tr>
			</thead>
			<tbody>
			  <c:forEach items="${examList.list}" var="list">
				<tr>
					<td hidden="true" style="text-align: center;">${list.id}</td>
					<td style="text-align: center;">
						<fmt:formatDate value="${list.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td style="text-align: center;">${list.examClass.title}</td>
					<td hidden="true" style="text-align: center;">${list.examClass.id}</td>
					<td style="text-align: center;">${list.title}</td>
					<%-- <td style="text-align: center;">${list.examCourse.title}</td> --%>
					<td style="text-align: center;">
						<%-- <c:if test="${list.examType eq '1'}">
							随堂练习
						</c:if>
						<c:if test="${list.examType eq '2'}">
							组卷考试
						</c:if>
						<c:if test="${list.examType eq '3'}">
							课后作业
						</c:if>
						<c:if test="${list.examType eq '5'}">
							在线考试
						</c:if>  --%>
						${fns:getDictLabels(list.examType,'examination_examtype','') }
					</td>
					<td style="text-align: center;">
						 ${fns:getDictLabels(list.examMode,'examination_examMode','') }
					</td>
					
					<%--<td style="text-align: center;">
						 <c:if test="${list.status eq '0'}">
							未发布
						</c:if>
						<c:if test="${list.status eq '1'}">
							正在进行
						</c:if>
						<c:if test="${list.status eq '2'}">
							已完成
						</c:if> 
						${fns:getDictLabels(list.status,'examination_status','') }
						
					</td>--%>
					<td style="text-align: center;">
						<input id='exam_${list.id}_${list.examClass.id}' style="background: url(${ctxStatic}/icon/check_1.png) no-repeat;width:20px;height:20px;display:inline-block;background-size:100%;cursor:pointer;"  type="button" title="查看"/>
					</td>
				</tr>
			</c:forEach> 
			</tbody>
		</table>
	 </form>
	 <div class="pagination">${examList}</div>
</body>
</html>