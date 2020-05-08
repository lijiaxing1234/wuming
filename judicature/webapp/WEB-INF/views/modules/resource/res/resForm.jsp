<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程管理</title>
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
		<li><a href="${ctx}/resource/list?categoryId=${resource.categoryId}">资源列表</a></li>
		<li class="active"><a href="${ctx}/resource/form?id=${resource.id}&categoryId=${resource.categoryId}">资源<shiro:hasPermission name="questionlib:resource:edit">${not empty resource.id?'修改':'添加'}</shiro:hasPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="resource" action="${ctx}/resource/save" method="post" class="form-horizontal" enctype="multipart/form-data">
		<form:hidden path="id"/>
		<input value="${resource.categoryId}" name="categoryId" type="hidden"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
<%-- 		<div class="control-group">
			<label class="control-label">类型：</label>
			<div class="controls">
				<sys:treeselect id="parent" name="categoryId" value="${resource.category.id}" labelName="parent.title" labelValue="${resource.category.name}"
					title="父知识点" url="/category/treeData" cssClass=" " allowClear="true" disabled="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>  --%>
		
		<div class="control-group">
			<label class="control-label">所属课程：</label>
			<div class="controls">
				   <select class="courseSelect input-small required" name="courseId" id="courseId" style="width:200px;">
				      <option value="">请选择</option> 
				      <c:forEach items="${questionlib:getCourse()}" var="course">
							<option value="${course.id}" ${resource.courseId==course.id?"selected":""}  >${course.title} </option>
					  </c:forEach>
				   	</select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> 
		<div class="control-group">
			<label class="control-label">视频URL：</label>
			<div class="controls">
				<form:input path="filePath" htmlEscape="false"/>
				<%-- <c:if test="${not empty resource.filePath}">
					${resource.filePath}<br>
				</c:if>
				<input type="file" name="files"> --%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">时长：</label>
			<div class="controls">
				<form:input path="fileTime" htmlEscape="false"/>分钟
			</div>
		</div> 
		
		<div class="control-group">
			<label class="control-label">音频URL：</label>
			<div class="controls">
				<form:input path="reserved1" htmlEscape="false"/>
				<%-- <c:if test="${not empty resource.filePath}">
					${resource.filePath}<br>
				</c:if>
				<input type="file" name="files"> --%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">文件大小：</label>
			<div class="controls">
				<form:input path="reserved2" htmlEscape="false"/>KB
			</div>
		</div> 
		<div class="control-group">
			<label class="control-label">主讲者：</label>
			<div class="controls">
				<form:input path="presenter" htmlEscape="false"/>
			</div>
		</div> 
		
		<div class="control-group">
			<label class="control-label">价格：</label>
			<div class="controls">
				<form:input path="price" htmlEscape="false"  onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/>元
			</div>
		</div> 
		<div class="form-actions">
			<shiro:hasPermission name="questionlib:resource:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>