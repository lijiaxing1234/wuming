<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/teacher/include/taglib.jsp"%>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	<title>题库-老师登录</title>
	<link rel="shortcut icon" href="${ctxStatic}/index/favicon.ico">
	<%@include file="/WEB-INF/views/teacher/include/head.jsp" %>	
	<link rel="stylesheet" href="${ctxStatic}/index/css/main.css">
	<%-- <script type="text/javascript" src="${ctxStatic}/index/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/index/js/main.js"></script> --%>
	<script type="text/javascript">
	if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
		alertx('未登录或登录超时。请重新登录，谢谢！');
		top.location = "${ctx}";
	}
	function login(){
		document.getElementById("inputForm").submit()
	}
	$(document).keydown(function(event){ 
		if(event.keyCode==13){ 
			login();
		} 
	 }); 
	
	function showps(){   
		 
	    if (document.getElementById("password").type=="password") {  
	        document.getElementById("pdiv").innerHTML="<label for='password'>密码</label> <input type=\"text\" name=\"password\"   style='padding: 0px;'   id=\"password\"  value="+ document.getElementById("password").value+"> <a href='javascript:hideps()'>隐藏密码</a> ";   
	    }  
	}   
	function hideps(){   
	 
	    if (document.getElementById("password").type=="text") {  
	        document.getElementById("pdiv").innerHTML="<label for='password'>密码</label> <input type=\"password\" style='padding: 0px;' name=\"password\" id=\"password\" value="+ document.getElementById("password").value+">  <a href='javascript:showps()'>显示密码</a>";   
	    }   
	}
	
	 function GetPass()
	 {
		 var username =$("#loginName").val();
		 if(username=="")
			 {
			   alertx("请输入登录名!");
			   return 
			 }
		 
		 var src ="iframe:/pass/form?type=3&loginName="+username;
		 var title ="找回密码";
		 top.$.jBox(src,{
				title:title,
				width:650,
			    height: 350,
				buttons:{}, 
			    bottomText:"",
			    loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
				},
			    closed:function(){
					$("#searchForm").submit();
					//window.location.href="${ctx}/questionlib/courseKnowledge/list";
				}
	  }); 
	 }
	</script>
<style>
/************************footer*******************************/
.s-footer{height: 130px;padding-top: 30px;border-top: 1px solid #e0e0e0;}
.s-footer p{text-align: center;line-height: 30px;}
</style>
</head>
<body>
	<div class="re-header container clearfix">
		<h2>我是logo</h2>
		<p>题库综合考评系统</p>
	</div>
	<div class="re-main teacher-login">
		<form:form id="inputForm" modelAttribute="user" action="login" method="post" class="form-horizontal">
		<div class="re-mcon container">
			<div class="reg-con">
				<div id="messageBox" class="alert alert-error ${empty message ? 'hide' : ''}">
					<button data-dismiss="alert" class="close">×</button>
					<label id="loginError" class="error">${message}</label>
				</div>
				<div class="reg-list">
					<label for="">用户名</label>
					<input id="loginName" name="loginName">
				</div>
					<div class="reg-list" id="pdiv">
					<label for="password">密码</label>
				    <input type="password" id="password" name="password" style='padding: 0px;' >   <a href="javascript:showps()">显示密码</a>
				</div>
				<div class="re-login clearfix">
					<p class="auto-login" style="display: none"><i></i>下次自动登录</p>
					<p class="forget"><a href="javascript:GetPass();">忘记密码？</a></p>
				</div>
			<div class="click-login " onclick="login();" style="cursor: pointer;" > <span  style="font-size: larger;color:#fff;" >登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录 </span> </div>
				<input id="btnSubmit" style="display:none"  type="submit" value=""/>
			</div>
		</div>
	</form:form>
		
	</div>
	<div class="s-footer">
		<p>©题库     地址：北京市东城区青年湖南街13号     邮编：100011 </p>
		<p>京ICP备12046843号-12</p>
		<p>京公网安备 11010102002668号</p>
	</div>
</body>
</html>

