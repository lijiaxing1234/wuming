<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html>
<head>
	<title><sitemesh:title/>-题库</title>
	<%@include file="/WEB-INF/views/teacher/include/head.jsp" %>	
	<style type="text/css">
	  input{width:163px;padding: 4px 0 ! important;border-radius: 3px; height: auto ! important;}
	  input[type="file"]{ text-indent: 0;}
	  .form-search select{border-radius: 3px;}
	  .pagination ul > .controls > a{padding: 4px 12px;}
	  .select2-container {width: 163px ! important;}
	  body, label, input, button, select, textarea, .uneditable-input, .navbar-search .search-query{font-family: 'Microsoft Yahei' ! important;}
	   .input-mini,
	   .input-small,
	   .input-medium,
	   .input-large,
	   .input-xlarge,
	   .input-xxlarge{width: 170px  ! important;} 
	   .btn-primary{ text-indent: 0;}
	   .pagination ul > .controls > a input{padding: 0 ! important;}
	   .form-search select{width:160px;}
	   /* .nav-tabs{font-weight:bold;} */
	   
	   
	   
	   /************************内容*******************************/
		a:hover, a:focus{text-decoration:none;}
		.main-content .main-left{background: #f9f9f9;width: 257px;float: left;overflow-y:auto;overflow-x:hidden;}
		.main-content .main-left .main-left-con{height: 737px;}
		.main-left-con .navlist{border: 1px solid #e5e5e5;border-bottom: 0 none;}
		.main-left-con .navlist > li{width: 256px;border-bottom: 1px solid #e5e5e5;cursor: pointer;font-size: 20px;}
		.main-left-con .navlist > li:last-child{border-bottom: 0 none;}
		.main-left-con .navlist > li i{margin-right: 15px;margin-left: 30px;width: 27px;height: 23px;display: inline-block;background: url(${ctxStatic}/modules/teacher/images/sprite.png) no-repeat 1px 0;background-size:200%;position: relative;top: 5px;}
		/* .main-left-con .navlist > li:nth-child(2) i{background-position: -5px -41px;;}
		.main-left-con .navlist > li:nth-child(3) i{height: 27px;background-position: -5px -74px;}
		.main-left-con .navlist > li:nth-child(4) i{height: 20px;background-position: -5px -105px;}
		.main-left-con .navlist > li:nth-child(5) i{height: 22px;background-position: -5px -131px;}
		.main-left-con .navlist > li:nth-child(6) i{height: 31px;background-position: -5px -157px;}
		.main-left-con .navlist > li:nth-child(7) i{height: 25px;background-position: -5px -194px;}
		.main-left-con .navlist > li:nth-child(8) i{height: 24px;background-position: -5px -230px;}
		.main-left-con .navlist > li:nth-child(9) i{height: 25px;background-position: -5px -260px;}
		.main-left-con .navlist > li:nth-child(10) i{height: 25px;background-position: -5px -289px;}
		.main-left-con .navlist > li:nth-child(11) i{background-position: -5px -318px;}
		.main-left-con .navlist > li:nth-child(12) i{background-position: -5px -351px;}
		.main-left-con .navlist > li:nth-child(13) i{height: 25px;background-position: -5px -385px;}
		.main-left-con .navlist > li:nth-child(14) i{background-position: -5px -413px;} */
		.main-left-con .navlist > li a{display: inline-block;width: 100%;height: 69px;line-height: 69px;color:#666;}
		.main-left-con .navlist > li em{width: 14px;height: 7px;display: inline-block;background: url(${ctxStatic}/img/click.png) no-repeat -1px -1px;float: right;margin: 32px 30px 0 0;}
		.main-left-con .navlist > li.active{background: #82dcf5;}
		/* .main-left-con .navlist > li.active i{background-position: -25px -2px;}
		.main-left-con .navlist > li:nth-child(2).active i{background-position: -43px -41px;}
		.main-left-con .navlist > li:nth-child(3).active i{background-position: -43px -74px;}
		.main-left-con .navlist > li:nth-child(4).active i{background-position: -43px -105px;}
		.main-left-con .navlist > li:nth-child(5).active i{background-position: -43px -131px;}
		.main-left-con .navlist > li:nth-child(6).active i{background-position: -43px -157px;}
		.main-left-con .navlist > li:nth-child(7).active i{background-position: -43px -194px;}
		.main-left-con .navlist > li:nth-child(8).active i{background-position: -43px -230px;}
		.main-left-con .navlist > li:nth-child(9).active i{background-position: -43px -260px;}
		.main-left-con .navlist > li:nth-child(10).active i{background-position: -43px -289px;}
		.main-left-con .navlist > li:nth-child(11).active i{background-position: -43px -318px;}
		.main-left-con .navlist > li:nth-child(12).active i{background-position: -43px -351px;}
		.main-left-con .navlist > li:nth-child(13).active i{background-position: -43px -385px;}
		.main-left-con .navlist > li:nth-child(14).active i{background-position: -43px -413px;} */
		.main-left-con .navlist > li.act i{background-position:-27px 0px;}
		.main-left-con .navlist > li:nth-child(2) i{background-position:1px -27px;}
		.main-left-con .navlist > li:nth-child(3) i{background-position:1px -48px;height:23px;}
		.main-left-con .navlist > li:nth-child(4) i{background-position:1px -69px;height:20px;}
		.main-left-con .navlist > li:nth-child(5) i{background-position:1px -87px;height:18px;}
		.main-left-con .navlist > li:nth-child(6) i{background-position:1px -104px;height:24px;}
		.main-left-con .navlist > li:nth-child(7) i{background-position:1px -129px;height:25px;}
		.main-left-con .navlist > li:nth-child(8) i{background-position:1px -150px;height:24px;}
		.main-left-con .navlist > li:nth-child(9) i{background-position:1px -172px;height:23px;}
		.main-left-con .navlist > li:nth-child(10) i{background-position:1px -193px;height:20px;}
		.main-left-con .navlist > li:nth-child(11) i{background-position:1px -212px;}
		.main-left-con .navlist > li:nth-child(12) i{background-position:1px -235px;}
		.main-left-con .navlist > li:nth-child(2).active i{background-position:-25px -27px;}
		.main-left-con .navlist > li:nth-child(3).active i{background-position:-25px -48px;}
		.main-left-con .navlist > li:nth-child(4).active i{background-position:-25px -69px;}
		.main-left-con .navlist > li:nth-child(5).active i{background-position:-25px -87px;}
		.main-left-con .navlist > li:nth-child(6).active i{background-position:-25px -104px;}
		.main-left-con .navlist > li:nth-child(7).active i{background-position:-25px -129px;}
		.main-left-con .navlist > li:nth-child(8).active i{background-position:-25px -150px;}
		.main-left-con .navlist > li:nth-child(9).active i{background-position:-25px -172px;}
		.main-left-con .navlist > li:nth-child(10).active i{background-position:-25px -193px;}
		.main-left-con .navlist > li:nth-child(11).active i{background-position:-25px -212px;}
		.main-left-con .navlist > li:nth-child(12).active i{background-position:-25px -235px;}
		.main-left-con .navlist > li.active > a{color: #fff;}
		.main-left-con .navlist > li.act{background: #3399d7;}
		.main-left-con .navlist > li.act > a{color: #fff;}
		.main-left-con .navlist > li ul{display: none;}
		.main-left-con .navlist > li ul li{background: #fff;border-top: 1px solid #e5e5e5;}
		.main-left-con .navlist > li ul li i{width: 4px;height: 4px ! important;display: inline-block;background: #999;border-radius: 100%;position: relative;top: -4px;margin-left: 65px;}
		.main-left-con .navlist > li ul li a{color: #333;}
		.main-left-con .navlist > li ul li.current{background: #f4fdff;}
		.main-left-con .navlist > li ul li.current i{background: #3399d7;}
		.main-left-con .navlist > li ul li.current a{color: #3399d7;}
	   	.main-content .main-left{overflow: hidden;}
		.main-content{height: 825px;}
		.main-left .img-logo > img{margin: 20px auto;}
		.main-content .main-left .main-left-con{overflow-x:hidden;overflow-y: scroll;width: 107%;}
		.main-left-con .navlist{border: 0 none;}
		.main-content .main-left{background: #242833;}
		.main-left-con .navlist > li a,
		.main-left-con .navlist > li.act > a,
		.main-left-con .navlist > li.active > a,
		.main-left-con .navlist > li ul li a{color: #a6a5ab;}
		.main-left-con .navlist > li.act,
		.main-left-con .navlist > li.active{background: #20242d}
		.main-left-con .navlist > li.active > a{border-left:6px solid #f3bb4a;}
		.main-left-con .navlist > li ul li{background: #20242d;border-top: 0 none;}
		.main-left-con .navlist > li ul li.current{background: #1c1f26;}
		.main-left-con .navlist > li ul li.current i{background: #989c9f;}
		.main-left-con .navlist > li ul li.current a{color: #fff;}
		.main-left-con .navlist > li{border-bottom: 0 none;width: auto;}
		.main-left-con .navlist > li em{width: 7px;height: 14px;margin-top: 30px;}
		.main-left-con .navlist > li.active em{background: url(${ctxStatic}/img/b_up.png) no-repeat;width: 21px;height: 21px;}
		.main-content .main-left .main-left-con{border:0 none;}
	   
	   /************************右边内容*******************************/
		.main-content .container{background: #fff;}
		.main-content .content-right{/* width: 920px; */float: left;background: #fff;height: 815px;padding: 10px 17px 0;overflow-y:hidden;}
		/* .main-content .content-right > div{height:100%;width:100%;margin:0 auto;clear:both;} */
		/* .main-right .notice{width: 100%;background: #fffcf9;line-height: 30px;color: #333;border:1px solid #fda72e;border-radius: 3px;padding-bottom: 5px;}
		.main-right .notice p i{width: 27px;height: 27px;display: inline-block;background: url(../images/notice.png) no-repeat;margin: 0 10px 0 20px;position: relative;top: 7px;}
		.main-right .notice p a{color: #ff6f44;font-size: 14px;}
		.main-right .jsyy h2{font-size: 18px;color: #333;line-height: 55px;}
		.main-right .jsyy .jsyy-list{width: 100%;padding: 30px 0;border:1px solid #e5e5e5;}
		.jsyy .jsyy-list li{float: left;text-align: center;margin-left: 10%;}
		.jsyy .jsyy-list li p{margin-top: 20px;}
		.jsyy .jsyy-list li p a{color: #333;}
		.jsyy .jsyy-list li:first-child {margin-left:3%;}
		.main-right .xxts h2{font-size: 18px;color: #333;line-height: 55px;}
		.xxts-list{width: 100%;padding: 25px 0;border:1px solid #e5e5e5;}
		.xxts-list p{line-height: 50px;}
		.xxts-list p i{width: 19px;height: 19px;display: inline-block;background: url(../images/notice_1.png) no-repeat;position: relative;top: 4px;margin: 0 15px 0 30px;} */
		
		@media screen and (min-width:1800px) and (max-width:1920px) {
			.container{width:80%;}
			.main-content .content-right{width: 70%;}
	} 

		@media screen and (min-width:1201px) and (max-width:1249px) {
			.container{width: 97%;}
			/*.main-content .content-right{width: 72%;} */
			 .jsyy .jsyy-list li{margin-left: 9%;}
		} 
		@media screen and (max-width: 1200px) {
			/* html{min-width: 945px;} */
			.container{width: 99%;}
			/* .main-content .content-right{width: 73%;} */
			.main-right .notice p i{margin: 0 5px;}
		}
		@media screen and (min-width:1151px) and (max-width:1119px) {
			/* .main-content .content-right{width: 74%;} */
			.jsyy .jsyy-list li{margin-left: 10%;}
		}
		@media screen and (min-width:1050px) and (max-width:1149px) {
			/* .main-content .content-right{width: 70%;} */
			.jsyy .jsyy-list li{margin-left: 8%;}
		}
		@media screen and (max-width:1048px) {
			/*.main-content .content-right{width: 68%;} */
			.jsyy .jsyy-list li{margin-left: 5.5%;}
		}   
		/********************列表页表格样式***********/
		table{background-color:#fff;}
		.table-bordered{border-color:#e5e5e5;}
		.table-striped tbody>tr:nth-child(odd)>td, .table-striped tbody>tr:nth-child(odd)>th{background-color:#fff;}
		.table-bordered th, .table-bordered td{border-color:#e5e5e5;}
		.table th{color:#fff;background:#64dfda;background-image:-webkit-linear-gradient(top,#64dfda,#64dfda);}
		/* .btn-primary{background-color:#3399d7;background-image:linear-gradient(to bottom, #3399d7, #3399d7);}
		.btn-primary:hover,.btn-primary:focus,.btn-primary:active,.btn-primary.active,.btn-primary.disabled,.btn-primary[disabled] {color: #ffffff;background-color: #3399d7;*background-color: #3399d7;}
		.btn-primary:active,.btn-primary.active { background-color: #3399d7 \9;} */
		.btn-primary{background:#1c9993;background-image:-webkit-linear-gradient(top,#1c9993,#1c9993);}
		.btn-primary:hover,.btn-primary:focus,.btn-primary:active,.btn-primary.active,.btn-primary.disabled,.btn-primary[disabled] {color: #ffffff;background-color: #1c9993;*background-color: #1c9993;}
		.btn-primary:active,.btn-primary.active { background-color: #1c9993 \9;}
		select{border-color:#3399d7;}
		.pagination ul > li > a{border-color:#e5e5e5;}
		.pagination ul > .controls > a{border-color:#e5e5e5;}
		.nav-tabs{font-weight:normal;}
		.pagination ul > .disabled > a{color:#666;}
		.pagination{width:auto;margin:20px 0;text-align:center;}
		ul{list-style:none;}
		.table-bordered td {text-align: left ! important;}
	</style>
	<sitemesh:head/>
</head>
<body>
	 <sitemesh:body/>
</body>
</html>