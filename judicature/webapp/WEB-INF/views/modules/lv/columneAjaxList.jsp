<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<%--
<%@ include file="/WEB-INF/views/include/treetable.jsp"%>
<script type="text/javascript">
	$(document).ready(function() {
		$("#treeTable").treeTable({
			expandLevel : 5
		});
	});

	function deleteShelfColumn(colId) {
		if (confirm("<fmt:message key='confirm.deletecolumn'></fmt:message>")) {
			$
					.ajax({
						type : "POST", //请求方式
						url : "${ctx}/admin/shelfColumn/getColumnInforByCatId", //请求路径
						cache : false, //(默认: true,dataType为script和jsonp时默认为false) jQuery 1.2 新功能，设置为 false 将不缓存此页面。
						data : "colId=" + colId,
						dataType : "json",
						success : function(data) {
							var obj = jQuery.parseJSON(data);
							if (obj) {
								alert("<fmt:message key='deletesuccess'></fmt:message>");
								location = "${ctx}/admin/shelfColumn/shelfColumnlist";
							} else {
								alert("<fmt:message key='column.hasebook'></fmt:message>");
							}
						}
					});
		}
	}
</script> --%>
<table id="treeTable" class="table  table-bordered ">
	<thead>
		<tr>
			<th width="15%"   style="text-align:center;">课程ID</th>
			<th width="30%"   style="text-align:center;">课程名称</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${page.list}" var="shelfColumn" varStatus="loop">
			<%-- <tr id="${shelfColumn.course_id}" pId="${shelfColumn.parentId}"> --%>
		    <tr id="${shelfColumn.course_id}" >
				<td style="text-align:center;" >${shelfColumn.course_id}</td>
				<td  style="text-align:left;">
				      <a href="${ctx}/admin/lv/getColumnIframe?colId=${shelfColumn.course_id}"  target="shelfColumnIframe" onclick="changeColor(this)">${shelfColumn.course_name}</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<div class="pagination">${page}</div>