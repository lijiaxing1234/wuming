<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
 <meta name="decorator" content="default"/>
 <title>内容列表</title>
<script type="text/javascript">
	function updatecontentImpInfor(contentimpId,typeId, event) {
		$.Event(event).preventDefault();
		parent.updatecontentImpmentInfor(contentimpId,typeId);
	}
</script>
</head>
<body>
	<table id="contentTable" class="table table-striped table-bordered ">
		<thead>
			<tr>
				<th width="6%">序号</th>
				<th width="10%">新闻类型</th>
				<th>新闻内容</th>
				<th width="10%">操作</th>
				<th width="8%">排序</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${columnImplementList}" var="content" varStatus="s">
				<tr charoff="${content.id}" lang="${content.seq}">
				    <td style="display: none;">${content.id}</td>
					<td>${s.index+1+page.pageSize*(page.pageNo-1)}</td>
					<td>
					   <c:if test="${content.type=='1'}">文字</c:if> 
					   <c:if test="${content.type=='2'}">图片</c:if>
					</td>
					<td>
						<c:if test="${content.type=='1'}">
							${content.content}
						</c:if>
					    <c:if test="${content.type=='2'}">
							<c:if test="${!empty content.content}">
								<c:choose>
								    <c:when test="${content.content eq null  || empty content.content}">
								        <img src="${content.content}" height="50" width="">
								    </c:when>
								    <c:when test="${not empty content.content}">
								        <img src="${baseUrl}${content.content}" height="50" width="">
								    </c:when>
								</c:choose>
							</c:if>
						</c:if>
					</td>
					<td>
							<a href="javascript:void(0)" onclick="updatecontentImpInfor('${content.id}','${content.type}')">编辑</a>
							<a href="javascript:void(0)" onClick="parent.deletecontentimple('${content.id}')"  >/删除</a>
					 </td>
					<td>
						<c:if test="${s.index > 0 }">
						   <a href="javascript:void(0)" onClick="parent.moveUp(this)"><i class="icon-arrow-up"></i></a>
						</c:if>
						<c:if test="${s.index + 1 < fn:length(columnImplementList) }">
						    <a href="javascript:void(0)" onClick="parent.moveDown(this)"><i class="icon-arrow-down"></i></a>
						</c:if>
				    </td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>