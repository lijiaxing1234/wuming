<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html >
<head>
<meta charset="utf-8">
<%-- <link href="${ctxStatic}/styles/shouji.css" rel="stylesheet" type="text/css" /> --%>
<title>预览</title>
</head>
<body>
	<div id="box">
		<div class="top">
			<div class="topa" style="font-size:78px;line-height:100px;">
			  <%--  ${imple.newTitle} --%>
			</div>
			<div class="topb" style="font-size:36px;text-align:right;"><fmt:formatDate value='${lvCourse.createTime}' pattern="MM-dd HH:mm" /></div>
		</div>
		<div id="conter">
			<c:forEach items="${implementList }" var="imple" >
				<c:if test="${imple.type=='2'}">
					<div class="tupian" align="center">
					<!-- <div class="tupian" > -->
						
			<c:choose>
			    <c:when test="${imple.content eq null  || empty imple.content}">
			        <img src="${imple.content } "  />
			    </c:when>
			    <c:when test="${not empty imple.content}">
			        <img src="${imple.content } "  />
			    </c:when>
			</c:choose>
					</div>
				</c:if>
				<c:if test="${imple.type=='1'}">
					<div style="font-size:50px;width:100%; margin:0px 0px 0px 0px; line-height:70px; text-indent:2em; font-size:50px; color:#4b4b4b; text-align:justify; text-justify:inter-ideograph; ">${imple.content }</div>
				</c:if>
			</c:forEach>
		</div>
		<SCRIPT LANGUAGE="JavaScript">
			var divs = document.getElementsByTagName("div");
			for ( var i = 0; i < divs.length; i++) {
				if (divs[i].className.indexOf("conner") !== -1) {
					var str = divs[i].innerHTML.replace(/[\x00-\xff]/g,
							function(s) {
								return s + " "
							});
					divs[i].innerHTML = str;
				}
			}
		</SCRIPT>
	</div>
</body>
</html>