<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html style="overflow-x:auto;overflow-y:auto;">
<head>
	<title><sitemesh:title/></title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<style type="text/css">
	    body, label, input, button, select, textarea, .uneditable-input, .navbar-search .search-query,.accordion-heading .accordion-toggle,.navbar .nav>li>a,.ztree *{font-family: 'Microsoft Yahei' ! important;}
	   .nav-tabs{font-weight:bold;}
	    input{width:150px;padding: 4px 0 ! important; text-indent: 10px; }
	    input[type="file"]{ text-indent: 0;}
	   .select2-container {width: 163px ! important;}
	   .input-mini,
	   .input-small,
	   .input-medium,
	   .input-large,
	   .input-xlarge,
	   .input-xxlarge{width: 170px  ! important;} 
	   .btn-primary{text-indent: 0;} 
	   .form-search .ul-form li{margin-bottom: 5px;}
	   .form-search .ul-form li.btns{padding-left: 5px;}
	   .btn{text-indent: 0;} 
	</style>
	<sitemesh:head/>
</head>
<body>
	<sitemesh:body/>
</body>
</html>