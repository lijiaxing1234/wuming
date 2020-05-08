<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>书籍检查管理</title>
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
		<li><a href="${ctx}/questionlib/tableBookVersion/">书籍检查列表</a></li>
		<li class="active"><a href="${ctx}/questionlib/tableBookVersion/form?id=${tableBookVersion.id}">书籍检查<shiro:hasPermission name="questionlib:tableBookVersion:edit">${not empty tableBookVersion.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="questionlib:tableBookVersion:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="tableBookVersion" action="${ctx}/questionlib/tableBookVersion/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">电子书名称：</label>
			<div class="controls">
				<form:input path="bookName" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				<form:select path="checkState" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('check_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">打开：</label>
			<div class="controls">
				<form:select path="isOpen" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('check_result')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">封面：</label>
			<div class="controls">
				<form:select path="isCover" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('check_result')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">版权页：</label>
			<div class="controls">
				<form:select path="isCopyright" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('check_result')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">目录：</label>
			<div class="controls">
				<form:select path="isCatalog" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('check_result')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">图片：</label>
			<div class="controls">
				<form:select path="isImage" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('check_result')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排版：</label>
			<div class="controls">
				<form:select path="isLayout" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('check_result')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">乱码：</label>
			<div class="controls">
				<form:select path="isCode" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('check_result')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">其他：</label>
			<div class="controls">
				<form:textarea path="otherDesc" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="questionlib:tableBookVersion:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>