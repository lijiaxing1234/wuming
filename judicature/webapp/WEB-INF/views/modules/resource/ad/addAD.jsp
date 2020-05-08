<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<form:form id="inputForm" modelAttribute="ad"
	action="${ctx}/admin/ad/addAD" method="post" class="form-horizontal" enctype="multipart/form-data" target="adframe">
	<div id="messageBox" class="alert alert-error" style="display: none"><fmt:message key="messageinfor"></fmt:message></div>
	<form:hidden path="colId" />
	<div class="modal-body">
	<div class="control-group">
		<label class="control-label"><fmt:message key="ad.type"></fmt:message><fmt:message key="comma"></fmt:message></label>
		<div class="controls">
			<form:select path="adType" onchange="getTargetInfor()">
				<option value="1"><fmt:message key='ebook'></fmt:message></option>
							<option value="2"><fmt:message key='ad.url'></fmt:message></option>
			</form:select>
			<span style="color: red">*</span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label"><fmt:message key="ad.target"></fmt:message><fmt:message key="comma"></fmt:message></label>
		<div class="controls">
			<input id="ebook" class="btn btn-primary" name="target" type="button"
				value="<fmt:message key='ad.choose.ebook'></fmt:message>" onclick="getEbookInfor()" class="required" /> <input
				name="target" id="adurl" maxlength="500" type="text"
				class="required" style="display: none" /> <span style="color: red">*</span>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label"><fmt:message key="ebook.icon"></fmt:message><fmt:message key="comma"></fmt:message></label>
		<div class="controls">
			<input type="file" name="uploadFile" class="required" id="uploadFile"/> <span
				style="color: red">*</span>
		</div>
	</div>
	</div>
	<div class="modal-footer" align="center">
		<input id="btnSubmit" class="btn btn-primary" type="submit"
			value="<fmt:message key='save'></fmt:message>" />&nbsp; <input id="btnCancel" class="btn" type="button"
			value="<fmt:message key='goback'></fmt:message>" onclick="history.go(-1)" />
	</div>
</form:form>