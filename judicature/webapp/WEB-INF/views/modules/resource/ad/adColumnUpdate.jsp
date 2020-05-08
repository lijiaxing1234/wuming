<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title><fmt:message key="ad.manage"></fmt:message></title>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$("#adCode").focus();
						$("#inputForm")
								.validate(
										{
											rules : {
												adCode : {
													remote : "${ctx}/admin/nas/ad/checkADColumnCodeUpdate?colId=${adColumn.colId}"
												}
											},
											messages : {
												adCode : {
													remote : "<fmt:message key='adcolumn.has.code'></fmt:message>"
												}
											},
											submitHandler : function(form) {
												loading('<fmt:message key="submiting"></fmt:message>');
												form.submit();
											},
											errorContainer : "#messageBox",
											errorPlacement : function(error,
													element) {
												if (element.is(":checkbox")
														|| element.is(":radio")) {
													error.appendTo(element
															.parent().parent());
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
		<li><a href="${ctx}/admin/nas/ad/adColumnlist"><fmt:message
					key="adcolumn.list"></fmt:message></a></li>
		<li class="active"><a href="${ctx}/admin/nas/ad/form"><fmt:message
					key="adcolumn.update"></fmt:message></a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="adColumn"
		action="${ctx}/admin/nas/ad/addADColumn" method="post"
		class="form-horizontal">
		<div id="messageBox" class="alert alert-error" style="display: none">
			<fmt:message key="messageinfor"></fmt:message>
		</div>
		<form:hidden path="colId" />
		<div class="control-group">
			<label class="control-label"><fmt:message key="adcolumn.name"></fmt:message><span
				style="color: red">(*)</span>
			<fmt:message key="comma"></fmt:message></label>
			<div class="controls">
				<form:input path="colName" htmlEscape="false" maxlength="100"
					class="required" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><fmt:message key="adcolumn.code"></fmt:message><span
				style="color: red">(*)</span>
			<fmt:message key="comma"></fmt:message></label>
			<div class="controls">
				<form:input path="adCode" htmlEscape="false" maxlength="100"
					class="required" />
			</div>
		</div>
		
		<%--<div class="control-group">
			<label class="control-label"><fmt:message key="shelf"></fmt:message><span
				style="color: red">(*)</span>
			<fmt:message key="comma"></fmt:message></label>
			<div class="controls">
				<form:select path="shelfId" class="required">
					<option value="">
						<fmt:message key='choose'></fmt:message>
					</option>
					<form:options items="${shelfList}" itemLabel="shelfName"
						itemValue="shelfId" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">栏目<span style="color: red">(*)</span><fmt:message key="comma"></fmt:message></label>
			<div class="controls">
				<form:select path="columnId" class="required">
					<option value="">
						<fmt:message key='choose'></fmt:message>
					</option>
					<form:options items="${firstShelfColumnList}" itemLabel="colName"
						itemValue="colId" htmlEscape="false" />
				</form:select>
			</div>
		</div> --%>
		
		
	    <div class="control-group">
			<label class="control-label">业务<span style="color: red">(*)</span><fmt:message key="comma"></fmt:message>
			</label>
			<div class="controls">
				<form:select path="busId" class="required">
					<option value=""><fmt:message key='choose'></fmt:message></option>
					<form:options items="${businessList}" itemLabel="name"
						itemValue="id" htmlEscape="false" />
				</form:select>
			</div>
        </div>	
		
		<div class="control-group hide">
			<label class="control-label"><fmt:message key="column"></fmt:message>
				<fmt:message key="comma"></fmt:message></label>
			<div class="controls">
				<tags:treeselect id="parentShelfColumn"
					name="parentShelfColumn.colId"
					value="${shelfColumnnew.parentShelfColumn.colId}"
					labelName="parentShelfColumn.colName"
					labelValue="${shelfColumnnew.parentShelfColumn.colName}"
					title="<fmt:message key='column'></fmt:message>"
					url="/admin/nas/ad/treeData"
					extId="${shelfColumnnew.parentShelfColumn.colId}"
					parentIds="${shelfColumnnew.parentShelfColumn.colCode}"
					paramId="shelfId" paramName="shelfId" notAllowSelectRoot="true" />
				<span style="color: red"><fmt:message key="ad.choose.shelf"></fmt:message></span>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit"
				value="<fmt:message key='save'></fmt:message>" />&nbsp; <input
				id="btnCancel" class="btn" type="button"
				value="<fmt:message key='goback'></fmt:message>"
				onclick="history.go(-1)" />
		</div>
	</form:form>
</body>
</html>