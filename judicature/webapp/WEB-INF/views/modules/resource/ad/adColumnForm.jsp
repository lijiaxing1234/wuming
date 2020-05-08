<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>广告添加</title>
<meta name="decorator" content="default"/>
<script type="text/javascript">
	$(document).ready(function() {
		$("#adCode").focus();
		$("#inputForm").validate({
			rules : {
				adCode : {
					remote : "${ctx}/ad/checkADColumnCode"
				}
			},
			messages : {
				adCode : {
					remote : "该广告栏目编号已存在"
				}
			},
			submitHandler : function(form) {
				form.submit();
			},
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
				if (element.is(":checkbox") || element.is(":radio")) {
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
		<li><a href="${ctx}/ad/adColumnlist">广告栏目列表</a></li>
		<li class="active"><a href="${ctx}/ad/form">广告栏目添加</a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="adColumn" action="${ctx}/ad/addADColumn" method="post" class="form-horizontal">
		<div id="messageBox" class="alert alert-error" style="display: none">
			保存失败！
		</div>
		<input name="id" value="${adColumn.id}" type="hidden">
		<div class="control-group">
			<label class="control-label">广告名称<span style="color: red">(*)</span>：</label>
			<div class="controls">
				<form:input path="colName" htmlEscape="false" maxlength="30" class="required" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">广告编号<span style="color: red">(*)</span>： </label>
			<div class="controls">
				<form:input path="adCode" htmlEscape="false" maxlength="32" class="required" />
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保存" />&nbsp; 
			<input id="btnCancel" class="btn" type="button" value="返回" onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>