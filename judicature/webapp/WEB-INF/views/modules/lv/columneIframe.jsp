<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<title>直播课程</title>
<style type="text/css">
	#contentTable{-moz-user-select:none;}
</style>
<script type="text/javascript">
   
    $(function(){
    	$("ul.nav > li").click(function(){
    		$(this).addClass("active").siblings().removeClass("active");
    	});
    	
    	changeColor(0);
    })
    
     function changeColor(obj) {
    	$("ul.nav > li").eq(obj).trigger("click");
    	var url=$("ul.nav > li").eq(obj).find("a:first").attr("href");
    	$("#impleListIframe").attr("src", url ? url : "about:blank");
	}

</script>
</head>
<body>
    
    <ul class="nav nav-tabs">
        <c:forEach   var="item"  items="${fns:getDictList('lv_course_id') }">
            <li><a href="${ctx}/admin/lv/getpreViewByID?colId=${columnKey.colId}&colCate=${item.value}" target="impleListIframe">${item.label }</a></li>
            <li><a href="${ctx}/admin/lv/courseForm?colId=${columnKey.colId}&colCate=${item.value}" target="impleListIframe">添加${item.label }</a></li>
        </c:forEach>
		<li><a href="${ctx}/admin/LVFeedback/list?lvCourseId=${columnKey.colId}" target="impleListIframe" >课程评价</a></li>
	</ul> 

	<div class="container-fluid">
	    <iframe name="impleListIframe"  frameborder="0" width="100%" height="540px" id="impleListIframe"></iframe>
	</div>
	
	
	<script type="text/javascript"> 
		var frameObj = $("#impleListIframe"),
		    leftWidth=$("div.container-fluid").width();
		function wSize(){
			var minHeight = 540;
			var strs = getWindowSize().toString().split(",");
			frameObj.height((strs[0] < minHeight ? minHeight : strs[0]));
		}
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>