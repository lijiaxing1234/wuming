<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>ios</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		
		function toTrue(){
			$.ajax({
				type : "POST", //请求方式
				url : "${ctx}/questionlib/common/upiosStatus?s=1", //请求路径
				cache : false, //(默认: true,dataType为script和jsonp时默认为false) jQuery 1.2 新功能，设置为 false 将不缓存此页面。
				dataType : "text",
				async: false,
				success : function(data) {
					if(data == "0"){
						alert("修改失败，请重试!");
					}else if(data == "1"){
						alert("修改成功!");
					}
					location.reload();
				}
			});
		}
		
		function toFalse(){
			$.ajax({
				type : "POST", //请求方式
				url : "${ctx}/questionlib/common/upiosStatus?s=0", //请求路径
				cache : false, //(默认: true,dataType为script和jsonp时默认为false) jQuery 1.2 新功能，设置为 false 将不缓存此页面。
				dataType : "text",
				async: false,
				success : function(data) {
					if(data == "0"){
						alert("修改失败，请重试!");
					}else if(data == "1"){
						alert("修改成功!");
					}
					location.reload();
				}
			});
		}
	</script>
</head>
<body>
	<button onclick="toTrue()">变为true</button> &nbsp;&nbsp;<button onclick="toFalse()">变为false</button> 
</body>
</html>