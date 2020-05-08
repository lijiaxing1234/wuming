<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程管理</title>
	<meta name="decorator" content="default"/>
	<%-- <script type="text/javascript" src="${ctxStatic}/jerichotab/js/jquery.jerichotab.js"></script> --%>
	<script type="text/javascript">
	function recom(id,status){
		$.ajax({
			type : "POST", //请求方式
			url : "${ctx}/admin/lv/updateRecom?lvId="+id+"&status="+status, //请求路径
			cache : false, //(默认: true,dataType为script和jsonp时默认为false) jQuery 1.2 新功能，设置为 false 将不缓存此页面。
			dataType : "text",
			async: false,
			success : function(data) {
				if(data == "0"){
					alert("修改失败，请重试!");
				}else if(data == "1"){
					alert("修改成功!");
				}else if(data == "2"){
					alert("已推荐满3部直播,不可再推荐!");
				}
				location.reload();
			}
		});
	}
	
	</script>
</head>
<body>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
		<tr>
			<th>课程ID</th>
			<th>课程名称</th>
			<th>封面图片</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${page.list}" var="shelfColumn" varStatus="loop">
			<%-- <tr id="${shelfColumn.course_id}" pId="${shelfColumn.parentId}"> --%>
		    <tr id="${shelfColumn.course_id}" >
				<td>${shelfColumn.course_id}</td>
				<td>
				  ${shelfColumn.course_name}
				</td>
				<td style="max-width:210px">
				<img src="${shelfColumn.iconUrl}" style="max-width:210px">
				</td>
				<%-- <td><c:if test="${shelfColumn.recommend == 0}">未推荐</c:if>
				<c:if test="${shelfColumn.recommend == 1}">已推荐</c:if>
				</td> --%>
				<td>
				<a href="javascript:void(0);" onclick="recom('${shelfColumn.course_id}','${shelfColumn.recommend}')">
				<c:if test="${shelfColumn.recommend == 0}">推荐</c:if>
				<c:if test="${shelfColumn.recommend == 1}">取消推荐</c:if>
				</a>/
				<a href="${ctx}/admin/lv/tolvImg?lvId=${shelfColumn.course_id}">添加图片</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>