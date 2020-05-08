<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>知识点管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	
	$(document).ready(function() {
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
		<li><a href="${ctx}/category/list">分类列表</a></li>
		<li class="active"><a href="${ctx}/category/form?id=${category.id}">分类<shiro:hasPermission name="questionlib:category:edit">${not empty category.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="questionlib:category:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="category" action="${ctx}/category/save" method="post" class="form-horizontal" enctype="multipart/form-data">
		 <form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">所属课程：</label>
			<div class="controls">
				   <select class="courseSelect input-small" name="courseId" id="courseId" style="width:200px;">
				      <option value="">请选择</option> 
				      <c:forEach items="${questionlib:getCourse()}" var="course">
							<option value="${course.id}" ${category.courseId==course.id?"selected":""}  >${course.title} </option>
					  </c:forEach>
				   	</select>
			</div>
		</div> 
		<div class="control-group">
			<label class="control-label">上级分类:</label>
			<div class="controls">
				<sys:treeselect id="parent" name="parent.id" value="${category.parent.id}" labelName="parent.title" labelValue="${category.parent.name}"
					title="父知识点" url="/category/treeData" cssClass=" " allowClear="true" disabled="true"/>
			</div> 
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分类简介：</label>
			<div class="controls">
				<form:input path="description" htmlEscape="false"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分类封面：</label>
			<div class="controls">
				<c:if test="${not empty category.image}">
				 <img alt="" src="${imageServer}${category.image}" style="width: 50px;height: 50px;">
				</c:if>
				<input type="file" name="files">
			</div>
		</div>
<%-- 		<div class="control-group">
			<label class="control-label">分类类型：</label>
			<div class="controls">
				<input type="radio" name="type" value="0" <c:if test="${category.type eq '0'}"> checked="checked"</c:if> >免费课堂</input>
				<input type="radio" name="type" value="1" <c:if test="${category.type eq '1'}"> checked="checked"</c:if>>为您推荐</input>
				<input type="radio" name="type" value="2" <c:if test="${category.type eq '2'}"> checked="checked"</c:if>>直播课堂</input>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">主讲者：</label>
			<div class="controls">
				<form:input path="persenter" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">价格：</label>
			<div class="controls">
				<form:input path="price" htmlEscape="false" onkeyup="if(isNaN(value))execCommand('undo')" onafterpaste="if(isNaN(value))execCommand('undo')"/>元
			</div>
		</div> --%>
		<div class="form-actions">
			<shiro:hasPermission name="questionlib:category:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>