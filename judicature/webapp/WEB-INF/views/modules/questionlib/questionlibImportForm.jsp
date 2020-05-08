<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>试题导入题库管理</title>
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
		<li><a href="${ctx}/questionlib/questionlibImport/">试题导入题库列表</a></li>
		<li class="active"><a href="${ctx}/questionlib/questionlibImport/form?id=${questionlibImport.id}">试题导入题库<shiro:hasPermission name="questionlib:questionlibImport:edit">${not empty questionlibImport.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="questionlib:questionlibImport:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="questionlibImport" action="${ctx}/questionlib/questionlibImport/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">题库：</label> <div style="margin-top: 2px;"> ${questionlib:getCourseQuestionlibById(questionlibImport.questionlibId).title}  </div> 
		 
		</div>
		<div class="control-group">
			<label class="control-label">专业：</label> <div style="margin-top: 2px;"> ${questionlib:getSpecialtyByID(questionlibImport.specialtyId).title}</div>
		 
		</div>
		<div class="control-group">
			<label class="control-label">导入文档：</label> <div style="margin-top: 2px;">${questionlibImport.title}</div>
		 
		</div>
		<div class="control-group">
			<label class="control-label">出题人：</label>  <div style="margin-top: 2px;">${questionlibImport.writer}</div>
		 
		</div>
		 
		<div class="control-group">
			<label class="control-label">上传人：</label> <div style="margin-top: 2px;">${questionlibImport.user.name}</div>
			 
		</div>
		<div class="control-group">
			<label class="control-label">上传人电话：</label>  <div style="margin-top: 2px;"> ${questionlibImport.phone} </div>
			 
		</div>
		<div class="control-group">
			<label class="control-label">设置学校：</label> <div style="margin-top: 2px;">  ${questionlib:getOfficeById(questionlibImport.school.id).name}</div>
		 
		</div>
		<div class="control-group">
			<label class="control-label">适用层次：</label> <div style="margin-top: 2px;"> ${fns:getDictLabel(questionlibImport.coursePhase, 'coursephase', '')} </div>
		 
		</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>