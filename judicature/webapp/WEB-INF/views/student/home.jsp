<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="student_default"/>
<style type="text/css">
 .content-right{ overflow: visible;}
</style>
<title>首页</title>
</head>
<body>
   
	<div class="cour-content container clearfix">	
	    <%@include file="/WEB-INF/views/student/include/main_left.jsp" %>
		<div class="content-right">
			 <%@include file="/WEB-INF/views/student/include/header.jsp" %>
		<%-- <sys:message content="${message}"/> --%>
		<input type="hidden" value="${message }" id="pageMsg">
		   <iframe id="studentMainFrame" name="studentMainFrame" src="" style="overflow: hidden;" scrolling="yes" frameborder="no" width="100%" height="776px"></iframe>
		</div>
	</div>
   <%@include file="/WEB-INF/views/student/include/footer.jsp" %>		
</body>
</html>