<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<title>提醒管理</title>
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
	 <style>
	 	
.tbtn-primary{
background-color:#1c9993 ! important;
background-image:-webkit-gradient(linear,0 0,0 100%,from(#1c9993),to(#1c9993)) ! important;
background-image:-webkit-linear-gradient(top,#1c9993,#1c9993) ! important;
background-image:linear-gradient(to bottom,#1c9993,#1c9993) ! important;
padding:4px 25px ! important;
color:#fff ! important;
}
	 </style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/questionlib/messageStudent/">提醒列表</a></li>
		<li class="active"><a href="${ctx}/questionlib/messageStudent/form?id=${messageStudent.id}">提醒${not empty messageStudent.id?'修改':'添加'}</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="messageStudent" action="${ctx}/questionlib/messageStudent/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">消息：</label>
			<div class="controls">
				<%-- <form:input path="message" htmlEscape="false" class="input-xlarge "/> --%>
				<form:textarea path="message"  htmlEscape="false" rows="5" cssStyle="width:300px ! important; height:115px ! important;"  maxlength="50" class="input-xxlarge required" />
				<span class="help-inline"><font color="red">*(最多可输入50个字)</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn tbtn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn tbtn-primary" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>