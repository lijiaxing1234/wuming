<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>专业管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					var specialtyid=$("#specialtyId").val();
					if(specialtyid){
						loading('正在提交，请稍等...');
						form.submit();
					}else{
						alertx("请选择专业!");
					}
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
		<li><a href="${ctx}/questionlib/specialty/list?extId=${extId}">专业列表</a></li>
		<li class="active"><a href="${ctx}/questionlib/specialty/form?extId=${extId}">专业<shiro:hasPermission name="questionlib:specialty:edit">${not empty specialty.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="questionlib:specialty:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="specialty" action="${ctx}/questionlib/specialty/save?extId=${extId}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">上一级:</label>
			<div class="controls">
                <sys:treeselect id="specialty" name="parent.id" value="${specialty.parent.id}" labelName="parent.title" labelValue="${specialty.parent.title}"
					title="专业" url="/questionlib/specialty/treeData" extId="${specialty.id}" cssClass="required" />
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">专业名称:</label>
			<div class="controls">
			    <div class="input-append">
				  <form:input path="title" htmlEscape="false" maxlength="50" class="required"/>
				</div>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">描述:</label>
			<div class="controls">
				<form:textarea path="desciption" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		
		<div class="form-actions">
			<shiro:hasPermission name="questionlib:specialty:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>