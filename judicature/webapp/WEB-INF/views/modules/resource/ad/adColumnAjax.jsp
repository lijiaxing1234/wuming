<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<table id="contentTable" class="table table-bordered " style="width: 100%">
	<thead>
		<tr>
			<th></th>
			<th>广告名称</th>
			<th>广告编号</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${page.list}" var="adColumn" varStatus="s">
			<tr>
				<td>${s.index+1}<input type="hidden" name="colIds" value="${adColumn.id}"></td>
				<td><a href="${ctx}/ad/adlist?colId=${adColumn.id}" target="adframe" onclick="changecolor(this)">${adColumn.colName}</a></td>
				<td>${adColumn.adCode}</td>				
			</tr>
		</c:forEach>
	</tbody>
</table>