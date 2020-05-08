<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学校数据库管理</title>
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
		//	chengeCourse();
		});
		
		function chengeCourse() {
			var specialtyId = $("#specialtyId").val();
			$("#courseId").empty();
			$("#questionlibId").empty();
		     //调用Ajax获取
			 if(specialtyId==null || specialtyId==""){
				  	$("#questionlibId").append("<option value=\"\" \"selected\">请选择</option>"); 
			 }else{
				  $.getJSON("${ctx}/questionlib/common/getCourseJSONBySpecialtyId?specialtyId="+$("#specialtyId").val(),{ r:Math.random() },function(data){
					  $("#courseId").append("<option value='' selected>全部</option>");
						for (var i=0; i<data.length; i++){
							if(data.id==""){
							 	 $("#questionlibId").append("<option value=\"\" \"selected\">"+data[i].value+"</option>"); 
							}else{
								$("#courseId").append("<option value='"+data[i].id+"'>"+data[i].title+"</option>"); 
							}
						}
				  });
				 /*  $.getJSON("${ctx}/questionlib/schoolQuestionlib/getCourseQuestionlibByVersionId?specialtyId="+specialtyId,function(data){
						 for (var i=0; i<data.length; i++){
							  if(data.id==""){
							 	 $("#questionlibId").append("<option value=\"\" \"selected\">"+data[i].value+"</option>"); 
							  }else{
								  $("#questionlibId").append("<option value=\""+data[i].id+"\">"+data[i].value+"</option>"); 
							  }
						 }
					}); */
			 }
		}
		  
		  function loadVersion(sel) {
			//调用Ajax获取版本信息
		     $("#courseVesionSelect").empty();
		     $("#questionlibId").empty();
		     var specialtyId = $("#specialtyId").val();
			 var courseId = $("#courseId").val();
		     if(specialtyId==null || specialtyId=="" || courseId==null || courseId==""){
				  	$("#questionlibId").append("<option value=\"\" \"selected\">请选择</option>"); 
			 }else{
			  	$.getJSON("${ctx}/questionlib/courseKnowledge/getCourseQuestionByCouresId?id="+$("#courseId").val(),{ r:Math.random() },function(data){
				  $("#courseVesionSelect").append("<option value='' selected>全部</option>"); 
				  for (var i=0; i<data.length; i++){
					  if(data.id==""){
						 	 $("#questionlibId").append("<option value=\"\" \"selected\">"+data[i].value+"</option>"); 
					  }else{
					  		$("#courseVesionSelect").append("<option value=\""+data[i].id+"\">"+data[i].value+"</option>"); 
					  }
				  }
				});
			  	/* $.getJSON("${ctx}/questionlib/schoolQuestionlib/getCourseQuestionlibByVersionId?courseId="+courseId+"&specialtyId="+specialtyId,function(data){
					 for (var i=0; i<data.length; i++){
						  if(data.id==""){
						 	 $("#questionlibId").append("<option value=\"\" \"selected\">"+data[i].value+"</option>"); 
						  }else{
							  $("#questionlibId").append("<option value=\""+data[i].id+"\">"+data[i].value+"</option>"); 
						  }
					 }
				}); */
			 }
			  
		} 
		  
		  function loadQuestionlib(sel) {
			  var specialtyId = $("#specialtyId").val();
			  var courseId = $("#courseId").val();
			  var versionId =sel.value;
			 // alert(specialtyId);
			 // alert(courseId);
			 // alert(versionId);
		     //调用Ajax获取题库信息
		     $("#questionlibId").empty();
		     if(specialtyId==null || specialtyId=="" || courseId==null || courseId=="" || versionId==null || versionId==""){
				  	$("#questionlibId").append("<option value=\"\" \"selected\">全部</option>"); 
			 }else{
			  	$.getJSON("${ctx}/questionlib/schoolQuestionlib/getCourseQuestionlibByVersionId?versionId="+versionId+"&courseId="+courseId+"&specialtyId="+specialtyId,{ r:Math.random() },function(data){
					 for (var i=0; i<data.length; i++){
						  if(data.id==""){
						 	 $("#questionlibId").append("<option value=\"\" \"selected\">"+data[i].value+"</option>"); 
						  }else{
							  $("#questionlibId").append("<option value=\""+data[i].id+"\">"+data[i].value+"</option>"); 
						  }
					 }
				});
			 }
		} 
		  
		  function changelib(sel) {
			  var questionlibId =sel.value;
		} 
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/questionlib/schoolQuestionlib/list?schoolId=${schoolId}">学校题库授权列表</a></li>
		<li class="active"><a href="${ctx}/questionlib/schoolQuestionlib/form?id=${schoolQuestionlib.id}&schoolId=${schoolId}">学校题库授权<shiro:hasPermission name="questionlib:schoolQuestionlib:edit">${not empty schoolQuestionlib.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="questionlib:schoolQuestionlib:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="schoolQuestionlib" action="${ctx}/questionlib/schoolQuestionlib/save?schoolId=${schoolId}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
		  <label class="control-label">专业：</label>
			  <div class="controls">
				<sys:treeselect id="specialty" name="specialty.id" value="${specialtyId==null?schoolQuestionlib.specialty.id:specialtyId}" ontextchange="true" labelName="specialty.name" labelValue="${ schoolQuestionlib.specialty.title }"
					title="专业" url="/questionlib/specialty/treeData" cssClass="required" notAllowSelectParent="true" />
<%-- 				<sys:treeselect id="specialty" name="specialty.id" value="${schoolQuestionlib.specialty.id==null?specialtyId:schoolQuestionlib.specialty.id}" ontextchange="true" labelName="specialty.name" labelValue="${schoolQuestionlib.specialty.title==null?(questionlib:getSpecialtyByID(specialtyId).title):schoolQuestionlib.specialty.title}"
					title="专业" url="/questionlib/specialty/treeData" cssClass="required"/> --%>
			 <font style="color: red;">&nbsp;*</font>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">课程：</label>
	         <div class="controls">
			 
				<select class="input-medium required" name="courseId" id="courseId"   onchange="loadVersion(this);">
					<option value="" selected="selected">请选择</option>
			 	     <c:forEach items="${empty specialtyId  ? null : questionlib:getCourseBySpecialtyId(specialtyId)}" var="course">
					   	<option value="${course.id}" ${courseId==course.id?"selected":""}  >${course.title} </option>
					 </c:forEach> 
			   	</select>
			 <font style="color: red;">&nbsp;*</font>
			</div>
		</div>
		
		<div class="control-group">
		  	<label class="control-label">版本：</label>
			  <div class="controls">
			  
				<select name="courseVesionId" id="courseVesionSelect" class="input-medium required" onchange="loadQuestionlib(this);"> 
		 	     <option value="" selected="selected">请选择</option>
		 	     <c:forEach items="${ empty courseId ? null :questionlib:getCourseVersionByCourseId(courseId)}" var="courseVesion">
				   	<option value="${courseVesion.id}" ${courseVesionId==courseVesion.id?"selected":""}  >${courseVesion.title} </option>
				 </c:forEach> 
			  </select>
			  <font style="color: red;">&nbsp;*</font>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">题库：</label>
		   <div class="controls">
			<select name="questionlibId" id="questionlibId" class="input-medium required" onchange="changelib(this);"> 
		 	    <option value="" selected="selected">请选择</option>
		 	 	<c:forEach items="${empty courseVesionId  ? null: questionlib:getCourseQuestionlibByVersionId(courseVesionId)}" var="courseQuestionlib">
				   	<option value="${courseQuestionlib.id}" ${questionlibId==courseQuestionlib.id?"selected":""}  >${courseQuestionlib.title} </option>
				</c:forEach>
			  </select>
			  <font style="color: red;">&nbsp;*</font>
			</div>
			<%-- <div class="controls">
				<form:select path="questionlibId" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${questionlibTld.getCourseQuestionlibByVersionId(courseVesionId)}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div> --%>
		</div>
		<div class="control-group">
			<label class="control-label">有效期开始时间：</label>
			<div class="controls">
				<input id="validStartDate" name="validStartDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${schoolQuestionlib.validStartDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" onFocus="WdatePicker({ minDate: 'new date',maxDate:'#F{$dp.$D(\'validEndDate\')}'})" readonly/><font style="color: red;">&nbsp;&nbsp;*</font>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">有效期结束时间：</label>
			<div class="controls">
				<input id="validEndDate" name="validEndDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${schoolQuestionlib.validEndDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'validStartDate\')}'})" readonly/><font style="color: red;">&nbsp;&nbsp;*</font>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">使用状态：</label>
			<div class="controls">
				<form:select path="state" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('schoolstate')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<font style="color: red;">&nbsp;&nbsp;*</font>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="questionlib:schoolQuestionlib:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	<div style="height:100px; ">&nbsp;</div> 
	
</body>
</html>