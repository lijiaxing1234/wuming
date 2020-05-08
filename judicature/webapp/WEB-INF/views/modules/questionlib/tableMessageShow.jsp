<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统消息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#content").focus();
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
		<li><a href="${ctx}/questionlib/tableMessage/">系统消息列表</a></li>
		<li class="active"><a href="#">系统消息<shiro:hasPermission name="questionlib:tableMessage:edit">${not empty tableMessage.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="questionlib:tableMessage:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tableMessage" action="${ctx}/questionlib/tableMessage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
	<%-- 	<div class="control-group">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="30" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls" style="margin-top: 3px;white-space:normal; width:500px;" >
				${tableMessage.content}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">发布时间：</label>
			<div class="controls" style="margin-top: 3px;">
				<fmt:formatDate value="${tableMessage.createtime}" pattern="yyyy-MM-dd  "/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">是否置顶:</label>
			<div class="controls">
				<form:select path="isTop" class="required" >
					<form:option value="0">否</form:option>
					<form:option value="1">是</form:option>
				</form:select>
			</div>
		</div> --%>
	<%-- 	<div class="control-group">
			<label class="control-label">office_id：</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" value="${tableMessage.office.id}" labelName="office.name" labelValue="${tableMessage.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div> --%>
	 	<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>