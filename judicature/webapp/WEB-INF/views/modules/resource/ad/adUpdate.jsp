<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script>
$(document).ready(
		function() {
			$("#adUpdateForm").validate({
				submitHandler : function(form) {
					loading('<fmt:message key="submiting"></fmt:message>');
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
<body>
	<form:form id="adUpdateForm" modelAttribute="ad" cssStyle="margin: 0;" 	action="${ctx}/admin/nas/ad/updateAD" method="post"
		class="form-horizontal" enctype="multipart/form-data"
		target="hiddenFrame">
		<div id="messageBox" class="alert alert-error" style="display: none">
			<fmt:message key="messageinfor"></fmt:message>
		</div>
		<input type="hidden" name="colId" id="colIdUpdate" value="${ad.colId}" />
		<form:hidden path="id" />
		<form:hidden path="shelfId" />
		<div class="control-group">
			<label class="control-label"><fmt:message key="ad.target"></fmt:message><span
				style="color: red">(*)</span> <fmt:message key="comma"></fmt:message>
			</label>
			<div class="controls" id="updatetargetInfor">
				${nasPublication.name }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><fmt:message key="ebook.icon"></fmt:message><span
				style="color: red">(*)</span> <fmt:message key="comma"></fmt:message>
			</label>
			<div class="controls" style="position: relative;" id="updateIcon">
				<img src="${ad.imageUrl}" width="50" height="40" /><input
					id="btnCancel1" class="btn btn-primary" type="button"
					value="<fmt:message key='update'></fmt:message>" /> <input
					type="file" id="updateuploadFile" name="uploadFile" onchange="" 
					style="position: absolute; left: 0; width: 81px; height: 40px; filter: alpha(opacity =             2); opacity: 0.01;" />
				<span class="help-inline">(1536×400)</span>
			</div>
		</div>
	</form:form>
</body>