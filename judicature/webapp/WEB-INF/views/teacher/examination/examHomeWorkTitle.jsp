<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	<title>题目调整</title>
		<%@include file="/WEB-INF/views/teacher/include/date.jsp" %>
	<style type="text/css">
	   .form-actions{margin:0px; border-top: 0px; text-align: right;}
	   .input-mini{width:30px;}
	</style>
	<script type="text/javascript">
	  
	  try{ top.$.jBox.closeTip();}catch(e){}
	
	  $(document).ready(function() {
		  
		  var obj=parent.$("iframe[id='teacherMainFrame']");
		 // console.log(obj.html());
		  
		  $("input[id^='btnSubmit_']").click(function(){
			var examId=$(this).attr("id").split("_")[1];
			  var examTitle=$("#examTitle").val();
			alertx("发布成功！",function(){
				if(obj){
			       obj.attr("src","${ctx}/examinationHomeWork/publishHomeWorkExam?examId="+examId);
				}else{
				   top.window.location.href ='${ctx}/examinationHomeWork/publishHomeWorkExam?examId='+examId+'&title='+examTitle; 
				}
			});
			
	 		//jAlert("发布成功！");
		  });
		  $("input[id^='btnSave_']").click(function(){
			  var examId=$(this).attr("id").split("_")[1];
			  var examTitle=$("#examTitle").val();
		 	/*   top.window.location.href ='${ctx}/examinationHomeWork/saveHomeWorkExam?examId='+examId; 
		 	  jAlert("保存成功！"); */
		 	     alertx("保存成功！",function(){
					if(obj){
						obj.attr("src","${ctx}/examinationHomeWork/saveHomeWorkExam?examId="+examId); 
					}else{
					   top.window.location.href ='${ctx}/examinationHomeWork/saveHomeWorkExam?examId='+examId+'&title='+examTitle; 
					}
				});
		  });
	  });
	</script>
</head>
<body>

<form:form id="inputForm"  modelAttribute="examination" action=" " method="post" class="form-horizontal">
    <font hidden="true">${examination.id}</font>
     <fieldset>
	     	<legend>课后题题目调整</legend>
			<div class="control-group">
				<label class="control-label">名称：</label>
				<div class="controls">
				<%-- 	${examination.title} --%>
				   <input id="examTitle" type="text" name="title" value="${examination.title}" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">第一步:题目微调</label>
				<div class="controls">
				  	<c:forEach var="item" items="${examdetails}" >
				        <c:if test="${item.type eq '0'||item.type eq 'A'}">
				           <a href="${ctx}/examinationHomeWork/adjustExamList?examdetailId=${item.id}&examId=${examination.id}" class="btn">题目调整</a>
				        </c:if>
				       
				     </c:forEach>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">第二步:</label>
				<div class="controls">
				    下&nbsp;&nbsp;&nbsp;载&nbsp;&nbsp;&nbsp;&nbsp;
				    <!-- <input id="downQuestion" class="btn btn-primary" type="button" value="下载题目"/> -->
				    <a href="${prc }/userfiles/template.docx"  class="btn tbtn-primary">下载题目 </a>&nbsp;&nbsp;&nbsp;&nbsp;
				    <a href="${prc }/userfiles/template.docx"  class="btn tbtn-primary">下载答案及讲解 </a>
					<!-- <input id="downAnswer" class="btn btn-primary" type="button" value="下载答案及讲解"/> -->
				</div>
			</div>
			
			<div class="form-actions">
				<input id="btnSave_${examination.id}" class="btn tbtn-primary" type="button" value="保存"/>
				<input id="btnSubmit_${examination.id}" class="btn tbtn-primary" type="button" value="发布"/>
			</div>
	</fieldset>
</form:form>

</body>
</html>