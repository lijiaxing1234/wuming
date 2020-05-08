<%-- <%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程版本管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
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
		<li><a href="${ctx}/questionlib/sCourseVersion/">课程版本列表</a></li>
		<li class="active"><a href="${ctx}/questionlib/sCourseVersion/form?id=${sCourseVersion.id}">课程版本<shiro:hasPermission name="questionlib:sCourseVersion:edit">${not empty sCourseVersion.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="questionlib:sCourseVersion:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sCourseVersion" action="${ctx}/questionlib/sCourseVersion/addSchoolCourseVersion" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">专业名称：</label>
			<div class="controls">
				<sys:treeselect id="specialty" ontextchange="true" name="specialty.id" value="${course.specialty.id}" labelName="specialty.name" labelValue="${course.specialty.title}"
					title="专业" url="/questionlib/specialty/treeData" cssClass="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>		
		<div class="control-group">
			<label class="control-label">课程名称：</label>
			<div class="controls">
				<form:select id="courseSelectId" path="course.id" class="input-xlarge required" onchange="getVersionsByCourseId()" >
					<form:option value="" label=""/>
					<!-- 查询所有课程 -->					
					<form:options items="${questionlib:getSchoolNotCourse()}" itemLabel="title" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">版本名称：</label>
			<div class="controls">
				<form:select id="versionSelectId" path="id" class="input-xlarge required">
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="questionlib:sCourseVersion:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<script type="text/javascript">
		function chengeCourse(){
			var specialtyId = $("#specialtyId").val();
			$.ajax({
				url:"${ctx}/questionlib/sCourseVersion/getCoursesBySpecialtyId",
				data:{specialtyId:specialtyId},
				dataType:'html',
				success:function(data){
					$("#courseSelectId").html("");
					$("#courseSelectId").append(data);
				}
			});
			
		}
	
	
		function getVersionsByCourseId(){
			var courseTitle=$("#courseSelectId").find("option:selected").text();  //获取Select选择的Text
			var courseId =$("#courseSelectId").val();  //获取Select选择的Value
			$.ajax({
				url:"${ctx}/questionlib/sCourseVersion/getVersionsByCourseId",
				type:"post",
				data:{courseId:courseId},
				dataType:'html',
				success:function(data){
					$("#versionSelectId").html("");
					$("#versionSelectId").append(data);
				}
			})
		}
	
	</script>
</body>
</html> --%>


<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程版本管理</title>
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
		 
	 });
   </script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/questionlib/sCourseVersion/">课程版本列表</a></li>
		<li class="active"><a href="${ctx}/questionlib/sCourseVersion/form?id=${sCourseVersion.id}">课程版本<shiro:hasPermission name="questionlib:sCourseVersion:edit">${not empty sCourseVersion.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="questionlib:sCourseVersion:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="sCourseVersion" action="${ctx}/questionlib/sCourseVersion/addSchoolCourseVersion" method="post" class="form-horizontal">
		<sys:message content="${message}"/>
		<input type="hidden" name="schoolVersionId" value="${sCourseVersion.schoolVersionId }">
		<ul class="ul-form">
			<div class="control-group">
				<label class="control-label">专业名称：</label>
				<div class="controls">
					<sys:treeselect id="specialty" ontextchange="true" name="specialty.id" value="${sCourseVersion.course.specialtyId}" labelName="specialty.name" labelValue="${empty sCourseVersion.course.specialtyId ? '' :questionlib:getSpecialtyByID(sCourseVersion.course.specialtyId).title}"
					title="专业" url="/questionlib/specialty/treeData"  allowClear="false"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">课程名称：</label>
				<div class="controls">
					 <select id="courseId"  name="course.id" class="input-medium">
				        <option value="">请选择</option>
					    <c:forEach items="${ empty sCourseVersion.course.specialtyId ? null: (questionlib:getCourseBySpecialtyId(sCourseVersion.course.specialtyId))}" var="course">
							<option value="${course.id}" ${sCourseVersion.course.id==course.id?"selected":""}  >${course.title} </option>
						</c:forEach>
				     </select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">版本名称：</label>
				<div class="controls">
					<select id="versionId" name="id"  class="input-medium">
				    	<option value="">请选择</option>
				        <c:forEach items="${empty sCourseVersion.course.id ? null:(questionlib:getCourseVersionByCourseId(sCourseVersion.course.id))}" var="courseVesion">
					   		<option value="${courseVesion.id}" ${sCourseVersion.id==courseVesion.id?"selected":""}  >${courseVesion.title} </option>
					 	</c:forEach>
				     </select>
				</div>
			</div>
			<div class="form-actions">
				<shiro:hasPermission name="questionlib:sCourseVersion:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
				<a id="btnCancel" class="btn" href="${ctx}/questionlib/sCourseVersion/"/>返 回</a>
			</div>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
</body>
</html>