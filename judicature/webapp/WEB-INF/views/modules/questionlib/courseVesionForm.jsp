<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程版本管理</title>
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
			
			chengeCourse();
			
		});
		
		function chengeCourse() {
            
			$("#courseId").empty();
		     //调用Ajax获取
			  $.getJSON("${ctx}/questionlib/common/getCourseJSONBySpecialtyId?specialtyId="+$("#specialtyId").val(),{ r:Math.random() },function(data){
					for (var i=0; i<data.length; i++){
					
					  if (data[i].id == '${courseVesion.courseId}') {
						  $("#courseId").append("<option selected value='"+data[i].id+"'>"+data[i].title+"</option>");
						  $(".select2-chosen").html(data[i].title);
					  } else {
						  $("#courseId").append("<option value='"+data[i].id+"'>"+data[i].title+"</option>").select2("val",data[i].id);
					  }
						 
					}
			  });
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/questionlib/courseVesion/list">课程版本列表</a></li>
		<li class="active"><a href="${ctx}/questionlib/courseVesion/form?id=${courseVesion.id}">课程版本<shiro:hasPermission name="questionlib:courseVesion:edit">${not empty courseVesion.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="questionlib:courseVesion:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="courseVesion" action="${ctx}/questionlib/courseVesion/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">名称：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*(注：名称请按照示例填写：专业名+课程名+版本名)</font> </span>
			</div>
		</div>
		 <div class="control-group">
			<label class="control-label">专业：</label>
			<div class="controls">
				 <sys:treeselect id="specialty" ontextchange="true" name="specialty.id" value="${courseVesion.specialty.id}" labelName="specialty.name" labelValue="${courseVesion.specialty.title}"
					title="专业" url="/questionlib/specialty/treeData" cssClass="required"/>
	
				
				   课程： 
				
				 <select class="input-xlarge required" name="courseId" id="courseId" style="width:150px;">
				 
			   </select><span class="help-inline"><font color="red">*</font> </span>
			   
			   
			   
			</div>
		</div>
		 
		<div class="form-actions">
			<shiro:hasPermission name="questionlib:courseVesion:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
<script type="text/javascript">

chengeCourse();

</script>
</html>