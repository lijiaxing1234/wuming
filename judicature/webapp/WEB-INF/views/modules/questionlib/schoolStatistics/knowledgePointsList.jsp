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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/questionlib/statistics/knowledgePointsList?extId=${extId}&courseVesionId=${courseVesionId}">知识点列表</a></li>
		  
	</ul>
	<form:form id="searchForm" modelAttribute="courseKnowledge" action="${ctx}/questionlib/statistics/knowledgePointsList" method="post" class="breadcrumb form-search">
		<input type="hidden" name="operation" value="search"/>
		<input type="hidden" name="id" value="${extId}"/>
		<input type="hidden" name="parentIds" value="${parentIds}"/>
		<ul class="ul-form">
			<li><label>时间范围：</label>
				 <select name ="pl">
				   <option value="1"  ${pl eq "1"?"selected":""}>一个月内</option>
				   <option value="2"  ${pl eq "2"?"selected":""}>两个月内</option>
				   <option value="3"  ${pl eq "3"?"selected":""}>三个月内</option>
				   <option value="4"  ${pl eq "4"?"selected":""}>四个月内</option>
				   <option value="5"  ${pl eq "5"?"selected":""}>五个月内</option>
				   <option value="6"  ${pl eq "6"?"selected":""}>六个月内</option>
				   <option value="12"  ${pl eq "12"?"selected":""}>一年内</option>
				   <option value="24"  ${pl eq "24"?"selected":""}>两年内</option>
				 </select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			 
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>知识点</th>
			 
			    <th>出题频率</th> 
			     
			     <th>使用人次</th> 
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td> 
				{{row.title}}
			 </td>
			 
			 
			 
             <td>
   				 {{row.count1}}
			</td>
              <td>
   				 {{row.count2}}
			</td>
		</tr>
	</script>
</body>
</html>