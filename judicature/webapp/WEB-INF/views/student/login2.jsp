<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/student/include/taglib.jsp"%>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv='X-UA-Compatible' content='IE=edge'>
	
	<title>题库-学生登录</title>
	<link rel="shortcut icon" href="${ctxStatic}/index/favicon.ico">
	<script type="text/javascript" src="${ctxStatic }/modules/Student/login/js/jquery-1.7.2.min.js"></script>
	
	

	<link href="${ctxStatic}/modules/bootstrap/common.css" rel="stylesheet" />
	<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
	<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/js/jquery.alerts.js" type="text/javascript"></script>
	<link href="${ctxStatic}/js/jquery.alerts.css" rel="stylesheet" type="text/css" media="screen" />
	<script src="${ctxStatic}/modules/Student/js/main.js" type="text/javascript"></script>
	
	
	<link rel='stylesheet' type='text/css' charset='utf-8'  href='${ctxStatic }/modules/Student/login/css/jquery-impromptu.css' />
	<link rel='stylesheet' type='text/css' charset='utf-8'  href='${ctxStatic }/modules/Student/login/css/demo.css' />
	
	<script type="text/javascript" src="${ctxStatic }/modules/Student/login/js/common.js"></script>
	
    <script  type='text/javascript' src='${ctxStatic }/modules/Student/login/js/template.js' charset='utf-8'></script>
    <script  type='text/javascript' src='${ctxStatic }/modules/Student/login/js/jquery.flexslider.js' charset='utf-8'></script>
	<!--[if IE 6]>
	  <script  type='text/javascript' src='${ctxStatic }/modules/Student/login/js/DD_belatedPNG.js' charset='utf-8'></script> 
	  <script type="text/javascript">$(function(){ if(typeof DD_belatedPNG === "object"){DD_belatedPNG.fix('*');} });</script>
	<![endif]-->
	
	<script type="text/javascript">var ctx='${prc}';</script>
	<style type="text/css">
		.loginPop-logo{height:95px;}
	</style>
</head>
<body>
<div class="zy-header JS-indexPageBox">
    <div class="zy-inner">
        <div class="zy-nav">
            <a href="/" class="logo"></a>
            <!-- <div class="navList">
                <a href="/index.vpage" class="menu active" title="首页">首页</a>
                <a href="http://www.17zuoye.com/help/concept.vpage" class="menu" title="产品概念">产品概念</a>
                <a href="http://www.17zuoye.com/help/uservoice.vpage" class="menu" title="各界声音">各界声音</a>
                <a href="http://www.17zuoye.com/help/news/index.vpage" class="menu" title="新闻中心">新闻中心</a>
                <a href="http://www.17zuoye.com/help/aboutus.vpage" class="menu" title="关于我们">关于我们</a>
                <a href="http://www.17zuoye.com/help/jobs.vpage" class="menu" title="加入我们">加入我们</a>
            </div> -->
        </div>
        <!-- <div class="rightIn">
            <a href="javascript:void(0);" target="_blank" class="load"><i class="phone-icon"></i>APP下载</a>
        </div> -->
    </div>
</div>

<div class="zy-homeContainer JS-indexPageBox" style="height: 100%;">
    <div class="JS-indexSwitch-main">
        <ul class="zy-homeBox slides">
            <li class="homeItem homeItem02">
                <div class="innerBox">
                    <div class="info">
                        <div class="title01" style="font-size: 74px">互问互答</div>
                        <div class="loginBtn clearfix">
                            <div class="zy-header" style="position: static; display: inline-block; width: auto; float: right; padding-left: 20px;">
                                <div class="rightIn" style="display: inline-block; float: none; margin: 0;padding: 0;">
                                    <a href="javascript:void(0);" target="_blank" class="load" style="padding: 0; border: none; float: none; width: auto; font-size: 16px; color: #000;"><i class="phone-icon"></i>APP下载</a>
                                </div>
                            </div>
                           <%-- <a href="javascript:;" class="JS-register-main">注册</a>  --%>
                            <a href="javascript:;" class="active JS-login-main">登录</a>
                        </div>
                    </div>
                </div>
            </li>
            <li class="homeItem homeItem03" >
                <div class="innerBox">
                    <div class="info">
                        
                            <div class="title01"  style="font-size: 74px;">学情分析</div>
                            <!-- <div class="text">
                                <div>新学年，新开始: 一起作业更换了别具寓意的新logo以及视觉形象，我们将带你进入一段充满惊喜的学习旅程……</div>
                                <div class="more"><a href="javascript:void(0);" target="_blank">查看更多</a></div>
                            </div> -->
                      
                        <div class="loginBtn clearfix">
                            <div class="zy-header" style="position: static; display: inline-block; width: auto; float: right; padding-left: 20px;">
                                <div class="rightIn" style="display: inline-block; float: none; margin: 0;padding: 0;">
                                    <a href="javascript:void(0);" target="_blank" class="load" style="padding: 0; border: none; float: none; width: auto; font-size: 16px; color: #000;"><i class="phone-icon"></i>APP下载</a>
                                </div>
                            </div>
                                <%--<a href="javascript:;" class="JS-register-main">注册</a>--%>
                            <a href="javascript:;" class="active JS-login-main">登录</a>
                        </div>
                    </div>
                </div>
            </li>

            <li class="homeItem homeItem01" style="width: 100%">
                <div class="innerBox">
                    <div class="info">
                        <div class="title01" style="font-size: 74px">错题回顾</div>
                        <div class="loginBtn clearfix">
                            <div class="zy-header" style="position: static; display: inline-block; width: auto; float: right; padding-left: 20px;">
                                <div class="rightIn" style="display: inline-block; float: none; margin: 0;padding: 0;">
                                    <a href="javascript:void(0);" target="_blank" class="load" style="padding: 0; border: none; float: none; width: auto; font-size: 16px; color: #000;"><i class="phone-icon"></i>APP下载</a>
                                </div>
                            </div>
                               <%-- <a href="javascript:;" class="JS-register-main">注册</a>--%>
                            <a href="javascript:;" class="active JS-login-main">登录</a>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <ul class="zy-scrollNav JS-indexSwitch-mode">
        <li style="left: -16px;"><a>1</a></li>
        <li><a>2</a></li>
        <li class="flex-active" style="right: -16px;"><a>3</a></li>
    </ul>
