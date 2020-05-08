<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>试题管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		var i = 1;
		$(document).ready(function() {
			var count = $("#count").val();
			if(count==null || count==""){
				count=1;
			}
			$("#newCount").val(count);	
			var questionType = $("#questionType").val();
			selectType(questionType,count);
		});
		function selectType(questionType,count){
			$(".addSelect").remove();
			$(".addAnswer").remove();
			for (var int = 0; int < 10; int++) {
				$(".choice"+i).remove();
				$(".answer"+i).remove();
			}
			if(questionType==null || questionType==""){
				var questionType = $("#questionType").val();
			}
			if(count==null || count==""){
				count=1;
			}
			if(questionType=="1" || questionType==5){
				//1-单选题/5-多选题
				i = count;
				for (var j = 0; j < i; j++) {
					addSelect(j);
				}
			}else if(questionType==2){
				//2-填空题
				i = count;
				if(i>1){
					for (var j = 1; j < i; j++) {
						$("#answer"+j).append("<br/>");
					}
				}
			}else if(questionType==3 || questionType==4 || (questionType>=6 && questionType<=10)){
				//3-计算题  / 4-简答题  / 6-概念题  / 7-综合题  / 8-作图题  / 9-制表题  / 10-识图题  

			}else if(questionType==11){
				// 11-判断题
				$("#choices").append("<br/>");
				$("#choices").append("<br/>"); 
			}
		}
		
		//添加选项
		function addSelect(j){
			var k = 0;
			if(j!=null){
				k=j;
			}else{
				k=i;
			}
			var select = "";
			if(k==0){
				select = "A";
			}else if(k==1){
				select = "B";
			}else if(k==2){
				select = "C";
			}else if(k==3){
				select = "D";
			}else if(k==4){
				select = "E";
			}else if(k==5){
				select = "F";
			}else if(k==6){
				select = "G";
			}else if(k==7){
				select = "H";
			}else if(k==8){
				select = "I";
			}else if(k==9){
				select = "J";
			}else{
				alert("选项至多为10个!");
				return;
			}
			if(j==null){
				i++;
				$("##choice"+j).append("<br>");
			}	
		}
		
	</script>
