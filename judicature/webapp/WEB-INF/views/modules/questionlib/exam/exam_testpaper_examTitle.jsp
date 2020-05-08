<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>题目调整</title>
<script type="text/javascript" src="${ctxStatic}/jquery/jquery.query.js"></script>
<style type="text/css">
	   .form-actions{margin:0px; border-top: 0px; text-align: right;}
	   .input-mini{width:30px;}
	   
	   
	</style>
	<script type="text/javascript">
	 $(document).ready(function() {
		 var selectAOrB=$("#BExam").val();
		 if(selectAOrB!="B"){
			 $("#ABexamSelect").attr("hidden","true");
		 }
	 });
	 function publish() {
		location.href="${ctx}/questionlib/exam/list";
	 }
	 
	</script>
</head>
<body>

<form:form id="inputForm"  modelAttribute="exam" action="${ctx}" method="post" class="form-horizontal">
    <font hidden="true">${exam.id}</font>
    <input id="examType" type="hidden" value="${exam.examType}"/>
     <fieldset>
	     	<legend>课后题题目调整</legend>
			<div class="control-group">
				<label class="control-label">名称：</label>&nbsp;&nbsp;${exam.title}
			</div>
			
			<c:choose>
			   <c:when test="${ exam.examType eq 2 }"><%--组卷考试 --%>
			   	<div class="control-group">
					<label class="control-label">题目微调：</label>
					<c:forEach var="item" items="${examdetails}" >
				       <c:if test="${item.type eq '0'||item.type eq 'A'}">
				        <input id="saveExam" class="btn btn-primary" type="button" style="padding: 4px;margin: 3px;" value="A卷查看" onclick="location.href='${ctx}/questionlib/exam/adjustExamList?examdetailId=${item.id}&examId=${exam.id};"/>
						     <%-- <a href="${ctx}/questionlib/exam/adjustExamList?examdetailId=${item.id}&examId=${exam.id}" class="btn">A卷查看</a> --%>
						<%-- <div class="controls">
						    <label class="control-label" style="text-align: left; width: 60px;">主标题</label>
						     <span>
						     <input id="mainTitle_A" name="mainTitle" type="text"  class="input-medium" value="${item.mainTitle }"/>
						      	<span class="help-inline"><font color="red">*</font> </span>
						     </span>
						     
						</div>
						<div class="controls">
						 <label class="control-label" style="text-align: left; width: 60px;">副标题</label>
						     <span>
						        <input id="subTitle_A" name="subTitle" type="text" value="${item.subTitle }"  class="input-medium" />
						    	<span class="help-inline"><font color="red">*</font></span>
						     </span>
						</div>
						
						<div class="controls">
				            <a href="${ctx }/common/downLoad/questionsById?quesDetailId=${item.id}" class="btn" dataUrl="${ctx }/common/downLoad/questionsById"   onclick="return validata(this,'_A');"  >下载试卷A</a>
				            <a href="${ctx }/common/downLoad/questionsAnswerById?quesDetailId=${item.id}" class="btn" dataUrl="${ctx }/common/downLoad/questionsAnswerById" onclick="return validata(this,'_A');"  >下载试卷A答案及讲解</a>
						</div> --%>
				    </c:if>
				    
				    
			        <c:if test="${item.type eq 'B'}">
							<input id="BExam" type="hidden" value="B"/>
							<input id="saveExam" class="btn btn-primary" type="button" style="padding: 4px;margin: 3px;" value="B卷查看" onclick="location.href='${ctx}/questionlib/exam/adjustExamList?examdetailId=${item.id}&examId=${exam.id};"/>
						   <%--  <a href="${ctx}/questionlib/exam/adjustExamList?examdetailId=${item.id}&examId=${exam.id}" class="btn">B卷查看</a> --%>						     
			          	<%-- <div class="controls">
						    <label class="control-label" style="text-align: left; width: 60px;">主标题</label>
						     <span>
						     <input id="mainTitle_B" name="mainTitle" type="text"  class="input-medium" value="${item.mainTitle }" />
						      	<span class="help-inline"><font color="red">*</font> </span>
						     </span>
						     
						</div>
						<div class="controls">
						 <label class="control-label" style="text-align: left; width: 60px;">副标题</label>
						     <span>
						        <input id="subTitle_B" name="subTitle" type="text"  class="input-medium" value="${item.subTitle }" />
						    	<span class="help-inline"><font color="red">*</font></span>
						     </span>
						</div>
			          <div class="controls">
			            <a href="${ctx }/common/downLoad/questionsById?quesDetailId=${item.id}" class="btn" dataUrl="${ctx }/common/downLoad/questionsById"  onclick="return validata(this,'_B');"  >下载试卷B</a>
			            <a href="${ctx }/common/downLoad/questionsAnswerById?quesDetailId=${item.id}" class="btn" dataUrl="${ctx }/common/downLoad/questionsAnswerById"  onclick="return validata(this,'_B');"  >下载试卷B答案及讲解</a>
			          </div> --%>
			        </c:if>
			     </c:forEach>
				</div>
			
			   
			   </c:when>
			   <c:otherwise> 
					<div class="control-group">
						<label class="control-label">题目微调：</label>
						     <c:forEach var="item" items="${examdetails}" >
						        <c:if test="${item.type eq '0'||item.type eq 'A'}">
						        
						         <input id="saveExam" class="btn " type="button" style="padding: 4px;margin: 3px;" value="A卷查看" onclick="location.href='${ctx}/questionlib/exam/adjustExamList?examdetailId=${item.id}&examId=${exam.id};'"/>
						          <%--  <a href="${ctx}/questionlib/exam/adjustExamList?examdetailId=${item.id}&examId=${exam.id}" class="btn">A卷查看</a> --%>
						        </c:if>
						        <c:if test="${item.type eq 'B'}">
						        	<input id="BExam" type="hidden" value="B"/>
						        	<input id="saveExam" class="btn  " type="button" style="padding: 4px;margin: 3px;" value="B卷查看" onclick="location.href='${ctx}/questionlib/exam/adjustExamList?examdetailId=${item.id}&examId=${exam.id};'"/>
						        <%--  <a href="${ctx}/questionlib/exam/adjustExamList?examdetailId=${item.id}&examId=${exam.id}" class="btn">B卷查看</a> --%>
						        </c:if>
						     </c:forEach>
						     
					</div>	
			  </c:otherwise>
			
			</c:choose>
			
			<div align="center">
				<input id="btnSubmit" class="btn  " type="button" style="padding: 4px;margin: 3px;" onclick="publish()" value="返回"/>
			</div>
	</fieldset>
</form:form>

</body>
</html>