</div>
<div id="RenderMain" class="JS-indexPageBox"></div>
<script type="text/html" id="T:LoginTemplateMain">
        <div class="loginPop-box loginBg">
        <div class="loginPop-close JS-clear-btn"></div>
        <div class="lop-inner">
            <div class="loginPop-logo"></div>
            <h1>登录</h1>
                <form id="FormSubmitInit" class="JS-formSubmit" method="post" action="${ctx}/login">
                    <input name="returnURL" type="hidden" value=''>
                    <div class="lop-content">
  						 <div class="c-text" >
							<span class="login-icon icon-5" style="background:url(${ctxStatic}/index/images/school_icon.png) no-repeat;background-size:100%;"></span>
							<div classs="ws" style="width:320px;height:40px;margin:0 auto;background:#fff;border:1px solid #999;border-radius:25px;">
                              <select id="index-select-school"  name="schoolId" class="JS-inputEvent" style="width:82%;height:40px;position:relative;right:-10px;background:#fff;border:0 none;border-radius:10px;">
    							  <option value="">请选择</option>
							     <c:forEach  items="${student:getAllSchool()}"  var="item"  >
 									 <option value="${item.schoolId}">${item.schoolName}</option>
							     </c:forEach>
							  </select>
							</div>
                            <div class="errorTips">请先选择学校</div>
                        </div>
						
                        <div class="c-text">
                            <span class="login-icon icon-5"></span>
                            <input type="text" value="${loginName}" name="loginName" tabIndex="1"
                                   class="JS-inputEvent" id="index_login_username" placeholder="登录账号"/>
                            <div class="errorTips">请输入登录账号</div>
                        </div>
                        <div class="c-text">
                            <span class="login-icon icon-4" id="index_login_icon"></span>
                            <input type="password" id="index_login_password" name="password" class="JS-inputEvent"
                                   tabIndex="2" placeholder="输入密码"/> 
                            <div class="errorTips"></div>
                        </div>
                        <div class="c-text">
                            <a href="javascript:void(0);"  class="info active JS-rememberMe-btn">
                                <%--<input name="_spring_security_remember_me" value="on" type="checkbox" checked="checked"
                                       style="display: none;"/>
                                <span class="login-icon icon-6"></span>记住我--%>
                            </a>
                            <a href="javascript:void(0);"  class="info JS-forgetPassword-btn">忘记密码</a>
							<span class="line"></span> &nbsp;&nbsp;&nbsp;&nbsp;  <a href="javascript:void(0);" class="info JS-showPassword-btn">显示密码</a>
                        </div>
                    </div>
                    <div class="loginPop-footer">
                        <input type="submit" value="登录" class="login-btn" id="_a_loginForm" name="_a_loginForm" tabIndex="3"/>
                        <%--<div class="qq-btn">
                            <a href="/qq/authorizecode.vpage" target="_blank" class="info-btn">用QQ登录</a>
                        </div>--%>


                   <p style="margin-top: 100px;  color: red;" >学生账号如果已经在线，再次登录将不被允许进入。</p>
                    </div>


			 
			
                </form>
        </div>
    </div>
</script>
<script  type='text/javascript' src='${ctxStatic }/modules/Student/login/login.js' charset='utf-8'></script>
<c:if test="${ not empty message }">
<script type="text/javascript">
	$(".JS-login-main").trigger('click');
	baseErrorForgot('<p style="margin-top: -10px;">登录失败！,${message}<br/><a href="javascript:void(0);"  class="info JS-forgetPassword-btn">忘记学号/密码了？</a></p>');
	//基础错误提示
	function baseErrorForgot(val){
	 var errorInfoIdx =  $("#index_login_password");
	
	 errorInfoIdx.parent().addClass('error');
	 errorInfoIdx.siblings(".errorTips").html(val);
	 setTimeout(function(){
	     errorInfoIdx.parent().removeClass('error');
	     errorInfoIdx.siblings(".errorTips").html("");
	 }, 3000);
	
	 $("#index_login_username, #index_login_password").on("keydown", function(){
	     errorInfoIdx.parent().removeClass('error');
	     errorInfoIdx.siblings(".errorTips").html("");
	 });
	}
</script>
</c:if>	
</body>
</html>

