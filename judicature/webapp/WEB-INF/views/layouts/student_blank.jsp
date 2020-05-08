<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html>
<head>
	<title><sitemesh:title/>-题库</title>
	<%@include file="/WEB-INF/views/student/include/head.jsp" %>
	<style type="text/css">
	  body, label, input, button, select, textarea, .uneditable-input, .navbar-search .search-query,.ztree *{font-family: 'Microsoft Yahei' ! important;}
	  .pagination ul > .controls > a{padding:4px 12px;}
	  .cr-header a{color:#fafafa;}
	  .cr-header button{background:#fcfcfc;border:1px solid #fafafa;color:#4aa7e0;border-radius:3px;}
	  input{width:163px;padding: 4px 0 ! important;}
	  input[type="file"]{ text-indent: 0;}
	  .form-search select{border-radius: 3px;} 
	  .input-mini,
	   .input-small,
	   .input-medium,
	   .input-large,
	   .input-xlarge,
	   .input-xxlarge{width: 170px  ! important;}
	  
	   .pagination ul > .controls > a input{padding: 0 ! important;} 
	    .nav-tabs{font-weight:bold;}
	    button{background:#4aa7e0;border:0 none;color:#fff;}
	    table{background-color:#fff;}
		.table-bordered{border-color:#e5e5e5;}
		.table-striped tbody>tr:nth-child(odd)>td, .table-striped tbody>tr:nth-child(odd)>th{background-color:#fff;}
		.table-bordered th, .table-bordered td{border-color:#e5e5e5;}
		/* .btn-primary{background-color:#3399d7;background-image:linear-gradient(to bottom, #3399d7, #3399d7);}
		.btn-primary:hover,.btn-primary:focus,.btn-primary:active,.btn-primary.active,.btn-primary.disabled,.btn-primary[disabled] {color: #ffffff;background-color: #3399d7;*background-color: #3399d7;}
		.btn-primary:active,.btn-primary.active { background-color: #3399d7 \9;} */
		.btn-primary{background:#1c9993;background-image:-webkit-linear-gradient(top,#1c9993,#1c9993);}
		.btn-primary:hover,.btn-primary:focus,.btn-primary:active,.btn-primary.active,.btn-primary.disabled,.btn-primary[disabled] {color: #ffffff;background-color: #1c9993;*background-color: #1c9993;}
		.btn-primary:active,.btn-primary.active { background-color: #1c9993 \9;}
		.table th{color:#fff;background:#64dfda;background-image:-webkit-linear-gradient(top,#64dfda,#64dfda);}
		select{border-color:#3399d7;}
		.pagination ul > li > a{border-color:#e5e5e5;}
		.pagination ul > .controls > a{border-color:#e5e5e5;}
		.nav-tabs{font-weight:normal;}
		.pagination ul > .disabled > a{color:#666;}
		.pagination{width:auto;margin:20px 0;text-align:center;}
		a{cursor:pointer;}
		a:hover,a:focus{text-decoration:none;}
		.table-bordered td {text-align: left ! important;}
	</style>		
	<sitemesh:head/>
</head>
<body>
	 <sitemesh:body/>
</body>
</html>