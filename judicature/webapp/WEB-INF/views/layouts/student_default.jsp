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
		
		
		.content-right{height:825px;}
		.content-right-con{padding: 0 20px;}
		.content-right-con .jsyy{border:1px solid #e6e6e6;height: 115px;margin: 20px auto;}
		.content-right-con .jsyy .jsyy-list{margin-top: 25px;}
		.jsyy .jsyy-list li{float: left;margin-left:10%;}
		.content-right-con .jsyy .jsyy-list li:first-child{margin-left:3%;}
		.content-right-con .jsyy .jsyy-list li a{float: left;margin-right: 15px;}
		.content-right-con .jsyy .jsyy-list li .list-text{float: left;}
		.content-right-con .jsyy .jsyy-list .list-text h2{display: inline-block;font-size: 20px;}
		.content-right-con .jsyy .list-text h2 a{color: #249994;}
		.content-right-con .jsyy .jsyy-list .list-text p{margin-top: 0;}
		
		.content-stu-sy {margin-bottom: 20px;}
		.content-stu-sy .con-stu-left{float: left;width: 73%;height: 277px;border:1px solid #eaeaea;}
		.content-stu-sy .con-stu-left img{width: 100%;height: 100%;}
		.content-stu-sy .con-stu-right{float: right;width: 23%;height: 277px;border:1px solid #eaeaea;}
		.content-stu-sy .con-stu-right img{width: 100%;height: 100%;}
		
		.content-stu .con-stu-left{float: left;width: 25%;height: 254px;border:1px solid #eaeaea;}
		.content-stu .con-stu-left img{width: 100%;height: 100%;}
		.content-stu .con-stu-middle{float: left;height: 254px;width: 45%;border:1px solid #eaeaea;margin-left: 3%;}
		.content-stu .con-stu-middle h2{line-height: 65px;font-size: 16px;padding: 0 90px;}
		.content-stu .con-stu-middle h2 i{float: right;}
		.content-stu .con-stu-middle img{width: 75%;margin: 0 auto;}
		.content-stu .con-stu-right{float: right;width: 23%;height: 254px;border:1px solid #eaeaea;}
		.content-stu .con-stu-right img{width: 100%;height: 100%;}
		.content-list .content-menu,.content-left .content-list{height:736px ! important;}
		.content-left .content-menu li i{background-size:200%;width:27px;height:23px;background-position:0 0;}
		.content-left .content-menu li:nth-child(2) i{height:27px;background-position:0 -23px;}
		.content-left .content-menu li:nth-child(3) i{height:23px;background-position:0 -48px;}
		.content-left .content-menu li:nth-child(4) i{height:18px;background-position:0 -69px;}
		.content-left .content-menu li:nth-child(5) i{height:20px;background-position:0 -85px;}
		.content-left .content-menu li:nth-child(6) i{height:27px;background-position:0 -104px;}
		.content-left .content-menu li:nth-child(7) i{height:25px;background-position:0 -129px;}
		.content-left .content-menu li:nth-child(8) i{height:24px;background-position:0 -150px;}
		.content-left .content-menu li:nth-child(9) i{height:23px;background-position:0 -172px;}
		.content-left .content-menu li:nth-child(10) i{height:21px;background-position:0 -193px;}
		.content-left .content-menu li:nth-child(11) i{background-position:0 -212px;}
		.content-left .content-menu li:nth-child(12) i{height:22px;background-position:0 -236px;}
		
		.content-left .content-menu .active i{background-position:-27px 0;}
		.content-left .content-menu li:nth-child(2).active i{background-position:-27px -23px;}
		.content-left .content-menu li:nth-child(3).active i{background-position:-27px -48px;}
		.content-left .content-menu li:nth-child(4).active i{background-position:-27px -69px;}
		.content-left .content-menu li:nth-child(5).active i{background-position:-27px -85px;}
		.content-left .content-menu li:nth-child(6).active i{background-position:-27px -104px;}
		.content-left .content-menu li:nth-child(7).active i{background-position:-27px -129px;}
		.content-left .content-menu li:nth-child(8).active i{background-position:-27px -150px;}
		.content-left .content-menu li:nth-child(9).active i{background-position:-27px -172px;}
		.content-left .content-menu li:nth-child(10).active i{background-position:-27px -193px;}
		.content-left .content-menu li:nth-child(11).active i{background-position:-27px -212px;}
		.content-left .content-menu li:nth-child(12).active i{background-position:-27px -236px;}
		@media screen /* and (min-width:1590px) */ and (max-width:1799px) {
		 	.jsyy .jsyy-list li{margin-left: 9%;}
		} 
		@media screen and (max-width:960px) {
			.jsyy .jsyy-list li{margin-left: 3%;}
		} 
		@media screen and (max-width:1048px) {
			.jsyy .jsyy-list li{margin-left: 5.5%;}
		} 
		@media screen  and (max-width:1149px) {
			.jsyy .jsyy-list li{margin-left: 8%;}
		}
		@media screen  and (max-width:1119px) {
			.jsyy .jsyy-list li{margin-left: 2%;}
		}
		/* @media screen and (min-width:1249px) {
			 .jsyy .jsyy-list li{margin-left: 7%;}
		}  */
		@media screen and (max-width: 1100px) and (min-width: 1025px){
		html{}
		}
	</style>	
	<sitemesh:head/>
</head>
<body>
     <%--
	<%@include file="/WEB-INF/views/student/include/header.jsp" %>
	<div class="cour-content container clearfix">	
		<%@include file="/WEB-INF/views/student/include/main_left.jsp" %>
		<div class="content-right"> --%>
		
		<sitemesh:body/>
		
	     <%--	</div>
	</div>
	<%@include file="/WEB-INF/views/student/include/footer.jsp" %>		 --%>
</body>
</html>