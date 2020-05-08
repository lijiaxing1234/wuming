<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
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
		});
	</script>
</head>
<body>

    <ul class="nav nav-tabs">
		<li style="display: none"><a href="${ctx}/school/shoolsources/list?knowledgeId=${edresources.knowledgeId}">教学资源列表</a></li>
		<li class="active"><a href="${ctx}/school/shoolsources/form?knowledgeId=${edresources.knowledgeId}">教学资源查看</a></li>
	</ul><br>
	<form:form id="inputForm" modelAttribute="edresources" action="${ctx}/school/shoolsources/form/Add" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden path="knowledgeId"/>
		
		<sys:message content="${message}"/>
		      <div class="control-group">
		         <label class="control-label">名称:</label>&nbsp;&nbsp;
		            <input type="text" name="resName" value="${edresources.resName}" readonly="readonly" class="required"/>
		          
		     </div>
			<div class="control-group">
				<label class="control-label">知识点:</label> &nbsp;&nbsp;
				 <input type="text" name="resName" value=" ${edresources.courseKnowledge.title}" readonly="readonly" class="required"/>
			 
			</div>
			
			
			 <div class="control-group">
				 &nbsp;&nbsp;
				 	<div class="controls">
						<form:hidden id="resFile" path="resFile" htmlEscape="false" maxlength="255" class="input-xlarge required" />
						<c:if test="${not empty edresources.resFile }">
							<a href="${pageContext.request.contextPath}/commonDownLoadFiles${edresources.resFile }" >${edresources.resName }</a>	
						</c:if>
				   </div>
			</div>
			<div class="form-actions pagination-left"  >
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" value="保存"/> -->
		</div>
	</form:form>
</body>
</html>