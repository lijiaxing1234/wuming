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
			var questionType = $("#questionType").val();
			//alert("readycount"+count);
			//alert("readytype"+questionType);
			selectType(questionType,count);
		});
		function selectType(questionType,count){
			if(questionType==null || questionType==""){
				var questionType = $("#questionType").val();
			}
			//alert("selectcount"+count);
			//alert("selecttype"+questionType);
			if(questionType=="1"){
				//1-单选题
				if(count==null || count==""){
					i = 1;
				}else{
					i = count;
				}
				/* alert("singlechoice"+i); */
				$(".addSelect").remove();
				$(".addAnswer").remove();
				$("#choices").append("<br class=\"addSelect\"/><input type=\"button\" value=\"添加选项\" onclick=\"addSelect();\" class=\"addSelect\"/>");
				$("#choices").append("<input type=\"button\" value=\"删除选项\" onclick=\"deleteSelect();\" class=\"addSelect\"/>");
				$("#choices").append("<br class=\"addSelect\"/><font class=\"addSelect\">选项：</font>");
				//$("#questionAnswer").hide();
				//$("#newAnswer").append("<input name=\"answer0\" type=\"text\" value=\"${versionQuestion.answer0}\" class=\"addSelect\"/>");
				for (var j = 0; j < i; j++) {
					addSelect(j);
				}
			}else if(questionType==2){
				//2-填空题
				if(count==null || count==""){
					i = 1;
				}else{
					i = count;
				}
				$(".addSelect").remove();
				$(".addAnswer").remove();
				//$("#questionAnswer").hide();
				$("#newAnswer").append("<input type=\"button\" value=\"添加答案\" onclick=\"addAnswer();\" class=\"addAnswer\"/>");
				$("#newAnswer").append("<input type=\"button\" value=\"删除答案\" onclick=\"deleteAnswer();\" class=\"addAnswer\"/>");
				for (var j = 1; j < i; j++) {
					var answer = $("#answer"+j).val();
					/* alert(answer); */
					$("#newAnswer").append("<br class=\"addAnswer\"/><input name=\"answer"+j+"\" type=\"text\" value=\""+answer+"\"  class=\"addAnswer\"/>");
				}
			}else if(questionType==3){
				//3-计算题
				$(".addSelect").remove();
				$(".addAnswer").remove();
				//$("#questionAnswer").show();
			}else if(questionType==4){
				//4-简答题
				$(".addSelect").remove();
				$(".addAnswer").remove();
				//$("#questionAnswer").show();
			}else if(questionType==5){
				//5-多选题
				if(count==null || count==""){
					i = 1;
				}else{
					i = count;
				}
				/* alert("multichoice"+i); */
				$(".addSelect").remove();
				$(".addAnswer").remove();
				$("#choices").append("<br class=\"addSelect\"/><input type=\"button\" value=\"添加选项\" onclick=\"addSelect();\" class=\"addSelect\"/>");
				$("#choices").append("<input type=\"button\" value=\"删除选项\" onclick=\"deleteSelect();\" class=\"addSelect\"/>");
				$("#choices").append("<br class=\"addSelect\"/><font class=\"addSelect\">选项：</font>");
				//$("#questionAnswer").hide();
				//$("#newAnswer").append("<input name=\"answer0\" type=\"text\" value=\"${versionQuestion.answer0}\" class=\"addSelect\"/>");
				for (var j = 0; j < i; j++) {
					addSelect(j);
				}
			}else{
				
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
			}
			/* alert(select);
			alert(k); */
			var choice = $("#choice"+k).val();
			$("#choices").append("<br class=\"addSelect choice"+k+"\"/><fon class=\"addSelect choice"+k+"\">"+select+":</font><input name=\"choice"+k+"\" type=\"text\" value=\""+choice+"\"  class=\"addSelect\"/>");
			if(j==null){
				i++;
				$("#newCount").val(i);
			}	
			/* alert("newCount"+$("#newCount").val()); */
		}
		
		//删除选项
		function deleteSelect(){
			if(i<3){
				i=2;
				$("#choiceMessage").html("<font color=\"red\">选择题至少有两个选项,无法继续删除!</font>");
			}else{
				i--;
				$(".choice"+i).remove();
				$("#newCount").val(i);
			}
		}
		
		//添加答案
		function addAnswer(j){
			if(j!=null){
				i=j;
			}
			$("#newAnswer").append("<br class=\"addAnswer answer"+i+"\"/><input name=\"answer"+i+"\" type=\"text\" value=\"\"  class=\"addAnswer answer"+i+"\"/>");
				i++;
				$("#newCount").val(i);
				/* alert($("#newCount").val()); */
		}
		
		//删除答案
		function deleteAnswer(){
			if(i<2){
				i=1;
				$("#answerMessage").html("<font color=\"red\">填空题至少应存在一个答案,不能继续删除!</font>");
			}else{
				i--;
				$(".answer"+i).remove();
				$("#newCount").val(i);
			}
		}
	</script>
