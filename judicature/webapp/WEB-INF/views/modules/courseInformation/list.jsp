<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>课程管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	function del(id){
		$.ajax({
			type : "POST", //请求方式
			url : "${ctx}/questionlib/course/delci?id="+id, //请求路径
			cache : false, //(默认: true,dataType为script和jsonp时默认为false) jQuery 1.2 新功能，设置为 false 将不缓存此页面。
			dataType : "text",
			success : function(html) {
				alert("删除成功！");
				location.reload();
			},
			error:function(html) {
				alert("网络延迟,删除失败,请重试！");
				location.reload();
			}
		});
	}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/questionlib/course/courseInformationList">课程资讯列表</a></li>
		<li><a href="${ctx}/questionlib/course/toAddci">课程资讯添加</a></li>
	</ul>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th style="width:20px;">序号</th>
			    <th>名称</th>
				<th>图片</th>
				<th>详情链接</th>
				<th>时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.content}" var="ci" varStatus="status">
			<tr>
				<td>${status.index+1+(page.pageNo-1)*page.pageSize }</td>	
				<td>${ci.title }</td>
				<td style="width:200px;">
					<img style="width:200px;" src="${ci.icon }">
				</td>
				<td>
    				<a href="${ci.url }">${ci.url }</a>
				</td>
				<td>
    				${ci.createDate}
				</td>
				<td><a href="javascript:void(0);" onclick="del('${ci.id}')">删除</a>/
				<a href="${ctx}/admin/lv/courseForm?colId=${ci.id}&colCate=1&type=2" target="impleListIframe">自定义新闻管理</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">
	<%@include file="/WEB-INF/views/include/page.jsp" %>
	</div>
</body>
</html>