<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<form:form id="contentupdateForm" modelAttribute="implement" target="hiddenFrame" cssStyle="margin: 0;" action="${ctx}/admin/lv/courseImplementFormSave?lvCourseId=${implement.lvCourseId}&lvCourseCate=${implement.lvCourseCate}&id=${implement.id }" method="post" class="form-horizontal" enctype="multipart/form-data">
	<div id="updateImpleMessageBox" class="alert alert-error" style="display: none">输入有误，请先更正。</div>
	<div class="control-group" >
		<label class="control-label">内容类型<span style="color: red">(*)</span>:
		</label>
		<div class="controls">
			<form:select path="type" disabled="true" id="typeId" onchange="getTargetInfor()" class="required">
				<form:option value="1" label="文字" />
				<form:option value="2" label="图片" />
			</form:select>
		</div>
	</div>
	<div class="control-group"  id="updatetargetInfor">
		<label class="control-label">内容<span style="color: red">(*)</span>:
		</label>
		<c:if test="${implement.type==1}">
			<div class="controls" id="word">
				<textarea name="content" id="contentIdupdate" cols="60" rows="6" maxlength="10000" class="required" >${implement.content}</textarea>
			</div>
		</c:if>
		<c:if test="${implement.type==2}">
			<div class="controls" style="position: relative;" id="imgId">
			<c:choose>
			    <c:when test="${implement.content eq null  || empty implement.content}">
			        <img src="${implement.content}" width="50" height="40" />
			       <input id="btnCancel1" class="btn btn-primary" type="button" value="修改" />
			    </c:when>
			    <c:when test="${not empty implement.content}">
			        <img src="${implement.content}" width="50" height="40" /> 
			       <input  id="btnCancel1" class="btn btn-primary" type="button" value="修改" />
			    </c:when>
			</c:choose>
				<input type="file" id="uploadshotFile_update" name="uploadshotFile"
					style="position: absolute; left: 0;top:25%; width: 140px; height: 40px; filter: alpha(opacity =     2); opacity: 0.01;"
					class="required" onchange="uploadphotos1()" />
			</div>
		</c:if>
	</div>
</form:form>