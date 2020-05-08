<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>试题管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		function selectType(){
			var questionType = $("#questionType").val();
			alert(questionType);
			$("#questionTitle").empty();
			if(questionType=="1"){
				//1-单选题
				$("#questionTitle").append("<input name=\"title\" type=\"text\" value=\"\"/>");
				$("#questionTitle").append("<br/>选项：");
				$("#questionTitle").append("<br/>插入图片:<input name=\"title\" type=\"file\" value=\"\"/>");
				$("#questionTitle").append("<br/>A:<input name=\"title\" type=\"text\" value=\"\"/>");
				$("#questionTitle").append("<br/>B:<input name=\"title\" type=\"text\" value=\"\"/>");
				$("#questionTitle").append("<br/>C:<input name=\"title\" type=\"text\" value=\"\"/>");
				$("#questionTitle").append("<br/>D:<input name=\"title\" type=\"text\" value=\"\"/>");
			}else if(questionType==2){
				//2-填空题
				$("#questionTitle").append("<input name=\"title\" type=\"text\" value=\"\"/>");
				$("#questionTitle").append("<br/>插入图片:<input name=\"title\" type=\"file\" value=\"\"/>");
			}else if(questionType==3){
				//3-计算题
				$("#questionTitle").append("<input name=\"title\" type=\"text\" value=\"\"/>");
				$("#questionTitle").append("<br/>插入图片:<input name=\"title\" type=\"file\" value=\"\"/>");
				$("#answer").empty();
				$("#answer").append("<input name=\"answer\" type=\"text\" value=\"\"/>");
				$("#answer").append("<br/>插入图片:<input name=\"answer\" type=\"file\" value=\"\"/>");
			}else if(questionType==4){
				//4-简答题
				$("#questionTitle").append("<input name=\"title\" type=\"text\" value=\"\"/>");
				$("#questionTitle").append("<br/>插入图片:<input name=\"title\" type=\"file\" value=\"\"/>");
				$("#answer").empty();
				$("#answer").append("<input name=\"answer\" type=\"text\" value=\"\"/>");
				$("#answer").append("<br/>插入图片:<input name=\"answer\" type=\"file\" value=\"\"/>");
			}else if(questionType==5){
				//5-多选题
				$("#questionTitle").append("<input name=\"title\" type=\"text\" value=\"\"/>");
				$("#questionTitle").append("<br/>选项：");
				$("#questionTitle").append("<br/>插入图片:<input name=\"title\" type=\"file\" value=\"\"/>");
				$("#questionTitle").append("<br/>A:<input name=\"title\" type=\"text\" value=\"\"/>");
				$("#questionTitle").append("<br/>B:<input name=\"title\" type=\"text\" value=\"\"/>");
				$("#questionTitle").append("<br/>C:<input name=\"title\" type=\"text\" value=\"\"/>");
				$("#questionTitle").append("<br/>D:<input name=\"title\" type=\"text\" value=\"\"/>");
			}else{
			}
		}
	</script>
</head>
<body>
	
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/questionlib/versionQuestion/list?extId=${extId}&courseQuestionlibId=${courseQuestionlibId}">试题列表</a></li>
		<li class="active"><a href="${ctx}/questionlib/versionQuestion/form?id=${versionQuestion.id}&extId=${extId}&courseQuestionlibId=${courseQuestionlibId}">试题<shiro:hasPermission name="questionlib:versionQuestion:edit">${not empty versionQuestion.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="questionlib:versionQuestion:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="versionQuestion" action="${ctx}/questionlib/versionQuestion/save?extId=${extId}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input id="operation" type="hidden" name="operation" value="${operation}"/>
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
				<form:select path="quesType" class="input-xlarge " onchange="selectType();" id="questionType">
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
					<form:options items="${fns:getDictList('questionlib_courseKnowledge_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
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
			<div class="controls" id="questionTitle">
				<form:input path="title" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">答案：</label>
			<div class="controls" id="answer">
				<form:input path="answer" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">讲解：</label>
			<div class="controls">
				<form:input path="description" htmlEscape="false" maxlength="1000" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出题人：</label>
			<div class="controls">
				<form:input path="writer" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">审题人：</label>
			<div class="controls">
				<form:input path="checker" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位：</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" value="${versionQuestion.office.id}" labelName="office.name" labelValue="${versionQuestion.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">所属老师：</label>
			<div class="controls">
				<form:input path="tearchId" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div> --%>
		<%-- <div class="control-group">
			<label class="control-label">题库名：</label>
			<div class="controls">
			<select name="questionlibId" id="courseQuestionlibSelect"  class="input-medium"  >
				<c:forEach items="${questionlib:getCourseQuestionlibByVersionId(courseVersionId)}" var="courseQuestionlib">
					   	<option value="${courseQuestionlib.id}" ${courseQuestionlibId==courseQuestionlib.id?"selected":""}  >${courseQuestionlib.title} </option>
				</c:forEach>
			</select>
			</div>
		</div> --%>
		<div class="form-actions">
			<shiro:hasPermission name="questionlib:versionQuestion:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>.
		</div>
		<textarea rows="10" cols="30" name="title" value="sad"></textarea>
	</form:form>
</body>
</html>