</head>
<body>
	
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/questionlib/versionQuestion/list?extId=${extId}&courseQuestionlibId=${courseQuestionlibId}">试题列表</a></li>
		<li class="active"><a href="${ctx}/questionlib/versionQuestion/form?id=${versionQuestion.id}&extId=${extId}&courseQuestionlibId=${courseQuestionlibId}">试题<shiro:hasPermission name="questionlib:versionQuestion:edit">${not empty versionQuestion.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="questionlib:versionQuestion:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
		<input id="choice0" type="hidden" value="${versionQuestion.choice0}"></div>
		<input id="choice1" type="hidden" value="${versionQuestion.choice1}"></div>
		<input id="choice2" type="hidden" value="${versionQuestion.choice2}"></div>
		<input id="choice3" type="hidden" value="${versionQuestion.choice3}"></div>
		<input id="choice4" type="hidden" value="${versionQuestion.choice4}"></div>
		<input id="choice5" type="hidden" value="${versionQuestion.choice5}"></div>
		<input id="choice6" type="hidden" value="${versionQuestion.choice6}"></div>
		<input id="choice7" type="hidden" value="${versionQuestion.choice7}"></div>
		<input id="choice8" type="hidden" value="${versionQuestion.choice8}"></div>
		<input id="choice9" type="hidden" value="${versionQuestion.choice9}"></div>
		<%-- <input id="answer0" type="hidden" value="${versionQuestion.answer0}"></div> --%>
		<input id="answer1" type="hidden" value="${versionQuestion.answer1}"></div>
		<input id="answer2" type="hidden" value="${versionQuestion.answer2}"></div>
		<input id="answer3" type="hidden" value="${versionQuestion.answer3}"></div>
		<input id="answer4" type="hidden" value="${versionQuestion.answer4}"></div>
		<input id="answer5" type="hidden" value="${versionQuestion.answer5}"></div>
		<input id="answer6" type="hidden" value="${versionQuestion.answer6}"></div>
		<input id="answer7" type="hidden" value="${versionQuestion.answer7}"></div>
		<input id="answer8" type="hidden" value="${versionQuestion.answer8}"></div>
		<input id="answer9" type="hidden" value="${versionQuestion.answer9}"></div>
		<input id="count" type="hidden" value="${versionQuestion.count}"/>
	<form:form id="inputForm" modelAttribute="versionQuestion" action="${ctx}/questionlib/versionQuestion/save?extId=${extId}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input id="newCount" type="hidden" name="count" value=""/>
		<input id="courseQuestionlibId" type="hidden" name="courseQuestionlibId" value="${courseQuestionlibId}"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">知识点：</label>
				<div class="controls">
                <sys:treeselect id="courseKnowledge" name="courseKnowledge.id" value="${versionQuestion.courseKnowledge.id}" labelName="courseKnowledge.title" labelValue="${versionQuestion.examCode}"
					title="知识点" url="/questionlib/versionQuestion/treeData?courseQuestionlibId=${courseQuestionlibId}" cssClass="required" checked="true" notAllowSelectParent="true" notAllowSelectRoot="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">题型：</label>
			<div class="controls">
				<form:select path="quesType" class="input-xlarge " onchange="selectType(${type});" id="questionType">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('question_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">难度：</label>
			<div class="controls">
				<form:select path="quesLevel" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('question_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分值：</label>
			<div class="controls">
				<form:input path="quesPoint" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">试题描述：</label>
			<div id="choiceMessage" class="controls"></div>
			<div class="controls" id="questionTitle">
				<%-- <form:input path="title" htmlEscape="false" maxlength="1000" class="input-xlarge "/> --%>
				<form:textarea id="title" htmlEscape="true" path="title" rows="4" maxlength="200" class="input-xxlarge"/>
					<sys:ckeditor replace="title" uploadPath="/questionlib/title" height="50"/>
			</div>
			<div class="controls" id="choices"></div>
		</div>
		<div class="control-group">
			<label class="control-label">答案：</label>
			<div class="controls" id="questionAnswer">
				<%-- <form:input path="answer" htmlEscape="false" maxlength="1000" class="input-xlarge "/> --%>
				<form:textarea id="answer0" htmlEscape="true" path="answer0" rows="4" maxlength="200" class="input-xxlarge"/>
					<sys:ckeditor replace="answer0" uploadPath="/questionlib/answer" height="50"/>
			</div>
			<div id=answerMessage class="controls"></div>
			<div class="controls" id="newAnswer"></div>
		</div>
		<div class="control-group">
			<label class="control-label">讲解：</label>
			<div class="controls" id="questionDescription">
				<%-- <form:input path="description" htmlEscape="false" maxlength="1000" class="input-xlarge "/> --%>
				<form:textarea id="description" htmlEscape="true" path="description" rows="4" maxlength="200" class="input-xxlarge"/>
					<sys:ckeditor replace="description" uploadPath="/questionlib/description" height="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出题人：</label>
			<div class="controls">
				<form:input path="writer" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="questionlib:versionQuestion:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>