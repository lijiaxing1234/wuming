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
		 $.utils.BindSelect("versionId",[]);
		 $.utils.BindSelect("courseId", "/questionlib/common/getCourseBySpecialtyId?specialtyId="+ ids[0]);
	 }
	 
	 $(function(){
		  $("#courseId").off("change").on("change",function(e){
			 var selectId=e.val;
			 $.utils.BindSelect("versionId", "/questionlib/common/getCourseVersionByCouresId?courseId="+selectId);
		 }); 
		  
		  
		  $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
		   });
		 
	 });
	 
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/questionlib/courseQuestionlib/list">题库列表</a></li>
		<li class="active"><a href="${ctx}/questionlib/courseQuestionlib/form?id=${courseQuestionlib.id}">题库<shiro:hasPermission name="questionlib:courseQuestionlib:edit">${not empty courseQuestionlib.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="questionlib:courseQuestionlib:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="courseQuestionlib" action="${ctx}/questionlib/courseQuestionlib/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="ownerType"/>
		<form:hidden path="schoolId"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-xlarge required"/><span class="help-inline"><font color="red">*</font> </span>
				<font color="red">(注：题库名称请按照示例填写：专业名-课程名-版本名-XXX题库)</font>
			</div>
		</div>
		<div class="control-group">
			 <label class="control-label">专业：</label>
			  <div class="controls">
				<sys:treeselect id="specialty" ontextchange="true" name="specialtyId" value="${courseQuestionlib.specialty.id}" labelName="specialty.name" labelValue="${courseQuestionlib.specialty.title}"
					title="专业" url="/questionlib/specialty/treeData" cssClass="required"/><span class="help-inline"><font color="red">*</font> </span>
			  课程：
			  <select class="courseSelect required" name="courseId" id="courseId" style="width:150px;" >
			  	<c:forEach items="${questionlib:getCourseBySpecialtyId(courseQuestionlib.specialty.id)}" var="course">
				   	<option value="${course.id}" ${courseQuestionlib.course.id==course.id ? "selected":""}  >${course.title} </option>
				 </c:forEach>
			   </select><span class="help-inline"><font color="red">*</font> </span>
			 版本： 
		       <select name=versionId id="versionId"  class="input-medium" >
				  <c:forEach items="${questionlib:getCourseVersionByCourseId(courseQuestionlib.course.id)}" var="courseVesion">
				   	<option value="${courseVesion.id}" ${courseQuestionlib.courseVesion.id==courseVesion.id?"selected":""}  >${courseVesion.title} </option>
				  </c:forEach>
			  </select>
			  <span class="help-inline"><font color="red">*</font> </span>
			 </div>
		</div>
		<%-- <input type="hidden" name="ownerType" value="${courseQuestionlib.ownerType}"/>
		<input type="hidden" name="schoolId" value="${courseQuestionlib.schoolId}"/> --%>
		<%-- <div class="control-group">
			<label class="control-label">所属类型：</label>
			<div class="controls">
				<form:select path="ownerType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('questionlibowner')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:input path="state" htmlEscape="false" maxlength="10" class="input-xlarge "/>
			</div>
		</div>--%>
		<%-- <div class="control-group">
			<label class="control-label">学校：</label>
			<div class="controls">
				<form:input path="schoolId" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">老师：</label>
			<div class="controls">
				<sys:treeselect id="user" name="user.id" value="${courseQuestionlib.user.id}" labelName="user.name" labelValue="${courseQuestionlib.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>  --%>
		<div class="form-actions">
			<shiro:hasPermission name="questionlib:courseQuestionlib:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>