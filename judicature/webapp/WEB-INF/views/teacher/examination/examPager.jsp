<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_blank"/>
	<title>组卷模式</title>
	<style type="text/css">
	   .form-actions{margin:0px; border-top: 0px; text-align: right;}
	</style>
	 <%@include file="/WEB-INF/views/teacher/include/validation.jsp" %>
   <script type="text/javascript">
   		 $(function(){
   			  $("input[name='isNew']").change(function(){
   				  var $this=$(this),
   				      controlGroup=$("div.control-group:not(:first)");
   				      newExam =$this.val();
   				  if(newExam&&newExam==="1"){
   					 controlGroup.each(function(key,value){
     					$(value).addClass("hide");
     				 });
   					$("#useTemplatId").removeClass("required");
   					 $("#inputForm").attr("action","${ctx}/examination/examPager");
   				  }if(newExam&&newExam==="0"){
   					 controlGroup.each(function(key,value){
   						if($(value).hasClass("hide")){
   							$(value).removeClass("hide");
   						}
     				 });
   					 $("#useTemplatId").addClass("required");
   					$("#inputForm").attr("action","${ctx }/template/usetemplate");
   				  }
   			  });
   			  
   			  
   			 $("#inputForm").validate({
	 				submitHandler: function(form){
						/* loading('正在提交，请稍等...'); */
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
<c:choose>
<c:when test="${totalQuesCount<=0}">
<script type="text/javascript">
    $(function(){
    	parent.removeJbox();	
		alertx("数据库中没有试题,请先导入试题后重试。",function(){
			parent.removeJbox();		
		});
    });
</script>
  <!--   <div style='padding:10px;color:red;'>数据库中没有试题,请先导入试题后重试。</div> -->
</c:when>
<c:otherwise>
    <%--target="teacherMainFrame" --%>
	<form:form id="inputForm" modelAttribute="examination" action="${ctx}/examination/examPager" method="post" class="form-horizontal" target="teacherMainFrame">
	        <form:hidden path="examType"/>
	        <form:hidden path="id"/>
	        <fieldset>
			     	<legend style="padding:0px;">选择创建方式</legend>
					<div class="control-group">
						<label class="control-label">选择</label>
						<div class="controls">
						    <form:radiobutton path="isNew" value="1" />新建试卷
						    <form:radiobutton path="isNew" value="0" />调用模板
						</div>
					</div>
					<div class="control-group hide">
						<label class="control-label">调用模板:</label>
						<div class="controls">
							<form:select path="useTemplatId"  class="input-small">
							    <form:option value="">请选择</form:option>
							   	<form:options items="${questionlib:getExample()}" itemLabel="title" itemValue="id"  htmlEscape="false"/>
							</form:select>
						</div>
					</div>
					<div class="form-actions">
						<input id="btnSubmit" class="btn tbtn-primary" type="submit" value="下一步"/>
					</div> 
			 </fieldset> 
	  </form:form>
  </c:otherwise>
 </c:choose>
</body>
</html>