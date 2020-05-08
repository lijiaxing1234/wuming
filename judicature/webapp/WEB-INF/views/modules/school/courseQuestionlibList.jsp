<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>题库管理</title>
	<meta name="decorator" content="default"/>
	
	<script type="text/javascript" src="${ctxStatic}/modules/questionlib/js/questionlibSelect.js"></script>
	<script type="text/javascript">
	
	 function chengeCourse(ids,names){
		 $.utils.BindSelect("courseId",[]);
		 $.utils.BindSelect("courseVesionSelect",[]);
		 $.utils.BindSelect("courseId", "/questionlib/common/getSchoolCourseBySpecialtyId?specialtyId="+ ids[0]);
	 }
	 
	 $(function(){
		  $("#courseId").off("change").on("change",function(e){
			 var selectId=e.val;
			 $.utils.BindSelect("courseVesionSelect", "/questionlib/common/getSchoolCourseVersionByCouresId?courseId="+selectId);
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
		<li class="active"><a href="${ctx}/school/schoolcourseQuestionlib/list ">题库列表</a></li>
		 <li><a href="${ctx}/school/schoolcourseQuestionlib/form">题库添加</a></li> 
	</ul>
	<form:form id="searchForm" modelAttribute="courseQuestionlib" action="${ctx}/school/schoolcourseQuestionlib/list" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>题库名称：</label>
				<form:input path="title" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li>
				<label>专业：</label>
				<sys:treeselect id="specialty" ontextchange="true" name="specialtyId" value="${specialtyId==null?courseQuestionlib.specialty.id:specialtyId}" labelName="specialty.name" labelValue="${specialtyId==null?courseQuestionlib.specialty.title:(questionlib:getSpecialtyByID(specialtyId)).title}"
					title="专业" url="/questionlib/specialty/treeData" cssClass="required"   notAllowSelectParent="true"  />
			</li>
			<li>
				<label>课程：</label>
				<select class="courseSelect" name="courseId" id="courseId"  style="width:200px;" >
				   <option value="">请选择</option>
					<c:forEach items="${specialtyId==null?null:(questionlib:getSchoolCourseBySpecialtyId(specialtyId))}" var="course">
						<option value="${course.id}" ${courseId==course.id?"selected":""}  >${course.title} </option>
					</c:forEach>
				</select>
			</li>
			<li>
				<label>版本：</label>
				<select name="courseVesionId" id="courseVesionSelect"  class="input-medium"  style="width:200px;">
				  <option value="">请选择</option>
					<c:forEach items="${courseId==null?null:(questionlib:getSchoolCourseVersionByCourseId(courseId))}" var="courseVesion">
				   		<option value="${courseVesion.id}" ${courseVesionId==courseVesion.id?"selected":""}  >${courseVesion.title} </option>
				 	</c:forEach>
			  	</select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width:20px;">序号</th>
				<th>题库名称</th>
				<th>版本</th>
				<th>所属类型</th>
				<th>操作</th> 
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="courseQuestionlib"  varStatus="status">
			<tr>
				<td>${status.index+1+(page.pageNo-1)*page.pageSize }</td>	
				<td><a href="${ctx}/school/schoolcourseQuestionlib/form?id=${courseQuestionlib.id}&courseVesionId=${courseVesionId}">
					${courseQuestionlib.title}
				</a></td>
				<td>
					${questionlib:getCourseVersionByVersionId(courseQuestionlib.versionId).title}
				</td>
				<td>
					${fns:getDictLabel(courseQuestionlib.ownerType, 'questionlibowner', '')}
				</td>
				<%-- <td>
					${questionlib:getOfficeById(questionlibImport.schoolId).name}
				</td> --%>
				 <td>
    				<a href="${ctx}/school/schoolcourseQuestionlib/form?id=${courseQuestionlib.id}&courseVesionId=${courseVesionId}">修改</a>
    			<shiro:hasPermission name="school.web:courseQuestionlib:delete">
					<a href="${ctx}/school/schoolcourseQuestionlib/delete?id=${courseQuestionlib.id}&courseVesionId=${courseVesionId}" onclick="return confirmx('确认要删除该题库吗？', this.href)">删除</a>
				</shiro:hasPermission>
				</td> 
			</tr>
		</c:forEach>
		</tbody>
	</table>
	 <div class="pagination">${page}</div>
</body>
</html>