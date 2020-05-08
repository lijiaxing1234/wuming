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
	   .btn-primary{ text-indent: 0;}
	   .form-search .ul-form li{margin-bottom: 5px;}
	   .form-search .ul-form li.btns{padding-left: 5px;} 
	   .btn{text-indent: 0;} 
	   
	   
	   .textArray{width: 570px  ! important;}
	</style>		
	<sitemesh:head/>
</head>
<body>
	<sitemesh:body/>
	<%--<script type="text/javascript">//<!-- 无框架时，左上角显示菜单图标按钮。
		if(!(self.frameElement && self.frameElement.tagName=="IFRAME")){
			$("body").prepend("<i id=\"btnMenu\" class=\"icon-th-list\" style=\"cursor:pointer;float:right;margin:10px;\"></i><div id=\"menuContent\"></div>");
			$("#btnMenu").click(function(){
				top.$.jBox('get:${ctx}/sys/menu/treeselect;JSESSIONID=<shiro:principal property="sessionid"/>', {title:'选择菜单', buttons:{'关闭':true}, width:300, height: 350, top:10});
				//if ($("#menuContent").html()==""){$.get("${ctx}/sys/menu/treeselect", function(data){$("#menuContent").html(data);});}else{$("#menuContent").toggle(100);}
			});
		}//-->
	</script> --%>
</body>
</html>