<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>系统消息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/questionlib/tableMessage/">系统消息列表</a></li>
		<li><a href="${ctx}/questionlib/tableMessage/form">系统消息添加</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="tableMessage" action="${ctx}/questionlib/tableMessage/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<%-- <li><label>标题：</label>
				<form:input path="title" htmlEscape="false" maxlength="30" class="input-medium"/>
			</li> --%>
		<%-- 	<li>
				<label>置顶：</label>
				<form:select path="isTop" style="width:140px;">
					<form:option value="">请选择</form:option>
					<form:option value="1">是</form:option>
					<form:option value="0">否</form:option>
				</form:select>
		   </li> --%>
		    <li><label>创建时间:</label> 
		       <input id="startTime" name="startTime" type="text" maxlength="20" class="input-medium Wdate" value="<fmt:formatDate value="${startTime}" pattern="yyyy-MM-dd"/>"
				onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'endTime\')}'});" />
			~ <input id="endTime" name="endTime" type="text" maxlength="20" class="input-medium Wdate" value="<fmt:formatDate value="${endTime}" pattern="yyyy-MM-dd"/>" 
			    onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,minDate:'#F{$dp.$D(\'startTime\')}'});" />
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<!-- <th>标题</th> -->
				<th>内容</th>
				<!-- <th>是否置顶</th> -->
				<th>发布时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tableMessage">
			<tr>
			<%-- 	<td><a href="${ctx}/questionlib/tableMessage/form?id=${tableMessage.id}">
					${tableMessage.title}
				</a></td> --%>
				<td>
				    <span title="${tableMessage.content}">
				             <c:choose>
								<c:when test="${fn:length(tableMessage.content) > 35}">
									<c:out value="${fn:substring(tableMessage.content, 0, 35)}..." />
								</c:when>
								<c:otherwise>
									<c:out value="${tableMessage.content} " />
								</c:otherwise>
							</c:choose>
					</span>
			     </td>
				  <%--  <td>
					    <c:if test="${notice.isTop==1}">
							是
						</c:if> <c:if test="${notice.isTop==0}">
							否
						</c:if>
				  </td> --%>
				  <td>
				      <fmt:formatDate value='${tableMessage.createtime}' pattern="yyyy-MM-dd HH:mm:ss" />
				 </td>
				 <td>
				     <a href="${ctx}/questionlib/tableMessage/form?id=${tableMessage.id}&show=1">查看</a>
    				<a href="${ctx}/questionlib/tableMessage/form?id=${tableMessage.id}">修改</a>
    				<shiro:hasPermission name="questionlib:tableMessage:delete">
						<a href="${ctx}/questionlib/tableMessage/delete?id=${tableMessage.id}" onclick="return confirmx('确认要删除该系统消息吗？', this.href)">删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>