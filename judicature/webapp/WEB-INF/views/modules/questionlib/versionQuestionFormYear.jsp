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
			if($("${isYear} == 1")){
				$("#d1").hidden();
				$("#d2").hidden();
				$("#d3").hidden();
				$("#courseIdSelect").attr("disabled",true);
				$("#regionSelect").attr("disabled",true);
				$("#townSelect").attr("disabled",true);
			}
			$("#inputForm").validate({
				rules:{
					quesPoint:{range:[0,30]}
				},
			
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
			  
			if(questionType=="1" || questionType==2 || questionType==3){
				//1-单选题/2-多选题 3不定项
				i = count;
				$("#choices").append("<br class=\"addSelect\"/><input type=\"button\" value=\"添加选项&nbsp; \" onclick=\"addSelect();\" class=\"addSelect\" style=\"width:70px;text-align:center;text-indent:0;margin-right:10px;\"/>");
				$("#choices").append("<input type=\"button\" value=\"删除选项 &nbsp;\" onclick=\"deleteSelect();\" class=\"addSelect\" style=\"margin-left: 10px;width:70px;text-align:center;text-indent:0;\" />");
				$("#choices").append("<br class=\"addSelect\"/><font class=\"addSelect\">选项：</font>");
				for (var j = 0; j < i; j++) {
					addSelect(j);
				}
			}else if(questionType>3){
				//6-论述   （填空） 
				i = count;
				$("#newAnswer").append("<input type=\"button\" value=\"添加答案  &nbsp;\" onclick=\"addAnswer();\" class=\"addAnswer\"/>");
				$("#newAnswer").append("<input type=\"button\" value=\"删除答案  &nbsp;\" onclick=\"deleteAnswer();\" class=\"addAnswer\" style=\"margin-left: 10px;\"/>");
				if(i>1){
					for (var j = 1; j < i; j++) {
						var answer = $("#answer"+j).val();
						$("#newAnswer").append("<br class=\"addAnswer\"/><textarea name=\"answer"+j+"\"  rows='4'  class='textArray required'  > "+answer+"</textarea> ");
					}
				}
			}else if(questionType>=4&&questionType!=6){
				//4-简答  / 6-概念题  / 7-综合题  / 8-作图题  / 9-制表题  / 10-识图题   

			}else if(questionType==100){
				// 100-判断题
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
				alertx("选项至多为6个!");
				return;
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
			$("#choices").append("<br class=\"addSelect choice"+k+"\"/><p class=\"addSelect choice"+k+"\" style=\"position:relative;top:5px;display:inline-block;\">"+select+"：</p>  <textarea name=\"choice"+k+"\"  rows='4'   class='textArray required'>"+choice+"</textarea> ");
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
				alert("填空题至多有10个空!");
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
		
		function s1(){
			var a = $("#courseIdSelect").val();
			$.ajax({
				contentType:"application/x-www-form-urlencoded;charset=UTF-8", 
				type : "POST", //请求方式
				url : "${ctx}/questionlib/versionQuestion/getChapters?id="+a, //请求路径
				cache : false, //(默认: true,dataType为script和jsonp时默认为false) jQuery 1.2 新功能，设置为 false 将不缓存此页面。
				async : false,
				dataType : "json",
				success : function(regions) {
					 if (regions != null && regions.length > 0) {  
			                var str = "<option value=''> " + "请选择" + "</option>;";  
			                $.each(regions, function(i, item) {
			                    if (item != "") {  
			                        str = str + "<option value="+item.id+"> " + item.title + "</option>;";  
			                        $("#regionSelect").html(str);  
			                    } else {  
			                        $("#regionSelect").html("<option id = oper value=''>请选择</option>");  
			                    }  
			                });  
			            } else {  
			                $("#regionSelect").html("<option id = oper>请选择</option>");  
			            }  
			            $("#townSelect").html("<option value=''>请选择</option>");  
				}
			});
			$("#regionSelect").attr("disabled",false);
			$("#d2").show(1000);
		}
		function s2(){
			var a = $("#regionSelect").val();
			$.ajax({
				contentType:"application/x-www-form-urlencoded;charset=UTF-8", 
				type : "POST", //请求方式
				url : "${ctx}/questionlib/versionQuestion/getNodes?parentId="+a, //请求路径
				cache : false, //(默认: true,dataType为script和jsonp时默认为false) jQuery 1.2 新功能，设置为 false 将不缓存此页面。
				async : false,
				dataType : "json",
				success : function(towns) {
					 if (towns != null && towns.length > 0) {  
			                var str = "<option value=''> " + "请选择" + "</option>;";  
			                $.each(towns, function(i, item) {
			                    if (item != "") {  
			                        str = str + "<option value="+item.id+"> " + item.title + "</option>;";  
			                        $("#townSelect").html(str);  
			                    } else {  
			                        $("#townSelect").html("<option id = oper value=''>请选择</option>");  
			                    }  
			                });  
			            } else {  
			                //$("#townSelect").html("<option id = oper>请选择</option>");
			                $("#d3").hidden(1000);
							$("#townSelect").attr("disabled",true);
			            }  
			            //$("#townSelect").html("<option value=''>请选择</option>");  
				}
			});
			$("#townSelect").attr("disabled",false);
			//$("#d3").show(1000);
		}
		
	</script>
</head>
<body>
	
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/questionlib/versionQuestion/list?extId=${extId}&courseQuestionlibId=${courseQuestionlibId}">试题列表</a></li>
		<li class="active"><a href="${ctx}/questionlib/versionQuestion/form?id=${versionQuestion.id}&extId=${extId}&courseQuestionlibId=${courseQuestionlibId}">试题<shiro:hasPermission name="questionlib:versionQuestion:edit">${not empty versionQuestion.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="questionlib:versionQuestion:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	 	<form>
		<input id="quesType" type="hidden" value="${versionQuestion.quesType}" >
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
	<form:form id="inputForm" modelAttribute="versionQuestion" action="${ctx}/questionlib/versionQuestion/save?extId=${extId}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input id="newCount" type="hidden" name="count" value=""/>
		 
		<form:hidden path="versionId"/>
		<sys:message content="${message}"/>		
		
		
		<div class="control-group">
			<label class="control-label">知识点：</label>
				<div class="controls"  style="height: 40px;">
                               <sys:treeselect id="courseKnowledge" name="courseKnowledge.id" value="${ckIds}" labelName="courseKnowledge.title" labelValue="${ckTitles}"
					title="知识点" url="/questionlib/versionQuestion/treeData?courseId=${courseId}" cssClass="required" checked="true" notAllowSelectParent="true" notAllowSelectRoot="true"/>
					<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group">
		<div id="d1">
		 <label class="control-label">课程:</label>
		<form:select path="courseId" cssClass="input-medium" onchange="s1()" id="courseIdSelect">
			 <option value="0">请选择</option>
			   <form:options items="${Course}" htmlEscape="false" itemLabel="title" itemValue="id"/>
		</form:select>
		
		<div id="d2">
		<label class="control-label">章:</label>
		<form:select path="chapterId" id="regionSelect" onchange="s2()">
		<option value="${versionQuestion.chapterId}">${versionQuestion.chapterName}</option>
		</form:select>
		</div>
		
		<div id="d3">
		<label class="control-label">节:</label>
		<form:select path="nodeId" id="townSelect">
		<option value="${versionQuestion.nodeId}">${versionQuestion.nodeName}</option>
		</form:select>
		</div>
		</div>
	</div>
		
		<div class="control-group">
			<label class="control-label">题型：</label>
			<div class="controls"  style="height: 40px;">
				<form:select path="quesType" class="input-xlarge required" onchange="selectType();" id="questionType">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('question_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		
		<%-- <div class="control-group" >
			<label class="control-label">难度：</label>
			<div class="controls "  style="height: 40px;">
				<form:select path="quesLevel" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('question_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">分值：</label>
			<div class="controls required"  style="height: 40px;">
 
			<form:input path="quesPoint" htmlEscape="false" maxlength="1000" class="input-xlarge number required"/>
			<span class="help-inline"><font color="red">*</font></span>
 
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">材料：</label>
			<div id="choiceMessage" class="controls"></div>
			<div class="controls  required" id="questionTitle"   >
				<%-- <form:input path="title" htmlEscape="false" maxlength="1000" class="input-xlarge "/> --%>
				<form:textarea   htmlEscape="false" path="material" rows="4" maxlength="500"  class="textArray "  cols="400"  />
					<%-- <sys:ckeditor replace="title" uploadPath="/upload/questionFile" height="200"/> --%>
			</div>
			 
		</div>
		
		<div class="control-group">
			<label class="control-label">试题描述：</label>
			<div id="choiceMessage" class="controls"></div>
			<div class="controls  required" id="questionTitle"   >
				<%-- <form:input path="title" htmlEscape="false" maxlength="1000" class="input-xlarge "/> --%>
				<form:textarea id="title" htmlEscape="false" path="title" rows="4" maxlength="1000" class="textArray required"/>
					<%-- <sys:ckeditor replace="title" uploadPath="/upload/questionFile" height="200"/> --%>
					<span class="help-inline"><font color="red">*</font></span>
			</div>
			
			<div class="controls" id="choices"></div>
		</div>
		<div class="control-group">
			<label class="control-label ">答案：</label>
			<div class="controls required"  id="questionAnswer">
				<%-- ${versionQuestion.answer0} --%>
				<%-- <form:input path="answer" htmlEscape="false" maxlength="1000" class="input-xlarge "/> --%>
				<form:textarea id="answer0" htmlEscape="false" path="answer0" rows="4"  class="textArray required"/>
				<%-- 	<sys:ckeditor replace="answer0" uploadPath="/upload/questionFile" height="200"/> --%>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
			<div id=answerMessage class="controls"></div>
			<div class="controls" id="newAnswer"></div>
		</div>
		<div class="control-group">
			<label class="control-label">讲解：</label>
			<div class="controls required" id="questionDescription">
				<%-- <form:input path="description" htmlEscape="false" maxlength="1000" class="input-xlarge "/> --%>
				<form:textarea id="description" htmlEscape="false" path="description"   rows="4"  class="textArray required"/>
					<%-- <sys:ckeditor replace="description" uploadPath="/upload/questionFile" height="200"/> --%>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">排序:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="50"  class="required digits input-small"/>
				<span class="help-inline">排列顺序，升序。</span>
			</div>
		</div>
		<div class="control-group" >
			<label class="control-label">出题人：</label>
			<div class="controls " style="height: 40px;">
				<form:input path="writer" htmlEscape="false" maxlength="32" class="input-xlarge  "/>
				<!-- <span class="help-inline"><font color="red">*</font> </span> -->
			</div>
		</div>
		
		<div style="height:50px; ">&nbsp</div>
		
		 
			<h2 style="color: red; text-align: center;"> 新建或重新编辑试题后需要审核后试题才能使用！ </h2>
				 
		 
		<div class="form-actions">
			<shiro:hasPermission name="questionlib:versionQuestion:edit">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		<div style="height:200px; ">&nbsp</div>
	</form:form>
</body>
</html>