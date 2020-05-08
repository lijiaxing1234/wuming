<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>直播管理</title>
<meta name="decorator" content="default"/>
<style type="text/css">
.addColor {
	background-color: #cdcdcd;
}
</style>
<script type="text/javascript">

	$(document).ready(function() {
		getshelfColumn({});
	});
	
	function columneAjaxPage(n,s){
		getshelfColumn({pageNo:n,pageSize:s});
	}
	function getshelfColumn(param) {
		$.ajax({
			type : "POST", //请求方式
			url : "${ctx}/admin/lv/columneAjaxList", //请求路径
			cache : false, //(默认: true,dataType为script和jsonp时默认为false) jQuery 1.2 新功能，设置为 false 将不缓存此页面。
			data : param,
			dataType : "html",
			success : function(html) {
				$("#shelfColumnInfor").html(html);
				var url = $("#shelfColumnInfor table a:first").attr("href");
				$("#shelfColumnInfor table a:first").parent().parent().addClass("addColor");
				$("#shelfColumnIframe").attr("src", url ? url : "about:blank");
			}
		});
	}
	function changeColor(obj) {
		$("#shelfColumnInfor table tr").removeClass("addColor");
		$(obj).parent().parent().addClass("addColor");
	}
	function gotoIfream() {
		$("#shelfColumnInfor table tr").each(function() {
			if ($(this).hasClass("addColor")) {
				url = $(this).find("a").attr("href");
			}
		});
		$("#shelfColumnIframe").attr("src", url ? url : "about:blank");
	}
</script>
</head>
<body>
	<%-- <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/admin/shelfColumn/shelfColumnlist">直播管理</a></li>
	</ul> 
	<form:form id="searchForm" modelAttribute="shelfColumn"
		action="${ctx}/admin/shelfColumn/shelfColumnlist" method="post"
		class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<input id="colId" name="colId" type="hidden" value="" />
		<div>
			<label><fmt:message key="shelf"></fmt:message> <fmt:message
					key="comma"></fmt:message></label>
			<form:select path="shelfId">
				<form:options items="${shelfList}" itemLabel="shelfName"
					itemValue="shelfId" htmlEscape="false" />
			</form:select>
			&nbsp;&nbsp; <input id="btnSubmit" class="btn btn-primary"
				type="button" onclick="getshelfColumn()"
				value="<fmt:message key='inquire'></fmt:message>" />
		</div>
	</form:form> --%>
	<tags:message content="${message}" />
	<div class="container-fluid">
		<div class="row-fluid">
			<div id="shelfColumnInfor" class="span4"></div>
			<div class="span8" style="margin-left: 1%;">
				<iframe name="shelfColumnIframe" frameborder="0" width="100%" height="540px" id="shelfColumnIframe"></iframe>
			</div>
		</div>
	</div>
	
	
<script type="text/javascript"> 
	var frameObj = $("#shelfColumnIframe"),
	    leftWidth=$("div.container-fluid  >  div.row-fluid").width();
	function wSize(){
		var minHeight = 540;
		var strs = getWindowSize().toString().split(",");
		frameObj.height((strs[0] < minHeight ? minHeight : strs[0]));
	}
</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>