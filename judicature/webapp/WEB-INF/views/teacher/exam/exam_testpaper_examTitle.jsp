<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="teacher_default"/>
	<title>试卷信息</title>
<%@include file="/WEB-INF/views/teacher/include/date.jsp"%>
<%@include file="/WEB-INF/views/teacher/include/validation.jsp"%>
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
		 var examStatus=$("#examStatus").val();
		 var examType=$("#examType").val();
		 if(examType=="3"){
			 location.href="${ctx}/exam/examList";
		 }else{
			 if(examType=="2"){
				 if(examStatus=="no"){
					 location.href="${ctx}/testPaper/testPaperListUnSubmit";
				 }else{
					 location.href="${ctx}/testPaper/testPaperList";
				 }
			 }else{
				location.href="${ctx}/testPaper/testPaperOnLineList";
			 }
		 }
	 }
	 
	 function  validata(value,type){
		  var error=true;
			$("#mainTitle"+type+",#subTitle"+type).each(function(i,e){
				var  label = $("<label>").attr("for",e.id).addClass("error").html("必填项");
				var tag=$("label[for="+e.id+"]");
				!!tag&&tag.remove();
				
				if(!!!e.value){
					label.insertAfter($(e).parent());
					error=false;
				}
			});
			if(error){
				 var param=$.query.load(value.href).set("mainTitle",$("#mainTitle"+type).val()).set("subTitle",$("#subTitle"+type).val());
				  $(value).attr("href",$(value).attr("dataUrl")+param);
			  return true;
			}else{
				top.$.jBox.tip("输入有误请检查","error",{persistent:true,opacity:0});
			}
		 
		  return false;
	}
	 
	</script>
</head>
<body>

<form:form id="inputForm"  modelAttribute="exam" action="${ctx}" method="post" class="form-horizontal">
    <font hidden="true">${exam.id}</font>
    <input id="examType" type="hidden" value="${exam.examType}"/>
    <input id="examStatus" type="hidden" value="${examStatus}"/>
     <fieldset>
	     	<legend>试卷信息</legend>
			<div class="control-group">
				<label class="control-label">名称：</label>
				<div class="controls">
					${exam.title}
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">试卷题数：</label>
				<div class="controls">
					${questionlibCount}道
				</div>
			</div>
			<c:choose>
				<c:when test="${exam.examType eq '3'}"></c:when>
				<c:otherwise>
					<div class="control-group">
						<label class="control-label">试卷总分：</label>
						<div class="controls">
						     ${exam.totalScore}分
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">答卷用时：</label>
						<div class="controls">
							${exam.timeDifference}分钟
						</div>
					</div>
				</c:otherwise>
			</c:choose>
			
			<c:choose>
			   <c:when test="${ exam.examType eq 2 }"><%--组卷考试 --%>
			   	<div class="control-group">
					<label class="control-label">题目查看：</label>
					<c:forEach var="item" items="${examdetails}" >
				       <c:if test="${item.type eq '0'||item.type eq 'A'}">
						<div class="controls" style="margin-bottom:10px;">
						     <a href="${ctx}/testPaper/adjustExamList?examdetailId=${item.id}&examId=${exam.id}" class="btn">A卷查看</a>&nbsp;&nbsp;
						      <c:if test="${item.type eq 'B'}">
						    	 <a href="${ctx}/testPaper/adjustExamList?examdetailId=${item.id}&examId=${exam.id}" class="btn">B卷查看</a>
						     </c:if>
						</div>
						<div class="controls" style="margin-bottom:10px;">
						    <label class="control-label" style="text-align: left; width: 60px;">主标题</label>
						     <span>
						     <input id="mainTitle_A" name="mainTitle" type="text"  class="input-medium" value="${item.mainTitle }"/>
						      	<span class="help-inline"><font color="red">*</font> </span>
						     </span>
						     
						</div>
						<div class="controls" style="margin-bottom:10px;">
						 <label class="control-label" style="text-align: left; width: 60px;">副标题</label>
						     <span>
						        <input id="subTitle_A" name="subTitle" type="text" value="${item.subTitle }"  class="input-medium" />
						    	<span class="help-inline"><font color="red">*</font></span>
						     </span>
						</div>
						
						<div class="controls">
				            <a href="${ctx }/common/downLoad/questionsById?quesDetailId=${item.id}" class="btn" dataUrl="${ctx }/common/downLoad/questionsById"   onclick="return validata(this,'_A');"  >下载试卷A</a>
				            <a href="${ctx }/common/downLoad/questionsAnswerById?quesDetailId=${item.id}" class="btn" dataUrl="${ctx }/common/downLoad/questionsAnswerById" onclick="return validata(this,'_A');"  >下载试卷A答案及讲解</a>
						</div>
				    </c:if>
				    
				    
			        <c:if test="${item.type eq 'B'}">
			          	<div class="controls">
							<input id="BExam" type="hidden" value="B"/>
						    						     
						</div>
			          	<div class="controls" style="margin-bottom:10px;">
						    <label class="control-label" style="text-align: left; width: 60px;">主标题</label>
						     <span>
						     <input id="mainTitle_B" name="mainTitle" type="text"  class="input-medium" value="${item.mainTitle }" />
						      	<span class="help-inline"><font color="red">*</font> </span>
						     </span>
						     
						</div>
						<div class="controls" style="margin-bottom:10px;">
						 <label class="control-label" style="text-align: left; width: 60px;">副标题</label>
						     <span>
						        <input id="subTitle_B" name="subTitle" type="text"  class="input-medium" value="${item.subTitle }" />
						    	<span class="help-inline"><font color="red">*</font></span>
						     </span>
						</div>
			          <div class="controls">
			            <a href="${ctx }/common/downLoad/questionsById?quesDetailId=${item.id}" class="btn" dataUrl="${ctx }/common/downLoad/questionsById"  onclick="return validata(this,'_B');"  >下载试卷B</a>
			            <a href="${ctx }/common/downLoad/questionsAnswerById?quesDetailId=${item.id}" class="btn" dataUrl="${ctx }/common/downLoad/questionsAnswerById"  onclick="return validata(this,'_B');"  >下载试卷B答案及讲解</a>
			          </div>
			        </c:if>
			     </c:forEach>
				</div>
			   
			   </c:when>
			   <c:otherwise> 
					<div class="control-group">
						<label class="control-label">试卷查看</label>
						  	<div class="controls">
						     <c:forEach var="item" items="${examdetails}" >
						        <c:if test="${item.type eq '0'||item.type eq 'A'}">
						           <a href="${ctx}/testPaper/adjustExamList?examdetailId=${item.id}&examId=${exam.id}" class="btn">A卷查看</a>
						        </c:if>
						        <c:if test="${item.type eq 'B'}">
						        	<input id="BExam" type="hidden" value="B"/>
						         <a href="${ctx}/testPaper/adjustExamList?examdetailId=${item.id}&examId=${exam.id}" class="btn">B卷查看</a>
						        </c:if>
						     </c:forEach>
						     
						</div>
					</div>	
			  </c:otherwise>
			</c:choose>
			<div class="form-actions">
				<input id="btnSubmit" class="btn tbtn-primary" type="button" onclick="publish()" value="返回"/>
			</div>
	</fieldset>
</form:form>

</body>
</html>