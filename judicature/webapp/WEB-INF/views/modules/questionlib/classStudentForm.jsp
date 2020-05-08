<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学生管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				rules: {
					stdCode: {remote: "${ctx}/questionlib/student/checkStdCode?oldStdCode=" + encodeURIComponent('${student.stdCode}')}
				},
				messages: {
					stdCode: {remote: "学号已存在"}
				},
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
		<li><a href="${ctx}/questionlib/student/findClassStudentByClassId?schoolClass.id=${student.schoolClass.id }">学生列表</a></li>
		<li class="active"><a href="${ctx}/questionlib/student/toAddOrUpdateStudentPage?schoolClass.id=${student.schoolClass.id }&&id=${student.id}">学生<shiro:hasPermission name="questionlib:student:edit">${not empty student.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="questionlib:student:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="student" action="${ctx}/questionlib/student/classStudentsave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">学生姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">班级：</label>
			<div class="controls">
				<form:select path="schoolClass.id"  class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${questionlib:getClassBySchoolId()}" itemLabel="title" itemValue="id" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学号：</label>
			<div class="controls">
				<input type="hidden" id="oldStdCode" name="oldStdCode" value="${student.stdCode}"/>
				<form:input path="stdCode" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*   （学生登录时使用学号登录）</font> </span>
				<!-- <span class="help-inline"><font color="red">（学生登录时使用学号登录） </font> </span> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:radiobuttons path="stdSex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">年龄：</label>
			<div class="controls">
				<form:input path="stdAge" htmlEscape="false" maxlength="3" class="input-xlarge digits"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号：</label>
			<div class="controls">
				<form:input path="stdPhone" htmlEscape="false" maxlength="11" class="input-xlarge digits required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱：</label>
			<div class="controls">
				<form:input path="stdEmail" htmlEscape="false" maxlength="100" class="input-xlarge required email"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码：</label>
			<div class="controls">
				<input name="stdPassword" type="password" value="" maxlength="50" minlength="3" class="${empty student.id?'input-xlarge required':'input-xlarge'}">
				<c:if test="${empty student.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
				<c:if test="${not empty student.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="questionlib:student:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>