<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>题库授权</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<style type="text/css">
		.ztree {overflow:auto;margin:0;_margin-top:10px;padding:10px 0 0 10px;}
	</style>
</head>
<body>
	<sys:message content="${message}"/>
	<form:form id="searchForm" modelAttribute="schoolQuestionlib" action="${ctx}/questionlib/schoolQuestionlib/index" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	</form:form>
	<div id="content" class="row-fluid">
	<%-- 	<div id="left" class="accordion-group">
			<div class="accordion-heading">
		    	<a class="accordion-toggle" href="${ctx}/questionlib/schoolQuestionlib/index">学校列表<i class="icon-refresh pull-right"></i></a>
		    </div>
			<table id="treeTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th style="width:20px;">序号</th>
						<th>学校</th>
					</tr>
				</thead>
				<tbody id="treeTableList">
					<c:forEach items="${schoolList}" var="office" varStatus="status">
						<tr>
							<td>${status.index+1 }</td>
							<td><a href="#" onclick="showQuestionlib('${office.id}')">${office.name }</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div> --%>
		<div style="margin-left: 20px;">学校名称：
		         <select style="width:350px" onchange="showQuestionlib()" id="schoolId"> <!-- class="input-medium" -->
		 	     	 <option value="" >请选择</option>  
		 	     <c:forEach items="${schoolList}" var="school">
				   	<option value="${school.id}"  >${school.name} </option>
				 </c:forEach>
			 	</select>
	    </div>
			 	<br/>
		<div id="right" style="margin-left: 10px;">
			<iframe id="officeContent" src="${ctx}/questionlib/schoolQuestionlib/list?schoolId=" width="100%" height="91%" frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
		function showQuestionlib(){
			
			var schoolId =$("#schoolId").val();
		 
			$('#officeContent').attr("src","${ctx}/questionlib/schoolQuestionlib/list?schoolId="+schoolId);
		}
		
		var leftWidth = 180; // 左侧窗口大小
		var htmlObj = $("html"), mainObj = $("#main");
		var frameObj = $("#left, #openClose, #right");//, #right iframe
		function wSize(){
			var strs = getWindowSize().toString().split(",");
			htmlObj.css({"overflow-x":"hidden", "overflow-y":"hidden"});
			mainObj.css("width","auto");
			frameObj.height(strs[0] - 5);
			var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
			$("#right").width($("#content").width()- leftWidth - $("#openClose").width() -5);
			$(".ztree").width(leftWidth - 10).height(frameObj.height() - 46);
		    <%--$('#officeContent').attr("src","${ctx}/questionlib/schoolQuestionlib/list");--%>
		}
		 
		
	</script>
	<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>