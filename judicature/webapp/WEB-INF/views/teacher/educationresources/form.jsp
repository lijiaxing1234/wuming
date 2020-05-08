<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<title>教学资源管理</title>
<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#resName").focus();
			$("#inputForm").validate({
				ignore: "input#courseKnowledgeId",
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					if (element.parent().parent().is("span")){
					    error.insertAfter(element.parent().parent());
					}else if (element.parent().is("span") || element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.insertAfter(element.parent());
					}else{
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
	<style type="text/css">
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
	    <li><a href="${ctx}/sources/list?knoId=${knoId}">教学资源列表</a></li>
		<li class="active"><a 	href="${ctx}/sources/form?id=${edresources.id}&knoId=${knoId}">教学资源${not empty edresources.id?'修改':'添加'}</a></li>
	</ul><br/>
	
	<form:form id="inputForm" modelAttribute="edresources" action="${ctx}/sources/save?knoId=${knoId}" method="post" class="form-horizontal">
		   <form:hidden path="id"/>
		   <sys:message content="${message}"/>
		   <div class="control-group">
		         <label class="control-label">名称:</label>
		         <div class="controls">
		            <span>
			            <form:input path="resName" cssClass="required" maxlength="10"/>
			            <span class="help-inline"><font color="red">*（最多可输入10个字）</font> </span>
		            </span>
		         </div>
		   </div>
		     
		   <div class="control-group">
				<label class="control-label">知识点：</label>
				<div class="controls">
				    <span>
						<sys:treeselect id="courseKnowledge" name="courseKnowledge.id"  value="${edresources.courseKnowledge.id}"
							labelName="courseKnowledge.title"
							labelValue="${edresources.courseKnowledge.title}" 
							title="知识点"
							url="${ctx}/common/courseKnowledgeByVersionTreeData"
							cssClass="required" 
							allowClear="true"/>	
						 <span class="help-inline"><font color="red">*</font> </span>
					</span>
				</div>	
			</div>
			
	      <div class="control-group">
			 <label class="control-label">上传文件：</label>
			 <div class="controls">
			    <span>
					<form:hidden id="resFile" path="resFile" htmlEscape="false" maxlength="500" class="input-xlarge"  cssClass="required"/>
					<sys:ckfinder input="resFile" type="files" uploadPath="/files"
						selectMultiple="false" maxWidth="100" maxHeight="100"  delBtn="清除文件"/>
				    <span class="help-inline"><font color="red">*</font> </span>
				</span>
			</div>
	      </div>
		<div class="form-actions pagination-left">
			<input id="btnSubmit" class="btn tbtn-primary" type="submit" value="保存 "/>
		</div>
	</form:form>
</body>
</html>