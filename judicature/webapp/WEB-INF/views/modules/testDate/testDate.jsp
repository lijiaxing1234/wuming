<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>考试时间管理</title>
		<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
</head>
<body>
	<sys:message content="${message}"/>
	<form:form id="searchForm"  modelAttribute="td"  action="${ctx}/questionlib/course/testDate?type=0" method="post" class="breadcrumb form-search">
	  <form:hidden path="id"  />  
	 
		<ul class="ul-form">
		<li>
			<label>考试时间：</label>
				<input type="text" name="examDate" htmlEscape="false" maxlength="100" class="input-medium" value="${td.examDate}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false})"/>    <input type="submit" value="确定"></li>
		</ul>
		   
		    
	</form:form>
</body>
</html>