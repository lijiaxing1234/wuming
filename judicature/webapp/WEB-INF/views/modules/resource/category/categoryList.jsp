<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>知识点管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			refresh();
		});
		function refresh(){
			console.log(${fns:toJson(list)});
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].id);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].parentId+',') == -1){
					if ((','+rootIds.join(',')+',').indexOf(','+data[i].parentId+',') == -1){
						rootIds.push(data[i].parentId);
					}
				}
			}
			for (var i=0; i<rootIds.length; i++){
				addRow("#treeTableList", tpl, data, rootIds[i], true);
			}
			$("#treeTable").treeTable({expandLevel : 5});
		}
		
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
						blank123:0}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
		
		function find()
		{
			$("#searchForm").submit();
		}
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/category/list">分类列表</a></li>
		<shiro:hasPermission name="questionlib:category:edit"><li><a href="${ctx}/category/form">分类添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="courseKnowledge" action="${ctx}/category/list" method="post" class="breadcrumb form-search">
		<input type="hidden" name="operation" value="search"/>
		<input id="extId" type="hidden" name="extId" value="${extId==null?'1':extId}"/>
		<input id="courseVesionId" type="hidden" name="courseVesionId" value="${courseVesionId}"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>分类2</th>
				<th>栏目ID</th>
				<shiro:hasPermission name="questionlib:category:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<div style="height:100px; ">&nbsp;</div> 
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="${ctx}/category/form?id={{row.id}}">
				{{row.name}}
			</a></td>
			<td> 
				{{row.id}}
			 </td>
			<shiro:hasPermission name="questionlib:category:edit"><td>
   				<a href="${ctx}/category/form?id={{row.id}}">修改</a>
				<a href="${ctx}/category/delete?cateId={{row.id}}" onclick="return confirmx('确认要删除该分类及所有子分类吗？', this.href)">删除</a>
			</td>
             </shiro:hasPermission>
		</tr>
	</script>
</body>
</html>