</head>
<body>
	
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/questionlib/versionQuestion/list?extId=${extId}&courseQuestionlibId=${courseQuestionlibId}">试题列表</a></li>
		<li class="active"><a href="${ctx}/questionlib/versionQuestion/form?id=${versionQuestion.id}&extId=${extId}&courseQuestionlibId=${courseQuestionlibId}&operation=view">试题<shiro:hasPermission name="questionlib:versionQuestion:edit">${not empty versionQuestion.id?'查看':'添加'}</shiro:hasPermission><shiro:lacksPermission name="questionlib:versionQuestion:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
		<form>
		<input id="quesType" type="hidden" value="${versionQuestion.quesType}">
		<input id="count" type="hidden" value="${versionQuestion.count}"/>
		<input  id="questionType" type="hidden" value="${versionQuestion.quesType }"> 
		</form>
	<form:form id="inputForm" modelAttribute="versionQuestion" action="${ctx}/questionlib/versionQuestion/save?extId=${extId}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input id="newCount" type="hidden" name="count" value=""/>
		<input id="courseQuestionlibId" type="hidden" name="courseQuestionlibId" value="${courseQuestionlibId}"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">专业：</label>
			<div class="controls" style="margin-top: 3px;"  >
			
			${questionlib:getSpecialtyByID(questionlib:getCourseByID(questionlib:getCourseVersionByVersionId(versionQuestion.versionId).courseId).specialtyId).title}
				<%-- <input type="text" value="${questionlib:getSpecialtyByID(questionlib:getCourseByID(questionlib:getCourseVersionByVersionId(versionQuestion.versionId).courseId).specialtyId).title}" disabled="disabled"/> --%>
			</div>
	     </div>
		 <div class="control-group">
			<label class="control-label">课程：</label>
			<div class="controls"   style="margin-top: 3px;" >
			${questionlib:getCourseByID(questionlib:getCourseVersionByVersionId(versionQuestion.versionId).courseId).title}
				<%-- <input type="text" value="${questionlib:getCourseByID(questionlib:getCourseVersionByVersionId(versionQuestion.versionId).courseId).title}" disabled="disabled"/> --%>
			</div>
			 
            </div>
		 <div class="control-group">
			<label class="control-label">版本：</label>
			<div class="controls"  style="margin-top: 3px;" >
			
			${questionlib:getCourseVersionByVersionId(versionQuestion.versionId).title}
				<%-- <input type="text" value="${questionlib:getCourseVersionByVersionId(versionQuestion.versionId).title}" disabled="disabled"/> --%>
			</div>
			 </div>
		 <div class="control-group">
			<label class="control-label">题库：</label>
			<div class="controls"  style="margin-top: 3px;" >
			
			${questionlib:getCourseQuestionlibById(versionQuestion.questionlibId).title}
				<%-- <input type="text" value="${questionlib:getCourseQuestionlibById(versionQuestion.questionlibId).title}" disabled="disabled"/> --%>
			</div>
		 </div>
		 <div class="control-group">
			<label class="control-label">知识点：</label>
			<div class="controls"  style="margin-top: 3px;" >
			${versionQuestion.examCode}
				<%-- <form:input path="examCode" htmlEscape="false" maxlength="1000" class="input-xlarge " disabled="true"/> --%>
			</div>
		</div>
		
		
		
		<div class="control-group">
			<label class="control-label">题型：</label>
			<div class="controls"  style="margin-top: 3px;" >
			${fns:getDictLabel(versionQuestion.quesType, 'question_type', '')}
				<%-- <input name="coursePhase" value="${fns:getDictLabel(versionQuestion.quesType, 'question_type', '')}" class="input-xlarge"  disabled="true"/> --%>
			</div>
		</div>
		<div class="control-group" >
			<label class="control-label">难度：</label>
			<div class="controls"   style="margin-top: 3px;">
			${fns:getDictLabel(versionQuestion.quesLevel, 'question_level', '')}
				<%-- <input name="coursePhase" value="${fns:getDictLabel(versionQuestion.quesLevel, 'question_level', '')}" class="input-xlarge"  disabled="true"/> --%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分值：</label>
			<div class="controls"   style="margin-top: 3px;">
			   ${versionQuestion.quesPoint}
				<%-- <form:input path="quesPoint" htmlEscape="false" maxlength="1000" class="input-xlarge " disabled="true"/> --%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">试题描述：</label>
			<div id="choiceMessage" class="controls"  style="margin-top: 3px;"></div>
			<div class="controls" id="questionTitle"   >
				${versionQuestion.title}
			</div>
			
		</div>
		
		<div class="control-group">
			<label class="control-label">选项：</label>
			 <div class="controls" id="choices"  style="margin-top: 3px;">
				<p id="choice0">${versionQuestion.choice0}</p>
				<p id="choice1">${versionQuestion.choice1}</p>
				<p id="choice2">${versionQuestion.choice2}</p>
				<p id="choice3">${versionQuestion.choice3}</p>
				<p id="choice4">${versionQuestion.choice4}</p>
				<p id="choice5">${versionQuestion.choice5}</p>
				<p id="choice6">${versionQuestion.choice6}</p>
				<p id="choice7">${versionQuestion.choice7}</p>
				<p id="choice8">${versionQuestion.choice8}</p>
				<p id="choice9">${versionQuestion.choice9}</p>
			</div>
		 </div>
		<div class="control-group">
			<label class="control-label">答案：</label>
			<div class="controls" id="questionAnswer"  style="margin-top: 3px;">
				${versionQuestion.answer0}
			</div>
			<div id=answerMessage class="controls"></div>
			<div class="controls" id="newAnswer">
				<p id="answer1">${versionQuestion.answer1}</p>
				<p id="answer1">${versionQuestion.answer2}</p>
				<p id="answer1">${versionQuestion.answer3}</p>
				<p id="answer1">${versionQuestion.answer4}</p>
				<p id="answer1">${versionQuestion.answer5}</p>
				<p id="answer1">${versionQuestion.answer6}</p>
				<p id="answer1">${versionQuestion.answer7}</p>
				<p id="answer1">${versionQuestion.answer8}</p>
				<p id="answer1">${versionQuestion.answer9}</p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">讲解：</label>
			<div class="controls" id="questionDescription"  style="margin-top: 3px;">
				${versionQuestion.description}
			</div>
		</div>
		<div class="control-group" >
			<label class="control-label">出题人：</label>
			<div class="controls"  style="margin-top: 3px;" >
			
			        ${versionQuestion.writer}
			<%-- 	<form:input path="writer" htmlEscape="false" maxlength="32" class="input-xlarge " disabled="true"/> --%>
			</div>
		</div>
		
		<div style="height:50px; ">&nbsp</div>
		<div class="form-actions">
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<div style="height:200px; ">&nbsp</div>
	</form:form>
</body>
</html>