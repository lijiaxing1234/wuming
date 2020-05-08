<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<title>试题管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
	.nav-tabs{font-weight:normal;}
	.pagination ul > li > a{border-color:#e5e5e5;}
	.pagination ul > .controls > a{border-color:#e5e5e5;}
	.nav-tabs{font-weight:normal;}
	.pagination ul > .disabled > a{color:#666;}
	.pagination{width:auto;margin:20px 0;text-align:center;}
	</style>
	<script type="text/javascript">
	var i = 1;
	$(document).ready(function() {
		
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
				}else if(element.parent().is("span")){
					error.appendTo(element.parent());
				} else {
					error.insertAfter(element);
				}
			}
		});
		
		
		var count = $("#count").val();
		if(count==null || count==""){
			count=1;
		}
		$("#newCount").val(count);	
		var questionType = $("#questionType").val();
		//alert("readycount"+count);
		//alert("readytype"+questionType);
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
		//alert("selectcount"+count);
		//alert("selecttype"+questionType);
		if(questionType=="1" || questionType==5){
			//1-单选题/5-多选题
			i = count;
			$("#choices").append("<br class=\"addSelect\"/><input type=\"button\" value=\"添加选项\" onclick=\"addSelect();\" class=\"addSelect btn\"/> &nbsp;&nbsp;&nbsp;");
			$("#choices").append("<input type=\"button\" value=\"删除选项\" onclick=\"deleteSelect();\" class=\"addSelect btn\"/>");
			$("#choices").append("<br class=\"addSelect\"/><font class=\"addSelect\">选项：</font>");
			for (var j = 0; j < i; j++) {
				addSelect(j);
			}
		}else if(questionType==2){
			//2-填空题
			i = count;
			$("#newAnswer").append("<input type=\"button\" value=\"添加答案\" onclick=\"addAnswer();\" class=\"addAnswer btn\"/> &nbsp;&nbsp;&nbsp;");
			$("#newAnswer").append("<input type=\"button\" value=\"删除答案\" onclick=\"deleteAnswer();\" class=\"addAnswer btn\"/>");
			if(i>1){
				for (var j = 1; j < i; j++) {
					var answer = $("#answer"+j).val();
					$("#newAnswer").append("<br class=\"addAnswer\"/><input name=\"answer"+j+"\" type=\"text\" value=\""+answer+"\"  class=\"addAnswer\"/>");
				}
			}
		}else if(questionType==3 || questionType==4 || (questionType>=6 && questionType<=10)){
			//3-计算题  / 4-简答题  / 6-概念题  / 7-综合题  / 8-作图题  / 9-制表题  / 10-识图题  

		}else if(questionType==11){
			// 11-判断题
			count = 2;
			i=count;
			$("#choices").append("<br class=\"addSelect\"/><font class=\"addSelect\">选项：</font>");
			var choice0 = $("#choice0").val();
			var choice1 = $("#choice1").val();
			if(choice0==null || choice0==""){
				choice0="1(正确)";
			}
			if(choice1==null || choice1==""){
				choice1="2(错误)";
			}
			$("#choices").append("<br class=\"addSelect\"/><input name=\"choice0\" type=\"text\" value=\""+choice0+"\"  class=\"addSelect\"/>");
			$("#choices").append("<br class=\"addSelect\"/><input name=\"choice1\" type=\"text\" value=\""+choice1+"\"  class=\"addSelect\"/>");
		}
		$("#newCount").val(count);
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
			alertx("选项至多为10个!");
			return;
		}
		var choice = $("#choice"+k).val();
		$("#choices").append("<br class=\"addSelect choice"+k+"\"/><font class=\"addSelect choice"+k+"\">"+select+":</font><input name=\"choice"+k+"\" type=\"text\" style=\"margin:8px;\" value=\""+choice+"\"  class=\"addSelect choice"+k+"\"/>");
		if(j==null){
			i++;
			$("#newCount").val(i);
		}	
	}
	
	//删除选项
	function deleteSelect(){
		if(i<2){
			i=1;
			//$("#choiceMessage").html("<font color=\"red\">选择题至少有两个选项,无法继续删除!</font>");
			alertx("选择题至少保留一个选项!");
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
		if(i>=10){
			alertx("填空题至多有10个空!");
			return;
		}
		$("#newAnswer").append("<br class=\"addAnswer answer"+i+"\"/><input name=\"answer"+i+"\" type=\"text\" value=\"\"  class=\"addAnswer answer"+i+"\"/>");
			i++;
			$("#newCount").val(i);
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
	<style type="">
		.btn-primary{background:#1c9993;background-image:-webkit-linear-gradient(top,#1c9993,#1c9993);}
		.btn-gray{background:#ccc ! important;background-image:-webkit-linear-gradient(top,#ccc,#ccc) ! important;color:#333;}
		.btn-gray:hover{color:#333;}
	</style>
</head>
<body style="float: left;">
	
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/examQues/list?knowledgeId=${knowledgeId}">试题列表</a></li>
		<li class="active"><a href="${ctx}/examQues/form?id=${versionQuestion.id}&knowledgeId=${knowledgeId}">试题${not empty versionQuestion.id?'修改':'添加'}</a></li>
	</ul><br/>
		<form>
		<input id="quesType" type="hidden" value="${versionQuestion.quesType}">
		<input id="choice0" type="hidden" value="${versionQuestion.choice0}">
		<input id="choice1" type="hidden" value="${versionQuestion.choice1}">
		<input id="choice2" type="hidden" value="${versionQuestion.choice2}">
		<input id="choice3" type="hidden" value="${versionQuestion.choice3}">
		<input id="choice4" type="hidden" value="${versionQuestion.choice4}">
		<input id="choice5" type="hidden" value="${versionQuestion.choice5}">
		<input id="choice6" type="hidden" value="${versionQuestion.choice6}">
		<input id="choice7" type="hidden" value="${versionQuestion.choice7}">
		<input id="choice8" type="hidden" value="${versionQuestion.choice8}">
		<input id="choice9" type="hidden" value="${versionQuestion.choice9}">
		<input id="answer1" type="hidden" value="${versionQuestion.answer1}">
		<input id="answer2" type="hidden" value="${versionQuestion.answer2}">
		<input id="answer3" type="hidden" value="${versionQuestion.answer3}">
		<input id="answer4" type="hidden" value="${versionQuestion.answer4}">
		<input id="answer5" type="hidden" value="${versionQuestion.answer5}">
		<input id="answer6" type="hidden" value="${versionQuestion.answer6}">
		<input id="answer7" type="hidden" value="${versionQuestion.answer7}">
		<input id="answer8" type="hidden" value="${versionQuestion.answer8}">
		<input id="answer9" type="hidden" value="${versionQuestion.answer9}">
		<input id="count" type="hidden" value="${versionQuestion.count}"/>
		</form>
	<form:form id="inputForm" modelAttribute="versionQuestion" action="${ctx}/examQues/save?knowledgeId=${knowledgeId}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input id="newCount" type="hidden" name="count" value=""/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">知识点：</label>
			 <div class="controls"  style="height: 40px;">
			    <span>
                   <syst:treeselect id="courseKnowledge" name="courseKnowledge.id" value="${ckIds}" labelName="courseKnowledge.title" labelValue="${ckTitles}"
					title="知识点" url="${ctx}/common/courseKnowledgeByVersionTreeData" cssClass="required"  notAllowSelectParent="true" notAllowSelectRoot="true"/>
				   <span class="help-inline"><font color="red">*</font></span>
				</span>
		     </div>
		</div>
		<div class="control-group">
			<label class="control-label">题库：</label>
			<div class="controls"  style="height: 40px;">
			    <span>
				<form:select path="questionlibId" class="input-medium required">
					<form:option value="" label="全部"/>
					<form:options items="${quesLibs}" itemLabel="title" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font></span>
			    </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">题型：</label>
			<div class="controls"  style="height: 40px;">
			    <span>
				<form:select path="quesType" class="input-xlarge required" onchange="selectType();" id="questionType">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('question_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
					<span class="help-inline"><font color="red">*</font></span>
			    </span>
			</div>
		</div>
		<div class="control-group" >
			<label class="control-label">难度：</label>
			<div class="controls"  style="height: 40px;">
			    <span>
				<form:select path="quesLevel" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('question_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				  <span class="help-inline"><font color="red">*</font></span>
			    </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">分值：</label>
			<div class="controls"  style="height: 40px;">
			    <span>
			      <form:input path="quesPoint" htmlEscape="false" maxlength="1000" class="input-xlarge number required"/>
				  <span class="help-inline"><font color="red">*</font></span>
			    </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">题干描述：</label>
			<div id="choiceMessage" class="controls"></div>
			<div class="controls" id="questionTitle"   >
				<%-- <form:input path="title" htmlEscape="false" maxlength="1000" class="input-xlarge "/> --%>
				<form:textarea id="title" htmlEscape="true" path="title" rows="4" maxlength="200" class="input-xxlarge"/>
					<sys:ckeditor replace="title" uploadPath="/upload/questionFile" height="200"/>
			</div>
			<div class="controls" id="choices"></div>
		</div>
		<div class="control-group">
			<label class="control-label">答案：</label>
			<div class="controls" id="questionAnswer">
				<form:textarea id="answer0" htmlEscape="true" path="answer0" rows="4" maxlength="200" class="input-xxlarge"/>
					<sys:ckeditor replace="answer0" uploadPath="/upload/questionFile" height="200"/>
			</div>
			<div id=answerMessage class="controls"></div>
			<div class="controls" id="newAnswer"></div>
		</div>
		<div class="control-group">
			<label class="control-label">讲解：</label>
			<div class="controls" id="questionDescription">
				<form:textarea id="description" htmlEscape="true" path="description" rows="4" maxlength="200" class="input-xxlarge"/>
					<sys:ckeditor replace="description" uploadPath="/upload/questionFile" height="200"/>
			</div>
		</div>
		<%-- <div class="control-group" >
			<label class="control-label">出题人：</label>
			<div class="controls" style="height: 40px;">
				<form:input path="writer" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div> --%>
		
		<div style="height:50px; ">&nbsp</div>
		<div class="form-actions clearfix">
			<div style="float:right;">
				<input style="float:left;margin-right:10px;" id="btnSubmit" class="btn tbtn-primary" type="submit" value="保 存"/>&nbsp;
				<input style="float:left;" id="btnCancel" class="btn tbtn-primary btn-gray" type="button" value="返 回" onclick="history.go(-1)"/>
			</div>
		</div>
		<div style="height:20px; ">&nbsp</div>
	</form:form>
	
</body>
</html>
