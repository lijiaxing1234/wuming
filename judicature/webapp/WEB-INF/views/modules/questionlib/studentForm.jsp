<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学生管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/modules/questionlib/js/questionlibSelect.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#specialtyTitle").off("change").on("change",function(e){
				 var selectId=e.val;
				 $.utils.BindSelect("schoolClassId",[]);
			     $.utils.BindSelect("schoolClassId", "/questionlib/schoolClass/findSchoolClassBySpecTitle?specTitle="+selectId);
		    }); 
			
			
			
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
					}else if(element.parent().is("span")){
						error.appendTo(element.parent());
					}else{
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/questionlib/student/">学生列表</a></li>
		<li class="active"><a href="${ctx}/questionlib/student/form?id=${student.id}">学生<shiro:hasPermission name="questionlib:student:edit">${not empty student.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="questionlib:student:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="student" action="${ctx}/questionlib/student/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">学生姓名：</label>
			<div class="controls">
			    <span>
				<form:input path="name" htmlEscape="false" maxlength="20" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">专业：</label>
			<div class="controls">
			       <span>
				       <select class="input-xlarge required" id="specialtyTitle" name="specialtyTitle">
				             <option value="">请选择</option>
				             <c:forEach  var="specTitle" items="${specTitles}">
				                <option value="${specTitle.specialtyTitle}"   ${specTitle.specialtyTitle eq student.schoolClass.specialtyTitle ? 'selected="selected"' : '' } >${specTitle.specialtyTitle}</option>
				             </c:forEach>
				       </select>
				       <span class="help-inline"><font color="red">*</font> </span>
			      </span>
					<%-- <form:option value="" label=""/>
					<form:options items="${questionlib:getClassBySchoolId()}" itemLabel="title" itemValue="id" htmlEscape="false"/> --%>
			</div>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">班级：</label>
			<div class="controls">
				<%-- <form:select path="schoolClass.id" class="input-xlarge required">
					<form:option value="" label="请选择"/>
					<form:options items="${not empty student.schoolClass.specialtyTitle ? questionlib:getClassBySpecTitle(student.schoolClass.specialtyTitle) : null}" itemLabel="title" itemValue="id" htmlEscape="false"/>
				</form:select> --%>
				 <span>
					  <select class="input-xlarge required" id="schoolClassId" name="schoolClass.id">
				             <option value="">请选择</option>
				             <c:forEach  var="specTitle" items="${not empty student.schoolClass.specialtyTitle ? questionlib:getClassBySpecTitle(student.schoolClass.specialtyTitle) : null}">
				                <option value="${specTitle.id}" ${specTitle.title eq student.schoolClass.title ? 'selected="selected"' : '' } >${specTitle.title}</option>
				             </c:forEach>
				      </select>
					<span class="help-inline"><font color="red">*</font> </span>
				</span>
			</div>
		</div>
		
		
		<div class="control-group">
			<label class="control-label">学号：</label>
			<div class="controls">
			   <span>
				<input type="hidden" id="oldStdCode" name="oldStdCode" value="${student.stdCode}"/>
				<form:input path="stdCode" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				</span>
				<span class="help-inline"><font color="red">学生登录名称（注：命名规则，例：北京大学学生，bjdx+XXX）</font> </span>
				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">密码：</label>
			<div class="controls">
			    <span>
				<input name="stdPassword" type="password" value="" maxlength="50" minlength="3" class="${empty student.id?'input-xlarge required':'input-xlarge'}">
				<c:if test="${empty student.id}"><span class="help-inline"><font color="red">*</font> </span></c:if>
				<c:if test="${not empty student.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
				</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
			    <span>
				<form:radiobuttons path="stdSex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				</span>
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
			    <span>
				<form:input path="stdPhone" htmlEscape="false" maxlength="11" class="input-xlarge digits required"/>
				<span class="help-inline"><font color="red">*</font> </span>
				</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱：</label>
			<div class="controls">
			    <span>
				<form:input path="stdEmail" htmlEscape="false" maxlength="100" class="input-xlarge required email"/>
				<span class="help-inline"><font color="red">*</font> </span>
				</span>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="questionlib:student:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>