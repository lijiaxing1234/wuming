<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<title>题库管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#title").focus();
			$("#inputForm").validate({
				rules: {
					title: {remote: "${ctx}/questionlib/checkTitle?oldTitle=" + encodeURIComponent('${courseQuestionlib.title}')}
				},
				messages: {
					title: {remote: "名称已存在"}
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
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
 <style type="text/css">
 	.form-horizontal{ margin-top: 40px;}
 	.btn-primary{background:#1c9993;background-image:-webkit-linear-gradient(top,#1c9993,#1c9993);}
 </style>
</head>
<body>
	<form:form id="inputForm" modelAttribute="courseQuestionlib" action="${ctx}/questionlib/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<div class="control-group">
			<label class="control-label"  style="text-align: left; width: 50px;">名称：</label>
			<div class="controls" style="text-align: left; margin-left: 0px;">
			    <span>
				<form:input path="title" htmlEscape="false" maxlength="12" class="input-xlarge required"/>
				 <span class="help-inline"><font color="red">*(最多只能输入12个字)</font></span>
				 </span>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn tbtn-primary" type="submit" value="保 存"/>&nbsp;
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="top.$.jBox.close(true);"/>
		</div>
	</form:form>
</body>
</html>