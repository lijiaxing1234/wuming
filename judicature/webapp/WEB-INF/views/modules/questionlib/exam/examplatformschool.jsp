<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>试卷管理</title>
	<meta name="decorator" content="default"/>
	
	<script type="text/javascript" src="${ctxStatic}/modules/questionlib/js/questionlibSelect.js"></script>
	<script type="text/javascript">
	
		 function chengeCourse(ids,names){
			 $.utils.BindSelect("courseId",[]);
			 $.utils.BindSelect("courseVesionSelect",[]);
			 $.utils.BindSelect("courseId", "/questionlib/exam/findSchoolCourseBySpecialtyId?specialtyId="+ ids[0]);
		 }
		 
		 $(document).ready(function() {
			  $("#courseId").off("change").on("change",function(e){
					 var selectId=e.val;
					 $.utils.BindSelect("courseVesionSelect", "/questionlib/exam/findSchoolCourseVersionByCourseId?courseId="+selectId);
			   }); 
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
	<ul class="nav nav-tabs">
		<li class="active"><a href="#">试卷列表</a></li>
	</ul>
	 <sys:message content="${message}" />
	<form:form id="searchForm" modelAttribute="examDTO" action="${ctx}/questionlib/exam/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<!-- 老师，考试名称，课程名称，版本名称 -->
			<%-- <li>
				<label>老师：</label>
				<form:input path="teacherName" htmlEscape="false" maxlength="100" class="input-medium"/>				
			</li> --%>
			<li>
				<label>考试类型：</label>
				<form:select path="examType" class="input-medium">
					<form:option value="" label="请选择"/>
					<%-- <form:option value="1" label="随堂练习"/>
					<form:option value="2" label="组卷考试"/>
					<form:option value="5" label="在线考试"/>
					<form:option value="3" label="课后作业"/>
					<form:option value="4" label="随堂例题"/> --%>
					<form:options items="${fns:getDictList('examination_examtype')}"  itemLabel="label" itemValue="value" /> 
				</form:select>
			</li>
			
			<li>
				<label>试卷名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			
			<li><label>专业： </label>
				<sys:treeselect id="specialty" ontextchange="true" name="specialtyId" value="${examDTO.specialtyId}" labelName="specialtyName" labelValue="${examDTO.specialtyName}"
					title="专业" url="/questionlib/exam/findSchoolSpecialty" cssClass="required" />
					
			</li>
			<li><label>课程：</label>
				<select  name="courseId" id="courseId" style="width:150px;">
				    <option value="">请选择</option>
				    <c:forEach items="${ empty examDTO.specialtyId ? null: (questionlib:findCourseBySpecialtyId(examDTO.specialtyId))}" var="course">
						<option value="${course.id}" ${examDTO.courseId==course.id?"selected":""}  >${course.title} </option>
					</c:forEach>
			   </select>
			</li>
			<li><label>版本：</label>
				<select   name="versionId" id="courseVesionSelect"  class="input-medium" >
			 	  <option value="">请选择</option>
			 	  <c:forEach items="${empty examDTO.courseId ? null : questionlib:findCourseVersionByCourseId(examDTO.courseId)}" var="courseVesion">
				   		<option value="${courseVesion.id}" ${examDTO.versionId==courseVesion.id?"selected":""}  >${courseVesion.title} </option>
				  </c:forEach>
			  </select>
			</li>
			
			
		<%--<c:if test="${examDTO.schoolName eq '化工出版社' }">
				<li>
					<label>学校：</label>
			 		<select style="width:350px"  name="schoolId" id="schoolId"> <!-- class="input-medium" -->
		 	     		<option value="" >请选择</option>  
		 	     		<c:forEach items="${schoolList}" var="school">
				   		<option value="${school.id}" ${examDTO.schoolId==school.id?"selected":""} } >${school.name} </option>
				 		</c:forEach>
			 	</select>
			 </li> </c:if>--%>
			 
			 
			<c:if test="${(not empty schoolList) and (fn:length(schoolList) > 0 ) }">
			   <li>
			     <label>学校：</label>
			    <form:select path="schoolId"  cssStyle="width:350px">
			      <form:option value="">请选择</form:option>
			      <form:options itemLabel="name" itemValue="id" items="${schoolList}"/>
			    </form:select>
			   </li>
			</c:if>
			
			
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
		
		
		<%-- <ul class="ul-form">
			<li><label>专业：</label>
				<sys:treeselect id="specialty" ontextchange="true" name="specialty.id" value="${course.specialty.id}" labelName="specialty.name" labelValue="${course.specialty.title}"
					title="专业" url="/questionlib/specialty/treeData" cssClass="required"/>
			</li>
			<li><label>课程：</label>
				 <select class="courseSelect" name="courseId" id="courseId" style="width:150px;" onchange="loadVersion(this);">
			   </select>
			</li>
			<li><label>版本：</label>
				<select   name="versionId" id="courseVesionSelect"  class="input-medium" >
			 	<c:forEach items="${questionlib:getCourseVersion()}" var="courseVesion">
					<c:forEach items="${courseVesionList}" var="courseVesion">
					   	<option value="${courseVesion.id}"  ${courseVesionId==courseVesion.id?"selected":""}>${courseVesion.title} </option>
					</c:forEach>
			  </select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul> --%>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width:20px;">序号</th>
				<!-- <th>专业</th> -->
				<th>课程</th>
				<th>版本</th>
				<th>老师</th>
				<!-- <th>班级</th> -->
				<th>考试类型</th>
				<th hidden="true">试卷ID</th>
				<th>试卷名称</th>
				<th>考试时间</th>
				<c:if test="${(not empty schoolList) and (fn:length(schoolList) > 0 ) }">
					<th>学校</th>
				</c:if>
				<!-- <th>操作</th> -->
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="exam" varStatus="s">
			<tr>
				<td>${s.index+1}</td>
				<%-- <td>
					${exam.specialtyName}
				</td> --%>
				<td>
					${exam.courseName}
				</td>
				<td>
					${exam.versionName}
				</td>
				<td>
					${exam.teacherName}
				</td>
				<%-- <td>
					${exam.schoolClassName}
				</td> --%>
				<td>
					<%--<c:if test="${exam.examType == 1 }">
						随堂练习
					</c:if>
					<c:if test="${exam.examType == 2 }">
						组卷考试
					</c:if>
					<c:if test="${exam.examType == 3 }">
						在线考试
					</c:if>
					<c:if test="${exam.examType == 4 }">
						课后作业
					</c:if>
					<c:if test="${exam.examType == 5 }">
						课堂例题
					</c:if> --%>
					
					${fns:getDictLabels(exam.examType,'examination_examtype', '')}
					
					
				</td>
				<td hidden="true">
					${exam.id}
				</td>
				<td>
					<a href="${ctx}/questionlib/exam/check?examId=${exam.id}">${exam.name}</a>
				</td>
				<td>
					<fmt:formatDate value="${exam.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<c:if test="${(not empty schoolList) and (fn:length(schoolList) > 0 ) }">
					<td>
						${exam.schoolName}
					</td>
				</c:if>
				<%-- <td>
					<a target="_black" href="${prc }/s/studentExam/searchExamQuestions?examId="+${exam.id}>查看</a>
				</td> --%>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>