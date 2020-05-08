<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html style="overflow-x:auto;overflow-y:auto;">
<head>
	<meta charset="UTF-8">
	<meta http-equiv='X-UA-Compatible' content='IE=edge'>
	<title>管理平台</title>
    <link rel="stylesheet" href="${ctxStatic}/index/choose/css/login-page.css">
    <link rel="stylesheet" href="${ctxStatic}/index/choose/css/reset.css">
</head>
<body>
  <!-- 页面头部 -->
    <div class="lg-header">
        <img class="lg-logo" src="${ctxStatic}/index/choose/images/logo.png" alt="">
        <a class="contact-us"  target="_blank" href="help?tp=contactUs">联系我们</a>
        <a class="use-help" target="_blank" href="help?tp=contactUs">使用帮助</a>
    </div>
    <div class="lg-main">
        <div class="main-center">
            <div class="innerbox">
                <h2>数字教学，智慧评测</h2>
                <p class="info">我们致力于提供全程的在线学习解决方案，带来更便捷的教学和学习体验</p>
                <div class="link">
                   <!--  <a href="s/login">我是学生</a>
                    <a href="t/login">我是老师</a> -->
                    <a href="a/login">管理员登录</a>
                </div>
            </div>
            
        </div>
    </div>

</body>
</html>
