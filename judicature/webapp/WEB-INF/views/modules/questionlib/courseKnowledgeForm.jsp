<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>知识点管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	
	$(document).ready(function() {
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
		<li><a href="${ctx}/questionlib/courseKnowledge/list?extId=${extId}&courseId=${courseId}">知识点列表</a></li>
		<li class="active"><a href="${ctx}/questionlib/courseKnowledge/form?extId=${extId}&courseId=${courseId}">知识点<shiro:hasPermission name="questionlib:courseKnowledge:edit">${not empty courseKnowledge.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="questionlib:courseKnowledge:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="courseKnowledge" action="${ctx}/questionlib/courseKnowledge/save?extId=${extId}&courseVesionId=${courseVesionId}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="courseId"/>
		<sys:message content="${message}"/>		
		
		<%-- <div class="control-group">
			<label class="control-label">课程：</label>
			<div class="controls">
				 <select class="courseSelect input-small required" name="courseId" id="courseId" onchange="changeKnowledge();" style="width:200px;">
				      <!-- <option value="">请选择</option> -->
				      <c:forEach items="${ questionlib:getCourse() }" var="course">
							<option value="${course.id}" ${courseKnowledge.courseId==course.id?"selected":""}  >${course.title} </option>
					  </c:forEach>
					  
				   	</select>
				<span class="help-inline"><font color="red">*</font> </span>
				
			</div>
		</div> --%>
		
		
		<div class="control-group">
			<label class="control-label">上级知识点:</label>
			<div class="controls">
				<sys:treeselect id="parent" name="parent.id" value="${courseKnowledge.parent.id}" labelName="parent.title" labelValue="${courseKnowledge.parent.title}"
					title="父知识点" url="/questionlib/courseKnowledge/treeData?courseId=${courseKnowledge.courseId}" cssClass=" " allowClear="true" disabled="true"/>
				<!-- <span class="help-inline"><font color="red">*</font> </span> -->
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">知识点编号：</label>
			<div class="controls">
				<form:input path="knowledgeCode" htmlEscape="false" maxlength="50"  />
				<!-- <span class="help-inline"><font color="red">*</font> </span> -->
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">排序:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="50"  class="required digits input-small"/>
				<span class="help-inline">排列顺序，升序。</span>
			</div>
		</div>
		
		<%-- <div class="control-group">
			<label class="control-label">难度：</label>
			<div class="controls">
				<form:select path="level" class="input-medium">
					<option></option>
					<form:option value="" label="请选择"/>
					<form:options items="${fns:getDictList('questionlib_courseKnowledge_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			<font color="red">注：一级考点最重要，最基础，二级、三级重要性越来越弱</font>
			</div>
		</div> --%>
		
		<div class="form-actions">
			<shiro:hasPermission name="questionlib:courseKnowledge:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>