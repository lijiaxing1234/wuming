<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_blank"/>
	<title>学生评论</title>
	<%@include file="/WEB-INF/views/teacher/include/validation.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#importForm").validate({
				submitHandler: function(form){
					//loading('正在提交，请稍等...');
					form.submit();
				},
				errorPlacement: function(error, element) {
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
</head>
<body>
	<form:form id="importForm" modelAttribute="studentReview" action="${ctx}/examination/save" method="post" class="form-horizontal">
		<input  name="studentId" type="hidden" value="${student.id}"/>
		<div class="control-group"> 
			<label class="control-label">姓名:</label>
			<div class="controls">
			    <span>
				<input id="stuName"  maxlength="50" class="required" name="names" value="${student.name }"  readonly="true"/>
				<span class="help-inline"><font color="red">*</font></span>
				</span>
				
			</div>
		</div>
		<div class="control-group"> 
			<label class="control-label">评论:</label>
			<div class="controls">
			    <span>
				<form:textarea path="content" rows="30"  cols="10" style="height:135px;width:450px" cssClass="required" maxlength="200" />
				<span class="help-inline"><font color="red">*(最多只能填写200字)</font></span>
				</span>
			</div>
		</div>
		
		 <input id="btnImportSubmit" class="btn tbtn-primary"
			type="submit" value="保存" style="margin-top:0px;margin-left:700px"/>
	</form:form>
</body>
</html>